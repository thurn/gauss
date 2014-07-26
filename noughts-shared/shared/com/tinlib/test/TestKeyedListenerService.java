package com.tinlib.test;

import com.firebase.client.ChildEventListener;
import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;
import com.google.common.collect.Maps;
import com.tinlib.services.KeyedListenerService;

import java.util.Map;

public class TestKeyedListenerService extends KeyedListenerService {
  private final Map<String, ValueEventListener> valueEventListeners = Maps.newHashMap();
  private final Map<String, ChildEventListener> childEventListeners = Maps.newHashMap();

  @Override
  public void addValueEventListener(final Firebase ref, String key, ValueEventListener listener) {
    valueEventListeners.put(key, listener);
    super.addValueEventListener(ref, key, listener);
  }

  @Override
  public void addChildEventListener(final Firebase ref, String key, ChildEventListener listener) {
    childEventListeners.put(key, listener);
    super.addChildEventListener(ref, key, listener);
  }

  public ValueEventListener getValueEventListenerForKey(String key) {
    if (valueEventListeners.containsKey(key)) {
      return valueEventListeners.get(key);
    } else {
      throw new RuntimeException("No listener for key " + key);
    }
  }

  public ChildEventListener getChildEventListenerForKey(String key) {
    if (childEventListeners.containsKey(key)) {
      return childEventListeners.get(key);
    } else {
      throw new RuntimeException("No listener for key " + key);
    }
  }
}
