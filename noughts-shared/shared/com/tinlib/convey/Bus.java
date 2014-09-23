package com.tinlib.convey;

import com.google.common.collect.ImmutableList;
import com.tinlib.defer.Promise;

public interface Bus {
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

  public Unsubscriber await(ImmutableList<Key<?>> keys, Subscriber subscriber);

  public <V> Promise<V> once(Key<?> key, Callback0<V> subscriber);

  public <V> Promise<V> once(ImmutableList<Key<?>> keys, Callback0<V> subscriber);

  public <V,A> Promise<V> once(Key<A> one, Callback1<V,A> subscriber);

  public <V,A> Promise<V> once(Key<A> one, ImmutableList<Key<?>> rest, Callback1<V,A> subscriber);

  public <V,A,B> Promise<V> once(Key<A> one, Key<B> two, Callback2<V,A,B> subscriber);

  public <V,A,B> Promise<V> once(Key<A> one, Key<B> two, ImmutableList<Key<?>> rest,
      Callback2<V,A,B> subscriber);

  public <V,A,B,C> Promise<V> once(Key<A> one, Key<B> two, Key<C> three,
      Callback3<V,A,B,C> subscriber);

  public <V,A,B,C> Promise<V> once(Key<A> one, Key<B> two, Key<C> three,
      ImmutableList<Key<?>> rest, Callback3<V,A,B,C> subscriber);

  public <V,A,B,C,D> Promise<V> once(Key<A> one, Key<B> two, Key<C> three, Key<D> four,
      Callback4<V,A,B,C,D> subscriber);

  public <V,A,B,C,D> Promise<V> once(Key<A> one, Key<B> two, Key<C> three, Key<D> four,
      ImmutableList<Key<?>> rest, Callback4<V,A,B,C,D> subscriber);

  public <V,A,B,C,D,E> Promise<V> once(Key<A> one, Key<B> two, Key<C> three, Key<D> four,
      Key<E> five, Callback5<V,A,B,C,D,E> subscriber);

  public <V,A,B,C,D,E> Promise<V> once(Key<A> one, Key<B> two, Key<C> three, Key<D> four,
      Key<E> five, ImmutableList<Key<?>> rest, Callback5<V,A,B,C,D,E> subscriber);

  public <V> Promise<V> once(ImmutableList<Key<?>> keys, Callback<V> subscriber);

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
