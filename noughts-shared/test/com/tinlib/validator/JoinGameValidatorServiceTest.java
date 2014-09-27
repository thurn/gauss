package com.tinlib.validator;

import com.firebase.client.Firebase;
import com.tinlib.asynctest.AsyncTestCase;
import com.tinlib.test.*;
import com.tinlib.util.Procedure;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class JoinGameValidatorServiceTest extends AsyncTestCase {
  private static final String VIEWER_ID = TestUtils.newViewerId();

  @Test
  public void testDefaultJoinGameValidatorCanJoinGame() {
    beginAsyncTestBlock();
    TestConfiguration.Builder builder = TestConfiguration.newBuilder();
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    TestHelper.runTest(this, builder.build(), new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
        JoinGameValidatorService joinGameValidatorService =
            helper.injector().get(JoinGameValidatorService.class);
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