package com.tinlib.message;

public interface Bus2 {
  public void post(String key);

  public void post(String key, Object value);

  public void produce(String key, Object value);

  public void invalidate(String key);

  public Unsubscriber await(Subscriber0 subscriber, String... keys);

  public Unsubscriber await(Subscriber1 subscriber, String... keys);

  public Unsubscriber await(Subscriber2 subscriber, String... keys);

  public Unsubscriber await(Subscriber3 subscriber, String... keys);

  public Unsubscriber await(Subscriber4 subscriber, String... keys);

  public Unsubscriber await(Subscriber5 subscriber, String... keys);

  public void once(Subscriber0 subscriber, String... keys);

  public void once(Subscriber1 subscriber, String... keys);

  public void once(Subscriber2 subscriber, String... keys);

  public void once(Subscriber3 subscriber, String... keys);

  public void once(Subscriber4 subscriber, String... keys);

  public void once(Subscriber5 subscriber, String... keys);

  /**
   * Indicates an error has occurred with the provided key. All Subscribers
   * currently waiting on this key will be unregistered and then a
   * RuntimeException will be thrown with the error description.
   *
   * @param key The key which has experienced the error.
   * @param errorMessage A description of the error.
   */
  public void error(String key, String errorMessage);
}
