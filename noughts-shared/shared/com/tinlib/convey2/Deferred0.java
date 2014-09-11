package com.tinlib.convey2;

public interface Deferred0 {
  public Deferred0 addSubscriber(Subscriber0 subscriber);

  public Deferred0 addErrorCallback(Runnable runnable);

  public Deferred0 addFinishedCallback(Runnable runnable);

  public Deferred0 unsubscribe();
}
