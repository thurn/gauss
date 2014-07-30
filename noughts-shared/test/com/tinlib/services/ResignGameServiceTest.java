package com.tinlib.services;

import com.firebase.client.Firebase;
import com.tinlib.analytics.AnalyticsHandler;
import com.tinlib.generated.Action;
import com.tinlib.generated.Game;
import com.tinlib.test.TestHelper;
import com.tinlib.test.TestUtils;
import com.tinlib.test.TinTestCase;
import com.tinlib.time.TimeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ResignGameServiceTest extends TinTestCase {
  private static final String VIEWER_ID = TestUtils.newViewerId();
  private static final String VIEWER_KEY = TestUtils.newViewerKey();
  private static final String GAME_ID = TestUtils.newGameId();

  @Mock
  private AnalyticsHandler mockAnalyticsHandler;
  @Mock
  private TimeService mockTimeService;

  @Test
  public void testResignGame() {
    final Game testGame = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build();
    final Action testAction = TestUtils.newUnsubmittedActionWithCommand(GAME_ID).build();
    Firebase firebase = new Firebase(TestHelper.FIREBASE_URL);
    TestHelper.Builder builder = newTestHelper(firebase, testGame, testAction);
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(TestHelper helper) {
      }
    });
  }

  private TestHelper.Builder newTestHelper(Firebase firebase, Game testGame, Action testAction) {
    TestHelper.Builder builder = TestHelper.newBuilder(this);
    builder.setFirebase(firebase);
    builder.setAnonymousViewer(VIEWER_ID, VIEWER_KEY);
    builder.setGame(testGame);
    builder.setCurrentAction(testAction);
    builder.setAnalyticsHandler(mockAnalyticsHandler);
    builder.setTimeService(mockTimeService);
    return builder;
  }
}
