package com.tinlib.services;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.tinlib.analytics.AnalyticsService;
import com.tinlib.core.TinKeys;
import com.tinlib.core.TinMessages;
import com.tinlib.error.ErrorService;
import com.tinlib.generated.Game;
import com.tinlib.generated.Profile;
import com.tinlib.inject.Injector;
import com.tinlib.message.Bus;
import com.tinlib.message.Subscriber1;
import com.tinlib.message.Subscriber2;
import com.tinlib.message.Subscriber3;
import com.tinlib.push.PushNotificationService;
import com.tinlib.util.Actions;
import com.tinlib.util.ListUtil;
import com.tinlib.util.Profiles;
import com.tinlib.validator.JoinGameValidatorService;

public class JoinGameService {
  private static final String PLAYER_ADDED = "JoinGameService.PLAYER_ADDED";
  private static final String ACTION_ADDED = "JoinGameService.ACTION_ADDED";

  private final Bus bus;
  private final ErrorService errorService;
  private final CurrentGameListener currentGameListener;
  private final AnalyticsService analyticsService;
  private final PushNotificationService pushNotificationService;
  private final JoinGameValidatorService joinGameValidatorService;
  private final GameMutator gameMutator;

  public JoinGameService(Injector injector) {
    bus = injector.get(TinKeys.BUS);
    errorService = injector.get(TinKeys.ERROR_SERVICE);
    currentGameListener = injector.get(TinKeys.CURRENT_GAME_SERVICE);
    analyticsService = injector.get(TinKeys.ANALYTICS_SERVICE);
    pushNotificationService = injector.get(TinKeys.PUSH_NOTIFICATION_SERVICE);
    joinGameValidatorService = injector.get(TinKeys.JOIN_GAME_VALIDATOR_SERVICE);
    gameMutator = injector.get(TinKeys.GAME_MUTATOR);
  }

  public void joinGame(final int playerNumber, final String gameId,
      final Optional<Profile> profile) {
    currentGameListener.loadGame(gameId);
    bus.once(TinMessages.VIEWER_ID, TinMessages.FIREBASE_REFERENCES, TinMessages.CURRENT_GAME,
        new Subscriber3<String, FirebaseReferences, Game>() {
      @Override
      public void onMessage(final String viewerId, FirebaseReferences references, Game game) {
        if (!joinGameValidatorService.canJoinGame(viewerId, game)) {
          errorService.error("Viewer '%s' can't join game '%s'", viewerId, gameId);
          return;
        }

        bus.once(PLAYER_ADDED, ACTION_ADDED, new Subscriber2<Game, Boolean>() {
          @Override
          public void onMessage(Game game, Boolean actionAdded) {
            bus.invalidate(PLAYER_ADDED);
            bus.invalidate(ACTION_ADDED);

            analyticsService.trackEvent("joinGame", ImmutableMap.of(
                "playerNumber", playerNumber + "",
                "gameId", gameId,
                "profile", profile.toString()));
            pushNotificationService.registerForPushNotifications(gameId, playerNumber);
            bus.produce(TinMessages.JOIN_GAME_COMPLETED, game);
          }
        });

        gameMutator.mutateCurrentGame(new GameMutator.GameMutation() {
          @Override
          public void mutate(String viewerId, Game.Builder game) {
            ListUtil.addOrSet(game.getPlayerList(), playerNumber, viewerId, "");
            if (profile.isPresent()) {
              ListUtil.addOrSet(game.getProfileList(), playerNumber, profile.get(),
                  Profiles.newEmptyProfile());
            }
          }

          @Override
          public void onComplete(String viewerId, FirebaseReferences references, Game game) {
            bus.produce(PLAYER_ADDED, game);
          }

          @Override
          public void onError(String viewerId, FirebaseError error) {
            errorService.error("Error with viewer '%s' joining game '%s'. %s", viewerId, gameId,
                error);
          }
        });

        references.currentActionReferenceForGame(gameId).setValue(
            Actions.newEmptyAction(gameId).serialize(), new Firebase.CompletionListener() {
          @Override
          public void onComplete(FirebaseError firebaseError, Firebase firebase) {
            if (firebaseError != null) {
              errorService.error("Error with viewer '%s' adding current action to game '%s'. %s",
                  viewerId, gameId, firebaseError);
            } else {
              bus.produce(ACTION_ADDED);
            }
          }
        });
      }
    });
  }

  public void joinGameFromRequestId(final int playerNumber, final String requestId,
      final Optional<Profile> profile) {
    bus.once(TinMessages.FIREBASE_REFERENCES, new Subscriber1<FirebaseReferences>() {
      @Override
      public void onMessage(FirebaseReferences references) {
        references.requestReference(requestId).addListenerForSingleValueEvent(
            new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
            joinGame(playerNumber, (String)dataSnapshot.getValue(), profile);
          }

          @Override
          public void onCancelled(FirebaseError firebaseError) {
            errorService.error("Error loading game ID for request ID '%s'. %s", requestId,
                firebaseError);
          }
        });
      }
    });
  }
}
