package com.tinlib.action.validator;

import com.tinlib.generated.Command;
import com.tinlib.generated.Game;
import com.firebase.client.Firebase;
import com.tinlib.test.TestHelper;
import com.tinlib.test.TestUtils;
import com.tinlib.test.TinTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class ActionValidatorServiceTest extends TinTestCase {
  private static final String VIEWER_ID = TestUtils.newViewerId();
  private static final String VIEWER_KEY = TestUtils.newViewerKey();
  private static final String GAME_ID = TestUtils.newGameId();

  @Test
  public void testDefaultActionValidatorCanAddCommand() {
    beginAsyncTestBlock();
    TestHelper.Builder builder = TestHelper.newBuilder(this);
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(TestHelper helper) {
        ActionValidatorService validator = new ActionValidatorService(helper.injector());
        Command command = Command.newBuilder().build();
        Game.Builder game = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID);
        assertTrue(validator.canAddCommand(VIEWER_ID, game.build(),
            TestUtils.newEmptyAction(GAME_ID), command));

        game.setIsGameOver(true);
        assertFalse(validator.canAddCommand(VIEWER_ID, game.build(),
            TestUtils.newEmptyAction(game.getId()), command));

        game.setIsGameOver(false);
        game.clearCurrentPlayerNumber();
        assertFalse(validator.canAddCommand(VIEWER_ID, game.build(),
            TestUtils.newEmptyAction(game.getId()), command));

        game.setCurrentPlayerNumber(1);
        assertFalse(validator.canAddCommand(VIEWER_ID, game.build(),
            TestUtils.newEmptyAction(game.getId()), command));

        finished();
      }
    });
    endAsyncTestBlock();
  }

  @Test
  public void testDefaultActionValidatorCanSubmitAction() {
    beginAsyncTestBlock();
    TestHelper.Builder builder = TestHelper.newBuilder(this);
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(TestHelper helper) {
        finished();
      }
    });
    endAsyncTestBlock();
  }
}