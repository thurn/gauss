package com.tinlib.test;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.common.collect.SetMultimap;
import com.tinlib.analytics.AnalyticsHandler;
import com.tinlib.asynctest.AsyncTestCase;
import com.tinlib.asynctest.CleanupFunction;
import com.tinlib.convey.Bus;
import com.tinlib.core.TinModule;
import com.tinlib.error.ErrorHandler;
import com.tinlib.generated.Action;
import com.tinlib.generated.Game;
import com.tinlib.infuse.*;
import com.tinlib.jgail.service.AIActionAdapter;
import com.tinlib.jgail.service.AIProvider;
import com.tinlib.push.PushNotificationHandler;
import com.tinlib.services.*;
import com.tinlib.util.Procedure;
import org.mockito.Matchers;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TestHelper implements CleanupFunction {
  public static String FIREBASE_URL = "https://tintest.firebaseio-demo.com";
  private final TestConfiguration testConfiguration;
  private final Injector injector;

  private static class TestInputsModule implements Module {
    @Override
    public void configure(Binder binder) {
      binder.bindClass(NextPlayerService.class,
          Initializers.returnValue(mock(NextPlayerService.class)));
      binder.bindClass(GameOverService.class,
          Initializers.returnValue(mock(GameOverService.class)));
      binder.bindClass(AIProvider.class,
          Initializers.returnValue(mock(AIProvider.class)));
      binder.bindClass(AIActionAdapter.class,
          Initializers.returnValue(mock(AIActionAdapter.class)));
    }
  }

  private TestHelper(AsyncTestCase testCase, TestConfiguration testConfiguration) {
    this.testConfiguration = testConfiguration;
    testCase.addCleanupFunction(this);
    injector = createInjector(testConfiguration);
  }

  public static void runTest(AsyncTestCase testCase, final TestConfiguration testConfiguration,
        final Procedure<TestHelper> test) {
    final TestHelper helper = new TestHelper(testCase, testConfiguration);
    if (testConfiguration.getViewerId() != null) {
      ViewerService viewerService = helper.injector().get(ViewerService.class);
      if (testConfiguration.isFacebook()) {
        viewerService.setViewerFacebookId(testConfiguration.getViewerId());
      } else {
        viewerService.setViewerAnonymousId(
            testConfiguration.getViewerId(), testConfiguration.getViewerKey());
      }
    }
    if (testConfiguration.getGame() == null) {
      test.run(helper);
    } else {
      final FirebaseReferences firebaseReferences =
          helper.getFirebaseReferencesWithFirebase(new Firebase(TestHelper.FIREBASE_URL));
      final Game game = testConfiguration.getGame();
      firebaseReferences.gameReference(game.getId()).setValue(game.serialize(),
          new Firebase.CompletionListener() {
        @Override
        public void onComplete(FirebaseError firebaseError, Firebase firebase) {
          final CurrentGameListener currentGameListener =
              helper.injector().get(CurrentGameListener.class);
          if (testConfiguration.getAction() == null) {
            currentGameListener.loadGame(game.getId());
            test.run(helper);
          } else {
            Action action = testConfiguration.getAction();
            firebaseReferences.currentActionReferenceForGame(game.getId()).setValue(
                action.serialize(), new Firebase.CompletionListener() {
              @Override
              public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                currentGameListener.loadGame(game.getId());
                test.run(helper);
              }
            });
          }
        }
      });
    }
  }

  private static Injector createInjector(final TestConfiguration testConfiguration) {
    return Injectors.newOverridingTestInjector(new TinModule(), new TestInputsModule(),
        new Module() {
      @Override
      public void configure(Binder binder) {
        if (testConfiguration.getFirebase() != null) {
          binder.bindClass(Firebase.class,
              Initializers.returnValue(testConfiguration.getFirebase()));
        }
        if (testConfiguration.getFailOnError()) {
          binder.multibindClass(ErrorHandler.class,
              Initializers.<ErrorHandler>returnValue(new ErrorHandler() {
            @Override
            public void error(String message, Object[] args) {
              fail(String.format(message, args));
            }
          }));
        }
        for (Class<?> key : testConfiguration.getClassMap().keySet()) {
          bindValue(binder, key, testConfiguration.getClassMap().get(key));
        }
        SetMultimap<Class<?>, Object> multiClassMap = testConfiguration.getMultiClassMap();
        for (Class<?> key : testConfiguration.getMultiClassMap().keySet()) {
          for (Object value : multiClassMap.get(key)) {
            multibindValue(binder, key, value);
          }
        }
      }
    });
  }

  @SuppressWarnings("unchecked")
  private static <T> void bindValue(Binder binder, Class<?> classObject, T value) {
    binder.bindClass((Class<T>)classObject, Initializers.returnValue(value));
  }

  @SuppressWarnings("unchecked")
  private static <T> void multibindValue(Binder binder, Class<?> classObject, T value) {
    binder.multibindClass((Class<T>)classObject, Initializers.returnValue(value));
  }

  public Injector injector() {
    return injector;
  }

  public Bus bus() {
    return injector.get(Bus.class);
  }

  public Firebase firebase() {
    return injector.get(Firebase.class);
  }

  public FirebaseReferences references() {
    return getFirebaseReferencesWithFirebase(firebase());
  }


  public void assertGameEquals(final Game game, final Runnable runnable) {
    references().gameReference(game.getId()).addListenerForSingleValueEvent(
        new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
            assertEquals(game, Game.newDeserializer().fromDataSnapshot(dataSnapshot));
            runnable.run();
          }

          @Override
          public void onCancelled(FirebaseError firebaseError) {
            fail("assertGameEquals listener cancelled.");
          }
        });
  }

  public void assertCurrentActionEquals(final Action action, final Runnable runnable) {
    references().currentActionReferenceForGame(action.getGameId()).addListenerForSingleValueEvent(
        new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
            assertEquals(action, Action.newDeserializer().fromDataSnapshot(dataSnapshot));
            runnable.run();
          }

          @Override
          public void onCancelled(FirebaseError firebaseError) {
            fail("assertCurrentActionEquals listener cancelled.");
          }
        });
  }

  public void assertRequestIdEquals(String requestId, final String gameId,
                                    final Runnable runnable) {
    references().requestReference(requestId).addListenerForSingleValueEvent(
        new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
            assertEquals(gameId, dataSnapshot.getValue());
            runnable.run();
          }

          @Override
          public void onCancelled(FirebaseError firebaseError) {
            fail("assertRequestIdEquals listener cancelled");
          }
        });
  }

  public static ErrorHandler finishedErrorHandler(final Runnable finishedRunnable) {
    return new ErrorHandler() {
      @Override
      public void error(String message, Object[] args) {
        finishedRunnable.run();
      }
    };
  }

  public static void verifyTrackedEvent(AnalyticsHandler handler) {
    verify(handler, times(1)).trackEvent(anyString(), Matchers.<Map<String, String>>any());
  }

  public static void verifyPushSent(PushNotificationHandler handler, String gameId,
                                    int playerNumber, String substring) {
    verify(handler, times(1)).sendPushNotification(eq(gameId), eq(playerNumber),
        contains(substring));
  }

  public static void verifyErrorHandled(ErrorHandler handler) {
    verify(handler, times(1)).error(anyString(), any(Object[].class));
  }

  private FirebaseReferences getFirebaseReferencesWithFirebase(Firebase firebase) {
    if (testConfiguration.isFacebook()) {
      return FirebaseReferences.facebook(testConfiguration.getViewerId(), firebase);
    } else {
      return FirebaseReferences.anonymous(testConfiguration.getViewerKey(), firebase);
    }
  }

  @Override
  public void cleanUpAsync(final CountDownLatch countDownLatch) {
    injector.get(KeyedListenerService.class).unregisterAll();
    new Firebase(FIREBASE_URL).removeValue(new Firebase.CompletionListener() {
      @Override
      public void onComplete(FirebaseError firebaseError, Firebase firebase) {
        countDownLatch.countDown();
      }
    });
  }
}
