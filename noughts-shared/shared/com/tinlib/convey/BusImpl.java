package com.tinlib.convey;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.*;
import com.tinlib.defer.Deferred;
import com.tinlib.defer.Deferreds;
import com.tinlib.defer.Promise;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

class BusImpl implements Bus {
  private static class Value<T> {
    private final T value;
    private final List<Key<?>> dependencies;

    public Value(T value, List<Key<?>> dependencies) {
      this.value = value;
      this.dependencies = dependencies;
    }
  }

  private class ProductionImpl implements Production {
    private final Map<Key<?>,Value<?>> values = Maps.newHashMap();

    @Override
    public <T> Production addKey(Key<T> key, T value, Key<?>... dependencies) {
      Preconditions.checkNotNull(value);
      values.put(key, new Value<>(value, Arrays.asList(dependencies)));
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void produce() {
      for (Key<?> key : values.keySet()) {
        setProducedValue(key, values.get(key).value, values.get(key).dependencies);
      }
      for (Key<?> key : values.keySet()) {
        fireHandlers((Key<Object>)key, Optional.of(values.get(key).value));
      }
    }
  }

  private class MessageHandler<V> {
    private final ImmutableList<Key<?>> keys;
    private final Map<Key<?>, Optional<?>> requirements = Maps.newHashMap();
    private final boolean once;
    private final Callback<V> callback;
    private final Optional<Deferred<V>> deferred;

    public MessageHandler(boolean once, ImmutableList<Key<?>> keys, Optional<Deferred<V>> deferred,
        Callback<V> callback) {
      this.once = once;
      this.callback = callback;
      this.keys = keys;
      this.deferred = deferred;

      for (Key<?> key : keys) {
        if (producedValues.containsKey(key)) {
          requirements.put(key, Optional.of(producedValues.get(key)));
        }
      }

      if (allSatisfied()) {
        chainDeferred(callback.call(realizeMap()));
      }

      if (!(allSatisfied() && once)) {
        for (Key<?> key : keys) {
          messageHandlers.put(key, this);
        }
      }
    }

    public <T> Optional<Unsubscriber> handle(Key<T> key, Optional<T> object) {
      requirements.put(key, object);
      if (allSatisfied()) {
        chainDeferred(callback.call(realizeMap()));
        return once ? Optional.of(createUnsubscriber()) : Optional.<Unsubscriber>absent();
      }
      return Optional.absent();
    }

    private void chainDeferred(Promise<V> promise) {
      if (deferred.isPresent()) {
        deferred.get().chainFrom(promise);
      }
    }

    public Unsubscriber createUnsubscriber() {
      return new Unsubscriber() {
        @Override
        public void unsubscribe() {
          removeMessageHandler(keys, MessageHandler.this);
        }
      };
    }

    private ImmutableMap<Key<?>, Object> realizeMap() {
      ImmutableMap.Builder<Key<?>, Object> builder = ImmutableMap.builder();
      for (Map.Entry<Key<?>, Optional<?>> entry : requirements.entrySet()) {
        if (entry.getValue().isPresent()) {
          builder.put(entry.getKey(), entry.getValue().get());
        }
      }
      return builder.build();
    }

    private boolean allSatisfied() {
      for (Key<?> key : keys) {
        if (!requirements.containsKey(key)) return false;
      }
      return true;
    }
  }

  private final SetMultimap<Key<?>, MessageHandler> messageHandlers = HashMultimap.create();
  private final SetMultimap<Key<?>, Key<?>> derivedKeys = HashMultimap.create();
  private final Map<Key<?>, Object> producedValues = Maps.newHashMap();

  @Override
  public <T> void post(Key<T> key) {
    fireHandlers(key, Optional.<T>absent());
  }

  @Override
  public <T> void post(Key<T> key, T value) {
    Preconditions.checkNotNull(value);
    fireHandlers(key, Optional.of(value));
  }

  @Override
  public <T> void produce(Key<T> key, T value, Key<?>... dependencies) {
    Preconditions.checkNotNull(value);
    setProducedValue(key, value, Arrays.asList(dependencies));
    fireHandlers(key, Optional.of(value));
  }

  @Override
  public Production newProduction() {
    return new ProductionImpl();
  }

  private void invalidate(Key<?> key) {
    for (Key<?> derived : derivedKeys.get(key)) {
      invalidate(derived);
    }
    producedValues.remove(key);
  }

  private synchronized void setProducedValue(Key<?> key, Object value, List<Key<?>> dependencies) {
    invalidate(key);

    producedValues.put(key, value);

    for (Key<?> dependency : dependencies) {
      derivedKeys.put(dependency, key);
    }
  }

  private synchronized <T> void fireHandlers(Key<T> key, Optional<T> value) {
    List<Unsubscriber> toRemove = Lists.newArrayList();
    for (MessageHandler<?> messageHandler : messageHandlers.get(key)) {
      Optional<Unsubscriber> unsubscriber = messageHandler.handle(key, value);
      if (unsubscriber.isPresent()) {
        toRemove.add(unsubscriber.get());
      }
    }

    // We unsubscribe after invoking all the handlers to avoid ConcurrentModificationException
    for (Unsubscriber unsubscriber : toRemove) {
      unsubscriber.unsubscribe();
    }
  }

  private synchronized void removeMessageHandler(List<Key<?>> keys, MessageHandler handler) {
    for (Key<?> key : keys) {
      messageHandlers.remove(key, handler);
    }
  }

  @Override
  public Unsubscriber await(Key<?> key, Subscriber0 subscriber) {
    return await(ImmutableList.<Key<?>>of(key), subscriber);
  }

  @Override
  public Unsubscriber await(ImmutableList<Key<?>> keys, final Subscriber0 subscriber) {
    return await(keyList(keys), new Subscriber() {
      @Override
      public void onMessage(ImmutableMap<Key<?>, Object> map) {
        subscriber.onMessage();
      }
    });
  }

  @Override
  public <A> Unsubscriber await(Key<A> one, Subscriber1<A> subscriber) {
    return await(one, ImmutableList.<Key<?>>of(), subscriber);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <A> Unsubscriber await(final Key<A> one, ImmutableList<Key<?>> rest,
      final Subscriber1<A> subscriber) {
    return await(keyList(rest, one), new Subscriber() {
      @Override
      public void onMessage(ImmutableMap<Key<?>, Object> map) {
        subscriber.onMessage((A) map.get(one));
      }
    });
  }

  @Override
  public <A, B> Unsubscriber await(Key<A> one, Key<B> two, Subscriber2<A, B> subscriber) {
    return await(one, two, ImmutableList.<Key<?>>of(), subscriber);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <A, B> Unsubscriber await(final Key<A> one, final Key<B> two, ImmutableList<Key<?>> rest,
      final Subscriber2<A, B> subscriber) {
    return await(keyList(rest, one, two), new Subscriber() {
      @Override
      public void onMessage(ImmutableMap<Key<?>, Object> map) {
        subscriber.onMessage((A) map.get(one), (B) map.get(two));
      }
    });
  }

  @Override
  public <A, B, C> Unsubscriber await(Key<A> one, Key<B> two, Key<C> three,
      Subscriber3<A, B, C> subscriber) {
    return await(one, two, three, ImmutableList.<Key<?>>of(), subscriber);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <A, B, C> Unsubscriber await(final Key<A> one, final Key<B> two, final Key<C> three,
      ImmutableList<Key<?>> rest, final Subscriber3<A, B, C> subscriber) {
    return await(keyList(rest, one, two, three), new Subscriber() {
      @Override
      public void onMessage(ImmutableMap<Key<?>, Object> map) {
        subscriber.onMessage((A) map.get(one), (B) map.get(two), (C) map.get(three));
      }
    });
  }

  @Override
  public <A, B, C, D> Unsubscriber await(Key<A> one, Key<B> two, Key<C> three, Key<D> four,
      Subscriber4<A, B, C, D> subscriber) {
    return await(one, two, three, four, ImmutableList.<Key<?>>of(), subscriber);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <A, B, C, D> Unsubscriber await(final Key<A> one, final Key<B> two, final Key<C> three,
      final Key<D> four, ImmutableList<Key<?>> rest, final Subscriber4<A, B, C, D> subscriber) {
    return await(keyList(rest, one, two, three, four), new Subscriber() {
      @Override
      public void onMessage(ImmutableMap<Key<?>, Object> map) {
        subscriber.onMessage((A) map.get(one), (B) map.get(two), (C) map.get(three),
            (D) map.get(four));
      }
    });
  }

  @Override
  public <A, B, C, D, E> Unsubscriber await(Key<A> one, Key<B> two, Key<C> three, Key<D> four,
      Key<E> five, Subscriber5<A, B, C, D, E> subscriber) {
    return await(one, two, three, four, five, ImmutableList.<Key<?>>of(), subscriber);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <A, B, C, D, E> Unsubscriber await(final Key<A> one, final Key<B> two, final Key<C> three,
      final Key<D> four, final Key<E> five, ImmutableList<Key<?>> rest,
      final Subscriber5<A, B, C, D, E> subscriber) {
    return await(keyList(rest, one, two, three, four, five), new Subscriber() {
      @Override
      public void onMessage(ImmutableMap<Key<?>, Object> map) {
        subscriber.onMessage((A) map.get(one), (B) map.get(two), (C) map.get(three),
            (D) map.get(four), (E) map.get(five));
      }
    });
  }

  @Override
  public synchronized Unsubscriber await(ImmutableList<Key<?>> keys, final Subscriber subscriber) {
    MessageHandler<Void> messageHandler = new MessageHandler<>(false /* once */, keys,
    Optional.<Deferred<Void>>absent(), new Callback<Void>() {
      @Override
      public Promise<Void> call(ImmutableMap<Key<?>, Object> map) {
        subscriber.onMessage(map);
        return null;
      }
    });
    return messageHandler.createUnsubscriber();
  }

  @Override
  public <V> Promise<V> once(Key<?> key, Callback0<V> callback) {
    return once(ImmutableList.<Key<?>>of(key), callback);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <V> Promise<V> once(ImmutableList<Key<?>> keys, final Callback0<V> callback) {
    return once(keyList(keys), new Callback<V>() {
      @Override
      public Promise<V> call(ImmutableMap<Key<?>, Object> map) {
        return callback.call();
      }
    });
  }

  @Override
  public <V, A> Promise<V> once(Key<A> one, Callback1<V, A> callback) {
    return once(one, ImmutableList.<Key<?>>of(), callback);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <V, A> Promise<V> once(final Key<A> one, ImmutableList<Key<?>> rest,
      final Callback1<V, A> callback) {
    return once(keyList(rest, one), new Callback<V>() {
      @Override
      public Promise<V> call(ImmutableMap<Key<?>, Object> map) {
        return callback.call((A)map.get(one));
      }
    });
  }

  @Override
  public <V, A, B> Promise<V> once(Key<A> one, Key<B> two, Callback2<V, A, B> callback) {
    return once(one, two, ImmutableList.<Key<?>>of(), callback);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <V, A, B> Promise<V> once(final Key<A> one, final Key<B> two, ImmutableList<Key<?>> rest,
      final Callback2<V, A, B> callback) {
    return once(keyList(rest, one, two), new Callback<V>() {
      @Override
      public Promise<V> call(ImmutableMap<Key<?>, Object> map) {
        return callback.call((A)map.get(one), (B)map.get(two));
      }
    });
  }

  @Override
  public <V, A, B, C> Promise<V> once(Key<A> one, Key<B> two, Key<C> three,
      Callback3<V, A, B, C> callback) {
    return once(one, two, three, ImmutableList.<Key<?>>of(), callback);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <V, A, B, C> Promise<V> once(final Key<A> one, final Key<B> two, final Key<C> three,
      ImmutableList<Key<?>> rest, final Callback3<V, A, B, C> callback) {
    return once(keyList(rest, one, two, three), new Callback<V>() {
      @Override
      public Promise<V> call(ImmutableMap<Key<?>, Object> map) {
        return callback.call((A)map.get(one), (B)map.get(two), (C)map.get(three));
      }
    });
  }

  @Override
  public <V, A, B, C, D> Promise<V> once(Key<A> one, Key<B> two, Key<C> three, Key<D> four,
      Callback4<V, A, B, C, D> callback) {
    return once(one, two, three, four, ImmutableList.<Key<?>>of(), callback);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <V, A, B, C, D> Promise<V> once(final Key<A> one, final Key<B> two, final Key<C> three,
      final Key<D> four, ImmutableList<Key<?>> rest, final Callback4<V, A, B, C, D> callback) {
    return once(keyList(rest, one, two, three, four), new Callback<V>() {
      @Override
      public Promise<V> call(ImmutableMap<Key<?>, Object> map) {
        return callback.call((A)map.get(one), (B)map.get(two), (C)map.get(three), (D)map.get(four));
      }
    });
  }

  @Override
  public <V, A, B, C, D, E> Promise<V> once(Key<A> one, Key<B> two, Key<C> three, Key<D> four,
      Key<E> five, Callback5<V, A, B, C, D, E> callback) {
    return once(one, two, three, four, five, ImmutableList.<Key<?>>of(), callback);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <V, A, B, C, D, E> Promise<V> once(final Key<A> one, final Key<B> two, final Key<C> three,
      final Key<D> four, final Key<E> five, ImmutableList<Key<?>> rest,
      final Callback5<V, A, B, C, D, E> callback) {
    return once(keyList(rest, one, two, three, four, five), new Callback<V>() {
      @Override
      public Promise<V> call(ImmutableMap<Key<?>, Object> map) {
        return callback.call((A)map.get(one), (B)map.get(two), (C)map.get(three),
            (D)map.get(four), (E)map.get(five));
      }
    });
  }

  @Override
  public synchronized <V> Promise<V> once(ImmutableList<Key<?>> keys, Callback<V> callback) {
    Deferred<V> result = Deferreds.newDeferred();
    new MessageHandler<>(true /* once */,  keys, Optional.of(result), callback);
    return result;
  }

  private ImmutableList<Key<?>> keyList(ImmutableList<Key<?>> rest, Key<?>... keys) {
    return ImmutableList.<Key<?>>builder()
        .addAll(rest)
        .addAll(Arrays.asList(keys))
        .build();
  }

  @Override
  public synchronized <T> void fail(Key<T> key) {
    for (MessageHandler handler : messageHandlers.get(key)) {
      handler.createUnsubscriber().unsubscribe();
    }
  }
}
