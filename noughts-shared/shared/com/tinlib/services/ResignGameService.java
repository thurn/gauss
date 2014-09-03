package com.tinlib.services;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.tinlib.analytics.AnalyticsService;
import com.tinlib.core.TinKeys;
import com.tinlib.core.TinMessages2;
import com.tinlib.entities.EntityMutator;
import com.tinlib.error.ErrorService;
import com.tinlib.error.TinException;
import com.tinlib.generated.Game;
import com.tinlib.inject.Injector;
import com.tinlib.message.*;
import com.tinlib.time.TimeService;
import com.tinlib.util.Actions;

public class ResignGameService {
  private final Bus2 bus;
  private final TimeService timeService;
  private final ErrorService errorService;
  private final AnalyticsService analyticsService;

  public ResignGameService(Injector injector) {
    bus = injector.get(TinKeys.BUS2);
    timeService = injector.get(TinKeys.TIME_SERVICE);
    errorService = injector.get(TinKeys.ERROR_SERVICE);
    analyticsService = injector.get(TinKeys.ANALYTICS_SERVICE);
  }

  public void resignGame(final String gameId) {
    final Key<Game> gameUpdated = Keys.createKey(Game.class, "gameUpdated");
    final Key<Void> actionCleared = Keys.createVoidKey("actionCleared");

    bus.once(gameUpdated, ImmutableList.<Key<?>>of(actionCleared), new Subscriber1<Game>() {
      @Override
      public void onMessage(Game game) {
        analyticsService.trackEvent("resignGame", ImmutableMap.of("gameId", gameId));
        bus.produce(TinMessages2.RESIGN_GAME_COMPLETED, game);
      }
    });

    bus.once(TinMessages2.VIEWER_ID, TinMessages2.FIREBASE_REFERENCES,
        new Subscriber2<String, FirebaseReferences>() {
      @Override
      public void onMessage(final String viewerId, FirebaseReferences references) {
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
            bus.post(gameUpdated, game);
          }

          @Override
          public void onError(FirebaseError error, boolean committed) {
            errorService.error("Error with viewer '%s' resigning game '%s'. %s", viewerId, gameId,
                error);
            bus.fail(gameUpdated);
          }
        });

        references.currentActionReferenceForGame(gameId).setValue(
            Actions.newEmptyAction(gameId).serialize(), new Firebase.CompletionListener() {
          @Override
          public void onComplete(FirebaseError firebaseError, Firebase firebase) {
            if (firebaseError != null) {
              errorService.error("Error with viewer '%s' resigning game '%s' - unable to clear " +
                  "current action. %s", viewerId, gameId, firebaseError);
              bus.fail(actionCleared);
            } else {
              bus.post(actionCleared);
            }
          }
        });
      }
    });
  }
}
