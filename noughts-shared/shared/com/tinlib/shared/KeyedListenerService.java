package com.tinlib.shared;

import com.firebase.client.ChildEventListener;
import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;
import com.google.common.collect.Maps;

import java.util.Map;

public class KeyedListenerService {
  Map<String, Runnable> valueUnsubscribers = Maps.newHashMap();
  Map<String, Runnable> childUnsubscribers = Maps.newHashMap();

  public void addValueEventListener(final Firebase ref, String key, ValueEventListener listener) {
    if (valueUnsubscribers.containsKey(key)) {
      valueUnsubscribers.get(key).run();
    }
    final ValueEventListener addedListener = ref.addValueEventListener(listener);
    valueUnsubscribers.put(key, new Runnable() {
      @Override
      public void run() {
        ref.removeEventListener(addedListener);
      }
    });
  }

  public void addChildEventListener(final Firebase ref, String key, ChildEventListener listener) {
    if (childUnsubscribers.containsKey(key)) {
      childUnsubscribers.get(key).run();
    }
    final ChildEventListener addedListener = ref.addChildEventListener(listener);
    childUnsubscribers.put(key, new Runnable() {
      @Override
      public void run() {
        ref.removeEventListener(addedListener);
      }
    });
  }
}
