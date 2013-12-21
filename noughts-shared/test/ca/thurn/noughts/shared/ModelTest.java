package ca.thurn.noughts.shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.thurn.noughts.shared.Game.GameDeserializer;
import ca.thurn.noughts.shared.Model.GameUpdateListener;
import ca.thurn.testing.SharedTestCase;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class ModelTest extends SharedTestCase {
  private Model model;
  private String userId;
  private Firebase firebase;

  @Override
  public void sharedSetUp(final Runnable done) {
    firebase = new Firebase("https://noughts-test.firebaseio-demo.com");
    userId = randomInteger() + "";
    model = new Model(userId, firebase);
    firebase.removeValue(new Firebase.CompletionListener() {
      @Override
      public void onComplete(FirebaseError error, Firebase ref) {
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
        assertTrue(game.players.contains(userId));
        assertEquals(Model.X_PLAYER, (int)game.currentPlayerNumber);
        assertTrue(game.lastModified > 0);
        assertTrue(game.isLocalMultiplayer());
        assertFalse(game.isGameOver());
        assertEquals(0, game.actions.size());
        finished();
      }
    });
    String id = model.newGame(true, null, null);
    assertTrue(!id.equals(""));
    endAsyncTestBlock();
  }
  
  public void testAddCommandToExistingAction() {
    beginAsyncTestBlock();
    final Game game = newGame();
    game.players.add(userId);
    game.currentPlayerNumber = 0;
    Action action = new Action(userId);
    action.gameId = game.id;
    game.actions.add(action);
    game.currentActionNumber = 0;
    assertEquals(action, game.currentAction());
    final Command command = new Command(map("column", 2, "row", 2));
    withTestData(game, new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.id, new GameUpdateListener() {
          @Override
          public void onGameUpdate(Game game) {
            assertTrue(game.lastModified > 0);
            Action action = game.currentAction();
            assertEquals(0, action.futureCommands.size());
            assertEquals(command, action.commands.get(0));
            model.removeGameUpdateListener(game.id);
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
        model.setGameUpdateListener(game.id, new GameUpdateListener() {
          @Override
          public void onGameUpdate(Game game) {
            assertEquals(0, (int)game.currentActionNumber);
            assertTrue(game.lastModified > 0);
            Action action = game.currentAction();
            assertEquals(userId, action.player);
            assertFalse(action.isSubmitted());
            assertEquals(game.id, action.gameId);
            assertEquals(1, action.commands.size());
            assertEquals(command, action.commands.get(0));
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
        model.setGameUpdateListener(game.id, new GameUpdateListener() {
          @Override
          public void onGameUpdate(Game game) {
            assertEquals(123L, (long)game.lastModified);
            finished();
          }
        });
        firebase.child("games").child(game.id).updateChildren(map("lastModified", 123L));
      }
    });
    endAsyncTestBlock();
  }
  
  public void testCouldSubmitCommand() {
    Command command = new Command(1, 1);
    assertFalse(model.couldSubmitCommand(newGame(map("gameOver", true)), command));
    Game game = newGameWithCurrentCommand();
    assertFalse(model.couldSubmitCommand(game, command));
    game.currentActionNumber = null;
    game.actions.get(0).setSubmitted(true);
    assertTrue(model.couldSubmitCommand(game, command));
    assertFalse(model.couldSubmitCommand(game, new Command(3, 1)));
    game.actions.get(0).commands.remove(0);
    game.actions.get(0).commands.add(command);
    assertFalse(model.couldSubmitCommand(game, command));
  }
  
  public void testCanUndo() {
    Game game = newGameWithCurrentCommand();
    assertTrue(model.canUndo(game));
    game.actions.get(0).commands.clear();
    assertFalse(model.canUndo(game));
  }
  
  public void testCanRedo() {
    Game game = newGameWithCurrentCommand();
    assertFalse(model.canRedo(game));
    Command command = game.actions.get(0).commands.remove(0);
    game.actions.get(0).futureCommands.add(command);
    assertTrue(model.canRedo(game));
  }
  
  public void testCanSubmit() {
    Game game = newGameWithCurrentCommand();
    assertTrue(model.canSubmit(game));
    game.actions.get(0).commands.clear();
    assertFalse(model.canSubmit(game));
    game.actions.get(0).commands.add(new Command(0, 5));
    assertFalse(model.canSubmit(game));
  }
  
  public void testComputeVictors() {
    Game game = newGame();
    game.actions.addAll(list(
      action("x", 0, 0),
      action("o", 0, 1),
      action("x", 0, 2)      
    ));
    assertNull(model.computeVictors(game));
    
    game = newGame();
    game.actions.addAll(list(
      action("x", 0, 0),
      action("x", 0, 1),
      action("x", 0, 2)
    ));
    assertDeepEquals(list("x"), model.computeVictors(game));
    
    game = newGame();
    game.actions.addAll(list(
      action("x", 0, 0),
      action("x", 1, 1),
      action("x", 2, 2)
    ));
    assertDeepEquals(list("x"), model.computeVictors(game));
    
    game = newGame();
    game.actions.addAll(list(
      action("x", 0, 2),
      action("o", 1, 1),
      action("x", 1, 2),
      action("o", 0, 1),
      action("x", 2, 2)
    ));
    assertDeepEquals(list("x"), model.computeVictors(game));
    
    game = newGame();
    game.players.add("x");
    game.players.add("o");
    game.actions.addAll(list(
      action("o", 0, 0),
      action("x", 0, 1),
      action("o", 0, 2),
      action("o", 1, 0),
      action("x", 1, 1),
      action("o", 1, 2),
      action("x", 2, 0),
      action("o", 2, 1),
      action("x", 2, 2)
    ));
    assertDeepEquals(list("x", "o"), model.computeVictors(game));
  }
  
  public void testSubmitCurrentAction() {
    beginAsyncTestBlock();
    final Game game = newGameWithCurrentCommand();
    withTestData(game, new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.id, new GameUpdateListener() {
          @Override
          public void onGameUpdate(Game game) {
            assertEquals(1, (int)game.currentPlayerNumber);
            assertNull(game.currentActionNumber);
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
    game.players.add(Model.LOCAL_MULTIPLAYER_OPPONENT_ID);
    withTestData(game, new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.id, new GameUpdateListener() {
          @Override
          public void onGameUpdate(Game game) {
            assertEquals(Model.LOCAL_MULTIPLAYER_OPPONENT_ID, game.currentPlayerId());
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
    game.players.add(userId);
    game.players.add("o");
    game.actions.addAll(list(
      action(userId, 0, 2),
      action("o", 1, 1),
      action(userId, 1, 2),
      action("o", 0, 1)
    ));
    Action action = new Action(userId);
    action.commands.add(new Command(2, 2));
    action.gameId = game.id;
    game.actions.add(action);
    game.currentActionNumber = 4;
    game.currentPlayerNumber = 0;
    withTestData(game, new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.id, new GameUpdateListener() {
          @Override
          public void onGameUpdate(Game game) {
            assertNull(game.currentPlayerNumber);
            assertNull(game.currentActionNumber);
            assertDeepEquals(list(userId), game.victors);
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
        model.setGameUpdateListener(game.id, new GameUpdateListener() {
          @Override
          public void onGameUpdate(Game game) {
            assertDeepEquals(list(), game.currentAction().commands);
            assertDeepEquals(list(new Command(2, 1)),
                game.currentAction().futureCommands);
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
    game.currentAction().commands.clear();
    final Command command = new Command(0, 0);
    game.currentAction().futureCommands.add(command);
    withTestData(game, new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.id, new GameUpdateListener() {
          @Override
          public void onGameUpdate(Game game) {
            assertDeepEquals(list(command), game.currentAction().commands);
            assertDeepEquals(list(), game.currentAction().futureCommands);
            finished();
          }
        });
        model.redoCommand(game);
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
  
  public void testIsPlayer() {
    assertFalse(model.isPlayer(newGame(map("players", list()))));
    assertFalse(model.isPlayer(newGame(map("players", list("foo")))));
    assertTrue(model.isPlayer(newGame(map("players", list("foo", userId)))));
  }

  public void testEnsureIsCurrentPlayer() {
    assertDies(new Runnable() {
      @Override
      public void run() {
        model.ensureIsCurrentPlayer(newGame(map(
          "players", list("foo"),
          "currentPlayerNumber", 0
        )));
      }
    });
  }

  private Action action(String player, int column, int row) {
    return new Action(map(
        "player", player,
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
  
  private Game newGameWithCurrentCommand() {
    return newGame(map(
        "currentPlayerNumber", 0,
        "players", list(userId),
        "actions", list(map(
          "commands", list(map(
            "column", 2,
            "row", 1
          ))
        )),
        "currentActionNumber", 0
        ));
  }
  
  private void assertDies(Runnable testFn) {
    try {
      testFn.run();
    } catch (RuntimeException expected) {}
  }
  
  private void withTestData(final Game game, final Runnable testFn) {
    firebase.child("games").addChildEventListener(new AbstractChildEventListener() {
      @Override
      public void onChildAdded(DataSnapshot snapshot, String previous) {
        firebase.child("games").removeEventListener(this);
        assertEquals(game, new GameDeserializer().fromDataSnapshot(snapshot));
        testFn.run();
      }
    });
    firebase.child("games").child(game.id).setValue(game.serialize());
  }
  
  @SafeVarargs
  private final <T> List<T> list(T... objects) {
    List<T> result = new ArrayList<T>();
    for (T t : objects) {
      result.add(t);
    }
    return result;
  }
  
  private Map<String, Object> map(Object... objects) {
    Map<String, Object> result = new HashMap<String, Object>();
    for (int i = 0; i < objects.length; i += 2) {
      result.put(objects[i].toString(), objects[i + 1]);
    }
    return result;
  }

}
