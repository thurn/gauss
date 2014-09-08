package com.tinlib.services;

import com.firebase.client.Firebase;
import com.tinlib.analytics.AnalyticsHandler;
import com.tinlib.core.TinKeys;
import com.tinlib.generated.Action;
import com.tinlib.generated.Command;
import com.tinlib.generated.Game;
import com.tinlib.convey.Subscriber1;
import com.tinlib.test.ErroringFirebase;
import com.tinlib.test.TestHelper;
import com.tinlib.test.TestUtils;
import com.tinlib.test.TinTestCase;
import com.tinlib.time.LastModifiedService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UndoServiceTest extends TinTestCase {
  private static final String VIEWER_ID = TestUtils.newViewerId();
  private static final String VIEWER_KEY = TestUtils.newViewerKey();
  private static final String GAME_ID = TestUtils.newGameId();

  @Mock
  private AnalyticsHandler mockAnalyticsHandler;
  @Mock
  private LastModifiedService mockLastModifiedService;

  @Test
  public void testUndo() {
    beginAsyncTestBlock();
    final Game testGame = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build();
    final Action testAction = TestUtils.newUnsubmittedActionWithCommand(GAME_ID).build();
    TestHelper.Builder builder = newTestHelper(testGame, testAction);
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(final TestHelper helper) {
        UndoService undoService = new UndoService(helper.injector());

        helper.bus().await(TinKeys.COMMAND_UNDO_COMPLETED, new Subscriber1<Command>() {
          @Override
          public void onMessage(Command command) {
            assertEquals(testAction.getCommand(0), command);
            Action expected = testAction.toBuilder()
                .clearCommandList()
                .addFutureCommand(testAction.getCommand(0))
                .build();
            helper.assertCurrentActionEquals(expected, FINISHED);
          }
        });

        undoService.undo();
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
    TestHelper.Builder builder = newTestHelper(testGame, testAction);
    builder.setErrorHandler(FINISHED_ERROR_HANDLER);
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(final TestHelper helper) {
        UndoService undoService = new UndoService(helper.injector());
        undoService.undo();
      }
    });
    endAsyncTestBlock();
  }

  @Test
  public void testUndoFirebaseError() {
    beginAsyncTestBlock();
    final Game testGame = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build();
    final Action testAction = TestUtils.newUnsubmittedActionWithCommand(GAME_ID).build();
    TestHelper.Builder builder = newTestHelper(testGame, testAction);
    builder.setErrorHandler(FINISHED_ERROR_HANDLER);
    builder.setFirebase(new ErroringFirebase(TestHelper.FIREBASE_URL,
        "games/" + GAME_ID + "/currentAction", "runTransaction"));
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(final TestHelper helper) {
        UndoService undoService = new UndoService(helper.injector());
        undoService.undo();
      }
    });
    endAsyncTestBlock();
  }

  @Test
  public void testRedo() {
    beginAsyncTestBlock();
    final Game testGame = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build();
    final Action testAction = TestUtils.newEmptyAction(GAME_ID)
        .setPlayerNumber(0)
        .addFutureCommand(Command.newBuilder().setPlayerNumber(0).build())
        .build();
    TestHelper.Builder builder = newTestHelper(testGame, testAction);
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(final TestHelper helper) {
        UndoService undoService = new UndoService(helper.injector());

        helper.bus().await(TinKeys.COMMAND_REDO_COMPLETED, new Subscriber1<Command>() {
          @Override
          public void onMessage(Command command) {
            assertEquals(testAction.getFutureCommand(0), command);
            Action expected = testAction.toBuilder()
                .clearFutureCommandList()
                .addCommand(testAction.getFutureCommand(0))
                .build();
            helper.assertCurrentActionEquals(expected, FINISHED);
          }
        });

        undoService.redo();
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
    TestHelper.Builder builder = newTestHelper(testGame, testAction);
    builder.setErrorHandler(FINISHED_ERROR_HANDLER);
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(final TestHelper helper) {
        UndoService undoService = new UndoService(helper.injector());
        undoService.redo();
      }
    });
    endAsyncTestBlock();
  }

  @Test
  public void testRedoFirebaseError() {
    beginAsyncTestBlock();
    final Game testGame = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build();
    final Action testAction = TestUtils.newEmptyAction(GAME_ID)
        .addFutureCommand(Command.newBuilder().setPlayerNumber(0).build())
        .build();
    TestHelper.Builder builder = newTestHelper(testGame, testAction);
    builder.setErrorHandler(FINISHED_ERROR_HANDLER);
    builder.setFirebase(new ErroringFirebase(TestHelper.FIREBASE_URL,
        "games/" + GAME_ID + "/currentAction", "runTransaction"));
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(final TestHelper helper) {
        UndoService undoService = new UndoService(helper.injector());
        undoService.redo();
      }
    });
    endAsyncTestBlock();
  }

  private TestHelper.Builder newTestHelper(Game testGame, Action testAction) {
    TestHelper.Builder builder = TestHelper.newBuilder(this);
    builder.setAnonymousViewer(VIEWER_ID, VIEWER_KEY);
    builder.setGame(testGame);
    builder.setCurrentAction(testAction);
    builder.setAnalyticsHandler(mockAnalyticsHandler);
    builder.setLastModifiedService(mockLastModifiedService);
    return builder;
  }
}
