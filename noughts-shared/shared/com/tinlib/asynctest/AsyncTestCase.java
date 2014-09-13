package com.tinlib.asynctest;

import com.google.common.base.Optional;
import com.google.common.collect.Sets;
import org.junit.After;
import org.junit.Before;

import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AsyncTestCase {
  public final Runnable FINISHED_RUNNABLE = new Runnable() {
    @Override
    public void run() {
      finished();
    }
  };

  private final AtomicBoolean finished = new AtomicBoolean(false);
  private final AtomicInteger numFinishes = new AtomicInteger(0);
  private int timeoutSeconds;
  private final Set<CleanupFunction> cleanupFunctions = Sets.newHashSet();
  private Optional<CountDownLatch> latch = Optional.absent();
  private int expectedFinishedCalls;
  private Optional<Throwable> throwableToPropagate = Optional.absent();

  public AsyncTestCase() {
    this(10 /* timeoutSeconds */);
  }

  public AsyncTestCase(int timeoutSeconds) {
    this(timeoutSeconds, true /* propagateExceptions */);
  }

  public AsyncTestCase(int timeoutSeconds, boolean propagateExceptions) {
    this.timeoutSeconds = timeoutSeconds;

    if (propagateExceptions) {
      Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(Thread t, Throwable e) {
          throwableToPropagate = Optional.of(e);
        }
      });
    }
  }

  @Before
  public final void before() {
    latch = Optional.absent();
    cleanupFunctions.clear();
  }

  @After
  public final void after() {
    CountDownLatch countDownLatch = new CountDownLatch(cleanupFunctions.size());
    for (CleanupFunction cleanupFunction : cleanupFunctions) {
      cleanupFunction.cleanUpAsync(countDownLatch);
    }

    try {
      boolean result = countDownLatch.await(timeoutSeconds, TimeUnit.SECONDS);
      if (throwableToPropagate.isPresent()) {
        throw new RuntimeException(throwableToPropagate.get());
      }
      throwableToPropagate = Optional.absent();
      if (!result) {
        throw new AssertionError("after() timed out.");
      }
    } catch (InterruptedException exception) {
      throw new RuntimeException(exception);
    }
  }

  public final void beginAsyncTestBlock() {
    beginAsyncTestBlock(1);
  }

  public final void beginAsyncTestBlock(int numFinishesExpected) {
    if (latch.isPresent()) {
      throw new AssertionError("Additional call to beginAsyncTestBlock() without corresponding " +
          "call to endAsyncTestBlock()!");
    }
    latch = Optional.of(new CountDownLatch(numFinishesExpected));
    expectedFinishedCalls = numFinishesExpected;
  }

  public final void endAsyncTestBlock() {
    if (!latch.isPresent()) {
      throw new AssertionError("endAsyncTestBlock() called without beginAsyncTestBlock()!");
    }
    try {
      boolean result = latch.get().await(timeoutSeconds, TimeUnit.SECONDS);
      if (throwableToPropagate.isPresent()) {
        throw new RuntimeException(throwableToPropagate.get());
      }
      throwableToPropagate = Optional.absent();
      if (!result) {
        throw new AssertionError("Timed out waiting for a call to finished(). Expected " +
            expectedFinishedCalls + " call(s) to finished(), got " +
            (expectedFinishedCalls - latch.get().getCount()) + " call(s) to finished().");
      }
    } catch (InterruptedException exception) {
      throw new RuntimeException(exception);
    } finally {
      latch = Optional.absent();
    }
  }

  public final void finished() {
    if (latch.isPresent()) {
      latch.get().countDown();
    } else {
      throw new AssertionError("Call to finished() without corresponding call to " +
          "beginAsyncTestBlock()!");
    }
  }

  public final void addCleanupFunction(CleanupFunction cleanupFunction) {
    cleanupFunctions.add(cleanupFunction);
  }
}
