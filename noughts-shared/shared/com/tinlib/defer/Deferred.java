package com.tinlib.defer;

public interface Deferred<V> extends Promise<V> {
  public void resolve(V value);

  public void resolve();

  public void chain(Promise<V> promise);

  public void fail(RuntimeException exception);
}
