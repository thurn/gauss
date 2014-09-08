package com.tinlib.services;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.tinlib.analytics.AnalyticsHandler;
import com.tinlib.core.TinKeys;
import com.tinlib.generated.Action;
import com.tinlib.generated.Game;
import com.tinlib.convey.Subscriber1;
import com.tinlib.test.ErroringFirebase;
import com.tinlib.test.TestHelper;
import com.tinlib.test.TestUtils;
import com.tinlib.test.TinTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

@RunWith(MockitoJUnitRunner.class)
public class ArchiveGameServiceTest extends TinTestCase {
  private static final String VIEWER_ID = TestUtils.newViewerId();
  private static final String VIEWER_KEY = TestUtils.newViewerKey();
  private static final String GAME_ID = TestUtils.newGameId();

  @Mock
  private AnalyticsHandler mockAnalyticsHandler;

  @Test
  public void testArchiveGame() {
    beginAsyncTestBlock();
    final Game testGame = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build();
    final Action testAction = TestUtils.newUnsubmittedActionWithCommand(GAME_ID).build();
    Firebase firebase = new Firebase(TestHelper.FIREBASE_URL);
    TestHelper.Builder builder = newTestHelper(firebase, testGame, testAction);
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(final TestHelper helper) {
        ArchiveGameService archiveGameService = new ArchiveGameService(helper.injector());
        helper.bus().once(TinKeys.ARCHIVE_GAME_COMPLETED, new Subscriber1<String>() {
          @Override
          public void onMessage(String gameId) {
            assertEquals(GAME_ID, gameId);
            helper.references().userReferenceForGame(GAME_ID).addListenerForSingleValueEvent(
                new ValueEventListener() {
              @Override
              public void onDataChange(DataSnapshot dataSnapshot) {
                assertNull(dataSnapshot.getValue());
                finished();
              }

              @Override
              public void onCancelled(FirebaseError firebaseError) {
                fail("listener cancelled");
              }
            });
          }
        });
        archiveGameService.archiveGame(GAME_ID);
      }
    });
    endAsyncTestBlock();

    TestHelper.verifyTrackedEvent(mockAnalyticsHandler);
  }

  @Test
  public void testArchiveGameFirebaseError() {
    beginAsyncTestBlock();
    final Game testGame = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build();
    final Action testAction = TestUtils.newUnsubmittedActionWithCommand(GAME_ID).build();
    Firebase firebase = new ErroringFirebase(TestHelper.FIREBASE_URL, VIEWER_KEY + "/games",
        "removeValue");
    TestHelper.Builder builder = newTestHelper(firebase, testGame, testAction);
    builder.setErrorHandler(FINISHED_ERROR_HANDLER);
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(final TestHelper helper) {
        ArchiveGameService archiveGameService = new ArchiveGameService(helper.injector());
        archiveGameService.archiveGame(GAME_ID);
      }
    });
    endAsyncTestBlock();
  }

  private TestHelper.Builder newTestHelper(Firebase firebase, Game testGame, Action testAction) {
    TestHelper.Builder builder = TestHelper.newBuilder(this);
    builder.setFirebase(firebase);
    builder.setAnonymousViewer(VIEWER_ID, VIEWER_KEY);
    builder.setGame(testGame);
    builder.setCurrentAction(testAction);
    builder.setAnalyticsHandler(mockAnalyticsHandler);
    return builder;
  }

}
