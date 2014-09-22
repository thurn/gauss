package com.tinlib.convey;

import com.tinlib.defer.Deferred;

public interface Callback0<V> {
  public Deferred<V> call();
}
