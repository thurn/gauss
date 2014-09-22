package com.tinlib.convey;

import com.tinlib.defer.Deferred;

public interface Callback1<V,A> {
  public Deferred<V> call(A value1);
}
