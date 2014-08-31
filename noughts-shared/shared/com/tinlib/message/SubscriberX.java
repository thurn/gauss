package com.tinlib.message;

import com.google.common.collect.ImmutableMap;

public interface SubscriberX {
  public void onMessage(ImmutableMap<Key<?>, Object> map);
}
