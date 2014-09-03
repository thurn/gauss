package com.tinlib.services;

import com.tinlib.core.TinKeys;
import com.tinlib.core.TinMessages;
import com.tinlib.core.TinMessages2;
import com.tinlib.generated.Game;
import com.tinlib.generated.GameStatus;
import com.tinlib.inject.Injector;
import com.tinlib.message.Bus;
import com.tinlib.message.Bus2;
import com.tinlib.message.Subscriber1;
import com.tinlib.util.Games;

public class GameStatusListener {
  private final Bus2 bus;
  private GameStatus lastStatus;

  public GameStatusListener(Injector injector) {
    bus = injector.get(TinKeys.BUS2);
    bus.await(TinMessages2.CURRENT_GAME, new Subscriber1<Game>() {
      @Override
      public void onMessage(Game currentGame) {
        GameStatus status = Games.gameStatus(currentGame);
        if (!status.equals(lastStatus)) {
          bus.produce(TinMessages2.GAME_STATUS, status, TinMessages2.CURRENT_GAME);
          lastStatus = status;
        }
      }
    });
  }
}
