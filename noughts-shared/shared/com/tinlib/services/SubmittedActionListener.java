package com.tinlib.services;

import com.tinlib.core.TinKeys;
import com.tinlib.core.TinMessages2;
import com.tinlib.generated.Game;
import com.tinlib.inject.Injector;
import com.tinlib.message.Bus2;
import com.tinlib.message.Subscriber1;

public class SubmittedActionListener {
  private final Bus2 bus;
  private Game lastGame;

  public SubmittedActionListener(Injector injector) {
    bus = injector.get(TinKeys.BUS2);
    listen();
  }

  private void listen() {
    bus.await(TinMessages2.CURRENT_GAME, new Subscriber1<Game>() {
      @Override
      public void onMessage(Game currentGame) {
        if (lastGame != null && currentGame.getId().equals(lastGame.getId())) {
          for (int i = lastGame.getSubmittedActionCount();
               i < currentGame.getSubmittedActionCount(); ++i) {
            bus.post(TinMessages2.ACTION_SUBMITTED, currentGame);
          }
        }
        lastGame = currentGame;
      }
    });
  }
}
