package com.tinlib.message;

public interface Subscriber3<A, B, C> extends AnySubscriber {
  public void onMessage(A value1, B value2, C value3);
}
