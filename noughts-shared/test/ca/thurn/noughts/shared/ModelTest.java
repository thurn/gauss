package ca.thurn.noughts.shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.thurn.noughts.shared.Game.GameStatus;
import ca.thurn.noughts.shared.Model.GameUpdateListener;
import ca.thurn.noughts.shared.Profile.Pronoun;
import ca.thurn.testing.SharedTestCase;

import com.firebase.client.Firebase;
import com.firebase.client.Firebase.CompletionListener;
import com.firebase.client.FirebaseError;

public class ModelTest extends SharedTestCase {
  private Model model;
  private String userId;
  private Firebase firebase;

  private static abstract class NoStatusGameUpdateListener implements GameUpdateListener {
    @Override
    public void onGameStatusChanged(GameStatus status) {
    }
  }
  
  @Override
  public void sharedSetUp(final Runnable done) {
    firebase = new Firebase("https://noughts-test.firebaseio-demo.com");
    firebase.removeValue(new CompletionListener() {
      @Override public void onComplete(FirebaseError error, Firebase firebase) {
        userId = "id" + randomInteger();
        model = new Model(userId, firebase);
        done.run();        
      }
    });

  }
  
  @Override
  public void sharedTearDown() {
    model.removeGameListListener();
  }
  
  public void testNewGame() {
    beginAsyncTestBlock();
    model.setGameListListener(new AbstractGameListListener() {
      @Override
      public void onGameAdded(Game game) {
        assertTrue(game.getPlayersMutable().contains(userId));
        assertEquals(Model.X_PLAYER, (int)game.getCurrentPlayerNumber());
        assertTrue(game.getLastModified() > 0);
        assertTrue(game.isLocalMultiplayer());
        assertFalse(game.isGameOver());
        assertTrue(game.isMinimal());
        assertEquals(0, game.getActionsMutable().size());
        assertEquals("John", game.getLocalProfiles().get(0).getName());
        finished();
      }
    });
    Map<Integer, Profile> localProfiles = new HashMap<Integer, Profile>();
    Profile profile = new Profile("John");
    localProfiles.put(0, profile);
    String id = model.newLocalMultiplayerGame(localProfiles);
    assertFalse(id.equals(""));
    endAsyncTestBlock();
  }
  
  public void testNewGameGameUpdate() {
    beginAsyncTestBlock();
    Map<String, Profile> profiles = new HashMap<String, Profile>();
    Profile profile = new Profile("John");
    profile.setPronoun(Pronoun.NEUTRAL);
    profiles.put(userId, profile);
    String id = model.newGame(profiles);
    model.setGameUpdateListener(id, new NoStatusGameUpdateListener() {
      @Override
      public void onGameUpdate(Game game) {
        assertTrue(game.getPlayersMutable().contains(userId));
        assertEquals(Model.X_PLAYER, (int)game.getCurrentPlayerNumber());
        assertTrue(game.getLastModified() > 0);
        assertFalse(game.isLocalMultiplayer());
        assertFalse(game.isGameOver());
        assertEquals(0, game.getActionsMutable().size());
        assertEquals("John", game.getProfiles().get(userId).getName());
        finished();
      }
    });
    endAsyncTestBlock();
  }
  
  public void testAddCommandToExistingAction() {
    beginAsyncTestBlock();
    final Game game = newGame();
    game.getPlayersMutable().add(userId);
    game.setCurrentPlayerNumber(0);
    Action action = new Action(0);
    action.setGameId(game.getId());
    game.getActionsMutable().add(action);
    game.setCurrentActionNumber(0);
    assertEquals(action, game.currentAction());
    final Command command = new Command(map("column", 2, "row", 2));
    withTestData(game, new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.getId(), new NoStatusGameUpdateListener() {
          @Override
          public void onGameUpdate(Game game) {
            assertNotNull(game);
            assertNotNull(game.getLastModified());
            assertTrue(game.getLastModified() > 0);
            Action action = game.currentAction();
            assertEquals(0, action.getFutureCommandsMutable().size());
            assertEquals(command, action.getCommandsMutable().get(0));
            model.removeGameUpdateListener(game.getId());
            finished();
          }
        });
        model.addCommand(game, command);
      }
    });
    endAsyncTestBlock();
  }
  
  public void testAddCommandNotCurrentPlayer() {
    assertDies(new Runnable() {
      @Override public void run() {
        model.addCommand(new Game(map(
            "players", list("foo", userId),
            "currentPlayerNumber", 0
            )), new Command(0, 0));
      }
    });
  }
  
  public void testAddCommandToNewAction() {
    beginAsyncTestBlock();
    final Game game = newGame(map("players", list(userId), "currentPlayerNumber", 0));
    final Command command = new Command(1, 1);
    withTestData(game, new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.getId(), new NoStatusGameUpdateListener() {
          @Override
          public void onGameUpdate(Game game) {
            assertEquals(0, (int)game.getCurrentActionNumber());
            assertTrue(game.getLastModified() > 0);
            Action action = game.currentAction();
            assertEquals(0, action.getPlayerNumber());
            assertFalse(action.isSubmitted());
            assertEquals(game.getId(), action.getGameId());
            assertEquals(1, action.getCommandsMutable().size());
            assertEquals(command, action.getCommandsMutable().get(0));
            finished();
          }
        });
        model.addCommand(game, command);
      }
    });
    endAsyncTestBlock();
  }
  
  public void testAddCommandUpdatesLastModified() {
    beginAsyncTestBlock();
    final Game game = newGame(map(
        "players", list(userId),
        "currentPlayerNumber", 0,
        "lastModified", 123L));
    final Command command = new Command(1, 1);
    withTestData(game, new Runnable() {
      @Override
      public void run() {
        model.setGameListListener(new AbstractGameListListener() {
          @Override
          public void onGameChanged(Game game) {
            assertTrue(game.isMinimal());
            assertTrue(game.getLastModified() > 150L);
            finished();
          }          
        });
        model.addCommand(game, command);
      }
    });
    endAsyncTestBlock();
  }
  
  public void testGameUpdateListener() {
    beginAsyncTestBlock();
    final Game game = newGame();
    withTestData(game, new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.getId(), new NoStatusGameUpdateListener() {
          @Override
          public void onGameUpdate(Game game) {
            assertEquals(123L, (long)game.getLastModified());
            finished();
          }
        });
        firebase.child("games").child(game.getId()).updateChildren(map("lastModified", 123L));
      }
    });
    endAsyncTestBlock();
  }
  
  public void testCouldSubmitCommand() {
    Command command = new Command(1, 1);
    assertFalse(model.couldSubmitCommand(newGame(map("gameOver", true)), command));
    Game game = newGameWithCurrentCommand();
    assertFalse(model.couldSubmitCommand(game, command));
    game.setCurrentActionNumber(null);
    game.getActionsMutable().get(0).setSubmitted(true);
    assertTrue(model.couldSubmitCommand(game, command));
    assertFalse(model.couldSubmitCommand(game, new Command(3, 1)));
    game.getActionsMutable().get(0).getCommandsMutable().remove(0);
    game.getActionsMutable().get(0).getCommandsMutable().add(command);
    assertFalse(model.couldSubmitCommand(game, command));
  }
  
  public void testCanUndo() {
    Game game = newGameWithCurrentCommand();
    assertTrue(model.canUndo(game));
    game.getActionsMutable().get(0).getCommandsMutable().clear();
    assertFalse(model.canUndo(game));
  }
  
  public void testCanRedo() {
    Game game = newGameWithCurrentCommand();
    assertFalse(model.canRedo(game));
    Command command = game.getActionsMutable().get(0).getCommandsMutable().remove(0);
    game.getActionsMutable().get(0).getFutureCommandsMutable().add(command);
    assertTrue(model.canRedo(game));
  }
  
  public void testCanSubmit() {
    Game game = newGameWithCurrentCommand();
    assertTrue(model.canSubmit(game));
    game.getActionsMutable().get(0).getCommandsMutable().clear();
    assertFalse(model.canSubmit(game));
    game.getActionsMutable().get(0).getCommandsMutable().add(new Command(0, 5));
    assertFalse(model.canSubmit(game));
  }
  
  public void testComputeVictors() {
    Game game = newGame();
    game.getActionsMutable().addAll(list(
      action(0, 0, 0),
      action(1, 0, 1),
      action(0, 0, 2)      
    ));
    assertNull(model.computeVictors(game));
    
    game = newGame();
    game.getActionsMutable().addAll(list(
      action(0, 0, 0),
      action(0, 0, 1),
      action(0, 0, 2)
    ));
    assertDeepEquals(list(0), model.computeVictors(game));
    
    game = newGame();
    game.getActionsMutable().addAll(list(
      action(0, 0, 0),
      action(0, 1, 1),
      action(0, 2, 2)
    ));
    assertDeepEquals(list(0), model.computeVictors(game));
    
    game = newGame();
    game.getActionsMutable().addAll(list(
      action(0, 0, 2),
      action(1, 1, 1),
      action(0, 1, 2),
      action(1, 0, 1),
      action(0, 2, 2)
    ));
    assertDeepEquals(list(0), model.computeVictors(game));
    
    game = newGame();
    game.getPlayersMutable().add("x");
    game.getPlayersMutable().add("o");
    game.getActionsMutable().addAll(list(
      action(1, 0, 0),
      action(0, 0, 1),
      action(1, 0, 2),
      action(1, 1, 0),
      action(0, 1, 1),
      action(1, 1, 2),
      action(0, 2, 0),
      action(1, 2, 1),
      action(0, 2, 2)
    ));
    assertDeepEquals(list(0, 1), model.computeVictors(game));
  }
  
  public void testSubmitCurrentAction() {
    beginAsyncTestBlock();
    final Game game = newGameWithCurrentCommand();
    withTestData(game, new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.getId(), new NoStatusGameUpdateListener() {
          @Override
          public void onGameUpdate(Game game) {
            assertEquals(1, (int)game.getCurrentPlayerNumber());
            assertNull(game.getCurrentActionNumber());
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
    final Game game = newGameWithCurrentCommand();
    withTestData(game, new Runnable() {
      @Override
      public void run() {
        model.setGameListListener(new AbstractGameListListener() {
          @Override
          public void onGameChanged(Game game) {
            assertEquals(1, (int)game.getCurrentPlayerNumber());
            assertTrue(game.getLastModified() > 150L);
            assertNull(game.getCurrentActionNumber());
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
    final Game game = newGameWithCurrentCommand();
    game.setLocalMultiplayer(true);
    game.getPlayersMutable().clear();
    game.getPlayersMutable().add(userId);
    game.getPlayersMutable().add(userId);
    withTestData(game, new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.getId(), new NoStatusGameUpdateListener() {
          @Override
          public void onGameUpdate(Game game) {
            assertEquals(userId, game.currentPlayerId());
            finished();
          }
        });
        model.submitCurrentAction(game);
      }
    });
    endAsyncTestBlock();
  }
  
  public void testSubmitCurrentActionGameOver() {
    beginAsyncTestBlock();
    final Game game = newGame();
    game.getPlayersMutable().add(userId);
    game.getPlayersMutable().add("o");
    game.getProfilesMutable().put(userId, new Profile(map(
        "name", "User",
        "pronoun", "MALE"
        )));
    game.getProfilesMutable().put("o", new Profile(map(
        "name", "Opponent",
        "pronoun", "FEMALE"
        )));    
    game.getActionsMutable().addAll(list(
      action(0, 0, 2),
      action(1, 1, 1),
      action(0, 1, 2),
      action(1, 0, 1)
    ));
    Action action = new Action(0);
    action.getCommandsMutable().add(new Command(2, 2));
    action.setGameId(game.getId());
    game.getActionsMutable().add(action);
    game.setCurrentActionNumber(4);
    game.setCurrentPlayerNumber(0);
    withTestData(game, new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.getId(), new NoStatusGameUpdateListener() {
          @Override
          public void onGameUpdate(Game game) {
            assertNull(game.getCurrentPlayerNumber());
            assertNull(game.getCurrentActionNumber());
            assertEquals(new Integer(0), game.getVictors().get(0));
            assertEquals(1, game.getVictors().size());
            assertTrue(game.isGameOver());
            finished();
          }
        });
        model.submitCurrentAction(game);
      }
    });
    endAsyncTestBlock();
  }
  
  public void testUndo() {
    beginAsyncTestBlock();
    final Game game = newGameWithCurrentCommand();
    withTestData(game, new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.getId(), new NoStatusGameUpdateListener() {
          @Override
          public void onGameUpdate(Game game) {
            assertDeepEquals(list(), game.currentAction().getCommandsMutable());
            assertDeepEquals(list(new Command(2, 1)),
                game.currentAction().getFutureCommandsMutable());
            finished();
          }
        });
        model.undoCommand(game);
      }
    });
    endAsyncTestBlock();
  }
  
  public void testRedo() {
    beginAsyncTestBlock();
    final Game game = newGameWithCurrentCommand();
    game.currentAction().getCommandsMutable().clear();
    final Command command = new Command(0, 0);
    game.currentAction().getFutureCommandsMutable().add(command);
    withTestData(game, new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.getId(), new NoStatusGameUpdateListener() {
          @Override
          public void onGameUpdate(Game game) {
            assertDeepEquals(list(command), game.currentAction().getCommandsMutable());
            assertDeepEquals(list(), game.currentAction().getFutureCommandsMutable());
            finished();
          }
        });
        model.redoCommand(game);
      }
    });
    endAsyncTestBlock();
  }
  
  public void testResignNotPlayer() {
    assertDies(new Runnable() {
      @Override
      public void run() {
        model.resignGame(newGame(map(
            "players", list("foo"),
            "currentPlayerNumber", 0
        )));
      }
    });
  }
  
  public void testResignGameOver() {
    assertDies(new Runnable() {
      @Override
      public void run() {
        model.resignGame(newGame(map(
            "players", list(userId, "foo"),
            "currentPlayerNumber", 0,
            "gameOver", true
        )));
      }
    });    
  }
  
  public void testResignGame() {
    beginAsyncTestBlock();
    final Game game = newGame(map(
        "currentPlayerNumber", 0,
        "players", list(userId, "foobar"),
        "currentActionNumber", 0,
        "lastModified", 123L
        ));
    withTestData(game, new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.getId(), new NoStatusGameUpdateListener() {
          @Override
          public void onGameUpdate(Game game) {
            assertTrue(game.getResignedPlayers().contains(userId));
            assertTrue(game.isGameOver());
            assertNull(game.getCurrentActionNumber());
            assertNull(game.getCurrentPlayerNumber());
            assertTrue(game.getVictors().contains(1));
            assertTrue(game.getLastModified() > 150L);
            finished();
          }
        });
        model.resignGame(game);
      }
    });
    endAsyncTestBlock();    
  }
  
  public void testArchiveNotPlayer() {
    assertDies(new Runnable() {
      @Override
      public void run() {
        model.archiveGame(newGame(map(
            "players", list("foo"),
            "currentPlayerNumber", 0
        )));
      }
    });
  }
  
  public void testArchiveGameNotOver() {
    assertDies(new Runnable() {
      @Override
      public void run() {
        model.archiveGame(newGame(map(
            "players", list(userId, "foo"),
            "currentPlayerNumber", 0,
            "gameOver", false
        )));
      }
    });    
  }
  
  public void testArchiveListGame() {
    beginAsyncTestBlock();
    final Game game = newGame(map(
        "currentPlayerNumber", 0,
        "players", list(userId, "foobar"),
        "gameOver", true
        ));
    withTestData(game, new Runnable() {
      @Override
      public void run() {
        model.setGameListListener(new AbstractGameListListener() {
          @Override
          public void onGameRemoved(Game newGame) {
            assertEquals(game.minimalGame(), newGame);
            finished();
          }
        });
        model.archiveGame(game);
      }
    });
    endAsyncTestBlock();    
  }
  
  public void testIsCurrentPlayer() {
    Map<String, Object> map = map("gameOver", true);
    Game g1 = newGame(map);
    assertFalse(model.isCurrentPlayer(g1));

    Game g2 = newGame(map("currentPlayerNumber", 0, "players", list("fooId")));
    assertFalse(model.isCurrentPlayer(g2));

    Game g3 = newGame(map(
      "currentPlayerNumber", 1,
      "players", list("fooId", userId)));
    assertTrue(model.isCurrentPlayer(g3));

    Game g4 = newGame(map(
      "currentPlayerNumber", 0,
      "players", list("fooId", userId)));
    assertFalse(model.isCurrentPlayer(g4));
  }

  public void testEnsureIsCurrentPlayer() {
    assertDies(new Runnable() {
      @Override
      public void run() {
        model.ensureIsCurrentPlayer(newGame(map(
          "players", list("foo", userId),
          "currentPlayerNumber", 0
        )));
      }
    });
  }

  public void testEnsureIsPlayer() {
    assertDies(new Runnable() {
      @Override
      public void run() {
        model.ensureIsPlayer(newGame(map(
          "players", list("foo"),
          "currentPlayerNumber", 0
        )));
      }
    });
  }
  
  @SuppressWarnings("unchecked")
  private Action action(int player, int column, int row) {
    return new Action(map(
        "playerNumber", player,
        "submitted", true,
        "commands", list(map(
          "column", column,
          "row", row
        ))));
  }
  
  private Game newGame() {
    return newGame(map());
  }
  
  private Game newGame(Map<String, Object> map) {
    String gameId = firebase.child("games").push().getName();
    map.put("id", gameId);
    return new Game(map);
  }
  
  @SuppressWarnings("unchecked")
  private Game newGameWithCurrentCommand() {
    return newGame(map(
        "currentPlayerNumber", 0,
        "players", list(userId, "opponentId"),
        "gameOver", false,
        "localMultiplayer", false,
        "isMinimal", false,
        "actions", list(map(
          "playerNumber", 0,
          "commands", list(map(
            "column", 2,
            "row", 1
          ))
        )),
        "profiles", map(
          userId, map(
            "name", "User",
            "pronoun", "MALE"
          ),
          "opponentId", map(
            "name", "Opponent",
            "pronoun", "FEMALE"
          )
        ),
        "lastModified", 123L,
        "currentActionNumber", 0
        ));
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
        assertEquals(game.minimalGame(), newGame);
        testFn.run();
      }
    });
    firebase.child("games").child(game.getId()).setValue(game.serialize());
    firebase.child("users").child(userId).child("games").child(game.getId())
        .setValue(game.minimalGame().serialize());
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
