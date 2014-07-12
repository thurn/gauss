package com.tinlib.message;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.*;

public final class Buses {
  private static class BusImpl implements Bus {
    private Map<String, Set<Subscriber>> subscribers = Maps.newHashMap();
    private Map<String, Object> producedValues = Maps.newHashMap();
    private Map<Subscriber, List<String>> subscriberMessages = Maps.newHashMap();
    private Map<String, Set<Awaiter>> awaiters = Maps.newHashMap();
    private Map<Awaiter, List<String>> awaiterMessages = Maps.newHashMap();

    private void sendMessage(String name, Object value) {
      if (subscribers.containsKey(name)) {
        for (Subscriber subscriber : subscribers.get(name)) {
          subscriber.onMessage(name, value);
        }
      }
      if (awaiters.containsKey(name)) {
        for (Awaiter awaiter : awaiters.get(name)) {
          if (allProduced(awaiterMessages.get(awaiter))) {
            fireOnResult(awaiter, awaiterMessages.get(awaiter));
          }
        }
      }
    }

    @Override
    public void post(String name) {
      sendMessage(name, null /* value */);
    }

    @Override
    public void post(String name, Object value) {
      if (value == null) {
        throw new NullPointerException("Cannot post() null for message " + name);
      }
      sendMessage(name, value);
    }

    @Override
    public void produce(String name, Object value) {
      if (value == null) {
        throw new NullPointerException("Cannot produce() null for message " + name);
      }
      sendMessage(name, value);
      producedValues.put(name, value);
    }

    @Override
    public void register(Subscriber subscriber, List<String> messages) {
      for (String message : messages) {
        if (!subscribers.containsKey(message)) {
          subscribers.put(message, Sets.<Subscriber>newHashSet());
        }
        subscribers.get(message).add(subscriber);
        if (producedValues.containsKey(message)) {
          subscriber.onMessage(message, producedValues.get(message));
        }
      }
      subscriberMessages.put(subscriber, Lists.newArrayList(messages));
    }

    @Override
    public void await(Awaiter awaiter, List<String> messages) {
      for (String message : messages) {
        if (!awaiters.containsKey(message)) {
          awaiters.put(message, Sets.<Awaiter>newHashSet());
        }
        if (allProduced(messages)) {
          fireOnResult(awaiter, messages);
        }
        awaiters.get(message).add(awaiter);
      }
      awaiterMessages.put(awaiter, Lists.newArrayList(messages));
    }

    private boolean allProduced(List<String> messages) {
      for (String message : messages) {
        if (!producedValues.containsKey(message)) return false;
      }
      return true;
    }

    private void fireOnResult(Awaiter awaiter, List<String> messages) {
      ImmutableMap.Builder<String, Object> result = ImmutableMap.builder();
      for (String message : messages) {
        result.put(message, producedValues.get(message));
      }
      awaiter.onResult(result.build());
    }

    @Override
    public void unregister(Subscriber subscriber) {
      if (!subscriberMessages.containsKey(subscriber)) {
        throw new IllegalArgumentException(
            "Unknown subscriber passed to unregister() " + subscriber);
      }
      for (String message : subscriberMessages.get(subscriber)) {
        subscribers.get(message).remove(subscriber);
      }
      subscriberMessages.remove(subscriber);
    }

    @Override
    public void unregister(Awaiter awaiter) {
      if (!awaiterMessages.containsKey(awaiter)) {
        throw new IllegalArgumentException("Unknown awaiter passed to unregister() " + awaiter);
      }
      for (String message : awaiterMessages.get(awaiter)) {
        awaiters.get(message).remove(awaiter);
      }
      awaiterMessages.remove(awaiter);
    }
  }

  private Buses() {}

  public static Bus newBus() {
    return new BusImpl();
  }
}
