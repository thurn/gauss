package com.tinlib.test;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.Firebase.CompletionListener;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;
import com.tinlib.analytics.AnalyticsHandler;
import com.tinlib.asynctest.CleanupFunction;
import com.tinlib.convey.Bus;
import com.tinlib.core.TinModule;
import com.tinlib.error.ErrorHandler;
import com.tinlib.generated.Action;
import com.tinlib.generated.Game;
import com.tinlib.infuse.*;
import com.tinlib.push.PushNotificationHandler;
import com.tinlib.services.*;
import com.tinlib.time.LastModifiedService;
import com.tinlib.time.TimeService;
import org.mockito.Matchers;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.contains;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class TestHelperTwo implements CleanupFunction {
  private static class TestInputsModule implements Module {
    @Override
    public void configure(Binder binder) {
      binder.bindClass(NextPlayerService.class,
          Initializers.returnValue(mock(NextPlayerService.class)));
      binder.bindClass(GameOverService.class,
          Initializers.returnValue(mock(GameOverService.class)));
    }
  }

  public static interface Test {
    public void run(TestHelperTwo helper);
  }

  public static class Builder {
    private final TinTestCase testCase;
    private FirebaseReferences references;
    private Firebase firebase;
    private Firebase realFirebase = new Firebase(FIREBASE_URL);
    private TestHelperTwo testHelper;
    private String viewerId;
    private String viewerKey;
    private boolean facebook;
    private ErrorHandler errorHandler;
    private AnalyticsHandler analyticsHandler;
    private PushNotificationHandler pushNotificationHandler;
    private TimeService timeService;
    private GameOverService gameOverService;
    private NextPlayerService nextPlayerService;
    private Game game;
    private Action action;
    private String gameId;
    private LastModifiedService lastModifiedService;
    private JoinGameService joinGameService;
    private final ClassToInstanceMap<Object> classToInstanceMap =
        MutableClassToInstanceMap.create();

    private Builder(TinTestCase testCase) {
      this.testCase = testCase;
    }

    public void setErrorHandler(ErrorHandler errorHandler) {
      this.errorHandler = errorHandler;
    }

    public void setPushNotificationHandler(PushNotificationHandler pushNotificationHandler) {
      this.pushNotificationHandler = pushNotificationHandler;
    }

    public void setAnalyticsHandler(AnalyticsHandler analyticsHandler) {
      this.analyticsHandler = analyticsHandler;
    }

    public void setFirebase(Firebase firebase) {
      this.firebase = firebase;
    }

    public void setAnonymousViewer(String viewerId, String viewerKey) {
      this.viewerId = viewerId;
      this.viewerKey = viewerKey;
      this.facebook = false;
      this.references = FirebaseReferences.anonymous(viewerKey, realFirebase);
    }

    public void setFacebookViewer(String facebookId) {
      this.viewerId = facebookId;
      this.viewerKey = facebookId;
      this.facebook = true;
      references = FirebaseReferences.facebook(facebookId, realFirebase);
    }

    public void setGame(Game game) {
      this.game = game;
      this.gameId = game.getId();
    }

    public void setCurrentAction(Action action) {
      this.action = action;
    }

    public void setTimeService(TimeService timeService) {
      this.timeService = timeService;
    }

    public void setGameOverService(GameOverService gameOverService) {
      this.gameOverService = gameOverService;
    }

    public void setNextPlayerService(NextPlayerService nextPlayerService) {
      this.nextPlayerService = nextPlayerService;
    }

    public void setLastModifiedService(LastModifiedService lastModifiedService) {
      this.lastModifiedService = lastModifiedService;
    }

    public void setJoinGameService(JoinGameService joinGameService) {
      this.joinGameService = joinGameService;
    }

    public <T> void bindInstance(Class<T> key, T value) {
      classToInstanceMap.putInstance(key, value);
    }

    public void runTest(final Test test) {
      if (firebase == null) {
        throw new RuntimeException("setFirebase() is required.");
      }
      if (errorHandler == null) {
        errorHandler = new ErrorHandler() {
          @Override
          public void error(String message, Object[] args) {
            fail(String.format(message, args));
          }
        };
      }

      final TestHelperTwo testHelper = new TestHelperTwo(firebase, viewerId, viewerKey, facebook,
          errorHandler, analyticsHandler, pushNotificationHandler, gameId, timeService,
          gameOverService, nextPlayerService, lastModifiedService, joinGameService, classToInstanceMap);
      testCase.setTestHelper(testHelper);
      if (game == null) {
        test.run(testHelper);
      } else {
        references.gameReference(game.getId()).setValue(game.serialize(), new CompletionListener() {
          @Override
          public void onComplete(FirebaseError firebaseError, Firebase firebase) {
            if (action == null) {
              test.run(testHelper);
            } else {
              references.currentActionReferenceForGame(game.getId()).setValue(action.serialize(),
                  new CompletionListener() {
                @Override
                public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                  test.run(testHelper);
                }
              });
            }
          }
        });
      }
    }
  }

  public static String FIREBASE_URL = "https://tintest.firebaseio-demo.com";

  private final FirebaseReferences references;
  private final Injector injector;

  public static Builder newBuilder(TinTestCase testCase) {
    return new Builder(testCase);
  }

  private TestHelperTwo(final Firebase firebase,
                        String viewerId,
                        String viewerKey,
                        boolean facebook,
                        final ErrorHandler errorHandler,
                        final AnalyticsHandler analyticsHandler,
                        final PushNotificationHandler pushNotificationHandler,
                        String gameId,
                        final TimeService timeService,
                        final GameOverService gameOverService,
                        final NextPlayerService nextPlayerService,
                        final LastModifiedService lastModifiedService,
                        final JoinGameService joinGameService,
                        final ClassToInstanceMap<Object> classToInstanceMap) {
    injector = Injectors.newOverridingTestInjector(new TinModule(), new TestInputsModule(),
        new Module() {
      @Override
      public void configure(Binder binder) {
        if (firebase != null) {
          binder.bindClass(Firebase.class,
              Initializers.returnValue(firebase));
        }
        if (errorHandler != null) {
          binder.multibindClass(ErrorHandler.class,
              Initializers.returnValue(errorHandler));
        }
        if (analyticsHandler != null) {
          binder.multibindClass(AnalyticsHandler.class,
              Initializers.returnValue(analyticsHandler));
        }
        if (pushNotificationHandler != null) {
          binder.multibindClass(PushNotificationHandler.class,
              Initializers.returnValue(pushNotificationHandler));
        }
        if (timeService != null) {
          binder.bindClass(TimeService.class,
              Initializers.returnValue(timeService));
        }
        if (gameOverService != null) {
          binder.bindClass(GameOverService.class,
              Initializers.returnValue(gameOverService));
        }
        if (nextPlayerService != null) {
          binder.bindClass(NextPlayerService.class,
              Initializers.returnValue(nextPlayerService));
        }
        if (lastModifiedService != null) {
          binder.bindClass(LastModifiedService.class,
              Initializers.returnValue(lastModifiedService));
        }
        if (joinGameService != null) {
          binder.bindClass(JoinGameService.class,
              Initializers.returnValue(joinGameService));
        }
        for (Class<?> classObject : classToInstanceMap.keySet()) {
          bindValue(binder, classObject, classToInstanceMap.getInstance(classObject));
        }
      }
    });
    if (viewerId != null && viewerKey != null) {
      if (facebook) {
        (new ViewerService(injector)).setViewerFacebookId(viewerId);
        references = FirebaseReferences.facebook(viewerId, firebase);
      } else {
        (new ViewerService(injector)).setViewerAnonymousId(viewerId, viewerKey);
        references = FirebaseReferences.anonymous(viewerKey, firebase);
      }
    } else {
      references = null;
    }
    if (gameId != null) {
      (new CurrentGameListener(injector)).loadGame(gameId);
      new CurrentActionListener(injector);
    }
  }

  @SuppressWarnings("unchecked")
  private <T> void bindValue(Binder binder, Class<?> classObject, T value) {
    binder.bindClass((Class<T>)classObject, Initializers.returnValue(value));
  }

  public Bus bus() {
    return injector.get(Bus.class);
  }

  public KeyedListenerService getKeyedListenerService() {
    return injector.get(KeyedListenerService.class);
  }

  public Firebase firebase() {
    return injector.get(Firebase.class);
  }

  public FirebaseReferences references() {
    return references;
  }

  public Injector injector() {
    return injector;
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

  public void cleanUp(final Runnable done) {
    getKeyedListenerService().unregisterAll();
    firebase().removeValue(new CompletionListener() {
      @Override
      public void onComplete(FirebaseError firebaseError, Firebase firebase) {
        done.run();
      }
    });
  }

  @Override
  public void cleanUpAsync(final CountDownLatch countDownLatch) {
    firebase().removeValue(new CompletionListener() {
      @Override
      public void onComplete(FirebaseError firebaseError, Firebase firebase) {
        countDownLatch.countDown();
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
    verify(handler, times(1)).trackEvent(Matchers.anyString(), Matchers.<Map<String, String>>any());
  }

  public static void verifyPushSent(PushNotificationHandler handler, String gameId,
      int playerNumber, String substring) {
    verify(handler, times(1)).sendPushNotification(eq(gameId), eq(playerNumber),
        contains(substring));
  }
}
