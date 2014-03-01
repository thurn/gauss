package ca.thurn.noughts.shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        assertTrue(game.getPlayerList().contains(userId));
        assertEquals(Model.X_PLAYER, (int)game.getCurrentPlayerNumber());
        assertTrue(game.getLastModified() > 0);
        assertTrue(game.isLocalMultiplayer());
        assertFalse(game.isGameOver());
        assertTrue(game.isMinimal());
        assertEquals(0, game.getActionCount());
        assertEquals("John", game.getLocalProfile(0).getName());
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
    Map<String, Profile> profiles = new HashMap<String, Profile>();
    Profile.Builder profile = Profile.newBuilder();
    profile.setName("John");
    profile.setPronoun(Pronoun.NEUTRAL);
    profiles.put(userId, profile.build());
    String id = model.newGame(profiles);
    model.setGameUpdateListener(id, new NoStatusGameUpdateListener() {
      @Override
      public void onGameUpdate(Game game) {
        assertTrue(game.getPlayerList().contains(userId));
        assertEquals(Model.X_PLAYER, (int)game.getCurrentPlayerNumber());
        assertTrue(game.getLastModified() > 0);
        assertFalse(game.isLocalMultiplayer());
        assertFalse(game.isGameOver());
        assertEquals(0, game.getActionCount());
        assertEquals("John", game.getProfile(userId).getName());
        finished();
      }
    });
    endAsyncTestBlock();
  }
  
  public void testAddCommandToExistingAction() {
    beginAsyncTestBlock();
    final Game.Builder game = newGame();
    game.addPlayer(userId);
    game.setCurrentPlayerNumber(0);
    Action.Builder action = Action.newBuilder();
    action.setPlayerNumber(0);
    action.setSubmitted(false);
    action.setGameId(game.getId());
    game.addAction(action.build());
    game.setCurrentActionNumber(0);
    assertEquals(action.build(), game.build().currentAction());
    final Command command = Command.newDeserializer()
        .deserialize(map("column", 2, "row", 2));
    withTestData(game.build(), new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.getId(), new NoStatusGameUpdateListener() {
          @Override
          public void onGameUpdate(Game game) {
            assertNotNull(game);
            assertNotNull(game.getLastModified());
            assertTrue(game.getLastModified() > 0);
            Action action = game.currentAction();
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
  
  public void testAddCommandNotCurrentPlayer() {
    assertDies(new Runnable() {
      @Override public void run() {
        model.addCommand(Game.newDeserializer().deserialize(map(
            "players", list("foo", userId),
            "currentPlayerNumber", 0
            )), newCommand(0, 0));
      }
    });
  }
  
  public void testAddCommandToNewAction() {
    beginAsyncTestBlock();
    final Game.Builder game = newGame(map("players", list(userId), "currentPlayerNumber", 0));
    final Command command = newCommand(1, 1);
    withTestData(game.build(), new Runnable() {
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
    final Game.Builder game = newGame(map(
        "players", list(userId, "opponentId"),
        "currentPlayerNumber", 0,
        "lastModified", 123L,
        "profiles", map(
          userId, map(
            "name", "Player 1"
          ),
          "opponentId", map(
            "name", "Player 2"
          )
         )
        ));
    final Command command = newCommand(1, 1);
    withTestData(game.build(), new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.getId(), new NoStatusGameUpdateListener() {
          @Override
          public void onGameUpdate(Game game) {
            assertFalse(game.hasCurrentActionNumber());
            assertTrue(game.getLastModified() > 150L);
            assertEquals(1, (int)game.getCurrentPlayerNumber());
            finished();
          }
        });
        model.addCommandAndSubmit(game.build(), command);
      }
    });
    endAsyncTestBlock();
  }
  
  public void testAddCommandUpdatesLastModified() {
    beginAsyncTestBlock();
    final Game.Builder game = newGame(map(
        "players", list(userId),
        "currentPlayerNumber", 0,
        "lastModified", 123L));
    final Command command = newCommand(1, 1);
    withTestData(game.build(), new Runnable() {
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
        model.addCommand(game.build(), command);
      }
    });
    endAsyncTestBlock();
  }
  
  public void testGameUpdateListener() {
    beginAsyncTestBlock();
    final Game.Builder game = newGame();
    withTestData(game.build(), new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.getId(), new NoStatusGameUpdateListener() {
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
  
  public void testCouldSubmitCommand() {
    Command command = newCommand(1, 1);
    assertFalse(model.couldSubmitCommand(newGame(map("gameOver", true)).build(), command));
    Game.Builder game = newGameWithCurrentCommand();
    assertFalse(model.couldSubmitCommand(game.build(), command));
    game.clearCurrentActionNumber();
    Action.Builder action = game.getAction(0).toBuilder();
    action.setSubmitted(true);
    game.setAction(0, action.build());
    assertTrue(model.couldSubmitCommand(game.build(), command));
    action = game.getAction(0).toBuilder();
    action.clearCommandList();
    action.addCommand(command);
    game.setAction(0, action.build());
    assertFalse(model.couldSubmitCommand(game.build(), command));
  }
  
  public void testCanUndo() {
    Game.Builder game = newGameWithCurrentCommand();
    assertTrue(model.canUndo(game.build()));
    Action.Builder action = game.getAction(0).toBuilder();
    action.clearCommandList();
    game.setAction(0, action.build());
    assertFalse(model.canUndo(game.build()));
  }
  
  public void testCanRedo() {
    Game.Builder game = newGameWithCurrentCommand();
    assertFalse(model.canRedo(game.build()));
    Action.Builder action = game.getAction(0).toBuilder();
    Command command = action.getCommandList().remove(0);
    action.addFutureCommand(command);
    game.setAction(0, action.build());
    assertTrue(model.canRedo(game.build()));
  }
  
  public void testCanSubmit() {
    Game.Builder game = newGameWithCurrentCommand();
    assertTrue(model.canSubmit(game.build()));
    Action.Builder action = game.getAction(0).toBuilder();
    action.clearCommandList();
    game.setAction(0, action.build());
    assertFalse(model.canSubmit(game.build()));
    Action.Builder action2 = game.getAction(0).toBuilder();
    action2.addCommand(newCommand(0, 5));
    game.setAction(0, action2.build());    
    assertFalse(model.canSubmit(game.build()));
  }
  
  public void testComputeVictors() {
    Game.Builder game = newGame();
    game.addAllAction(list(
      action(0, 0, 0),
      action(1, 0, 1),
      action(0, 0, 2)      
    ));
    assertNull(model.computeVictorsIfCurrentActionSubmitted(game.build()));
    
    game = newGame();
    game.addAllAction(list(
      action(0, 0, 0),
      action(0, 0, 1),
      action(0, 0, 2)
    ));
    assertDeepEquals(list(0), model.computeVictorsIfCurrentActionSubmitted(game.build()));
    
    game = newGame();
    game.addAllAction(list(
      action(0, 0, 0),
      action(0, 1, 1),
      action(0, 2, 2)
    ));
    assertDeepEquals(list(0), model.computeVictorsIfCurrentActionSubmitted(game.build()));
    
    game = newGame();
    game.addAllAction(list(
      action(0, 0, 2),
      action(1, 1, 1),
      action(0, 1, 2),
      action(1, 0, 1),
      action(0, 2, 2)
    ));
    assertDeepEquals(list(0), model.computeVictorsIfCurrentActionSubmitted(game.build()));
    
    game = newGame();
    game.addPlayer("x");
    game.addPlayer("o");
    game.addAllAction(list(
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
    assertDeepEquals(list(0, 1), model.computeVictorsIfCurrentActionSubmitted(game.build()));
  }
  
  public void testSubmitCurrentAction() {
    beginAsyncTestBlock();
    final Game.Builder game = newGameWithCurrentCommand();
    withTestData(game.build(), new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.getId(), new NoStatusGameUpdateListener() {
          @Override
          public void onGameUpdate(Game game) {
            assertEquals(1, (int)game.getCurrentPlayerNumber());
            assertFalse(game.hasCurrentActionNumber()); 
            finished();
          }
        });
        model.submitCurrentAction(game.build());
      }
    });
    endAsyncTestBlock();
  }
  
  public void testSubmitCurrentActionUpdatesGameStatus() {
    // TODO: this.
  }
  
  public void testSubmitCurrentActionGameList() {
    beginAsyncTestBlock();
    final Game.Builder game = newGameWithCurrentCommand();
    withTestData(game.build(), new Runnable() {
      @Override
      public void run() {
        model.setGameListListener(new AbstractGameListListener() {
          @Override
          public void onGameChanged(Game game) {
            assertEquals(1, (int)game.getCurrentPlayerNumber());
            assertTrue(game.getLastModified() > 150L);
            assertFalse(game.hasCurrentActionNumber());
            finished();            
          }
        });
        model.submitCurrentAction(game.build());
      }
    });
    endAsyncTestBlock();
  }
  
  public void testSubmitCurrentActionLocalMultiplayer() {
    beginAsyncTestBlock();
    final Game.Builder game = newGameWithCurrentCommand();
    game.setLocalMultiplayer(true);
    game.clearPlayerList();
    game.addPlayer(userId);
    game.addPlayer(userId);
    withTestData(game.build(), new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.getId(), new NoStatusGameUpdateListener() {
          @Override
          public void onGameUpdate(Game game) {
            assertEquals(userId, game.currentPlayerId());
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
    game.putProfile(userId, Profile.newDeserializer().deserialize(map(
        "name", "User",
        "pronoun", "MALE"
        )));
    game.putProfile("o", Profile.newDeserializer().deserialize(map(
        "name", "Opponent",
        "pronoun", "FEMALE"
        )));    
    game.addAllAction(list(
      action(0, 0, 2),
      action(1, 1, 1),
      action(0, 1, 2),
      action(1, 0, 1)
    ));
    Action.Builder action = Action.newBuilder();
    action.setPlayerNumber(0);
    action.setSubmitted(false);
    action.addCommand(newCommand(2, 2));
    action.setGameId(game.getId());
    game.addAction(action.build());
    game.setCurrentActionNumber(4);
    game.setCurrentPlayerNumber(0);
    withTestData(game.build(), new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.getId(), new NoStatusGameUpdateListener() {
          @Override
          public void onGameUpdate(Game game) {
            assertFalse(game.hasCurrentPlayerNumber());
            assertFalse(game.hasCurrentActionNumber());
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
        model.setGameUpdateListener(game.getId(), new NoStatusGameUpdateListener() {
          @Override
          public void onGameUpdate(Game game) {
            assertDeepEquals(list(), game.currentAction().getCommandList());
            assertDeepEquals(list(newCommand(2, 1)),
                game.currentAction().getFutureCommandList());    
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
    Action.Builder action = game.build().currentAction().toBuilder();
    action.clearCommandList();
    final Command command = newCommand(0, 0);
    action.addFutureCommand(command);
    game.setAction(game.getCurrentActionNumber(), action.build());
    withTestData(game.build(), new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.getId(), new NoStatusGameUpdateListener() {
          @Override
          public void onGameUpdate(Game game) {
            assertDeepEquals(list(command), game.currentAction().getCommandList());
            assertDeepEquals(list(), game.currentAction().getFutureCommandList());     
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
            "players", list("foo"),
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
            "players", list(userId, "foo"),
            "currentPlayerNumber", 0,
            "gameOver", true
        )).build());
      }
    });    
  }
  
  public void testResignLocalMultiplayer() {
    beginAsyncTestBlock();
    final Game.Builder game = newGame(map(
        "currentPlayerNumber", 1,
        "players", list(userId, userId),
        "currentActionNumber", 0,
        "localMultiplayer", true,
        "lastModified", 123L
        ));
    withTestData(game.build(), new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.getId(), new NoStatusGameUpdateListener() {
          @Override
          public void onGameUpdate(Game game) {
            assertDeepEquals(list(new Integer(1)), game.getResignedPlayerList());
            assertTrue(game.isGameOver());
            assertFalse(game.hasCurrentActionNumber());
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
    final Game.Builder game = newGame(map(
        "currentPlayerNumber", 0,
        "players", list(userId, "foobar"),
        "currentActionNumber", 0,
        "lastModified", 123L
        ));
    withTestData(game.build(), new Runnable() {
      @Override
      public void run() {
        model.setGameUpdateListener(game.getId(), new NoStatusGameUpdateListener() {
          @Override
          public void onGameUpdate(Game game) {
            assertDeepEquals(list(new Integer(0)), game.getResignedPlayerList());
            assertTrue(game.isGameOver());
            assertFalse(game.hasCurrentActionNumber());
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
            "players", list("foo"),
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
            "players", list(userId, "foo"),
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
        "players", list(userId, "foobar"),
        "gameOver", true
        ));
    withTestData(game.build(), new Runnable() {
      @Override
      public void run() {
        model.setGameListListener(new AbstractGameListListener() {
          @Override
          public void onGameRemoved(Game newGame) {
            assertEquals(game.build().minimalGame(), newGame);
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

    Game g2 = newGame(map("currentPlayerNumber", 0, "players", list("fooId"))).build();
    assertFalse(model.isCurrentPlayer(g2));

    Game g3 = newGame(map(
      "currentPlayerNumber", 1,
      "players", list("fooId", userId))).build();
    assertTrue(model.isCurrentPlayer(g3));

    Game g4 = newGame(map(
      "currentPlayerNumber", 0,
      "players", list("fooId", userId))).build();
    assertFalse(model.isCurrentPlayer(g4));
  }

  public void testEnsureIsCurrentPlayer() {
    assertDies(new Runnable() {
      @Override
      public void run() {
        model.ensureIsCurrentPlayer(newGame(map(
          "players", list("foo", userId),
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
          "players", list("foo"),
          "currentPlayerNumber", 0
        )).build());
      }
    });
  }
  
  @SuppressWarnings("unchecked")
  private Action action(int player, int column, int row) {
    return Action.newDeserializer().deserialize(map(
        "playerNumber", player,
        "submitted", true,
        "commands", list(map(
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
  
  @SuppressWarnings("unchecked")
  private Game.Builder newGameWithCurrentCommand() {
    return newGame(map(
        "currentPlayerNumber", 0,
        "players", list(userId, "opponentId"),
        "gameOver", false,
        "localMultiplayer", false,
        "getIsMinimal", false,
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
