package com.tinlib.ai.service;

import com.tinlib.ai.core.ActionScore;
import com.tinlib.ai.core.Agent;
import com.tinlib.ai.core.State;
import com.tinlib.core.TinKeys;
import com.tinlib.core.TinMessages;
import com.tinlib.generated.Action;
import com.tinlib.generated.Command;
import com.tinlib.generated.Game;
import com.tinlib.generated.Profile;
import com.tinlib.inject.Injector;
import com.tinlib.message.Bus;
import com.tinlib.message.Subscriber2;
import com.tinlib.services.AddCommandService;
import com.tinlib.services.SubmitActionService;
import com.tinlib.util.Actions;

import java.util.List;

public class AIService {
  private final Bus bus;
  private final AIProvider aiProvider;
  private final AIActionAdapter aiActionAdapter;
  private final AddCommandService addCommandService;
  private final SubmitActionService submitActionService;

  public AIService(Injector injector) {
    bus = injector.get(TinKeys.BUS);
    aiProvider = injector.get(TinKeys.AI_PROVIDER);
    aiActionAdapter = injector.get(TinKeys.AI_ACTION_ADAPTER);
    addCommandService = injector.get(TinKeys.ADD_COMMAND_SERVICE);
    submitActionService = injector.get(TinKeys.SUBMIT_ACTION_SERVICE);
    listen();
  }

  private void listen() {
    bus.await(TinMessages.VIEWER_ID, TinMessages.ACTION_SUBMITTED, new Subscriber2<String, Game>() {
      @Override
      public void onMessage(String viewerId, Game currentGame) {
        if (currentGame.getIsGameOver() || !currentGame.hasCurrentPlayerNumber()) return;
        Profile profile = currentGame.getProfile(currentGame.getCurrentPlayerNumber());
        if (!(profile.hasIsComputerPlayer() && profile.getIsComputerPlayer())) return;

        State state = aiProvider.provideState(profile);
        state.initializeFrom(currentGame);
        Agent agent = aiProvider.provideAgent(profile);
        ActionScore actionScore = agent.pickAction(currentGame.getCurrentPlayerNumber(), state);
        List<Command> commands = aiActionAdapter.adaptAction(actionScore.getAction());
        Action.Builder action = Actions.newEmptyAction(currentGame.getId()).toBuilder();
        addCommandService.addCommandsToAction(viewerId, currentGame, action, commands);
        submitActionService.submitAction(action.build());
      }
    });
  }
}