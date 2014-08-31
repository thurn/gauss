package com.tinlib.message;

import com.google.common.collect.ImmutableMap;

interface SubscriberX {
  void onMessage(ImmutableMap<Key<?>, Object> map);
}
