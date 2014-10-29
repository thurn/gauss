package com.tinlib.services;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.tinlib.analytics.AnalyticsService;
import com.tinlib.convey.Bus;
import com.tinlib.convey.Subscriber1;
import com.tinlib.convey.Subscriber3;
import com.tinlib.core.TinKeys;
import com.tinlib.defer.*;
import com.tinlib.error.ErrorService;
import com.tinlib.generated.Game;
import com.tinlib.generated.Profile;
import com.tinlib.infuse.Injector;
import com.tinlib.push.PushNotificationService;
import com.tinlib.util.Actions;
import com.tinlib.util.ListUtil;
import com.tinlib.util.Profiles;
import com.tinlib.validator.JoinGameValidatorService;

public class JoinGameService {
  private final Bus bus;
  private final ErrorService errorService;
  private final CurrentGameListener currentGameListener;
  private final AnalyticsService analyticsService;
  private final PushNotificationService pushNotificationService;
  private final JoinGameValidatorService joinGameValidatorService;
  private final GameMutator gameMutator;

  public JoinGameService(Injector injector) {
    bus = injector.get(Bus.class);
    errorService = injector.get(ErrorService.class);
    currentGameListener = injector.get(CurrentGameListener.class);
    analyticsService = injector.get(AnalyticsService.class);
    pushNotificationService = injector.get(PushNotificationService.class);
    joinGameValidatorService = injector.get(JoinGameValidatorService.class);
    gameMutator = injector.get(GameMutator.class);
  }

  public Promise<Game> joinGame(final int playerNumber, final String gameId,
      final Optional<Profile> profile) {
    final Deferred<Game> playerAdded = Deferreds.newDeferred();
    final Deferred<Void> actionAdded = Deferreds.newDeferred();

    currentGameListener.loadGame(gameId);
    bus.once(TinKeys.VIEWER_ID, TinKeys.FIREBASE_REFERENCES, TinKeys.CURRENT_GAME,
        new Subscriber3<String, FirebaseReferences, Game>() {
      @Override
      public void onMessage(final String viewerId, FirebaseReferences references, Game game) {
        if (!joinGameValidatorService.canJoinGame(viewerId, game)) {
          Deferreds.failAll(errorService.error("Viewer '%s' can't join game '%s'", viewerId,
              gameId), playerAdded, actionAdded);
          return;
        }

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
            playerAdded.resolve(game);
          }

          @Override
          public void onError(String viewerId, FirebaseError error) {
            playerAdded.fail(errorService.error("Error with viewer '%s' joining game '%s'. %s",
                viewerId, gameId, error));
          }
        });

        references.currentActionReferenceForGame(gameId).setValue(
            Actions.newEmptyAction(gameId).serialize(), new Firebase.CompletionListener() {
          @Override
          public void onComplete(FirebaseError firebaseError, Firebase firebase) {
            if (firebaseError != null) {
              errorService.error("Error with viewer '%s' adding current action to game '%s'. %s",
                  viewerId, gameId, firebaseError);
              actionAdded.fail(firebaseError.toException());
            } else {
              actionAdded.resolve();
            }
          }
        });
      }
    });

    Promise<Pair<Game, Void>> prom = Promises.awaitPair(playerAdded, actionAdded);
    Promise<Game> result = prom.then(
        new Function<Pair<Game, Void>, Promise<Game>>() {
      @Override
      public Promise<Game> apply(Pair<Game, Void> pair) {
        analyticsService.trackEvent("joinGame", ImmutableMap.of(
            "playerNumber", playerNumber + "",
            "gameId", gameId,
            "profile", profile.toString()));
        pushNotificationService.registerForPushNotifications(gameId, playerNumber);
        return Deferreds.newResolvedDeferred(pair.getFirst());
      }
    });
    return result;
  }

  public Promise<Game> joinGameFromRequestId(final int playerNumber, final String requestId,
      final Optional<Profile> profile) {
    final Deferred<Game> result = Deferreds.newDeferred();
    bus.once(TinKeys.FIREBASE_REFERENCES, new Subscriber1<FirebaseReferences>() {
      @Override
      public void onMessage(FirebaseReferences references) {
        references.requestReference(requestId).addListenerForSingleValueEvent(
            new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
            result.chainFrom(joinGame(playerNumber, (String) dataSnapshot.getValue(), profile));
          }

          @Override
          public void onCancelled(FirebaseError firebaseError) {
            result.fail(errorService.error("Error loading game ID for request ID '%s'. %s",
                requestId, firebaseError));
          }
        });
      }
    });
    return result;
  }
}
