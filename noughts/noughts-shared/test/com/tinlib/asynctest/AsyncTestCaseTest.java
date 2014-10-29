package com.tinlib.asynctest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class AsyncTestCaseTest {
  @Test
  public void testAsyncTest() {
    final AtomicBoolean fired = new AtomicBoolean(false);
    final AsyncTestCase testCase = new AsyncTestCase(){};
    testCase.beginAsyncTestBlock();
    new Timer().schedule(new TimerTask() {
      @Override
      public void run() {
        fired.set(true);
        testCase.finished();
      }
    }, 100L);
    testCase.endAsyncTestBlock();
    assertTrue(fired.get());
  }

  @Test
  public void testFinishedRunnable() {
    final AtomicBoolean fired = new AtomicBoolean(false);
    final AsyncTestCase testCase = new AsyncTestCase(){};
    testCase.beginAsyncTestBlock();
    new Timer().schedule(new TimerTask() {
      @Override
      public void run() {
        fired.set(true);
        testCase.FINISHED_RUNNABLE.run();
      }
    }, 100L);
    testCase.endAsyncTestBlock();
    assertTrue(fired.get());
  }

  @Test(expected = AssertionError.class)
  public void testTimeout() {
    final AsyncTestCase testCase = new AsyncTestCase(1){};
    testCase.beginAsyncTestBlock();
    testCase.endAsyncTestBlock();
  }

  @Test(expected = RuntimeException.class)
  public void testPropagateException() {
    final AsyncTestCase testCase = new AsyncTestCase(1){};
    testCase.beginAsyncTestBlock();
    new Timer().schedule(new TimerTask() {
      @Override
      public void run() {
        throw new RuntimeException();
      }
    }, 100L);
    testCase.endAsyncTestBlock();
  }

  @Test(expected = AssertionError.class)
  public void testEndBlockWithoutBeginBlock() {
    AsyncTestCase testCase = new AsyncTestCase(){};
    testCase.endAsyncTestBlock();
  }

  @Test(expected = AssertionError.class)
  public void testFinishedWithoutBeginBlock() {
    AsyncTestCase testCase = new AsyncTestCase(){};
    testCase.finished();
  }

  @Test(expected = AssertionError.class)
  public void testDuplicateBeginBlockCall() {
    AsyncTestCase testCase = new AsyncTestCase(){};
    testCase.beginAsyncTestBlock();
    testCase.beginAsyncTestBlock();
  }

  @Test
  public void testCleanUpFunction() {
    AsyncTestCase testCase = new AsyncTestCase(){};
    final AtomicBoolean fired = new AtomicBoolean(false);
    testCase.before();
    testCase.addCleanupFunction(new CleanupFunction() {
      @Override
      public void cleanUpAsync(final CountDownLatch countDownLatch) {
        new Timer().schedule(new TimerTask() {
          @Override
          public void run() {
            fired.set(true);
            countDownLatch.countDown();
          }
        }, 100L);

      }
    });
    testCase.after();
    assertTrue(fired.get());
  }

  @Test
  public void testSynchronousCleanupFunction() {
    AsyncTestCase testCase = new AsyncTestCase(){};
    final AtomicBoolean fired = new AtomicBoolean(false);
    testCase.before();
    testCase.addCleanupFunction(new SynchronousCleanupFunction() {
      @Override
      public void cleanUp() {
        fired.set(true);
      }
    });
    testCase.after();
    assertTrue(fired.get());
  }

  @Test(expected = AssertionError.class)
  public void testAfterTimedOut() {
    AsyncTestCase testCase = new AsyncTestCase(1){};
    testCase.before();
    testCase.addCleanupFunction(new CleanupFunction() {
      @Override
      public void cleanUpAsync(CountDownLatch countDownLatch) {
      }
    });
    testCase.after();
  }

  @Test(expected = RuntimeException.class)
  public void testPropagateExceptionInAfter() {
    AsyncTestCase testCase = new AsyncTestCase(1){};
    testCase.before();
    testCase.addCleanupFunction(new CleanupFunction() {
      @Override
      public void cleanUpAsync(CountDownLatch countDownLatch) {
        new Timer().schedule(new TimerTask() {
          @Override
          public void run() {
            throw new RuntimeException();
          }
        }, 100L);
      }
    });
    testCase.after();
  }

  @Test(expected = RuntimeException.class)
  public void testInterruptedThread() {
    AsyncTestCase testCase = new AsyncTestCase(){};
    testCase.beginAsyncTestBlock();
    Thread.currentThread().interrupt();
    testCase.endAsyncTestBlock();
  }

  @Test(expected = RuntimeException.class)
  public void testInterruptedCleanup() {
    AsyncTestCase testCase = new AsyncTestCase(1){};
    testCase.before();
    testCase.addCleanupFunction(new CleanupFunction() {
      @Override
      public void cleanUpAsync(CountDownLatch countDownLatch) {
        Thread.currentThread().interrupt();
      }
    });
    testCase.after();
  }
}
