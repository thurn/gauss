package com.tinlib.shared;

import com.tinlib.generated.Action;
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
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CurrentActionTest extends TinTestCase {
  private static final String VIEWER_ID = TestUtils.newViewerId();
  private static final String VIEWER_KEY = TestUtils.newViewerKey();
  private static final String GAME_ID = TestUtils.newGameId();

  @Mock
  private ErrorHandler mockErrorHandler;

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
        final CurrentAction currentAction = new CurrentAction(helper.injector());

        helper.bus().once(TinMessages.CURRENT_ACTION, new Subscriber1<Action>() {
          @Override
          public void onMessage(Action currentAction) {
            assertEquals(testAction, currentAction);
            finished();
          }
        });

        helper.references().currentActionReferenceForGame(GAME_ID).setValue(
            testAction.serialize());
        helper.bus().produce(TinMessages.CURRENT_GAME_ID, GAME_ID);
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
    builder.setErrorHandler(mockErrorHandler);
    builder.setAnonymousViewer(VIEWER_ID, VIEWER_KEY);
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(TestHelper helper) {
        CurrentAction currentAction = new CurrentAction(helper.injector());
        helper.bus().produce(TinMessages.CURRENT_GAME_ID, GAME_ID);
        finished();
      }
    });
    endAsyncTestBlock();

    TestHelper.verifyError(mockErrorHandler);
  }
}
