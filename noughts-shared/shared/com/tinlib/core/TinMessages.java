package com.tinlib.core;

import static com.tinlib.util.Identifiers.id;

public final class TinMessages {
  /**
   * Fired whenever an error occurs. The value will be a String describing the
   * error.
   */
  public static final String ERROR = id("tin.ERROR");

  /**
   * A {@link com.tinlib.services.FirebaseReferences} instance configured with the ID of the
   * current viewer.
   */
  public static final String FIREBASE_REFERENCES = id("tin.FIREBASE_REFERENCES");

  /**
   * A String with the ID of the current viewer.
   */
  public static final String VIEWER_ID = id("tin.VIEWER_ID");

  /**
   * Fired when the user requests to load a game. The value will be a String
   * with the ID of the game.
   */
  public static final String CURRENT_GAME_ID = id("tin.CURRENT_GAME_ID");

  /**
   * The current {@link com.tinlib.generated.Game}
   */
  public static final String CURRENT_GAME = id("tin.CURRENT_GAME");

  /**
   * The current {@link com.tinlib.generated.Action} of the current
   * game.
   */
  public static final String CURRENT_ACTION = id("tin.CURRENT_ACTION");

  /**
   * The {@link com.tinlib.generated.GameStatus} of the current
   * game, fired whenever the game status changes.
   */
  public static final String GAME_STATUS = id("tin.GAME_STATUS");

  /**
   * Fired when the user is playing in a game with an incomplete profile. The
   * value will be a suggested {@link com.tinlib.generated.Profile}
   * for the user.
   *
   * <p>This message is never fired when the current game is a local
   * multiplayer game, or when the game is over.</p>
   */
  public static final String PROFILE_REQUIRED = id("tin.PROFILE_REQUIRED");

  /**
   * Fired when the user's profile is loaded or set and it's complete. The
   * companion message to {@link TinMessages#PROFILE_REQUIRED}, which is fired
   * when the user's profile is not complete. The associated value will be
   * the user's completed {@link com.tinlib.generated.Profile}.
   *
   * <p>This message is never fired when the current game is a local
   * multiplayer game, or when the game is over.</p>
   */
  public static final String VIEWER_PROFILE = id("tin.VIEWER_PROFILE");

  /**
   * Fired when an action is submitted. The value will be the just-submitted
   * {@link com.tinlib.generated.Action}.
   */
  public static final String SUBMIT_ACTION_COMPLETED = id("tin.SUBMIT_ACTION_COMPLETED");

  /**
   * Fired when a command is undone. The value will be the undone
   * {@link com.tinlib.generated.Command}.
   */
  public static final String COMMAND_UNDO_COMPLETED = id("tin.COMMAND_UNDO_COMPLETED");

  /**
   * Fired when a command is redone. The value will be the redone
   * {@link com.tinlib.generated.Command}.
   */
  public static final String COMMAND_REDO_COMPLETED = id("tin.COMMAND_REDO_COMPLETED");

  /**
   * Fired when a command is added. The value will be the newly-added
   * {@link com.tinlib.generated.Command}.
   */
  public static final String COMMAND_ADD_COMPLETED = id("tin.COMMAND_ADD_COMPLETED");

  /**
   * Fired when a command is changed. The value will be a
   * {@link com.tinlib.generated.IndexCommand} with the changed command and
   * its index.
   */
  public static final String COMMAND_CHANGE_COMPLETED = id("tin.COMMAND_CHANGE_COMPLETED");

  /**
   * Fired when a new game is created. The value will be the newly-created
   * {@link com.tinlib.generated.Game}.
   */
  public static final String CREATE_GAME_COMPLETED = id("tin.CREATE_GAME_COMPLETED");

  /**
   * Fired when the user joins a game. The value will be
   * {@link com.tinlib.generated.Game} they joined.
   */
  public static final String JOIN_GAME_COMPLETED = id("tin.JOIN_GAME_COMPLETED");

  /**
   * Fired when the user's account is upgraded to Facebook. No associated
   * value.
   */
  public static final String ACCOUNT_UPGRADE_COMPLETED = id("tin.ACCOUNT_UPGRADE_COMPLETED");

  /**
   * Fired when a game is added to the game list. The value will be the
   * {@link com.tinlib.generated.IndexPath} at which the game was added.
   */
  public static final String GAME_LIST_ADD = id("tin.GAME_LIST_ADD");

  /**
   * Fired when a game in the game list is changed. The value will be a
   * {@link com.tinlib.generated.GameListUpdate} containing the location in the
   * list to move a row from and the location in the list to move the row to.
   */
  public static final String GAME_LIST_MOVE = id("tin.GAME_LIST_CHANGE");

  /**
   * Fired when a game in the game list is removed. The value will be the
   * {@link com.tinlib.generated.IndexPath} at which the game was removed.
   */
  public static final String GAME_LIST_REMOVE = id("tin.GAME_LIST_REMOVE");

  /**
   * Fired with a {@link com.tinlib.services.GameList} for the viewer whenever
   * the viewer changes.
   */
  public static final String GAME_LIST = id("tin.GAME_LIST");

  /**
   * Fired when the viewer resigns from a game. The value will be the
   * {@link com.tinlib.generated.Game} which the viewer resigned from.
   */
  public static final String RESIGN_GAME_COMPLETED = id("tin.RESIGN_GAME_COMPLETED");

  /**
   * Fired when the viewer archives a game which has ended. The value will
   * be the ID of the archived game.
   */
  public static final String ARCHIVE_GAME_COMPLETED = id("tin.ARCHIVE_GAME_COMPLETED");

  /**
   * Fired when a proposed command is added by the viewer. The value will be an
   * {@link com.tinlib.generated.IndexCommand} containing the added command and
   * the index at which is was added.
   */
  public static final String COMMAND_ADDED = id("tin.COMMAND_ADDED");

  /**
   * Fired when a command is part of an action submitted by any player. Note
   * that even the viewer's own commands may not show up via
   * {@link TinMessages#COMMAND_ADDED} first because it is possible to add
   * commands directly at submit time. The value will be an
   * {@link com.tinlib.generated.IndexCommand} containing the newly submitted
   * command.
   */
  public static final String COMMAND_SUBMITTED = id("tin.COMMAND_SUBMITTED");

  /**
   * Fired when a command is undone by the viewer. The value will be an
   * {@link com.tinlib.generated.IndexCommand} containing the command which
   * was undone and the index it formerly occupied.
   */
  public static final String COMMAND_UNDONE = id("tin.COMMAND_UNDONE");

  /**
   * Fired when the value of one of the viewer's proposed commands changes. The
   * value will be an {@link com.tinlib.generated.IndexCommand} containing the
   * changed command.
   */
  public static final String COMMAND_CHANGED = id("tin.COMMAND_CHANGED");

  /**
   * Fired when a new submitted action is added to the current game. The value
   * will be the {@link com.tinlib.generated.Game} with the newly-submitted
   * action.
   */
  public static final String ACTION_SUBMITTED = id("tin.ACTION_SUBMITTED");

  /**
   * Fired when the current game is ended. The value will be the
   * {@link com.tinlib.generated.Game} which has just ended.
   */
  public static final String GAME_OVER = id("tin.GAME_OVER");
}
