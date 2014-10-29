package com.tinlib.services;

import com.firebase.client.Firebase;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.tinlib.analytics.AnalyticsHandler;
import com.tinlib.asynctest.AsyncTestCase;
import com.tinlib.defer.SuccessHandler;
import com.tinlib.error.ErrorHandler;
import com.tinlib.generated.Action;
import com.tinlib.generated.Game;
import com.tinlib.push.PushNotificationHandler;
import com.tinlib.erroringfirebase.ErroringFirebase;
import com.tinlib.test.TestConfiguration;
import com.tinlib.test.TestHelper;
import com.tinlib.test.TestUtils;
import com.tinlib.time.TimeService;
import com.tinlib.util.Procedure;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class SubmitActionServiceTest extends AsyncTestCase {
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
  @Mock
  private ErrorHandler mockErrorHandler;

  @Test
  public void testSubmitCurrentAction() {
    beginAsyncTestBlock(2);
    final Game testGame = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build();
    final Action testAction = TestUtils.newUnsubmittedActionWithCommand(GAME_ID)
        .build();
    TestConfiguration.Builder builder = newTestConfig(testGame, testAction);
    TestHelper.runTest(this, builder.build(), new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
        SubmitActionService submitService = helper.injector().get(SubmitActionService.class);

        final Game expectedGame = testGame.toBuilder()
            .addSubmittedAction(testAction.toBuilder().setIsSubmitted(true))
            .setLastModified(456L)
            .setCurrentPlayerNumber(1)
            .build();
        final Action expectedAction = TestUtils.newEmptyAction(GAME_ID).build();

        when(mockTimeService.currentTimeMillis()).thenReturn(456L);
        when(mockGameOverService.computeVictors(eq(testGame), eq(testAction)))
            .thenReturn(Optional.<List<Integer>>absent());
        when(mockNextPlayerService.nextPlayerNumber(eq(testGame), eq(testAction)))
            .thenReturn(1);

        submitService.submitCurrentAction().addSuccessHandler(new SuccessHandler<Void>() {
          @Override
          public void onSuccess(Void value) {
            helper.assertGameEquals(expectedGame, FINISHED_RUNNABLE);
            helper.assertCurrentActionEquals(expectedAction, FINISHED_RUNNABLE);
          }
        });
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
    TestConfiguration.Builder builder = newTestConfig(testGame, testAction);
    TestHelper.runTest(this, builder.build(), new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
        SubmitActionService submitService = helper.injector().get(SubmitActionService.class);

        final Game expectedGame = testGame.toBuilder()
            .addSubmittedAction(testAction.toBuilder().setIsSubmitted(true))
            .setLastModified(456L)
            .clearCurrentPlayerNumber()
            .addVictor(0)
            .setIsGameOver(true)
            .build();
        final Action expectedAction = TestUtils.newEmptyAction(GAME_ID).build();

        when(mockTimeService.currentTimeMillis()).thenReturn(456L);
        when(mockGameOverService.computeVictors(eq(testGame), eq(testAction)))
            .thenReturn(Optional.<List<Integer>>of(ImmutableList.of(0)));

        submitService.submitCurrentAction().addSuccessHandler(new SuccessHandler<Void>() {
          @Override
          public void onSuccess(Void value) {
            helper.assertGameEquals(expectedGame, FINISHED_RUNNABLE);
            helper.assertCurrentActionEquals(expectedAction, FINISHED_RUNNABLE);
          }
        });
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
    TestConfiguration.Builder builder = newTestConfig(testGame, testAction);
    TestHelper.runTest(this, builder.build(), new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
        SubmitActionService submitService = helper.injector().get(SubmitActionService.class);
        Action action = TestUtils.newUnsubmittedActionWithCommand(GAME_ID).build();

        final Game expectedGame = testGame.toBuilder()
            .addSubmittedAction(action.toBuilder().setIsSubmitted(true))
            .setLastModified(456L)
            .setCurrentPlayerNumber(1)
            .build();
        final Action expectedAction = TestUtils.newEmptyAction(GAME_ID).build();

        when(mockTimeService.currentTimeMillis()).thenReturn(456L);
        when(mockGameOverService.computeVictors(eq(testGame), eq(action)))
            .thenReturn(Optional.<List<Integer>>absent());
        when(mockNextPlayerService.nextPlayerNumber(eq(testGame), eq(action)))
            .thenReturn(1);

        submitService.submitAction(action).addSuccessHandler(new SuccessHandler<Void>() {
          @Override
          public void onSuccess(Void value) {
            helper.assertGameEquals(expectedGame, FINISHED_RUNNABLE);
            helper.assertCurrentActionEquals(expectedAction, FINISHED_RUNNABLE);
          }
        });
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
    TestConfiguration.Builder builder = newTestConfig(testGame, testAction);
    builder.setFailOnError(false);
    builder.multibindInstance(ErrorHandler.class, mockErrorHandler);
    TestHelper.runTest(this, builder.build(), new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
        SubmitActionService submitService = helper.injector().get(SubmitActionService.class);
        submitService.submitCurrentAction().addFailureHandler(FINISHED_RUNNABLE);
      }
    });
    endAsyncTestBlock();

    TestHelper.verifyErrorHandled(mockErrorHandler);
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
    TestConfiguration.Builder builder = newTestConfig(testGame, testAction);
    builder.setFirebase(firebase);
    builder.setFailOnError(false);
    builder.multibindInstance(ErrorHandler.class, mockErrorHandler);
    TestHelper.runTest(this, builder.build(), new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
        SubmitActionService submitService = helper.injector().get(SubmitActionService.class);
        when(mockTimeService.currentTimeMillis()).thenReturn(456L);
        when(mockGameOverService.computeVictors(eq(testGame), eq(testAction)))
            .thenReturn(Optional.<List<Integer>>absent());
        when(mockNextPlayerService.nextPlayerNumber(eq(testGame), eq(testAction)))
            .thenReturn(1);
        submitService.submitCurrentAction().addFailureHandler(FINISHED_RUNNABLE);
      }
    });
    endAsyncTestBlock();

    TestHelper.verifyErrorHandled(mockErrorHandler);
  }

  private TestConfiguration.Builder newTestConfig(Game testGame, Action testAction) {
    TestConfiguration.Builder builder = TestConfiguration.newBuilder();
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    builder.setAnonymousViewer(VIEWER_ID, VIEWER_KEY);
    builder.setCurrentGame(testGame);
    builder.setCurrentAction(testAction);
    builder.bindInstance(GameOverService.class, mockGameOverService);
    builder.bindInstance(NextPlayerService.class, mockNextPlayerService);
    builder.bindInstance(TimeService.class, mockTimeService);
    builder.multibindInstance(AnalyticsHandler.class, mockAnalyticsHandler);
    builder.multibindInstance(PushNotificationHandler.class, mockPushNotificationHandler);
    return builder;
  }
}
