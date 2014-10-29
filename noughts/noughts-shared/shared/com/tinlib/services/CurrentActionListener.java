package com.tinlib.services;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.tinlib.convey.Bus;
import com.tinlib.convey.Subscriber2;
import com.tinlib.core.TinKeys;
import com.tinlib.entities.FirebaseDeserializer;
import com.tinlib.error.ErrorService;
import com.tinlib.generated.Action;
import com.tinlib.infuse.Injector;

/**
 * Service for broadcasting updates about the state of the player's current
 * action within the current game.
 *
 * <h1>Dependencies</h1>
 * <ul>
 *   <li>{@link Bus}</li>
 *   <li>{@link ErrorService}</li>
 *   <li>{@link KeyedListenerService}</li>
 * </ul>
 *
 * <h1>Input Messages</h1>
 * <ul>
 *   <li>{@link TinKeys#CURRENT_GAME_ID}</li>
 *   <li>{@link TinKeys#FIREBASE_REFERENCES}</li>
 * </ul>
 *
 * <h1>Output Messages</h1>
 * <ul>
 *   <li>{@link TinKeys#CURRENT_ACTION}</li>
 * </ul>
 */
public class CurrentActionListener implements Subscriber2<FirebaseReferences, String> {
  public static final String LISTENER_KEY = "tin.CurrentAction";

  private final Bus bus;
  private final ErrorService errorService;
  private final KeyedListenerService listenerService;

  private String gameId;

  public CurrentActionListener(Injector injector) {
    bus = injector.get(Bus.class);
    errorService = injector.get(ErrorService.class);
    listenerService = injector.get(KeyedListenerService.class);
    bus.await(TinKeys.FIREBASE_REFERENCES, TinKeys.CURRENT_GAME_ID, this);
  }

  @Override
  public void onMessage(FirebaseReferences references, String gameId) {
    listenerService.addValueEventListener(references.currentActionReferenceForGame(gameId),
        LISTENER_KEY, new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        if (dataSnapshot.getValue() != null) {
          Action currentAction = FirebaseDeserializer.fromDataSnapshot(Action.newDeserializer(),
              dataSnapshot);
          bus.produce(TinKeys.CURRENT_ACTION, currentAction, TinKeys.CURRENT_GAME_ID);
        }
      }

      @Override
      public void onCancelled(FirebaseError firebaseError) {
        errorService.error("Current action value listener cancelled: %s",
            firebaseError);
      }
    });
  }
}
