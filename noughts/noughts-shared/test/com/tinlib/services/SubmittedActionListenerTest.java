package com.tinlib.services;

import com.firebase.client.Firebase;
import com.tinlib.asynctest.AsyncTestCase;
import com.tinlib.core.TinKeys;
import com.tinlib.generated.Action;
import com.tinlib.generated.Game;
import com.tinlib.convey.Subscriber1;
import com.tinlib.test.*;
import com.tinlib.util.Procedure;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class SubmittedActionListenerTest extends AsyncTestCase {
  private static final String VIEWER_ID = TestUtils.newViewerId();
  private static final String GAME_ID = TestUtils.newGameId();

  @Test
  public void testGameOver() {
    beginAsyncTestBlock();
    final Game testGame = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build();
    final Action testAction = TestUtils.newEmptyAction(GAME_ID).setIsSubmitted(true).build();
    TestConfiguration.Builder builder = TestConfiguration.newBuilder();
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    TestHelper.runTest(this, builder.build(), new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
        helper.bus().once(TinKeys.ACTION_SUBMITTED, new Subscriber1<Game>() {
          @Override
          public void onMessage(Game game) {
            assertEquals(testGame.toBuilder().addSubmittedAction(testAction).build(), game);
            finished();
          }
        });
        helper.bus().produce(TinKeys.CURRENT_GAME, testGame);
        helper.bus().produce(TinKeys.CURRENT_GAME,
            testGame.toBuilder().addSubmittedAction(testAction).build());
      }
    });
    endAsyncTestBlock();
  }
}
