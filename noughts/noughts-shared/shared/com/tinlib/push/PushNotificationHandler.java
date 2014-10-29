package com.tinlib.push;

public interface PushNotificationHandler {
  public void registerForPushNotifications(String gameId, int playerNumber);

  public void unregisterForPushNotifications(String gameId, int playerNumber);

  public void sendPushNotification(String gameId, int playerNumber, String message);
}
