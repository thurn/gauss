package com.tinlib.services;

import com.tinlib.convey.Bus;
import com.tinlib.convey.Subscriber1;
import com.tinlib.core.TinKeys;
import com.tinlib.generated.Game;
import com.tinlib.infuse.Injector;

public class SubmittedActionListener {
  private final Bus bus;
  private Game lastGame;

  public SubmittedActionListener(Injector injector) {
    bus = injector.get(Bus.class);
    listen();
  }

  private void listen() {
    bus.await(TinKeys.CURRENT_GAME, new Subscriber1<Game>() {
      @Override
      public void onMessage(Game currentGame) {
        if (lastGame != null && currentGame.getId().equals(lastGame.getId())) {
          for (int i = lastGame.getSubmittedActionCount();
               i < currentGame.getSubmittedActionCount(); ++i) {
            bus.post(TinKeys.ACTION_SUBMITTED, currentGame);
          }
        }
        lastGame = currentGame;
      }
    });
  }
}
