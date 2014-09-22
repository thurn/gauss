package com.tinlib.convey;

import com.tinlib.defer.Deferred;

public interface Callback3<V, A, B, C> {
  public Deferred<V> call(A value1, B value2, C value3);
}
