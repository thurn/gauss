package com.tinlib.shared;

import com.tinlib.generated.Game;
import com.tinlib.generated.Profile;
import com.firebase.client.FirebaseError;
import com.google.common.collect.ImmutableMap;
import com.tinlib.analytics.AnalyticsService;
import com.tinlib.core.TinKeys;
import com.tinlib.core.TinMessages;
import com.tinlib.error.ErrorService;
import com.tinlib.inject.Injector;
import com.tinlib.message.Bus;
import com.tinlib.message.Subscriber2;

public class ProfileService implements Subscriber2<String, Game> {
  private final Bus bus;
  private final GameMutator gameMutator;
  private final AnalyticsService analyticsService;
  private final ErrorService errorService;

  public ProfileService(Injector injector) {
    bus = injector.get(TinKeys.BUS);
    gameMutator = injector.get(TinKeys.GAME_MUTATOR);
    analyticsService = injector.get(TinKeys.ANALYTICS_SERVICE);
    errorService = injector.get(TinKeys.ERROR_SERVICE);
    bus.await(TinMessages.VIEWER_ID, TinMessages.CURRENT_GAME, this);
  }

  public void setProfileForViewer(final String gameId, final Profile profile) {
    gameMutator.mutateGame(gameId, new GameMutator.GameMutation() {
      @Override
      public void mutate(String viewerId, Game.Builder game) {
        if (game.getIsLocalMultiplayer()) {
          throw errorService.die(
              "Can't setProfileForViewer for local multiplayer game %s", game);
        }
        int playerNumber = Games.playerNumberForPlayerId(game.build(), viewerId);
        game.setProfile(playerNumber, profile);
      }

      @Override
      public void onComplete(String viewerId, Game game) {
        analyticsService.trackEvent("Set viewer profile",
            ImmutableMap.of("gameId", gameId, "viewerId", viewerId, "profile", profile.toString()));
      }

      @Override
      public void onError(String viewerId, FirebaseError error) {
        errorService.error("Error setting profile for viewer %s. Error: %s", viewerId, error);
      }
    });
  }

  @Override
  public void onMessage(String viewerId, Game currentGame) {
    if (currentGame.getIsLocalMultiplayer() || currentGame.getIsGameOver() ||
        !currentGame.getPlayerList().contains(viewerId)) {
      return;
    }
    Profile profile = currentGame.getProfile(Games.playerNumberForPlayerId(currentGame, viewerId));
    // For now, a profile is defined as "completed" if it has an image selected.
    if (!profile.hasImageString()) {
      bus.invalidate(TinMessages.COMPLETED_VIEWER_PROFILE);
      bus.produce(TinMessages.PROFILE_REQUIRED, profile);
    } else {
      bus.invalidate(TinMessages.PROFILE_REQUIRED);
      bus.produce(TinMessages.COMPLETED_VIEWER_PROFILE, profile);
    }
  }
}
