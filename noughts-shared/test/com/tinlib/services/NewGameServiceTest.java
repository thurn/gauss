package com.tinlib.services;

import com.firebase.client.Firebase;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.tinlib.analytics.AnalyticsHandler;
import com.tinlib.asynctest.AsyncTestCase;
import com.tinlib.core.TinKeys;
import com.tinlib.error.ErrorHandler;
import com.tinlib.generated.Game;
import com.tinlib.generated.Profile;
import com.tinlib.convey.Subscriber1;
import com.tinlib.test.*;
import com.tinlib.time.TimeService;
import com.tinlib.util.Procedure;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NewGameServiceTest extends AsyncTestCase {
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
    TestConfiguration.Builder builder = newTestConfig();
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    TestHelper.runTest(this, builder.build(), new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
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
            helper.assertGameEquals(expected, FINISHED_RUNNABLE);
            if (localMultiplayer) {
              finished();
            } else {
              helper.assertRequestIdEquals(REQUEST_ID, GAME_ID, FINISHED_RUNNABLE);
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

    TestHelper.verifyTrackedEvent(mockAnalyticsHandler);
    verify(mockJoinGameService).joinGame(eq(0), eq(GAME_ID), eq(Optional.<Profile>absent()));
  }

  @Test
  public void testSetGameFirebaseError() {
    runFirebaseErrorTest(new ErroringFirebase(TestHelper.FIREBASE_URL,
        "firebaseio-demo.com/games/" + GAME_ID, "setValue"));
  }

  @Test
  public void testSetRequestIdFirebaseError() {
    runFirebaseErrorTest(new ErroringFirebase(TestHelper.FIREBASE_URL,
        "requests/r" + REQUEST_ID, "setValue"));
  }

  private void runFirebaseErrorTest(Firebase firebase) {
    beginAsyncTestBlock();
    TestConfiguration.Builder builder = newTestConfig();
    builder.setFirebase(firebase);
    builder.setFailOnError(false);
    builder.multibindInstance(ErrorHandler.class,
        TestHelper.finishedErrorHandler(FINISHED_RUNNABLE));
    TestHelper.runTest(this, builder.build(), new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
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

  private TestConfiguration.Builder newTestConfig() {
    TestConfiguration.Builder builder = TestConfiguration.newBuilder();
    builder.setAnonymousViewer(VIEWER_ID, VIEWER_KEY);
    builder.multibindInstance(AnalyticsHandler.class, mockAnalyticsHandler);
    builder.bindInstance(TimeService.class, mockTimeService);
    builder.bindInstance(JoinGameService.class, mockJoinGameService);
    return builder;
  }
}
