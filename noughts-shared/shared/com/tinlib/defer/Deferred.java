package com.tinlib.defer;

public interface Deferred<V> extends Promise<V> {
  public void resolve(V value);

  public void resolve();

  public void fail(RuntimeException exception);
}
