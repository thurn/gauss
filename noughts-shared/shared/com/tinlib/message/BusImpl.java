package com.tinlib.message;

import com.google.common.collect.*;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

class BusImpl implements Bus {
  private final Map<String, Set<SubscriberHolder>> subscribers = Maps.newHashMap();
  private final Map<String, Set<Listener>> listeners = Maps.newHashMap();
  private final Map<String, Object> values = Maps.newHashMap();
  private final Map<AnySubscriber, Runnable> unsubscribeFunctions = Maps.newHashMap();
  private final Map<Listener, Runnable> unlistenFunctions = Maps.newHashMap();

  private class SubscriberHolder {
    private final AnySubscriber subscriber;
    private final List<String> dependencies;
    private final boolean once;

    SubscriberHolder(AnySubscriber subscriber, List<String> dependencies, boolean once) {
      this.subscriber = subscriber;
      this.dependencies = dependencies;
      this.once = once;
    }

    @SuppressWarnings("unchecked")
    public @Nullable AnySubscriber handleMessage() {
      if (!allSatisfied()) return null;

      if (subscriber instanceof Subscriber0) {
        ((Subscriber0)subscriber).onMessage();
      } else if (subscriber instanceof Subscriber1) {
        ((Subscriber1)subscriber).onMessage(values.get(dependencies.get(0)));
      } else if (subscriber instanceof Subscriber2) {
        ((Subscriber2)subscriber).onMessage(values.get(dependencies.get(0)),
            values.get(dependencies.get(1)));
      } else if (subscriber instanceof Subscriber3) {
        ((Subscriber3)subscriber).onMessage(values.get(dependencies.get(0)),
            values.get(dependencies.get(1)), values.get(dependencies.get(2)));
      } else if (subscriber instanceof Subscriber4) {
        ((Subscriber4)subscriber).onMessage(values.get(dependencies.get(0)),
            values.get(dependencies.get(1)), values.get(dependencies.get(2)),
            values.get(dependencies.get(3)));
      } else if (subscriber instanceof Subscriber5) {
        ((Subscriber5)subscriber).onMessage(values.get(dependencies.get(0)),
            values.get(dependencies.get(1)), values.get(dependencies.get(2)),
            values.get(dependencies.get(3)), values.get(dependencies.get(5)));
      } else {
        Map<String, Object> result = Maps.newHashMap();
        for (String dependency : dependencies) {
          result.put(dependency, values.get(dependency));
        }
        ((Subscriber)subscriber).onMessage(result);
      }

      return once ? subscriber : null;
    }

    private boolean allSatisfied() {
      for (String dependency : dependencies) {
        if (!values.containsKey(dependency)) {
          return false;
        }
      }
      return true;
    }
  }

  @Override
  public void await(String message, Subscriber0 subscriber) {
    addSubscriber(Lists.newArrayList(message), false /* once */, subscriber);
  }

  @Override
  public <A> void await(String message, Subscriber1<A> subscriber) {
    addSubscriber(Lists.newArrayList(message), false /* once */, subscriber);
  }

  @Override
  public <A, B> void await(String message1, String message2, Subscriber2<A, B> subscriber) {
    addSubscriber(Lists.newArrayList(message1, message2), false /* once */, subscriber);
  }

  @Override
  public <A, B, C> void await(String message1, String message2, String message3,
      Subscriber3<A, B, C> subscriber) {
    addSubscriber(Lists.newArrayList(message1, message2, message3), false /* once */, subscriber);
  }

  @Override
  public <A, B, C, D> void await(String message1, String message2, String message3,
      String message4, Subscriber4<A, B, C, D> subscriber) {
    addSubscriber(Lists.newArrayList(message1, message2, message3, message4), false /* once */,
        subscriber);
  }

  @Override
  public <A, B, C, D, E> void await(String message1, String message2, String message3,
      String message4, String message5, Subscriber5<A, B, C, D, E> subscriber) {
    addSubscriber(Lists.newArrayList(message1, message2, message3, message4, message5),
        false /* once */, subscriber);
  }

  @Override
  public void await(List<String> messages, Subscriber subscriber) {
    addSubscriber(messages, false /* once */, subscriber);
  }

  @Override
  public void listen(final List<String> messages, final Listener listener) {
    for (String message : messages) {
      if (!listeners.containsKey(message)) {
        listeners.put(message, Sets.<Listener>newHashSet());
      }
      listeners.get(message).add(listener);
    }
    unlistenFunctions.put(listener, new Runnable() {
      @Override
      public void run() {
        for (String message : messages) {
          listeners.get(message).remove(listener);
        }
      }
    });
  }

  @Override
  public void once(String message, Subscriber0 subscriber) {
    addSubscriber(Lists.newArrayList(message), true /* once */, subscriber);
  }

  @Override
  public <A> void once(String message, Subscriber1<A> subscriber) {
    addSubscriber(Lists.newArrayList(message), true /* once */, subscriber);
  }

  @Override
  public <A, B> void once(String message1, String message2, Subscriber2<A, B> subscriber) {
    addSubscriber(Lists.newArrayList(message1, message2), true /* once */, subscriber);
  }

  @Override
  public <A, B, C> void once(String message1, String message2, String message3,
      Subscriber3<A, B, C> subscriber) {
    addSubscriber(Lists.newArrayList(message1, message2, message3), true /* once */, subscriber);
  }

  @Override
  public <A, B, C, D> void once(String message1, String message2, String message3, String message4,
      Subscriber4<A, B, C, D> subscriber) {
    addSubscriber(Lists.newArrayList(message1, message2, message3, message4), true /* once */,
        subscriber);
  }

  @Override
  public <A, B, C, D, E> void once(String message1, String message2, String message3,
      String message4, String message5, Subscriber5<A, B, C, D, E> subscriber) {
    addSubscriber(Lists.newArrayList(message1, message2, message3, message4, message5),
        true /* once */, subscriber);
  }

  @Override
  public void once(List<String> messages, Subscriber subscriber) {
    addSubscriber(messages, true /* once */, subscriber);
  }

  private void addSubscriber(final List<String> messages, boolean once, AnySubscriber subscriber) {
    final SubscriberHolder subscriberHolder = new SubscriberHolder(subscriber, messages, once);
    if (subscriberHolder.allSatisfied()) {
      subscriberHolder.handleMessage();
      if (once) return;
    }
    for (String message : messages) {
      if (!subscribers.containsKey(message)) {
        subscribers.put(message, Sets.<SubscriberHolder>newHashSet());
      }
      subscribers.get(message).add(subscriberHolder);
    }
    unsubscribeFunctions.put(subscriber, new Runnable() {
      @Override
      public void run() {
        for (String message : messages) {
          subscribers.get(message).remove(subscriberHolder);
        }
      }
    });
  }

  @Override
  public void produce(String message) {
    produce(message, true);
  }

  @Override
  public void produce(String message, Object value) {
    checkNotNull(value);
    values.put(message, value);

    if (listeners.containsKey(message)) {
      for (Listener listener : listeners.get(message)) {
        listener.onUpdate(message, value);
      }
    }

    if (subscribers.containsKey(message)) {
      Set<AnySubscriber> toRemoveSet = Sets.newHashSet();
      for (SubscriberHolder subscriberHolder : subscribers.get(message)) {
        AnySubscriber toRemove = subscriberHolder.handleMessage();
        if (toRemove != null) toRemoveSet.add(toRemove);
      }

      for (AnySubscriber subscriber : toRemoveSet) {
        unregister(subscriber);
      }
    }
  }

  @Override
  public void unregister(AnySubscriber subscriber) {
    if (unsubscribeFunctions.containsKey(subscriber)) {
      unsubscribeFunctions.get(subscriber).run();
    } else {
      throw new RuntimeException("Unknown subscriber passed to unregister " + subscriber);
    }
  }

  @Override
  public void unregister(Listener listener) {
    if (unlistenFunctions.containsKey(listener)) {
      unlistenFunctions.get(listener).run();
    } else {
      throw new RuntimeException("Unknown listener passed to unregister " + listener);
    }
  }

  @Override
  public void invalidate(String message) {
    values.remove(message);
  }

  @Override
  public void clearAll() {
    subscribers.clear();
    listeners.clear();
    values.clear();
    unlistenFunctions.clear();
    unsubscribeFunctions.clear();
  }
}
