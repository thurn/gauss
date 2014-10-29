package com.tinlib.validator;

import com.firebase.client.Firebase;
import com.tinlib.asynctest.AsyncTestCase;
import com.tinlib.generated.Action;
import com.tinlib.generated.Command;
import com.tinlib.generated.Game;
import com.tinlib.test.TestConfiguration;
import com.tinlib.test.TestHelper;
import com.tinlib.test.TestUtils;
import com.tinlib.util.Procedure;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class ActionValidatorServiceTest extends AsyncTestCase {
  private static final String VIEWER_ID = TestUtils.newViewerId();
  private static final String VIEWER_KEY = TestUtils.newViewerKey();
  private static final String GAME_ID = TestUtils.newGameId();

  @Test
  public void testDefaultActionValidatorCanAddCommand() {
    beginAsyncTestBlock();
    TestConfiguration.Builder builder = TestConfiguration.newBuilder();
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    TestHelper.runTest(this, builder.build(), new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
        ActionValidatorService validator = helper.injector().get(ActionValidatorService.class);
        Command command = Command.newBuilder().build();
        Game.Builder game = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID);
        assertTrue(validator.canAddCommand(VIEWER_ID, game.build(),
            TestUtils.newEmptyAction(GAME_ID).build(), command));

        game.setIsGameOver(true);
        assertFalse(validator.canAddCommand(VIEWER_ID, game.build(),
            TestUtils.newEmptyAction(game.getId()).build(), command));

        game.setIsGameOver(false);
        game.clearCurrentPlayerNumber();
        assertFalse(validator.canAddCommand(VIEWER_ID, game.build(),
            TestUtils.newEmptyAction(game.getId()).build(), command));

        game.setCurrentPlayerNumber(1);
        assertFalse(validator.canAddCommand(VIEWER_ID, game.build(),
            TestUtils.newEmptyAction(game.getId()).build(), command));

        finished();
      }
    });
    endAsyncTestBlock();
  }

  @Test
  public void testDefaultActionValidatorCanSetCommand() {
    beginAsyncTestBlock();
    TestConfiguration.Builder builder = TestConfiguration.newBuilder();
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    TestHelper.runTest(this, builder.build(), new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
        ActionValidatorService validator = helper.injector().get(ActionValidatorService.class);
        Command command = Command.newBuilder().build();
        Game.Builder game = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID);
        assertFalse(validator.canSetCommand(VIEWER_ID, game.build(),
            TestUtils.newEmptyAction(GAME_ID).build(), command, 0));

        game.setIsGameOver(true);
        assertFalse(validator.canSetCommand(VIEWER_ID, game.build(),
            TestUtils.newUnsubmittedActionWithCommand(GAME_ID).build(), command, 0));

        game.setIsGameOver(false);
        game.clearCurrentPlayerNumber();
        assertFalse(validator.canSetCommand(VIEWER_ID, game.build(),
            TestUtils.newUnsubmittedActionWithCommand(GAME_ID).build(), command, 0));

        game.setCurrentPlayerNumber(1);
        assertFalse(validator.canSetCommand(VIEWER_ID, game.build(),
            TestUtils.newUnsubmittedActionWithCommand(GAME_ID).build(), command, 0));

        game.setCurrentPlayerNumber(0);
        assertFalse(validator.canSetCommand(VIEWER_ID, game.build(),
            TestUtils.newUnsubmittedActionWithCommand(GAME_ID).build(), command, 2));

        game.setCurrentPlayerNumber(0);
        assertFalse(validator.canSetCommand(VIEWER_ID, game.build(),
            TestUtils.newUnsubmittedActionWithCommand(GAME_ID).build(), command, -1));

        game.setCurrentPlayerNumber(0);
        assertTrue(validator.canSetCommand(VIEWER_ID, game.build(),
            TestUtils.newUnsubmittedActionWithCommand(GAME_ID).build(), command, 0));

        finished();
      }
    });
    endAsyncTestBlock();
  }

  @Test
  public void testDefaultActionValidatorCanSubmitAction() {
    beginAsyncTestBlock();
    TestConfiguration.Builder builder = TestConfiguration.newBuilder();
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    TestHelper.runTest(this, builder.build(), new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
        ActionValidatorService validator = helper.injector().get(ActionValidatorService.class);
        Game.Builder game = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID);
        assertFalse(validator.canSubmitAction(VIEWER_ID, game.build(),
            TestUtils.newEmptyAction(GAME_ID).build()));

        Action.Builder currentAction = Action.newBuilder().setGameId(GAME_ID)
            .addCommand(Command.newBuilder());
        assertTrue(validator.canSubmitAction(VIEWER_ID, game.build(),
            currentAction.build()));

        currentAction.setGameId("foo");
        assertFalse(validator.canSubmitAction(VIEWER_ID, game.build(),
            currentAction.build()));
        currentAction.setGameId(GAME_ID);

        currentAction.setIsSubmitted(true);
        assertFalse(validator.canSubmitAction(VIEWER_ID, game.build(),
            currentAction.build()));
        currentAction.setIsSubmitted(false);

        game.setIsGameOver(true);
        assertFalse(validator.canSubmitAction(VIEWER_ID, game.build(),
            currentAction.build()));

        game.setIsGameOver(false);
        game.clearCurrentPlayerNumber();
        assertFalse(validator.canSubmitAction(VIEWER_ID, game.build(),
            currentAction.build()));

        game.setCurrentPlayerNumber(1);
        assertFalse(validator.canSubmitAction(VIEWER_ID, game.build(),
            currentAction.build()));

        finished();
      }
    });
    endAsyncTestBlock();
  }
}