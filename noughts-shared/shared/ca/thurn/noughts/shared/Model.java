package ca.thurn.noughts.shared;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;

import ca.thurn.noughts.shared.entities.Action;
import ca.thurn.noughts.shared.entities.Command;
import ca.thurn.noughts.shared.entities.Game;
import ca.thurn.noughts.shared.entities.Profile;
import ca.thurn.uct.algorithm.MonteCarloSearch;

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
public class Model extends AbstractChildEventListener {
  public static final int X_PLAYER = 0;
  public static final int O_PLAYER = 1;

  /**
   * Function to mutate a game.
   */
  private static abstract class GameMutation {
    /**
     * @param game Game to mutate.
     */
    public abstract void mutate(Game.Builder gameBuilder);
    
    public void onComplete(Game game) {
    }
  }
  
  /**
   * Handler implementation for mutating games.
   */
  private static class GameMutationHandler implements Handler {
    private final GameMutation function;
    private final Game original;
    
    /**
     * @param function Mutation function to use.
     */
    public GameMutationHandler(GameMutation function) {
      this(function, null);
    }
    
    /**
     * @param function Mutation function to use.
     * @param original The original value of the game you are mutating.
     *     Specifying this argument causes the mutation to be aborted if the
     *     current value of the game at this location is different from
     *     original.
     */    
    public GameMutationHandler(GameMutation function, Game original) {
      this.function = function;
      this.original = original;
    }
    
    @Override
    public void onComplete(FirebaseError error, boolean done, DataSnapshot snapshot) {
      if (snapshot.getValue() != null) {
        this.function.onComplete(Game.newDeserializer().fromDataSnapshot(snapshot));
      }
    }

    @Override
    public Result doTransaction(MutableData mutableData) {
      if (mutableData.getValue() == null) {
        // Local updates sometimes cause transactions to be run with null
        // for the current value. It is safe to ignore these.
        return Transaction.success(mutableData);
      }
      Game deserialized = Game.newDeserializer().fromMutableData(mutableData);
      if (original != null && !original.equals(deserialized)) {
        return Transaction.abort();
      }
      Game game = applyMutation(deserialized, function);
      mutableData.setValue(game.serialize());
      return Transaction.success(mutableData);
    }   
  }

  private final String userId;
  private final String userKey;
  private final Firebase firebase;
  private GameListUpdateListener gameListUpdateListener;
  private final Map<String, ValueEventListener> valueEventListeners;
  private final Map<String, GameUpdateListener> gameUpdateListeners;
  private final Map<String, CommandUpdateListener> commandUpdateListeners;
  private final Map<String, Game> games;
  private boolean isComputerThinking = false;
  
  public Model(String userId, String userKey, Firebase firebase) {
    this.userId = userId;
    this.userKey = userKey;
    this.firebase = firebase;
    valueEventListeners = new HashMap<String, ValueEventListener>();
    gameUpdateListeners = new HashMap<String, GameUpdateListener>();
    commandUpdateListeners = new HashMap<String, CommandUpdateListener>();
    games = new HashMap<String, Game>();
    userReference().child("games").addChildEventListener(this);
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
    return Games.currentPlayerId(game).equals(userId);
  }

  /**
   * Adds a GameListUpdateListener, overriding any previous listener.
   *
   * @param listener Listener to add.
   */
  public void setGameListUpdateListener(GameListUpdateListener listener) {
    gameListUpdateListener = listener;
  }
  
  /**
   * Adds a GameUpdateListener for the indicated game. Overwrites any
   * previously added game update listener.
   *
   * @param gameId The ID of the game.
   * @param listener The listener to add.
   */
  public void setGameUpdateListener(String gameId, GameUpdateListener listener) {
    gameUpdateListeners.put(gameId, listener);
    if (games.containsKey(gameId)) {
      Game game = games.get(gameId);
      listener.onGameUpdate(game);
      listener.onGameStatusChanged(Games.gameStatus(game));
    }
  }
  
  /**
   * Adds a listener to be notified when commands are performed in the specified game.
   *
   * @param gameId ID of game to listen on.
   * @param listener The listener to add.
   */
  public void setCommandUpdateListener(String gameId, CommandUpdateListener listener) {
    commandUpdateListeners.put(gameId, listener);
    if (games.containsKey(gameId)) {
      listener.onRegistered(userId, games.get(gameId));
    }
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
   * Removes all Firebase listeners for this model. Should generally not be needed.
   */
  public void removeAllFirebaseListeners() {
    for (Entry<String, ValueEventListener> entry : valueEventListeners.entrySet()) {
      firebase.child("games").child(entry.getKey()).removeEventListener(entry.getValue());
    }
  }

  @Override
  public void onChildAdded(DataSnapshot snapshot, String previous) {
    String gameId = snapshot.getName();
    ValueEventListener valueEventListener = new ValueEventListener() {
      @Override
      public void onCancelled(FirebaseError error) {
      }

      @Override
      public void onDataChange(DataSnapshot snapshot) {
        if (snapshot.getValue() != null) {
          Game game = Game.newDeserializer().fromDataSnapshot(snapshot);
          Game oldGame = games.get(game.getId());
          games.put(game.getId(), game);
          fireListeners(snapshot, oldGame);
        }
      }
    };
    valueEventListeners.put(gameId, valueEventListener);
    firebase.child("games").child(gameId).addValueEventListener(valueEventListener);
  }

  @Override
  public void onChildRemoved(DataSnapshot snapshot) {
    String gameId = snapshot.getName();
    if (valueEventListeners.containsKey(gameId)) {
      firebase.child("games").child(gameId).removeEventListener(valueEventListeners.get(gameId));
    }
  }

  private Game fireListeners(DataSnapshot snapshot, Game oldGame) {
    Game game = Game.newDeserializer().fromDataSnapshot(snapshot);
    String gameId = game.getId();
    if (gameListUpdateListener != null) {
      if (oldGame == null) {
        gameListUpdateListener.onGameAdded(game);
      } else {
        gameListUpdateListener.onGameChanged(game);
      }
    }
    if (gameUpdateListeners.containsKey(gameId)) {
      GameUpdateListener gameListener = gameUpdateListeners.get(gameId);
      if (oldGame == null || !game.equals(oldGame)) {
        gameListener.onGameUpdate(game);
      }
      if (oldGame == null || Games.differentStatus(game, oldGame)) {
        gameListener.onGameStatusChanged(Games.gameStatus(game));              
      }      
    }
    if (commandUpdateListeners.containsKey(gameId)) {
      CommandUpdateListener listener = commandUpdateListeners.get(gameId);
      if (oldGame == null) {
        listener.onRegistered(userId, game);
      } else {
        Map<Command, Action> added = Games.commandsAdded(oldGame, game);
        for (Entry<Command, Action> entry : added.entrySet()) {
          listener.onCommandAdded(entry.getValue(), entry.getKey());
        }
        Map<Command, Action> removed = Games.commandsRemoved(oldGame, game);
        for (Entry<Command, Action> entry : removed.entrySet()) {
          listener.onCommandRemoved(entry.getValue(), entry.getKey());
        }
        if (Games.currentCommandChanged(oldGame, game)) {
          Action oldAction = oldGame.getCurrentAction();
          Action newAction = game.getCurrentAction();
          listener.onCommandChanged(newAction, oldAction.getCommand(oldAction.getCommandCount() - 1),
              newAction.getCommand(newAction.getCommandCount() - 1));
        }
        Map<Command, Action> submitted = Games.commandsSubmitted(oldGame, game);
        for (Entry<Command, Action> entry : submitted.entrySet()) {
          listener.onCommandSubmitted(entry.getValue(), entry.getKey());
        }
        if (game.isGameOver() && !oldGame.isGameOver()) {
          listener.onGameOver(game);
        }
      }
    }
    return game;
  }
  
  /**
   * @return The current {@link GameListPartitions} for this model.
   */
  public GameListPartitions getGameListPartitions() {
    return new GameListPartitions(userId, games.values());
  }
  
  /**
   * @return The total number of games tracked by this model (including ones in
   * the game-over state)
   */
  public int gameCount() {
    return games.size();
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
    Game.Builder builder = Game.newBuilder();
    builder.setId(ref.getName());
    builder.addPlayer(userId);
    builder.setIsLocalMultiplayer(localMultiplayer);
    if (localMultiplayer) builder.addPlayer(userId);
    builder.setCurrentPlayerNumber(X_PLAYER);
    builder.setLastModified(Clock.getInstance().currentTimeMillis());
    builder.setIsGameOver(false);
    builder.putAllProfile(profiles);
    builder.addAllLocalProfile(localProfiles);
    Game game = builder.build();
    ref.setValue(game.serialize());
    Firebase userRef = userReference().child("games").child(game.getId());
    userRef.setValue(true);
    return game.getId();
  }
  
  /**
   * Add and submit the provided command.
   * 
   * @param game Game to add and submit command for
   * @param command Command to add and submit.
   */
  public void addCommandAndSubmit(Game game, Command command) {
    addCommandAndSubmit(game, command, null /* onComplete */);
  }
  
  /**
   * Adds the provided command as in {@link Model#addCommand(Game, Command)}
   * and also submits the resulting action as in {@link
   * Model#submitCurrentAction(Game)}. Note that this will fire multiple
   * game updates for each step, pass a completion function to execute code
   * when the submit is finished.
   *
   * @param game The game.
   * @param command Command to add.
   * @param onComplete Optionally, a function to invoke when the command is submitted.
   */
  public void addCommandAndSubmit(Game game, Command command, OnMutationCompleted onComplete) {
    addCommand(game, command, true /* submit */, onComplete);
  }

  /**
   * Adds the provided command to the current action's command list. If there
   * is no current action, creates one. Any commands beyond the current
   * location in the undo history are deleted.
   *
   * @param game The current game.
   * @param command The command to add.
   * @return The game with the command added.
   */  
  public void addCommand(Game game, Command command) {
    addCommand(game, command, false /* submit */, null /* onComplete */);
  }
  
  /**
   * Add the provided command to the game, optionally also submitting the command.
   */
  private void addCommand(final Game game, final Command command, final boolean submit,
      final OnMutationCompleted onComplete) {
    ensureIsCurrentPlayer(game);
    if (!couldSubmitCommand(game, command)) die("Illegal Command: " + command);
    final long timestamp = Clock.getInstance().currentTimeMillis();
    GameMutation mutation = new GameMutation() {
      @Override public void mutate(Game.Builder game) {
        game.setLastModified(timestamp);
        if (game.hasCurrentAction()) {
          Action.Builder action = game.getCurrentAction().toBuilder();
          action.clearFutureCommandList();
          action.addCommand(command);
          game.setCurrentAction(action);
        } else {
          Action.Builder action = Action.newBuilder();
          action.setPlayerNumber(game.getCurrentPlayerNumber());
          action.setGameId(game.getId());
          action.setIsSubmitted(false);
          action.addCommand(command);
          game.setCurrentAction(action);
        }
      }
      
      @Override public void onComplete(Game game) {
        if (submit) {
          submitCurrentAction(game, onComplete);
        }
      }
    };
    mutateCanonicalGame(game, mutation, true /* abortOnConflict */);
  }

  /**
   * Replaces the last command of the current action of the provided game with
   * the provided command. The current action must exist and already have one
   * or more commands.
   * 
   * @param game Game to modify the last command of.
   * @param command New value to use as the game's last command.
   */
  public void updateLastCommand(Game game, final Command command) {
    ensureIsCurrentPlayer(game);
    if (!couldUpdateLastCommand(game, command)) die("Illegal Command: " + command);   
    final long timestamp = Clock.getInstance().currentTimeMillis();
    GameMutation mutation = new GameMutation() {
      @Override public void mutate(Game.Builder game) {
        game.setLastModified(timestamp);
        Action.Builder currentAction = game.getCurrentAction().toBuilder();
        currentAction.getCommandList().remove(currentAction.getCommandCount() - 1);
        currentAction.addCommand(command);
        game.setCurrentAction(currentAction);
      }
    };
    mutateCanonicalGame(game, mutation, true /* abortOnConflict */);
  }
  
  /**
   * Checks if the game's last command could be legally updated to a new value.
   * 
   * @param game The game to check.
   * @param command The proposed new value for the game's last command.
   * @return True if updating the game's last command by
   *     {@link Model#updateLastCommand(Game, Command)} would produce a legal
   *     game state.
   */
  public boolean couldUpdateLastCommand(Game game, Command command) {
    if (!isCurrentPlayer(game)) return false;
    if (!game.hasCurrentAction() || game.getCurrentAction().getCommandCount() == 0) return false;
    Action.Builder currentAction = game.getCurrentAction().toBuilder();
    currentAction.getCommandList().remove(currentAction.getCommandCount() - 1);
    currentAction.addCommand(command);
    Game newGame = game.toBuilder().setCurrentAction(currentAction).build();
    return isLegalCommand(newGame, command);
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
    if (game.hasCurrentAction() && game.getCurrentAction().getCommandCount() > 0) return false;
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
    return game.getCurrentAction().getCommandCount() > 0;    
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
    return game.getCurrentAction().getFutureCommandCount() > 0;    
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
    Action action = game.getCurrentAction();
    if (action.getCommandCount() == 0) return false;
    for (Command command : action.getCommandList()) {
      if (!isLegalCommand(game, command)) return false;
    }
    return true;
  }

  /**
   * Version of {@link Model#submitCurrentAction(Game, OnMutationCompleted)}
   * with no completion function.
   * 
   * @param game The game to submit the current action of.
   */
  public void submitCurrentAction(Game game) {
    submitCurrentAction(game, null);
  }
  
  /**
   * Submits the provided game's current action, if it is a legal one. If this
   * ends the game: populates the "victors" array and sets the "gameOver"
   * bit. Otherwise, updates the current player.
   * 
   * @param game The game to submit the current action of.
   * @param onComplete Optionally, a function to invoke when the action is
   *     submitted.
   * @return The game with the action submitted.
   */  
  public void submitCurrentAction(Game game, final OnMutationCompleted onComplete) {
    ensureIsCurrentPlayer(game);
    if (!canSubmit(game)) die("Illegal action!");
    boolean isXPlayer = game.getCurrentPlayerNumber() == X_PLAYER;
    final long timestamp = Clock.getInstance().currentTimeMillis();
    final int newPlayerNumber = isXPlayer ? O_PLAYER : X_PLAYER; 
    final List<Integer> victors = computeVictorsIfCurrentActionSubmitted(game);
    GameMutation mutation = new GameMutation() {
      @Override public void mutate(Game.Builder game) {
        game.addSubmittedAction(game.getCurrentAction().toBuilder().setIsSubmitted(true));
        game.setLastModified(timestamp);
        game.clearCurrentAction();
        if (victors == null) {
          game.setCurrentPlayerNumber(newPlayerNumber);
        } else {
          // Game over!
          game.clearCurrentPlayerNumber();
          game.addAllVictor(victors);
          game.setIsGameOver(true);
        }
      }
      
      @Override public void onComplete(Game game) {
        if (onComplete != null) {
          onComplete.onMutationCompleted(game);
        }
      }
    };
    mutateCanonicalGame(game, mutation, true /* abortOnConflict */);
    handleComputerAction(applyMutation(game, mutation));
  }
  
  /**
   * Checks if it is the computer's turn in this game, and performs the
   * appropriate computer move if it is.
   * 
   * @param game Game to check.
   */
  public void handleComputerAction(final Game game) {
    if (game.isGameOver() || isComputerThinking) return;
    Profile currentProfile = Games.playerProfile(game, game.getCurrentPlayerNumber());
    if (currentProfile.isComputerPlayer()) {
      isComputerThinking = true;
      final ComputerState computerState = new ComputerState();
      computerState.initializeFrom(new ComputerState.GameInitializer(game));
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
          addCommandAndSubmit(game, command, new OnMutationCompleted() {
            @Override
            public void onMutationCompleted(Game game) {
              isComputerThinking = false;
            }
          });
        }
      }, 4000L);
    }
  }
  
  /**
   * Undoes the player's previous command. Throws an exception if there's no
   * previous command to undo.
   * 
   * @param game The game to undo the previous command of.
   * @return The game with the command undone.
   */  
  public void undoCommand(Game game) {
    ensureIsCurrentPlayer(game);
    if (!canUndo(game)) die("Can't undo.");
    GameMutation mutation = new GameMutation() {
      @Override public void mutate(Game.Builder game) {
        Action.Builder action = game.getCurrentAction().toBuilder();
        Command command = action.getCommandList().remove(action.getCommandList().size() - 1);
        action.addFutureCommand(command);
        game.setCurrentAction(action);
    }};
    mutateCanonicalGame(game, mutation, true /* abortOnConflict */);
  }
  
  /**
   * Re-does the player's previously undone command. Throws an exception if there's no
   * previous command to redo.
   * 
   * @param game The game to redo the previous command of.
   * @return The game with the command redone.
   */
  public void redoCommand(Game game) {
    ensureIsCurrentPlayer(game);
    if (!canRedo(game)) die("Can't redo.");
    GameMutation mutation = new GameMutation() {
      @Override public void mutate(Game.Builder game) {
        Action.Builder action = game.getCurrentAction().toBuilder();
        Command command = action.getFutureCommandList().remove(
            action.getFutureCommandCount() - 1);
        action.addCommand(command);
        game.setCurrentAction(action);
    }};
    mutateCanonicalGame(game, mutation, true /* abortOnCoflict */);
  }
  
  /**
   * Leave a game. In a 2-player game, this means your opponent wins.
   * 
   * @param game Game to resign from.
   * @return The game after resignation.
   */
  public void resignGame(Game game) {
    ensureIsPlayer(game);
    if (game.isGameOver()) die("Can't resign from a game which is already over");
    final long timestamp = Clock.getInstance().currentTimeMillis();
    GameMutation mutation = new GameMutation() {
      @Override public void mutate(Game.Builder game) {
        game.addResignedPlayer(game.getCurrentPlayerNumber());
        game.setIsGameOver(true);
        game.clearCurrentAction();
        if (game.isLocalMultiplayer() && game.getPlayerCount() == 2) {
          game.addVictor(game.getCurrentPlayerNumber() == 0 ? 1 : 0);
        } else if (Games.hasOpponent(game.build(), userId)) {
          int opponentPlayerNumber = Games.opponentPlayerNumber(game.build(), userId);          
          game.addVictor(opponentPlayerNumber);
        }
        game.clearCurrentPlayerNumber();
        game.setLastModified(timestamp);
      }
    };
    mutateCanonicalGame(game, mutation, false /* abortOnConflict */);
  }
  
  /**
   * Remove a game from a user's game list.
   *
   * @param game The game to archive.
   */
  public void archiveGame(Game game) {
    ensureIsPlayer(game);
    if (!game.isGameOver()) die("Can't archive a game which is in progress");
    userReference().child("games").child(game.getId()).removeValue();
    games.remove(game.getId());
  }
  
  /**
   * Builds the "victors" array for the game. If the game is over, a list will be
   * returned containing the victorious or drawing players (which may be empty to
   * indicate that "nobody wins"). Otherwise, null is returned. The computation
   * is done *as if* the current action were submitted.
   * 
   * @param game The game to find the victors for
   * @return A list of player numbers of victors or null if the game is not
   *     over.
   */
  // Visible for testing only
  List<Integer> computeVictorsIfCurrentActionSubmitted(Game game) {
    // 1) check for win
    
    Action[][] actionTable = makeActionTable(game, true /* includeCurrent */);
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
    for (Action action : game.getSubmittedActionList()) {
      if (action.isSubmitted()) submitted++;
    }
    if (submitted >= 8) {
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
    return makeActionTable(game, false /* includeCurrent */)[column][row] == null;    
  }
  
  /** 
   * Returns a 2-dimensional map of game actions spatially indexed by
   * [column][row], so e.g. table[0][2] is the bottom-left square's action.
   * If includeCurrent is true, the game's current action will also be included
   * in the action table.
   */  
  private Action[][] makeActionTable(Game game, boolean includeCurrent) {
    Action[][] result = new Action[3][3];
    for (Action action : game.getSubmittedActionList()) {
      for (Command command : action.getCommandList()) {
        result[command.getColumn()][command.getRow()] = action;
      }
    }
    if (includeCurrent) {
      for (Command command : game.getCurrentAction().getCommandList()) {
        result[command.getColumn()][command.getRow()] = game.getCurrentAction();
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
   * @param abortOnConflict If true, abort the transaction if the current game
   *     at this ID is different from "game".
   */
  private void mutateCanonicalGame(Game game, final GameMutation function,
      boolean abortOnConflict) {
    Firebase gameRef = firebase.child("games").child(game.getId());
    GameMutationHandler handler = abortOnConflict ? 
        new GameMutationHandler(function, game) :
          new GameMutationHandler(function);
    gameRef.runTransaction(handler);
  }

  private static Game applyMutation(Game game, GameMutation mutation) {
    Game.Builder builder = game.toBuilder();
    mutation.mutate(builder);
    return builder.build();
  }
  
  /**
   * @param gameId A game ID
   * @return A Firebase reference for this game in the master game list.
   */
  private Firebase gameReference(String gameId) {
    return firebase.child("games").child(gameId);
  }
  
  private Firebase userReference() {
    return firebase.child("users").child(userKey);
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
    if (!game.getPlayerList().contains(userId)) die("Unauthorized user: " + userId);
  }
}
