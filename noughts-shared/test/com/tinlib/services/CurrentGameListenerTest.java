package com.tinlib.services;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.tinlib.asynctest.AsyncTestCase;
import com.tinlib.core.TinKeys;
import com.tinlib.error.ErrorHandler;
import com.tinlib.erroringfirebase.ErroringFirebase;
import com.tinlib.generated.Game;
import com.tinlib.convey.Subscriber1;
import com.tinlib.test.*;
import com.tinlib.util.Procedure;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CurrentGameListenerTest extends AsyncTestCase {
  private static final String VIEWER_ID = TestUtils.newViewerId();
  private static final String VIEWER_KEY = TestUtils.newViewerKey();
  private static final String GAME_ID = TestUtils.newGameId();

  @Mock
  ErrorHandler mockErrorHandler;

  @Test
  public void testLoadGame() {
    beginAsyncTestBlock(2);
    final Game testGame = TestUtils.newGameWithOnePlayer(GAME_ID).build();
    TestConfiguration.Builder builder = TestConfiguration.newBuilder();
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    builder.setAnonymousViewer(VIEWER_ID, VIEWER_KEY);
    TestHelper.runTest(this, builder.build(), new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
        final CurrentGameListener currentGameListener =
            helper.injector().get(CurrentGameListener.class);
        helper.bus().once(TinKeys.CURRENT_GAME_ID, new Subscriber1<String>() {
          @Override
          public void onMessage(String currentGameId) {
            assertEquals(GAME_ID, currentGameId);
            finished();
          }
        });
        helper.bus().once(TinKeys.CURRENT_GAME, new Subscriber1<Game>() {
          @Override
          public void onMessage(Game currentGame) {
            assertEquals(testGame, currentGame);
            finished();
          }
        });

        helper.references().gameReference(GAME_ID).setValue(testGame.serialize(),
            new Firebase.CompletionListener() {
          @Override
          public void onComplete(FirebaseError firebaseError, Firebase firebase) {
            currentGameListener.loadGame(GAME_ID);
          }
        });
      }
    });
    endAsyncTestBlock();
  }

  @Test
  public void testLoadGameError() {
    beginAsyncTestBlock();
    TestConfiguration.Builder builder = TestConfiguration.newBuilder();
    builder.setFirebase(new ErroringFirebase(TestHelper.FIREBASE_URL, "games/" + GAME_ID,
        "addValueEventListener"));
    builder.setFailOnError(false);
    builder.multibindInstance(ErrorHandler.class,
        TestHelper.finishedErrorHandler(FINISHED_RUNNABLE));
    builder.setAnonymousViewer(VIEWER_ID, VIEWER_KEY);
    TestHelper.runTest(this, builder.build(), new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
        CurrentGameListener currentGameListener = helper.injector().get(CurrentGameListener.class);
        currentGameListener.loadGame(GAME_ID);
      }
    });
    endAsyncTestBlock();
  }
}
