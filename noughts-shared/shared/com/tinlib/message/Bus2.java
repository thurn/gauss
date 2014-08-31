package com.tinlib.message;

import java.util.List;

public interface Bus2 {
  public <T> void post(Key<T> key);

  public <T> void post(Key<T> key, T value);

  public <T> void produce(Key<T> key, T value);

  public <T> void invalidate(Key<T> key);

  public Unsubscriber await(Subscriber0 subscriber, Key<?>... keys);

  public <A> Unsubscriber await(Subscriber1<A> subscriber, Key<A> one, Key<?>... rest);

  public <A,B> Unsubscriber await(Subscriber2<A,B> subscriber, Key<A> one, Key<B> two,
      Key<?>... rest);

  public <A,B,C> Unsubscriber await(Subscriber3<A,B,C> subscriber, Key<A> one, Key<B> two,
      Key<C> three, Key<?>... rest);

  public <A,B,C,D> Unsubscriber await(Subscriber4<A,B,C,D> subscriber, Key<A> one, Key<B> two,
      Key<C> three, Key<D> four, Key<?>... rest);

  public <A,B,C,D,E> Unsubscriber await(Subscriber5<A,B,C,D,E> subscriber, Key<A> one, Key<B> two,
      Key<C> three, Key<D> four, Key<E> five, Key<?>... rest);

  public Unsubscriber await(SubscriberX subscriber, Key<?> keys);

  public void once(Subscriber0 subscriber, Key<?>... keys);

  public <A> void once(Subscriber1<A> subscriber, Key<A> one, Key<?>... rest);

  public <A,B> void once(Subscriber2<A,B> subscriber, Key<A> one, Key<B> two, Key<?>... rest);

  public <A,B,C> void once(Subscriber3<A,B,C> subscriber, Key<A> one, Key<B> two, Key<C> three,
      Key<?>... rest);

  public <A,B,C,D> void once(Subscriber4<A,B,C,D> subscriber, Key<A> one, Key<B> two, Key<C> three,
      Key<D> four, Key<?>... rest);

  public <A,B,C,D,E> void once(Subscriber5<A,B,C,D,E> subscriber, Key<A> one, Key<B> two,
      Key<C> three, Key<D> four, Key<E> five, Key<?>... rest);

  public void once(SubscriberX subscriber, List<Key<?>> keys);

  /**
   * Indicates an error has occurred with the provided key. All Subscribers
   * currently waiting on this key will be unregistered and then a
   * RuntimeException will be thrown with the error description.
   *
   * @param key The key which has experienced the error.
   * @param errorMessage A description of the error.
   */
  public <T> void error(Key<T> key, String errorMessage);
}
