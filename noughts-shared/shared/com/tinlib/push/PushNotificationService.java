package com.tinlib.push;

import com.tinlib.core.TinKeys2;
import com.tinlib.infuse.Injector;

import java.util.Set;

public class PushNotificationService {
  private final Set<PushNotificationHandler> pushNotificationHandlers;

  public PushNotificationService(Injector injector) {
    pushNotificationHandlers = injector.getMultiple(TinKeys2.PUSH_NOTIFICATION_HANDLERS);
  }

  public void registerForPushNotifications(String gameId, int playerNumber) {
    for(PushNotificationHandler handler : pushNotificationHandlers) {
      handler.registerForPushNotifications(gameId, playerNumber);
    }
  }

  public void unregisterForPushNotifications(String gameId, int playerNumber) {
    for(PushNotificationHandler handler : pushNotificationHandlers) {
      handler.unregisterForPushNotifications(gameId, playerNumber);
    }
  }

  public void sendPushNotification(String gameId, int playerNumber, String message) {
    for(PushNotificationHandler handler : pushNotificationHandlers) {
      handler.sendPushNotification(gameId, playerNumber, message);
    }
  }
}
