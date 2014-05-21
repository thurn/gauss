package ca.thurn.noughts.shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ca.thurn.noughts.shared.entities.Action;
import ca.thurn.noughts.shared.entities.Command;
import ca.thurn.noughts.shared.entities.Game;
import ca.thurn.noughts.shared.entities.GameStatus;
import ca.thurn.noughts.shared.entities.Profile;
import ca.thurn.noughts.shared.entities.Pronoun;
import ca.thurn.testing.SharedTestCase;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.Firebase.CompletionListener;
import com.firebase.client.FirebaseError;

public class ModelTest extends SharedTestCase {
  private Model model;
  private String userId;
  private String userKey;
  private Firebase firebase;
  private Map<Firebase, ChildEventListener> listenersToCleanUp;

  private static abstract class TestGameUpdateListener implements GameUpdateListener {
    @Override
    public void onGameUpdate(Game game) {}
    @Override
    public void onCurrentActionUpdate(Action currentAction) {}
    @Override
    public void onGameStatusChanged(GameStatus status) {}
    @Override
    public void onProfileRequired(String gameId, String name) {}
  }

  private static class TestPushNotificationService implements PushNotificationService {
    @Override
    public void addChannel(String channelId) {}
    @Override
    public void removeChannel(String channelId) {}
    @Override
    public void sendPushNotification(String channelId, Map<String, String> data) {}
  }

  private static class TestAnalyticsService implements AnalyticsService {
    @Override
    public void trackEvent(String name) {}

    @Override
    public void trackEvent(String name, Map<String, String> dimensions) {}
  }

  @Override
  public void sharedSetUp(final Runnable done) {
    listenersToCleanUp = new HashMap<Firebase, ChildEventListener>();
    firebase = new Firebase("https://noughts-test.firebaseio-demo.com");
    firebase.removeValue(new CompletionListener() {
      @Override public void onComplete(FirebaseError error, Firebase ref) {
        userId = "id" + randomInteger();
        userKey = "K" + userId;
        model = Model.anonymousModel(userId, userKey, firebase, new TestPushNotificationService(),
            new TestAnalyticsService());
        done.run();
      }
    });
  }

  @Override
  public void sharedTearDown() {
    model.removeAllFirebaseListeners();
    for (Entry<Firebase, ChildEventListener> entry : listenersToCleanUp.entrySet()) {
      entry.getKey().removeEventListener(entry.getValue());
    }
  }

  public void testIsCurrentPlayer() {
    Game g1 = newGameWithTwoPlayers().setIsGameOver(true).build();
    assertFalse(model.isCurrentPlayer(g1));

    Game g2 = newGameWithTwoPlayers().setPlayer(0, "foo").build();
    assertFalse(model.isCurrentPlayer(g2));

    Game g3 = newGameWithTwoPlayers().build();
    assertTrue(model.isCurrentPlayer(g3));

    Game g4 = newGameWithTwoPlayers().setCurrentPlayerNumber(1).build();
    assertFalse(model.isCurrentPlayer(g4));
  }

  public void testGetPreliminaryGameId() {
    assertNotNull(model.getPreliminaryGameId());
  }

  public void testNewGame() {
    beginAsyncTestBlock();
    model.setGameListListener(new AbstractGameListListener() {
      @Override
      public void onGameAdded(Game game) {
        assertTrue(game.getPlayerList().contains(userId));
        assertEquals(Model.X_PLAYER, game.getCurrentPlayerNumber());
        assertTrue(game.getLastModified() > 0);
        assertTrue(game.isLocalMultiplayer());
        assertFalse(game.isGameOver());
        assertEquals(0, game.getSubmittedActionCount());
        assertEquals("John", game.getProfile(0).getName());
        finished();
      }
    });
    List<Profile> localProfiles = new ArrayList<Profile>();
    Profile profile = Profile.newBuilder().setName("John").build();
    localProfiles.add(profile);
    String id = model.newLocalMultiplayerGame(localProfiles);
    assertFalse(id.equals(""));
    endAsyncTestBlock();
  }

  public void testNewGameGameUpdate() {
    beginAsyncTestBlock();
    List<Profile> profiles = new ArrayList<Profile>();
    Profile.Builder profile = Profile.newBuilder();
    profile.setName("John");
    profile.setPronoun(Pronoun.NEUTRAL);
    profiles.add(profile.build());
    String id = model.newGame(profiles);
    model.setGameUpdateListener(id, new TestGameUpdateListener() {
      @Override
      public void onGameUpdate(Game game) {
        assertTrue(game.getPlayerList().contains(userId));
        assertEquals(Model.X_PLAYER, game.getCurrentPlayerNumber());
        assertTrue(game.getLastModified() > 0);
        assertFalse(game.isLocalMultiplayer());
        assertFalse(game.isGameOver());
        assertEquals(0, game.getSubmittedActionCount());
        assertEquals("John", game.getProfile(0).getName());
        finished();
      }
    });
    endAsyncTestBlock();
  }

  public void testSetProfileForViewer() {
    beginAsyncTestBlock(2);
    final Game game = newGameWithTwoPlayers().build();
    Action action = newEmptyAction(game.getId());
    final Profile profile = Profile.newBuilder()
        .setName("name")
        .setIsComputerPlayer(false)
        .build();
    withTestData(game, action, new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.getId(), new TestGameUpdateListener() {
          @Override
          public void onGameUpdate(Game game) {
            assertEquals(profile, game.getProfile(0));
            finished();
          }
        }, false /* immediate */);
        model.setProfileForViewer(game.getId(), profile, new OnMutationCompleted() {
          @Override
          public void onMutationCompleted(Game game) {
            assertEquals(profile, game.getProfile(0));
            finished();
          }
        });
      }
    });
    endAsyncTestBlock();
  }

  public void testAddCommandToExistingAction() {
    beginAsyncTestBlock(2);
    final Game.Builder game = newGameWithTwoPlayers();
    final Command command = Command.newDeserializer()
        .deserialize(map("column", 2, "row", 2));
    withTestData(game.build(), newEmptyAction(game.getId()), new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.getId(), new TestGameUpdateListener() {
          @Override
          public void onGameUpdate(Game update) {
            assertTrue(update.getLastModified() > 200);
            finished();
          }

          @Override
          public void onCurrentActionUpdate(Action currentAction) {
            assertEquals(0, currentAction.getFutureCommandCount());
            assertEquals(command, currentAction.getCommand(0));
            assertEquals(0, currentAction.getPlayerNumber());
            assertEquals(game.getId(), currentAction.getGameId());
            finished();
          }
        }, false /* immediate */);
        model.addCommand(game.getId(), command);
      }
    });
    endAsyncTestBlock();
  }

  public void testUpdateLastCommand() {
    beginAsyncTestBlock();
    final Game game = newGameWithTwoPlayers().build();
    Action.Builder action = newUnsubmittedActionWithCommand(game.getId());
    final Command command = Command.newBuilder().setColumn(2).setRow(2).build();
    withTestData(game, action.build(), new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.getId(), new TestGameUpdateListener() {
          @Override
          public void onCurrentActionUpdate(Action currentAction) {
            assertEquals(list(command), currentAction.getCommandList());
            finished();
          }
        }, false /* immediate */);
        model.updateLastCommand(game.getId(), command);
      }
    });
    endAsyncTestBlock();
  }

  public void testCouldUpdateLastCommand() {
    Game game = newGameWithTwoPlayers().build();
    Action action = newUnsubmittedActionWithCommand(game.getId()).build();
    Command command = Command.newBuilder().setRow(1).setColumn(1).build();
    assertTrue(model.couldUpdateLastCommand(game, action, command));
  }

  public void testCouldNotUpdateLastCommand() {
    Game game = newGameWithTwoPlayers().build();
    Command command = Command.newBuilder().setRow(1).setColumn(1).build();
    assertFalse(model.couldUpdateLastCommand(game, newEmptyAction(game.getId()), command));
  }

  public void testAddCommandNotCurrentPlayer() {
    beginAsyncTestBlock();
    final Game game = newGameWithTwoPlayers()
        .clearPlayerList()
        .addPlayer("opponent")
        .addPlayer(userId)
        .build();
    withTestData(game, newEmptyAction(game.getId()), new Runnable() {
      @Override
      public void run() {
        assertDiesAndFinish(new Runnable() {
          @Override public void run() {
            model.addCommand(game.getId(), newCommand(0, 0));
          }
        });
      }
    });
    endAsyncTestBlock();
  }

  public void testAddCommandToNewAction() {
    beginAsyncTestBlock(2);
    final Game.Builder game = newGameWithTwoPlayers();
    final Command command = newCommand(1, 1);
    withTestData(game.build(), newEmptyAction(game.getId()), new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.getId(), new TestGameUpdateListener() {
          @Override
          public void onGameUpdate(Game update) {
            assertTrue(update.getLastModified() > 200);
            finished();
          }

          @Override
          public void onCurrentActionUpdate(Action currentAction) {
            assertEquals(0, currentAction.getPlayerNumber());
            assertFalse(currentAction.isSubmitted());
            assertEquals(game.getId(), currentAction.getGameId());
            assertEquals(1, currentAction.getCommandCount());
            assertEquals(command, currentAction.getCommand(0));
            finished();
          }
        }, false /* immediate */);
        model.addCommand(game.getId(), command);
      }
    });
    endAsyncTestBlock();
  }

  public void testAddCommandAndSubmit() {
    beginAsyncTestBlock();
    final Game.Builder game = newGameWithTwoPlayers();
    final Command command = newCommand(1, 1);
    withTestData(game.build(), newEmptyAction(game.getId()), new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.getId(), new TestGameUpdateListener() {
          @Override
          public void onGameUpdate(Game game) {
            assertEquals(command, game.getSubmittedAction(0).getCommand(0));
            assertEquals(1, game.getCurrentPlayerNumber());
            finished();
          }
        }, false /* immediate */);
        model.addCommandAndSubmit(game.getId(), command);
      }
    });
    endAsyncTestBlock();
  }

  public void testCouldAddCommand() {
    Command command = newCommand(1, 1);
    Game.Builder game = newGameWithTwoPlayers();
    game.setIsGameOver(true);
    assertFalse(model.couldAddCommand(game.build(), newEmptyAction(game.getId()), command));

    game = newGameWithTwoPlayers();
    Action action2 = newUnsubmittedActionWithCommand(game.getId()).build();
    assertFalse(model.couldAddCommand(game.build(), action2, command));

    game.addSubmittedAction(Action.newBuilder()
        .setIsSubmitted(true)
        .addCommand(newCommand(2, 1)));
    assertTrue(model.couldAddCommand(game.build(), newEmptyAction(game.getId()), command));

    game.clearSubmittedActionList();
    game.addSubmittedAction(Action.newBuilder().setIsSubmitted(true).addCommand(command));
    assertFalse(model.couldAddCommand(game.build(), newEmptyAction(game.getId()), command));
  }

  public void testCanUndo() {
    Game.Builder game = newGameWithTwoPlayers();
    Action.Builder action = newUnsubmittedActionWithCommand(game.getId());
    assertTrue(model.canUndo(game.build(), action.build()));
    action.clearCommandList();
    assertFalse(model.canUndo(game.build(), action.build()));
  }

  public void testCanRedo() {
    Game.Builder game = newGameWithTwoPlayers();
    Action.Builder action = newUnsubmittedActionWithCommand(game.getId());
    assertFalse(model.canRedo(game.build(), action.build()));
    Command command = action.getCommandList().remove(0);
    action.addFutureCommand(command);
    assertTrue(model.canRedo(game.build(), action.build()));
  }

  public void testCanSubmit() {
    Game.Builder game = newGameWithTwoPlayers();
    Action.Builder action = newUnsubmittedActionWithCommand(game.getId());
    assertTrue(model.canSubmit(game.build(), action.build()));
    action.clearCommandList();
    assertFalse(model.canSubmit(game.build(), action.build()));
    action.addCommand(newCommand(0, 5));
    assertFalse(model.canSubmit(game.build(), action.build()));
  }

  public void testComputeVictors() {
    Game.Builder game = newGameWithTwoPlayers();
    game.addAllSubmittedAction(list(
      action(0, 0, 0, true),
      action(1, 0, 1, true)
    ));
    Action currentAction = action(0, 0, 2, false);
    assertNull(model.computeVictorsIfSubmitted(game.build(), currentAction));

    game = newGameWithTwoPlayers();
    game.addAllSubmittedAction(list(
      action(0, 0, 0, true),
      action(0, 0, 1, true)
    ));
    currentAction = action(0, 0, 2, false);
    assertDeepEquals(list(0), model.computeVictorsIfSubmitted(game.build(), currentAction));

    game = newGameWithTwoPlayers();
    game.addAllSubmittedAction(list(
      action(0, 0, 0, true),
      action(0, 1, 1, true)
    ));
    currentAction = action(0, 2, 2, false);
    assertDeepEquals(list(0), model.computeVictorsIfSubmitted(game.build(), currentAction));

    game = newGameWithTwoPlayers();
    game.addAllSubmittedAction(list(
      action(0, 0, 2, true),
      action(1, 1, 1, true),
      action(0, 1, 2, true),
      action(1, 0, 1, true)
    ));
    currentAction = action(0, 2, 2, false);
    assertDeepEquals(list(0), model.computeVictorsIfSubmitted(game.build(), currentAction));

    game = newGameWithTwoPlayers();
    game.addPlayer("x");
    game.addPlayer("o");
    game.addAllSubmittedAction(list(
      action(1, 0, 0, true),
      action(0, 0, 1, true),
      action(1, 0, 2, true),
      action(1, 1, 0, true),
      action(0, 1, 1, true),
      action(1, 1, 2, true),
      action(0, 2, 0, true),
      action(1, 2, 1, true)
    ));
    currentAction = action(0, 2, 2, false);
    assertDeepEquals(list(0, 1), model.computeVictorsIfSubmitted(game.build(), currentAction));
  }

  public void testSubmitCurrentAction() {
    beginAsyncTestBlock(2);
    final Game game = newGameWithTwoPlayers().build();
    final Action action = newUnsubmittedActionWithCommand(game.getId()).build();
    withTestData(game, action, new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.getId(), new TestGameUpdateListener() {
          @Override
          public void onGameUpdate(Game game) {
            assertEquals(1, game.getCurrentPlayerNumber());
            assertEquals(action.getCommand(0),
                game.getSubmittedAction(game.getSubmittedActionCount() - 1).getCommand(0));
            finished();
          }

          @Override
          public void onCurrentActionUpdate(Action currentAction) {
            assertEquals(newEmptyAction(game.getId()), currentAction);
            finished();
          }
        }, false /* immediate */);
        model.submitCurrentAction(game.getId());
      }
    });
    endAsyncTestBlock();
  }

  public void testSubmitCurrentActionUpdatesGameStatus() {
    beginAsyncTestBlock();
    final Game game = newGameWithTwoPlayers().build();
    Action action = newUnsubmittedActionWithCommand(game.getId()).build();
    withTestData(game, action, new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.getId(), new TestGameUpdateListener() {
          @Override
          public void onGameStatusChanged(GameStatus status) {
            assertEquals(1, status.getStatusPlayer());
            finished();
          }
        }, false /* immediate */);
        model.submitCurrentAction(game.getId());
      }
    });
    endAsyncTestBlock();
  }

  public void testSubmitCurrentActionGameList() {
    beginAsyncTestBlock();
    final Game game = newGameWithTwoPlayers().build();
    Action action = newUnsubmittedActionWithCommand(game.getId()).build();
    withTestData(game, action, new Runnable() {
      @Override
      public void run() {
        model.setGameListListener(new AbstractGameListListener() {
          @Override
          public void onGameChanged(Game game) {
            assertEquals(1, game.getCurrentPlayerNumber());
            assertTrue(game.getLastModified() > 150L);
            finished();
          }
        });
        model.submitCurrentAction(game.getId());
      }
    });
    endAsyncTestBlock();
  }

  public void testSubmitCurrentActionLocalMultiplayer() {
    beginAsyncTestBlock();
    final Game.Builder game = newGameWithTwoPlayers();
    game.setIsLocalMultiplayer(true);
    game.clearPlayerList();
    game.addPlayer(userId);
    game.addPlayer(userId);
    Action action = newUnsubmittedActionWithCommand(game.getId()).build();
    withTestData(game.build(), action, new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.getId(), new TestGameUpdateListener() {
          @Override
          public void onGameUpdate(Game game) {
            assertEquals(userId, Games.currentPlayerId(game));
            finished();
          }
        }, false /* immediate */);
        model.submitCurrentAction(game.getId());
      }
    });
    endAsyncTestBlock();
  }

  public void testSubmitCurrentActionGameOver() {
    beginAsyncTestBlock(2);
    final Game.Builder game = newGameWithTwoPlayers();
    game.addAllSubmittedAction(list(
      action(0, 0, 2, true),
      action(1, 1, 1, true),
      action(0, 1, 2, true),
      action(1, 0, 1, true)
    ));
    Action.Builder action = Action.newBuilder();
    action.setPlayerNumber(0);
    action.setIsSubmitted(false);
    action.addCommand(newCommand(2, 2));
    action.setGameId(game.getId());
    withTestData(game.build(), action.build(), new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.getId(), new TestGameUpdateListener() {
          @Override
          public void onGameUpdate(Game game) {
            assertFalse(game.hasCurrentPlayerNumber());
            assertEquals(0, game.getVictor(0));
            assertEquals(1, game.getVictorCount());
            assertTrue(game.isGameOver());
            finished();
          }

          @Override
          public void onCurrentActionUpdate(Action currentAction) {
            assertEquals(newEmptyAction(game.getId()), currentAction);
            finished();
          }
        }, false /* immediate */);
        model.submitCurrentAction(game.getId());
      }
    });
    endAsyncTestBlock();
  }

  public void testUndo() {
    beginAsyncTestBlock();
    final Game game = newGameWithTwoPlayers().build();
    final Action action = newUnsubmittedActionWithCommand(game.getId()).build();
    withTestData(game, action, new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.getId(), new TestGameUpdateListener() {
          @Override
          public void onCurrentActionUpdate(Action currentAction) {
            assertEquals(0, currentAction.getCommandCount());
            assertEquals(action.getCommand(0), currentAction.getFutureCommand(0));
            finished();
          }
        }, false /* immediate */);
        model.undoCommand(game.getId());
      }
    });
    endAsyncTestBlock();
  }

  public void testRedo() {
    beginAsyncTestBlock();
    final Game.Builder game = newGameWithTwoPlayers();
    final Action.Builder action = newEmptyAction(game.getId()).toBuilder();
    final Command command = newCommand(0, 0);
    action.addFutureCommand(command);
    withTestData(game.build(), action.build(), new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.getId(), new TestGameUpdateListener() {
          @Override
          public void onCurrentActionUpdate(Action currentAction) {
            assertEquals(action.getFutureCommand(0), currentAction.getCommand(0));
            assertEquals(0, currentAction.getFutureCommandCount());
            finished();
          }
        }, false /* immediate */);
        model.redoCommand(game.getId());
      }
    });
    endAsyncTestBlock();
  }

  public void testResignNotPlayer() {
    beginAsyncTestBlock();
    Game test = Game.newBuilder()
        .setId("gameid")
        .addPlayer("foo")
        .setIsGameOver(false)
        .setCurrentPlayerNumber(0)
        .build();
    withTestData(test, newEmptyAction("gameid"), new Runnable() {
      @Override
      public void run() {
        assertDiesAndFinish(new Runnable() {
          @Override
          public void run() {
            model.resignGame("gameid");
          }
        });
      }
    });
    endAsyncTestBlock();
  }

  public void testResignGameOver() {
    beginAsyncTestBlock();
    Game test = Game.newBuilder()
        .setId("gameid")
        .addPlayer(userId)
        .setIsGameOver(true)
        .setCurrentPlayerNumber(0)
        .build();
    withTestData(test, newEmptyAction("gameid"), new Runnable() {
      @Override
      public void run() {
        assertDiesAndFinish(new Runnable() {
          @Override
          public void run() {
            model.resignGame("gameid");
          }
        });
      }
    });
    endAsyncTestBlock();
  }

  public void testResignLocalMultiplayer() {
    beginAsyncTestBlock(2);
    final Game.Builder game = newGameWithTwoPlayers();
    game.setIsLocalMultiplayer(true);
    game.setPlayer(1, userId);
    Action.Builder action = newUnsubmittedActionWithCommand(game.getId());
    withTestData(game.build(), action.build(), new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.getId(), new TestGameUpdateListener() {
          @Override
          public void onGameUpdate(Game game) {
            assertTrue(game.isGameOver());
            assertFalse(game.hasCurrentPlayerNumber());
            assertTrue(game.getVictorList().contains(1));
            assertTrue(game.getLastModified() > 150L);
            finished();
          }

          @Override
          public void onCurrentActionUpdate(Action currentAction) {
            assertEquals(newEmptyAction(game.getId()), currentAction);
            finished();
          }
        }, false /* immediate */);
        model.resignGame(game.getId());
      }
    });
    endAsyncTestBlock();
  }

  public void testResignGame() {
    beginAsyncTestBlock(2);
    final Game.Builder game = newGameWithTwoPlayers();
    Action.Builder action = newUnsubmittedActionWithCommand(game.getId());
    withTestData(game.build(), action.build(), new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.getId(), new TestGameUpdateListener() {
          @Override
          public void onGameUpdate(Game game) {
            assertTrue(game.isGameOver());
            assertFalse(game.hasCurrentPlayerNumber());
            assertTrue(game.getVictorList().contains(1));
            assertTrue(game.getLastModified() > 150L);
            finished();
          }

          @Override
          public void onCurrentActionUpdate(Action currentAction) {
            assertEquals(newEmptyAction(game.getId()), currentAction);
            finished();
          }
        }, false /* immediate */);
        model.resignGame(game.getId());
      }
    });
    endAsyncTestBlock();
  }

  public void testArchiveGame() {
    beginAsyncTestBlock();
    final Game.Builder game = newGameWithTwoPlayers();
    withTestData(game.build(), newEmptyAction(game.getId()), new Runnable() {
      @Override
      public void run() {
        model.setGameListListener(new AbstractGameListListener() {
          @Override
          public void onGameRemoved(String gameId) {
            assertEquals(game.getId(), gameId);
            finished();
          }
        });
        model.archiveGame(game.getId());
      }
    });
    endAsyncTestBlock();
  }
//
//  public void testSubscribeViewerToGame() {
//    beginAsyncTestBlock();
//    model.setGameUpdateListener("gameId", new TestGameUpdateListener() {
//      @Override
//      public void onCurrentActionUpdate(Action currentAction) {
//        assertEquals(newEmptyAction("gameId"), currentAction);
//        finished();
//      }
//    });
//    model.subscribeViewerToGame("gameId");
//    endAsyncTestBlock();
//  }

  public void testRequestGameStatus() {
    beginAsyncTestBlock();
    final Game game = newGameWithTwoPlayers().build();
    Action action = newEmptyAction(game.getId());
    withTestData(game, action, new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.getId(), new TestGameUpdateListener() {
          @Override
          public void onGameStatusChanged(GameStatus status) {
            assertEquals(0, status.getStatusPlayer());
            assertEquals(false, status.isComputerThinking());
            assertEquals("User's turn", status.getStatusString());
            finished();
          }
        }, false /* immediate */);
        model.requestGameStatus(game.getId());
      }
    });
    endAsyncTestBlock();
  }

//  public void testJoinGame() {
//    beginAsyncTestBlock();
//    final Game.Builder game = newGameWithOnePlayer();
//    final Profile myProfile = Profile.newBuilder()
//        .setName("myName")
//        .setIsComputerPlayer(false)
//        .build();
//    withTestData(game.build(), newEmptyAction(game.getId()), new Runnable() {
//      @Override
//      public void run() {
//        model.setGameUpdateListener(game.getId(), new TestGameUpdateListener() {
//          @Override
//          public void onGameUpdate(Game updated) {
//            assertTrue(updated.getPlayerList().contains(userId));
//            assertEquals(myProfile, updated.getProfile(1));
//            finished();
//          }
//        }, false /* immediate */);
//        model.joinGameIfPossible(game.getId(), myProfile);
//      }
//    });
//    endAsyncTestBlock();
////  }
//
//  private void checkCantJoinWithGame(final Game game) {
//    beginAsyncTestBlock();
//    withTestData(game, newEmptyAction(game.getId()), new Runnable() {
//      @Override
//      public void run() {
//        assertFalse(model.joinGameIfPossible(game.getId(), Profile.newBuilder().build()));
//        finished();
//      }
//    });
//    endAsyncTestBlock();
//  }
//
//  public void testJoinGameGameOver() {
//    checkCantJoinWithGame(newGameWithOnePlayer().setIsGameOver(true).build());
//  }
//
//  public void testJoinGameGameFull() {
//    Game game = newGameWithTwoPlayers()
//        .setPlayer(0, "player")
//        .build();
//    checkCantJoinWithGame(game);
//  }
//
//  public void testJoinGameAlreadyPlayer() {
//    checkCantJoinWithGame(newGameWithTwoPlayers().build());
//  }

  public void testPutFacebookRequestId() {
    beginAsyncTestBlock();
    final String requestId = randomInteger() + "";
    ChildEventListener listener = firebase.child("requests").addChildEventListener(
        new AbstractChildEventListener() {
      @Override
      public void onChildAdded(DataSnapshot snapshot, String previous) {
        assertEquals("r" + requestId, snapshot.getName());
        assertEquals("gameId", snapshot.getValue());
        finished();
      }
    });
    listenersToCleanUp.put(firebase.child("requests"), listener);
    model.putFacebookRequestId(requestId, "gameId");
    endAsyncTestBlock();
  }

//  public void testSubscribeToRequestId() {
//    beginAsyncTestBlock(2);
//    final String requestId = randomInteger() + "";
//    firebase.child("requests").child("r" + requestId).setValue("gameId", new CompletionListener() {
//      @Override
//      public void onComplete(FirebaseError error, Firebase ref) {
//        model.setGameUpdateListener("gameId", new TestGameUpdateListener() {
//          @Override
//          public void onCurrentActionUpdate(Action currentAction) {
//            assertEquals(newEmptyAction("gameId"), currentAction);
//            finished();
//          }
//        });
//        model.subscribeToRequestId(requestId, new RequestLoadedCallback() {
//          @Override
//          public void onRequestLoaded(String gameId) {
//            assertEquals("gameId", gameId);
//            finished();
//          }
//        });
//      }
//    });
//    endAsyncTestBlock();
//  }

  public void testUpgradeAccountToFacebook() {
    // TODO: implement
  }

  public void testEnsureIsCurrentPlayer() {
    beginAsyncTestBlock();
    final Game game = newGameWithTwoPlayers().setCurrentPlayerNumber(1).build();
    withTestData(game, newEmptyAction(game.getId()), new Runnable() {
      @Override
      public void run() {
        assertDiesAndFinish(new Runnable() {
          @Override
          public void run() {
            model.ensureIsCurrentPlayer(game.getId());
          }
        });
      }
    });
    endAsyncTestBlock();
  }

  public void testEnsureIsPlayer() {
    beginAsyncTestBlock();
    final Game game = newGameWithTwoPlayers().setPlayer(0, "foo").build();
    withTestData(game, newEmptyAction(game.getId()), new Runnable() {
      @Override
      public void run() {
        assertDiesAndFinish(new Runnable() {
          @Override
          public void run() {
            model.ensureIsPlayer(game.getId());
          }
        });
      }
    });
    endAsyncTestBlock();
  }

  private Action action(int player, int column, int row, boolean submitted) {
    return Action.newBuilder()
        .setPlayerNumber(player)
        .setIsSubmitted(submitted)
        .addCommand(Command.newBuilder()
            .setColumn(column)
            .setRow(row))
        .build();
  }

  private Action newEmptyAction(String gameId) {
    return Action.newBuilder()
        .setGameId(gameId)
        .setIsSubmitted(false)
        .build();
  }

  private Action.Builder newUnsubmittedActionWithCommand(String gameId) {
    return Action.newBuilder()
        .setPlayerNumber(0)
        .setIsSubmitted(false)
        .setGameId(gameId)
        .addCommand(Command.newBuilder().setColumn(0).setRow(0));
  }

private Game.Builder newGameWithOnePlayer() {
  return Game.newBuilder()
      .setId("{gameId" + randomInteger() + "}")
      .setCurrentPlayerNumber(0)
      .setIsGameOver(false)
      .setIsLocalMultiplayer(false)
      .addPlayer("opponentId")
      .addProfile(Profile.newBuilder()
          .setName("Opponent")
          .setPronoun(Pronoun.NEUTRAL))
      .setLastModified(123);
}

  private Game.Builder newGameWithTwoPlayers() {
    return Game.newBuilder()
        .setId("{gameId" + randomInteger() + "}")
        .setCurrentPlayerNumber(0)
        .addAllPlayer(list(userId, "opponentId"))
        .setIsGameOver(false)
        .setIsLocalMultiplayer(false)
        .addProfile(Profile.newBuilder()
            .setName("User")
            .setPronoun(Pronoun.MALE))
        .addProfile(Profile.newBuilder()
            .setName("Opponent")
            .setPronoun(Pronoun.FEMALE))
        .setLastModified(123);
  }

  private void assertDiesAndFinish(Runnable testFn) {
    try {
      testFn.run();
    } catch (RuntimeException expected) {
      finished();
    }
  }

  private void withTestData(final Game game, final Action currentAction, final Runnable testFn) {
    model.setGameListListener(new AbstractGameListListener() {
      @Override
      public void onGameAdded(Game game) {
        model.setGameListListener(null);
        testFn.run();
      }
    });
    firebase.child("games").child(game.getId()).setValue(game.serialize());
    Firebase userGame = firebase.child("users").child(userKey).child("games").child(game.getId());
    userGame.child("currentAction").setValue(currentAction.serialize());
  }

  private Command newCommand(int column, int row) {
    return Command
        .newBuilder()
        .setColumn(column)
        .setRow(row)
        .build();
  }

  final static <T> List<T> list(T... objects) {
    List<T> result = new ArrayList<T>();
    for (T t : objects) {
      result.add(t);
    }
    return result;
  }

  static Map<String, Object> map(Object... objects) {
    Map<String, Object> result = new HashMap<String, Object>();
    for (int i = 0; i < objects.length; i += 2) {
      result.put(objects[i].toString(), objects[i + 1]);
    }
    return result;
  }
}
