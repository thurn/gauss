package com.tinlib.validator;

import com.firebase.client.Firebase;
import com.tinlib.generated.Action;
import com.tinlib.generated.Command;
import com.tinlib.generated.Game;
import com.tinlib.test.TestHelper;
import com.tinlib.test.TestUtils;
import com.tinlib.test.TinTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class JoinGameValidatorServiceTest extends TinTestCase {
  private static final String VIEWER_ID = TestUtils.newViewerId();

  @Test
  public void testDefaultJoinGameValidatorCanJoinGame() {
    beginAsyncTestBlock();
    TestHelper.Builder builder = TestHelper.newBuilder(this);
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(TestHelper helper) {
        JoinGameValidatorService joinGameValidatorService =
            new JoinGameValidatorService(helper.injector());
        assertTrue(joinGameValidatorService.canJoinGame(VIEWER_ID,
            TestUtils.newGameWithOnePlayer("gameid").build()));

        assertFalse(joinGameValidatorService.canJoinGame(VIEWER_ID,
            TestUtils.newGameWithOnePlayer("gameid")
                .setIsGameOver(true)
                .build()));

        assertFalse(joinGameValidatorService.canJoinGame("opponentId",
            TestUtils.newGameWithOnePlayer("gameid").build()));
        finished();
      }
    });
    endAsyncTestBlock();
  }
}