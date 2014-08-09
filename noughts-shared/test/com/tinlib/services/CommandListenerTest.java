package com.tinlib.services;

import com.firebase.client.Firebase;
import com.tinlib.core.TinMessages;
import com.tinlib.generated.Command;
import com.tinlib.generated.IndexCommand;
import com.tinlib.message.Subscriber1;
import com.tinlib.test.TestHelper;
import com.tinlib.test.TestUtils;
import com.tinlib.test.TinTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CommandListenerTest extends TinTestCase {
  private static final String GAME_ID = TestUtils.newGameId();

  @Test
  public void testCommandAdded() {
    beginAsyncTestBlock();
    final Command testCommand = Command.newBuilder().setPlayerNumber(44).build();
    TestHelper.Builder builder = TestHelper.newBuilder(this);
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(TestHelper helper) {
        helper.bus().once(TinMessages.COMMAND_ADDED, new Subscriber1<IndexCommand>() {
          @Override
          public void onMessage(IndexCommand indexCommand) {
            IndexCommand expected = IndexCommand.newBuilder()
                .setIndex(0)
                .setCommand(testCommand)
                .build();
            assertEquals(expected, indexCommand);
            finished();
          }
        });
        helper.bus().produce(TinMessages.CURRENT_ACTION,
            TestUtils.newEmptyAction(GAME_ID)
                .setPlayerNumber(0)
                .build());
        helper.bus().produce(TinMessages.CURRENT_ACTION,
            TestUtils.newEmptyAction(GAME_ID)
                .addCommand(testCommand)
                .setPlayerNumber(0)
                .build());
      }
    });
    endAsyncTestBlock();
  }

  @Test
  public void testCommandChanged() {
    beginAsyncTestBlock();
    final Command testCommand = Command.newBuilder().setPlayerNumber(44).build();
    TestHelper.Builder builder = TestHelper.newBuilder(this);
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(TestHelper helper) {
        helper.bus().once(TinMessages.COMMAND_CHANGED, new Subscriber1<IndexCommand>() {
          @Override
          public void onMessage(IndexCommand indexCommand) {
            IndexCommand expected = IndexCommand.newBuilder()
                .setIndex(0)
                .setCommand(testCommand)
                .build();
            assertEquals(expected, indexCommand);
            finished();
          }
        });
        helper.bus().produce(TinMessages.CURRENT_ACTION,
            TestUtils.newEmptyAction(GAME_ID)
                .setPlayerNumber(0)
                .addCommand(testCommand.toBuilder().setPlayerNumber(12))
                .build());
        helper.bus().produce(TinMessages.CURRENT_ACTION,
            TestUtils.newEmptyAction(GAME_ID)
                .addCommand(testCommand)
                .setPlayerNumber(0)
                .build());
      }
    });
    endAsyncTestBlock();
  }


  @Test
  public void testCommandUndone() {
    beginAsyncTestBlock();
    final Command testCommand = Command.newBuilder().setPlayerNumber(44).build();
    TestHelper.Builder builder = TestHelper.newBuilder(this);
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(TestHelper helper) {
        helper.bus().once(TinMessages.COMMAND_UNDONE, new Subscriber1<IndexCommand>() {
          @Override
          public void onMessage(IndexCommand indexCommand) {
            IndexCommand expected = IndexCommand.newBuilder()
                .setIndex(0)
                .setCommand(testCommand)
                .build();
            assertEquals(expected, indexCommand);
            finished();
          }
        });
        helper.bus().produce(TinMessages.CURRENT_ACTION,
            TestUtils.newEmptyAction(GAME_ID)
                .setPlayerNumber(0)
                .addCommand(testCommand)
                .build());
        helper.bus().produce(TinMessages.CURRENT_ACTION,
            TestUtils.newEmptyAction(GAME_ID)
                .setPlayerNumber(0)
                .build());
      }
    });
    endAsyncTestBlock();
  }

}
