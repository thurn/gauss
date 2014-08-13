package com.tinlib.test;

import com.firebase.client.DataSnapshot;
import com.firebase.client.ValueEventListener;
import com.google.common.collect.Maps;
import com.tinlib.generated.Action;
import com.tinlib.generated.Game;
import com.firebase.client.Firebase;
import com.firebase.client.Firebase.CompletionListener;
import com.firebase.client.FirebaseError;
import com.tinlib.analytics.AnalyticsHandler;
import com.tinlib.core.TinKeys;
import com.tinlib.core.TinModule;
import com.tinlib.error.ErrorHandler;
import com.tinlib.inject.*;
import com.tinlib.message.Bus;
import com.tinlib.push.PushNotificationHandler;
import com.tinlib.services.*;
import com.tinlib.time.LastModifiedService;
import com.tinlib.time.TimeService;
import org.mockito.Matchers;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.contains;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TestHelper {
  public static interface Test {
    public void run(TestHelper helper);
  }

  public static class Builder {
    private final TinTestCase testCase;
    private FirebaseReferences references;
    private Firebase firebase;
    private Firebase realFirebase = new Firebase(FIREBASE_URL);
    private TestHelper testHelper;
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
    private final Map<String, Object> instanceMap = Maps.newHashMap();

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

    public void bindInstance(String key, Object value) {
      instanceMap.put(key, value);
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

      final TestHelper testHelper = new TestHelper(firebase, viewerId, viewerKey, facebook,
          errorHandler, analyticsHandler, pushNotificationHandler, gameId, timeService,
          gameOverService, nextPlayerService, lastModifiedService, joinGameService, instanceMap);
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

  private TestHelper(final Firebase firebase,
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
      final Map<String, Object> instanceMap) {
    injector = OverridingInjector.newOverridingInjector(new TinModule(), new Module() {
      @Override
      public void configure(Binder binder) {
        if (firebase != null) {
          binder.bindSingletonKey(TinKeys.FIREBASE,
              Initializers.returnValue(firebase));
        }
        if (errorHandler != null) {
          binder.multibindKey(TinKeys.ERROR_HANDLERS,
              Initializers.returnValue(errorHandler));
        }
        if (analyticsHandler != null) {
          binder.multibindKey(TinKeys.ANALYTICS_HANDLERS,
              Initializers.returnValue(analyticsHandler));
        }
        if (pushNotificationHandler != null) {
          binder.multibindKey(TinKeys.PUSH_NOTIFICATION_HANDLERS,
              Initializers.returnValue(pushNotificationHandler));
        }
        if (timeService != null) {
          binder.bindSingletonKey(TinKeys.TIME_SERVICE,
              Initializers.returnValue(timeService));
        }
        if (gameOverService != null) {
          binder.bindSingletonKey(TinKeys.GAME_OVER_SERVICE,
              Initializers.returnValue(gameOverService));
        }
        if (nextPlayerService != null) {
          binder.bindSingletonKey(TinKeys.NEXT_PLAYER_SERVICE,
              Initializers.returnValue(nextPlayerService));
        }
        if (lastModifiedService != null) {
          binder.bindSingletonKey(TinKeys.LAST_MODIFIED_SERVICE,
              Initializers.returnValue(lastModifiedService));
        }
        if (joinGameService != null) {
          binder.bindSingletonKey(TinKeys.JOIN_GAME_SERVICE,
              Initializers.returnValue(joinGameService));
        }
        for (Map.Entry<String, Object> entry : instanceMap.entrySet()) {
          binder.bindSingletonKey(entry.getKey(), Initializers.returnValue(entry.getValue()));
        }
      }
    });
    injector.get(TinKeys.COMMAND_LISTENER); // TODO: Make this not necessary
    injector.get(TinKeys.GAME_OVER_LISTENER);
    injector.get(TinKeys.SUBMITTED_ACTION_LISTENER);
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

  public Bus bus() {
    return injector.get(TinKeys.BUS);
  }

  public KeyedListenerService getKeyedListenerService() {
    return injector.get(TinKeys.KEYED_LISTENER_SERVICE);
  }

  public Firebase firebase() {
    return injector.get(TinKeys.FIREBASE);
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
    bus().clearAll();
    firebase().removeValue(new CompletionListener() {
      @Override
      public void onComplete(FirebaseError firebaseError, Firebase firebase) {
        done.run();
      }
    });
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
