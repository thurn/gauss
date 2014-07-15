package com.tinlib.shared;

import ca.thurn.noughts.shared.entities.Action;
import ca.thurn.noughts.shared.entities.EntityMutator;
import ca.thurn.noughts.shared.entities.Game;
import com.firebase.client.FirebaseError;
import com.tinlib.core.TinKeys;
import com.tinlib.core.TinMessages;
import com.tinlib.inject.Injector;
import com.tinlib.message.Bus;
import com.tinlib.message.Subscriber2;

/**
 * Service for mutating games and their current actions.
 */
public class GameMutator {
  public static interface GameMutation {
    public void mutate(String viewerId, Game.Builder game);

    public void onComplete(String viewerId, Game game);

    public void onError(String viewerId, FirebaseError error);
  }

  public static interface ActionMutation {
    public void mutate(String viewerId, Action.Builder action);

    public void onComplete(String viewerId, Action action);

    public void onError(String viewerId, FirebaseError error);
  }

  private final Bus bus;

  public GameMutator(Injector injector) {
    bus = injector.get(TinKeys.BUS);
  }

  /**
   * Applies the provided entity mutation to the game with the provided ID.
   */
  public void mutateGame(final String gameId, final GameMutation mutation) {
    bus.once(TinMessages.VIEWER_ID, TinMessages.FIREBASE_REFERENCES,
        new Subscriber2<String, FirebaseReferences>() {
      @Override
      public void onMessage(final String viewerId, FirebaseReferences references) {
        EntityMutator.mutateEntity(references.gameReference(gameId), Game.newDeserializer(),
            new EntityMutator.Mutation<Game, Game.Builder>() {
          @Override
          public void mutate(Game.Builder builder) {
            mutation.mutate(viewerId, builder);
          }

          @Override
          public void onComplete(Game entity) {
            mutation.onComplete(viewerId, entity);
          }

          @Override
          public void onError(FirebaseError error, boolean committed) {
            mutation.onError(viewerId, error);
          }
        });
      }
    });
  }

  /**
   * Applies the provided entity mutation to the current action of the game
   * with the provided ID.
   */
  public void mutateAction(final String gameId, final ActionMutation mutation) {
    bus.once(TinMessages.VIEWER_ID, TinMessages.FIREBASE_REFERENCES,
        new Subscriber2<String, FirebaseReferences>() {
      @Override
      public void onMessage(final String viewerId, FirebaseReferences references) {
        EntityMutator.mutateEntity(references.currentActionReferenceForGame(gameId),
            Action.newDeserializer(), new EntityMutator.Mutation<Action, Action.Builder>() {
          @Override
          public void mutate(Action.Builder builder) {
            mutation.mutate(viewerId, builder);
          }

          @Override
          public void onComplete(Action entity) {
            mutation.onComplete(viewerId, entity);
          }

          @Override
          public void onError(FirebaseError error, boolean committed) {
            mutation.onError(viewerId, error);
          }
        });
      }
    });
  }
}
