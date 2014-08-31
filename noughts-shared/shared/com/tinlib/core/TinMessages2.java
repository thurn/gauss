package com.tinlib.core;

import com.tinlib.generated.*;
import com.tinlib.message.Key;
import com.tinlib.message.Keys;
import com.tinlib.services.FirebaseReferences;
import com.tinlib.services.GameList;

public final class TinMessages2 {
  /**
   * Fired whenever an error occurs. The value will be a String describing the
   * error.
   */
  public static final Key<String> ERROR = Keys.createKey(String.class);

  /**
   * A {@link com.tinlib.services.FirebaseReferences} instance configured with the ID of the
   * current viewer.
   */
  public static final Key<FirebaseReferences> FIREBASE_REFERENCES =
      Keys.createKey(FirebaseReferences.class);

  /**
   * A String with the ID of the current viewer.
   */
  public static final Key<String> VIEWER_ID = Keys.createKey(String.class);

  /**
   * Fired when the user requests to load a game. The value will be a String
   * with the ID of the game.
   */
  public static final Key<String> CURRENT_GAME_ID = Keys.createKey(String.class);

  /**
   * The current {@link com.tinlib.generated.Game}
   */
  public static final Key<Game> CURRENT_GAME = Keys.createKey(Game.class);

  /**
   * The current {@link com.tinlib.generated.Action} of the current
   * game.
   */
  public static final Key<Action> CURRENT_ACTION = Keys.createKey(Action.class);

  /**
   * The {@link com.tinlib.generated.GameStatus} of the current
   * game, fired whenever the game status changes.
   */
  public static final Key<GameStatus> GAME_STATUS = Keys.createKey(GameStatus.class);

  /**
   * Fired when the user is playing in a game with an incomplete profile. The
   * value will be a suggested {@link com.tinlib.generated.Profile}
   * for the user.
   *
   * <p>This message is never fired when the current game is a local
   * multiplayer game, or when the game is over.</p>
   */
  public static final Key<Profile> PROFILE_REQUIRED = Keys.createKey(Profile.class);

  /**
   * Fired when the user's profile is loaded or set and it's complete. The
   * companion message to {@link com.tinlib.core.TinMessages2#PROFILE_REQUIRED}, which is fired
   * when the user's profile is not complete. The associated value will be
   * the user's completed {@link com.tinlib.generated.Profile}.
   *
   * <p>This message is never fired when the current game is a local
   * multiplayer game, or when the game is over.</p>
   */
  public static final Key<Profile> VIEWER_PROFILE = Keys.createKey(Profile.class);

  /**
   * Fired when an action is submitted. The value will be the just-submitted
   * {@link com.tinlib.generated.Action}.
   */
  public static final Key<Action> SUBMIT_ACTION_COMPLETED = Keys.createKey(Action.class);

  /**
   * Fired when a command is undone. The value will be the undone
   * {@link com.tinlib.generated.Command}.
   */
  public static final Key<Command> COMMAND_UNDO_COMPLETED = Keys.createKey(Command.class);

  /**
   * Fired when a command is redone. The value will be the redone
   * {@link com.tinlib.generated.Command}.
   */
  public static final Key<Command> COMMAND_REDO_COMPLETED = Keys.createKey(Command.class);

  /**
   * Fired when a command is added. The value will be the newly-added
   * {@link com.tinlib.generated.Command}.
   */
  public static final Key<Command> COMMAND_ADD_COMPLETED = Keys.createKey(Command.class);

  /**
   * Fired when a command is changed. The value will be a
   * {@link com.tinlib.generated.IndexCommand} with the changed command and
   * its index.
   */
  public static final Key<IndexCommand> COMMAND_CHANGE_COMPLETED =
      Keys.createKey(IndexCommand.class);

  /**
   * Fired when a new game is created. The value will be the newly-created
   * {@link com.tinlib.generated.Game}.
   */
  public static final Key<Game> CREATE_GAME_COMPLETED = Keys.createKey(Game.class);

  /**
   * Fired when the user joins a game. The value will be
   * {@link com.tinlib.generated.Game} they joined.
   */
  public static final Key<Game> JOIN_GAME_COMPLETED = Keys.createKey(Game.class);

  /**
   * Fired when the user's account is upgraded to Facebook. No associated
   * value.
   */
  public static final Key<Void> ACCOUNT_UPGRADE_COMPLETED = Keys.createVoidKey();

  /**
   * Fired when a game is added to the game list. The value will be the
   * {@link com.tinlib.generated.IndexPath} at which the game was added.
   */
  public static final Key<IndexPath> GAME_LIST_ADD = Keys.createKey(IndexPath.class);

  /**
   * Fired when a game in the game list is changed. The value will be a
   * {@link com.tinlib.generated.GameListUpdate} containing the location in the
   * list to move a row from and the location in the list to move the row to.
   */
  public static final Key<GameListUpdate> GAME_LIST_MOVE = Keys.createKey(GameListUpdate.class);

  /**
   * Fired when a game in the game list is removed. The value will be the
   * {@link com.tinlib.generated.IndexPath} at which the game was removed.
   */
  public static final Key<IndexPath> GAME_LIST_REMOVE = Keys.createKey(IndexPath.class);

  /**
   * Fired with a {@link com.tinlib.services.GameList} for the viewer whenever
   * the viewer changes.
   */
  public static final Key<GameList> GAME_LIST = Keys.createKey(GameList.class);

  /**
   * Fired when the viewer resigns from a game. The value will be the
   * {@link com.tinlib.generated.Game} which the viewer resigned from.
   */
  public static final Key<Game> RESIGN_GAME_COMPLETED = Keys.createKey(Game.class);

  /**
   * Fired when the viewer archives a game which has ended. The value will
   * be the ID of the archived game.
   */
  public static final Key<String> ARCHIVE_GAME_COMPLETED = Keys.createKey(String.class);

  /**
   * Fired when a proposed command is added by the viewer. The value will be an
   * {@link com.tinlib.generated.IndexCommand} containing the added command and
   * the index at which is was added.
   */
  public static final Key<IndexCommand> COMMAND_ADDED = Keys.createKey(IndexCommand.class);

  /**
   * Fired when a command is part of an action submitted by any player. Note
   * that even the viewer's own commands may not show up via
   * {@link com.tinlib.core.TinMessages2#COMMAND_ADDED} first because it is possible to add
   * commands directly at submit time. The value will be an
   * {@link com.tinlib.generated.IndexCommand} containing the newly submitted
   * command.
   */
  public static final Key<IndexCommand> COMMAND_SUBMITTED = Keys.createKey(IndexCommand.class);

  /**
   * Fired when a command is undone by the viewer. The value will be an
   * {@link com.tinlib.generated.IndexCommand} containing the command which
   * was undone and the index it formerly occupied.
   */
  public static final Key<IndexCommand> COMMAND_UNDONE = Keys.createKey(IndexCommand.class);

  /**
   * Fired when the value of one of the viewer's proposed commands changes. The
   * value will be an {@link com.tinlib.generated.IndexCommand} containing the
   * changed command.
   */
  public static final Key<IndexCommand> COMMAND_CHANGED = Keys.createKey(IndexCommand.class);

  /**
   * Fired when a new submitted action is added to the current game. The value
   * will be the {@link com.tinlib.generated.Game} with the newly-submitted
   * action.
   */
  public static final Key<Game> ACTION_SUBMITTED = Keys.createKey(Game.class);

  /**
   * Fired when the current game is ended. The value will be the
   * {@link com.tinlib.generated.Game} which has just ended.
   */
  public static final Key<Game> GAME_OVER = Keys.createKey(Game.class);
}
