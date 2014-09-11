package com.tinlib.convey2;

public interface AbstractDeferred {
  public AbstractDeferred addErrorCallback(Runnable runnable);

  public AbstractDeferred addFinishedCallback(Runnable runnable);

  public AbstractDeferred unsubscribe();
}
