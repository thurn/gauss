package com.tinlib.services;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.tinlib.core.TinKeys;
import com.tinlib.core.TinKeys2;
import com.tinlib.error.ErrorService;
import com.tinlib.generated.Game;
import com.tinlib.infuse.Injector;
import com.tinlib.convey.Bus;
import com.tinlib.convey.Subscriber2;

/**
 * Service for broadcasting updates about the state of the current game.
 *
 * <h1>Dependencies</h1>
 * <ul>
 *   <li>{@link com.tinlib.core.TinKeys2#BUS}</li>
 *   <li>{@link com.tinlib.core.TinKeys2#ERROR_SERVICE}</li>
 *   <li>{@link com.tinlib.core.TinKeys2#KEYED_LISTENER_SERVICE}</li>
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

  private final Bus bus;
  private final ErrorService errorService;
  private final KeyedListenerService listenerService;

  private String gameId;

  public CurrentGameListener(Injector injector) {
    bus = injector.get(TinKeys2.BUS2);
    errorService = injector.get(TinKeys2.ERROR_SERVICE);
    listenerService = injector.get(TinKeys2.KEYED_LISTENER_SERVICE);
    bus.await(TinKeys.FIREBASE_REFERENCES, TinKeys.CURRENT_GAME_ID, this);
  }

  /**
   * Loads the game with the given game ID, making it the new current game.
   * Broadcasts the TinMessages.CURRENT_GAME_ID message.
   */
  public void loadGame(String gameId) {
    bus.produce(TinKeys.CURRENT_GAME_ID, gameId);
  }

  @Override
  public void onMessage(FirebaseReferences references, final String gameId) {
    listenerService.addValueEventListener(references.gameReference(gameId), LISTENER_KEY,
        new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        if (dataSnapshot.getValue() != null) {
          Game currentGame = Game.newDeserializer().fromDataSnapshot(dataSnapshot);
          bus.produce(TinKeys.CURRENT_GAME, currentGame, TinKeys.CURRENT_GAME_ID);
        }
      }

      @Override
      public void onCancelled(FirebaseError firebaseError) {
        errorService.error("Game value listener cancelled: %s", firebaseError);
      }
    });
  }
}
