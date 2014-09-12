package com.tinlib.services;

import com.firebase.client.Firebase;
import com.tinlib.asynctest.AsyncTestCase;
import com.tinlib.core.TinKeys;
import com.tinlib.error.ErrorHandler;
import com.tinlib.generated.Action;
import com.tinlib.convey.Subscriber1;
import com.tinlib.test.*;
import com.tinlib.util.Procedure;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CurrentActionListenerTest extends AsyncTestCase {
  private static final String VIEWER_ID = TestUtils.newViewerId();
  private static final String VIEWER_KEY = TestUtils.newViewerKey();
  private static final String GAME_ID = TestUtils.newGameId();

  @Test
  public void testSetCurrentAction() {
    beginAsyncTestBlock();
    final Action testAction = TestUtils.newUnsubmittedActionWithCommand(GAME_ID).build();

    TestConfiguration.Builder builder = TestConfiguration.newBuilder();
    builder.setFirebase(new Firebase(TestHelperTwo.FIREBASE_URL));
    builder.setAnonymousViewer(VIEWER_ID, VIEWER_KEY);
    TestHelper.runTest(this, builder.build(), new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
        final CurrentActionListener currentActionListener =
            new CurrentActionListener(helper.injector());
        helper.bus().once(TinKeys.CURRENT_ACTION, new Subscriber1<Action>() {
          @Override
          public void onMessage(Action currentAction) {
            assertEquals(testAction, currentAction);
            finished();
          }
        });

        helper.references().currentActionReferenceForGame(GAME_ID).setValue(
            testAction.serialize());
        helper.bus().produce(TinKeys.CURRENT_GAME_ID, GAME_ID);
      }
    });
    endAsyncTestBlock();
  }

  @Test
  public void testCurrentActionError() {
    beginAsyncTestBlock();
    TestConfiguration.Builder builder = TestConfiguration.newBuilder();
    builder.setFirebase(new ErroringFirebase(TestHelperTwo.FIREBASE_URL,
        "games/" + GAME_ID + "/currentAction", "addValueEventListener"));
    builder.setFailOnError(false);
    builder.multibindInstance(ErrorHandler.class,
        TestHelper.finishedErrorHandler(FINISHED_RUNNABLE));
    builder.setAnonymousViewer(VIEWER_ID, VIEWER_KEY);
    TestHelper.runTest(this, builder.build(), new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
        CurrentActionListener currentActionListener = new CurrentActionListener(helper.injector());
        helper.bus().produce(TinKeys.CURRENT_GAME_ID, GAME_ID);
      }
    });
    endAsyncTestBlock();
  }
}
