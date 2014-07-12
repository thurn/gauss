package com.tinlib.test;

import com.firebase.client.Firebase;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.jayway.awaitility.Awaitility;
import com.tinlib.inject.*;
import com.tinlib.message.Bus;
import com.tinlib.message.Buses;
import com.tinlib.message.Subscriber;
import com.tinlib.shared.AnalyticsService;
import com.tinlib.shared.ErrorService;
import com.tinlib.shared.KeyedListenerService;
import com.tinlib.shared.TinKeys;
import org.junit.After;
import org.junit.Before;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

public abstract class TinTestCase {
  public static interface ValueListener {
    public void onValue(Object object);
  }

  private class DefaultTestBindingsModule implements Module {
    @Override
    public void configure(Binder binder) {
      binder.bindSingletonKey(TinKeys.BUS,
          Initializers.returnValue(bus));
      binder.bindSingletonKey(TinKeys.KEYED_LISTENER_SERVICE,
          Initializers.returnValue(new KeyedListenerService()));
      binder.bindSingletonKey(TinKeys.ANALYTICS_SERVICE,
          Initializers.returnValue(new AnalyticsService() {
            @Override
            public void trackEvent(String name) {}

            @Override
            public void trackEvent(String name, Map<String, String> dimensions) {}
          }));
      binder.bindSingletonKey(TinKeys.ERROR_SERVICE, new Initializer() {
        @Override
        public Object initialize(Injector injector) {
          return new ErrorService(injector);
        }
      });
    }
  }

  private class FirebaseModule implements Module {
    @Override
    public void configure(Binder binder) {
      binder.bindSingletonKey(TinKeys.FIREBASE, Initializers.returnValue(firebase));
    }
  }

  private static class ErroringFirebaseModule implements Module {
    @Override
    public void configure(Binder binder) {
      binder.bindSingletonKey(TinKeys.FIREBASE,
          Initializers.returnValue(new ErroringFirebase("https://tintest.firebaseio-demo.com")));
    }
  }

  private final AtomicBoolean finished = new AtomicBoolean(false);
  private final AtomicInteger numFinishes = new AtomicInteger(0);
  private final List<Subscriber> subscribersToCleanUp = Lists.newArrayList();
  protected final Bus bus = Buses.newBus();
  protected final Firebase firebase = new Firebase("https://tintest.firebaseio-demo.com");
  private final Runnable finishedRunnable = new Runnable() {
    @Override
    public void run() {
      finished();
    }
  };

  @Before
  public final void tinSetUp() {
    beginAsyncTestBlock();
    setUp(finishedRunnable);
    endAsyncTestBlock();
  }

  @After
  public final void tinTearDown() {
    beginAsyncTestBlock();
    tearDown(finishedRunnable);
    for (Subscriber subscriber : subscribersToCleanUp) {
      bus.unregister(subscriber);
    }
    endAsyncTestBlock();
  }

  public abstract void setUp(Runnable done);

  public abstract void tearDown(Runnable done);

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

  public void schedule(int delayMillis, final Runnable runnable) {
    new java.util.Timer().schedule(new TimerTask() {
      @Override
      public void run() {
        runnable.run();
      }
    }, delayMillis);
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

  public void expectMessage(final String message, final ValueListener listener) {
    Subscriber subscriber = new Subscriber() {
      @Override
      public void onMessage(String name, Object value) {
        assertEquals(message, name);
        listener.onValue(value);
      }
    };
    subscribersToCleanUp.add(subscriber);
    bus.register(subscriber, ImmutableList.of(message));
  }

  public Injector newTestInjector() {
    return newTestInjector(new EmptyModule());
  }

  public Injector newTestInjector(Module module) {
    return Injectors.newInjector(module, new DefaultTestBindingsModule(), new FirebaseModule());
  }

  public Injector newErroringFirebaseInjector() {
    return newErroringFirebaseInjector(new EmptyModule());
  }

  public Injector newErroringFirebaseInjector(Module module) {
    return Injectors.newInjector(module, new DefaultTestBindingsModule(),
        new ErroringFirebaseModule());
  }
}
