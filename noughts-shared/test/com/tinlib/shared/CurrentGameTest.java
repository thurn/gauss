package com.tinlib.shared;

import ca.thurn.noughts.shared.entities.Game;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.tinlib.core.TinMessages;
import com.tinlib.error.ErrorHandler;
import com.tinlib.message.Subscriber1;
import com.tinlib.test.ErroringFirebase;
import com.tinlib.test.TestHelper;
import com.tinlib.test.TestUtils;
import com.tinlib.test.TinTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CurrentGameTest extends TinTestCase {
  private static final String VIEWER_ID = TestUtils.newViewerId();
  private static final String VIEWER_KEY = TestUtils.newViewerKey();

  @Mock
  ErrorHandler mockErrorHandler;

  @Test
  public void testLoadGame() {
    beginAsyncTestBlock(2);
    final Game testGame = TestUtils.newGameWithOnePlayer().build();
    TestHelper.Builder builder = TestHelper.newBuilder(this);
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    builder.setAnonymousViewer(VIEWER_ID, VIEWER_KEY);
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(TestHelper helper) {
        final CurrentGame currentGame = new CurrentGame(helper.injector());
        helper.bus().once(TinMessages.CURRENT_GAME_ID, new Subscriber1<String>() {
          @Override
          public void onMessage(String currentGameId) {
            assertEquals(TestUtils.GAME_ID, currentGameId);
            finished();
          }
        });
        helper.bus().once(TinMessages.CURRENT_GAME, new Subscriber1<Game>() {
          @Override
          public void onMessage(Game currentGame) {
            assertEquals(testGame, currentGame);
            finished();
          }
        });

        helper.references().gameReference(TestUtils.GAME_ID).setValue(testGame.serialize(),
            new Firebase.CompletionListener() {
          @Override
          public void onComplete(FirebaseError firebaseError, Firebase firebase) {
            currentGame.loadGame(TestUtils.GAME_ID);
          }
        });
      }
    });
    endAsyncTestBlock();
  }

  @Test
  public void testLoadGameError() {
    beginAsyncTestBlock();
    TestHelper.Builder builder = TestHelper.newBuilder(this);
    builder.setFirebase(new ErroringFirebase(TestHelper.FIREBASE_URL));
    builder.setErrorHandler(mockErrorHandler);
    builder.setAnonymousViewer(VIEWER_ID, VIEWER_KEY);
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(TestHelper helper) {
        CurrentGame currentGame = new CurrentGame(helper.injector());
        currentGame.loadGame(TestUtils.GAME_ID);
        finished();
      }
    });
    endAsyncTestBlock();

    TestHelper.verifyError(mockErrorHandler);
  }
}
