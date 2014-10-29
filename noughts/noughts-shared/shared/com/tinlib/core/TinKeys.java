package com.tinlib.core;

import com.tinlib.generated.*;
import com.tinlib.convey.Key;
import com.tinlib.convey.Keys;
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
   * Fired when a proposed command is added by the viewer. The value will be an
   * {@link com.tinlib.generated.IndexCommand} containing the added command and
   * the index at which is was added.
   */
  public static final Key<IndexCommand> COMMAND_ADDED =
      Keys.createKey(IndexCommand.class, "COMMAND_ADDED");

  /**
   * Fired when a command is part of an action submitted by any player. Note
   * that even the viewer's own commands may not show up via
   * {@link TinKeys#COMMAND_ADDED} first because it is possible to add
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

  public static final Key<Void> AI_ACTION_SUBMITTED =
      Keys.createVoidKey("AI_ACTION_SUBMITTED");
}
