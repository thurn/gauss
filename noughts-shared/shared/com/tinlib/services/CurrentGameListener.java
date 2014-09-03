package com.tinlib.services;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.tinlib.core.TinKeys;
import com.tinlib.core.TinMessages2;
import com.tinlib.error.ErrorService;
import com.tinlib.generated.Game;
import com.tinlib.inject.Injector;
import com.tinlib.message.Bus2;
import com.tinlib.message.Subscriber2;

/**
 * Service for broadcasting updates about the state of the current game.
 *
 * <h1>Dependencies</h1>
 * <ul>
 *   <li>{@link com.tinlib.core.TinKeys#BUS}</li>
 *   <li>{@link com.tinlib.core.TinKeys#ERROR_SERVICE}</li>
 *   <li>{@link com.tinlib.core.TinKeys#KEYED_LISTENER_SERVICE}</li>
 * </ul>
 *
 * <h1>Input Messages</h1>
 * <ul>
 *   <li>{@link com.tinlib.core.TinMessages#CURRENT_GAME_ID}</li>
 *   <li>{@link com.tinlib.core.TinMessages#FIREBASE_REFERENCES}</li>
 * </ul>
 *
 * <h1>Output Messages</h1>
 * <ul>
 *   <li>{@link com.tinlib.core.TinMessages#CURRENT_GAME_ID}</li>
 *   <li>{@link com.tinlib.core.TinMessages#CURRENT_GAME}</li>
 * </ul>
 */
public class CurrentGameListener implements Subscriber2<FirebaseReferences, String> {
  public static final String LISTENER_KEY = "tin.CurrentGame";

  private final Bus2 bus;
  private final ErrorService errorService;
  private final KeyedListenerService listenerService;

  private String gameId;

  public CurrentGameListener(Injector injector) {
    bus = injector.get(TinKeys.BUS2);
    errorService = injector.get(TinKeys.ERROR_SERVICE);
    listenerService = injector.get(TinKeys.KEYED_LISTENER_SERVICE);
    bus.await(TinMessages2.FIREBASE_REFERENCES, TinMessages2.CURRENT_GAME_ID, this);
  }

  /**
   * Loads the game with the given game ID, making it the new current game.
   * Broadcasts the TinMessages.CURRENT_GAME_ID message.
   */
  public void loadGame(String gameId) {
    bus.produce(TinMessages2.CURRENT_GAME_ID, gameId);
  }

  @Override
  public void onMessage(FirebaseReferences references, final String gameId) {
    listenerService.addValueEventListener(references.gameReference(gameId), LISTENER_KEY,
        new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        if (dataSnapshot.getValue() != null) {
          Game currentGame = Game.newDeserializer().fromDataSnapshot(dataSnapshot);
          bus.produce(TinMessages2.CURRENT_GAME, currentGame, TinMessages2.CURRENT_GAME_ID);
        }
      }

      @Override
      public void onCancelled(FirebaseError firebaseError) {
        errorService.error("Game value listener cancelled: %s", firebaseError);
      }
    });
  }
}
