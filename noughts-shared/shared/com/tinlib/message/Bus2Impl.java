package com.tinlib.message;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Bus2Impl implements Bus2 {
  private static interface OnMessage {
    void onMessage(ImmutableMap<Key<?>, Object> map);
  }

  private class MessageHandler {
    private final List<Key<?>> keys;
    private final Map<Key<?>, Optional<?>> requirements = Maps.newHashMap();
    private final boolean once;
    private final OnMessage onMessage;

    public MessageHandler(boolean once, List<Key<?>> keys, OnMessage onMessage) {
      this.once = once;
      this.onMessage = onMessage;
      this.keys = keys;

      for (Key<?> key : keys) {
        if (producedValues.containsKey(key)) {
          requirements.put(key, Optional.of(producedValues.get(key)));
        }
      }

      if (allSatisfied()) {
        onMessage.onMessage(realizeMap());
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
        onMessage.onMessage(realizeMap());
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
  public <T> void produce(Key<T> key, T value) {
    Preconditions.checkNotNull(value);
    fireHandlers(key, Optional.of(value));
  }

  private <T> void fireHandlers(Key<T> key, Optional<T> value) {
    Set<MessageHandler> handlerSet = messageHandlers.get(key);
    for (MessageHandler messageHandler : handlerSet) {
      messageHandler.handle(key, value);
    }
  }

  @Override
  public <T> void invalidate(Key<T> key) {

  }

  @Override
  public Unsubscriber await(final Subscriber0 subscriber, Key<?>... keys) {
    MessageHandler messageHandler = new MessageHandler(false /* once */, keyList(keys),
        new OnMessage() {
      @Override
      public void onMessage(ImmutableMap<Key<?>, Object> map) {
        subscriber.onMessage();
      }
    });
    return messageHandler.createUnsubscriber();
  }

  @Override
  @SuppressWarnings("unchecked")
  public <A> Unsubscriber await(final Subscriber1<A> subscriber, final Key<A> one, Key<?>... rest) {
    MessageHandler messageHandler = new MessageHandler(false /* once */, keyList(rest, one),
        new OnMessage() {
      @Override
      public void onMessage(ImmutableMap<Key<?>, Object> map) {
        subscriber.onMessage((A)map.get(one));
      }
    });
    return messageHandler.createUnsubscriber();
  }

  @Override
  @SuppressWarnings("unchecked")
  public <A, B> Unsubscriber await(final Subscriber2<A, B> subscriber, final Key<A> one,
      final Key<B> two, Key<?>... rest) {
    MessageHandler messageHandler = new MessageHandler(false /* once */, keyList(rest, one, two),
        new OnMessage() {
      @Override
      public void onMessage(ImmutableMap<Key<?>, Object> map) {
        subscriber.onMessage((A)map.get(one), (B)map.get(two));
      }
    });
    return messageHandler.createUnsubscriber();
  }

  @Override
  @SuppressWarnings("unchecked")
  public <A, B, C> Unsubscriber await(final Subscriber3<A, B, C> subscriber, final Key<A> one,
      final Key<B> two, final Key<C> three, Key<?>... rest) {
    MessageHandler messageHandler = new MessageHandler(false /* once */,
        keyList(rest, one, two, three), new OnMessage() {
      @Override
      public void onMessage(ImmutableMap<Key<?>, Object> map) {
        subscriber.onMessage((A)map.get(one), (B)map.get(two), (C)map.get(three));
      }
    });
    return messageHandler.createUnsubscriber();
  }

  @Override
  @SuppressWarnings("unchecked")
  public <A, B, C, D> Unsubscriber await(final Subscriber4<A, B, C, D> subscriber, final Key<A> one,
      final Key<B> two, final Key<C> three, final Key<D> four, Key<?>... rest) {
    MessageHandler messageHandler = new MessageHandler(false /* once */,
        keyList(rest, one, two, three, four), new OnMessage() {
      @Override
      public void onMessage(ImmutableMap<Key<?>, Object> map) {
        subscriber.onMessage((A)map.get(one), (B)map.get(two), (C)map.get(three), (D)map.get(four));
      }
    });
    return messageHandler.createUnsubscriber();
  }

  @Override
  @SuppressWarnings("unchecked")
  public <A, B, C, D, E> Unsubscriber await(final Subscriber5<A, B, C, D, E> subscriber,
      final Key<A> one, final Key<B> two, final Key<C> three, final Key<D> four, final Key<E> five,
      Key<?>... rest) {
    MessageHandler messageHandler = new MessageHandler(false /* once */,
        keyList(rest, one, two, three, four, five), new OnMessage() {
      @Override
      public void onMessage(ImmutableMap<Key<?>, Object> map) {
        subscriber.onMessage((A)map.get(one), (B)map.get(two), (C)map.get(three), (D)map.get(four),
            (E)map.get(five));
      }
    });
    return messageHandler.createUnsubscriber();
  }

  @Override
  public Unsubscriber await(SubscriberX subscriber, Key<?> keys) {
    return null;
  }

  @Override
  @SuppressWarnings("unchecked")
  public void once(final Subscriber0 subscriber, Key<?>... keys) {
    new MessageHandler(true /* once */, keyList(keys), new OnMessage() {
      @Override
      public void onMessage(ImmutableMap<Key<?>, Object> map) {
        subscriber.onMessage();
      }
    });
  }

  @Override
  @SuppressWarnings("unchecked")
  public <A> void once(final Subscriber1<A> subscriber, final Key<A> one, Key<?>... rest) {
    new MessageHandler(true /* once */, keyList(rest, one), new OnMessage() {
      @Override
      public void onMessage(ImmutableMap<Key<?>, Object> map) {
        subscriber.onMessage((A)map.get(one));
      }
    });
  }

  @Override
  @SuppressWarnings("unchecked")
  public <A, B> void once(final Subscriber2<A, B> subscriber, final Key<A> one, final Key<B> two,
      Key<?>... rest) {
    new MessageHandler(true /* once */, keyList(rest, one, two), new OnMessage() {
      @Override
      public void onMessage(ImmutableMap<Key<?>, Object> map) {
        subscriber.onMessage((A)map.get(one), (B)map.get(two));
      }
    });
  }

  @Override
  @SuppressWarnings("unchecked")
  public <A, B, C> void once(final Subscriber3<A, B, C> subscriber, final Key<A> one,
      final Key<B> two, final Key<C> three, Key<?>... rest) {
    new MessageHandler(true /* once */, keyList(rest, one, two, three), new OnMessage() {
      @Override
      public void onMessage(ImmutableMap<Key<?>, Object> map) {
        subscriber.onMessage((A)map.get(one), (B)map.get(two), (C)map.get(three));
      }
    });
  }

  @Override
  @SuppressWarnings("unchecked")
  public <A, B, C, D> void once(final Subscriber4<A, B, C, D> subscriber, final Key<A> one,
      final Key<B> two, final Key<C> three, final Key<D> four, Key<?>... rest) {
    new MessageHandler(true /* once */, keyList(rest, one, two, three, four), new OnMessage() {
      @Override
      public void onMessage(ImmutableMap<Key<?>, Object> map) {
        subscriber.onMessage((A)map.get(one), (B)map.get(two), (C)map.get(three), (D)map.get(four));
      }
    });
  }

  @Override
  @SuppressWarnings("unchecked")
  public <A, B, C, D, E> void once(final Subscriber5<A, B, C, D, E> subscriber, final Key<A> one,
      final Key<B> two, final Key<C> three, final Key<D> four, final Key<E> five, Key<?>... rest) {
    new MessageHandler(true /* once */, keyList(rest, one, two, three, four, five),
        new OnMessage() {
      @Override
      public void onMessage(ImmutableMap<Key<?>, Object> map) {
        subscriber.onMessage((A)map.get(one), (B)map.get(two), (C)map.get(three), (D)map.get(four),
            (E)map.get(five));
      }
    });
  }

  @Override
  public void once(SubscriberX subscriber, List<Key<?>> keys) {

  }

  private List<Key<?>> keyList(Key<?>[] rest, Key<?>... keys) {
    List<Key<?>> result = Lists.newArrayList();
    result.addAll(Arrays.asList(rest));
    result.addAll(Arrays.asList(keys));
    return result;
  }

  @Override
  public <T> void error(Key<T> key, String errorMessage) {

  }
}
