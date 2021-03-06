package com.tinlib.jgail.service;

import com.tinlib.convey.Bus;
import com.tinlib.convey.Subscriber2;
import com.tinlib.core.TinKeys;
import com.tinlib.defer.SuccessHandler;
import com.tinlib.generated.Action;
import com.tinlib.generated.Command;
import com.tinlib.generated.Game;
import com.tinlib.generated.Profile;
import com.tinlib.infuse.Injector;
import com.tinlib.jgail.core.ActionScore;
import com.tinlib.jgail.core.Agent;
import com.tinlib.jgail.core.State;
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
    bus = injector.get(Bus.class);
    aiProvider = injector.get(AIProvider.class);
    aiActionAdapter = injector.get(AIActionAdapter.class);
    addCommandService = injector.get(AddCommandService.class);
    submitActionService = injector.get(SubmitActionService.class);
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
        final Action.Builder action = Actions.newEmptyAction(currentGame.getId()).toBuilder();
        addCommandService.addCommandsToAction(viewerId, currentGame, action, commands);
        submitActionService.submitAction(action.build()).addSuccessHandler(
            new SuccessHandler<Void>() {
          @Override
          public void onSuccess(Void value) {
            bus.post(TinKeys.AI_ACTION_SUBMITTED);
          }
        });
      }
    });
  }
}