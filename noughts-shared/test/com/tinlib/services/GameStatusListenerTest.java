package com.tinlib.services;

import com.firebase.client.Firebase;
import com.tinlib.asynctest.AsyncTestCase;
import com.tinlib.core.TinKeys;
import com.tinlib.generated.Game;
import com.tinlib.generated.GameStatus;
import com.tinlib.convey.Subscriber1;
import com.tinlib.test.*;
import com.tinlib.defer.Procedure;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class GameStatusListenerTest extends AsyncTestCase {
  private static final String VIEWER_ID = TestUtils.newViewerId();
  private static final String VIEWER_KEY = TestUtils.newViewerKey();
  private static final String GAME_ID = TestUtils.newGameId();

  @Test
  public void testGameStatus() {
    beginAsyncTestBlock();
    final Game testGame = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build();
    TestConfiguration.Builder builder = TestConfiguration.newBuilder();
    builder.setFirebase(new Firebase(TestHelperTwo.FIREBASE_URL));
    TestHelper.runTest(this, builder.build(), new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
        helper.bus().once(TinKeys.GAME_STATUS, new Subscriber1<GameStatus>() {
          @Override
          public void onMessage(GameStatus gameStatus) {
            assertEquals(0, gameStatus.getStatusPlayer());
            finished();
          }
        });
        GameStatusListener gameStatusListener = new GameStatusListener(helper.injector());
        helper.bus().produce(TinKeys.CURRENT_GAME, testGame);
      }
    });
    endAsyncTestBlock();
  }
}
