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
}
