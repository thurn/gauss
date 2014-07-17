package com.tinlib.shared;

import com.tinlib.generated.Game;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.tinlib.test.ErroringFirebase;
import com.tinlib.test.TestHelper;
import com.tinlib.test.TestUtils;
import com.tinlib.test.TinTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class GameMutatorTest extends TinTestCase {
  private static final String VIEWER_ID = TestUtils.newViewerId();
  private static final String VIEWER_KEY = TestUtils.newViewerKey();
  private static final String GAME_ID = TestUtils.newGameId();

  @Test
  public void testMutateGame() {
    beginAsyncTestBlock(2);
    TestHelper.Builder builder = TestHelper.newBuilder(this);
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    builder.setAnonymousViewer(VIEWER_ID, VIEWER_KEY);
    builder.setGame(TestUtils.newGameWithOnePlayer(GAME_ID).build());
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(TestHelper helper) {
        GameMutator gameMutator = new GameMutator(helper.injector());
        gameMutator.mutateGame(GAME_ID, new GameMutator.GameMutation() {
          @Override
          public void mutate(String viewerId, Game.Builder game) {
            assertEquals(VIEWER_ID, viewerId);
            assertEquals(TestUtils.newGameWithOnePlayer(GAME_ID), game);
            game.setIsGameOver(true);
            game.setCurrentPlayerNumber(507);
            game.addVictor(11);
            finished();
          }

          @Override
          public void onComplete(String viewerId, Game game) {
            assertEquals(VIEWER_ID, viewerId);
            assertTrue(game.isGameOver());
            assertEquals(507, game.getCurrentPlayerNumber());
            assertEquals(11, game.getVictor(0));
            finished();
          }

          @Override
          public void onError(String viewerId, FirebaseError error) {
            fail("Unxpected error.");
          }
        });
      }
    });
    endAsyncTestBlock();
  }

  @Test
  public void testMutateGameError() {
    beginAsyncTestBlock();
    TestHelper.Builder builder = TestHelper.newBuilder(this);
    builder.setFirebase(new ErroringFirebase(TestHelper.FIREBASE_URL));
    builder.setAnonymousViewer(VIEWER_ID, VIEWER_KEY);
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(TestHelper helper) {
        GameMutator gameMutator = new GameMutator(helper.injector());
        gameMutator.mutateGame(GAME_ID, new GameMutator.GameMutation() {
          @Override
          public void mutate(String viewerId, Game.Builder game) {
          }

          @Override
          public void onComplete(String viewerId, Game game) {
            fail("onComplete called when error expected");
          }

          @Override
          public void onError(String viewerId, FirebaseError error) {
            assertEquals(VIEWER_ID, viewerId);
            assertNotNull(error);
            finished();
          }
        });
      }
    });
    endAsyncTestBlock();
  }
}
