package com.tinlib.jgail.service;

import com.tinlib.jgail.core.ActionScore;
import com.tinlib.jgail.core.Agent;
import com.tinlib.jgail.core.State;
import com.tinlib.core.TinKeys;
import com.tinlib.core.TinKeys2;
import com.tinlib.generated.Action;
import com.tinlib.generated.Command;
import com.tinlib.generated.Game;
import com.tinlib.generated.Profile;
import com.tinlib.infuse.Injector;
import com.tinlib.convey.Bus;
import com.tinlib.convey.Subscriber2;
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
    bus = injector.get(TinKeys2.BUS);
    aiProvider = injector.get(TinKeys2.AI_PROVIDER);
    aiActionAdapter = injector.get(TinKeys2.AI_ACTION_ADAPTER);
    addCommandService = injector.get(TinKeys2.ADD_COMMAND_SERVICE);
    submitActionService = injector.get(TinKeys2.SUBMIT_ACTION_SERVICE);
    listen();
  }

  private void listen() {
    bus.await(TinKeys.VIEWER_ID, TinKeys.ACTION_SUBMITTED, new Subscriber2<String, Game>() {
      @Override
      public void onMessage(String viewerId, Game currentGame) {
        if (currentGame.getIsGameOver() || !currentGame.hasCurrentPlayerNumber()) return;
        Profile profile = currentGame.getProfile(currentGame.getCurrentPlayerNumber());
        if (!(profile.hasIsComputerPlayer() && profile.getIsComputerPlayer())) return;

        State state = aiProvider.provideState(profile);
        state.initializeFrom(currentGame);
        Agent agent = aiProvider.provideAgent(profile);
        ActionScore actionScore = agent.pickActionBlocking(currentGame.getCurrentPlayerNumber(),
            state);
        List<Command> commands = aiActionAdapter.adaptAction(actionScore.getAction());
        Action.Builder action = Actions.newEmptyAction(currentGame.getId()).toBuilder();
        addCommandService.addCommandsToAction(viewerId, currentGame, action, commands);
        submitActionService.submitAction(action.build());
      }
    });
  }
}