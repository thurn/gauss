package com.tinlib.shared;

import com.tinlib.test.TinTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ViewerTest extends TinTestCase {
  private Viewer viewer;

  @Override
  public void setUp(Runnable done) {
    viewer = new Viewer(newTestInjector());
    done.run();
  }

  @Override
  public void tearDown(Runnable done) {
    done.run();
  }

  @Test
  public void testSetViewerAnonymousId() {
    beginAsyncTestBlock(2);
    expectMessage(TinMessages.VIEWER_ID, new ValueListener() {
      @Override
      public void onValue(Object object) {
        assertEquals("viewerId", object);
        finished();
      }
    });
    expectMessage(TinMessages.FIREBASE_REFERENCES, new ValueListener() {
      @Override
      public void onValue(Object object) {
        FirebaseReferences references = (FirebaseReferences)object;
        assertEquals("gameId", references.gameReference("gameId").getName());
        assertEquals("games", references.gameReference("games").getParent().getName());

        assertEquals("games", references.userGamesReference().getName());
        assertEquals("viewerKey", references.userGamesReference().getParent().getName());
        assertEquals("users", references.userGamesReference().getParent().getParent().getName());
        finished();
      }
    });
    viewer.setViewerAnonymousId("viewerId", "viewerKey");
    endAsyncTestBlock();
  }

  @Test
  public void testSetViewerFacebookId() {
    beginAsyncTestBlock(2);
    expectMessage(TinMessages.VIEWER_ID, new ValueListener() {
      @Override
      public void onValue(Object object) {
        assertEquals("facebookId", object);
        finished();
      }
    });
    expectMessage(TinMessages.FIREBASE_REFERENCES, new ValueListener() {
      @Override
      public void onValue(Object object) {
        FirebaseReferences references = (FirebaseReferences)object;
        assertEquals("gameId", references.gameReference("gameId").getName());
        assertEquals("games", references.gameReference("games").getParent().getName());

        assertEquals("games", references.userGamesReference().getName());
        assertEquals("facebookId", references.userGamesReference().getParent().getName());
        assertEquals("facebookUsers",
            references.userGamesReference().getParent().getParent().getName());
        finished();
      }
    });
    viewer.setViewerFacebookId("facebookId");
    endAsyncTestBlock();
  }
}
