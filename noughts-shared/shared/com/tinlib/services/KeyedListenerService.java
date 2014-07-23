package com.tinlib.services;

import com.firebase.client.ChildEventListener;
import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Service for adding Firebase listeners based on String keys. This is
 * identical to the behavior of the regular Firebase listener-adding methods,
 * expect that if you add another listener with the same key, the original
 * listener will be removed.
 */
public class KeyedListenerService {
  Map<String, Runnable> valueUnsubscribers = Maps.newHashMap();
  Map<String, Runnable> childUnsubscribers = Maps.newHashMap();

  /**
   * Adds a keyed ValueEventListener as in
   * {@link Firebase#addValueEventListener(com.firebase.client.ValueEventListener)}.
   */
  public void addValueEventListener(final Firebase ref, String key, ValueEventListener listener) {
    unregisterValueListener(key);
    final ValueEventListener addedListener = ref.addValueEventListener(listener);
    valueUnsubscribers.put(key, new Runnable() {
      @Override
      public void run() {
        ref.removeEventListener(addedListener);
      }
    });
  }

  /**
   * Removes the value listener with the provided key.
   */
  public void unregisterValueListener(String key) {
    if (valueUnsubscribers.containsKey(key)) {
      valueUnsubscribers.get(key).run();
    }
  }

  /**
   * Adds a keyed ChildEventListener as in
   * {@link Firebase#addChildEventListener(com.firebase.client.ChildEventListener)}.
   */
  public void addChildEventListener(final Firebase ref, String key, ChildEventListener listener) {
    unregisterChildListener(key);
    final ChildEventListener addedListener = ref.addChildEventListener(listener);
    childUnsubscribers.put(key, new Runnable() {
      @Override
      public void run() {
        ref.removeEventListener(addedListener);
      }
    });
  }

  /**
   * Removes the child listener with the provided key
   */
  public void unregisterChildListener(String key) {
    if (childUnsubscribers.containsKey(key)) {
      childUnsubscribers.get(key).run();
    }
  }

  /**
   * Unregisters ALL listeners which have been registered with this service.
   */
  public void unregisterAll() {
    for (Runnable runnable : valueUnsubscribers.values()) {
      runnable.run();
    }
    for (Runnable runnable : childUnsubscribers.values()) {
      runnable.run();
    }
  }
}
