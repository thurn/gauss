package ca.thurn.noughts.shared;


public interface PushNotificationListener {
  /**
   * Called when the viewer joins a new game.
   *
   * @param channelId ID of the channel to add.
   */
  public void onJoinedGame(String channelId);

  /**
   * Called when the viewer leaves a game.
   *
   * @param channelId ID of the channel to remove.
   */
  public void onLeftGame(String channelId);

  /**
   * Called when a push notification needs to be sent.
   *
   * @param channeId ID of channel to send the notification out on.
   * @param gameId Associated game ID.
   * @param message Message to associate with the notification.
   */
  public void onPushRequired(String channelId, String gameId, String message);
}
