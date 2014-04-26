package ca.thurn.noughts.shared;

/**
 * Callback function for subscribing to games by Facebook request ID.
 */
public interface RequestLoadedCallback {
  /**
   * Called when the viewer has been subscribed successfully to a game based on
   * a Facebook request ID.
   *
   * @param gameId ID of game viewer was subscribed to.
   */
  public void onRequestLoaded(String gameId);
}
