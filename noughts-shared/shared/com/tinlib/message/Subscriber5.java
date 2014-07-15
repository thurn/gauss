package com.tinlib.message;

public interface Subscriber5<A,B,C,D,E> extends AnySubscriber {
  public void onMessage(A value1, B value2, C value3, D value4, E value5);
}
