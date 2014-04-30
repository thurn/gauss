package ca.thurn.noughts.shared;

import java.util.List;

public interface PushNotificationListener {
  /**
   * Called when the viewer joins a new game.
   *
   * @param channelIds IDs of the channels to add.
   */
  public void onJoinedGame(List<String> channelIds);
  
  /**
   * Called when the viewer leaves a game.
   *
   * @param channelIds IDs of the channels to remove.
   */
  public void onLeftGame(List<String> channelIds);
  
  /**
   * Called when a push notification needs to be sent.
   *
   * @param channeId ID of channel to send the notification out on.
   * @param gameId Associated game ID.
   * @param message Message to associate with the notification.
   */
  public void onPushRequired(String channelId, String gameId, String message);
}
