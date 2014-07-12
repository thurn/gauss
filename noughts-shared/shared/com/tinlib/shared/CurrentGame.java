package com.tinlib.shared;

import ca.thurn.noughts.shared.entities.Game;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.common.collect.ImmutableList;
import com.tinlib.inject.Injector;
import com.tinlib.message.Awaiter;
import com.tinlib.message.Bus;

import java.util.Map;

public class CurrentGame implements Awaiter {
  private final Bus bus;
  private final ErrorService errorService;
  private final KeyedListenerService listenerService;

  private String gameId;

  public CurrentGame(Injector injector) {
    bus = (Bus)injector.get(TinKeys.BUS);
    errorService = (ErrorService)injector.get(TinKeys.ERROR_SERVICE);
    listenerService = (KeyedListenerService)injector.get(TinKeys.KEYED_LISTENER_SERVICE);
  }

  /**
   * Loads the game with the given game ID, making it the new current game.
   */
  public void loadGame(String gameId) {
    bus.produce(TinMessages.CURRENT_GAME_ID, gameId);
    bus.await(this, ImmutableList.of(TinMessages.FIREBASE_REFERENCES, TinMessages.CURRENT_GAME_ID));
  }

  @Override
  public void onResult(Map<String, Object> map) {
    final FirebaseReferences references =
        (FirebaseReferences)map.get(TinMessages.FIREBASE_REFERENCES);
    final String gameId = (String)map.get(TinMessages.CURRENT_GAME_ID);

    listenerService.addValueEventListener(references.gameReference(gameId),
        TinMessages.CURRENT_GAME, new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
            Game currentGame = Game.newDeserializer().fromDataSnapshot(dataSnapshot);
            bus.produce(TinMessages.CURRENT_GAME, currentGame);
          }

          @Override
          public void onCancelled(FirebaseError firebaseError) {
            errorService.error("Game value listener cancelled: %s", firebaseError.getMessage());
          }
        }
    );
  }
}
