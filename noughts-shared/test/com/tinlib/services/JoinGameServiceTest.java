package com.tinlib.services;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.common.base.Optional;
import com.tinlib.analytics.AnalyticsHandler;
import com.tinlib.core.TinMessages2;
import com.tinlib.generated.Game;
import com.tinlib.generated.Profile;
import com.tinlib.message.Subscriber1;
import com.tinlib.push.PushNotificationHandler;
import com.tinlib.test.ErroringFirebase;
import com.tinlib.test.TestHelper;
import com.tinlib.test.TestUtils;
import com.tinlib.test.TinTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class JoinGameServiceTest extends TinTestCase {
  private static final String VIEWER_ID = TestUtils.newViewerId();
  private static final String VIEWER_KEY = TestUtils.newViewerKey();
  private static final String GAME_ID = TestUtils.newGameId();
  private static final String REQUEST_ID = "REQUEST_ID";

  @Mock
  private AnalyticsHandler mockAnalyticsHandler;
  @Mock
  private PushNotificationHandler mockPushNotificationHandler;

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
    TestHelper.Builder builder = newTestHelper(testGame);
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(final TestHelper helper) {
        JoinGameService joinGameService = new JoinGameService(helper.injector());
        helper.bus2().await(TinMessages2.JOIN_GAME_COMPLETED, new Subscriber1<Game>() {
          @Override
          public void onMessage(Game game) {
            final Game expected = testGame.toBuilder()
                .addPlayer(VIEWER_ID)
                .addProfile(testProfile)
                .build();
            assertEquals(expected, game);
            helper.assertGameEquals(expected, FINISHED);
            helper.assertCurrentActionEquals(TestUtils.newEmptyAction(GAME_ID).build(), FINISHED);
          }
        });
        if (fromRequestId) {
          joinGameService.joinGameFromRequestId(1, REQUEST_ID, Optional.of(testProfile));
        } else {
          joinGameService.joinGame(1, GAME_ID, Optional.of(testProfile));
        }
      }
    });
  }

  @Test
  public void testCantJoinGame() {
    beginAsyncTestBlock();
    final Game testGame = TestUtils.newGameWithOnePlayer(GAME_ID).setIsGameOver(true).build();
    final Profile testProfile = Profile.newBuilder().setName("Name").build();
    TestHelper.Builder builder = newTestHelper(testGame);
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    builder.setErrorHandler(FINISHED_ERROR_HANDLER);
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(final TestHelper helper) {
        JoinGameService joinGameService = new JoinGameService(helper.injector());
        joinGameService.joinGame(1, GAME_ID, Optional.of(testProfile));
      }
    });
    endAsyncTestBlock();
  }

  @Test
  public void testJoinGameFirebaseGameError() {
    beginAsyncTestBlock();
    final Game testGame = TestUtils.newGameWithOnePlayer(GAME_ID).build();
    final Profile testProfile = Profile.newBuilder().setName("Name").build();
    TestHelper.Builder builder = newTestHelper(testGame);
    builder.setErrorHandler(FINISHED_ERROR_HANDLER);
    builder.setFirebase(new ErroringFirebase(TestHelper.FIREBASE_URL,
        "firebaseio-demo.com/games/" + GAME_ID, "runTransaction"));
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(final TestHelper helper) {
        JoinGameService joinGameService = new JoinGameService(helper.injector());
        joinGameService.joinGame(1, GAME_ID, Optional.of(testProfile));
      }
    });
    endAsyncTestBlock();
  }

  @Test
  public void testJoinGameFirebaseActionError() {
    beginAsyncTestBlock();
    final Game testGame = TestUtils.newGameWithOnePlayer(GAME_ID).build();
    final Profile testProfile = Profile.newBuilder().setName("Name").build();
    TestHelper.Builder builder = newTestHelper(testGame);
    builder.setErrorHandler(FINISHED_ERROR_HANDLER);
    builder.setFirebase(new ErroringFirebase(TestHelper.FIREBASE_URL,
        "games/" + GAME_ID + "/currentAction", "setValue"));
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(final TestHelper helper) {
        JoinGameService joinGameService = new JoinGameService(helper.injector());
        joinGameService.joinGame(1, GAME_ID, Optional.of(testProfile));
      }
    });
    endAsyncTestBlock();
  }

  @Test
  public void testJoinGameFirebaseRequestsError() {
    beginAsyncTestBlock();
    final Game testGame = TestUtils.newGameWithOnePlayer(GAME_ID).build();
    final Profile testProfile = Profile.newBuilder().setName("Name").build();
    TestHelper.Builder builder = newTestHelper(testGame);
    builder.setErrorHandler(FINISHED_ERROR_HANDLER);
    builder.setFirebase(new ErroringFirebase(TestHelper.FIREBASE_URL,
        "firebaseio-demo.com/requests", "addListenerForSingleValueEvent"));
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(final TestHelper helper) {
        JoinGameService joinGameService = new JoinGameService(helper.injector());
        joinGameService.joinGameFromRequestId(1, REQUEST_ID, Optional.of(testProfile));
      }
    });
    endAsyncTestBlock();
  }

  private TestHelper.Builder newTestHelper(Game testGame) {
    TestHelper.Builder builder = TestHelper.newBuilder(this);
    builder.setAnonymousViewer(VIEWER_ID, VIEWER_KEY);
    builder.setGame(testGame);
    builder.setAnalyticsHandler(mockAnalyticsHandler);
    builder.setPushNotificationHandler(mockPushNotificationHandler);
    return builder;
  }
}
