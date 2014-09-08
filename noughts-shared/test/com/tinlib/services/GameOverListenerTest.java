package com.tinlib.services;

import com.firebase.client.Firebase;
import com.tinlib.core.TinKeys;
import com.tinlib.generated.Game;
import com.tinlib.convey.Subscriber1;
import com.tinlib.test.TestHelper;
import com.tinlib.test.TestUtils;
import com.tinlib.test.TinTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class GameOverListenerTest extends TinTestCase {
  private static final String VIEWER_ID = TestUtils.newViewerId();
  private static final String GAME_ID = TestUtils.newGameId();

  @Test
  public void testGameOver() {
    beginAsyncTestBlock();
    final Game.Builder testGame = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID);
    TestHelper.Builder builder = TestHelper.newBuilder(this);
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(TestHelper helper) {
        helper.bus().once(TinKeys.GAME_OVER, new Subscriber1<Game>() {
          @Override
          public void onMessage(Game game) {
            assertEquals(testGame.setIsGameOver(true).build(), game);
            finished();
          }
        });
        helper.bus().produce(TinKeys.CURRENT_GAME, testGame.build());
        helper.bus().produce(TinKeys.CURRENT_GAME, testGame.setIsGameOver(true).build());
      }
    });
    endAsyncTestBlock();
  }
}
