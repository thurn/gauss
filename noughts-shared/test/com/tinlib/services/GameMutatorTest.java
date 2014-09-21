package com.tinlib.services;

import com.tinlib.asynctest.AsyncTestCase;
import com.tinlib.erroringfirebase.ErroringFirebase;
import com.tinlib.generated.Action;
import com.tinlib.generated.Command;
import com.tinlib.generated.Game;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.tinlib.test.*;
import com.tinlib.util.Procedure;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class GameMutatorTest extends AsyncTestCase {
  private static final String VIEWER_ID = TestUtils.newViewerId();
  private static final String VIEWER_KEY = TestUtils.newViewerKey();
  private static final String GAME_ID = TestUtils.newGameId();

  @Test
  public void testMutateGame() {
    beginAsyncTestBlock();
    final Action testAction = TestUtils.newEmptyAction(GAME_ID).build();
    final Game testGame = TestUtils.newGameWithOnePlayer(GAME_ID).build();
    TestConfiguration.Builder builder = TestConfiguration.newBuilder();
    builder.setAnonymousViewer(VIEWER_ID, VIEWER_KEY);
    builder.setCurrentGame(testGame);
    builder.setCurrentAction(testAction);
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    TestHelper.runTest(this, builder.build(), new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
        GameMutator gameMutator = new GameMutator(helper.injector());
        gameMutator.mutateCurrentGame(new GameMutator.GameMutation() {
          @Override
          public void mutate(String viewerId, Game.Builder game) {
            assertEquals(VIEWER_ID, viewerId);
            assertEquals(testGame, game.build());
            game.setIsGameOver(true);
            game.setCurrentPlayerNumber(507);
            game.addVictor(11);
          }

          @Override
          public void onComplete(String viewerId, FirebaseReferences references,
              Game game) {
            assertEquals(VIEWER_ID, viewerId);
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
    TestConfiguration.Builder builder = TestConfiguration.newBuilder();
    builder.setAnonymousViewer(VIEWER_ID, VIEWER_KEY);
    builder.setCurrentGame(TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build());
    builder.setCurrentAction(TestUtils.newEmptyAction(GAME_ID).build());
    builder.setFirebase(new ErroringFirebase(TestHelper.FIREBASE_URL, "games/" + GAME_ID,
        "runTransaction"));
    TestHelper.runTest(this, builder.build(), new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
        GameMutator gameMutator = new GameMutator(helper.injector());
        gameMutator.mutateCurrentGame(new GameMutator.GameMutation() {
          @Override
          public void mutate(String viewerId, Game.Builder game) {
          }

          @Override
          public void onComplete(String viewerId, FirebaseReferences references,
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

  @Test
  public void testMutateCurrentAction() {
    beginAsyncTestBlock();
    final Action testAction = TestUtils.newEmptyAction(GAME_ID).build();
    final Game testGame = TestUtils.newGameWithOnePlayer(GAME_ID).build();
    final Command testCommand = Command.newBuilder().setPlayerNumber(42).build();
    TestConfiguration.Builder builder = TestConfiguration.newBuilder();
    builder.setAnonymousViewer(VIEWER_ID, VIEWER_KEY);
    builder.setCurrentGame(testGame);
    builder.setCurrentAction(testAction);
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    TestHelper.runTest(this, builder.build(), new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
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
    TestConfiguration.Builder builder = TestConfiguration.newBuilder();
    builder.setAnonymousViewer(VIEWER_ID, VIEWER_KEY);
    builder.setCurrentGame(TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build());
    builder.setCurrentAction(TestUtils.newEmptyAction(GAME_ID).build());
    builder.setFirebase(new ErroringFirebase(TestHelper.FIREBASE_URL,
        "games/" + GAME_ID + "/currentAction", "runTransaction"));
    TestHelper.runTest(this, builder.build(), new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
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
