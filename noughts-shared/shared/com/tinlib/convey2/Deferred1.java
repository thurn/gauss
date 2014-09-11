package com.tinlib.convey2;

public interface Deferred1<A> {
  public Deferred1<A> addSubscriber(Subscriber1<A> subscriber);

  public Deferred1<A> addErrorCallback(Runnable runnable);

  public Deferred1<A> addFinishedCallback(Runnable runnable);

  public Deferred1<A> unsubscribe();
}
