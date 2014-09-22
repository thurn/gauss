package com.tinlib.convey;

import com.tinlib.defer.Deferred;

public interface Callback2<V, A, B> {
  public Deferred<V> call(A value1, B value2);
}
