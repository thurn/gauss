package ca.thurn.noughts.shared;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Nullable;

import ca.thurn.noughts.shared.entities.Action;
import ca.thurn.noughts.shared.entities.Command;
import ca.thurn.noughts.shared.entities.Game;
import ca.thurn.noughts.shared.entities.Profile;
import ca.thurn.noughts.shared.entities.Pronoun;
import ca.thurn.uct.algorithm.MonteCarloSearch;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.GenericTypeIndicator;
import com.firebase.client.MutableData;
import com.firebase.client.Transaction;
import com.firebase.client.ValueEventListener;
import com.google.common.annotations.VisibleForTesting;

/**
 * The data model for noughts.
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
   * Function to mutate an action.
   */
  private static abstract class ActionMutation {
    /**
     * Performs the mutation.
     *
     * @param action Action to mutate.
     */
    public abstract void mutate(Action.Builder action);

    /**
     * Called when mutation commits.
     *
     * @param action Final action.
     */
    public void onComplete(Action action) {
    }
  }

  private String userId;
  private String userKey;
  private boolean isFacebookUser;
  private final Firebase firebase;
  private GameListListener gameListListener;
  private final PushNotificationService pushNotificationService;
  private final AnalyticsService analyticsService;
  private ChildEventListener gameChildEventListener;
  private final Map<String, ValueEventListener> valueEventListeners;
  private final Map<String, GameUpdateListener> gameUpdateListeners;
  private final Map<String, CommandUpdateListener> commandUpdateListeners;
  private final Map<String, Action> currentActions;
  private final Map<String, Game> games;
  private boolean isComputerThinking = false;

  public static Model anonymousModel(String userId, String userKey, Firebase firebase,
      PushNotificationService pushNotificationService, AnalyticsService analyticsService) {
    return new Model(userId, userKey, false /* isFacebookUser */, firebase,
        pushNotificationService, analyticsService);
  }

  public static Model facebookModel(String facebookId, Firebase firebase,
      PushNotificationService pushNotificationService, AnalyticsService analyticsService) {
    return new Model(facebookId, facebookId, true /* isFacebookuser */, firebase,
        pushNotificationService, analyticsService);
  }

  private Model(String userId, String userKey, boolean isFacebookUser, Firebase firebase,
      PushNotificationService pushNotificationService, AnalyticsService analyticsService) {
    this.userId = userId;
    this.userKey = userKey;
    this.isFacebookUser = isFacebookUser;
    this.firebase = firebase;
    this.pushNotificationService = pushNotificationService;
    this.analyticsService = analyticsService;
    valueEventListeners = new HashMap<String, ValueEventListener>();
    gameUpdateListeners = new HashMap<String, GameUpdateListener>();
    commandUpdateListeners = new HashMap<String, CommandUpdateListener>();
    currentActions = new HashMap<String, Action>();
    games = new HashMap<String, Game>();
    gameChildEventListener = userGamesReference().addChildEventListener(this);
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
    return Games.hasCurrentPlayerId(game) && Games.currentPlayerId(game).equals(userId);
  }

  /**
   * Adds a GameListListener, overriding any previous listener.
   *
   * @param listener Listener to add.
   */
  public void setGameListListener(GameListListener listener) {
    gameListListener = listener;
  }

  /**
   * See {@link Model#setGameUpdateListener(String, GameUpdateListener, boolean)}.
   */
  public void setGameUpdateListener(String gameId, GameUpdateListener listener) {
    setGameUpdateListener(gameId, listener, true /* immediate */);
  }

  /**
   * Adds a GameUpdateListener for the indicated game. Overwrites any
   * previously added game update listener.
   *
   * @param gameId The ID of the game.
   * @param listener The listener to add.
   * @param immediate Whether or not to fire the listener immediately with known
   *     current values. Defaults to true.
   */
  public void setGameUpdateListener(String gameId, GameUpdateListener listener, boolean immediate) {
    gameUpdateListeners.put(gameId, listener);
    if (immediate) {
      if (games.containsKey(gameId)) {
        Game game = games.get(gameId);
        fireListeners(game, null /* oldGame */);
      }
      if (currentActions.containsKey(gameId)) {
        listener.onCurrentActionUpdate(getCurrentAction(gameId));
      }
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
        throw new RuntimeException();
      }

      @Override
      public void onDataChange(DataSnapshot snapshot) {
        if (snapshot.getValue() != null) {
          Game game = Game.newDeserializer().fromDataSnapshot(snapshot);
          Game oldGame = games.get(game.getId());
          games.put(game.getId(), game);
          fireListeners(game, oldGame);
        }
      }
    };
    ValueEventListener added =
        firebase.child("games").child(gameId).addValueEventListener(valueEventListener);
    valueEventListeners.put(gameId, added);
    processCurrentActionUpdate(snapshot);
  }

  @Override
  public void onChildChanged(DataSnapshot snapshot, String previous) {
    processCurrentActionUpdate(snapshot);
  }

  private void processCurrentActionUpdate(DataSnapshot snapshot) {
    String gameId = snapshot.getName();
    if (!snapshot.hasChild("currentAction")) die("No current action for game %s", gameId);
    Map<String, Object> value = snapshot.child("currentAction").getValue(
        new GenericTypeIndicator<Map<String, Object>>(){});
    Action action = Action.newDeserializer().deserialize(value);
    if (commandUpdateListeners.containsKey(gameId)) {
      fireCurrentActionListeners(gameId, action, commandUpdateListeners.get(gameId));
    }
    currentActions.put(gameId, action);
    if (gameUpdateListeners.containsKey(gameId)) {
      gameUpdateListeners.get(gameId).onCurrentActionUpdate(action);
    }
  }

  @Override
  public void onChildRemoved(DataSnapshot snapshot) {
    String gameId = snapshot.getName();
    if (gameListListener != null) {
      gameListListener.onGameRemoved(gameId);
    }
    if (valueEventListeners.containsKey(gameId)) {
      firebase.child("games").child(gameId).removeEventListener(valueEventListeners.get(gameId));
    }
    currentActions.remove(gameId);
  }

  private void fireCurrentActionListeners(String gameId, Action action,
      CommandUpdateListener listener) {
    Action old = currentActions.get(gameId);
    if (old == null || !action.hasPlayerNumber()) {
      // No player number indicates a newly created action
      for (Command command : action.getCommandList()) {
        listener.onCommandAdded(action, command);
      }
    } else if (old.getCommandCount() > action.getCommandCount()) {
      int count = old.getCommandCount();
      while (count > action.getCommandCount()) {
        listener.onCommandRemoved(action, old.getCommand(count - 1));
        count--;
      }
    } else if (old.getCommandCount() < action.getCommandCount()) {
      int count = action.getCommandCount();
      while (count > old.getCommandCount()) {
        listener.onCommandAdded(action, action.getCommand(count - 1));
        count--;
      }
    } else if (old.getCommandCount() > 0 && action.getCommandCount() > 0) {
      Command oldCommand = old.getCommand(old.getCommandCount() - 1);
      Command newCommand = action.getCommand(action.getCommandCount() - 1);
      if (!oldCommand.equals(newCommand)) {
        listener.onCommandChanged(action, oldCommand, newCommand);
      }
    }
  }

  private Game fireListeners(Game game, Game oldGame) {
    String gameId = game.getId();
    if (gameUpdateListeners.containsKey(gameId)) {
      GameUpdateListener gameListener = gameUpdateListeners.get(gameId);
      if (oldGame == null || !game.equals(oldGame)) {
        gameListener.onGameUpdate(game);
      }
      if (game != null && !game.equals(oldGame) && profileRequired(game)) {
        String name = null;
        if (Games.viewerProfile(game, userId).hasName()) {
          name = Games.viewerProfile(game, userId).getName();
        }
        gameListener.onProfileRequired(gameId, name);
      }
      if (oldGame == null || Games.differentStatus(game, oldGame)) {
        gameListener.onGameStatusChanged(Games.gameStatus(game));
      }
    }
    if (commandUpdateListeners.containsKey(gameId)) {
      CommandUpdateListener listener = commandUpdateListeners.get(gameId);
      if (oldGame == null) {
        if (currentActions.containsKey(gameId)) {
          listener.onRegistered(userId, game, getCurrentAction(gameId));
        }
      } else {
        if (game.isGameOver() && !oldGame.isGameOver()) {
          listener.onGameOver(game);
        }
        int count = game.getSubmittedActionCount();
        while (count > oldGame.getSubmittedActionCount()) {
          Action submitted = game.getSubmittedAction(count - 1);
          boolean byViewer = game.isLocalMultiplayer() ||
              Games.playerNumberForPlayerId(game, userId) == submitted.getPlayerNumber();
          listener.onActionSubmitted(submitted, byViewer);
          count--;
        }
      }
    }
    if (gameListListener != null) {
      if (oldGame == null) {
        gameListListener.onGameAdded(game);
      } else if (!game.equals(oldGame)) {
        gameListListener.onGameChanged(game);
      }
    }
    return game;
  }

  /**
   * @param game A game.
   * @return True if the viewer is a player in this game and does not have a
   *     profile yet.
   */
  private boolean profileRequired(Game game) {
    if (game.isGameOver() || game.isLocalMultiplayer()) return false;
    return !game.getProfile(Games.playerNumberForPlayerId(game, userId)).hasImageString();
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
   * @return An ID to use for a game which may be created in the future.
   */
  public String getPreliminaryGameId() {
    return firebase.child("games").push().getName();
  }

  /**
   * Version of {@link Model#newGame(Map, String)} with no game ID specified.
   */
  public String newGame(List<Profile> profiles) {
    return newGame(profiles, null /* gameId */);
  }

  /**
   * Partially create a new game with no opponent specified yet, returning the
   * game ID.
   *
   * @param profiles Map from player IDs in this game to their user profiles.
   * @param gameId Optionally, the game ID to use.
   * @return The newly created game's ID.
   */
  public String newGame(List<Profile> profiles, String gameId) {
    return newGame(false /* localMultiplayer */, profiles, gameId);
  }

  /**
   * Create a new local multiplayer game.
   *
   * @param localProfiles List of local profiles for players in this game.
   * @return The newly created game's ID.
   */
  public String newLocalMultiplayerGame(List<Profile> profiles) {
    return newGame(true /* localMultiplayer */, profiles, null /* gameId */);
  }

  /**
   * Partially create a new game with no opponent specified yet, returning the
   * game ID.
   *
   * @param localMultiplayer Sets whether the game is a local multiplayer game.
   * @param userProfile Map from user IDs to profiles.
   * @param profiles List of local profiles for players in this game.
   * @param gameId Optionally, the game ID to use.
   * @return The newly created game's ID.
   */
  private String newGame(boolean localMultiplayer, List<Profile> profiles, String gameId) {
    Firebase ref;
    if (gameId == null) {
      ref = firebase.child("games").push();
    } else {
      ref = gameReference(gameId);
    }

    Game.Builder builder = Game.newBuilder();
    builder.setId(ref.getName());
    builder.addPlayer(userId);
    builder.setIsLocalMultiplayer(localMultiplayer);
    if (localMultiplayer) builder.addPlayer(userId);
    builder.setCurrentPlayerNumber(X_PLAYER);
    builder.setLastModified(Clock.getInstance().currentTimeMillis());
    builder.setIsGameOver(false);
    for (int i = 0; i < 2; ++i) {
      if (i < profiles.size() && profiles.get(i) != null) {
        builder.addProfile(profiles.get(i));
      } else {
        builder.addProfile(Profile.newBuilder().setPronoun(Pronoun.NEUTRAL).build());
      }
    }
    Game game = builder.build();
    ref.setValue(game.serialize());
    Firebase userRef = actionReferenceForGame(game.getId());
    userRef.setValue(newEmptyAction(game.getId()).serialize());
    if (!game.isLocalMultiplayer()) {
      pushNotificationService.addChannel(Games.channelIdForViewer(game, userId));
    }
    Map<String, String> dimensions = new HashMap<String, String>();
    dimensions.put("localMultiplayer", game.isLocalMultiplayer() + "");
    dimensions.put("profileCount", profiles.size() + "");
    analyticsService.trackEvent("newGame", dimensions);
    return game.getId();
  }

  /**
   * Sets a profile for the current player. Automatically sets them to "not a
   * computer player".
   *
   * @param game The game.
   * @param profile The profile.
   * @param onComplete Completion callback.
   */
  public void setProfileForViewer(String gameId, final Profile profile,
      final OnMutationCompleted onComplete) {
    Game game = getGame(gameId);
    final int playerNumber = Games.playerNumberForPlayerId(game, userId);
    GameMutation mutation = new GameMutation() {
      @Override public void mutate(Game.Builder game) {
        game.setProfile(playerNumber, profile.toBuilder().setIsComputerPlayer(false));
      }

      @Override public void onComplete(Game game) {
        onComplete.onMutationCompleted(game);
      }
    };
    analyticsService.trackEvent("setProfileForViewer");
    mutateGame(gameId, mutation, true /* abortOnConflict */);
  }

  /**
   * Add and submit the provided command.
   *
   * @param game Game to add and submit command for
   * @param command Command to add and submit.
   */
  public void addCommandAndSubmit(String gameId, Command command) {
    addCommandAndSubmit(gameId, command, null /* onComplete */);
  }

  /**
   * Adds the provided command as in {@link Model#addCommand(Game, Command)}
   * and also submits the resulting action as in {@link
   * Model#submitCurrentAction(Game)}.
   *
   * @param game The game.
   * @param command Command to add.
   * @param onComplete Optionally, a function to invoke when the command is submitted.
   */
  public void addCommandAndSubmit(String gameId, Command command, OnMutationCompleted onComplete) {
    final Game game = getGame(gameId);
    if (!couldAddCommand(game, getCurrentAction(gameId), command)) {
      die("Illegal command: %s", command);
    }
    Action toSubmit = currentActions.get(gameId)
        .toBuilder()
        .setPlayerNumber(game.getCurrentPlayerNumber())
        .clearFutureCommandList()
        .addCommand(command)
        .build();
    submitAction(gameId, toSubmit, onComplete);
    analyticsService.trackEvent("addCommandAndSubmit");
  }

  /**
   * Adds the provided command to the current action's command list. If there
   * is no current action, creates one. Any commands beyond the current
   * location in the undo history are deleted.
   *
   * @param game The current game.
   * @param currentAction The game's current action, or null if there is no
   *     current action.
   * @param command The command to add.
   * @return The game with the command added.
   */
  public void addCommand(String gameId, final Command command) {
    final Game game = getGame(gameId);
    if (!couldAddCommand(game, getCurrentAction(gameId), command)) {
      die("Illegal command: %s", command);
    }
    mutateCurrentAction(gameId, new ActionMutation() {
      @Override
      public void mutate(Action.Builder action) {
        action.clearFutureCommandList();
        action.addCommand(command);
        action.setPlayerNumber(game.getCurrentPlayerNumber());
      }
    });
    analyticsService.trackEvent("addCommand");
    updateLastModified(gameId);
  }

  /**
   * Replaces the last command of the current action of the provided game with
   * the provided command. The current action must exist and already have one
   * or more commands.
   *
   * @param game Game to modify the last command of.
   * @param command New value to use as the game's last command.
   */
  public void updateLastCommand(String gameId, final Command command) {
    if (!couldUpdateLastCommand(getGame(gameId), getCurrentAction(gameId), command)) {
      die("Illegal Command: %s", command);
    }

    mutateCurrentAction(gameId, new ActionMutation() {
      @Override
      public void mutate(Action.Builder action) {
        action.getCommandList().remove(action.getCommandCount() - 1);
        action.addCommand(command);
      }
    });
    analyticsService.trackEvent("updateLastCommand");
    updateLastModified(gameId);
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
  public boolean couldUpdateLastCommand(Game game, Action currentAction, Command command) {
    if (!isCurrentPlayer(game)) return false;
    if (currentAction.getCommandCount() == 0) return false;
    Action.Builder builder = currentAction.toBuilder();
    builder.getCommandList().remove(builder.getCommandCount() - 1);
    return couldAddCommand(game, builder.build(), command);
  }

  /**
   * Checks if a command could legally be played in a game.
   *
   * @param game Game the command will be added to.
   * @param command The command to check.
   * @return true if this command could be added the current action of this
   *     game.
   */
  public boolean couldAddCommand(Game game, Action currentAction, Command command) {
    if (!isCurrentPlayer(game)) return false;
    if (currentAction.getCommandCount() > 0) return false;
    return isSquareAvailable(game, command);
  }

  /**
   * Checks if an undo action is currently possible.
   *
   * @param game The current game.
   * @param currentAction The current action.
   * @return True if a command has been added to the current action of "game"
   *     which can be undone.
   */
  public boolean canUndo(Game game, Action currentAction) {
    if (!isCurrentPlayer(game)) return false;
    return currentAction.getCommandCount() > 0;
  }

  /**
   * Checks if a redo action is currently possible.
   *
   * @param game The game to check (possibly null).
   * @return True if a command has been added to the "futureCommands" of the
   *     current action of "game" and thus can be redone.
   */
  public boolean canRedo(Game game, Action currentAction) {
    if (!isCurrentPlayer(game)) return false;
    return currentAction.getFutureCommandCount() > 0;
  }

  /**
   * Checks if the current action can be submitted.
   *
   * @param game The game to check (possibly null).
   * @return True if the current action of "game" is a legal one which could be
   *     submitted.
   */
  public boolean canSubmit(Game game, Action currentAction) {
    if (!isCurrentPlayer(game)) return false;
    if (currentAction.getCommandCount() == 0) return false;
    for (Command command : currentAction.getCommandList()) {
      if (!isSquareAvailable(game, command)) return false;
    }
    return true;
  }

  /**
   * Version of {@link Model#submitCurrentAction(Game, OnMutationCompleted)}
   * with no completion function.
   *
   * @param game The game to submit the current action of.
   * @param action The action to submit.
   */
  public void submitCurrentAction(String gameId) {
    submitCurrentAction(gameId, null /* onCommplete */);
  }

  /**
   * Submits the provided game's current action, if it is a legal one. If this
   * ends the game: populates the "victors" array and sets the "gameOver"
   * bit. Otherwise, updates the current player.
   *
   * @param game The game to submit the current action of.
   * @param action The current action.
   * @param onComplete Optionally, a function to invoke when the action is
   *     submitted.
   */
  public void submitCurrentAction(String gameId, OnMutationCompleted onComplete) {
    final Action currentAction = getCurrentAction(gameId);
    submitAction(gameId, currentAction, onComplete);
  }

  /**
   * Submits the provided action.
   *
   * @param gameId Current Game ID.
   * @param action Action to submit.
   * @param onComplete Optionally, a function to invoke when the action is
   *     submitted.
   */
  private void submitAction(String gameId, final Action action,
      final OnMutationCompleted onComplete) {
    ensureIsCurrentPlayer(gameId);
    Game game = getGame(gameId);
    if (!canSubmit(game, action)) die("Illegal action!");
    boolean isXPlayer = game.getCurrentPlayerNumber() == X_PLAYER;
    final long timestamp = Clock.getInstance().currentTimeMillis();
    final int newPlayerNumber = isXPlayer ? O_PLAYER : X_PLAYER;
    final List<Integer> victors = computeVictorsIfSubmitted(game, action);
    GameMutation mutation = new GameMutation() {
      @Override public void mutate(Game.Builder game) {
        game.addSubmittedAction(action.toBuilder().setIsSubmitted(true));
        game.setLastModified(timestamp);
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
        sendPushNotification(game);
      }
    };
    actionReferenceForGame(gameId).setValue(newEmptyAction(gameId).serialize());
    mutateGame(gameId, mutation, true /* abortOnConflict */);
    analyticsService.trackEvent("submitCurrentAction");
  }

  /**
   * Sends a push notification to the viewer's opponent with information about
   * the current game status.
   * @param game The current game.
   */
  private void sendPushNotification(Game game) {
    if (!game.isLocalMultiplayer() && game.getPlayerCount() > 1) {
      String opponentId = game.getPlayer(Games.opponentPlayerNumber(game, userId));
      if (game.isGameOver()) {
        int viewerPlayerNumber = Games.playerNumberForPlayerId(game, userId);
        for (int i = 0; i < game.getPlayerCount(); ++i) {
          if (i != viewerPlayerNumber) {
            String message = "Game " + Games.vsString(game, opponentId) + ": Game over";
            sendPush(game.getId(), i, message);
          }
        }
      } else {
        String message = "Game " + Games.vsString(game, opponentId) + ": It's your turn!";
        sendPush(game.getId(), game.getCurrentPlayerNumber(), message);
      }
    }
  }

  private void sendPush(String gameId, int playerNumber, String message) {
    Map<String, String> data = new HashMap<String, String>();
    data.put("alert", message);
    data.put("gameId", gameId);
    data.put("title", "noughts");
    data.put("badge", "Increment");
    String channelId = Games.channelIdForPlayer(gameId, playerNumber);
    pushNotificationService.sendPushNotification(channelId, data);
  }

  /**
   * Checks if it is the computer's turn in this game, and performs the
   * appropriate computer move if it is.
   *
   * @param game Game to check.
   */
  public void handleComputerAction(final String gameId) {
    Game game = getGame(gameId);
    if (!game.hasCurrentPlayerNumber() || isComputerThinking ||
        !game.getProfile(game.getCurrentPlayerNumber()).hasIsComputerPlayer() ||
        !game.getProfile(game.getCurrentPlayerNumber()).isComputerPlayer()) {
      return;
    }
    analyticsService.trackEvent("handleComputerAction");
    Profile currentProfile = game.getProfile(game.getCurrentPlayerNumber());
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
        addCommandAndSubmit(gameId, command, new OnMutationCompleted() {
          @Override
          public void onMutationCompleted(Game game) {
            isComputerThinking = false;
          }
        });
      }
    }, 4000L);
  }

  /**
   * Undoes the player's previous command. Throws an exception if there's no
   * previous command to undo.
   *
   * @param game The game to undo the previous command of.
   * @param action The current action.
   * @return The game with the command undone.
   */
  public void undoCommand(String gameId) {
    ensureIsCurrentPlayer(gameId);
    if (!canUndo(getGame(gameId), getCurrentAction(gameId))) die("Can't undo.");
    mutateCurrentAction(gameId, new ActionMutation() {
      @Override
      public void mutate(Action.Builder action) {
        Command command = action.getCommandList().remove(action.getCommandList().size() - 1);
        action.addFutureCommand(command);
      }
    });
    updateLastModified(gameId);
    analyticsService.trackEvent("undoCommand");
  }

  /**
   * Re-does the player's previously undone command. Throws an exception if there's no
   * previous command to redo.
   *
   * @param game The game to redo the previous command of.
   * @return The game with the command redone.
   */
  public void redoCommand(String gameId) {
    ensureIsCurrentPlayer(gameId);
    if (!canRedo(getGame(gameId), getCurrentAction(gameId))) die("Can't redo.");
    mutateCurrentAction(gameId, new ActionMutation() {
      @Override
      public void mutate(Action.Builder action) {
        Command command = action.getFutureCommandList().remove(
            action.getFutureCommandCount() - 1);
        action.addCommand(command);
      }
    });
    updateLastModified(gameId);
    analyticsService.trackEvent("redoCommand");
  }

  /**
   * Leave a game. In a 2-player game, this means your opponent wins.
   *
   * @param game Game to resign from.
   * @return The game after resignation.
   */
  public void resignGame(String gameId) {
    ensureIsPlayer(gameId);
    if (getGame(gameId).isGameOver()) die("Can't resign from a game which is already over");
    final long timestamp = Clock.getInstance().currentTimeMillis();
    GameMutation mutation = new GameMutation() {
      @Override public void mutate(Game.Builder game) {
        game.setIsGameOver(true);
        if (game.isLocalMultiplayer() && game.getPlayerCount() == 2) {
          game.addVictor(game.getCurrentPlayerNumber() == 0 ? 1 : 0);
        } else {
          int opponentPlayerNumber = Games.opponentPlayerNumber(game.build(), userId);
          game.addVictor(opponentPlayerNumber);
        }
        game.clearCurrentPlayerNumber();
        game.setLastModified(timestamp);
      }

      @Override public void onComplete(Game game) {
        if (game != null && !game.isLocalMultiplayer()) {
          pushNotificationService.removeChannel(Games.channelIdForViewer(game, userId));
        }
      }
    };
    actionReferenceForGame(gameId).setValue(newEmptyAction(gameId).serialize());
    mutateGame(gameId, mutation, false /* abortOnConflict */);
    analyticsService.trackEvent("resignGame");
  }

  /**
   * Remove a game from a user's game list.
   *
   * @param game The game to archive.
   */
  public void archiveGame(String gameId) {
    userReferenceForGame(gameId).removeValue();
    analyticsService.trackEvent("archiveGame");
  }

  /**
   * Subscribes the viewer to the provided game ID. This does NOT make them a
   * player in the game, it merely adds the game to their game list.
   *
   * @param gameId Game ID to subscribe to.
   */
  public void subscribeViewerToGame(String gameId) {
    if (!currentActions.containsKey(gameId)) {
      actionReferenceForGame(gameId).setValue(newEmptyAction(gameId).serialize());
      analyticsService.trackEvent("subscribeViewerToGame");
    }
  }

  public void requestGameStatus(String gameId) {
    if (gameUpdateListeners.containsKey(gameId) && games.containsKey(gameId)) {
      gameUpdateListeners.get(gameId).onGameStatusChanged(Games.gameStatus(getGame(gameId)));
    }
  }

  /**
   * Add the viewer to the provided game if there's room and they're not
   * already a player.
   *
   * @param game Game to add the viewer to.
   * @param profile Optionally, a profile to add for this player
   */
  public void joinGameIfPossible(String gameId, final @Nullable Profile profile) {
    Game game = getGame(gameId);
    if (!(game.isGameOver()) && game.getPlayerCount() < 2 &&
        !game.getPlayerList().contains(userId)) {
      GameMutation mutation = new GameMutation() {
        @Override
        public void mutate(Game.Builder game) {
          game.addPlayer(userId);
          if (profile != null) {
            if (game.getProfileCount() < 2) {
              game.addProfile(profile);
            } else {
              game.setProfile(1, profile);
            }
          }
        }

        @Override
        public void onComplete(Game game) {
          if (!game.isLocalMultiplayer()) {
            pushNotificationService.addChannel(Games.channelIdForViewer(game, userId));
          }
        }
      };
      mutateGame(gameId, mutation, true /* abortOnConflict */);
      analyticsService.trackEvent("joinGameIfPossible");
    }
  }

  /**
   * Associates a game ID with a given Facebook Request ID
   *
   * @param requestId The request ID.
   * @param gameId The game ID.
   */
  public void putFacebookRequestId(String requestId, String gameId) {
    requestReference(requestId).setValue(gameId);
    analyticsService.trackEvent("putFacebookRequestId");
  }

  public void subscribeToRequestIds(String requestId, final RequestLoadedCallback callback) {
    requestReference(requestId).addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onCancelled(FirebaseError error) {}

      @Override
      public void onDataChange(DataSnapshot snapshot) {
        String gameId = (String)snapshot.getValue();
        subscribeViewerToGame(gameId);
        callback.onRequestLoaded(gameId);
      }
    });
  }

  /**
   * Switches the user from an anonymous account to a facebook account. This
   * one-time process changes their userID in all currently known games and
   * returns a new Model object for the new account. It is intended to happen
   * on a best-effort basis, successful migration of all games is not
   * guaranteed.
   *
   * @param facebookId The user's facebook ID.
   * @param callback Callback to invoke once the user's current actions have
   *     been moved.
   */
  public void upgradeAccountToFacebook(String facebookId, final OnUpgradeCompleted callback) {
    final String oldId = userId;
    final String oldKey = userKey;
    final GameListListener listListener = gameListListener;
    final ChildEventListener childListener = this;
    gameListListener = null;
    userId = facebookId;
    userKey = facebookId;
    isFacebookUser = true;
    final AtomicInteger callbacksExpected =
        new AtomicInteger(games.size() + currentActions.size());
    Map<String, String> dimensions = new HashMap<String, String>();
    dimensions.put("callbacksExpected", callbacksExpected.get() + "");
    analyticsService.trackEvent("upgradeAccountToFacebook", dimensions);

    final OnUpgradeCompleted internalCallback = new OnUpgradeCompleted() {
      @Override
      public void onUpgradeCompleted() {
        firebase.child("users").child(oldKey).removeValue(new Firebase.CompletionListener() {
          @Override
          public void onComplete(FirebaseError error, Firebase reference) {
            gameListListener = listListener;
            firebase.child("users").child(oldKey).child("games")
                .removeEventListener(gameChildEventListener);
            gameChildEventListener = userGamesReference().addChildEventListener(childListener);
            callback.onUpgradeCompleted();
          }
        });
      }
    };

    if (callbacksExpected.get() == 0) {
      internalCallback.onUpgradeCompleted();
      return;
    }

    for (String gameId : games.keySet()) {
      mutateGame(gameId, new GameMutation() {
        @Override
        public void mutate(Game.Builder gameBuilder) {
          for (int i = 0; i < gameBuilder.getPlayerCount(); ++i) {
            if (gameBuilder.getPlayer(i).equals(oldId)) {
              gameBuilder.setPlayer(i, userId);
            }
          }
        }
        @Override
        public void onComplete(Game game) {
          if (callbacksExpected.decrementAndGet() == 0) {
            internalCallback.onUpgradeCompleted();
          }
        }
      }, false /* abortOnConflict */);
    }

    for (String gameId : currentActions.keySet()) {
      actionReferenceForGame(gameId).setValue(currentActions.get(gameId).serialize(),
          new Firebase.CompletionListener() {
        @Override
        public void onComplete(FirebaseError error, Firebase reference) {
          if (callbacksExpected.decrementAndGet() == 0) {
            internalCallback.onUpgradeCompleted();
          }
        }
      });
    }
  }

  /**
   * Builds the "victors" array for the game. If the game is over, a list will be
   * returned containing the victorious or drawing players (which may be empty to
   * indicate that "nobody wins"). Otherwise, null is returned. The computation
   * is done *as if* the current action were submitted.
   *
   * @param game The game to find the victors for
   * @param currentAction The current action in this game.
   * @return A list of player numbers of victors or null if the game is not
   *     over.
   */
  @VisibleForTesting
  List<Integer> computeVictorsIfSubmitted(Game game, Action currentAction) {
    checkNotNull(currentAction);

    // 1) check for win
    Action[][] actionTable = makeActionTable(game, currentAction);
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

  private Game getGame(String gameId) {
    if (games.containsKey(gameId)) {
      return games.get(gameId);
    } else {
      throw die("Unknown game %s", gameId);
    }
  }

  private Action getCurrentAction(String gameId) {
    if (currentActions.containsKey(gameId)) {
      return currentActions.get(gameId);
    } else {
      throw die("Can't find current action for game %s", gameId);
    }
  }

  /**
   * Returns true if the square mentioned in this command is available.
   */
  private boolean isSquareAvailable(Game game, Command command) {
    int column = command.getColumn();
    int row = command.getRow();
    if (column < 0 || row < 0 || column > 2 || row > 2) {
      return false;
    }
    return makeActionTable(game, null /* currentAction */)[column][row] == null;
  }

  /**
   * Returns a 2-dimensional map of game actions spatially indexed by
   * [column][row], so e.g. table[0][2] is the bottom-left square's action.
   * If currentAction is not null, it will also be included in the action
   * table.
   */
  private Action[][] makeActionTable(Game game, /* Optional */ Action currentAction) {
    Action[][] result = new Action[3][3];
    for (Action action : game.getSubmittedActionList()) {
      for (Command command : action.getCommandList()) {
        result[command.getColumn()][command.getRow()] = action;
      }
    }
    if (currentAction != null) {
      for (Command command : currentAction.getCommandList()) {
        result[command.getColumn()][command.getRow()] = currentAction;
      }
    }
    return result;
  }

  private void updateLastModified(String gameId) {
    final long timestamp = Clock.getInstance().currentTimeMillis();
    mutateGame(gameId, new GameMutation() {
      @Override
      public void mutate(Game.Builder game) {
        game.setLastModified(timestamp);
      }
    }, true /* abortOnConflict */);
  }

  private void  mutateGame(String gameId, final GameMutation mutation,
      final boolean abortOnConflict) {
    final Game original = getGame(gameId);
    gameReference(gameId).runTransaction(new Transaction.Handler() {
      @Override
      public Transaction.Result doTransaction(MutableData data) {
        if (data.getValue() == null) {
          return Transaction.success(data);
        }
        Game game = Game.newDeserializer().fromMutableData(data);
        if (!original.equals(game) && abortOnConflict) {
          System.out.println("\naborting on conflict");
          System.out.println("\noriginal " + original);
          System.out.println("\nnew " + game);
          return Transaction.abort();
        }
        Game.Builder builder = game.toBuilder();
        mutation.mutate(builder);
        data.setValue(builder.build().serialize());
        return Transaction.success(data);
      }

      @Override
      public void onComplete(FirebaseError error, boolean done, DataSnapshot snapshot) {
        if (snapshot != null && snapshot.getValue() != null) {
          mutation.onComplete(Game.newDeserializer().fromDataSnapshot(snapshot));
        }
      }
    });
  }

  private void mutateCurrentAction(String gameId, final ActionMutation mutation) {
    actionReferenceForGame(gameId).runTransaction(new Transaction.Handler() {
      @Override
      public Transaction.Result doTransaction(MutableData data) {
        if (data.getValue() == null) {
          return Transaction.success(data);
        }
        Action.Builder action = Action.newDeserializer().fromMutableData(data).toBuilder();
        mutation.mutate(action);
        data.setValue(action.build().serialize());
        return Transaction.success(data);
      }

      @Override
      public void onComplete(FirebaseError error, boolean done, DataSnapshot snapshot) {
        if (snapshot.getValue() != null) {
          mutation.onComplete(Action.newDeserializer().fromDataSnapshot(snapshot));
        }
      }
    });
  }

  private Action newEmptyAction(String gameId) {
    return Action.newBuilder()
        .setGameId(gameId)
        .setIsSubmitted(false)
        .build();
  }

  /**
   * @param gameId A game ID
   * @return A Firebase reference for this game in the master game list.
   */
  private Firebase gameReference(String gameId) {
    return firebase.child("games").child(gameId);
  }

  private Firebase userGamesReference() {
    Firebase users = firebase.child(isFacebookUser ? "facebookUsers" : "users");
    return users.child(userKey).child("games");
  }

  private Firebase userReferenceForGame(String gameId) {
    return userGamesReference().child(gameId);
  }

  private Firebase actionReferenceForGame(String gameId) {
    return userReferenceForGame(gameId).child("currentAction");
  }

  private Firebase requestReference(String requestId) {
    return firebase.child("requests").child("r" + requestId);
  }

  /**
   * Throws an exception.
   */
  private RuntimeException die(String message, Object... args) {
    Map<String, String> dimensions = new HashMap<String, String>();
    dimensions.put("message", message);
    analyticsService.trackEvent("die", dimensions);
    throw new RuntimeException(String.format(message, args));
  }

  /**
   * Ensures the current user is the current player in the provided game.
   */
  void ensureIsCurrentPlayer(String gameId) {
    if (!isCurrentPlayer(getGame(gameId))) die("Not current player: %s", userId);
  }

  /**
   * Ensures the current user is a player in the provided game.
   */
  void ensureIsPlayer(String gameId) {
    if (!getGame(gameId).getPlayerList().contains(userId)) die("Not player: %s", userId);
  }

  void ensureGameIsNotOver(String gameId) {
    if (getGame(gameId).isGameOver()) die("Game has ended");
  }
}
