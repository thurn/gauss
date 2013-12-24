package ca.thurn.noughts.shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.thurn.noughts.shared.Model.GameUpdateListener;
import ca.thurn.testing.SharedTestCase;

import com.firebase.client.Firebase;

public class ModelTest extends SharedTestCase {
  private Model model;
  private String userId;
  private Firebase firebase;

  @Override
  public void sharedSetUp(Runnable done) {
    firebase = new Firebase("https://www.example.com/");
    userId = randomInteger() + "";
    model = new Model(userId, firebase);
    done.run();
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
        assertEquals(0, game.getActionsMutable().size());
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
    game.getPlayersMutable().add(userId);
    game.setCurrentPlayerNumber(0);
    Action action = new Action(userId);
    action.setGameId(game.getId());
    game.getActionsMutable().add(action);
    game.setCurrentActionNumber(0);
    assertEquals(action, game.currentAction());
    final Command command = new Command(map("column", 2, "row", 2));
    withTestData(game, new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.getId(), new GameUpdateListener() {
          @Override
          public void onGameUpdate(Game game) {
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
        model.setGameUpdateListener(game.getId(), new GameUpdateListener() {
          @Override
          public void onGameUpdate(Game game) {
            assertEquals(0, (int)game.getCurrentActionNumber());
            assertTrue(game.getLastModified() > 0);
            Action action = game.currentAction();
            assertEquals(userId, action.getPlayer());
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
  
  public void testGameUpdateListener() {
    beginAsyncTestBlock();
    final Game game = newGame();
    withTestData(game, new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.getId(), new GameUpdateListener() {
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
      action("x", 0, 0),
      action("o", 0, 1),
      action("x", 0, 2)      
    ));
    assertNull(model.computeVictors(game));
    
    game = newGame();
    game.getActionsMutable().addAll(list(
      action("x", 0, 0),
      action("x", 0, 1),
      action("x", 0, 2)
    ));
    assertDeepEquals(list("x"), model.computeVictors(game));
    
    game = newGame();
    game.getActionsMutable().addAll(list(
      action("x", 0, 0),
      action("x", 1, 1),
      action("x", 2, 2)
    ));
    assertDeepEquals(list("x"), model.computeVictors(game));
    
    game = newGame();
    game.getActionsMutable().addAll(list(
      action("x", 0, 2),
      action("o", 1, 1),
      action("x", 1, 2),
      action("o", 0, 1),
      action("x", 2, 2)
    ));
    assertDeepEquals(list("x"), model.computeVictors(game));
    
    game = newGame();
    game.getPlayersMutable().add("x");
    game.getPlayersMutable().add("o");
    game.getActionsMutable().addAll(list(
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
        model.setGameUpdateListener(game.getId(), new GameUpdateListener() {
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
  
  public void testSubmitCurrentActionLocalMultiplayer() {
    beginAsyncTestBlock();
    final Game game = newGameWithCurrentCommand();
    game.setLocalMultiplayer(true);
    game.getPlayersMutable().add(Model.LOCAL_MULTIPLAYER_OPPONENT_ID);
    withTestData(game, new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.getId(), new GameUpdateListener() {
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
    game.getPlayersMutable().add(userId);
    game.getPlayersMutable().add("o");
    game.getActionsMutable().addAll(list(
      action(userId, 0, 2),
      action("o", 1, 1),
      action(userId, 1, 2),
      action("o", 0, 1)
    ));
    Action action = new Action(userId);
    action.getCommandsMutable().add(new Command(2, 2));
    action.setGameId(game.getId());
    game.getActionsMutable().add(action);
    game.setCurrentActionNumber(4);
    game.setCurrentPlayerNumber(0);
    withTestData(game, new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.getId(), new GameUpdateListener() {
          @Override
          public void onGameUpdate(Game game) {
            assertNull(game.getCurrentPlayerNumber());
            assertNull(game.getCurrentActionNumber());
            assertDeepEquals(list(userId), game.getVictorsMutable());
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
        model.setGameUpdateListener(game.getId(), new GameUpdateListener() {
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
        model.setGameUpdateListener(game.getId(), new GameUpdateListener() {
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
    model.setGameListListener(new AbstractGameListListener() {
      @Override
      public void onGameAdded(Game newGame) {
        assertEquals(game, newGame);
        testFn.run();
      }      
    });
    firebase.child("games").child(game.getId()).setValue(game.serialize());
  }
  
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
