package com.tinlib.services;

import com.firebase.client.Firebase;
import com.tinlib.core.TinKeys;
import com.tinlib.convey.Subscriber1;
import com.tinlib.test.TestHelperTwo;
import com.tinlib.test.TinTestCase;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ViewerServiceTest extends TinTestCase {
  @org.junit.Test
  public void testSetViewerAnonymousId() {
    beginAsyncTestBlock(2);
    TestHelperTwo.Builder helper = TestHelperTwo.newBuilder(this);
    helper.setFirebase(new Firebase(TestHelperTwo.FIREBASE_URL));
    helper.runTest(new TestHelperTwo.Test() {
      @Override
      public void run(TestHelperTwo helper) {
        ViewerService viewerService = new ViewerService(helper.injector());

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
    TestHelperTwo.Builder builder = TestHelperTwo.newBuilder(this);
    builder.setFirebase(new Firebase(TestHelperTwo.FIREBASE_URL));
    builder.runTest(new TestHelperTwo.Test() {
      @Override
      public void run(TestHelperTwo helper) {
        ViewerService viewerService = new ViewerService(helper.injector());
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
