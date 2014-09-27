package com.tinlib.services;

import com.firebase.client.Firebase;
import com.tinlib.analytics.AnalyticsHandler;
import com.tinlib.asynctest.AsyncTestCase;
import com.tinlib.defer.SuccessHandler;
import com.tinlib.error.ErrorHandler;
import com.tinlib.erroringfirebase.ErroringFirebase;
import com.tinlib.generated.Action;
import com.tinlib.generated.Command;
import com.tinlib.generated.Game;
import com.tinlib.test.TestConfiguration;
import com.tinlib.test.TestHelper;
import com.tinlib.test.TestUtils;
import com.tinlib.time.LastModifiedService;
import com.tinlib.util.Procedure;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UndoServiceTest extends AsyncTestCase {
  private static final String VIEWER_ID = TestUtils.newViewerId();
  private static final String VIEWER_KEY = TestUtils.newViewerKey();
  private static final String GAME_ID = TestUtils.newGameId();

  @Mock
  private AnalyticsHandler mockAnalyticsHandler;
  @Mock
  private LastModifiedService mockLastModifiedService;
  @Mock
  private ErrorHandler mockErrorHandler;

  @Test
  public void testUndo() {
    beginAsyncTestBlock();
    final Game testGame = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build();
    final Action testAction = TestUtils.newUnsubmittedActionWithCommand(GAME_ID).build();
    TestConfiguration.Builder builder = newTestConfig(testGame, testAction);
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    TestHelper.runTest(this, builder.build(), new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
        UndoService undoService = helper.injector().get(UndoService.class);
        undoService.undo().addSuccessHandler(new SuccessHandler<Command>() {
          @Override
          public void onSuccess(Command command) {
            assertEquals(testAction.getCommand(0), command);
            Action expected = testAction.toBuilder()
                .clearCommandList()
                .addFutureCommand(testAction.getCommand(0))
                .build();
            helper.assertCurrentActionEquals(expected, FINISHED_RUNNABLE);
          }
        });
      }
    });
    endAsyncTestBlock();

    TestHelper.verifyTrackedEvent(mockAnalyticsHandler);
    verify(mockLastModifiedService).updateLastModified(eq(GAME_ID));
  }

  @Test
  public void testCantUndo() {
    beginAsyncTestBlock();
    final Game testGame = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build();
    final Action testAction = TestUtils.newEmptyAction(GAME_ID).build();
    TestConfiguration.Builder builder = newTestConfig(testGame, testAction);
    builder.setFailOnError(false);
    builder.multibindInstance(ErrorHandler.class, mockErrorHandler);
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    TestHelper.runTest(this, builder.build(), new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
        UndoService undoService = helper.injector().get(UndoService.class);
        undoService.undo().addFailureHandler(FINISHED_RUNNABLE);
      }
    });
    endAsyncTestBlock();
    TestHelper.verifyErrorHandled(mockErrorHandler);
  }

  @Test
  public void testUndoFirebaseError() {
    beginAsyncTestBlock();
    final Game testGame = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build();
    final Action testAction = TestUtils.newUnsubmittedActionWithCommand(GAME_ID).build();
    TestConfiguration.Builder builder = newTestConfig(testGame, testAction);
    builder.setFailOnError(false);
    builder.multibindInstance(ErrorHandler.class, mockErrorHandler);
    builder.setFirebase(new ErroringFirebase(TestHelper.FIREBASE_URL,
        "games/" + GAME_ID + "/currentAction", "runTransaction"));
    TestHelper.runTest(this, builder.build(), new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
        UndoService undoService = helper.injector().get(UndoService.class);
        undoService.undo().addFailureHandler(FINISHED_RUNNABLE);
      }
    });
    endAsyncTestBlock();
    TestHelper.verifyErrorHandled(mockErrorHandler);
  }

  @Test
  public void testRedo() {
    beginAsyncTestBlock();
    final Game testGame = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build();
    final Action testAction = TestUtils.newEmptyAction(GAME_ID)
        .setPlayerNumber(0)
        .addFutureCommand(Command.newBuilder().setPlayerNumber(0).build())
        .build();
    TestConfiguration.Builder builder = newTestConfig(testGame, testAction);
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    TestHelper.runTest(this, builder.build(), new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
        UndoService undoService = helper.injector().get(UndoService.class);
        undoService.redo().addSuccessHandler(new SuccessHandler<Command>() {
          @Override
          public void onSuccess(Command command) {
            assertEquals(testAction.getFutureCommand(0), command);
            Action expected = testAction.toBuilder()
                .clearFutureCommandList()
                .addCommand(testAction.getFutureCommand(0))
                .build();
            helper.assertCurrentActionEquals(expected, FINISHED_RUNNABLE);
          }
        });
      }
    });
    endAsyncTestBlock();

    TestHelper.verifyTrackedEvent(mockAnalyticsHandler);
    verify(mockLastModifiedService).updateLastModified(eq(GAME_ID));
  }

  @Test
  public void testCantRedo() {
    beginAsyncTestBlock();
    final Game testGame = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build();
    final Action testAction = TestUtils.newEmptyAction(GAME_ID).build();
    TestConfiguration.Builder builder = newTestConfig(testGame, testAction);
    builder.setFailOnError(false);
    builder.multibindInstance(ErrorHandler.class, mockErrorHandler);
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    TestHelper.runTest(this, builder.build(), new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
        UndoService undoService = helper.injector().get(UndoService.class);
        undoService.redo().addFailureHandler(FINISHED_RUNNABLE);
      }
    });
    endAsyncTestBlock();
    TestHelper.verifyErrorHandled(mockErrorHandler);
  }

  @Test
  public void testRedoFirebaseError() {
    beginAsyncTestBlock();
    final Game testGame = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build();
    final Action testAction = TestUtils.newEmptyAction(GAME_ID)
        .addFutureCommand(Command.newBuilder().setPlayerNumber(0).build())
        .build();
    TestConfiguration.Builder builder = newTestConfig(testGame, testAction);
    builder.setFailOnError(false);
    builder.multibindInstance(ErrorHandler.class, mockErrorHandler);
    builder.setFirebase(new ErroringFirebase(TestHelper.FIREBASE_URL,
        "games/" + GAME_ID + "/currentAction", "runTransaction"));
    TestHelper.runTest(this, builder.build(), new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
        UndoService undoService = helper.injector().get(UndoService.class);
        undoService.redo().addFailureHandler(FINISHED_RUNNABLE);
      }
    });
    endAsyncTestBlock();
    TestHelper.verifyErrorHandled(mockErrorHandler);
  }

  private TestConfiguration.Builder newTestConfig(Game testGame, Action testAction) {
    TestConfiguration.Builder builder = TestConfiguration.newBuilder();
    builder.setAnonymousViewer(VIEWER_ID, VIEWER_KEY);
    builder.setCurrentGame(testGame);
    builder.setCurrentAction(testAction);
    builder.multibindInstance(AnalyticsHandler.class, mockAnalyticsHandler);
    builder.bindInstance(LastModifiedService.class, mockLastModifiedService);
    return builder;
  }
}
