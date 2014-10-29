package com.tinlib.asynctest;

import java.util.concurrent.CountDownLatch;

public interface CleanupFunction {
  public void cleanUpAsync(CountDownLatch countDownLatch);
}
