package com.tinlib.services;

import com.tinlib.core.TinKeys;
import com.tinlib.core.TinMessages;
import com.tinlib.core.TinMessages2;
import com.tinlib.generated.Game;
import com.tinlib.inject.Injector;
import com.tinlib.message.Bus;
import com.tinlib.message.Bus2;
import com.tinlib.message.Subscriber1;

public class GameOverListener {
  private final Bus2 bus;
  private Game previousGame;

  public GameOverListener(Injector injector) {
    bus = injector.get(TinKeys.BUS2);
    listen();
  }

  private void listen() {
    bus.await(TinMessages2.CURRENT_GAME, new Subscriber1<Game>() {
      @Override
      public void onMessage(Game currentGame) {
        if (previousGame != null && currentGame.getIsGameOver() && !previousGame.getIsGameOver()) {
          bus.post(TinMessages2.GAME_OVER, currentGame);
        }
        previousGame = currentGame;
      }
    });
  }
}
