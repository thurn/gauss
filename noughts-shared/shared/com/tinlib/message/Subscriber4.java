package com.tinlib.message;

public interface Subscriber4<A,B,C,D> extends AnySubscriber {
  public void onMessage(A value1, B value2, C value3, D value4);
}
