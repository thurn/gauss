package com.tinlib.test;

import ca.thurn.noughts.shared.entities.Action;
import ca.thurn.noughts.shared.entities.Game;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.common.collect.Lists;
import com.jayway.awaitility.Awaitility;
import com.tinlib.analytics.AnalyticsHandler;
import com.tinlib.core.TinKeys;
import com.tinlib.core.TinModule;
import com.tinlib.error.ErrorHandler;
import com.tinlib.inject.*;
import com.tinlib.message.Bus;
import com.tinlib.shared.*;
import org.junit.After;
import org.junit.Before;
import org.mockito.Matchers;
import org.mockito.Mock;

import java.util.Iterator;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public abstract class TinTestCase {
  public static interface TestFunction {
    public void runTest(Injector injector);
  }

  public class FirebaseModule implements Module {
    @Override
    public void configure(Binder binder) {
      binder.bindSingletonKey(TinKeys.FIREBASE, Initializers.returnValue(firebase));
    }
  }

  public static class ErroringFirebaseModule implements Module {
    @Override
    public void configure(Binder binder) {
      binder.bindSingletonKey(TinKeys.FIREBASE,
          Initializers.returnValue(new ErroringFirebase("https://tintest.firebaseio-demo.com")));
    }
  }

  public class MockErrorHandlerModule implements Module {
    @Override
    public void configure(Binder binder) {
      binder.multibindKey(TinKeys.ERROR_HANDLERS,
          Initializers.returnValue(mockErrorHandler));
    }
  }

  public class FailOnErrorModule implements Module {
    @Override
    public void configure(Binder binder) {
      binder.multibindKey(TinKeys.ERROR_HANDLERS, Initializers.returnValue(new ErrorHandler() {
        @Override
        public void error(String message, Object[] args) {
          fail(message);
        }
      }));
    }
  }

  public class MockAnalyticsHandlerModule implements Module {
    @Override
    public void configure(Binder binder) {
      binder.multibindKey(TinKeys.ANALYTICS_HANDLERS,
          Initializers.returnValue(mockAnalyticsHandler));
    }
  }

  public static final String VIEWER_ID = "viewerId";
  public static final String VIEWER_KEY = "viewerKey";

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
    beginAsyncTestBlock();
    firebase.removeValue(new Firebase.CompletionListener() {
      @Override
      public void onComplete(FirebaseError firebaseError, Firebase firebase) {
        setUp(finishedRunnable);
      }
    });
    endAsyncTestBlock();
  }

  @After
  public final void tinTearDown() {
    beginAsyncTestBlock();
    tearDown(finishedRunnable);
    if (testHelper != null) {
      testHelper.cleanUp();
    }
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

  public Injector newTinInjector(Module... modules) {
    Injector result = Injectors.newInjector(Lists.asList(new TinModule(), modules));
    bus = result.get(TinKeys.BUS);
    keyedListenerService = result.get(TinKeys.KEYED_LISTENER_SERVICE);
    return result;
  }

  public void verifyError() {
    verify(mockErrorHandler, times(1)).error(Matchers.anyString(), Matchers.any(Object[].class));
  }

  public void verifyTrackEvent() {
    verify(mockAnalyticsHandler, times(1)).trackEvent(Matchers.anyString(),
        Matchers.<Map<String, String>>any());
  }

  public void withTestGameAndAction(final Game game, final Action action,
      final TestFunction function) {
    final FirebaseReferences references = FirebaseReferences.anonymous("viewerKey", firebase);
    references.gameReference(game.getId()).setValue(game.serialize(),
        new Firebase.CompletionListener() {
      @Override
      public void onComplete(FirebaseError firebaseError, Firebase firebase) {
        references.currentActionReferenceForGame(game.getId()).setValue(action.serialize(),
            new Firebase.CompletionListener() {
          @Override
          public void onComplete(FirebaseError firebaseError, Firebase firebase) {
            Injector injector = newTinInjector(new FirebaseModule(),
                new MockAnalyticsHandlerModule(),
                new FailOnErrorModule());
            Viewer viewer = new Viewer(injector);
            viewer.setViewerAnonymousId(VIEWER_ID, VIEWER_KEY);
            CurrentGame currentGame = new CurrentGame(injector);
            currentGame.loadGame(game.getId());
            new CurrentAction(injector);
            function.runTest(injector);
          }
        });
      }
    });
  }
}
