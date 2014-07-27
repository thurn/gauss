package com.tinlib.services;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.tinlib.analytics.AnalyticsHandler;
import com.tinlib.core.TinMessages;
import com.tinlib.generated.Action;
import com.tinlib.generated.Game;
import com.tinlib.message.Subscriber0;
import com.tinlib.message.Subscriber1;
import com.tinlib.test.TestHelper;
import com.tinlib.test.TestUtils;
import com.tinlib.test.TinTestCase;
import com.tinlib.time.TimeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(MockitoJUnitRunner.class)
public class FacebookServiceTest extends TinTestCase {
  private static final String VIEWER_ID = TestUtils.newViewerId();
  private static final String VIEWER_KEY = TestUtils.newViewerKey();
  private static final String GAME_ID = TestUtils.newGameId();
  private static final String FACEBOOK_ID = "FACEBOOK_ID";

  @Mock
  AnalyticsHandler mockAnalyticsHandler;
  @Mock
  TimeService mockTimeService;

  @Test
  public void testUpgradeAccountToFacebook() {
    beginAsyncTestBlock(3);
    final Game testGame = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build();
    final Action testAction = TestUtils.newUnsubmittedActionWithCommand(GAME_ID).build();
    final Firebase firebase = new Firebase(TestHelper.FIREBASE_URL);
    TestHelper.Builder builder = TestHelper.newBuilder(this);
    builder.setAnalyticsHandler(mockAnalyticsHandler);
    builder.setAnonymousViewer(VIEWER_ID, VIEWER_KEY);
    builder.setGame(testGame);
    builder.setCurrentAction(testAction);
    builder.setFirebase(firebase);
    builder.setTimeService(mockTimeService);
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(final TestHelper helper) {
        GameListService gameListService = new GameListService(helper.injector());
        helper.bus().once(TinMessages.GAME_LIST_ADD, new Subscriber0() {
          @Override
          public void onMessage() {
            FacebookService facebookService = new FacebookService(helper.injector());
            helper.bus().once(TinMessages.ACCOUNT_UPGRADED_TO_FACEBOOK, new Subscriber0() {
              @Override
              public void onMessage() {
                FirebaseReferences facebookReferences =
                    FirebaseReferences.facebook(FACEBOOK_ID, firebase);
                Game expectedGame = testGame.toBuilder().setPlayer(0, FACEBOOK_ID).build();
                helper.assertGameEquals(expectedGame, FINISHED);

                facebookReferences.currentActionReferenceForGame(GAME_ID)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                  @Override
                  public void onDataChange(DataSnapshot dataSnapshot) {
                    assertEquals(testAction,
                        Action.newDeserializer().fromDataSnapshot(dataSnapshot));
                    finished();
                  }

                  @Override
                  public void onCancelled(FirebaseError firebaseError) {
                    fail("Current action listener cancelled.");
                  }
                });

                helper.bus().once(TinMessages.VIEWER_ID, new Subscriber1<String>() {
                  @Override
                  public void onMessage(String viewerId) {
                    assertEquals(FACEBOOK_ID, viewerId);
                    finished();
                  }
                });
              }
            });
            facebookService.upgradeAccountToFacebook(FACEBOOK_ID);
          }
        });
      }
    });
    endAsyncTestBlock();

    TestHelper.verifyTrackedEvent(mockAnalyticsHandler);
  }
}
