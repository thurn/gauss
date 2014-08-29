package com.tinlib.message;

public interface Bus2 {
  public void post(String key);

  public void post(String key, Object value);

  public void produce(String key, Object value);

  public void invalidate(String key);

  public Unsubscriber await(Subscriber0 subscriber, String... keys);

  public <A> Unsubscriber await(Subscriber1<A> subscriber, String... keys);

  public <A,B> Unsubscriber await(Subscriber2<A,B> subscriber, String... keys);

  public <A,B,C> Unsubscriber await(Subscriber3<A,B,C> subscriber, String... keys);

  public <A,B,C,D> Unsubscriber await(Subscriber4<A,B,C,D> subscriber, String... keys);

  public <A,B,C,D,E> Unsubscriber await(Subscriber5<A,B,C,D,E> subscriber, String... keys);

  public void once(Subscriber0 subscriber, String... keys);

  public <A> void once(Subscriber1 subscriber, String... keys);

  public <A,B> void once(Subscriber2<A,B> subscriber, String... keys);

  public <A,B,C> void once(Subscriber3<A,B,C> subscriber, String... keys);

  public <A,B,C,D> void once(Subscriber4<A,B,C,D> subscriber, String... keys);

  public <A,B,C,D,E> void once(Subscriber5<A,B,C,D,E> subscriber, String... keys);

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
