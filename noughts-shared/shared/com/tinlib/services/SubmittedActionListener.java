package com.tinlib.services;

import com.tinlib.core.TinKeys;
import com.tinlib.core.TinMessages;
import com.tinlib.generated.Game;
import com.tinlib.inject.Injector;
import com.tinlib.message.Bus;
import com.tinlib.message.Subscriber1;

public class SubmittedActionListener {
  private final Bus bus;
  private Game lastGame;

  public SubmittedActionListener(Injector injector) {
    bus = injector.get(TinKeys.BUS);
    listen();
  }

  private void listen() {
    bus.await(TinMessages.CURRENT_GAME, new Subscriber1<Game>() {
      @Override
      public void onMessage(Game currentGame) {
        if (lastGame != null && currentGame.getId().equals(lastGame.getId())) {
          for (int i = lastGame.getSubmittedActionCount();
               i < currentGame.getSubmittedActionCount(); ++i) {
            bus.produce(TinMessages.ACTION_SUBMITTED, currentGame.getSubmittedAction(i));
          }
        }
        lastGame = currentGame;
      }
    });
  }
}
