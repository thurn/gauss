package com.tinlib.shared;

import com.firebase.client.Firebase;
import com.tinlib.core.TinMessages;
import com.tinlib.message.Subscriber1;
import com.tinlib.test.TestHelper;
import com.tinlib.test.TinTestCase;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ViewerTest extends TinTestCase {
  @org.junit.Test
  public void testSetViewerAnonymousId() {
    beginAsyncTestBlock(2);
    TestHelper.Builder helper = TestHelper.newBuilder(this);
    helper.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    helper.runTest(new TestHelper.Test() {
      @Override
      public void run(TestHelper helper) {
        Viewer viewer = new Viewer(helper.injector());

        helper.bus().once(TinMessages.VIEWER_ID, new Subscriber1<String>() {
          @Override
          public void onMessage(String viewerId) {
            assertEquals("viewerId", viewerId);
            finished();
          }
        });
        helper.bus().once(TinMessages.FIREBASE_REFERENCES, new Subscriber1<FirebaseReferences>() {
          @Override
          public void onMessage(FirebaseReferences references) {
            assertEquals("gameId", references.gameReference("gameId").getName());
            assertEquals("games", references.gameReference("games").getParent().getName());

            assertEquals("games", references.userGamesReference().getName());
            assertEquals("viewerKey", references.userGamesReference().getParent().getName());
            assertEquals("users", references.userGamesReference().getParent().getParent().getName());
            finished();
          }
            }
        );
        viewer.setViewerAnonymousId("viewerId", "viewerKey");
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
        Viewer viewer = new Viewer(helper.injector());
        helper.bus().once(TinMessages.VIEWER_ID, new Subscriber1<String>() {
          @Override
          public void onMessage(String viewerId) {
            assertEquals("facebookId", viewerId);
            finished();
          }
        });
        helper.bus().once(TinMessages.FIREBASE_REFERENCES, new Subscriber1<FirebaseReferences>() {
              @Override
              public void onMessage(FirebaseReferences references) {
                assertEquals("gameId", references.gameReference("gameId").getName());
                assertEquals("games", references.gameReference("games").getParent().getName());

                assertEquals("games", references.userGamesReference().getName());
                assertEquals("facebookId", references.userGamesReference().getParent().getName());
                assertEquals("facebookUsers",
                    references.userGamesReference().getParent().getParent().getName());
                finished();
              }
            }
        );
        viewer.setViewerFacebookId("facebookId");
      }
    });
    endAsyncTestBlock();
  }
}
