package com.tinlib.message;

public interface Subscriber2<A, B> extends AnySubscriber {
  public void onMessage(A value1, B value2);
}
