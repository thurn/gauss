package com.tinlib.convey2;

public interface Deferred3<A,B,C> {
  public Deferred3<A,B,C> addSubscriber(Subscriber3<A,B,C> subscriber);

  public Deferred3<A,B,C> addErrorCallback(Runnable runnable);

  public Deferred3<A,B,C> addFinishedCallback(Runnable runnable);

  public Deferred3<A,B,C> unsubscribe();
}
