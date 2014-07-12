package com.tinlib.message;

public interface Subscriber {
  /**
   * Invoked when a message is posted to a {@link com.tinlib.message.Bus} which this Subscriber
   * is registered with.
   *
   * @param name Message name.
   * @param value Optionally, an associated value, or null if no value is
   *     associated with this message.
   */
  public void onMessage(String name, Object value);
}
