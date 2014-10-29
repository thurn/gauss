package com.tinlib.services;

import com.firebase.client.Firebase;
import com.tinlib.asynctest.AsyncTestCase;
import com.tinlib.core.TinKeys;
import com.tinlib.convey.Subscriber1;
import com.tinlib.test.TestConfiguration;
import com.tinlib.test.TestHelper;
import com.tinlib.util.Procedure;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ViewerServiceTest extends AsyncTestCase {
  @org.junit.Test
  public void testSetViewerAnonymousId() {
    beginAsyncTestBlock(2);
    TestConfiguration.Builder builder = TestConfiguration.newBuilder();
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    TestHelper.runTest(this, builder.build(), new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
        ViewerService viewerService = helper.injector().get(ViewerService.class);

        helper.bus().once(TinKeys.VIEWER_ID, new Subscriber1<String>() {
          @Override
          public void onMessage(String viewerId) {
            assertEquals("viewerId", viewerId);
            finished();
          }
        });
        helper.bus().once(TinKeys.FIREBASE_REFERENCES, new Subscriber1<FirebaseReferences>() {
          @Override
          public void onMessage(FirebaseReferences references) {
            assertEquals("gameId", references.gameReference("gameId").getName());
            assertEquals("games", references.gameReference("games").getParent().getName());

            assertEquals("games", references.userGames().getName());
            assertEquals("viewerKey", references.userGames().getParent().getName());
            assertEquals("users",
                references.userGames().getParent().getParent().getName());

            assertEquals("submittedAction",
                references.gameSubmittedActionsReference("gameId").getName());
            assertEquals("command",
                references.commandsReferenceForCurrentAction("gameId").getName());
            assertEquals("r123", references.requestReference("123").getName());
            finished();
          }
        });
        viewerService.setViewerAnonymousId("viewerId", "viewerKey");
      }
    });
    endAsyncTestBlock();
  }

  @org.junit.Test
  public void testSetViewerFacebookId() {
    beginAsyncTestBlock(2);
    TestConfiguration.Builder builder = TestConfiguration.newBuilder();
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    TestHelper.runTest(this, builder.build(), new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
        ViewerService viewerService = helper.injector().get(ViewerService.class);
        helper.bus().once(TinKeys.VIEWER_ID, new Subscriber1<String>() {
          @Override
          public void onMessage(String viewerId) {
            assertEquals("facebookId", viewerId);
            finished();
          }
        });
        helper.bus().once(TinKeys.FIREBASE_REFERENCES, new Subscriber1<FirebaseReferences>() {
          @Override
          public void onMessage(FirebaseReferences references) {
            assertEquals("gameId", references.gameReference("gameId").getName());
            assertEquals("games", references.gameReference("games").getParent().getName());

            assertEquals("games", references.userGames().getName());
            assertEquals("facebookId", references.userGames().getParent().getName());
            assertEquals("facebookUsers",
                references.userGames().getParent().getParent().getName());
            finished();
          }
        });
        viewerService.setViewerFacebookId("facebookId");
      }
    });
    endAsyncTestBlock();
  }
}
