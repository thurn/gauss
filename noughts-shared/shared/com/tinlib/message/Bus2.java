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

  public Unsubscriber await(Key<?> key, Subscriber0 subscriber);

  public Unsubscriber await(ImmutableList<Key<?>> keys, Subscriber0 subscriber);

  public <A> Unsubscriber await(Key<A> one, Subscriber1<A> subscriber);

  public <A> Unsubscriber await(Key<A> one, ImmutableList<Key<?>> rest, Subscriber1<A> subscriber);

  public <A,B> Unsubscriber await(Key<A> one, Key<B> two, Subscriber2<A, B> subscriber);

  public <A,B> Unsubscriber await(Key<A> one, Key<B> two, ImmutableList<Key<?>> rest,
      Subscriber2<A, B> subscriber);

  public <A,B,C> Unsubscriber await(Key<A> one, Key<B> two, Key<C> three,
      Subscriber3<A, B, C> subscriber);

  public <A,B,C> Unsubscriber await(Key<A> one, Key<B> two, Key<C> three,
      ImmutableList<Key<?>> rest, Subscriber3<A, B, C> subscriber);

  public <A,B,C,D> Unsubscriber await(Key<A> one, Key<B> two, Key<C> three, Key<D> four,
      Subscriber4<A, B, C, D> subscriber);

  public <A,B,C,D> Unsubscriber await(Key<A> one, Key<B> two, Key<C> three, Key<D> four,
      ImmutableList<Key<?>> rest, Subscriber4<A, B, C, D> subscriber);

  public <A,B,C,D,E> Unsubscriber await(Key<A> one, Key<B> two, Key<C> three, Key<D> four,
      Key<E> five, Subscriber5<A, B, C, D, E> subscriber);

  public <A,B,C,D,E> Unsubscriber await(Key<A> one, Key<B> two, Key<C> three, Key<D> four,
      Key<E> five, ImmutableList<Key<?>> rest, Subscriber5<A, B, C, D, E> subscriber);

  public Unsubscriber await(ImmutableList<Key<?>> keys, SubscriberX subscriber);

  public void once(Key<?> key, Subscriber0 subscriber);

  public void once(ImmutableList<Key<?>> keys, Subscriber0 subscriber);

  public <A> void once(Key<A> one, Subscriber1<A> subscriber);

  public <A> void once(Key<A> one, ImmutableList<Key<?>> rest, Subscriber1<A> subscriber);

  public <A,B> void once(Key<A> one, Key<B> two, Subscriber2<A, B> subscriber);

  public <A,B> void once(Key<A> one, Key<B> two, ImmutableList<Key<?>> rest,
      Subscriber2<A, B> subscriber);

  public <A,B,C> void once(Key<A> one, Key<B> two, Key<C> three, Subscriber3<A, B, C> subscriber);

  public <A,B,C> void once(Key<A> one, Key<B> two, Key<C> three, ImmutableList<Key<?>> rest,
      Subscriber3<A, B, C> subscriber);

  public <A,B,C,D> void once(Key<A> one, Key<B> two, Key<C> three, Key<D> four,
      Subscriber4<A, B, C, D> subscriber);

  public <A,B,C,D> void once(Key<A> one, Key<B> two, Key<C> three, Key<D> four,
      ImmutableList<Key<?>> rest, Subscriber4<A, B, C, D> subscriber);

  public <A,B,C,D,E> void once(Key<A> one, Key<B> two, Key<C> three, Key<D> four, Key<E> five,
      Subscriber5<A, B, C, D, E> subscriber);

  public <A,B,C,D,E> void once(Key<A> one, Key<B> two, Key<C> three, Key<D> four, Key<E> five,
      ImmutableList<Key<?>> rest, Subscriber5<A, B, C, D, E> subscriber);

  public void once(ImmutableList<Key<?>> keys, SubscriberX subscriber);

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
