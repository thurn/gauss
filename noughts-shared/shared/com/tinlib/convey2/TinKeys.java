package com.tinlib.convey2;

import com.tinlib.generated.*;
import com.tinlib.services.FirebaseReferences;
import com.tinlib.services.GameList;

public final class TinKeys {
  /**
   * A {@link com.tinlib.services.FirebaseReferences} instance configured with the ID of the
   * current viewer.
   */
  public static final Key<FirebaseReferences> FIREBASE_REFERENCES =
      Keys.createKey(FirebaseReferences.class, "FIREBASE_REFERENCES");

  /**
   * A String with the ID of the current viewer.
   */
  public static final Key<String> VIEWER_ID =
      Keys.createKey(String.class, "VIEWER_ID");

  /**
   * Fired when the user requests to load a game. The value will be a String
   * with the ID of the game.
   */
  public static final Key<String> CURRENT_GAME_ID =
      Keys.createKey(String.class, "CURRENT_GAME_ID");

  /**
   * The current {@link com.tinlib.generated.Game}
   */
  public static final Key<Game> CURRENT_GAME =
      Keys.createKey(Game.class, "CURRENT_GAME");

  /**
   * The current {@link com.tinlib.generated.Action} of the current
   * game.
   */
  public static final Key<Action> CURRENT_ACTION =
      Keys.createKey(Action.class, "CURRENT_ACTION");

  /**
   * The {@link com.tinlib.generated.GameStatus} of the current
   * game, fired whenever the game status changes.
   */
  public static final Key<GameStatus> GAME_STATUS =
      Keys.createKey(GameStatus.class, "GAME_STATUS");

  /**
   * Fired when the user has successfully updated their profile. The value will
   * be the new value of their profile
   */
  public static final Key<Profile> SET_PROFILE_COMPLETED =
      Keys.createKey(Profile.class, "SET_PROFILE_COMPLETED");

  /**
   * Fired when an action is submitted. The value will be the just-submitted
   * {@link com.tinlib.generated.Action}.
   */
  public static final Key<Action> SUBMIT_ACTION_COMPLETED =
      Keys.createKey(Action.class, "SUBMIT_ACTION_COMPLETED");

  /**
   * Fired when a command is undone. The value will be the undone
   * {@link com.tinlib.generated.Command}.
   */
  public static final Key<Command> COMMAND_UNDO_COMPLETED =
      Keys.createKey(Command.class, "COMMAND_UNDO_COMPLETED");

  /**
   * Fired when a command is redone. The value will be the redone
   * {@link com.tinlib.generated.Command}.
   */
  public static final Key<Command> COMMAND_REDO_COMPLETED =
      Keys.createKey(Command.class, "COMMAND_REDO_COMPLETED");

  /**
   * Fired when a command is added. The value will be the newly-added
   * {@link com.tinlib.generated.Command}.
   */
  public static final Key<Command> COMMAND_ADD_COMPLETED =
      Keys.createKey(Command.class, "COMMAND_ADD_COMPLETED");

  /**
   * Fired when a command is changed. The value will be a
   * {@link com.tinlib.generated.IndexCommand} with the changed command and
   * its index.
   */
  public static final Key<IndexCommand> COMMAND_CHANGE_COMPLETED =
      Keys.createKey(IndexCommand.class, "COMMAND_CHANGE_COMPLETED");

  /**
   * Fired when a new game is created. The value will be the newly-created
   * {@link com.tinlib.generated.Game}.
   */
  public static final Key<Game> CREATE_GAME_COMPLETED =
      Keys.createKey(Game.class, "CREATE_GAME_COMPLETED");

  /**
   * Fired when the user joins a game. The value will be
   * {@link com.tinlib.generated.Game} they joined.
   */
  public static final Key<Game> JOIN_GAME_COMPLETED =
      Keys.createKey(Game.class, "JOIN_GAME_COMPLETED");

  /**
   * Fired when the user's account is upgraded to Facebook. No associated
   * value.
   */
  public static final Key<Void> ACCOUNT_UPGRADE_COMPLETED =
      Keys.createVoidKey("ACCOUNT_UPGRADE_COMPLETED");

  /**
   * Fired when a game is added to the game list. The value will be the
   * {@link com.tinlib.generated.IndexPath} at which the game was added.
   */
  public static final Key<IndexPath> GAME_LIST_ADD =
      Keys.createKey(IndexPath.class, "GAME_LIST_ADD");

  /**
   * Fired when a game in the game list is changed. The value will be a
   * {@link com.tinlib.generated.GameListUpdate} containing the location in the
   * list to move a row from and the location in the list to move the row to.
   */
  public static final Key<GameListUpdate> GAME_LIST_MOVE =
      Keys.createKey(GameListUpdate.class, "GAME_LIST_MOVE");

  /**
   * Fired when a game in the game list is removed. The value will be the
   * {@link com.tinlib.generated.IndexPath} at which the game was removed.
   */
  public static final Key<IndexPath> GAME_LIST_REMOVE =
      Keys.createKey(IndexPath.class, "GAME_LIST_REMOVE");

  /**
   * Fired with a {@link com.tinlib.services.GameList} for the viewer whenever
   * the viewer changes.
   */
  public static final Key<GameList> GAME_LIST =
      Keys.createKey(GameList.class, "GAME_LIST");

  /**
   * Fired when the viewer resigns from a game. The value will be the
   * {@link com.tinlib.generated.Game} which the viewer resigned from.
   */
  public static final Key<Game> RESIGN_GAME_COMPLETED =
      Keys.createKey(Game.class, "RESIGN_GAME_COMPLETED");

  /**
   * Fired when the viewer archives a game which has ended. The value will
   * be the ID of the archived game.
   */
  public static final Key<String> ARCHIVE_GAME_COMPLETED =
      Keys.createKey(String.class, "ARCHIVE_GAME_COMPLETED");

  /**
   * Fired when a proposed command is added by the viewer. The value will be an
   * {@link com.tinlib.generated.IndexCommand} containing the added command and
   * the index at which is was added.
   */
  public static final Key<IndexCommand> COMMAND_ADDED =
      Keys.createKey(IndexCommand.class, "COMMAND_ADDED");

  /**
   * Fired when a command is part of an action submitted by any player. Note
   * that even the viewer's own commands may not show up via
   * {@link com.tinlib.convey2.TinKeys#COMMAND_ADDED} first because it is possible to add
   * commands directly at submit time. The value will be an
   * {@link com.tinlib.generated.IndexCommand} containing the newly submitted
   * command.
   */
  public static final Key<IndexCommand> COMMAND_SUBMITTED =
      Keys.createKey(IndexCommand.class, "COMMAND_SUBMITTED");

  /**
   * Fired when a command is undone by the viewer. The value will be an
   * {@link com.tinlib.generated.IndexCommand} containing the command which
   * was undone and the index it formerly occupied.
   */
  public static final Key<IndexCommand> COMMAND_UNDONE =
      Keys.createKey(IndexCommand.class, "COMMAND_UNDONE");

  /**
   * Fired when the value of one of the viewer's proposed commands changes. The
   * value will be an {@link com.tinlib.generated.IndexCommand} containing the
   * changed command.
   */
  public static final Key<IndexCommand> COMMAND_CHANGED =
      Keys.createKey(IndexCommand.class, "COMMAND_CHANGED");

  /**
   * Fired when a new submitted action is added to the current game. The value
   * will be the {@link com.tinlib.generated.Game} with the newly-submitted
   * action.
   */
  public static final Key<Game> ACTION_SUBMITTED =
      Keys.createKey(Game.class, "ACTION_SUBMITTED");

  /**
   * Fired when the current game is ended. The value will be the
   * {@link com.tinlib.generated.Game} which has just ended.
   */
  public static final Key<Game> GAME_OVER =
      Keys.createKey(Game.class, "GAME_OVER");
}
