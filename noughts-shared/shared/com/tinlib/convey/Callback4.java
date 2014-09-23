package com.tinlib.convey;

import com.tinlib.defer.Promise;

public interface Callback4<V,A,B,C,D> {
  public Promise<V> call(A value1, B value2, C value3, D value4);
}
