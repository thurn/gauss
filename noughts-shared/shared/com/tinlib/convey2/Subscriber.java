package com.tinlib.convey2;

import com.google.common.collect.ImmutableMap;

public abstract class Subscriber extends AbstractSubscriber {
  public abstract void onMessage(ImmutableMap<Key<?>, Object> map);
}
