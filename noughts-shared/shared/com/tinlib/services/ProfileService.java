package com.tinlib.services;

import com.firebase.client.FirebaseError;
import com.google.common.collect.ImmutableMap;
import com.tinlib.analytics.AnalyticsService;
import com.tinlib.core.TinKeys;
import com.tinlib.core.TinKeys2;
import com.tinlib.error.ErrorService;
import com.tinlib.error.TinException;
import com.tinlib.generated.Game;
import com.tinlib.generated.Profile;
import com.tinlib.infuse.Injector;
import com.tinlib.convey.Bus;
import com.tinlib.util.Games;

public class ProfileService {
  private final Bus bus;
  private final GameMutator gameMutator;
  private final AnalyticsService analyticsService;
  private final ErrorService errorService;

  public ProfileService(Injector injector) {
    bus = injector.get(TinKeys2.BUS2);
    gameMutator = injector.get(TinKeys2.GAME_MUTATOR);
    analyticsService = injector.get(TinKeys2.ANALYTICS_SERVICE);
    errorService = injector.get(TinKeys2.ERROR_SERVICE);
  }

  public void setProfileForViewer(final Profile profile) {
    gameMutator.mutateCurrentGame(new GameMutator.GameMutation() {
      @Override
      public void mutate(String viewerId, Game.Builder game) {
        if (game.getIsLocalMultiplayer()) {
          throw new TinException(
              "Can't setProfileForViewer for local multiplayer game %s", game);
        }
        int playerNumber = Games.playerNumberForPlayerId(game.build(), viewerId);
        game.setProfile(playerNumber, profile);
      }

      @Override
      public void onComplete(String viewerId, FirebaseReferences references, Game game) {
        analyticsService.trackEvent("Set viewer profile",
            ImmutableMap.of("gameId", game.getId(), "viewerId", viewerId,
                "profile", profile.toString()));
        bus.post(TinKeys.SET_PROFILE_COMPLETED, profile);
      }

      @Override
      public void onError(String viewerId, FirebaseError error) {
        errorService.error("Error setting profile for viewer %s. Error: %s", viewerId, error);
      }
    });
  }
}
