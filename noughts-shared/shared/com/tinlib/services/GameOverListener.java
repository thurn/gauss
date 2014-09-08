package com.tinlib.services;

import com.tinlib.core.TinKeys;
import com.tinlib.core.TinKeys2;
import com.tinlib.generated.Game;
import com.tinlib.infuse.Injector;
import com.tinlib.convey.Bus;
import com.tinlib.convey.Subscriber1;

public class GameOverListener {
  private final Bus bus;
  private Game previousGame;

  public GameOverListener(Injector injector) {
    bus = injector.get(TinKeys2.BUS);
    listen();
  }

  private void listen() {
    bus.await(TinKeys.CURRENT_GAME, new Subscriber1<Game>() {
      @Override
      public void onMessage(Game currentGame) {
        if (previousGame != null && currentGame.getIsGameOver() && !previousGame.getIsGameOver()) {
          bus.post(TinKeys.GAME_OVER, currentGame);
        }
        previousGame = currentGame;
      }
    });
  }
}
