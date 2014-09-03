package com.tinlib.services;

import com.firebase.client.FirebaseError;
import com.tinlib.core.TinKeys;
import com.tinlib.core.TinMessages2;
import com.tinlib.entities.EntityMutator;
import com.tinlib.generated.Action;
import com.tinlib.generated.Game;
import com.tinlib.inject.Injector;
import com.tinlib.message.Bus2;
import com.tinlib.message.Subscriber3;

/**
 * Service for mutating games and their current actions.
 */
public class GameMutator {
  public static interface GameMutation {
    public void mutate(String viewerId, Game.Builder game);

    public void onComplete(String viewerId, FirebaseReferences references, Game game);

    public void onError(String viewerId, FirebaseError error);
  }

  public static interface ActionMutation {
    public void mutate(String viewerId, Action.Builder action, Game currentGame);

    public void onComplete(String viewerId, FirebaseReferences references, Action action,
        Game game);

    public void onError(String viewerId, FirebaseError error);
  }

  private final Bus2 bus;

  public GameMutator(Injector injector) {
    bus = injector.get(TinKeys.BUS2);
  }

  /**
   * Applies the provided entity mutation to the current game.
   */
  public void mutateCurrentGame(final GameMutation mutation) {
    bus.once(TinMessages2.VIEWER_ID, TinMessages2.FIREBASE_REFERENCES, TinMessages2.CURRENT_GAME_ID,
        new Subscriber3<String, FirebaseReferences, String>() {
      @Override
      public void onMessage(final String viewerId, final FirebaseReferences references,
          final String currentGameId) {
        EntityMutator.mutateEntity(references.gameReference(currentGameId), Game.newDeserializer(),
            new EntityMutator.Mutation<Game, Game.Builder>() {
          @Override
          public void mutate(Game.Builder builder) {
            mutation.mutate(viewerId, builder);
          }

          @Override
          public void onComplete(Game game) {
            mutation.onComplete(viewerId, references, game);
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
   * Applies the provided entity mutation to the current action of the current game.
   */
  public void mutateCurrentAction(final ActionMutation mutation) {
    bus.once(TinMessages2.VIEWER_ID, TinMessages2.FIREBASE_REFERENCES, TinMessages2.CURRENT_GAME,
        new Subscriber3<String, FirebaseReferences, Game>() {
      @Override
      public void onMessage(final String viewerId, final FirebaseReferences references,
          final Game currentGame) {
        EntityMutator.mutateEntity(references.currentActionReferenceForGame(currentGame.getId()),
            Action.newDeserializer(), new EntityMutator.Mutation<Action, Action.Builder>() {
          @Override
          public void mutate(Action.Builder builder) {
            mutation.mutate(viewerId, builder, currentGame);
          }

          @Override
          public void onComplete(Action entity) {
            mutation.onComplete(viewerId, references, entity, currentGame);
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
