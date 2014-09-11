package com.tinlib.convey2;

import com.google.common.collect.ImmutableList;

public interface Bus {
  public static interface Production {
    public <T> Production addKey(Key<T> key, T value, Key<?>... dependencies);

    public void produce();
  }

  public <T> void post(Key<T> key);

  public <T> void post(Key<T> key, T value);

  public <T> void produce(Key<T> key, T value, Key<?>... dependencies);

  public Production newProduction();

  public Deferred0 await(ImmutableList<Key<?>> keys);

  public <A> Deferred1<A> await(Key<A> one);

  public <A> Deferred1<A> await(Key<A> one, ImmutableList<Key<?>> rest);

  public <A,B> Deferred2<A,B> await(Key<A> one, Key<B> two);

  public <A,B> Deferred2<A,B> await(Key<A> one, Key<B> two, ImmutableList<Key<?>> rest);

  public <A,B,C> Deferred3<A,B,C> await(Key<A> one, Key<B> two, Key<C> three);

  public <A,B,C> Deferred3<A,B,C> await(Key<A> one, Key<B> two, Key<C> three,
      ImmutableList<Key<?>> rest);

  public <A,B,C,D> Unsubscriber await(Key<A> one, Key<B> two, Key<C> three, Key<D> four,
                                      Subscriber4<A, B, C, D> subscriber);

  public <A,B,C,D> Unsubscriber await(Key<A> one, Key<B> two, Key<C> three, Key<D> four,
                                      ImmutableList<Key<?>> rest, Subscriber4<A, B, C, D> subscriber);

  public <A,B,C,D,E> Unsubscriber await(Key<A> one, Key<B> two, Key<C> three, Key<D> four,
                                        Key<E> five, Subscriber5<A, B, C, D, E> subscriber);

  public <A,B,C,D,E> Unsubscriber await(Key<A> one, Key<B> two, Key<C> three, Key<D> four,
                                        Key<E> five, ImmutableList<Key<?>> rest, Subscriber5<A, B, C, D, E> subscriber);

  public Unsubscriber await(ImmutableList<Key<?>> keys, Subscriber subscriber);

  public Deferred0 once(ImmutableList<Key<?>> keys);

  public <A> Deferred1<A> once(Key<A> one);

  public <A> Deferred1<A> once(Key<A> one, ImmutableList<Key<?>> rest);

  public <A,B> Deferred2<A,B> once(Key<A> one, Key<B> two);

  public <A,B> Deferred2<A,B> once(Key<A> one, Key<B> two, ImmutableList<Key<?>> rest);

  public <A,B,C> Deferred3<A,B,C> once(Key<A> one, Key<B> two, Key<C> three);

  public <A,B,C> Deferred3<A,B,C> once(Key<A> one, Key<B> two, Key<C> three,
      ImmutableList<Key<?>> rest);

  public <A,B,C,D> void once(Key<A> one, Key<B> two, Key<C> three, Key<D> four,
                             Subscriber4<A, B, C, D> subscriber);

  public <A,B,C,D> void once(Key<A> one, Key<B> two, Key<C> three, Key<D> four,
                             ImmutableList<Key<?>> rest, Subscriber4<A, B, C, D> subscriber);

  public <A,B,C,D,E> void once(Key<A> one, Key<B> two, Key<C> three, Key<D> four, Key<E> five,
                               Subscriber5<A, B, C, D, E> subscriber);

  public <A,B,C,D,E> void once(Key<A> one, Key<B> two, Key<C> three, Key<D> four, Key<E> five,
                               ImmutableList<Key<?>> rest, Subscriber5<A, B, C, D, E> subscriber);

  public void once(ImmutableList<Key<?>> keys, Subscriber subscriber);

  /**
   * Indicates an unrecoverable error has occurred producing a key. All
   * Subscribers waiting on this key will be removed. This should only
   * be used if there is no possibility of the key being successfully produced
   * again in the future.
   *
   * @param key The key which has experienced the error.
   */
  public <T> void fail(Key<T> key, RuntimeException exception);
}
