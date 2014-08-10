package com.tinlib.services;

import com.tinlib.core.TinKeys;
import com.tinlib.core.TinMessages;
import com.tinlib.generated.Game;
import com.tinlib.generated.GameStatus;
import com.tinlib.inject.Injector;
import com.tinlib.message.Bus;
import com.tinlib.message.Subscriber1;
import com.tinlib.util.Games;

public class GameStatusListener {
  private final Bus bus;
  private GameStatus lastStatus;

  public GameStatusListener(Injector injector) {
    bus = injector.get(TinKeys.BUS);
    bus.await(TinMessages.CURRENT_GAME, new Subscriber1<Game>() {
      @Override
      public void onMessage(Game currentGame) {
        GameStatus status = Games.gameStatus(currentGame);
        if (!status.equals(lastStatus)) {
          bus.produce(TinMessages.GAME_STATUS, status);
          lastStatus = status;
        }
      }
    });
  }
}
