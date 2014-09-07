package com.tinlib.convey;

import com.google.common.collect.ImmutableMap;

interface Subscriber {
  void onMessage(ImmutableMap<Key<?>, Object> map);
}
