package com.tinlib.shared;

import ca.thurn.noughts.shared.entities.Game;
import ca.thurn.noughts.shared.entities.ImageString;
import ca.thurn.noughts.shared.entities.Profile;
import com.firebase.client.Firebase;
import com.tinlib.core.TinMessages;
import com.tinlib.message.Subscriber1;
import com.tinlib.test.TestHelper;
import com.tinlib.test.TestUtils;
import com.tinlib.test.TinTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ProfileServiceTest extends TinTestCase {
  private static final String VIEWER_ID = TestUtils.newViewerId();
  private static final String VIEWER_KEY = TestUtils.newViewerKey();
  private static final String GAME_ID = TestUtils.newGameId();

  @Test
  public void testSetProfileForViewer() {
    beginAsyncTestBlock(2);
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
            finished();
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
}
