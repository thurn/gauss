package com.tinlib.convey2;

public abstract class AbstractSubscriber {
  public void onError(RuntimeException runtimeException) {
    throw runtimeException;
  }

  public void onFinished() {
  }
}
