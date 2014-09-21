package com.tinlib.services;

import com.firebase.client.Firebase;
import com.google.common.collect.ImmutableList;
import com.tinlib.analytics.AnalyticsHandler;
import com.tinlib.asynctest.AsyncTestCase;
import com.tinlib.defer.SuccessHandler;
import com.tinlib.error.ErrorHandler;
import com.tinlib.generated.Action;
import com.tinlib.generated.Command;
import com.tinlib.generated.Game;
import com.tinlib.generated.IndexCommand;
import com.tinlib.erroringfirebase.ErroringFirebase;
import com.tinlib.test.TestConfiguration;
import com.tinlib.test.TestHelper;
import com.tinlib.test.TestUtils;
import com.tinlib.time.LastModifiedService;
import com.tinlib.util.Procedure;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AddCommandServiceTest extends AsyncTestCase {
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
  public void testAddCommand() {
    beginAsyncTestBlock();
    final Game testGame = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build();
    final Action testAction = TestUtils.newEmptyAction(GAME_ID)
        .addFutureCommand(Command.newBuilder().setPlayerNumber(17).build())
        .build();
    TestConfiguration.Builder builder = newTestConfig(testGame, testAction);
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    TestHelper.runTest(this, builder.build(), new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
        AddCommandService addCommandService = new AddCommandService(helper.injector());
        final Command.Builder testCommand = Command.newBuilder();

        addCommandService.addCommand(testCommand.build()).addSuccessHandler(
            new SuccessHandler<Command>() {
          @Override
          public void onSuccess(Command command) {
            assertEquals(testCommand.setPlayerNumber(0).build(), command);
            Action expected = testAction.toBuilder()
                .addCommand(testCommand.setPlayerNumber(0))
                .setPlayerNumber(0)
                .clearFutureCommandList()
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
  public void testAddCommandIllegal() {
    beginAsyncTestBlock();
    final Game testGame = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID)
        .setCurrentPlayerNumber(1)
        .build();
    final Action testAction = TestUtils.newEmptyAction(GAME_ID).build();
    TestConfiguration.Builder builder = newTestConfig(testGame, testAction);
    builder.setFailOnError(false);
    builder.multibindInstance(ErrorHandler.class, mockErrorHandler);
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    TestHelper.runTest(this, builder.build(), new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
        new AddCommandService(helper.injector()).addCommand(Command.newBuilder().build())
            .addFailureHandler(FINISHED_RUNNABLE);
      }
    });
    endAsyncTestBlock();
    TestHelper.verifyErrorHandled(mockErrorHandler);
  }

  @Test
  public void testAddCommandFirebaseError() {
    beginAsyncTestBlock();
    final Game testGame = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build();
    final Action testAction = TestUtils.newEmptyAction(GAME_ID).build();
    TestConfiguration.Builder builder = newTestConfig(testGame, testAction);
    builder.setFailOnError(false);
    builder.multibindInstance(ErrorHandler.class, mockErrorHandler);
    builder.setFirebase(new ErroringFirebase(TestHelper.FIREBASE_URL,
        "games/" + GAME_ID + "/currentAction", "runTransaction"));
    TestHelper.runTest(this, builder.build(), new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
        new AddCommandService(helper.injector()).addCommand(Command.newBuilder().build())
            .addFailureHandler(FINISHED_RUNNABLE);
      }
    });
    endAsyncTestBlock();
    TestHelper.verifyErrorHandled(mockErrorHandler);
  }

  @Test
  public void testSetCommand() {
    beginAsyncTestBlock();
    final Game testGame = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build();
    final Action testAction = Action.newBuilder()
        .setGameId(GAME_ID)
        .setPlayerNumber(0)
        .addCommand(Command.newBuilder().setPlayerNumber(42))
        .build();
    TestConfiguration.Builder builder = newTestConfig(testGame, testAction);
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    TestHelper.runTest(this, builder.build(), new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
        AddCommandService addCommandService = new AddCommandService(helper.injector());
        final Command.Builder testCommand = Command.newBuilder();

        addCommandService.setCommand(0, testCommand.build()).addSuccessHandler(
            new SuccessHandler<IndexCommand>() {
          @Override
          public void onSuccess(IndexCommand indexCommand) {
            assertEquals(testCommand.setPlayerNumber(0).build(), indexCommand.getCommand());
            assertEquals(0, indexCommand.getIndex());
            Action expected = testAction.toBuilder()
                .setCommand(0, testCommand.setPlayerNumber(0))
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
  public void testSetCommandIllegal() {
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
        new AddCommandService(helper.injector()).setCommand(0, Command.newBuilder().build())
            .addFailureHandler(FINISHED_RUNNABLE);
      }
    });
    endAsyncTestBlock();
    TestHelper.verifyErrorHandled(mockErrorHandler);
  }

  @Test
  public void testSetCommandFirebaseError() {
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
        new AddCommandService(helper.injector()).setCommand(0, Command.newBuilder().build())
            .addFailureHandler(FINISHED_RUNNABLE);
      }
    });
    endAsyncTestBlock();
    TestHelper.verifyErrorHandled(mockErrorHandler);
  }

  @Test
  public void testAddCommandsToAction() {
    beginAsyncTestBlock();
    final Game testGame = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build();
    final Action testAction = TestUtils.newEmptyAction(GAME_ID).build();
    TestConfiguration.Builder builder = newTestConfig(testGame, testAction);
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    TestHelper.runTest(this, builder.build(), new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
        AddCommandService addCommandService = new AddCommandService(helper.injector());
        Action.Builder action = TestUtils.newEmptyAction(GAME_ID);
        List<Command> commands = ImmutableList.of(Command.newBuilder().setPlayerNumber(12).build());
        addCommandService.addCommandsToAction(VIEWER_ID, testGame, action, commands);
        assertEquals(1, action.build().getCommandCount());
        assertEquals(0, action.build().getCommand(0).getPlayerNumber());
        finished();
      }
    });
    endAsyncTestBlock();
  }

  private TestConfiguration.Builder newTestConfig(Game testGame, Action testAction) {
    return TestConfiguration.newBuilder()
        .setAnonymousViewer(VIEWER_ID, VIEWER_KEY)
        .setCurrentGame(testGame)
        .setCurrentAction(testAction)
        .multibindInstance(AnalyticsHandler.class, mockAnalyticsHandler)
        .bindInstance(LastModifiedService.class, mockLastModifiedService);
  }
}
