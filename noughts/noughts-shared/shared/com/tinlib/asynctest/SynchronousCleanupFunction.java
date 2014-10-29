package com.tinlib.asynctest;

import java.util.concurrent.CountDownLatch;

public abstract class SynchronousCleanupFunction implements CleanupFunction {
  @Override
  public final void cleanUpAsync(CountDownLatch countDownLatch) {
    cleanUp();
    countDownLatch.countDown();
  }

  public abstract void cleanUp();
}
