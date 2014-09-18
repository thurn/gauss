package com.tinlib.services;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.tinlib.analytics.AnalyticsHandler;
import com.tinlib.asynctest.AsyncTestCase;
import com.tinlib.error.ErrorHandler;
import com.tinlib.generated.Action;
import com.tinlib.generated.Game;
import com.tinlib.test.ErroringFirebase;
import com.tinlib.test.TestConfiguration;
import com.tinlib.test.TestHelper;
import com.tinlib.test.TestUtils;
import com.tinlib.util.Procedure;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

@RunWith(MockitoJUnitRunner.class)
public class ArchiveGameServiceTest extends AsyncTestCase {
  private static final String VIEWER_ID = TestUtils.newViewerId();
  private static final String VIEWER_KEY = TestUtils.newViewerKey();
  private static final String GAME_ID = TestUtils.newGameId();

  @Mock
  private AnalyticsHandler mockAnalyticsHandler;
  @Mock
  private ErrorHandler mockErrorHandler;

  @Test
  public void testArchiveGame() {
    beginAsyncTestBlock();
    final Game testGame = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build();
    final Action testAction = TestUtils.newUnsubmittedActionWithCommand(GAME_ID).build();
    Firebase firebase = new Firebase(TestHelper.FIREBASE_URL);
    TestConfiguration config = newTestConfig(firebase, testGame, testAction).build();
    TestHelper.runTest(this, config, new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
        ArchiveGameService archiveGameService = new ArchiveGameService(helper.injector());

        archiveGameService.archiveGame(GAME_ID).addSuccessHandler(new Runnable() {
          @Override
          public void run() {
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
    TestConfiguration.Builder builder = newTestConfig(firebase, testGame, testAction);
    builder.setFailOnError(false);
    builder.multibindInstance(ErrorHandler.class, mockErrorHandler);
    TestHelper.runTest(this, builder.build(), new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
        ArchiveGameService archiveGameService = new ArchiveGameService(helper.injector());
        archiveGameService.archiveGame(GAME_ID).addFailureHandler(FINISHED_RUNNABLE);
      }
    });
    endAsyncTestBlock();
    TestHelper.verifyErrorHandled(mockErrorHandler);
  }

  private TestConfiguration.Builder newTestConfig(Firebase firebase, Game testGame,
      Action testAction) {
    TestConfiguration.Builder builder = TestConfiguration.newBuilder();
    builder.setFirebase(firebase);
    builder.setAnonymousViewer(VIEWER_ID, VIEWER_KEY);
    builder.setCurrentGame(testGame);
    builder.setCurrentAction(testAction);
    builder.multibindInstance(AnalyticsHandler.class, mockAnalyticsHandler);
    return builder;
  }

}
