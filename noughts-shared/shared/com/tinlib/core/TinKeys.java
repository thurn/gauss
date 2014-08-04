package com.tinlib.core;

import static com.tinlib.util.Identifiers.id;

/**
 * Keys which various components of Tin expect to be able to inject via
 * {@link com.tinlib.inject.Injector#get(String)}. Some are bound to default
 * values by {@link TinModule}, others must be bound by clients.
 */
public class TinKeys {
  /**
   * A {@link com.tinlib.message.Bus} for coordinating messages. Refer to
   * {@link TinMessages} for documentation on possible messages.
   */
  public static final String BUS = id("tin.BUS");

  /**
   * A {@link com.tinlib.error.ErrorService} instance for reporting errors.
   */
  public static final String ERROR_SERVICE = id("tin.ERROR_SERVICE");

  /**
   * A multibinding key for {@link com.tinlib.error.ErrorHandler} implementations. Each Handler
   * will be called by {@link com.tinlib.error.ErrorService} when an error occurs.
   */
  public static final String ERROR_HANDLERS = id("tin.ERROR_HANDLERS");

  /**
   * A {@link com.firebase.client.Firebase} database instance.
   */
  public static final String FIREBASE = id("tin.FIREBASE");

  /**
   * A {@link com.tinlib.services.KeyedListenerService} instance, used to unregister listeners.
   */
  public static final String KEYED_LISTENER_SERVICE = id("tin.KEYED_LISTENER_SERVICE");

  /**
   * An {@link com.tinlib.analytics.AnalyticsService} instance, used to track user events.
   */
  public static final String ANALYTICS_SERVICE = id("tin.ANALYTICS_SERVICE");

  /**
   * A multibinding key for {@link com.tinlib.analytics.AnalyticsHandler}
   * implementations. Each Handler will be called by
   * {@link com.tinlib.analytics.AnalyticsService} in turn.
   */
  public static final String ANALYTICS_HANDLERS = id("tin.ANALYTICS_HANDLERS");

  /**
   * A {@link com.tinlib.push.PushNotificationService} instance, used to send push
   * notifications.
   */
  public static final String PUSH_NOTIFICATION_SERVICE = id("tin.PUSH_NOTIFICATION_SERVICE");

  /**
   * A multibinding key for {@link com.tinlib.push.PushNotificationHandler}
   * implementations. Each Handler will be called by
   * {@link com.tinlib.push.PushNotificationService} in turn.
   */
  public static final String PUSH_NOTIFICATION_HANDLERS = id("tin.PUSH_NOTIFICATION_HANDLERS");

  /**
   * A {@link com.tinlib.services.GameMutator} for mutating games and their current actions.
   */
  public static final String GAME_MUTATOR = id("tin.GAME_MUTATOR");

  /**
   * A {@link com.tinlib.validator.ActionValidatorService} which you can ask to
   * check the legality of game actions by referring to the
   * {@link com.tinlib.validator.ActionValidator} instances bound to the
   * {@link TinKeys#ACTION_VALIDATORS} key.
   */
  public static final String ACTION_VALIDATOR_SERVICE = id("tin.ACTION_VALIDATOR_SERVICE");

  /**
   * A multibinding key for {@link com.tinlib.validator.ActionValidator} instances
   * which will be consulted in turn by
   * {@link com.tinlib.validator.ActionValidatorService} to determine whether game
   * actions are legal.
   */
  public static final String ACTION_VALIDATORS = id("tin.ACTION_VALIDATORS");

  /**
   * A {@link com.tinlib.services.GameOverService} implementation.
   */
  public static final String GAME_OVER_SERVICE = id("tin.GAME_OVER_SERVICE");

  /**
   * A {@link com.tinlib.services.NextPlayerService} implementation.
   */
  public static final String NEXT_PLAYER_SERVICE = id("tin.NEXT_PLAYER_SERVICE");

  /**
   * A {@link com.tinlib.time.TimeService} implementation.
   */
  public static final String TIME_SERVICE = id("tin.TIME_SERVICE");

  /**
   * The {@link com.tinlib.time.LastModifiedService}.
   */
  public static final String LAST_MODIFIED_SERVICE = id("tin.LAST_MODIFIED_SERVICE");

  /**
   * The {@link com.tinlib.services.JoinGameService}.
   */
  public static final String JOIN_GAME_SERVICE = id("tin.JOIN_GAME_SERVICE");

  /**
   * The {@link com.tinlib.services.CurrentGameService}.
   */
  public static final String CURRENT_GAME_SERVICE = id("tin.CURRENT_GAME_SERVICE");

  /**
   * {@link com.tinlib.validator.JoinGameValidatorService}
   */
  public static final String JOIN_GAME_VALIDATOR_SERVICE = id("tin.JOIN_GAME_VALIDATOR_SERVICE");

  /**
   * Multikey for {@link com.tinlib.validator.JoinGameValidator} interfaces.
   */
  public static final String JOIN_GAME_VALIDATORS = id("tin.JOIN_GAME_VALIDATORS");

  /**
   * {@link com.tinlib.services.ViewerService}
   */
  public static final String VIEWER_SERVICE = id("tin.VIEWER_SERVICE");
}
