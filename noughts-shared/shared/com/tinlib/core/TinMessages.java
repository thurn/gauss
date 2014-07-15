package com.tinlib.core;

public final class TinMessages {
  /**
   * Fired whenever an error occurs. The value will be a String describing the
   * error.
   */
  public static final String ERROR = "tin.ERROR";

  /**
   * A {@link com.tinlib.shared.FirebaseReferences} instance configured with the ID of the
   * current viewer.
   */
  public static final String FIREBASE_REFERENCES = "tin.FIREBASE_REFERENCES";

  /**
   * A String with the ID of the current viewer.
   */
  public static final String VIEWER_ID = "tin.VIEWER_ID";

  /**
   * Fired when the user requests to load a game. The value will be a String
   * with the ID of the game.
   */
  public static final String CURRENT_GAME_ID = "tin.CURRENT_GAME_ID";

  /**
   * The current {@link ca.thurn.noughts.shared.entities.Game}
   */
  public static final String CURRENT_GAME = "tin.CURRENT_GAME";

  /**
   * The current {@link ca.thurn.noughts.shared.entities.Action} of the current
   * game.
   */
  public static final String CURRENT_ACTION = "tin.CURRENT_ACTION";

  /**
   * The {@link ca.thurn.noughts.shared.entities.GameStatus} of the current
   * game, fired whenever the game status changes.
   */
  public static final String GAME_STATUS = "tin.GAME_STATUS";

  /**
   * Fired when the user is playing in a game with an incomplete profile. The
   * value will be a suggested {@link ca.thurn.noughts.shared.entities.Profile}
   * for the user.
   *
   * <p>This message is never fired when the current game is a local
   * multiplayer game, or when the game is over.</p>
   */
  public static final String PROFILE_REQUIRED = "tin.PROFILE_REQUIRED";

  /**
   * Fired when the user's profile is loaded or set and it's complete. The
   * companion message to {@link TinMessages#PROFILE_REQUIRED}, which is fired
   * when the user's profile is not complete. The associated value will be
   * the user's completed {@link ca.thurn.noughts.shared.entities.Profile}.
   *
   * <p>This message is never fired when the current game is a local
   * multiplayer game, or when the game is over.</p>
   */
  public static final String COMPLETED_VIEWER_PROFILE = "tin.COMPLETED_VIEWER_PROFILE";

  /**
   * Fired when an action is submitted. The value will be the just-submitted
   * {@link ca.thurn.noughts.shared.entities.Action}.
   */
  public static final String ACTION_SUBMITTED = "tin.ACTION_SUBMITTED";

  /**
   * Fired when a command is undone. The value will be the undone
   * {@link ca.thurn.noughts.shared.entities.Command}.
   */
  public static final String COMMAND_UNDONE = "tin.COMMAND_UNDONE";

  /**
   * Fired when a command is redone. The value will be the redone
   * {@link ca.thurn.noughts.shared.entities.Command}.
   */
  public static final String COMMAND_REDONE = "tin.COMMAND_REDONE";

  /**
   * Fired when a command is added. The value will be the newly-added
   * {@link ca.thurn.noughts.shared.entities.Command}.
   */
  public static final String COMMAND_ADDED = "tin.COMMAND_ADDED";

  /**
   * Fired when a command is changed. The value will be the new value of the
   * {@link ca.thurn.noughts.shared.entities.Command}
   */
  public static final String COMMAND_CHANGED = "tin.COMMAND_CHANGED";

  /**
   * Fired when a new game is created. The value will be the newly-created
   * {@link ca.thurn.noughts.shared.entities.Game}.
   */
  public static final String GAME_CREATED = "tin.GAME_CREATED";
}
