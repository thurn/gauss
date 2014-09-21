package com.tinlib.time;

import com.firebase.client.Firebase;
import com.tinlib.asynctest.AsyncTestCase;
import com.tinlib.convey.Subscriber1;
import com.tinlib.core.TinKeys;
import com.tinlib.error.ErrorHandler;
import com.tinlib.generated.Game;
import com.tinlib.erroringfirebase.ErroringFirebase;
import com.tinlib.test.TestConfiguration;
import com.tinlib.test.TestHelper;
import com.tinlib.test.TestUtils;
import com.tinlib.util.Procedure;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LastModifiedServiceTest extends AsyncTestCase {
  private static final String VIEWER_ID = TestUtils.newViewerId();
  private static final String VIEWER_KEY = TestUtils.newViewerKey();
  private static final String GAME_ID = TestUtils.newGameId();

  @Mock
  private TimeService mockTimeService;

  @Test
  public void testUpdateLastModified() {
    beginAsyncTestBlock();
    final Game testGame = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build();
    TestConfiguration.Builder builder = TestConfiguration.newBuilder();
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    builder.setAnonymousViewer(VIEWER_ID, VIEWER_KEY);
    builder.bindInstance(TimeService.class, mockTimeService);
    builder.setCurrentGame(testGame);
    TestHelper.runTest(this, builder.build(), new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
        LastModifiedService lastModifiedService = new LastModifiedService(helper.injector());
        when(mockTimeService.currentTimeMillis()).thenReturn(789L);
        helper.bus().once(TinKeys.CURRENT_GAME, new Subscriber1<Game>() {
          @Override
          public void onMessage(Game currentGame) {
            assertEquals(789L, currentGame.getLastModified());
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
    TestConfiguration.Builder builder = TestConfiguration.newBuilder();
    builder.setAnonymousViewer(VIEWER_ID, VIEWER_KEY);
    builder.bindInstance(TimeService.class, mockTimeService);
    builder.setCurrentGame(testGame);
    builder.setFailOnError(false);
    builder.multibindInstance(ErrorHandler.class,
        TestHelper.finishedErrorHandler(FINISHED_RUNNABLE));
    builder.setFirebase(new ErroringFirebase(TestHelper.FIREBASE_URL, "games/" + GAME_ID,
        "runTransaction"));
    TestHelper.runTest(this, builder.build(), new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
        LastModifiedService lastModifiedService = new LastModifiedService(helper.injector());
        lastModifiedService.updateLastModified(GAME_ID);
      }
    });
    endAsyncTestBlock();
  }
}
