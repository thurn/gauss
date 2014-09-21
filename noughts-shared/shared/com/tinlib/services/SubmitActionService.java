package com.tinlib.services;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.tinlib.analytics.AnalyticsService;
import com.tinlib.convey.Bus;
import com.tinlib.convey.Subscriber1;
import com.tinlib.core.TinKeys;
import com.tinlib.defer.Deferred;
import com.tinlib.defer.Deferreds;
import com.tinlib.defer.Promise;
import com.tinlib.error.ErrorService;
import com.tinlib.error.TinException;
import com.tinlib.generated.Action;
import com.tinlib.generated.Game;
import com.tinlib.infuse.Injector;
import com.tinlib.push.PushNotificationService;
import com.tinlib.time.TimeService;
import com.tinlib.util.Actions;
import com.tinlib.util.Games;
import com.tinlib.validator.ActionValidatorService;

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
    bus = injector.get(Bus.class);
    validatorService = injector.get(ActionValidatorService.class);
    errorService = injector.get(ErrorService.class);
    pushNotificationService = injector.get(PushNotificationService.class);
    analyticsService = injector.get(AnalyticsService.class);
    nextPlayerService = injector.get(NextPlayerService.class);
    gameOverService = injector.get(GameOverService.class);
    gameMutator = injector.get(GameMutator.class);
    timeService = injector.get(TimeService.class);
  }

  public Promise<Void> submitCurrentAction() {
    final Deferred<Void> result = Deferreds.newDeferred();
    bus.once(TinKeys.CURRENT_ACTION, new Subscriber1<Action>() {
      @Override
      public void onMessage(final Action currentAction) {
        result.chainFrom(submitAction(currentAction));
      }
    });
    return result;
  }

  public Promise<Void> submitAction(final Action action) {
    final Deferred<Void> result = Deferreds.newDeferred();

    gameMutator.mutateCurrentGame(new GameMutator.GameMutation() {
      @Override
      public void mutate(String viewerId, Game.Builder game) {
        Game currentGame = game.build();

        if (!validatorService.canSubmitAction(viewerId, currentGame, action)) {
          throw new TinException("Illegal action '%s'\nIn game '%s'", action,
              currentGame);
        }
        Optional<List<Integer>> victors =
            gameOverService.computeVictors(currentGame, action);

        game.addSubmittedAction(action.toBuilder().setIsSubmitted(true));
        game.setLastModified(timeService.currentTimeMillis());
        if (victors.isPresent()) {
          // Game over!
          game.clearCurrentPlayerNumber();
          game.addAllVictor(victors.get());
          game.setIsGameOver(true);
        } else {
          game.setCurrentPlayerNumber(
              nextPlayerService.nextPlayerNumber(currentGame, action));
        }
      }

      @Override
      public void onComplete(final String viewerId, FirebaseReferences references,
          final Game game) {
        Action emptyAction = Actions.newEmptyAction(game.getId());
        references.currentActionReferenceForGame(game.getId()).setValue(
            emptyAction.serialize(), new Firebase.CompletionListener() {
              @Override
              public void onComplete(FirebaseError error, Firebase firebase) {
                if (error != null) {
                  result.fail(errorService.error("Error updating current action for game. %s",
                      error));
                } else {
                  ImmutableMap<String, String> dimensions = ImmutableMap.of(
                      "viewerId", viewerId, "gameId", game.getId());
                  analyticsService.trackEvent("submitCurrentAction", dimensions);
                  sendNotificationOnActionSubmitted(viewerId, game);
                  result.resolve();
                }
              }
            }
        );
      }

      @Override
      public void onError(String viewerId, FirebaseError error) {
        result.fail(errorService.error("Error submitting action. %s.", error));
      }
    });

    return result;
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
