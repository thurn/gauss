package com.tinlib.core;

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
  public static final String BUS = "tin.BUS";

  /**
   * A {@link com.tinlib.error.ErrorService} instance for reporting errors.
   */
  public static final String ERROR_SERVICE = "tin.ERROR_SERVICE";

  /**
   * A multibinding key for {@link com.tinlib.error.ErrorHandler} implementations. Each Handler
   * will be called by {@link com.tinlib.error.ErrorService} when an error occurs.
   */
  public static final String ERROR_HANDLERS = "tin.ERROR_HANDLERS";

  /**
   * A {@link com.firebase.client.Firebase} database instance.
   */
  public static final String FIREBASE = "tin.FIREBASE";

  /**
   * A {@link com.tinlib.shared.KeyedListenerService} instance, used to unregister listeners.
   */
  public static final String KEYED_LISTENER_SERVICE = "tin.KEYED_LISTENER_SERVICE";

  /**
   * An {@link com.tinlib.analytics.AnalyticsService} instance, used to track user events.
   */
  public static final String ANALYTICS_SERVICE = "tin.ANALYTICS_SERVICE";

  /**
   * A multibinding key for {@link com.tinlib.analytics.AnalyticsHandler}
   * implementations. Each Handler will be called by
   * {@link com.tinlib.analytics.AnalyticsService} in turn.
   */
  public static final String ANALYTICS_HANDLERS = "tin.ANALYTICS_HANDLERS";

  /**
   * A {@link com.tinlib.push.PushNotificationService} instance, used to send push
   * notifications.
   */
  public static final String PUSH_NOTIFICATION_SERVICE = "tin.PUSH_NOTIFICATION_SERVICE";

  /**
   * A multibinding key for {@link com.tinlib.push.PushNotificationHandler}
   * implementations. Each Handler will be called by
   * {@link com.tinlib.push.PushNotificationService} in turn.
   */
  public static final String PUSH_NOTIFICATION_HANDLERS = "tin.PUSH_NOTIFICATION_HANDLERS";

  /**
   * A {@link com.tinlib.shared.GameMutator} for mutating games and their current actions.
   */
  public static final String GAME_MUTATOR = "tin.GAME_MUTATOR";

  /**
   * A {@link com.tinlib.action.validator.ActionValidatorService} which you can ask to
   * check the legality of game actions by referring to the
   * {@link com.tinlib.action.validator.ActionValidator} instances bound to the
   * {@link TinKeys#ACTION_VALIDATORS} key.
   */
  public static final String ACTION_VALIDATOR_SERVICE = "tin.ACTION_VALIDATOR_SERVICE";

  /**
   * A multibinding key for {@link com.tinlib.action.validator.ActionValidator} instances
   * which will be consulted in turn by
   * {@link com.tinlib.action.validator.ActionValidatorService} to determine whether game
   * actions are legal.
   */
  public static final String ACTION_VALIDATORS = "tin.ACTION_VALIDATORS";

  /**
   * A {@link com.tinlib.shared.GameOverService} implementation.
   */
  public static final String GAME_OVER_SERVICE = "tin.GAME_OVER_SERVICE";

  /**
   * A {@link com.tinlib.shared.NextPlayerService} implementation.
   */
  public static final String NEXT_PLAYER_SERVICE = "tin.NEXT_PLAYER_SERVICE";

  /**
   * A {@link com.tinlib.time.TimeService} implementation.
   */
  public static final String TIME_SERVICE = "tin.TIME_SERVICE";
}
