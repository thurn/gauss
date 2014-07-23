package com.tinlib.test;

import com.firebase.client.Firebase;
import com.jayway.awaitility.Awaitility;
import com.tinlib.analytics.AnalyticsHandler;
import com.tinlib.error.ErrorHandler;
import com.tinlib.message.Bus;
import com.tinlib.services.*;
import org.junit.After;
import org.junit.Before;
import org.mockito.Mock;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

public abstract class TinTestCase {
  public Runnable FINISHED = new Runnable() {
    @Override
    public void run() {
      finished();
    }
  };

  public ErrorHandler FINISHED_ERROR_HANDLER = new ErrorHandler() {
    @Override
    public void error(String message, Object[] args) {
      finished();
    }
  };

  private final AtomicBoolean finished = new AtomicBoolean(false);
  private final AtomicInteger numFinishes = new AtomicInteger(0);
  private final Runnable finishedRunnable = new Runnable() {
    @Override
    public void run() {
      finished();
    }
  };
  private TestHelper testHelper;

  @Mock
  protected ErrorHandler mockErrorHandler;
  @Mock
  protected AnalyticsHandler mockAnalyticsHandler;

  protected Bus bus;
  protected KeyedListenerService keyedListenerService;
  protected final Firebase firebase = new Firebase("https://tintest.firebaseio-demo.com");

  @Before
  public final void tinSetUp() {
    numFinishes.set(0);
    finished.set(false);
    beginAsyncTestBlock();
    setUp(finishedRunnable);
    endAsyncTestBlock();
  }

  @After
  public final void tinTearDown() {
    beginAsyncTestBlock();
    tearDown(new Runnable() {
      @Override
      public void run() {
        if (testHelper != null) {
          testHelper.cleanUp(finishedRunnable);
        } else {
          finished();
        }
      }
    });
    endAsyncTestBlock();
  }

  public void setTestHelper(TestHelper testHelper) {
    this.testHelper = testHelper;
  }

  public void setUp(Runnable done) {
    done.run();
  }

  public void tearDown(Runnable done) {
    done.run();
  }

  public void beginAsyncTestBlock() {
    beginAsyncTestBlock(1);
  }

  public synchronized void beginAsyncTestBlock(int numFinishesExpected) {
    numFinishes.set(numFinishesExpected);
  }

  public void endAsyncTestBlock() {
    Awaitility.await("Waiting for call to finished()").untilTrue(finished);
    finished.set(false);
  }

  /**
   * Indicates that your test, where you previously called beginAsyncTestBlock(), is done
   * executing.
   */
  public synchronized void finished() {
    numFinishes.getAndDecrement();
    if (numFinishes.get() <= 0) {
      finished.set(true);
    }
  }

  public void assertDeepEquals(Object o1, Object o2) {
    assertDeepEquals("(no message)", o1, o2);
  }

  public void assertDeepEquals(String msg, Object o1, Object o2) {
    if (o1 instanceof Iterable && o2 instanceof Iterable) {
      @SuppressWarnings("unchecked")
      Iterator<Object> ite1 = ((Iterable<Object>) o1).iterator();
      @SuppressWarnings("unchecked")
      Iterator<Object> ite2 = ((Iterable<Object>) o2).iterator();
      while (ite1.hasNext() && ite2.hasNext()) {
        assertDeepEquals(msg, ite1.next(), ite2.next());
      }
      assertFalse("Iterable sizes differ", ite1.hasNext() || ite2.hasNext());
    } else if (o1 instanceof Map && o2 instanceof Map) {
      @SuppressWarnings("unchecked")
      Map<Object, Object> map1 = (Map<Object, Object>) o1;
      @SuppressWarnings("unchecked")
      Map<Object, Object> map2 = (Map<Object, Object>) o2;
      assertEquals("Map sizes differ", map1.size(), map2.size());
      for (Map.Entry<Object, Object> entry : map1.entrySet()) {
        assertTrue(map2.containsKey(entry.getKey()));
        assertDeepEquals(msg, entry.getValue(), map2.get(entry.getKey()));
      }
    } else {
      assertEquals(msg, o1, o2);
    }
  }
}
