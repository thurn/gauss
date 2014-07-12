package com.tinlib.shared;

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
   * A {@link ErrorService} instance for reporting errors.
   */
  public static final String ERROR_SERVICE = "tin.ERROR_SERVICE";

  /**
   * A {@link com.firebase.client.Firebase} database instance.
   */
  public static final String FIREBASE = "tin.FIREBASE";

  public static final String KEYED_LISTENER_SERVICE = "tin.KEYED_LISTENER_SERVICE";

  /**
   * An {@link AnalyticsService} instance, used to track user events.
   */
  public static final String ANALYTICS_SERVICE = "tin.ANALYTICS_SERVICE";

  /**
   * A {@link PushNotificationService} instance, used
   * to send push notifications.
   */
  public static final String PUSH_NOTIFICATION_SERVICE = "tin.PUSH_NOTIFICATION_SERVICE";
}
