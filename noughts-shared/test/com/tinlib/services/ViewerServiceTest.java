package com.tinlib.services;

import com.firebase.client.Firebase;
import com.tinlib.core.TinMessages2;
import com.tinlib.message.Subscriber1;
import com.tinlib.test.TestHelper;
import com.tinlib.test.TinTestCase;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ViewerServiceTest extends TinTestCase {
  @org.junit.Test
  public void testSetViewerAnonymousId() {
    beginAsyncTestBlock(2);
    TestHelper.Builder helper = TestHelper.newBuilder(this);
    helper.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    helper.runTest(new TestHelper.Test() {
      @Override
      public void run(TestHelper helper) {
        ViewerService viewerService = new ViewerService(helper.injector());

        helper.bus2().once(new Subscriber1<String>() {
          @Override
          public void onMessage(String viewerId) {
            assertEquals("viewerId", viewerId);
            finished();
          }
        }, TinMessages2.VIEWER_ID);
        helper.bus2().once(new Subscriber1<FirebaseReferences>() {
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
        }, TinMessages2.FIREBASE_REFERENCES);
        viewerService.setViewerAnonymousId("viewerId", "viewerKey");
      }
    });
    endAsyncTestBlock();
  }

  @org.junit.Test
  public void testSetViewerFacebookId() {
    beginAsyncTestBlock(2);
    TestHelper.Builder builder = TestHelper.newBuilder(this);
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(TestHelper helper) {
        ViewerService viewerService = new ViewerService(helper.injector());
        helper.bus2().once(new Subscriber1<String>() {
          @Override
          public void onMessage(String viewerId) {
            assertEquals("facebookId", viewerId);
            finished();
          }
        }, TinMessages2.VIEWER_ID);
        helper.bus2().once(new Subscriber1<FirebaseReferences>() {
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
        }, TinMessages2.FIREBASE_REFERENCES);
        viewerService.setViewerFacebookId("facebookId");
      }
    });
    endAsyncTestBlock();
  }
}
