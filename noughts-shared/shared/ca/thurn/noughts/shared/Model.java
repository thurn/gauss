package ca.thurn.noughts.shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.xtext.xbase.lib.Procedures;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.MutableData;
import com.firebase.client.Transaction;
import com.firebase.client.Transaction.Handler;
import com.firebase.client.Transaction.Result;

public class Model {
  public static final int X_PLAYER = 0;
  public static final int O_PLAYER = 1;
  public static final String LOCAL_MULTIPLAYER_OPPONENT_ID = "LOCAL_MULTIPLAYER_OPPONENT_ID";
  
  private String userId;
  private final Firebase firebase;
  
  public Model(String userId, Firebase firebase) {
    this.userId = userId;
    this.firebase = firebase;
  }
  
  /**
   * Updates the current user for this model.
   *
   * @param userId The new user's ID.
   */
  public void setUserId(String userId) {
    this.userId = userId; 
  }
  
  /**
   * @param game A game.
   * @return True if the current user is the current player in the provided
   * game. 
   */
  public boolean isCurrentPlayer(Game game) {
    if (game.gameOver) return false;
    return game.currentPlayerId() == userId;
  }

  /**
   * @param game A game.
   * @return True if the current user is a player in the provided game.
   */
  public boolean isPlayer(Game game) {
    return game.players.contains(userId);
  }
  
  /**
   * Partially create a new game with no opponent specified yet, returning the
   * game ID.
   *
   * @param localMultiplayer Sets whether the game is a local multiplayer game.
   * @param userProfile Optionally, the profile of the current user.
   * @param opponentProfile Optionally, the profile of the opponent
   *     for this game.
   * @return The newly created game's ID.
   */  
  public String newGame(boolean localMultiplayer, Map<String, String> userProfile,
      Map<String, String> opponentProfile) {
    Firebase ref = firebase.child("games").push();
    Game game = new Game();
    game.id = ref.getName();
    game.players.add(userId);
    game.localMultiplayer = localMultiplayer;
    if (localMultiplayer) game.players.add(LOCAL_MULTIPLAYER_OPPONENT_ID);
    game.currentPlayerNumber = X_PLAYER;
    game.currentActionNumber = null;
    game.lastModified = System.currentTimeMillis();
    game.gameOver = false;
    
    if (userProfile != null) {
      if (userProfile.get("id") != userId) {
        die("Expected user ID in profile to match model user ID");
      }
      game.profiles.put(userId, userProfile);
    }
    if (opponentProfile != null) {
      game.profiles.put(opponentProfile.get("id"), opponentProfile);
      game.players.add(opponentProfile.get("id"));
    }
    
    ref.setValue(game.serialize());
    return game.id;
  }
  
  /**
   * Adds the provided command to the current action's command list. If there
   * is no current action, creates one. Any commands beyond the current
   * location in the undo history are deleted.
   *
   * @param game The current game.
   * @param command The command to add.
   */  
  public void addCommand(final Game game, final Command command) {
    ensureIsCurrentPlayer(game);
    if (!couldSubmitCommand(game, command)) die("Illegal Command: " + command);
    mutateGame(game, new Procedures.Procedure1<Game>() {
      @Override public void apply(Game newGame) {
        long timestamp = System.currentTimeMillis();
        if (game.hasCurrentAction()) {
          newGame.lastModified = timestamp;
          Action action = newGame.currentAction();
          action.futureCommands.clear();
          action.gameId = "bar";
          action.commands.add(command);
        } else {
          Action action = new Action();
          action.player = userId;
          action.playerNumber = game.currentPlayerNumber;
          action.submitted = false;
          action.gameId = game.id;
          action.commands.add(command);
          newGame.actions.add(action);
          newGame.currentActionNumber = newGame.actions.size() - 1;
          newGame.lastModified = timestamp;
        }
      }
    });
  }
  
  /**
   * Checks if a command could legally be played in a game.
   * 
   * @param game Game the command will be added to.
   * @param command The command to check.
   * @return true if this command could be added the current action of this
   *     game. 
   */  
  public boolean couldSubmitCommand(Game game, Command command) {
    if (!isCurrentPlayer(game)) return false;
    if (game.hasCurrentAction() && game.currentAction().commands.size() > 0) return false;
    return isLegalCommand(game, command);
  }
  
  /**
   * Checks if an undo action is currently possible.
   * 
   * @param game The game to check (possibly null).
   * @return True if a command has been added to the current action of "game"
   *     which can be undone.
   */  
  public boolean canUndo(Game game) {
    if (game == null || !game.hasCurrentAction()) return false;
    return game.currentAction().commands.size() > 0;    
  }
  
  /**
   * Checks if a redo action is currently possible.
   * 
   * @param game The game to check (possibly null).
   * @return True if a command has been added to the "futureCommands" of the
   *     current action of "game" and thus can be redone.
   */  
  public boolean canRedo(Game game) {
    if (game == null || !game.hasCurrentAction()) return false;
    return game.currentAction().futureCommands.size() > 0;    
  }
  
  /**
   * Checks if the current action can be submitted.
   * 
   * @param game The game to check (possibly null).
   * @return True if the current action of "game" is a legal one which could be
   *     submitted. 
   */
  public boolean canSubmit(Game game) {
    if (game == null || !game.hasCurrentAction()) return false;
    Action action = game.currentAction();
    if (action.commands.size() == 0) return false;
    for (Command command : action.commands) {
      if (!isLegalCommand(game, command)) return false;
    }
    return true;
  }

  /**
  * Submits the provided game's current action, if it is a legal one. If this
  * ends the game: populates the "victors" array and sets the "gameOver"
  * bit. Otherwise, updates the current player.
  * 
  * @param game The game to submit.
  */  
  public void submitCurrentAction(Game game) {
    ensureIsCurrentPlayer(game);
    if (!canSubmit(game)) die("Illegal action!");
    boolean isXPlayer = game.currentPlayerNumber == X_PLAYER;
    final int newPlayerNumber = isXPlayer ? O_PLAYER : X_PLAYER;
    mutateGame(game, new Procedures.Procedure1<Game>() {
      @Override public void apply(Game newGame) {
        newGame.currentAction().submitted = true;
        List<String> victors = computeVictors(newGame);
        if (victors == null) {
          newGame.currentPlayerNumber = newPlayerNumber;
          newGame.currentActionNumber = null;
          if (newGame.localMultiplayer) {
            // Update model with new player number in local multiplayer games
            setUserId(newGame.currentPlayerId());
          }
        } else {
          // Game over!
          newGame.currentPlayerNumber = null;
          newGame.currentActionNumber = null;
          newGame.victors.addAll(victors);
          newGame.gameOver = true;
        }        
    }});
  }
  
  /**
   * Undoes the player's previous command. Throws an exception if there's no
   * previous command to undo.
   * 
   * @param game The game to undo the previous command of.
   */  
  public void undoCommand(Game game) {
    ensureIsCurrentPlayer(game);
    if (game.currentAction().commands.size() == 0) die("No previous command to undo");
    mutateGame(game, new Procedures.Procedure1<Game>() {
      @Override public void apply(Game newGame) {
        Action action = newGame.currentAction();
        Command command = action.commands.remove(action.commands.size() - 1);
        action.futureCommands.add(command);
    }});
  }
  
  /**
   * Re-does the player's previously undone command. Throws an exception if there's no
   * previous command to redo.
   * 
   * @param game The game to undo the previous command of.
   */
  public void redoCommand(Game game) {
    ensureIsCurrentPlayer(game);
    if (game.currentAction().futureCommands.size() == 0) die("No previous next command to redo");
    mutateGame(game, new Procedures.Procedure1<Game>() {
      @Override public void apply(Game newGame) {
        Action action = newGame.currentAction();
        Command command = action.futureCommands.remove(action.futureCommands.size() - 1);
        action.commands.add(command);
    }});
  }
  
  /**
   * @param game The game, assumed to be 2-player game.
   * @return The ID of your opponent in this game, or null if there isn't one.
   */  
  public String getOpponentId(Game game) {
    for (String player : game.players) {
      if (!player.equals(userId)) return player;
    }
    throw die("No opponent found");
  }
  
  
  /**
   * Builds the "victors" array for the game. If the game is over, a list will be
   * returned containing the victorious or drawing players (which may be empty to
   * indicate that "nobody wins"). Otherwise, null is returned.
   * 
   * @param game The game to find the victors for
   * @return A list of victors or null if the game is not over.
   */
  private List<String> computeVictors(Game game) {
    // 1) check for win
    
    Action[][] actionTable = makeActionTable(game);
    // All possible winning lines in [column, row] format:
    int[][][] lines =  { {{0,0}, {1,0}, {2,0}}, {{0,1}, {1,1}, {2,1}},
        {{0,2}, {1,2}, {2,2}}, {{0,0}, {0,1}, {0,2}}, {{1,0}, {1,1}, {1,2}},
        {{2,0}, {2,1}, {2,2}}, {{0,0}, {1,1}, {2,2}}, {{2,0}, {1,1}, {0,2}} };
    for (int i = 0; i < lines.length; ++i) {
      Action action1 = actionTable[lines[i][0][0]][lines[i][0][1]];
      Action action2 = actionTable[lines[i][1][0]][lines[i][1][1]];
      Action action3 = actionTable[lines[i][2][0]][lines[i][2][1]];
      if (action1 != null && action2 != null && action3 != null &&
          action1.player.equals(action2.player) && action2.player.equals(action3.player)) {
        List<String> result = new ArrayList<String>();
        result.add(action1.player);
        return result;
      }
    }
    
    // 2) check for draw
    
    int submitted = 0;
    for (Action action : game.actions) {
      if (action.isSubmitted()) submitted++;
    }
    if (submitted == 9) {
      return game.players;
    }
    
    // 3) game is not over
    return null;
  }
  
  
  /**
   * Checks if the square at (column, row) has been already taken.
   */  
  private boolean isLegalCommand(Game game, Command command) {
    int column = command.column;
    int row = command.row; 
    if (column < 0 || row < 0 || column > 2 || row > 2) {
      return false;
    }
    return makeActionTable(game)[column][row] != null;    
  }
  
  /** 
   * Returns a 2-dimensional map of *submitted* game actions spatially indexed
   * by [column][row], so e.g. table[0][2] is the bottom-left square's action. 
   */  
  private Action[][] makeActionTable(Game game) {
    Action[][] result = new Action[3][3];
    for (Action action : game.actions) {
      if (action.isSubmitted()) {
        for (Command command : action.commands) {
          result[command.column][command.row] = action;
        }
      }
    }
    return result;
  }
  
  /**
   * Runs a  
   */
  private void mutateGame(Game game, final Procedures.Procedure1<Game> function) {
    Firebase ref = firebase.child("games").child(game.id);
    ref.runTransaction(new Transaction.Handler() {
      
      @Override
      public void onComplete(FirebaseError error, boolean done, DataSnapshot snapshot) {
      }
      
      @Override
      public Result doTransaction(MutableData mutableData) {
        Game game = Game.fromMutableData(mutableData);
        function.apply(game);
        mutableData.setValue(game.serialize());
        return null;
      }
    });
  }  
  
  /**
   * Throws an exception.
   */  
  private RuntimeException die(String message) {
    throw new RuntimeException(message);
  }

  /**
   * Ensures the current user is a player in the provided game.
   */
  private void ensureIsPlayer(Game game) {
    if (!isPlayer(game)) die("Unauthorized user: " + userId);
  }

  /**
   * Ensures the current user is the current player in the provided game.
   */
  private void ensureIsCurrentPlayer(Game game) {
    if (!isCurrentPlayer(game)) die("Unauthorized user: " + userId);
  }
}
