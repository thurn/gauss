package com.tinlib.convey;

import com.tinlib.defer.Deferred;

public interface Callback5<V,A,B,C,D,E> {
  public Deferred<V> call(A value1, B value2, C value3, D value4, E value5);
}
