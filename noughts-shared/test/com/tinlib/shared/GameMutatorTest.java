package com.tinlib.shared;

import com.tinlib.generated.Action;
import com.tinlib.generated.Command;
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
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class GameMutatorTest extends TinTestCase {
  private static final String VIEWER_ID = TestUtils.newViewerId();
  private static final String VIEWER_KEY = TestUtils.newViewerKey();
  private static final String GAME_ID = TestUtils.newGameId();

  @Test
  public void testMutateGame() {
    beginAsyncTestBlock();
    final Action testAction = TestUtils.newEmptyAction(GAME_ID).build();
    final Game testGame = TestUtils.newGameWithOnePlayer(GAME_ID).build();
    TestHelper.Builder builder = TestHelper.newBuilder(this);
    builder.setAnonymousViewer(VIEWER_ID, VIEWER_KEY);
    builder.setGame(testGame);
    builder.setCurrentAction(testAction);
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(TestHelper helper) {
        GameMutator gameMutator = new GameMutator(helper.injector());
        gameMutator.mutateCurrentGame(new GameMutator.GameMutation() {
          @Override
          public void mutate(String viewerId, Action currentAction, Game.Builder game) {
            assertEquals(VIEWER_ID, viewerId);
            assertEquals(testGame, game.build());
            assertEquals(testAction, currentAction);
            game.setIsGameOver(true);
            game.setCurrentPlayerNumber(507);
            game.addVictor(11);
          }

          @Override
          public void onComplete(String viewerId, FirebaseReferences references,
              Action currentAction, Game game) {
            assertEquals(VIEWER_ID, viewerId);
            assertEquals(testAction, currentAction);
            assertTrue(game.getIsGameOver());
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
    builder.setAnonymousViewer(VIEWER_ID, VIEWER_KEY);
    builder.setGame(TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build());
    builder.setCurrentAction(TestUtils.newEmptyAction(GAME_ID).build());
    builder.setFirebase(new ErroringFirebase(TestHelper.FIREBASE_URL, "games/" + GAME_ID,
        "runTransaction"));
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(TestHelper helper) {
        GameMutator gameMutator = new GameMutator(helper.injector());
        gameMutator.mutateCurrentGame(new GameMutator.GameMutation() {
          @Override
          public void mutate(String viewerId, Action currentAction, Game.Builder game) {
          }

          @Override
          public void onComplete(String viewerId, FirebaseReferences references,
              Action currentAction, Game game) {
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

  @Test
  public void testMutateCurrentAction() {
    beginAsyncTestBlock();
    final Action testAction = TestUtils.newEmptyAction(GAME_ID).build();
    final Game testGame = TestUtils.newGameWithOnePlayer(GAME_ID).build();
    final Command testCommand = Command.newBuilder().setPlayerNumber(42).build();
    TestHelper.Builder builder = TestHelper.newBuilder(this);
    builder.setAnonymousViewer(VIEWER_ID, VIEWER_KEY);
    builder.setGame(testGame);
    builder.setCurrentAction(testAction);
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(TestHelper helper) {
        GameMutator gameMutator = new GameMutator(helper.injector());
        gameMutator.mutateCurrentAction(new GameMutator.ActionMutation() {
          @Override
          public void mutate(String viewerId, Action.Builder action, Game game) {
            assertEquals(VIEWER_ID, viewerId);
            assertEquals(testAction, action.build());
            assertEquals(testGame, game);
            action.addCommand(testCommand);
          }

          @Override
          public void onComplete(String viewerId, FirebaseReferences references, Action action,
              Game game) {
            assertEquals(VIEWER_ID, viewerId);
            assertEquals(testAction.toBuilder().addCommand(testCommand).build(), action);
            assertEquals(testGame, game);
            finished();
          }

          @Override
          public void onError(String viewerId, FirebaseError error) {
            fail("Unexpected error mutating current action");
          }
        });
      }
    });
    endAsyncTestBlock();
  }

  @Test
  public void testMutateCurrentActionError() {
    beginAsyncTestBlock();
    TestHelper.Builder builder = TestHelper.newBuilder(this);
    builder.setAnonymousViewer(VIEWER_ID, VIEWER_KEY);
    builder.setGame(TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build());
    builder.setCurrentAction(TestUtils.newEmptyAction(GAME_ID).build());
    builder.setFirebase(new ErroringFirebase(TestHelper.FIREBASE_URL,
        "games/" + GAME_ID + "/currentAction", "runTransaction"));
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(TestHelper helper) {
        GameMutator gameMutator = new GameMutator(helper.injector());
        gameMutator.mutateCurrentAction(new GameMutator.ActionMutation() {
          @Override
          public void mutate(String viewerId, Action.Builder action, Game game) {
          }

          @Override
          public void onComplete(String viewerId, FirebaseReferences references, Action action,
              Game game) {
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
