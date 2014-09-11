package com.tinlib.convey2;

public abstract class Subscriber2<A, B> extends AbstractSubscriber {
  public abstract void onMessage(A value1, B value2);
}
