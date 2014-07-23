package com.tinlib.services;

import com.firebase.client.Firebase;
import com.tinlib.analytics.AnalyticsHandler;
import com.tinlib.core.TinMessages;
import com.tinlib.generated.Action;
import com.tinlib.generated.Command;
import com.tinlib.generated.Game;
import com.tinlib.generated.IndexCommand;
import com.tinlib.message.Subscriber1;
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
public class AddCommandServiceTest extends TinTestCase {
  private static final String VIEWER_ID = TestUtils.newViewerId();
  private static final String VIEWER_KEY = TestUtils.newViewerKey();
  private static final String GAME_ID = TestUtils.newGameId();

  @Mock
  private AnalyticsHandler mockAnalyticsHandler;
  @Mock
  private LastModifiedService mockLastModifiedService;

  @Test
  public void testAddCommand() {
    beginAsyncTestBlock();
    final Game testGame = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build();
    final Action testAction = TestUtils.newEmptyAction(GAME_ID)
        .addFutureCommand(Command.newBuilder().setPlayerNumber(17).build())
        .build();
    TestHelper.Builder builder = newTestHelper(testGame, testAction);
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(final TestHelper helper) {
        AddCommandService addCommandService = new AddCommandService(helper.injector());
        final Command.Builder testCommand = Command.newBuilder();

        helper.bus().await(TinMessages.COMMAND_ADDED, new Subscriber1<Command>() {
          @Override
          public void onMessage(Command command) {
            assertEquals(testCommand.setPlayerNumber(0).build(), command);
            Action expected = testAction.toBuilder()
                .addCommand(testCommand.setPlayerNumber(0))
                .setPlayerNumber(0)
                .clearFutureCommandList()
                .build();
            helper.assertCurrentActionEquals(expected, FINISHED);
          }
        });

       addCommandService.addCommand(testCommand.build());
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
    TestHelper.Builder builder = newTestHelper(testGame, testAction);
    builder.setErrorHandler(FINISHED_ERROR_HANDLER);
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(final TestHelper helper) {
        new AddCommandService(helper.injector()).addCommand(Command.newBuilder().build());
      }
    });
    endAsyncTestBlock();
  }

  @Test
  public void testAddCommandFirebaseError() {
    beginAsyncTestBlock();
    final Game testGame = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build();
    final Action testAction = TestUtils.newEmptyAction(GAME_ID).build();
    TestHelper.Builder builder = newTestHelper(testGame, testAction);
    builder.setErrorHandler(FINISHED_ERROR_HANDLER);
    builder.setFirebase(new ErroringFirebase(TestHelper.FIREBASE_URL,
        "games/" + GAME_ID + "/currentAction", "runTransaction"));
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(final TestHelper helper) {
        new AddCommandService(helper.injector()).addCommand(Command.newBuilder().build());
      }
    });
    endAsyncTestBlock();
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
    TestHelper.Builder builder = newTestHelper(testGame, testAction);
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(final TestHelper helper) {
        AddCommandService addCommandService = new AddCommandService(helper.injector());
        final Command.Builder testCommand = Command.newBuilder();

        helper.bus().await(TinMessages.COMMAND_CHANGED, new Subscriber1<IndexCommand>() {
          @Override
          public void onMessage(IndexCommand indexCommand) {
            assertEquals(testCommand.setPlayerNumber(0).build(), indexCommand.getCommand());
            assertEquals(0, indexCommand.getIndex());
            Action expected = testAction.toBuilder()
                .setCommand(0, testCommand.setPlayerNumber(0))
                .build();
            helper.assertCurrentActionEquals(expected, FINISHED);
          }
        });

        addCommandService.setCommand(0, testCommand.build());
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
    TestHelper.Builder builder = newTestHelper(testGame, testAction);
    builder.setErrorHandler(FINISHED_ERROR_HANDLER);
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(final TestHelper helper) {
        new AddCommandService(helper.injector()).setCommand(0, Command.newBuilder().build());
      }
    });
    endAsyncTestBlock();
  }

  @Test
  public void testSetCommandFirebaseError() {
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
        new AddCommandService(helper.injector()).setCommand(0, Command.newBuilder().build());
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
