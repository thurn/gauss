package com.tinlib.services;

import com.firebase.client.Firebase;
import com.tinlib.core.TinMessages2;
import com.tinlib.error.ErrorHandler;
import com.tinlib.generated.Action;
import com.tinlib.message.Subscriber1;
import com.tinlib.test.ErroringFirebase;
import com.tinlib.test.TestHelper;
import com.tinlib.test.TestUtils;
import com.tinlib.test.TinTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CurrentActionListenerTest extends TinTestCase {
  private static final String VIEWER_ID = TestUtils.newViewerId();
  private static final String VIEWER_KEY = TestUtils.newViewerKey();
  private static final String GAME_ID = TestUtils.newGameId();

  @Test
  public void testSetCurrentAction() {
    beginAsyncTestBlock();
    final Action testAction = TestUtils.newUnsubmittedActionWithCommand(GAME_ID).build();

    TestHelper.Builder builder = TestHelper.newBuilder(this);
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    builder.setAnonymousViewer(VIEWER_ID, VIEWER_KEY);
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(TestHelper helper) {
        final CurrentActionListener currentActionListener =
            new CurrentActionListener(helper.injector());
        helper.bus2().once(TinMessages2.CURRENT_ACTION, new Subscriber1<Action>() {
          @Override
          public void onMessage(Action currentAction) {
            assertEquals(testAction, currentAction);
            finished();
          }
        });

        helper.references().currentActionReferenceForGame(GAME_ID).setValue(
            testAction.serialize());
        helper.bus2().produce(TinMessages2.CURRENT_GAME_ID, GAME_ID);
      }
    });
    endAsyncTestBlock();
  }

  @Test
  public void testCurrentActionError() {
    beginAsyncTestBlock();
    TestHelper.Builder builder = TestHelper.newBuilder(this);
    builder.setFirebase(new ErroringFirebase(TestHelper.FIREBASE_URL,
        "games/" + GAME_ID + "/currentAction", "addValueEventListener"));
    builder.setErrorHandler(new ErrorHandler() {
      @Override
      public void error(String message, Object[] args) {
        finished();
      }
    });
    builder.setAnonymousViewer(VIEWER_ID, VIEWER_KEY);
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(TestHelper helper) {
        CurrentActionListener currentActionListener = new CurrentActionListener(helper.injector());
        helper.bus2().produce(TinMessages2.CURRENT_GAME_ID, GAME_ID);
      }
    });
    endAsyncTestBlock();
  }
}
