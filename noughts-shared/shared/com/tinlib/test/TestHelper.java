package com.tinlib.test;

import ca.thurn.noughts.shared.entities.Action;
import ca.thurn.noughts.shared.entities.Game;
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
import com.tinlib.shared.*;
import org.mockito.Matchers;

import java.util.Map;

import static org.junit.Assert.fail;
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
    private Game game;
    private Action action;
    private String gameId;

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

    public void runTest(final Test test) {
      if (errorHandler == null) {
        errorHandler = new ErrorHandler() {
          @Override
          public void error(String message, Object[] args) {
            fail(String.format(message, args));
          }
        };
      }

      final TestHelper testHelper = new TestHelper(firebase, viewerId, viewerKey, facebook,
          errorHandler, analyticsHandler, pushNotificationHandler, gameId);
      testCase.setTestHelper(testHelper);
      if (game == null) {
        test.run(testHelper);
      } else {
        if (action == null) {
          action = Action.newBuilder().setGameId(game.getId()).build();
        }
        references.gameReference(game.getId()).setValue(game.serialize(), new CompletionListener() {
          @Override
          public void onComplete(FirebaseError firebaseError, Firebase firebase) {
            references.currentActionReferenceForGame(game.getId()).setValue(action.serialize(),
                new CompletionListener() {
              @Override
              public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                test.run(testHelper);
              }
            });
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
      String gameId) {
    injector = Injectors.newInjector(new TinModule(), new Module() {
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
      }
    });
    if (viewerId != null && viewerKey != null) {
      if (facebook) {
        (new Viewer(injector)).setViewerFacebookId(viewerId);
        references = FirebaseReferences.facebook(viewerId, firebase);
      } else {
        (new Viewer(injector)).setViewerAnonymousId(viewerId, viewerKey);
        references = FirebaseReferences.anonymous(viewerKey, firebase);
      }
    } else {
      references = null;
    }
    if (gameId != null) {
      (new CurrentGame(injector)).loadGame(gameId);
      new CurrentAction(injector);
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

  public void cleanUp() {
    getKeyedListenerService().unregisterAll();
    bus().clearAll();
  }

  public static void verifyTrackedEvent(AnalyticsHandler handler) {
    verify(handler, times(1)).trackEvent(Matchers.anyString(), Matchers.<Map<String, String>>any());
  }

  public static void verifyError(ErrorHandler handler) {
    verify(handler, times(1)).error(Matchers.anyString(), Matchers.any(Object[].class));
  }
}
