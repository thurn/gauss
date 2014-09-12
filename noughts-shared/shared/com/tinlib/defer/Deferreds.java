package com.tinlib.defer;

public class Deferreds {
  private Deferreds() {}

  public static <V> Deferred<V> newDeferred() {
    return new DeferredImpl<>();
  }

}
