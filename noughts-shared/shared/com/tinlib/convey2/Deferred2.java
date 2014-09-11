package com.tinlib.convey2;

public interface Deferred2<A,B> {
  public Deferred2<A,B> addSubscriber(Subscriber2<A,B> subscriber);

  public Deferred2<A,B> addErrorCallback(Runnable runnable);

  public Deferred2<A,B> addFinishedCallback(Runnable runnable);

  public Deferred2<A,B> unsubscribe();
}
