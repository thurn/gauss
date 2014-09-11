package com.tinlib.convey2;

public abstract class Subscriber4<A,B,C,D> extends AbstractSubscriber {
  public abstract void onMessage(A value1, B value2, C value3, D value4);
}
