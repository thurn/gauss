package com.tinlib.services;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.tinlib.analytics.AnalyticsService;
import com.tinlib.convey.Bus;
import com.tinlib.convey.Subscriber2;
import com.tinlib.core.TinKeys;
import com.tinlib.defer.*;
import com.tinlib.entities.EntityMutator;
import com.tinlib.error.ErrorService;
import com.tinlib.error.TinException;
import com.tinlib.generated.Game;
import com.tinlib.infuse.Injector;
import com.tinlib.time.TimeService;
import com.tinlib.util.Actions;

public class ResignGameService {
  private final Bus bus;
  private final TimeService timeService;
  private final ErrorService errorService;
  private final AnalyticsService analyticsService;

  public ResignGameService(Injector injector) {
    bus = injector.get(Bus.class);
    timeService = injector.get(TimeService.class);
    errorService = injector.get(ErrorService.class);
    analyticsService = injector.get(AnalyticsService.class);
  }

  public Promise<Game> resignGame(final String gameId) {
    final Deferred<Game> gameUpdated = Deferreds.newDeferred();
    final Deferred<Void> actionCleared = Deferreds.newDeferred();

    bus.once(TinKeys.VIEWER_ID, TinKeys.FIREBASE_REFERENCES,
        new Subscriber2<String, FirebaseReferences>() {
      @Override
      public void onMessage(final String viewerId, FirebaseReferences references) {
        updateGame(viewerId, references, gameId, gameUpdated);
        clearAction(viewerId, references, gameId, actionCleared);
      }
    });

    return Promises.awaitPair(gameUpdated, actionCleared).then(
        new Function<Pair<Game, Void>, Promise<Game>>() {
      @Override
      public Promise<Game> apply(Pair<Game, Void> pair) {
        analyticsService.trackEvent("resignGame", ImmutableMap.of("gameId", gameId));
        return Deferreds.newResolvedDeferred(pair.getFirst());
      }
    });
  }

  private void clearAction(final String viewerId, FirebaseReferences references,
      final String gameId, final Deferred<Void> actionCleared) {
    references.currentActionReferenceForGame(gameId).setValue(
        Actions.newEmptyAction(gameId).serialize(), new Firebase.CompletionListener() {
      @Override
      public void onComplete(FirebaseError firebaseError, Firebase firebase) {
        if (firebaseError != null) {
          errorService.error("Error with viewer '%s' resigning game '%s' - unable to clear " +
              "current action. %s", viewerId, gameId, firebaseError);
          actionCleared.fail(firebaseError.toException());
        } else {
          actionCleared.resolve();
        }
      }
    });
  }

  private void updateGame(final String viewerId, FirebaseReferences references, final String gameId,
        final Deferred<Game> gameUpdated) {
    EntityMutator.mutateEntity(references.gameReference(gameId), Game.newDeserializer(),
        new EntityMutator.Mutation<Game, Game.Builder>() {
          @Override
          public void mutate(Game.Builder game) {
            if (game.getIsGameOver() || !game.getPlayerList().contains(viewerId)) {
              throw new TinException("Viewer '%s' cannot resign game '%s'", viewerId, gameId);
            }
            game.setIsGameOver(true);
            game.clearCurrentPlayerNumber();
            game.setLastModified(timeService.currentTimeMillis());
            for (int i = 0; i < game.getPlayerCount(); ++i) {
              if (!game.getPlayer(i).equals(viewerId)) {
                game.addVictor(i);
              }
            }
          }

          @Override
          public void onComplete(Game game) {
            gameUpdated.resolve(game);
          }

          @Override
          public void onError(FirebaseError error, boolean committed) {
            errorService.error("Error with viewer '%s' resigning game '%s'. %s", viewerId, gameId,
                error);
            gameUpdated.fail(error.toException());
          }
        }
    );
  }
}
