package com.tinlib.message;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.SetMultimap;

import java.util.Map;
import java.util.Set;

public class Bus2Impl implements Bus2 {
  private static interface OnMessage {
    void onMessage(ImmutableMap<String, Optional<Object>> map);
  }

  private class MessageHandler {
    private final Map<String, Optional<Object>> requirements = Maps.newHashMap();
    private final boolean once;
    private final OnMessage onMessage;

    public MessageHandler(boolean once, String[] keys, OnMessage onMessage) {
      this.once = once;
      this.onMessage = onMessage;
      for (String key : keys) {
        if (producedValues.containsKey(key)) {
          requirements.put(key, Optional.of(producedValues.get(key)));
        } else {
          requirements.put(key, Optional.absent());
        }
      }

      if (allSatisfied()) {
        onMessage.onMessage(ImmutableMap.copyOf(requirements));
      }

      if (!(allSatisfied() && once)) {
        for (String key : keys) {
          messageHandlers.put(key, this);
        }
      }
    }

    public void handle(String key, Object object) {
      requirements.put(key, Optional.of(object));
      if (allSatisfied()) {
        onMessage.onMessage(ImmutableMap.copyOf(requirements));
        if (once) {
          createUnsubscriber().unsubscribe();
        }
      }
    }

    public Unsubscriber createUnsubscriber() {
      return new Unsubscriber() {
        @Override
        public void unsubscribe() {
          for (String key : requirements.keySet()) {
            messageHandlers.remove(key, this);
          }
        }
      };
    }

    private boolean allSatisfied() {
      for (Optional<Object> value : requirements.values()) {
        if (!value.isPresent()) return false;
      }
      return true;
    }
  }

  private final SetMultimap<String, MessageHandler> messageHandlers = HashMultimap.create();
  private final Map<String, Object> producedValues = Maps.newHashMap();

  @Override
  public void post(String key) {
    fireHandlers(key, true);
  }

  @Override
  public void post(String key, Object value) {
    Preconditions.checkNotNull(value);
    fireHandlers(key, value);
  }

  @Override
  public void produce(String key, Object value) {
    Preconditions.checkNotNull(value);
    fireHandlers(key, value);
  }

  private void fireHandlers(String key, Object value) {
    Set<MessageHandler> handlerSet = messageHandlers.get(key);
    for (MessageHandler messageHandler : handlerSet) {
      messageHandler.handle(key, value);
    }
  }

  @Override
  public void invalidate(String key) {
  }

  @Override
  public Unsubscriber await(final Subscriber0 subscriber, String... keys) {
    MessageHandler messageHandler = new MessageHandler(false /* once */, keys, new OnMessage() {
      @Override
      public void onMessage(ImmutableMap<String, Optional<Object>> map) {
        subscriber.onMessage();
      }
    });
    return messageHandler.createUnsubscriber();
  }

  @Override
  @SuppressWarnings("unchecked")
  public Unsubscriber await(final Subscriber1 subscriber, final String... keys) {
    Preconditions.checkArgument(keys.length >= 1);
    MessageHandler messageHandler = new MessageHandler(false /* once */, keys, new OnMessage() {
      @Override
      public void onMessage(ImmutableMap<String, Optional<Object>> map) {
        subscriber.onMessage(map.get(keys[0]).get());
      }
    });
    return messageHandler.createUnsubscriber();
  }

  @Override
  @SuppressWarnings("unchecked")
  public Unsubscriber await(final Subscriber2 subscriber, final String... keys) {
    Preconditions.checkArgument(keys.length >= 2);
    MessageHandler messageHandler = new MessageHandler(false /* once */, keys, new OnMessage() {
      @Override
      public void onMessage(ImmutableMap<String, Optional<Object>> map) {
        subscriber.onMessage(map.get(keys[0]).get(), map.get(keys[1]).get());
      }
    });
    return messageHandler.createUnsubscriber();
  }

  @Override
  @SuppressWarnings("unchecked")
  public Unsubscriber await(final Subscriber3 subscriber, final String... keys) {
    Preconditions.checkArgument(keys.length >= 3);
    MessageHandler messageHandler = new MessageHandler(false /* once */, keys, new OnMessage() {
      @Override
      public void onMessage(ImmutableMap<String, Optional<Object>> map) {
        subscriber.onMessage(map.get(keys[0]).get(), map.get(keys[1]).get(),
            map.get(keys[2]).get());
      }
    });
    return messageHandler.createUnsubscriber();
  }

  @Override
  @SuppressWarnings("unchecked")
  public Unsubscriber await(final Subscriber4 subscriber, final String... keys) {
    Preconditions.checkArgument(keys.length >= 4);
    MessageHandler messageHandler = new MessageHandler(false /* once */, keys, new OnMessage() {
      @Override
      public void onMessage(ImmutableMap<String, Optional<Object>> map) {
        subscriber.onMessage(map.get(keys[0]).get(), map.get(keys[1]).get(),
            map.get(keys[2]).get(), map.get(keys[3]).get());
      }
    });
    return messageHandler.createUnsubscriber();
  }

  @Override
  @SuppressWarnings("unchecked")
  public Unsubscriber await(final Subscriber5 subscriber, final String... keys) {
    Preconditions.checkArgument(keys.length >= 5);
    MessageHandler messageHandler = new MessageHandler(false /* once */, keys, new OnMessage() {
      @Override
      public void onMessage(ImmutableMap<String, Optional<Object>> map) {
        subscriber.onMessage(map.get(keys[0]).get(), map.get(keys[1]).get(),
            map.get(keys[2]).get(), map.get(keys[3]).get(), map.get(keys[4]).get());
      }
    });
    return messageHandler.createUnsubscriber();
  }

  @Override
  @SuppressWarnings("unchecked")
  public void once(final Subscriber0 subscriber, String... keys) {
    new MessageHandler(true /* once */, keys, new OnMessage() {
      @Override
      public void onMessage(ImmutableMap<String, Optional<Object>> map) {
        subscriber.onMessage();
      }
    });
  }

  @Override
  @SuppressWarnings("unchecked")
  public void once(final Subscriber1 subscriber, final String... keys) {
    Preconditions.checkArgument(keys.length >= 1);
    new MessageHandler(true /* once */, keys, new OnMessage() {
      @Override
      public void onMessage(ImmutableMap<String, Optional<Object>> map) {
        subscriber.onMessage(map.get(keys[0]).get());
      }
    });
  }

  @Override
  @SuppressWarnings("unchecked")
  public void once(final Subscriber2 subscriber, final String... keys) {
    Preconditions.checkArgument(keys.length >= 2);
    new MessageHandler(true /* once */, keys, new OnMessage() {
      @Override
      public void onMessage(ImmutableMap<String, Optional<Object>> map) {
        subscriber.onMessage(map.get(keys[0]).get(), map.get(keys[1]).get());
      }
    });
  }

  @Override
  @SuppressWarnings("unchecked")
  public void once(final Subscriber3 subscriber, final String... keys) {
    Preconditions.checkArgument(keys.length >= 3);
    new MessageHandler(true /* once */, keys, new OnMessage() {
      @Override
      public void onMessage(ImmutableMap<String, Optional<Object>> map) {
        subscriber.onMessage(map.get(keys[0]).get(), map.get(keys[1]).get(),
            map.get(keys[2]).get());
      }
    });
  }

  @Override
  @SuppressWarnings("unchecked")
  public void once(final Subscriber4 subscriber, final String... keys) {
    Preconditions.checkArgument(keys.length >= 4);
    new MessageHandler(true /* once */, keys, new OnMessage() {
      @Override
      public void onMessage(ImmutableMap<String, Optional<Object>> map) {
        subscriber.onMessage(map.get(keys[0]).get(), map.get(keys[1]).get(),
            map.get(keys[2]).get(), map.get(keys[3]).get());
      }
    });
  }

  @Override
  @SuppressWarnings("unchecked")
  public void once(final Subscriber5 subscriber, final String... keys) {
    Preconditions.checkArgument(keys.length >= 5);
    new MessageHandler(false /* once */, keys, new OnMessage() {
      @Override
      public void onMessage(ImmutableMap<String, Optional<Object>> map) {
        subscriber.onMessage(map.get(keys[0]).get(), map.get(keys[1]).get(),
            map.get(keys[2]).get(), map.get(keys[3]).get(), map.get(keys[4]).get());
      }
    });
  }

  @Override
  public void error(String key, String errorMessage) {

  }
}
