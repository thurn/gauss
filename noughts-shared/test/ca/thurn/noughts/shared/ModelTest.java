package ca.thurn.noughts.shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import ca.thurn.noughts.shared.entities.Action;
import ca.thurn.noughts.shared.entities.Command;
import ca.thurn.noughts.shared.entities.Game;
import ca.thurn.noughts.shared.entities.GameStatus;
import ca.thurn.noughts.shared.entities.Profile;
import ca.thurn.noughts.shared.entities.Pronoun;
import ca.thurn.testing.SharedTestCase;

import com.firebase.client.Firebase;
import com.firebase.client.Firebase.CompletionListener;
import com.firebase.client.FirebaseError;

public class ModelTest extends SharedTestCase {
  private Model model;
  private String userId;
  private String userKey;
  private Firebase firebase;

  private static abstract class OnGameUpdateListener implements GameUpdateListener {
    @Override
    public void onGameStatusChanged(GameStatus status) {
    }
    
    @Override
    public void onProfileRequired(Game game) {
    }
  }
  
  private static abstract class GameStatusUpdateListener implements GameUpdateListener {
    @Override
    public void onGameUpdate(Game game) {
    }
    
    @Override
    public void onProfileRequired(Game game) {
    }    
  }
  
  @Override
  public void sharedSetUp(final Runnable done) {
    firebase = new Firebase("https://noughts-test.firebaseio-demo.com");
    firebase.removeValue(new CompletionListener() {
      @Override public void onComplete(FirebaseError error, Firebase firebase) {
        userId = "id" + randomInteger();
        userKey = "K" + userId;
        model = new Model(userId, userKey, firebase);
        done.run();        
      }
    });

  }
  
  @Override
  public void sharedTearDown() {
    model.removeAllFirebaseListeners();
  }
  
  public void testNewGame() {
    beginAsyncTestBlock();
    model.setGameListListener(new AbstractGameListListener() {
      @Override
      public void onGameAdded(Game game) {
        assertTrue(game.getPlayerList().contains(userId));
        assertEquals(Model.X_PLAYER, (int)game.getCurrentPlayerNumber());
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
    model.setGameUpdateListener(id, new OnGameUpdateListener() {
      @Override
      public void onGameUpdate(Game game) {
        assertTrue(game.getPlayerList().contains(userId));
        assertEquals(Model.X_PLAYER, (int)game.getCurrentPlayerNumber());
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
  
  public void testAddCommandToExistingAction() {
    beginAsyncTestBlock();
    final Game.Builder game = newGameWithTwoPlayers();
    Action.Builder action = Action.newBuilder();
    action.setPlayerNumber(0);
    action.setIsSubmitted(false);
    action.setGameId(game.getId());
    game.setCurrentAction(action.build());
    assertEquals(action.build(), game.getCurrentAction());
    final Command command = Command.newDeserializer()
        .deserialize(map("column", 2, "row", 2));
    withTestData(game.build(), new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.getId(), new OnGameUpdateListener() {
          @Override
          public void onGameUpdate(Game game) {
            assertNotNull(game);
            assertNotNull(game.getLastModified());
            assertTrue(game.getLastModified() > 0);
            Action action = game.getCurrentAction();
            assertEquals(0, action.getFutureCommandCount());
            assertEquals(command, action.getCommand(0)); 
            model.removeGameUpdateListener(game.getId());
            finished();
          }
        });
        model.addCommand(game.build(), command);
      }
    });
    endAsyncTestBlock();
  }
  
  public void testUpdateLastCommand() {
    beginAsyncTestBlock();
    final Game game = newGameWithCurrentCommand().build();
    final Command command = Command.newBuilder().setColumn(0).setRow(0).build();
    withTestData(game, new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.getId(), new OnGameUpdateListener() {
          @Override
          public void onGameUpdate(Game game) {
            assertEquals(list(command), game.getCurrentAction().getCommandList());
            finished();
          }
        });
        model.updateLastCommand(game, command);
      }
    });
    endAsyncTestBlock();
  }
  
  public void testCouldUpdateLastCommand() {
    Game game = newGameWithCurrentCommand().build();
    Command command = Command.newBuilder().setRow(0).setColumn(0).build();
    assertTrue(model.couldUpdateLastCommand(game, command));
    assertFalse(model.couldUpdateLastCommand(
        game.toBuilder().clearCurrentAction().build(), command));
  }
  
  public void testAddCommandNotCurrentPlayer() {
    assertDies(new Runnable() {
      @Override public void run() {
        model.addCommand(Game.newDeserializer().deserialize(map(
            "playerList", list("foo", userId),
            "currentPlayerNumber", 0
            )), newCommand(0, 0));
      }
    });
  }
  
  public void testAddCommandToNewAction() {
    beginAsyncTestBlock();
    final Game.Builder game = newGameWithTwoPlayers();
    final Command command = newCommand(1, 1);
    withTestData(game.build(), new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.getId(), new OnGameUpdateListener() {
          @Override
          public void onGameUpdate(Game game) {
            assertTrue(game.getLastModified() > 0);
            Action action = game.getCurrentAction();
            assertEquals(0, action.getPlayerNumber());
            assertFalse(action.isSubmitted());
            assertEquals(game.getId(), action.getGameId());
            assertEquals(1, action.getCommandCount());
            assertEquals(command, action.getCommand(0));   
            finished();
          }
        });
        model.addCommand(game.build(), command);
      }
    });
    endAsyncTestBlock();
  }
  
  public void testAddCommandAndSubmit() {
    beginAsyncTestBlock();
    final Game.Builder game = newGameWithTwoPlayers();
    final Command command = newCommand(1, 1);
    withTestData(game.build(), new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.getId(), new OnGameUpdateListener() {
          final AtomicInteger numUpdates = new AtomicInteger(0); 
          @Override
          public void onGameUpdate(Game game) {
            // Allow for two updates because addAndSubmit fires twice
            if (numUpdates.getAndIncrement() == 1) {
              assertFalse(game.hasCurrentAction());
              assertEquals(1, game.getCurrentPlayerNumber());
              finished();
            }
          }
        });
        model.addCommandAndSubmit(game.build(), command);
      }
    });
    endAsyncTestBlock();
  }
  
  public void testGameUpdateListener() {
    beginAsyncTestBlock();
    final Game game = newGameWithTwoPlayers().build();
    withTestData(game, new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.getId(), new OnGameUpdateListener() {
          @Override
          public void onGameUpdate(Game game) {
            assertEquals(123L, (long)game.getLastModified());
            finished();
          }
        });
        firebase.child("games").child(game.getId()).child("lastModified").setValue(123L);
      }
    });
    endAsyncTestBlock();
  }
  
  public void testAttachCommandListener() {
    // TODO: this
  }
  
  public void testCommandListenerCommandAdded() {
    // TODO: this
  }
  
  public void testCouldSubmitCommand() {
    Command command = newCommand(1, 1);
    assertFalse(model.couldSubmitCommand(newGame(map("gameOver", true)).build(), command));
    Game.Builder game = newGameWithCurrentCommand();
    assertFalse(model.couldSubmitCommand(game.build(), command));
    game.clearCurrentAction();
    game.clearSubmittedActionList();
    game.addSubmittedAction(Action.newBuilder()
        .setIsSubmitted(true)
        .addCommand(newCommand(2, 1)));
    assertTrue(model.couldSubmitCommand(game.build(), command));
    game.clearSubmittedActionList();
    game.addSubmittedAction(Action.newBuilder().setIsSubmitted(true).addCommand(command));
    assertFalse(model.couldSubmitCommand(game.build(), command));
  }
  
  public void testCanUndo() {
    Game.Builder game = newGameWithCurrentCommand();
    assertTrue(model.canUndo(game.build()));
    Action.Builder action = game.getCurrentAction().toBuilder();
    action.clearCommandList();
    game.setCurrentAction(action);
    assertFalse(model.canUndo(game.build()));
  }
  
  public void testCanRedo() {
    Game.Builder game = newGameWithCurrentCommand();
    assertFalse(model.canRedo(game.build()));
    Action.Builder action = game.getCurrentAction().toBuilder();
    Command command = action.getCommandList().remove(0);
    action.addFutureCommand(command);
    game.setCurrentAction(action);
    assertTrue(model.canRedo(game.build()));
  }
  
  public void testCanSubmit() {
    Game.Builder game = newGameWithCurrentCommand();
    assertTrue(model.canSubmit(game.build()));
    Action.Builder action = game.getCurrentAction().toBuilder();
    action.clearCommandList();
    game.setCurrentAction(action);
    assertFalse(model.canSubmit(game.build()));
    Action.Builder action2 = game.getCurrentAction().toBuilder();
    action2.addCommand(newCommand(0, 5));
    game.setCurrentAction(action2);    
    assertFalse(model.canSubmit(game.build()));
  }
  
  public void testComputeVictors() {
    Game.Builder game = newGame();
    game.addAllSubmittedAction(list(
      action(0, 0, 0, true),
      action(1, 0, 1, true)
    ));
    game.setCurrentAction(action(0, 0, 2, false));
    assertNull(model.computeVictorsIfCurrentActionSubmitted(game.build()));
    
    game = newGame();
    game.addAllSubmittedAction(list(
      action(0, 0, 0, true),
      action(0, 0, 1, true)
    ));
    game.setCurrentAction(action(0, 0, 2, false));
    assertDeepEquals(list(0), model.computeVictorsIfCurrentActionSubmitted(game.build()));
    
    game = newGame();
    game.addAllSubmittedAction(list(
      action(0, 0, 0, true),
      action(0, 1, 1, true)
    ));
    game.setCurrentAction(action(0, 2, 2, false));
    assertDeepEquals(list(0), model.computeVictorsIfCurrentActionSubmitted(game.build()));
    
    game = newGame();
    game.addAllSubmittedAction(list(
      action(0, 0, 2, true),
      action(1, 1, 1, true),
      action(0, 1, 2, true),
      action(1, 0, 1, true)
    ));
    game.setCurrentAction(action(0, 2, 2, false));
    assertDeepEquals(list(0), model.computeVictorsIfCurrentActionSubmitted(game.build()));
    
    game = newGame();
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
    game.setCurrentAction(action(0, 2, 2, false));
    assertDeepEquals(list(0, 1), model.computeVictorsIfCurrentActionSubmitted(game.build()));
  }
  
  public void testSubmitCurrentAction() {
    beginAsyncTestBlock();
    final Game game = newGameWithCurrentCommand().build();
    withTestData(game, new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.getId(), new OnGameUpdateListener() {
          @Override
          public void onGameUpdate(Game game) {
            assertEquals(1, (int)game.getCurrentPlayerNumber());
            assertFalse(game.hasCurrentAction()); 
            finished();
          }
        });
        model.submitCurrentAction(game);
      }
    });
    endAsyncTestBlock();
  }
  
  public void testSubmitCurrentActionUpdatesGameStatus() {
    beginAsyncTestBlock();
    final Game game = newGameWithCurrentCommand().build();
    withTestData(game, new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.getId(), new GameStatusUpdateListener() {
          @Override
          public void onGameUpdate(Game game) {}

          @Override
          public void onGameStatusChanged(GameStatus status) {
            assertEquals(status.getStatusPlayer(), 1);
            finished();
          }
        });
        model.submitCurrentAction(game);
      }
    });
    endAsyncTestBlock();
  }
  
  public void testSubmitCurrentActionGameList() {
    beginAsyncTestBlock();
    final Game game = newGameWithCurrentCommand().build();
    withTestData(game, new Runnable() {
      @Override
      public void run() {
        model.setGameListListener(new AbstractGameListListener() {
          @Override
          public void onGameChanged(Game game) {
            assertEquals(1, (int)game.getCurrentPlayerNumber());
            assertTrue(game.getLastModified() > 150L);
            assertFalse(game.hasCurrentAction());
            finished();            
          }
        });
        model.submitCurrentAction(game);
      }
    });
    endAsyncTestBlock();
  }
  
  public void testSubmitCurrentActionLocalMultiplayer() {
    beginAsyncTestBlock();
    final Game.Builder game = newGameWithCurrentCommand();
    game.setIsLocalMultiplayer(true);
    game.clearPlayerList();
    game.addPlayer(userId);
    game.addPlayer(userId);
    withTestData(game.build(), new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.getId(), new OnGameUpdateListener() {
          @Override
          public void onGameUpdate(Game game) {
            assertEquals(userId, Games.currentPlayerId(game));
            finished();
          }
        });
        model.submitCurrentAction(game.build());
      }
    });
    endAsyncTestBlock();
  }
  
  public void testSubmitCurrentActionGameOver() {
    beginAsyncTestBlock();
    final Game.Builder game = newGame();
    game.addPlayer(userId);
    game.addPlayer("o");
    game.setProfile(0, Profile.newDeserializer().deserialize(map(
        "name", "User",
        "pronoun", "MALE"
        )));
    game.setProfile(1, Profile.newDeserializer().deserialize(map(
        "name", "Opponent",
        "pronoun", "FEMALE"
        )));
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
    game.setCurrentAction(action);
    game.setCurrentPlayerNumber(0);
    withTestData(game.build(), new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.getId(), new OnGameUpdateListener() {
          @Override
          public void onGameUpdate(Game game) {
            assertFalse(game.hasCurrentPlayerNumber());
            assertFalse(game.hasCurrentAction());
            assertEquals(0, game.getVictor(0));
            assertEquals(1, game.getVictorCount());
            assertTrue(game.isGameOver());   
            finished();
          }
        });
        model.submitCurrentAction(game.build());
      }
    });
    endAsyncTestBlock();
  }
  
  public void testUndo() {
    beginAsyncTestBlock();
    final Game.Builder game = newGameWithCurrentCommand();
    withTestData(game.build(), new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.getId(), new OnGameUpdateListener() {
          @Override
          public void onGameUpdate(Game game) {
            assertDeepEquals(list(), game.getCurrentAction().getCommandList());
            assertDeepEquals(list(newCommand(2, 1)),
                game.getCurrentAction().getFutureCommandList());    
            finished();
          }
        });
        model.undoCommand(game.build());
      }
    });
    endAsyncTestBlock();
  }
  
  public void testRedo() {
    beginAsyncTestBlock();
    final Game.Builder game = newGameWithCurrentCommand();
    Action.Builder action = game.getCurrentAction().toBuilder();
    action.clearCommandList();
    final Command command = newCommand(0, 0);
    action.addFutureCommand(command);
    game.setCurrentAction(action);
    withTestData(game.build(), new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.getId(), new OnGameUpdateListener() {
          @Override
          public void onGameUpdate(Game game) {
            assertDeepEquals(list(command), game.getCurrentAction().getCommandList());
            assertDeepEquals(list(), game.getCurrentAction().getFutureCommandList());     
            finished();
          }
        });
        model.redoCommand(game.build());
      }
    });
    endAsyncTestBlock();
  }
  
  public void testResignNotPlayer() {
    assertDies(new Runnable() {
      @Override
      public void run() {
        model.resignGame(newGame(map(
            "playerList", list("foo"),
            "currentPlayerNumber", 0
        )).build());
      }
    });
  }
  
  public void testResignGameOver() {
    assertDies(new Runnable() {
      @Override
      public void run() {
        model.resignGame(newGame(map(
            "playerList", list(userId, "foo"),
            "currentPlayerNumber", 0,
            "gameOver", true
        )).build());
      }
    });    
  }
  
  public void testResignLocalMultiplayer() {
    beginAsyncTestBlock();
    final Game.Builder game = newLocalMultiplayerGameWithTwoPlayers();
    game.setCurrentPlayerNumber(1);
    withTestData(game.build(), new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.getId(), new OnGameUpdateListener() {
          @Override
          public void onGameUpdate(Game game) {
            assertDeepEquals(list(new Integer(1)), game.getResignedPlayerList());
            assertTrue(game.isGameOver());
            assertFalse(game.hasCurrentAction());
            assertFalse(game.hasCurrentPlayerNumber());
            assertTrue(game.getVictorList().contains(0));
            assertTrue(game.getLastModified() > 150L); 
            finished();
          }
        });
        model.resignGame(game.build());
      }
    });
    endAsyncTestBlock();      
  }
  
  public void testResignGame() {
    beginAsyncTestBlock();
    final Game.Builder game = newGameWithTwoPlayers();
    withTestData(game.build(), new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.getId(), new OnGameUpdateListener() {
          @Override
          public void onGameUpdate(Game game) {
            assertDeepEquals(list(new Integer(0)), game.getResignedPlayerList());
            assertTrue(game.isGameOver());
            assertFalse(game.hasCurrentAction());
            assertFalse(game.hasCurrentPlayerNumber());
            assertTrue(game.getVictorList().contains(1));
            assertTrue(game.getLastModified() > 150L);
            finished();
          }
        });
        model.resignGame(game.build());
      }
    });
    endAsyncTestBlock();    
  }
  
  public void testArchiveNotPlayer() {
    assertDies(new Runnable() {
      @Override
      public void run() {
        model.archiveGame(newGame(map(
            "playerList", list("foo"),
            "currentPlayerNumber", 0
        )).build());
      }
    });
  }
  
  public void testArchiveGameNotOver() {
    assertDies(new Runnable() {
      @Override
      public void run() {
        model.archiveGame(newGame(map(
            "playerList", list(userId, "foo"),
            "currentPlayerNumber", 0,
            "gameOver", false
        )).build());
      }
    });    
  }
  
  public void testArchiveListGame() {
    beginAsyncTestBlock();
    final Game.Builder game = newGame(map(
        "currentPlayerNumber", 0,
        "playerList", list(userId, "foobar"),
        "gameOver", true
        ));
    withTestData(game.build(), new Runnable() {
      @Override
      public void run() {
        model.setGameListListener(new AbstractGameListListener() {
          @Override
          public void onGameRemoved(Game newGame) {
            assertEquals(game.build(), newGame);
            finished();
          }
        });
        model.archiveGame(game.build());
      }
    });
    endAsyncTestBlock();    
  }
  
  public void testIsCurrentPlayer() {
    Map<String, Object> map = map("gameOver", true);
    Game g1 = newGame(map).build();
    assertFalse(model.isCurrentPlayer(g1));

    Game g2 = newGame(map("currentPlayerNumber", 0, "playerList", list("fooId"))).build();
    assertFalse(model.isCurrentPlayer(g2));

    Game g3 = newGame(map(
      "currentPlayerNumber", 1,
      "playerList", list("fooId", userId))).build();
    assertTrue(model.isCurrentPlayer(g3));

    Game g4 = newGame(map(
      "currentPlayerNumber", 0,
      "playerList", list("fooId", userId))).build();
    assertFalse(model.isCurrentPlayer(g4));
  }

  public void testEnsureIsCurrentPlayer() {
    assertDies(new Runnable() {
      @Override
      public void run() {
        model.ensureIsCurrentPlayer(newGame(map(
          "playerList", list("foo", userId),
          "currentPlayerNumber", 0
        )).build());
      }
    });
  }

  public void testEnsureIsPlayer() {
    assertDies(new Runnable() {
      @Override
      public void run() {
        model.ensureIsPlayer(newGame(map(
          "playerList", list("foo"),
          "currentPlayerNumber", 0
        )).build());
      }
    });
  }
  
  @SuppressWarnings("unchecked")
  private Action action(int player, int column, int row, boolean submitted) {
    return Action.newDeserializer().deserialize(map(
        "playerNumber", player,
        "submitted", submitted,
        "commandList", list(map(
          "column", column,
          "row", row
        ))));
  }
  
  private Game.Builder newGame() {
    return newGame(map());
  }
  
  private Game.Builder newGame(Map<String, Object> map) {
    String gameId = firebase.child("games").push().getName();
    map.put("id", gameId);
    return Game.newDeserializer().deserialize(map).toBuilder();
  }
  
  private Game.Builder newGameWithTwoPlayers() {
    return newGame(map(
      "currentPlayerNumber", 0,
      "playerList", list(userId, "opponentId"),
      "gameOver", false,
      "localMultiplayer", false,
      "minimal", false,
      "profileMap", map(
          userId, map(
            "name", "User",
            "pronoun", "MALE"
          ),
          "opponentId", map(
            "name", "Opponent",
            "pronoun", "FEMALE"
          )
        ),
      "lastModified", 123L
    ));
  }
  
  @SuppressWarnings("unchecked")
  private Game.Builder newLocalMultiplayerGameWithTwoPlayers() {
    return newGame(map(
      "currentPlayerNumber", 0,
      "playerList", list(userId, userId),
      "gameOver", false,
      "localMultiplayer", true,
      "minimal", false,
      "localProfileList", list(
        map(
          "name", "User",
          "pronoun", "MALE"
        ),
        map(
            "name", "User 2",
            "pronoun", "MALE"
        )        
      ),
      "lastModified", 123L
    ));
  }  
  
  private Game.Builder newGameWithCurrentCommand() {
    Game.Builder game = newGameWithTwoPlayers();
    game.setCurrentAction(Action.newBuilder()
        .setPlayerNumber(0)
        .setIsSubmitted(false)
        .addCommand(Command.newBuilder()
            .setColumn(2)
            .setRow(1)));
    return game;
  }
  
  private void assertDies(Runnable testFn) {
    try {
      testFn.run();
    } catch (RuntimeException expected) {}
  }
  
  private void withTestData(final Game game, final Runnable testFn) {
    model.setGameListListener(new AbstractGameListListener() {
      @Override
      public void onGameAdded(Game newGame) {
        assertEquals(game, newGame);
        testFn.run();
      }
    });
    firebase.child("games").child(game.getId()).setValue(game.serialize());
    firebase.child("users").child(userId).child("games").child(game.getId())
        .setValue(game.serialize());
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
