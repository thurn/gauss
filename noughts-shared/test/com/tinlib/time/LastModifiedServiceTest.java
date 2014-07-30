package com.tinlib.time;

import com.firebase.client.Firebase;
import com.tinlib.core.TinMessages;
import com.tinlib.generated.Game;
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
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LastModifiedServiceTest extends TinTestCase {
  private static final String VIEWER_ID = TestUtils.newViewerId();
  private static final String VIEWER_KEY = TestUtils.newViewerKey();
  private static final String GAME_ID = TestUtils.newGameId();

  @Mock
  private TimeService mockTimeService;

  @Test
  public void testUpdateLastModified() {
    beginAsyncTestBlock(2);
    final Game testGame = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build();
    TestHelper.Builder builder = TestHelper.newBuilder(this);
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    builder.setAnonymousViewer(VIEWER_ID, VIEWER_KEY);
    builder.setTimeService(mockTimeService);
    builder.setGame(testGame);
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(final TestHelper helper) {
        LastModifiedService lastModifiedService = new LastModifiedService(helper.injector());
        when(mockTimeService.currentTimeMillis()).thenReturn(789L);
        helper.bus().await(TinMessages.CURRENT_GAME, new Subscriber1<Game>() {
          @Override
          public void onMessage(Game currentGame) {
            System.out.println("message " + currentGame);
            //assertEquals(789L, currentGame.getLastModified());
            finished();
          }
        });
        lastModifiedService.updateLastModified(GAME_ID);
      }
    });
    endAsyncTestBlock();
  }

  @Test
  public void testUpdateLastModifiedFirebaseError() {
    beginAsyncTestBlock();
    final Game testGame = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build();
    TestHelper.Builder builder = TestHelper.newBuilder(this);
    builder.setAnonymousViewer(VIEWER_ID, VIEWER_KEY);
    builder.setTimeService(mockTimeService);
    builder.setGame(testGame);
    builder.setErrorHandler(FINISHED_ERROR_HANDLER);
    builder.setFirebase(new ErroringFirebase(TestHelper.FIREBASE_URL, "games/" + GAME_ID,
        "runTransaction"));
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(final TestHelper helper) {
        LastModifiedService lastModifiedService = new LastModifiedService(helper.injector());
        lastModifiedService.updateLastModified(GAME_ID);
      }
    });
    endAsyncTestBlock();
  }
}