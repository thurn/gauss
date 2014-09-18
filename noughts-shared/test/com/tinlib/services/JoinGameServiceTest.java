package com.tinlib.services;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.common.base.Optional;
import com.tinlib.analytics.AnalyticsHandler;
import com.tinlib.asynctest.AsyncTestCase;
import com.tinlib.defer.SuccessHandler;
import com.tinlib.error.ErrorHandler;
import com.tinlib.generated.Game;
import com.tinlib.generated.Profile;
import com.tinlib.push.PushNotificationHandler;
import com.tinlib.test.ErroringFirebase;
import com.tinlib.test.TestConfiguration;
import com.tinlib.test.TestHelper;
import com.tinlib.test.TestUtils;
import com.tinlib.util.Procedure;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class JoinGameServiceTest extends AsyncTestCase {
  private static final String VIEWER_ID = TestUtils.newViewerId();
  private static final String VIEWER_KEY = TestUtils.newViewerKey();
  private static final String GAME_ID = TestUtils.newGameId();
  private static final String REQUEST_ID = "REQUEST_ID";

  @Mock
  private AnalyticsHandler mockAnalyticsHandler;
  @Mock
  private PushNotificationHandler mockPushNotificationHandler;
  @Mock
  private ErrorHandler mockErrorHandler;

  @Test
  public void testJoinGame() {
    beginAsyncTestBlock(2);
    runTestJoinGame(false /* fromRequestId */);
    endAsyncTestBlock();

    TestHelper.verifyTrackedEvent(mockAnalyticsHandler);
    verify(mockPushNotificationHandler, times(1)).registerForPushNotifications(eq(GAME_ID), eq(1));
  }

  @Test
  public void testJoinGameFromRequestId() {
    beginAsyncTestBlock(2);
    Firebase firebase = new Firebase(TestHelper.FIREBASE_URL);
    FirebaseReferences references = FirebaseReferences.anonymous(VIEWER_KEY, firebase);
    references.requestReference(REQUEST_ID).setValue(GAME_ID, new Firebase.CompletionListener() {
      @Override
      public void onComplete(FirebaseError firebaseError, Firebase firebase) {
        runTestJoinGame(true /* fromRequestId*/);
      }
    });
    endAsyncTestBlock();

    TestHelper.verifyTrackedEvent(mockAnalyticsHandler);
    verify(mockPushNotificationHandler, times(1)).registerForPushNotifications(eq(GAME_ID), eq(1));
  }

  private void runTestJoinGame(final boolean fromRequestId) {
    final Game testGame = TestUtils.newGameWithOnePlayer(GAME_ID).build();
    final Profile testProfile = Profile.newBuilder().setName("Name").build();
    TestConfiguration.Builder builder = newTestConfig(testGame);
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    TestHelper.runTest(this, builder.build(), new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
        JoinGameService joinGameService = new JoinGameService(helper.injector());
        SuccessHandler<Game> successHandler = new SuccessHandler<Game>() {
          @Override
          public void onSuccess(Game game) {
            final Game expected = testGame.toBuilder()
                .addPlayer(VIEWER_ID)
                .addProfile(testProfile)
                .build();
            assertEquals(expected, game);
            helper.assertGameEquals(expected, FINISHED_RUNNABLE);
            helper.assertCurrentActionEquals(TestUtils.newEmptyAction(GAME_ID).build(),
                FINISHED_RUNNABLE);
          }
        };

        if (fromRequestId) {
          joinGameService
              .joinGameFromRequestId(1, REQUEST_ID, Optional.of(testProfile))
              .addSuccessHandler(successHandler);
        } else {
          joinGameService
              .joinGame(1, GAME_ID, Optional.of(testProfile))
              .addSuccessHandler(successHandler);
        }
      }
    });
  }

  @Test
  public void testCantJoinGame() {
    beginAsyncTestBlock();
    final Game testGame = TestUtils.newGameWithOnePlayer(GAME_ID).setIsGameOver(true).build();
    final Profile testProfile = Profile.newBuilder().setName("Name").build();
    TestConfiguration.Builder builder = newTestConfig(testGame);
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    builder.setFailOnError(false);
    builder.multibindInstance(ErrorHandler.class, mockErrorHandler);
    TestHelper.runTest(this, builder.build(), new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
        JoinGameService joinGameService = new JoinGameService(helper.injector());
        joinGameService
            .joinGame(1, GAME_ID, Optional.of(testProfile))
            .addFailureHandler(FINISHED_RUNNABLE);
      }
    });
    endAsyncTestBlock();

    TestHelper.verifyErrorHandled(mockErrorHandler);
  }

  @Test
  public void testJoinGameFirebaseGameError() {
    beginAsyncTestBlock();
    final Game testGame = TestUtils.newGameWithOnePlayer(GAME_ID).build();
    final Profile testProfile = Profile.newBuilder().setName("Name").build();
    TestConfiguration.Builder builder = newTestConfig(testGame);
    builder.setFailOnError(false);
    builder.multibindInstance(ErrorHandler.class, mockErrorHandler);
    builder.setFirebase(new ErroringFirebase(TestHelper.FIREBASE_URL,
        "firebaseio-demo.com/games/" + GAME_ID, "runTransaction"));
    TestHelper.runTest(this, builder.build(), new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
        JoinGameService joinGameService = new JoinGameService(helper.injector());
        joinGameService
            .joinGame(1, GAME_ID, Optional.of(testProfile))
            .addFailureHandler(FINISHED_RUNNABLE);
      }
    });
    endAsyncTestBlock();

    TestHelper.verifyErrorHandled(mockErrorHandler);
  }

  @Test
  public void testJoinGameFirebaseActionError() {
    beginAsyncTestBlock();
    final Game testGame = TestUtils.newGameWithOnePlayer(GAME_ID).build();
    final Profile testProfile = Profile.newBuilder().setName("Name").build();
    TestConfiguration.Builder builder = newTestConfig(testGame);
    builder.setFailOnError(false);
    builder.multibindInstance(ErrorHandler.class, mockErrorHandler);
    builder.setFirebase(new ErroringFirebase(TestHelper.FIREBASE_URL,
        "games/" + GAME_ID + "/currentAction", "setValue"));
    TestHelper.runTest(this, builder.build(), new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
        JoinGameService joinGameService = new JoinGameService(helper.injector());
        joinGameService
            .joinGame(1, GAME_ID, Optional.of(testProfile))
            .addFailureHandler(FINISHED_RUNNABLE);
      }
    });
    endAsyncTestBlock();

    TestHelper.verifyErrorHandled(mockErrorHandler);
  }

  @Test
  public void testJoinGameFirebaseRequestsError() {
    beginAsyncTestBlock();
    final Game testGame = TestUtils.newGameWithOnePlayer(GAME_ID).build();
    final Profile testProfile = Profile.newBuilder().setName("Name").build();
    TestConfiguration.Builder builder = newTestConfig(testGame);
    builder.setFailOnError(false);
    builder.multibindInstance(ErrorHandler.class, mockErrorHandler);
    builder.setFirebase(new ErroringFirebase(TestHelper.FIREBASE_URL,
        "firebaseio-demo.com/requests", "addListenerForSingleValueEvent"));
    TestHelper.runTest(this, builder.build(), new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
        JoinGameService joinGameService = new JoinGameService(helper.injector());
        joinGameService
            .joinGameFromRequestId(1, REQUEST_ID, Optional.of(testProfile))
            .addFailureHandler(FINISHED_RUNNABLE);
      }
    });
    endAsyncTestBlock();

    TestHelper.verifyErrorHandled(mockErrorHandler);
  }

  private TestConfiguration.Builder newTestConfig(Game testGame) {
    TestConfiguration.Builder builder = TestConfiguration.newBuilder();
    builder.setAnonymousViewer(VIEWER_ID, VIEWER_KEY);
    builder.setCurrentGame(testGame);
    builder.multibindInstance(AnalyticsHandler.class, mockAnalyticsHandler);
    builder.multibindInstance(PushNotificationHandler.class, mockPushNotificationHandler);
    return builder;
  }
}
