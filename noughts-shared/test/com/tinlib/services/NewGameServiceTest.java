package com.tinlib.services;

import com.firebase.client.Firebase;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.tinlib.analytics.AnalyticsHandler;
import com.tinlib.core.TinKeys;
import com.tinlib.generated.Game;
import com.tinlib.generated.Profile;
import com.tinlib.convey.Subscriber1;
import com.tinlib.test.ErroringFirebase;
import com.tinlib.test.TestHelperTwo;
import com.tinlib.test.TestUtils;
import com.tinlib.test.TinTestCase;
import com.tinlib.time.TimeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NewGameServiceTest extends TinTestCase {
  private static final String VIEWER_ID = TestUtils.newViewerId();
  private static final String VIEWER_KEY = TestUtils.newViewerKey();
  private static final String GAME_ID = TestUtils.newGameId();
  private static final Profile VIEWER_PROFILE = Profile.newBuilder().setName("Viewer").build();
  private static final String REQUEST_ID = "requestid";

  @Mock
  private AnalyticsHandler mockAnalyticsHandler;
  @Mock
  private TimeService mockTimeService;
  @Mock
  private JoinGameService mockJoinGameService;

  @Test
  public void testNewGame() {
    runNewGameTest(false /* localMultiplayer */);
  }

  @Test
  public void testNewLocalMultiplayerGame() {
    runNewGameTest(true /* localMultiplayer */);
  }

  private void runNewGameTest(final boolean localMultiplayer) {
    beginAsyncTestBlock(2);
    TestHelperTwo.Builder builder = newTestConfig();
    builder.setFirebase(new Firebase(TestHelperTwo.FIREBASE_URL));
    builder.runTest(new TestHelperTwo.Test() {
      @Override
      public void run(final TestHelperTwo helper) {
        NewGameService newGameService = new NewGameService(helper.injector());

        helper.bus().await(TinKeys.CREATE_GAME_COMPLETED, new Subscriber1<Game>() {
          @Override
          public void onMessage(Game game) {
            final Game expected = Game.newBuilder()
                .setId(GAME_ID)
                .addPlayer(VIEWER_ID)
                .addProfile(VIEWER_PROFILE)
                .setIsLocalMultiplayer(localMultiplayer)
                .setCurrentPlayerNumber(0)
                .setLastModified(444L)
                .setIsGameOver(false)
                .build();
            assertEquals(expected, game);
            helper.assertGameEquals(expected, FINISHED);
            if (localMultiplayer) {
              finished();
            } else {
              helper.assertRequestIdEquals(REQUEST_ID, GAME_ID, FINISHED);
            }
          }
        });

        when(mockTimeService.currentTimeMillis()).thenReturn(444L);

        if (localMultiplayer) {
          newGameService.newLocalMultiplayerGameBuilder(GAME_ID)
              .addPlayers(ImmutableList.of(VIEWER_ID))
              .addProfiles(ImmutableList.of(VIEWER_PROFILE))
              .setViewerPlayerNumber(0)
              .create();
        } else {
          newGameService.newGameBuilder(GAME_ID)
              .addPlayers(ImmutableList.of(VIEWER_ID))
              .addProfiles(ImmutableList.of(VIEWER_PROFILE))
              .setViewerPlayerNumber(0)
              .setRequestId(REQUEST_ID)
              .create();
        }
      }
    });
    endAsyncTestBlock();

    TestHelperTwo.verifyTrackedEvent(mockAnalyticsHandler);
    verify(mockJoinGameService).joinGame(eq(0), eq(GAME_ID), eq(Optional.<Profile>absent()));
  }

  @Test
  public void testSetGameFirebaseError() {
    runFirebaseErrorTest(new ErroringFirebase(TestHelperTwo.FIREBASE_URL,
        "firebaseio-demo.com/games/" + GAME_ID, "setValue"));
  }

  @Test
  public void testSetRequestIdFirebaseError() {
    runFirebaseErrorTest(new ErroringFirebase(TestHelperTwo.FIREBASE_URL,
        "requests/r" + REQUEST_ID, "setValue"));
  }

  private void runFirebaseErrorTest(Firebase firebase) {
    beginAsyncTestBlock();
    TestHelperTwo.Builder builder = newTestConfig();
    builder.setFirebase(firebase);
    builder.setErrorHandler(FINISHED_ERROR_HANDLER);
    builder.runTest(new TestHelperTwo.Test() {
      @Override
      public void run(final TestHelperTwo helper) {
        NewGameService newGameService = new NewGameService(helper.injector());
        when(mockTimeService.currentTimeMillis()).thenReturn(444L);
        newGameService.newGameBuilder(GAME_ID)
            .addPlayers(ImmutableList.of(VIEWER_ID))
            .addProfiles(ImmutableList.of(VIEWER_PROFILE))
            .setViewerPlayerNumber(0)
            .setRequestId(REQUEST_ID)
            .create();
      }
    });
    endAsyncTestBlock();
  }

  private TestHelperTwo.Builder newTestConfig() {
    TestHelperTwo.Builder builder = TestHelperTwo.newBuilder(this);
    builder.setAnonymousViewer(VIEWER_ID, VIEWER_KEY);
    builder.setAnalyticsHandler(mockAnalyticsHandler);
    builder.setTimeService(mockTimeService);
    builder.setJoinGameService(mockJoinGameService);
    return builder;
  }
}
