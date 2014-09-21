package com.tinlib.services;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.tinlib.analytics.AnalyticsHandler;
import com.tinlib.asynctest.AsyncTestCase;
import com.tinlib.core.TinKeys;
import com.tinlib.error.ErrorHandler;
import com.tinlib.erroringfirebase.ErroringFirebase;
import com.tinlib.generated.Action;
import com.tinlib.generated.Game;
import com.tinlib.convey.Subscriber0;
import com.tinlib.convey.Subscriber1;
import com.tinlib.test.*;
import com.tinlib.time.TimeService;
import com.tinlib.util.Procedure;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(MockitoJUnitRunner.class)
public class FacebookServiceTest extends AsyncTestCase {
  private static final String VIEWER_ID = TestUtils.newViewerId();
  private static final String VIEWER_KEY = TestUtils.newViewerKey();
  private static final String GAME_ID = TestUtils.newGameId();
  private static final String FACEBOOK_ID = "FACEBOOK_ID";

  @Mock
  AnalyticsHandler mockAnalyticsHandler;
  @Mock
  TimeService mockTimeService;
  @Mock
  ErrorHandler mockErrorHandler;

  @Test
  public void testUpgradeAccountToFacebook() {
    beginAsyncTestBlock(3);
    final Game testGame = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build();
    final Action testAction = TestUtils.newUnsubmittedActionWithCommand(GAME_ID).build();
    final Firebase firebase = new Firebase(TestHelper.FIREBASE_URL);
    TestConfiguration.Builder builder = newTestConfiguration(firebase, testGame, testAction);
    TestHelper.runTest(this, builder.build(), new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
        GameListService gameListService = new GameListService(helper.injector());
        helper.bus().once(TinKeys.GAME_LIST_ADD, new Subscriber0() {
          @Override
          public void onMessage() {
            FacebookService facebookService = new FacebookService(helper.injector());
            facebookService.upgradeAccountToFacebook(FACEBOOK_ID).addSuccessHandler(new Runnable() {
              @Override
              public void run() {
                FirebaseReferences facebookReferences =
                    FirebaseReferences.facebook(FACEBOOK_ID, firebase);
                Game expectedGame = testGame.toBuilder().setPlayer(0, FACEBOOK_ID).build();
                helper.assertGameEquals(expectedGame, FINISHED_RUNNABLE);

                facebookReferences.currentActionReferenceForGame(GAME_ID)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                      @Override
                      public void onDataChange(DataSnapshot dataSnapshot) {
                        assertEquals(testAction,
                            Action.newDeserializer().fromDataSnapshot(dataSnapshot));
                        finished();
                      }

                      @Override
                      public void onCancelled(FirebaseError firebaseError) {
                        fail("Current action listener cancelled.");
                      }
                    });

                helper.bus().once(TinKeys.VIEWER_ID, new Subscriber1<String>() {
                  @Override
                  public void onMessage(String viewerId) {
                    assertEquals(FACEBOOK_ID, viewerId);
                    finished();
                  }
                });
              }
            });
          }
        });
      }
    });
    endAsyncTestBlock();

    TestHelper.verifyTrackedEvent(mockAnalyticsHandler);
  }

  @Test
  public void testUpgradeToFacebookFirebaseGamesError() {
    runTestUpgradeToFacebookError(new ErroringFirebase(TestHelper.FIREBASE_URL,
        "firebaseio-demo.com/games/" + GAME_ID, "runTransaction"));
  }

  @Test
  public void testUpgradeToFacebookFirebaseActionError() {
    runTestUpgradeToFacebookError(new ErroringFirebase(TestHelper.FIREBASE_URL,
        "firebaseio-demo.com/users/" + VIEWER_KEY + "/games", "addListenerForSingleValueEvent"));
  }

  @Test
  public void testUpgradeToFacebookFirebaseFacebookActionError() {
    runTestUpgradeToFacebookError(new ErroringFirebase(TestHelper.FIREBASE_URL,
        "firebaseio-demo.com/facebookUsers/" + FACEBOOK_ID + "/games",
        "setValue"));
  }

  private void runTestUpgradeToFacebookError(Firebase firebase) {
    beginAsyncTestBlock();
    final Game testGame = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build();
    final Action testAction = TestUtils.newUnsubmittedActionWithCommand(GAME_ID).build();
    TestConfiguration.Builder builder = newTestConfiguration(firebase, testGame, testAction);
    builder.setFailOnError(false);
    builder.multibindInstance(ErrorHandler.class, mockErrorHandler);
    TestHelper.runTest(this, builder.build(), new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
        GameListService gameListService = new GameListService(helper.injector());
        helper.bus().once(TinKeys.GAME_LIST_ADD, new Subscriber0() {
          @Override
          public void onMessage() {
            FacebookService facebookService = new FacebookService(helper.injector());
            facebookService.upgradeAccountToFacebook(FACEBOOK_ID)
                .addFailureHandler(FINISHED_RUNNABLE);
          }
        });
      }
    });
    endAsyncTestBlock();

    TestHelper.verifyErrorHandled(mockErrorHandler);
  }

  private TestConfiguration.Builder newTestConfiguration(Firebase firebase, Game game,
      Action action) {
    TestConfiguration.Builder builder = TestConfiguration.newBuilder();
    builder.multibindInstance(AnalyticsHandler.class, mockAnalyticsHandler);
    builder.setAnonymousViewer(VIEWER_ID, VIEWER_KEY);
    builder.setCurrentGame(game);
    builder.setCurrentAction(action);
    builder.bindInstance(TimeService.class, mockTimeService);
    builder.setFirebase(firebase);
    return builder;
  }
}
