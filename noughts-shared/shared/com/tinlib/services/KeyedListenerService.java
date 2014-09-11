package com.tinlib.services;

import com.firebase.client.*;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Service for adding Firebase listeners based on String keys. This is
 * identical to the behavior of the regular Firebase listener-adding methods,
 * expect that if you add another listener with the same key, the original
 * listener will be removed.
 */
public class KeyedListenerService {
  private Map<String, Runnable> valueUnsubscribers = Maps.newHashMap();
  private Map<String, Runnable> childUnsubscribers = Maps.newHashMap();

  /**
   * Adds a keyed ValueEventListener as in
   * {@link Firebase#addValueEventListener(com.firebase.client.ValueEventListener)}.
   */
  public void addValueEventListener(final Firebase ref, String key, final ValueEventListener listener) {
    unregisterValueListener(key);
    final ValueEventListener addedListener = ref.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        if (dataSnapshot.getValue() == null) return;
        listener.onDataChange(dataSnapshot);
      }

      @Override
      public void onCancelled(FirebaseError firebaseError) {
        listener.onCancelled(firebaseError);
      }
    });
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
  public void addChildEventListener(final Firebase ref, String key,
      final ChildEventListener listener) {
    unregisterChildListener(key);
    final ChildEventListener addedListener = ref.addChildEventListener(new ChildEventListener() {
      @Override
      public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        if (dataSnapshot.getValue() == null) return;
        listener.onChildAdded(dataSnapshot, s);
      }

      @Override
      public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        if (dataSnapshot.getValue() == null) return;
        listener.onChildChanged(dataSnapshot, s);
      }

      @Override
      public void onChildRemoved(DataSnapshot dataSnapshot) {
        if (dataSnapshot.getValue() == null) return;
        listener.onChildRemoved(dataSnapshot);
      }

      @Override
      public void onChildMoved(DataSnapshot dataSnapshot, String s) {
        if (dataSnapshot.getValue() == null) return;
        listener.onChildMoved(dataSnapshot, s);
      }

      @Override
      public void onCancelled(FirebaseError firebaseError) {
        listener.onCancelled(firebaseError);
      }
    });
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
