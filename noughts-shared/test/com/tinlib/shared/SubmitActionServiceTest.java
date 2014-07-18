package com.tinlib.shared;

import com.firebase.client.Firebase;
import com.google.common.base.Optional;
import com.tinlib.analytics.AnalyticsHandler;
import com.tinlib.core.TinMessages;
import com.tinlib.generated.Action;
import com.tinlib.generated.Game;
import com.tinlib.message.Subscriber0;
import com.tinlib.push.PushNotificationHandler;
import com.tinlib.test.TestHelper;
import com.tinlib.test.TestUtils;
import com.tinlib.test.TinTestCase;
import com.tinlib.time.TimeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class SubmitActionServiceTest extends TinTestCase {
  private static final String VIEWER_ID = TestUtils.newViewerId();
  private static final String VIEWER_KEY = TestUtils.newViewerKey();
  private static final String GAME_ID = TestUtils.newGameId();

  @Mock
  private AnalyticsHandler mockAnalyticsHandler;
  @Mock
  private PushNotificationHandler mockPushNotificationHandler;
  @Mock
  private GameOverService mockGameOverService;
  @Mock
  private NextPlayerService mockNextPlayerService;
  @Mock
  private TimeService mockTimeService;

  @Test
  public void testSubmitActionSuccess() {
    beginAsyncTestBlock(2);
    final Game testGame = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build();
    final Action testAction = TestUtils.newUnsubmittedActionWithCommand(GAME_ID)
        .build();
    TestHelper.Builder builder = TestHelper.newBuilder(this);
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    builder.setAnonymousViewer(VIEWER_ID, VIEWER_KEY);
    builder.setGame(testGame);
    builder.setCurrentAction(testAction);
    builder.setAnalyticsHandler(mockAnalyticsHandler);
    builder.setPushNotificationHandler(mockPushNotificationHandler);
    builder.setGameOverService(mockGameOverService);
    builder.setNextPlayerService(mockNextPlayerService);
    builder.setTimeService(mockTimeService);
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(final TestHelper helper) {
        SubmitActionService submitService = new SubmitActionService(helper.injector());

        final Game expectedGame = testGame.toBuilder()
            .addSubmittedAction(testAction.toBuilder().setIsSubmitted(true))
            .setCurrentPlayerNumber(1)
            .build();
        final Action expectedAction = TestUtils.newEmptyAction(GAME_ID).build();
        helper.bus().once(TinMessages.ACTION_SUBMITTED, new Subscriber0() {
          @Override
          public void onMessage() {
          helper.assertGameEquals(expectedGame, new Runnable() {
            @Override
            public void run() {
              finished();
            }
          });
          helper.assertCurrentActionEquals(expectedAction, new Runnable() {
            @Override
            public void run() {
              finished();
            }
          });
          }
        });

        when(mockTimeService.currentTimeMillis()).thenReturn(123L);
        when(mockGameOverService.computeVictors(eq(testGame), eq(testAction)))
            .thenReturn(Optional.<List<Integer>>absent());
        when(mockNextPlayerService.nextPlayerNumber(eq(testGame), eq(testAction)))
            .thenReturn(1);

        submitService.submitCurrentAction();
      }
    });
    endAsyncTestBlock();

    TestHelper.verifyTrackedEvent(mockAnalyticsHandler);
    TestHelper.verifyPushSent(mockPushNotificationHandler, GAME_ID, 1, "It's your turn");
  }
}
