package com.tinlib.convey;

import com.tinlib.defer.Promise;

public interface Callback3<V, A, B, C> {
  public Promise<V> call(A value1, B value2, C value3);
}
