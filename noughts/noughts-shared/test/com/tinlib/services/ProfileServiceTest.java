package com.tinlib.services;

import com.firebase.client.Firebase;
import com.tinlib.asynctest.AsyncTestCase;
import com.tinlib.defer.SuccessHandler;
import com.tinlib.error.ErrorHandler;
import com.tinlib.generated.Game;
import com.tinlib.generated.ImageString;
import com.tinlib.generated.Profile;
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

@RunWith(MockitoJUnitRunner.class)
public class ProfileServiceTest extends AsyncTestCase {
  private static final String VIEWER_ID = TestUtils.newViewerId();
  private static final String VIEWER_KEY = TestUtils.newViewerKey();
  private static final String GAME_ID = TestUtils.newGameId();

  @Mock
  private ErrorHandler mockErrorHandler;

  @Test
  public void testSetProfileForViewer() {
    beginAsyncTestBlock();
    final Game testGame = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build();
    TestConfiguration.Builder builder = TestConfiguration.newBuilder();
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    builder.setAnonymousViewer(VIEWER_ID, VIEWER_KEY);
    builder.setCurrentGame(testGame);
    TestHelper.runTest(this, builder.build(), new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
        final ProfileService profileService = new ProfileService(helper.injector());
        final Profile testProfile = TestUtils.newTestProfile()
            .setImageString(ImageString.newBuilder().setString("foo"))
            .build();
        profileService.setProfileForViewer(testProfile).addSuccessHandler(
            new SuccessHandler<Profile>() {
          @Override
          public void onSuccess(Profile completedProfile) {
            assertEquals(testProfile, completedProfile);
            Game expected = testGame.toBuilder().setProfile(0, testProfile).build();
            helper.assertGameEquals(expected, FINISHED_RUNNABLE);
          }
        });
      }
    });
    endAsyncTestBlock();
  }

  @Test
  public void testSetProfileLocalMultiplayerError() {
    beginAsyncTestBlock();
    final Game testGame = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID)
        .setIsLocalMultiplayer(true)
        .build();
    TestConfiguration.Builder builder = TestConfiguration.newBuilder();
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    builder.setAnonymousViewer(VIEWER_ID, VIEWER_KEY);
    builder.setFailOnError(false);
    builder.multibindInstance(ErrorHandler.class, mockErrorHandler);
    builder.setCurrentGame(testGame);
    builder.setCurrentAction(TestUtils.newEmptyAction(GAME_ID).build());
    TestHelper.runTest(this, builder.build(), new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
        ProfileService profileService = helper.injector().get(ProfileService.class);
        final Profile testProfile = TestUtils.newTestProfile()
            .setImageString(ImageString.newBuilder().setString("foo"))
            .build();
        profileService.setProfileForViewer(testProfile).addFailureHandler(FINISHED_RUNNABLE);
      }
    });
    endAsyncTestBlock();

    TestHelper.verifyErrorHandled(mockErrorHandler);
  }

  @Test
  public void testSetProfileFirebaseError() {
    beginAsyncTestBlock();
    final Game testGame = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build();
    TestConfiguration.Builder builder = TestConfiguration.newBuilder();
    builder.setFirebase(new ErroringFirebase(TestHelper.FIREBASE_URL, "games/" + GAME_ID,
        "runTransaction"));
    builder.setAnonymousViewer(VIEWER_ID, VIEWER_KEY);
    builder.setFailOnError(false);
    builder.multibindInstance(ErrorHandler.class, mockErrorHandler);
    builder.setCurrentGame(testGame);
    builder.setCurrentAction(TestUtils.newEmptyAction(GAME_ID).build());
    TestHelper.runTest(this, builder.build(), new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
        ProfileService profileService = helper.injector().get(ProfileService.class);
        Profile testProfile = TestUtils.newTestProfile()
            .setImageString(ImageString.newBuilder().setString("foo"))
            .build();
        profileService.setProfileForViewer(testProfile).addFailureHandler(FINISHED_RUNNABLE);
      }
    });
    endAsyncTestBlock();

    TestHelper.verifyErrorHandled(mockErrorHandler);
  }
}
