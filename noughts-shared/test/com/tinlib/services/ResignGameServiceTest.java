package com.tinlib.services;

import com.firebase.client.Firebase;
import com.tinlib.analytics.AnalyticsHandler;
import com.tinlib.core.TinMessages2;
import com.tinlib.generated.Action;
import com.tinlib.generated.Game;
import com.tinlib.message.Subscriber1;
import com.tinlib.test.ErroringFirebase;
import com.tinlib.test.TestHelper;
import com.tinlib.test.TestUtils;
import com.tinlib.test.TinTestCase;
import com.tinlib.time.TimeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ResignGameServiceTest extends TinTestCase {
  private static final String VIEWER_ID = TestUtils.newViewerId();
  private static final String VIEWER_KEY = TestUtils.newViewerKey();
  private static final String GAME_ID = TestUtils.newGameId();

  @Mock
  private AnalyticsHandler mockAnalyticsHandler;
  @Mock
  private TimeService mockTimeService;

  @Test
  public void testResignGame() {
    beginAsyncTestBlock();
    final Game testGame = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build();
    final Action testAction = TestUtils.newUnsubmittedActionWithCommand(GAME_ID).build();
    Firebase firebase = new Firebase(TestHelper.FIREBASE_URL);
    TestHelper.Builder builder = newTestHelper(firebase, testGame, testAction);
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(final TestHelper helper) {
        ResignGameService resignGameService = new ResignGameService(helper.injector());
        helper.bus2().once(TinMessages2.RESIGN_GAME_COMPLETED, new Subscriber1<Game>() {
          @Override
          public void onMessage(Game game) {
            Game expected = testGame.toBuilder()
                .setIsGameOver(true)
                .clearCurrentPlayerNumber()
                .addVictor(1)
                .setLastModified(777L)
                .build();
            assertEquals(expected, game);
            helper.assertGameEquals(expected, FINISHED);
          }
        });
        when(mockTimeService.currentTimeMillis()).thenReturn(777L);
        resignGameService.resignGame(GAME_ID);
      }
    });
    endAsyncTestBlock();

    TestHelper.verifyTrackedEvent(mockAnalyticsHandler);
  }

  @Test
  public void testResignGameError() {
    runTestResignGameError(TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID)
        .setIsGameOver(true)
        .build(), new Firebase(TestHelper.FIREBASE_URL));
  }

  @Test
  public void testResignGameFirebaseGameError() {
    runTestResignGameError(TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build(),
        new ErroringFirebase(TestHelper.FIREBASE_URL, "firebaseio-demo.com/games/" + GAME_ID,
            "runTransaction"));
  }

  @Test
  public void testResignGameFirebaseActionError() {
    runTestResignGameError(TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build(),
        new ErroringFirebase(TestHelper.FIREBASE_URL,
            "firebaseio-demo.com/users/" + VIEWER_KEY + "/games",
            "setValue"));
  }

  private void runTestResignGameError(Game testGame, Firebase firebase) {
    beginAsyncTestBlock();
    final Action testAction = TestUtils.newUnsubmittedActionWithCommand(GAME_ID).build();
    TestHelper.Builder builder = newTestHelper(firebase, testGame, testAction);
    builder.setErrorHandler(FINISHED_ERROR_HANDLER);
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(final TestHelper helper) {
        ResignGameService resignGameService = new ResignGameService(helper.injector());
        resignGameService.resignGame(GAME_ID);
      }
    });
    endAsyncTestBlock();
  }

  private TestHelper.Builder newTestHelper(Firebase firebase, Game testGame, Action testAction) {
    TestHelper.Builder builder = TestHelper.newBuilder(this);
    builder.setFirebase(firebase);
    builder.setAnonymousViewer(VIEWER_ID, VIEWER_KEY);
    builder.setGame(testGame);
    builder.setCurrentAction(testAction);
    builder.setAnalyticsHandler(mockAnalyticsHandler);
    builder.setTimeService(mockTimeService);
    return builder;
  }
}
