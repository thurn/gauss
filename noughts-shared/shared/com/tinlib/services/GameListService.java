package com.tinlib.services;

import com.tinlib.core.TinKeys;
import com.tinlib.core.TinKeys2;
import com.tinlib.infuse.Injector;
import com.tinlib.convey.Bus;
import com.tinlib.convey.Subscriber2;

public class GameListService {
  private final Bus bus;

  public GameListService(final Injector injector) {
    bus = injector.get(TinKeys2.BUS);
    bus.await(TinKeys.VIEWER_ID, TinKeys.FIREBASE_REFERENCES,
        new Subscriber2<String, FirebaseReferences>() {
      @Override
      public void onMessage(String viewerId, FirebaseReferences firebaseReferences) {
        bus.produce(TinKeys.GAME_LIST, new GameList(injector, viewerId, firebaseReferences),
            TinKeys.VIEWER_ID, TinKeys.FIREBASE_REFERENCES);
      }
    });
  }
}
