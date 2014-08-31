package com.tinlib.message;

import com.google.common.collect.ImmutableList;

public interface Bus2 {
  public static interface Production {
    public <T> Production addKey(Key<T> key, T value, Key<?>... dependencies);

    public void produce();
  }

  public <T> void post(Key<T> key);

  public <T> void post(Key<T> key, T value);

  public <T> void produce(Key<T> key, T value, Key<?>... dependencies);

  public Production newProduction();

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

  public Unsubscriber await(SubscriberX subscriber, ImmutableList<Key<?>> keys);

  public void once(Subscriber0 subscriber, Key<?>... keys);

  public <A> void once(Subscriber1<A> subscriber, Key<A> one, Key<?>... rest);

  public <A,B> void once(Subscriber2<A,B> subscriber, Key<A> one, Key<B> two, Key<?>... rest);

  public <A,B,C> void once(Subscriber3<A,B,C> subscriber, Key<A> one, Key<B> two, Key<C> three,
      Key<?>... rest);

  public <A,B,C,D> void once(Subscriber4<A,B,C,D> subscriber, Key<A> one, Key<B> two, Key<C> three,
      Key<D> four, Key<?>... rest);

  public <A,B,C,D,E> void once(Subscriber5<A,B,C,D,E> subscriber, Key<A> one, Key<B> two,
      Key<C> three, Key<D> four, Key<E> five, Key<?>... rest);

  public void once(SubscriberX subscriber, ImmutableList<Key<?>> keys);

  /**
   * Indicates an unrecoverable error has occurred producing a key. All
   * Subscribers waiting on this key will be removed. This should only
   * be used if there is no possibility of the key being successfully produced
   * again in the future.
   *
   * @param key The key which has experienced the error.
   */
  public <T> void fail(Key<T> key);
}
