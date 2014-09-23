package com.tinlib.convey;

import com.google.common.collect.ImmutableMap;
import com.tinlib.defer.Promise;

interface Callback<V> {
  public Promise<V> call(ImmutableMap<Key<?>, Object> map);
}
