package com.tinlib.services;

import com.firebase.client.Firebase;
import com.tinlib.core.TinMessages;
import com.tinlib.generated.Game;
import com.tinlib.generated.GameStatus;
import com.tinlib.message.Subscriber1;
import com.tinlib.test.TestHelper;
import com.tinlib.test.TestUtils;
import com.tinlib.test.TinTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class GameStatusServiceTest extends TinTestCase {
  private static final String VIEWER_ID = TestUtils.newViewerId();
  private static final String VIEWER_KEY = TestUtils.newViewerKey();
  private static final String GAME_ID = TestUtils.newGameId();

  @Test
  public void testGameStatus() {
    beginAsyncTestBlock();
    final Game testGame = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build();
    TestHelper.Builder builder = TestHelper.newBuilder(this);
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(TestHelper helper) {
        helper.bus().once(TinMessages.GAME_STATUS, new Subscriber1<GameStatus>() {
          @Override
          public void onMessage(GameStatus gameStatus) {
            assertEquals(0, gameStatus.getStatusPlayer());
            finished();
          }
        });
        GameStatusService gameStatusService = new GameStatusService(helper.injector());
        helper.bus().produce(TinMessages.CURRENT_GAME, testGame);
      }
    });
    endAsyncTestBlock();
  }
}
