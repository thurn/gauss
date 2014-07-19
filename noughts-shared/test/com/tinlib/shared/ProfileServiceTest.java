package com.tinlib.shared;

import com.tinlib.analytics.AnalyticsHandler;
import com.tinlib.error.TinException;
import com.tinlib.generated.Game;
import com.tinlib.generated.ImageString;
import com.tinlib.generated.Profile;
import com.firebase.client.Firebase;
import com.tinlib.core.TinMessages;
import com.tinlib.error.ErrorHandler;
import com.tinlib.message.Subscriber1;
import com.tinlib.test.ErroringFirebase;
import com.tinlib.test.TestHelper;
import com.tinlib.test.TestUtils;
import com.tinlib.test.TinTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ProfileServiceTest extends TinTestCase {
  private static final String VIEWER_ID = TestUtils.newViewerId();
  private static final String VIEWER_KEY = TestUtils.newViewerKey();
  private static final String GAME_ID = TestUtils.newGameId();

  @Test
  public void testSetProfileForViewer() {
    beginAsyncTestBlock();
    final Game testGame = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build();
    TestHelper.Builder builder = TestHelper.newBuilder(this);
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    builder.setAnonymousViewer(VIEWER_ID, VIEWER_KEY);
    builder.setGame(testGame);
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(TestHelper helper) {
        final ProfileService profileService = new ProfileService(helper.injector());
        final Profile testProfile = TestUtils.newTestProfile()
            .setImageString(ImageString.newBuilder().setString("foo"))
            .build();
        helper.bus().once(TinMessages.PROFILE_REQUIRED, new Subscriber1<Profile>() {
          @Override
          public void onMessage(Profile profile) {
            assertEquals(testGame.getProfile(0), profile);
            profileService.setProfileForViewer(GAME_ID, testProfile);
          }
        });
        helper.bus().once(TinMessages.COMPLETED_VIEWER_PROFILE, new Subscriber1<Profile>() {
          @Override
          public void onMessage(Profile completedViewerProfile) {
            assertEquals(testProfile, completedViewerProfile);
            finished();
          }
        });
      }
    });
    endAsyncTestBlock();
  }

  public void testSetProfileLocalMultiplayerError() {
    beginAsyncTestBlock();
    final Game testGame = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID)
        .setIsLocalMultiplayer(true)
        .build();
    TestHelper.Builder builder = TestHelper.newBuilder(this);
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    builder.setAnonymousViewer(VIEWER_ID, VIEWER_KEY);
    builder.setErrorHandler(new ErrorHandler() {
      @Override
      public void error(String message, Object[] args) {
        finished();
      }
    });
    builder.setGame(testGame);
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(TestHelper helper) {
        ProfileService profileService = new ProfileService(helper.injector());
        final Profile testProfile = TestUtils.newTestProfile()
            .setImageString(ImageString.newBuilder().setString("foo"))
            .build();
        profileService.setProfileForViewer(GAME_ID, testProfile);
      }
    });
    endAsyncTestBlock();
  }

  @Test
  public void testSetProfileFirebaseError() {
    beginAsyncTestBlock();
    final Game testGame = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build();
    TestHelper.Builder builder = TestHelper.newBuilder(this);
    builder.setFirebase(new ErroringFirebase(TestHelper.FIREBASE_URL, "games/" + GAME_ID,
        "runTransaction"));
    builder.setAnonymousViewer(VIEWER_ID, VIEWER_KEY);
    builder.setErrorHandler(new ErrorHandler() {
      @Override
      public void error(String message, Object[] args) {
        finished();
      }
    });
    builder.setGame(testGame);
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(TestHelper helper) {
        ProfileService profileService = new ProfileService(helper.injector());
        final Profile testProfile = TestUtils.newTestProfile()
            .setImageString(ImageString.newBuilder().setString("foo"))
            .build();
        profileService.setProfileForViewer(GAME_ID, testProfile);
      }
    });
    endAsyncTestBlock();
  }
}
