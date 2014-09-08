package com.tinlib.services;

import com.tinlib.core.TinKeys;
import com.tinlib.core.TinKeys2;
import com.tinlib.generated.Game;
import com.tinlib.generated.GameStatus;
import com.tinlib.infuse.Injector;
import com.tinlib.convey.Bus;
import com.tinlib.convey.Subscriber1;
import com.tinlib.util.Games;

public class GameStatusListener {
  private final Bus bus;
  private GameStatus lastStatus;

  public GameStatusListener(Injector injector) {
    bus = injector.get(TinKeys2.BUS);
    bus.await(TinKeys.CURRENT_GAME, new Subscriber1<Game>() {
      @Override
      public void onMessage(Game currentGame) {
        GameStatus status = Games.gameStatus(currentGame);
        if (!status.equals(lastStatus)) {
          bus.produce(TinKeys.GAME_STATUS, status, TinKeys.CURRENT_GAME);
          lastStatus = status;
        }
      }
    });
  }
}
