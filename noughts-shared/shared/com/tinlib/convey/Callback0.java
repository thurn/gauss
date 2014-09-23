package com.tinlib.convey;

import com.tinlib.defer.Promise;

public interface Callback0<V> {
  public Promise<V> call();
}
