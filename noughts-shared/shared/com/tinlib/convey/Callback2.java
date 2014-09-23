package com.tinlib.convey;

import com.tinlib.defer.Promise;

public interface Callback2<V, A, B> {
  public Promise<V> call(A value1, B value2);
}
