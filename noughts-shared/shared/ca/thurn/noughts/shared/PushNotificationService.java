package ca.thurn.noughts.shared;

import java.util.Map;


public interface PushNotificationService {
  /**
   * Called to subscribe the user to a new channel.
   *
   * @param channelId ID of the channel to add.
   */
  public void addChannel(String channelId);

  /**
   * Called to unsubscribe the user from a channel.
   *
   * @param channelId ID of the channel to remove.
   */
  public void removeChannel(String channelId);

  /**
   * Called when a push notification needs to be sent.
   *
   * @param channeId ID of channel to send the notification out on.
   * @param data Data for the notification
   */
  public void sendPushNotification(String channelId, Map<String, String> data);
}
