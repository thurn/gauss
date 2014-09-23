package com.tinlib.convey;

import com.tinlib.defer.Promise;

public interface Callback1<V,A> {
  public Promise<V> call(A value1);
}
