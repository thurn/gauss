package com.tinlib.convey;

import com.google.common.collect.ImmutableMap;
import com.tinlib.defer.Deferred;

interface Callback<V> {
  Deferred<V> call(ImmutableMap<Key<?>, Object> map);
}
