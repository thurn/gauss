package com.tinlib.defer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class DeferredImplTest {
  @Test
  public void testResolve() {
    final AtomicBoolean ran = new AtomicBoolean(false);
    Deferred<String> deferred = Deferreds.newDeferred();
    deferred.addSuccessHandler(new SuccessHandler<String>() {
      @Override
      public void onSuccess(String value) {
        assertEquals("value", value);
        ran.set(true);
      }
    });
    deferred.resolve("value");
    assertTrue(ran.get());
  }

  @Test
  public void testFail() {
    final AtomicBoolean ran = new AtomicBoolean(false);
    Deferred<String> deferred = Deferreds.newDeferred();
    final RuntimeException toThrow = new RuntimeException();
    deferred.addFailureHandler(new FailureHandler() {
      @Override
      public void onError(RuntimeException exception) {
        assertEquals(toThrow, exception);
        ran.set(true);
      }
    });
    deferred.fail(toThrow);
    assertTrue(ran.get());
  }

  @Test
  public void testResolveVoid() {
    final AtomicBoolean ran = new AtomicBoolean(false);
    Deferred<Void> deferred = Deferreds.newDeferred();
    deferred.addSuccessHandler(new SuccessHandler<Void>() {
      @Override
      public void onSuccess(Void value) {
        assertNull(value);
        ran.set(true);
      }
    });
    deferred.resolve();
    assertTrue(ran.get());
  }

  @Test
  public void testCompletionHandler() {
    final AtomicBoolean ran = new AtomicBoolean(false);
    Deferred<Void> deferred = Deferreds.newDeferred();
    deferred.addCompletionHandler(new Runnable() {
      @Override
      public void run() {
        ran.set(true);
      }
    });
    deferred.resolve();
    assertTrue(ran.get());
  }

  @Test
  public void testAddAfterResolve() {
    final AtomicBoolean successRan = new AtomicBoolean(false);
    final AtomicBoolean completeRan = new AtomicBoolean(false);
    Deferred<Void> deferred = Deferreds.newDeferred();
    deferred.resolve();
    deferred.addSuccessHandler(new Runnable() {
      @Override
      public void run() {
        successRan.set(true);
      }
    });
    deferred.addCompletionHandler(new Runnable() {
      @Override
      public void run() {
        completeRan.set(true);
      }
    });
    assertTrue(successRan.get());
    assertTrue(completeRan.get());
  }

/*
- resolve()
- completion handlers
- add success handler after success
- runnable success handler
- add failure handler after failure
- runnable failure handler
- getState()
- then(Function)
- then(Callable)
- then(Runnable)
- chainFrom(Promise)
*/

}
