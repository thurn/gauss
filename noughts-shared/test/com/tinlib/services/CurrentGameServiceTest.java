package com.tinlib.services;

import com.tinlib.generated.Game;
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
public class CurrentGameServiceTest extends TinTestCase {
  private static final String VIEWER_ID = TestUtils.newViewerId();
  private static final String VIEWER_KEY = TestUtils.newViewerKey();
  private static final String GAME_ID = TestUtils.newGameId();

  @Mock
  ErrorHandler mockErrorHandler;

  @Test
  public void testLoadGame() {
    beginAsyncTestBlock(2);
    final Game testGame = TestUtils.newGameWithOnePlayer(GAME_ID).build();
    TestHelper.Builder builder = TestHelper.newBuilder(this);
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    builder.setAnonymousViewer(VIEWER_ID, VIEWER_KEY);
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(TestHelper helper) {
        final CurrentGameService currentGameService = new CurrentGameService(helper.injector());
        helper.bus().once(TinMessages.CURRENT_GAME_ID, new Subscriber1<String>() {
          @Override
          public void onMessage(String currentGameId) {
            assertEquals(GAME_ID, currentGameId);
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

        helper.references().gameReference(GAME_ID).setValue(testGame.serialize(),
            new Firebase.CompletionListener() {
          @Override
          public void onComplete(FirebaseError firebaseError, Firebase firebase) {
            currentGameService.loadGame(GAME_ID);
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
    builder.setFirebase(new ErroringFirebase(TestHelper.FIREBASE_URL, "games/" + GAME_ID,
        "addValueEventListener"));
    builder.setErrorHandler(new ErrorHandler() {
      @Override
      public void error(String message, Object[] args) {
        finished();
      }
    });
    builder.setAnonymousViewer(VIEWER_ID, VIEWER_KEY);
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(TestHelper helper) {
        CurrentGameService currentGameService = new CurrentGameService(helper.injector());
        currentGameService.loadGame(GAME_ID);
      }
    });
    endAsyncTestBlock();
  }
}
