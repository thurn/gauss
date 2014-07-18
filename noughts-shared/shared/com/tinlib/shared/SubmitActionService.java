package com.tinlib.shared;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.tinlib.action.validator.ActionValidatorService;
import com.tinlib.analytics.AnalyticsService;
import com.tinlib.core.TinKeys;
import com.tinlib.core.TinMessages;
import com.tinlib.error.ErrorService;
import com.tinlib.generated.Action;
import com.tinlib.generated.Game;
import com.tinlib.inject.Injector;
import com.tinlib.message.Bus;
import com.tinlib.message.Subscriber4;
import com.tinlib.push.PushNotificationService;
import com.tinlib.time.TimeService;

import java.util.List;

public class SubmitActionService {
  private final Bus bus;
  private final ActionValidatorService validatorService;
  private final ErrorService errorService;
  private final PushNotificationService pushNotificationService;
  private final AnalyticsService analyticsService;
  private final NextPlayerService nextPlayerService;
  private final GameOverService gameOverService;
  private final GameMutator gameMutator;
  private final TimeService timeService;

  public SubmitActionService(Injector injector) {
    bus = injector.get(TinKeys.BUS);
    validatorService = injector.get(TinKeys.ACTION_VALIDATOR_SERVICE);
    errorService = injector.get(TinKeys.ERROR_SERVICE);
    pushNotificationService = injector.get(TinKeys.PUSH_NOTIFICATION_SERVICE);
    analyticsService = injector.get(TinKeys.ANALYTICS_SERVICE);
    nextPlayerService = injector.get(TinKeys.NEXT_PLAYER_SERVICE);
    gameOverService = injector.get(TinKeys.GAME_OVER_SERVICE);
    gameMutator = injector.get(TinKeys.GAME_MUTATOR);
    timeService = injector.get(TinKeys.TIME_SERVICE);
  }

  public void submitCurrentAction() {
    bus.once(TinMessages.VIEWER_ID, TinMessages.FIREBASE_REFERENCES, TinMessages.CURRENT_GAME,
        TinMessages.CURRENT_ACTION, new Subscriber4<String, FirebaseReferences, Game, Action>() {
      @Override
      public void onMessage(String viewerId, final FirebaseReferences firebaseReferences,
          Game currentGame, final Action currentAction) {
        if (!validatorService.canSubmitAction(viewerId, currentGame, currentAction)) {
          throw errorService.die("Illegal action %s\nIn game %s", currentGame, currentAction);
        }
        final Optional<List<Integer>> victors = gameOverService.computeVictors(currentGame,
            currentAction);
        final int newPlayerNumber = nextPlayerService.nextPlayerNumber(currentGame, currentAction);

        gameMutator.mutateGame(currentGame.getId(), new GameMutator.GameMutation() {
          @Override
          public void mutate(String viewerId, Game.Builder game) {
            game.addSubmittedAction(currentAction.toBuilder().setIsSubmitted(true));
            game.setLastModified(timeService.currentTimeMillis());
            if (victors.isPresent()) {
              // Game over!
              game.clearCurrentPlayerNumber();
              game.addAllVictor(victors.get());
              game.setIsGameOver(true);
            } else {
              game.setCurrentPlayerNumber(newPlayerNumber);
            }
          }

          @Override
          public void onComplete(final String viewerId, final Game game) {
            Action emptyAction = Actions.newEmptyAction(game.getId());
            firebaseReferences.currentActionReferenceForGame(game.getId()).setValue(
                emptyAction.serialize(), new Firebase.CompletionListener() {
              @Override
              public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                ImmutableMap<String, String> dimensions = ImmutableMap.of(
                    "viewerId", viewerId, "game", game.toString());
                analyticsService.trackEvent("submitCurrentAction", dimensions);
                sendNotificationOnActionSubmitted(viewerId, game);
                bus.produce(TinMessages.ACTION_SUBMITTED);
              }
            });
          }

          @Override
          public void onError(String viewerId, FirebaseError error) {
            errorService.error("Error submitting action %s.", error);
          }
        });
      }
    });
  }

  private void sendNotificationOnActionSubmitted(String viewerId, Game game) {
    if (!game.getIsLocalMultiplayer() && game.getPlayerCount() > 1) {
      String opponentId = game.getPlayer(Games.opponentPlayerNumber(game, viewerId));
      if (game.getIsGameOver()) {
        int viewerPlayerNumber = Games.playerNumberForPlayerId(game, viewerId);
        for (int i = 0; i < game.getPlayerCount(); ++i) {
          if (i != viewerPlayerNumber) {
            String message = "Game " + Games.vsString(game, opponentId) + ": Game over";
            pushNotificationService.sendPushNotification(game.getId(), i, message);
          }
        }
      } else {
        String message = "Game " + Games.vsString(game, opponentId) + ": It's your turn!";
        pushNotificationService.sendPushNotification(game.getId(), game.getCurrentPlayerNumber(),
            message);
      }
    }
  }
}
