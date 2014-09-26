package com.tinlib.convey;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.*;

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

  private class MessageHandler {
    private final ImmutableList<Key<?>> keys;
    private final Map<Key<?>, Optional<?>> requirements = Maps.newHashMap();
    private final boolean once;
    private final Subscriber subscriber;

    public MessageHandler(boolean once, ImmutableList<Key<?>> keys, Subscriber subscriber) {
      this.once = once;
      this.subscriber = subscriber;
      this.keys = keys;

      for (Key<?> key : keys) {
        if (producedValues.containsKey(key)) {
          requirements.put(key, Optional.of(producedValues.get(key)));
        }
      }

      if (allSatisfied()) {
        subscriber.onMessage(realizeMap());
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
        subscriber.onMessage(realizeMap());
        return once ? Optional.of(createUnsubscriber()) : Optional.<Unsubscriber>absent();
      }
      return Optional.absent();
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
    for (MessageHandler messageHandler : messageHandlers.get(key)) {
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
  public synchronized Unsubscriber await(ImmutableList<Key<?>> keys, Subscriber subscriber) {
    MessageHandler messageHandler = new MessageHandler(false /* once */, keys, subscriber);
    return messageHandler.createUnsubscriber();
  }

  @Override
  public void once(Key<?> key, Subscriber0 subscriber) {
    once(ImmutableList.<Key<?>>of(key),subscriber);
  }

  @Override
  @SuppressWarnings("unchecked")
  public void once(ImmutableList<Key<?>> keys, final Subscriber0 subscriber) {
    once(keyList(keys), new Subscriber() {
      @Override
      public void onMessage(ImmutableMap<Key<?>, Object> map) {
        subscriber.onMessage();
      }
    });
  }

  @Override
  public <A> void once(Key<A> one, Subscriber1<A> subscriber) {
    once(one, ImmutableList.<Key<?>>of(), subscriber);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <A> void once(final Key<A> one, ImmutableList<Key<?>> rest,
      final Subscriber1<A> subscriber) {
    once(keyList(rest, one), new Subscriber() {
      @Override
      public void onMessage(ImmutableMap<Key<?>, Object> map) {
        subscriber.onMessage((A)map.get(one));
      }
    });
  }

  @Override
  public <A, B> void once(Key<A> one, Key<B> two, Subscriber2<A, B> subscriber) {
    once(one, two, ImmutableList.<Key<?>>of(), subscriber);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <A, B> void once(final Key<A> one, final Key<B> two, ImmutableList<Key<?>> rest,
      final Subscriber2<A, B> subscriber) {
    once(keyList(rest, one, two), new Subscriber() {
      @Override
      public void onMessage(ImmutableMap<Key<?>, Object> map) {
        subscriber.onMessage((A)map.get(one), (B)map.get(two));
      }
    });
  }

  @Override
  public <A, B, C> void once(Key<A> one, Key<B> two, Key<C> three,
      Subscriber3<A, B, C> subscriber) {
    once(one, two, three, ImmutableList.<Key<?>>of(), subscriber);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <A, B, C> void once(final Key<A> one, final Key<B> two, final Key<C> three,
      ImmutableList<Key<?>> rest, final Subscriber3<A, B, C> subscriber) {
    once(keyList(rest, one, two, three), new Subscriber() {
      @Override
      public void onMessage(ImmutableMap<Key<?>, Object> map) {
        subscriber.onMessage((A)map.get(one), (B)map.get(two), (C)map.get(three));
      }
    });
  }

  @Override
  public <A, B, C, D> void once(Key<A> one, Key<B> two, Key<C> three, Key<D> four,
      Subscriber4<A, B, C, D> subscriber) {
    once(one, two, three, four, ImmutableList.<Key<?>>of(), subscriber);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <A, B, C, D> void once(final Key<A> one, final Key<B> two, final Key<C> three,
      final Key<D> four, ImmutableList<Key<?>> rest, final Subscriber4<A, B, C, D> subscriber) {
    once(keyList(rest, one, two, three, four), new Subscriber() {
      @Override
      public void onMessage(ImmutableMap<Key<?>, Object> map) {
        subscriber.onMessage((A)map.get(one), (B)map.get(two), (C)map.get(three), (D)map.get(four));
      }
    });
  }

  @Override
  public <A, B, C, D, E> void once(Key<A> one, Key<B> two, Key<C> three, Key<D> four, Key<E> five,
      Subscriber5<A, B, C, D, E> subscriber) {
    once(one, two, three, four, five, ImmutableList.<Key<?>>of(), subscriber);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <A, B, C, D, E> void once(final Key<A> one, final Key<B> two, final Key<C> three,
      final Key<D> four, final Key<E> five, ImmutableList<Key<?>> rest,
      final Subscriber5<A, B, C, D, E> subscriber) {
    once(keyList(rest, one, two, three, four, five), new Subscriber() {
      @Override
      public void onMessage(ImmutableMap<Key<?>, Object> map) {
        subscriber.onMessage((A)map.get(one), (B)map.get(two), (C)map.get(three), (D)map.get(four),
            (E)map.get(five));
      }
    });
  }

  @Override
  public synchronized void once(ImmutableList<Key<?>> keys, Subscriber subscriber) {
    new MessageHandler(true /* once */,  keys, subscriber);
  }

  private ImmutableList<Key<?>> keyList(ImmutableList<Key<?>> rest, Key<?>... keys) {
    return ImmutableList.<Key<?>>builder()
        .addAll(rest)
        .addAll(Arrays.asList(keys))
        .build();
  }
}
