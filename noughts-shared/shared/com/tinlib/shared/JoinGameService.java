package com.tinlib.shared;

import com.firebase.client.FirebaseError;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.tinlib.analytics.AnalyticsService;
import com.tinlib.core.TinKeys;
import com.tinlib.core.TinMessages;
import com.tinlib.error.ErrorService;
import com.tinlib.error.TinException;
import com.tinlib.generated.Action;
import com.tinlib.generated.Game;
import com.tinlib.generated.Profile;
import com.tinlib.inject.Injector;
import com.tinlib.message.Bus;
import com.tinlib.message.Subscriber1;
import com.tinlib.message.Subscriber2;
import com.tinlib.push.PushNotificationHandler;
import com.tinlib.push.PushNotificationService;
import com.tinlib.validator.JoinGameValidatorService;

public class JoinGameService {
  private final Bus bus;
  private final ErrorService errorService;
  private final CurrentGameService currentGameService;
  private final AnalyticsService analyticsService;
  private final PushNotificationService pushNotificationService;
  private final JoinGameValidatorService joinGameValidatorService;
  private final GameMutator gameMutator;

  public JoinGameService(Injector injector) {
    bus = injector.get(TinKeys.BUS);
    errorService = injector.get(TinKeys.ERROR_SERVICE);
    currentGameService = injector.get(TinKeys.CURRENT_GAME_SERVICE);
    analyticsService = injector.get(TinKeys.ANALYTICS_SERVICE);
    pushNotificationService = injector.get(TinKeys.PUSH_NOTIFICATION_SERVICE);
    joinGameValidatorService = injector.get(TinKeys.JOIN_GAME_VALIDATOR_SERVICE);
    gameMutator = injector.get(TinKeys.GAME_MUTATOR);
  }

  public void joinGame(final int playerNumber, final String gameId,
      final Optional<Profile> profile) {
    currentGameService.loadGame(gameId);
    bus.once(TinMessages.VIEWER_ID, TinMessages.CURRENT_GAME, new Subscriber2<String, Game>() {
      @Override
      public void onMessage(String viewerId, Game currentGame) {
        if (!joinGameValidatorService.canJoinGame(viewerId, currentGame)) {
          throw new TinException("Viewer '%s' can't join game '%s'", viewerId, gameId);
        }
        gameMutator.mutateCurrentGame(new GameMutator.GameMutation() {
          @Override
          public void mutate(String viewerId, Action currentAction, Game.Builder game) {
            game.setPlayer(playerNumber, viewerId);
            if (profile.isPresent()) {
              game.setProfile(playerNumber, profile.get());
            }
          }

          @Override
          public void onComplete(String viewerId, FirebaseReferences references, Action action,
              Game game) {
            analyticsService.trackEvent("joinGame", ImmutableMap.of(
                "playerNumber", playerNumber + "",
                "gameId", gameId,
                "profile", profile.toString()));
            pushNotificationService.registerForPushNotifications(gameId, playerNumber);
            bus.produce(TinMessages.GAME_JOINED, game);
          }

          @Override
          public void onError(String viewerId, FirebaseError error) {
            errorService.error("Error with viewer '%s' joining game '%s'. %s", viewerId, gameId,
                error);
          }
        });
      }
    });
  }

  public void joinGameFromRequestId(String gameId, Optional<Profile> profile) {

  }
}
