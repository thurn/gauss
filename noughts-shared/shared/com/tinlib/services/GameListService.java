package com.tinlib.services;

import com.tinlib.core.TinKeys;
import com.tinlib.core.TinMessages;
import com.tinlib.inject.Injector;
import com.tinlib.message.Bus;
import com.tinlib.message.Subscriber2;

public class GameListService {
  private final Bus bus;

  public GameListService(final Injector injector) {
    bus = injector.get(TinKeys.BUS);
    bus.await(TinMessages.VIEWER_ID, TinMessages.FIREBASE_REFERENCES,
        new Subscriber2<String, FirebaseReferences>() {
      @Override
      public void onMessage(String viewerId, FirebaseReferences firebaseReferences) {
        bus.produce(TinMessages.GAME_LIST, new GameList(injector, viewerId, firebaseReferences));
      }
    });
  }
}
