package com.tinlib.services;

import com.tinlib.core.TinKeys;
import com.tinlib.core.TinMessages2;
import com.tinlib.inject.Injector;
import com.tinlib.message.Bus2;
import com.tinlib.message.Subscriber2;

public class GameListService {
  private final Bus2 bus;

  public GameListService(final Injector injector) {
    bus = injector.get(TinKeys.BUS2);
    bus.await(TinMessages2.VIEWER_ID, TinMessages2.FIREBASE_REFERENCES,
        new Subscriber2<String, FirebaseReferences>() {
      @Override
      public void onMessage(String viewerId, FirebaseReferences firebaseReferences) {
        bus.produce(TinMessages2.GAME_LIST, new GameList(injector, viewerId, firebaseReferences),
            TinMessages2.VIEWER_ID, TinMessages2.FIREBASE_REFERENCES);
      }
    });
  }
}
