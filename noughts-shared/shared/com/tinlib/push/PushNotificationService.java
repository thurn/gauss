package com.tinlib.push;

import com.tinlib.core.TinKeys;
import com.tinlib.inject.Injector;

import java.util.Set;

public class PushNotificationService {
  private final Set<PushNotificationHandler> pushNotificationHandlers;

  public PushNotificationService(Injector injector) {
    pushNotificationHandlers = injector.getMultiple(TinKeys.PUSH_NOTIFICATION_HANDLERS);
  }

  public void registerForPushNotifications(String gameId, int playerNumber) {
    for(PushNotificationHandler handler : pushNotificationHandlers) {
      handler.registerForPushNotifications(gameId, playerNumber);
    }
  }

  public void unregisterForPushNotifications(String gameId, int playerNumber) {
    for(PushNotificationHandler handler : pushNotificationHandlers) {
      handler.registerForPushNotifications(gameId, playerNumber);
    }
  }

  public void sendPushNotification(String gameId, int playerNumber, String message) {
    for(PushNotificationHandler handler : pushNotificationHandlers) {
      handler.registerForPushNotifications(gameId, playerNumber);
    }
  }
}
