package ca.thurn.noughts.shared;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import ca.thurn.noughts.shared.Game.GameDeserializer;
import ca.thurn.noughts.shared.Game.GameStatus;
import ca.thurn.uct.algorithm.MonteCarloSearch;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.MutableData;
import com.firebase.client.Transaction;
import com.firebase.client.Transaction.Handler;
import com.firebase.client.Transaction.Result;
import com.firebase.client.ValueEventListener;

/**
 * The data model for noughts. Data is denormalized into two distinct
 * locations:
 * 
 * /users/<userid>/games/<gameid>/
 * - This path contains all of the metadata needed to render a user's game
 *   list, such as whose turn it is, when the game was last modified, etc.
 * - It does not contain enough information to actually render the state of the
 *   game, such as previous actions in the game.
 *   
 * /games/<gameid>/
 * - This path contains all of the above, plus information on the actual state
 *   of the game, such as a history of actions.
 * - When you load a game for rendering, you need to subscribe here to get the
 *   actual state of the game.
 */
public class Model implements ChildEventListener {
  public static final int X_PLAYER = 0;
  public static final int O_PLAYER = 1;

  /**
   * Interface to implement to listen for game updates.
   */
  public static interface GameUpdateListener {
    public void onGameUpdate(Game game);
    
    public void onGameStatusChanged(GameStatus status);
  }
  
  /**
   * Interface to implement to listen for game list updates.
   */
  public static interface GameListListener {
    public void onGameAdded(Game game);
    
    public void onGameChanged(Game game);
        
    public void onGameRemoved(Game game);
  }
  
  /**
   * Function to mutate a game.
   */
  private static abstract class GameMutation {
    /**
     * @param game Game to mutate.
     */
    public abstract void mutate(Game game);
  }
  
  /**
   * Handler implementation for mutating games.
   */
  private static class GameMutationHandler implements Handler {
    private final GameMutation function;
    private final boolean useMinimalForm;
    
    /**
     * @param function Mutation function to use.
     * @param useMinimalForm If true, write only the game's minimal form as
     *     returned by {@link Game#minimalGame()}.
     */
    public GameMutationHandler(GameMutation function, boolean useMinimalForm) {
      this.function = function;
      this.useMinimalForm = useMinimalForm;
    }
    
    @Override
    public void onComplete(FirebaseError error, boolean done, DataSnapshot snapshot) {
    }

    @Override
    public Result doTransaction(MutableData mutableData) {
      if (mutableData.getValue() == null) {
        // Local updates sometimes cause transactions to be run with null
        // for the current value. It is safe to ignore these.
        return Transaction.success(mutableData);
      }
      Game game = new GameDeserializer().fromMutableData(mutableData);
      function.mutate(game);
      if (useMinimalForm) {
        mutableData.setValue(game.minimalGame().serialize());
      } else {
        mutableData.setValue(game.serialize());
      }
      return Transaction.success(mutableData);
    }   
  }
  
  private final String userId;
  private final Firebase firebase;
  private final Map<String, ValueEventListener> valueEventListeners;
  private final Map<String, GameUpdateListener> gameUpdateListeners;
  private final Map<String, Game> userGameList;
  private GameListListener gameListListener;
  
  public Model(String userId, Firebase firebase) {
    this.userId = userId;
    this.firebase = firebase;
    valueEventListeners = new HashMap<String, ValueEventListener>();
    gameUpdateListeners = new HashMap<String, GameUpdateListener>();
    userGameList = new HashMap<String, Game>();
    firebase.child("users").child(userId).child("games").addChildEventListener(this);    
  }
  
  /**
   * @return The current user ID
   */
  public String getUserId() {
    return this.userId;
  }

  /**
   * @param game A game.
   * @return True if the current user is the current player in the provided
   * game. 
   */
  public boolean isCurrentPlayer(Game game) {
    if (game.isGameOver()) return false;
    return game.currentPlayerId().equals(userId);
  }
  
  /**
   * Adds a listener whose onGameUpdate method will be invoked when the game
   * with the provided ID is changed. Overwrites any previously added
   * game update listener.
   *
   * @param gameId The ID of the game.
   * @param listener The listener to add.
   */
  public void setGameUpdateListener(final String gameId, final GameUpdateListener listener) {
    if (gameId == null || listener == null) {
      throw new IllegalArgumentException("Null argument to setGameUpdateListener");
    }
    if (valueEventListeners.containsKey(gameId)) {
      gameReference(gameId).removeEventListener(valueEventListeners.get(gameId));
    }
    ValueEventListener valueListener = gameReference(gameId).addValueEventListener(
        new ValueEventListener() {
      @Override
      public void onCancelled(FirebaseError error) {
      }

      @Override
      public void onDataChange(DataSnapshot snapshot) {
        if (snapshot.getValue() != null) {
          // We ignore null values in listener functions. They can happen when
          // you add a new game and then immediately attach a new listener. The
          // listener should still be triggered once the new game is in the
          // system.
          Game game = new GameDeserializer().fromDataSnapshot(snapshot);
          listener.onGameUpdate(game);
        }
      }
    });
    valueEventListeners.put(gameId, valueListener);
    gameUpdateListeners.put(gameId, listener);
  }

  
  /**
   * Removes the GameUpdateListener associated with this Game ID.
   *
   * @param gameId ID of game to remove listener for.
   */
  public void removeGameUpdateListener(String gameId) {
    if (valueEventListeners.containsKey(gameId)) {
      gameReference(gameId).removeEventListener(valueEventListeners.get(gameId));
    }
    valueEventListeners.remove(gameId);
  }
  
  /**
   * Adds a GameListListener which will be triggered whenever the game list
   * changes. Overwrites any previously added game list listener.
   *
   * @param listener The listener to add.
   */
  public void setGameListListener(final GameListListener listener) {
    gameListListener = listener;
  }
  
  /**
   * Unregisters the current game list listener. You should call this if the
   * Model is no longer going to be used so the listener stops firing.
   */
  public void removeGameListListener() {
    firebase.removeEventListener(this);
    gameListListener = null;
  }
  
  @Override
  public void onCancelled(FirebaseError error) {
  }

  @Override
  public void onChildAdded(DataSnapshot snapshot, String previous) {
    Game game = new GameDeserializer().fromDataSnapshot(snapshot);
    if (gameListListener != null) {
      gameListListener.onGameAdded(game);
    }
    userGameList.put(game.getId(), game);
  }

  @Override
  public void onChildChanged(DataSnapshot snapshot, String previous) {
    Game game = new GameDeserializer().fromDataSnapshot(snapshot); 
    if (gameListListener != null) {
      gameListListener.onGameChanged(game);
    }
    userGameList.put(game.getId(), game);
  }

  @Override
  public void onChildMoved(DataSnapshot snapshot, String previous) {
  }  

  @Override
  public void onChildRemoved(DataSnapshot snapshot) {
    Game game = new GameDeserializer().fromDataSnapshot(snapshot);
    if (gameListListener != null) {
      gameListListener.onGameRemoved(new GameDeserializer().fromDataSnapshot(snapshot));
    }
    userGameList.remove(game.getId());
  }
  
  /**
   * @return The current {@link GameListPartitions} for this model.
   */
  public GameListPartitions getGameListPartitions() {
    return new GameListPartitions(userId, userGameList.values());
  }
  
  /**
   * @return The total number of games tracked by this model (including ones in
   * the game-over state)
   */
  public int gameCount() {
    return userGameList.size();
  }
  
  /**
   * Partially create a new game with no opponent specified yet, returning the
   * game ID.
   * 
   * @param profiles Map from player IDs in this game to their user profiles.
   * @return The newly created game's ID.
   */
  public String newGame(Map<String, Profile> profiles) {
    return newGame(false /* localMultiplayer */, profiles, Collections.<Profile>emptyList());
  }
  
  /**
   * Create a new local multiplayer game.
   *
   * @param localProfiles List of local profiles for players in this game.
   * @return The newly created game's ID.
   */
  public String newLocalMultiplayerGame(List<Profile> localProfiles) {
    return newGame(true /* localMultiplayer */, Collections.<String, Profile>emptyMap(),
        localProfiles);
  }

  /**
   * Partially create a new game with no opponent specified yet, returning the
   * game ID.
   *
   * @param localMultiplayer Sets whether the game is a local multiplayer game.
   * @param userProfile Map from user IDs to profiles.
   * @param localProfiles List of local profiles for players in this game. 
   * @return The newly created game's ID.
   */  
  private String newGame(boolean localMultiplayer, Map<String, Profile> profiles,
      List<Profile> localProfiles) {
    Firebase ref = firebase.child("games").push();
    Game game = new Game(ref.getName());
    game.getPlayersMutable().add(userId);
    game.setLocalMultiplayer(localMultiplayer);
    if (localMultiplayer) game.getPlayersMutable().add(userId);
    game.setCurrentPlayerNumber(X_PLAYER);
    game.setCurrentActionNumber(null);
    game.setLastModified(Clock.getInstance().currentTimeMillis());
    game.setGameOver(false);
    game.getProfilesMutable().putAll(profiles);
    game.getLocalProfilesMutable().addAll(localProfiles);
    ref.setValue(game.serialize());
    Firebase userRef = userRefForGame(game, userId);
    userRef.setValue(game.minimalGame().serialize());
    return game.getId();
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
    ensureIsNotMinimal(game);
    if (!couldSubmitCommand(game, command)) die("Illegal Command: " + command);
    final long timestamp = Clock.getInstance().currentTimeMillis();
    GameMutation mutation = new GameMutation() {
      @Override public void mutate(Game game) {
        game.setLastModified(timestamp);
        if (game.hasCurrentAction()) {
          Action action = game.currentAction();
          action.getFutureCommandsMutable().clear();
          action.getCommandsMutable().add(command);
        } else {
          Action action = new Action(game.getCurrentPlayerNumber());
          action.setGameId(game.getId());
          action.setSubmitted(false);
          action.getCommandsMutable().add(command);
          game.getActionsMutable().add(action);
          game.setCurrentActionNumber(game.getActions().size() - 1);
        }
      }
    };
    mutateCanonicalGame(game, mutation);
    mutateGameLists(game, new GameMutation() {
      @Override public void mutate(Game game) {
        game.setLastModified(timestamp);        
      }
    });
    mutation.mutate(game);
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
    if (game.hasCurrentAction() && game.currentAction().getCommands().size() > 0) return false;
    boolean res = isLegalCommand(game, command);
    return res;
  }
  
  /**
   * Checks if an undo action is currently possible.
   * 
   * @param game The game to check (possibly null).
   * @return True if a command has been added to the current action of "game"
   *     which can be undone.
   */  
  public boolean canUndo(Game game) {
    ensureIsNotMinimal(game);
    if (game == null || !game.hasCurrentAction()) return false;
    return game.currentAction().getCommands().size() > 0;    
  }
  
  /**
   * Checks if a redo action is currently possible.
   * 
   * @param game The game to check (possibly null).
   * @return True if a command has been added to the "futureCommands" of the
   *     current action of "game" and thus can be redone.
   */  
  public boolean canRedo(Game game) {
    ensureIsNotMinimal(game);
    if (game == null || !game.hasCurrentAction()) return false;
    return game.currentAction().getFutureCommands().size() > 0;    
  }
  
  /**
   * Checks if the current action can be submitted.
   * 
   * @param game The game to check (possibly null).
   * @return True if the current action of "game" is a legal one which could be
   *     submitted. 
   */
  public boolean canSubmit(Game game) {
    ensureIsNotMinimal(game);
    if (game == null || !game.hasCurrentAction()) return false;
    Action action = game.currentAction();
    if (action.getCommands().size() == 0) return false;
    for (Command command : action.getCommands()) {
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
    ensureIsNotMinimal(game);
    if (!canSubmit(game)) die("Illegal action!");
    boolean isXPlayer = game.getCurrentPlayerNumber() == X_PLAYER;
    final long timestamp = Clock.getInstance().currentTimeMillis();
    final int newPlayerNumber = isXPlayer ? O_PLAYER : X_PLAYER; 
    
    // mark move submitted in advance to make computeVictors() work.
    game.currentAction().setSubmitted(true);
    final List<Integer> victors = computeVictors(game);

    GameMutation mutation = new GameMutation() {
      @Override public void mutate(Game game) {
        if (!game.isMinimal()) {
          game.currentAction().setSubmitted(true);
        }
        game.setLastModified(timestamp);
        if (victors == null) {
          game.setCurrentPlayerNumber(newPlayerNumber);
          game.setCurrentActionNumber(null);
        } else {
          // Game over!
          game.setCurrentPlayerNumber(null);
          game.setCurrentActionNumber(null);
          game.getVictorsMutable().addAll(victors);
          game.setGameOver(true);
        }        
    }};
    mutateCanonicalGame(game, mutation);
    mutateGameLists(game, mutation);
    mutation.mutate(game);
    if (gameUpdateListeners.containsKey(game.getId())) {
      gameUpdateListeners.get(game.getId()).onGameStatusChanged(game.gameStatus());
    }
    handleComputerAction(game);
  }

  /**
   * Checks if it is the computer's turn in this game, and performs the
   * appropriate computer move if it is.
   * 
   * @param game Game to check.
   */
  public void handleComputerAction(final Game game) {
    if (game.isGameOver()) return;
    Profile currentProfile = game.playerProfile(game.getCurrentPlayerNumber());
    if (currentProfile.isComputerPlayer()) {
      final ComputerState computerState = new ComputerState();
      computerState.initializeFrom(game);
      int numSimulations;
      switch (currentProfile.getComputerDifficultyLevel()) {
        case 0: {
          // ~70% player win rate
          numSimulations = 5;
          break;
        }
        case 1: {
          // ~50% player win rate
          numSimulations = 100;
          break;
        }
        case 2: {
          // ~10% player win rate
          numSimulations = 1000;
          break;
        }
        default: {
          throw die("Unknown difficulty level");
        }
      }
      final MonteCarloSearch agent = MonteCarloSearch.builder(new ComputerState())
          .setNumSimulations(numSimulations)
          .build();
      int player = computerState.convertPlayerNumber(game.getCurrentPlayerNumber());
      agent.beginAsynchronousSearch(player, computerState);
      Timer timer = new Timer();
      timer.schedule(new TimerTask() {
        @Override public void run() {
          long action = agent.getAsynchronousSearchResult().getAction();
          Command command = computerState.longToCommand(action);
          addCommand(game, command);
          submitCurrentAction(game);
        }
      }, 10000L);
    }
  }
  
  /**
   * Undoes the player's previous command. Throws an exception if there's no
   * previous command to undo.
   * 
   * @param game The game to undo the previous command of.
   */  
  public void undoCommand(Game game) {
    ensureIsCurrentPlayer(game);
    if (!canUndo(game)) die("Can't undo.");
    mutateCanonicalGame(game, new GameMutation() {
      @Override public void mutate(Game newGame) {
        Action action = newGame.currentAction();
        Command command = action.getCommandsMutable().remove(action.getCommands().size() - 1);
        action.getFutureCommandsMutable().add(command);
    }});
  }
  
  /**
   * Re-does the player's previously undone command. Throws an exception if there's no
   * previous command to redo.
   * 
   * @param game The game to redo the previous command of.
   */
  public void redoCommand(Game game) {
    ensureIsCurrentPlayer(game);
    if (!canRedo(game)) die("Can't redo.");
    mutateCanonicalGame(game, new GameMutation() {
      @Override public void mutate(Game newGame) {
        Action action = newGame.currentAction();
        Command command = action.getFutureCommandsMutable().remove(action.getFutureCommands().size() - 1);
        action.getCommandsMutable().add(command);
    }});
  }
  
  /**
   * Leave a game. In a 2-player game, this means your opponent wins.
   * 
   * @param game Game to resign from.
   */
  public void resignGame(Game game) {
    ensureIsPlayer(game);
    if (game.isGameOver()) die("Can't resign from a game which is already over");
    final long timestamp = Clock.getInstance().currentTimeMillis();
    GameMutation mutation = new GameMutation() {
      @Override public void mutate(Game game) {
        game.getResignedPlayersMutable().add(game.getCurrentPlayerNumber());
        game.setGameOver(true);
        game.setCurrentActionNumber(null);
        if (game.isLocalMultiplayer() && game.getPlayers().size() == 2) {
          game.getVictorsMutable().add(game.getCurrentPlayerNumber() == 0 ? 1 : 0);
        } else if (game.hasOpponent(userId)) {
          int opponentPlayerNumber = game.opponentPlayerNumber(userId);          
          game.getVictorsMutable().add(opponentPlayerNumber);
        }
        game.setCurrentPlayerNumber(null);
        game.setLastModified(timestamp);
      }
    };
    mutateCanonicalGame(game, mutation);
    mutateGameLists(game, mutation);
  }
  
  public void archiveGame(Game game) {
    ensureIsPlayer(game);
    if (!game.isGameOver()) die("Can't archive a game which is in progress");
    userRefForGame(game, userId).removeValue();
    userGameList.remove(game.getId());
  }
  
  /**
   * Builds the "victors" array for the game. If the game is over, a list will be
   * returned containing the victorious or drawing players (which may be empty to
   * indicate that "nobody wins"). Otherwise, null is returned.
   * 
   * @param game The game to find the victors for
   * @return A list of player numbers of victors or null if the game is not
   *     over.
   */
  // Visible for testing only
  List<Integer> computeVictors(Game game) {
    ensureIsNotMinimal(game);
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
          action1.getPlayerNumber() == action2.getPlayerNumber() &&
          action2.getPlayerNumber() == action3.getPlayerNumber()) {
        List<Integer> result = new ArrayList<Integer>();
        result.add(action1.getPlayerNumber());
        return result;
      }
    }
    
    // 2) check for draw
    
    int submitted = 0;
    for (Action action : game.getActions()) {
      if (action.isSubmitted()) submitted++;
    }
    if (submitted == 9) {
      List<Integer> both = new ArrayList<Integer>();
      both.add(0);
      both.add(1);
      return both;
    }
    
    // 3) game is not over
    return null;
  }
  
  
  /**
   * Returns true if the square at (column, row) is available. 
   */  
  private boolean isLegalCommand(Game game, Command command) {
    int column = command.getColumn();
    int row = command.getRow(); 
    if (column < 0 || row < 0 || column > 2 || row > 2) {
      return false;
    }
    return makeActionTable(game)[column][row] == null;    
  }
  
  /** 
   * Returns a 2-dimensional map of *submitted* game actions spatially indexed
   * by [column][row], so e.g. table[0][2] is the bottom-left square's action. 
   */  
  private Action[][] makeActionTable(Game game) {
    Action[][] result = new Action[3][3];
    for (Action action : game.getActions()) {
      if (action.isSubmitted()) {
        for (Command command : action.getCommands()) {
          result[command.getColumn()][command.getRow()] = action;
        }
      }
    }
    return result;
  }
  
  /**
   * Runs a transaction to mutate the provided game via the provided function
   * under /games/<gameid>. 
   * 
   * @param game Game to mutate.
   * @param function Mutation function to employ
   */
  private void mutateCanonicalGame(Game game, final GameMutation function) {
    Firebase gameRef = firebase.child("games").child(game.getId());
    gameRef.runTransaction(new GameMutationHandler(function, false /* useMinimalForm */));
  }

  /**
   * Runs a transaction to mutate the provided game in the game list for each
   * player in the game, under /users/<userid>/games/<gameid>/.
   *
   * @param game Game to mutate.
   * @param function Mutation function to employ.
   */
  private void mutateGameLists(Game game, final GameMutation function) {
    for (String player : new HashSet<String>(game.getPlayers())) {
      Firebase userRef = userRefForGame(game, player);
      userRef.runTransaction(new GameMutationHandler(function, true /* useMinimalForm */));
    }
    if (userGameList.containsKey(game.getId())) {
      function.mutate(userGameList.get(game.getId()));
    }
  }
  
  /**
   * @param game A game.
   * @return The Firebase reference for this game in the current user's game list.
   */
  private Firebase userRefForGame(Game game, String userId) {
    return firebase.child("users").child(userId).child("games").child(game.getId());
  }
  
  /**
   * @param gameId A game ID
   * @return A Firebase reference for this game in the master game list.
   */
  private Firebase gameReference(String gameId) {
    return firebase.child("games").child(gameId);
  }
  
  /**
   * Throws an exception.
   */  
  private RuntimeException die(String message) {
    throw new RuntimeException(message);
  }

  /**
   * Ensures the current user is the current player in the provided game.
   */
  void ensureIsCurrentPlayer(Game game) {
    if (!isCurrentPlayer(game)) die("Unauthorized user: " + userId);
  }
  
  /**
   * Ensures the current user is a player in the provided game.
   */
  void ensureIsPlayer(Game game) {
    if (!game.getPlayers().contains(userId)) die("Unauthorized user: " + userId);
  }
  
  /**
   * Ensures the provided game is not in minimal form.
   */
  void ensureIsNotMinimal(Game game) {
    if (game.isMinimal()) die("Unexpected minimal game");
  }
}
