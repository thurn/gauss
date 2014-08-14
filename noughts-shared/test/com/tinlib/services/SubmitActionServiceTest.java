package com.tinlib.services;

import com.firebase.client.Firebase;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.tinlib.analytics.AnalyticsHandler;
import com.tinlib.core.TinMessages;
import com.tinlib.generated.Action;
import com.tinlib.generated.Game;
import com.tinlib.message.Subscriber0;
import com.tinlib.push.PushNotificationHandler;
import com.tinlib.test.ErroringFirebase;
import com.tinlib.test.TestHelper;
import com.tinlib.test.TestUtils;
import com.tinlib.test.TinTestCase;
import com.tinlib.time.TimeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class SubmitActionServiceTest extends TinTestCase {
  private static final String VIEWER_ID = TestUtils.newViewerId();
  private static final String VIEWER_KEY = TestUtils.newViewerKey();
  private static final String GAME_ID = TestUtils.newGameId();

  @Mock
  private AnalyticsHandler mockAnalyticsHandler;
  @Mock
  private PushNotificationHandler mockPushNotificationHandler;
  @Mock
  private GameOverService mockGameOverService;
  @Mock
  private NextPlayerService mockNextPlayerService;
  @Mock
  private TimeService mockTimeService;

  @Test
  public void testSubmitCurrentAction() {
    beginAsyncTestBlock(2);
    final Game testGame = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build();
    final Action testAction = TestUtils.newUnsubmittedActionWithCommand(GAME_ID)
        .build();
    TestHelper.Builder builder = newBuilder(testGame, testAction);
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(final TestHelper helper) {
        SubmitActionService submitService = new SubmitActionService(helper.injector());

        final Game expectedGame = testGame.toBuilder()
            .addSubmittedAction(testAction.toBuilder().setIsSubmitted(true))
            .setLastModified(456L)
            .setCurrentPlayerNumber(1)
            .build();
        final Action expectedAction = TestUtils.newEmptyAction(GAME_ID).build();
        helper.bus().once(TinMessages.SUBMIT_ACTION_COMPLETED, new Subscriber0() {
          @Override
          public void onMessage() {
          helper.assertGameEquals(expectedGame, FINISHED);
          helper.assertCurrentActionEquals(expectedAction, FINISHED);
          }
        });

        when(mockTimeService.currentTimeMillis()).thenReturn(456L);
        when(mockGameOverService.computeVictors(eq(testGame), eq(testAction)))
            .thenReturn(Optional.<List<Integer>>absent());
        when(mockNextPlayerService.nextPlayerNumber(eq(testGame), eq(testAction)))
            .thenReturn(1);

        submitService.submitCurrentAction();
      }
    });
    endAsyncTestBlock();

    TestHelper.verifyTrackedEvent(mockAnalyticsHandler);
    TestHelper.verifyPushSent(mockPushNotificationHandler, GAME_ID, 1, "It's your turn");
  }

  @Test
  public void testSubmitCurrentActionEndsGame() {
    beginAsyncTestBlock(2);
    final Game testGame = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build();
    final Action testAction = TestUtils.newUnsubmittedActionWithCommand(GAME_ID).build();
    TestHelper.Builder builder = newBuilder(testGame, testAction);
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(final TestHelper helper) {
        SubmitActionService submitService = new SubmitActionService(helper.injector());

        final Game expectedGame = testGame.toBuilder()
            .addSubmittedAction(testAction.toBuilder().setIsSubmitted(true))
            .setLastModified(456L)
            .clearCurrentPlayerNumber()
            .addVictor(0)
            .setIsGameOver(true)
            .build();
        final Action expectedAction = TestUtils.newEmptyAction(GAME_ID).build();
        helper.bus().once(TinMessages.SUBMIT_ACTION_COMPLETED, new Subscriber0() {
          @Override
          public void onMessage() {
            helper.assertGameEquals(expectedGame, FINISHED);
            helper.assertCurrentActionEquals(expectedAction, FINISHED);
          }
        });

        when(mockTimeService.currentTimeMillis()).thenReturn(456L);
        when(mockGameOverService.computeVictors(eq(testGame), eq(testAction)))
            .thenReturn(Optional.<List<Integer>>of(ImmutableList.of(0)));

        submitService.submitCurrentAction();
      }
    });
    endAsyncTestBlock();

    TestHelper.verifyTrackedEvent(mockAnalyticsHandler);
    TestHelper.verifyPushSent(mockPushNotificationHandler, GAME_ID, 1, "Game over");
  }

  @Test
  public void testSubmitAction() {
    beginAsyncTestBlock(2);
    final Game testGame = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build();
    final Action testAction = TestUtils.newEmptyAction(GAME_ID).build();
    TestHelper.Builder builder = newBuilder(testGame, testAction);
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(final TestHelper helper) {
        SubmitActionService submitService = new SubmitActionService(helper.injector());
        Action action = TestUtils.newUnsubmittedActionWithCommand(GAME_ID).build();

        final Game expectedGame = testGame.toBuilder()
            .addSubmittedAction(action.toBuilder().setIsSubmitted(true))
            .setLastModified(456L)
            .setCurrentPlayerNumber(1)
            .build();
        final Action expectedAction = TestUtils.newEmptyAction(GAME_ID).build();
        helper.bus().once(TinMessages.SUBMIT_ACTION_COMPLETED, new Subscriber0() {
          @Override
          public void onMessage() {
            helper.assertGameEquals(expectedGame, FINISHED);
            helper.assertCurrentActionEquals(expectedAction, FINISHED);
          }
        });

        when(mockTimeService.currentTimeMillis()).thenReturn(456L);
        when(mockGameOverService.computeVictors(eq(testGame), eq(action)))
            .thenReturn(Optional.<List<Integer>>absent());
        when(mockNextPlayerService.nextPlayerNumber(eq(testGame), eq(action)))
            .thenReturn(1);

        submitService.submitAction(action);
      }
    });
    endAsyncTestBlock();

    TestHelper.verifyTrackedEvent(mockAnalyticsHandler);
    TestHelper.verifyPushSent(mockPushNotificationHandler, GAME_ID, 1, "It's your turn");
  }

  @Test
  public void testSubmitActionIllegalAction() {
    beginAsyncTestBlock();
    final Game testGame = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build();
    final Action testAction = TestUtils.newEmptyAction(GAME_ID).build();
    TestHelper.Builder builder = newBuilder(testGame, testAction);
    builder.setErrorHandler(FINISHED_ERROR_HANDLER);
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(final TestHelper helper) {
        SubmitActionService submitService = new SubmitActionService(helper.injector());
        submitService.submitCurrentAction();
      }
    });
    endAsyncTestBlock();
  }

  @Test
  public void testSubmitActionFirebaseGameError() {
    runFirebaseErrorTest(new ErroringFirebase(TestHelper.FIREBASE_URL, "/games/" + GAME_ID,
        "runTransaction"));
  }

  @Test
  public void testSubmitActionFirebaseActionError() {
    runFirebaseErrorTest(new ErroringFirebase(TestHelper.FIREBASE_URL,
        "games/" + GAME_ID + "/currentAction", "setValue"));
  }

  private void runFirebaseErrorTest(ErroringFirebase firebase) {
    beginAsyncTestBlock();
    final Game testGame = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build();
    final Action testAction = TestUtils.newUnsubmittedActionWithCommand(GAME_ID)
        .build();
    TestHelper.Builder builder = newBuilder(testGame, testAction);
    builder.setFirebase(firebase);
    builder.setErrorHandler(FINISHED_ERROR_HANDLER);
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(final TestHelper helper) {
        SubmitActionService submitService = new SubmitActionService(helper.injector());
        when(mockTimeService.currentTimeMillis()).thenReturn(456L);
        when(mockGameOverService.computeVictors(eq(testGame), eq(testAction)))
            .thenReturn(Optional.<List<Integer>>absent());
        when(mockNextPlayerService.nextPlayerNumber(eq(testGame), eq(testAction)))
            .thenReturn(1);
        submitService.submitCurrentAction();
      }
    });
    endAsyncTestBlock();
  }

  private TestHelper.Builder newBuilder(Game testGame, Action testAction) {
    TestHelper.Builder builder = TestHelper.newBuilder(this);
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    builder.setAnonymousViewer(VIEWER_ID, VIEWER_KEY);
    builder.setGame(testGame);
    builder.setCurrentAction(testAction);
    builder.setGameOverService(mockGameOverService);
    builder.setNextPlayerService(mockNextPlayerService);
    builder.setTimeService(mockTimeService);
    builder.setAnalyticsHandler(mockAnalyticsHandler);
    builder.setPushNotificationHandler(mockPushNotificationHandler);
    return builder;
  }
}
