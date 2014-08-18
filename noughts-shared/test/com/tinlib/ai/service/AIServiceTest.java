package com.tinlib.ai.service;

import com.firebase.client.Firebase;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.tinlib.ai.core.ActionScore;
import com.tinlib.ai.core.Agent;
import com.tinlib.ai.core.State;
import com.tinlib.core.TinKeys;
import com.tinlib.core.TinMessages;
import com.tinlib.generated.Action;
import com.tinlib.generated.Command;
import com.tinlib.generated.Game;
import com.tinlib.generated.Profile;
import com.tinlib.message.Subscriber0;
import com.tinlib.services.GameOverService;
import com.tinlib.services.NextPlayerService;
import com.tinlib.test.TestHelper;
import com.tinlib.test.TestUtils;
import com.tinlib.test.TinTestCase;
import com.tinlib.time.TimeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AIServiceTest extends TinTestCase {
  private static final String VIEWER_ID = TestUtils.newViewerId();
  private static final String VIEWER_KEY = TestUtils.newViewerKey();
  private static final String GAME_ID = TestUtils.newGameId();
  private final long ACTION = 123L;
  private final List<Command> COMMANDS =
      ImmutableList.of(Command.newBuilder().setPlayerNumber(4).build());

  @Mock
  private Agent mockAgent;
  @Mock
  private State mockState;
  @Mock
  private GameOverService mockGameOverService;
  @Mock
  private NextPlayerService mockNextPlayerService;
  @Mock
  private TimeService mockTimeService;

  private class MockAIProvider implements AIProvider {
    @Override
    public State provideState(Profile profile) {
      return mockState;
    }

    @Override
    public Agent provideAgent(Profile profile) {
      return mockAgent;
    }
  }

  private class TestActionAdapter implements AIActionAdapter {
    @Override
    public List<Command> adaptAction(long action) {
      return COMMANDS;
    }
  }

  @Test
  public void testPickComputerAction() {
    beginAsyncTestBlock();
    Game.Builder testGameBuilder = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID);
    testGameBuilder.setCurrentPlayerNumber(1);
    testGameBuilder.setProfile(1,
        testGameBuilder.getProfile(1).toBuilder().setIsComputerPlayer(true));
    testGameBuilder.setPlayer(1, VIEWER_ID);
    testGameBuilder.setIsLocalMultiplayer(true);
    final Game testGame = testGameBuilder.build();
    TestHelper.Builder builder = TestHelper.newBuilder(this);
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    builder.setAnonymousViewer(VIEWER_ID, VIEWER_KEY);
    builder.setGame(testGame);
    builder.setCurrentAction(TestUtils.newEmptyAction(GAME_ID).build());
    builder.setGameOverService(mockGameOverService);
    builder.setNextPlayerService(mockNextPlayerService);
    builder.setTimeService(mockTimeService);
    builder.bindInstance(TinKeys.AI_PROVIDER, new MockAIProvider());
    builder.bindInstance(TinKeys.AI_ACTION_ADAPTER, new TestActionAdapter());
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(final TestHelper helper) {
        AIService aiService = new AIService(helper.injector());
        helper.bus().await(TinMessages.SUBMIT_ACTION_COMPLETED, new Subscriber0() {
          @Override
          public void onMessage() {
            Game expectedGame = testGame.toBuilder()
                .setLastModified(456L)
                .addSubmittedAction(TestUtils.newEmptyAction(GAME_ID)
                    .setIsSubmitted(true)
                    .setPlayerNumber(1)
                    .addCommand(Command.newBuilder().setPlayerNumber(1)))
                .setCurrentPlayerNumber(0)
                .build();
            helper.assertGameEquals(expectedGame, FINISHED);
          }
        });
        when(mockTimeService.currentTimeMillis()).thenReturn(456L);
        when(mockGameOverService.computeVictors(eq(testGame), any(Action.class)))
            .thenReturn(Optional.<List<Integer>>absent());
        when(mockNextPlayerService.nextPlayerNumber(eq(testGame), any(Action.class)))
            .thenReturn(0);
        when(mockAgent.pickAction(eq(1), eq(mockState))).thenReturn(new ActionScore(ACTION, 1.0));
        helper.bus().produce(TinMessages.ACTION_SUBMITTED, testGame);
      }
    });
    endAsyncTestBlock();
  }
}
