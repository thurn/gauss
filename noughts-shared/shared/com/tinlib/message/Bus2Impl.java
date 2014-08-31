package com.tinlib.message;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Bus2Impl implements Bus2 {
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
    private final SubscriberX subscriberX;

    public MessageHandler(boolean once, ImmutableList<Key<?>> keys, SubscriberX subscriberX) {
      this.once = once;
      this.subscriberX = subscriberX;
      this.keys = keys;

      for (Key<?> key : keys) {
        if (producedValues.containsKey(key)) {
          requirements.put(key, Optional.of(producedValues.get(key)));
        }
      }

      if (allSatisfied()) {
        subscriberX.onMessage(realizeMap());
      }

      if (!(allSatisfied() && once)) {
        for (Key<?> key : keys) {
          messageHandlers.put(key, this);
        }
      }
    }

    public <T> void handle(Key<T> key, Optional<T> object) {
      requirements.put(key, object);
      if (allSatisfied()) {
        subscriberX.onMessage(realizeMap());
        if (once) {
          createUnsubscriber().unsubscribe();
        }
      }
    }

    public Unsubscriber createUnsubscriber() {
      return new Unsubscriber() {
        @Override
        public void unsubscribe() {
          for (Key<?> key : keys) {
            messageHandlers.remove(key, this);
          }
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

  private void setProducedValue(Key<?> key, Object value, List<Key<?>> dependencies) {
    for (Key<?> derived : derivedKeys.get(key)) {
      producedValues.remove(derived);
    }

    producedValues.put(key, value);

    for (Key<?> dependency : dependencies) {
      derivedKeys.put(dependency, key);
    }
  }

  private <T> void fireHandlers(Key<T> key, Optional<T> value) {
    Set<MessageHandler> handlerSet = messageHandlers.get(key);
    for (MessageHandler messageHandler : handlerSet) {
      messageHandler.handle(key, value);
    }
  }

  @Override
  public Unsubscriber await(final Subscriber0 subscriber, Key<?>... keys) {
    return await(new SubscriberX() {
      @Override
      public void onMessage(ImmutableMap<Key<?>, Object> map) {
        subscriber.onMessage();
      }
    }, keyList(keys));
  }

  @Override
  @SuppressWarnings("unchecked")
  public <A> Unsubscriber await(final Subscriber1<A> subscriber, final Key<A> one, Key<?>... rest) {
    return await(new SubscriberX() {
      @Override
      public void onMessage(ImmutableMap<Key<?>, Object> map) {
        subscriber.onMessage((A) map.get(one));
      }
    }, keyList(rest, one));
  }

  @Override
  @SuppressWarnings("unchecked")
  public <A, B> Unsubscriber await(final Subscriber2<A, B> subscriber, final Key<A> one,
      final Key<B> two, Key<?>... rest) {
    return await(new SubscriberX() {
      @Override
      public void onMessage(ImmutableMap<Key<?>, Object> map) {
        subscriber.onMessage((A) map.get(one), (B) map.get(two));
      }
    }, keyList(rest, one, two));
  }

  @Override
  @SuppressWarnings("unchecked")
  public <A, B, C> Unsubscriber await(final Subscriber3<A, B, C> subscriber, final Key<A> one,
      final Key<B> two, final Key<C> three, Key<?>... rest) {
    return await(new SubscriberX() {
      @Override
      public void onMessage(ImmutableMap<Key<?>, Object> map) {
        subscriber.onMessage((A) map.get(one), (B) map.get(two), (C) map.get(three));
      }
    }, keyList(rest, one, two, three));
  }

  @Override
  @SuppressWarnings("unchecked")
  public <A, B, C, D> Unsubscriber await(final Subscriber4<A, B, C, D> subscriber, final Key<A> one,
      final Key<B> two, final Key<C> three, final Key<D> four, Key<?>... rest) {
    return await(new SubscriberX() {
      @Override
      public void onMessage(ImmutableMap<Key<?>, Object> map) {
        subscriber.onMessage((A) map.get(one), (B) map.get(two), (C) map.get(three), (D) map.get(four));
      }
    }, keyList(rest, one, two, three, four));
  }

  @Override
  @SuppressWarnings("unchecked")
  public <A, B, C, D, E> Unsubscriber await(final Subscriber5<A, B, C, D, E> subscriber,
      final Key<A> one, final Key<B> two, final Key<C> three, final Key<D> four, final Key<E> five,
      Key<?>... rest) {
    return await(new SubscriberX() {
      @Override
      public void onMessage(ImmutableMap<Key<?>, Object> map) {
        subscriber.onMessage((A) map.get(one), (B) map.get(two), (C) map.get(three), (D) map.get(four),
            (E) map.get(five));
      }
    }, keyList(rest, one, two, three, four, five));
  }

  @Override
  public Unsubscriber await(SubscriberX subscriber, ImmutableList<Key<?>> keys) {
    MessageHandler messageHandler = new MessageHandler(false /* once */, keys, subscriber);
    return messageHandler.createUnsubscriber();
  }

  @Override
  @SuppressWarnings("unchecked")
  public void once(final Subscriber0 subscriber, Key<?>... keys) {
    once(new SubscriberX() {
      @Override
      public void onMessage(ImmutableMap<Key<?>, Object> map) {
        subscriber.onMessage();
      }
    }, keyList(keys));
  }

  @Override
  @SuppressWarnings("unchecked")
  public <A> void once(final Subscriber1<A> subscriber, final Key<A> one, Key<?>... rest) {
    once(new SubscriberX() {
      @Override
      public void onMessage(ImmutableMap<Key<?>, Object> map) {
        subscriber.onMessage((A)map.get(one));
      }
    }, keyList(rest, one));
  }

  @Override
  @SuppressWarnings("unchecked")
  public <A, B> void once(final Subscriber2<A, B> subscriber, final Key<A> one, final Key<B> two,
      Key<?>... rest) {
    once(new SubscriberX() {
      @Override
      public void onMessage(ImmutableMap<Key<?>, Object> map) {
        subscriber.onMessage((A)map.get(one), (B)map.get(two));
      }
    }, keyList(rest, one, two));
  }

  @Override
  @SuppressWarnings("unchecked")
  public <A, B, C> void once(final Subscriber3<A, B, C> subscriber, final Key<A> one,
      final Key<B> two, final Key<C> three, Key<?>... rest) {
    once(new SubscriberX() {
      @Override
      public void onMessage(ImmutableMap<Key<?>, Object> map) {
        subscriber.onMessage((A)map.get(one), (B)map.get(two), (C)map.get(three));
      }
    }, keyList(rest, one, two, three));
  }

  @Override
  @SuppressWarnings("unchecked")
  public <A, B, C, D> void once(final Subscriber4<A, B, C, D> subscriber, final Key<A> one,
      final Key<B> two, final Key<C> three, final Key<D> four, Key<?>... rest) {
    once(new SubscriberX() {
      @Override
      public void onMessage(ImmutableMap<Key<?>, Object> map) {
        subscriber.onMessage((A)map.get(one), (B)map.get(two), (C)map.get(three), (D)map.get(four));
      }
    }, keyList(rest, one, two, three, four));
  }

  @Override
  @SuppressWarnings("unchecked")
  public <A, B, C, D, E> void once(final Subscriber5<A, B, C, D, E> subscriber, final Key<A> one,
      final Key<B> two, final Key<C> three, final Key<D> four, final Key<E> five, Key<?>... rest) {
    once(new SubscriberX() {
      @Override
      public void onMessage(ImmutableMap<Key<?>, Object> map) {
        subscriber.onMessage((A)map.get(one), (B)map.get(two), (C)map.get(three), (D)map.get(four),
            (E)map.get(five));
      }
    }, keyList(rest, one, two, three, four, five));
  }

  @Override
  public void once(SubscriberX subscriber, ImmutableList<Key<?>> keys) {
    new MessageHandler(true /* once */,  keys, subscriber);
  }

  private ImmutableList<Key<?>> keyList(Key<?>[] rest, Key<?>... keys) {
    ImmutableList.Builder<Key<?>> result = ImmutableList.builder();
    result.addAll(Arrays.asList(rest));
    result.addAll(Arrays.asList(keys));
    return result.build();
  }

  @Override
  public <T> void fail(Key<T> key) {
    messageHandlers.removeAll(key);
  }
}
