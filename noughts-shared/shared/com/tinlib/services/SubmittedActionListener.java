package com.tinlib.services;

import com.tinlib.core.TinKeys;
import com.tinlib.core.TinKeys2;
import com.tinlib.generated.Game;
import com.tinlib.infuse.Injector;
import com.tinlib.convey.Bus;
import com.tinlib.convey.Subscriber1;

public class SubmittedActionListener {
  private final Bus bus;
  private Game lastGame;

  public SubmittedActionListener(Injector injector) {
    bus = injector.get(TinKeys2.BUS);
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
