package com.tinlib.shared;

import com.firebase.client.Firebase;
import com.firebase.client.Firebase.CompletionListener;
import com.firebase.client.FirebaseError;
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
import com.tinlib.message.Subscriber2;
import com.tinlib.message.Subscriber3;
import com.tinlib.time.TimeService;

import java.util.List;

public class NewGameService {
  private static final String GAME_VALUE_SET = "NewGameService#GAME_VALUE_SET";
  private static final String ACTION_VALUE_SET = "NewGameService#ACTION_VALUE_SET";
  private static final String REQUEST_ID_SET = "NewGameService#REQUEST_ID_SET";

  private final Bus bus;
  private final ErrorService errorService;
  private final AnalyticsService analyticsService;
  private final TimeService timeService;
  private final JoinGameService joinGameService;

  public NewGameService(Injector injector) {
    bus = injector.get(TinKeys.BUS);
    errorService = injector.get(TinKeys.ERROR_SERVICE);
    analyticsService = injector.get(TinKeys.ANALYTICS_SERVICE);
    timeService = injector.get(TinKeys.TIME_SERVICE);
    joinGameService = injector.get(TinKeys.JOIN_GAME_SERVICE);
  }

  public void newGame(List<String> players, List<Profile> profiles, String gameId,
      Optional<String> requestId) {
    makeNewGame(players, profiles, gameId, requestId, false /* localMultiplayer */);
  }

  public void newLocalMultiplayerGame(List<String> players, List<Profile> profiles,
      String gameId, Optional<String> requestId) {
    makeNewGame(players, profiles, gameId, requestId, true /* localMultiplayer*/);
  }

  private void makeNewGame(final List<String> players, final List<Profile> profiles,
      final String gameId, final Optional<String> requestId, final boolean localMultiplayer) {
    bus.await(TinMessages.VIEWER_ID, TinMessages.FIREBASE_REFERENCES,
        new Subscriber2<String, FirebaseReferences>() {
      @Override
      public void onMessage(String viewerId, final FirebaseReferences firebaseReferences) {
        final Game.Builder game = Game.newBuilder();
        game.setId(gameId);
        game.addAllPlayer(players);
        game.addAllProfile(profiles);
        game.setIsLocalMultiplayer(localMultiplayer);
        game.setCurrentPlayerNumber(0);
        game.setLastModified(timeService.currentTimeMillis());
        game.setIsGameOver(false);

        firebaseReferences.gameReference(gameId).setValue(game.build().serialize(),
            new CompletionListener() {
          @Override
          public void onComplete(FirebaseError firebaseError, Firebase firebase) {
            if (firebaseError != null) {
              errorService.error("Error setting value of new game for game '%s'. %s", gameId,
                  firebaseError);
            } else {
              bus.produce(GAME_VALUE_SET);
            }
          }
        });

        firebaseReferences.currentActionReferenceForGame(gameId).setValue(
            Actions.newEmptyAction(gameId).serialize(), new CompletionListener() {
          @Override
          public void onComplete(FirebaseError firebaseError, Firebase firebase) {
            if (firebaseError != null) {
              errorService.error("Error setting new current action for game '%s'. $s", gameId,
                  firebaseError);
            } else {
              bus.produce(ACTION_VALUE_SET);
            }
          }
        });

        if (requestId.isPresent()) {
          firebaseReferences.requestReference(requestId.get()).setValue(gameId,
              new CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
              if (firebaseError != null) {
                errorService.error("Error associating request ID '%s' with game '%s'. %s",
                    requestId, gameId, firebaseError);
              } else {
                bus.produce(REQUEST_ID_SET);
              }
            }
          });
        } else {
          bus.produce(REQUEST_ID_SET);
        }

        bus.await(GAME_VALUE_SET, ACTION_VALUE_SET, REQUEST_ID_SET,
            new Subscriber3<Object, Object, Object>() {
          @Override
          public void onMessage(Object v1, Object v2, Object v3) {
            bus.invalidate(GAME_VALUE_SET);
            bus.invalidate(ACTION_VALUE_SET);
            bus.invalidate(REQUEST_ID_SET);

            analyticsService.trackEvent("makeNewGame", ImmutableMap.of(
                "players", players.toString(), "profiles", profiles.toString(),
                "gameId", gameId, "localMultiplayer", localMultiplayer + ""));
            joinGameService.joinGame(gameId, Optional.<Profile>absent());
            bus.produce(TinMessages.GAME_CREATED, game.build());
          }
        });
      }
    });
  }
}