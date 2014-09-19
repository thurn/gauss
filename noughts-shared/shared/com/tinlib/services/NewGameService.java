package com.tinlib.services;

import com.firebase.client.Firebase;
import com.firebase.client.Firebase.CompletionListener;
import com.firebase.client.FirebaseError;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.tinlib.analytics.AnalyticsService;
import com.tinlib.convey.Bus;
import com.tinlib.convey.Subscriber2;
import com.tinlib.core.TinKeys;
import com.tinlib.defer.Deferred;
import com.tinlib.defer.Deferreds;
import com.tinlib.defer.Promise;
import com.tinlib.defer.Promises;
import com.tinlib.error.ErrorService;
import com.tinlib.generated.Game;
import com.tinlib.generated.Profile;
import com.tinlib.infuse.Injector;
import com.tinlib.time.TimeService;

import java.util.List;
import java.util.concurrent.Callable;

public class NewGameService {
  public class NewGameBuilder {
    private final String gameId;
    private final boolean localMultiplayer;
    private final List<String> players = Lists.newArrayList();
    private final List<Profile> profiles = Lists.newArrayList();
    private int playerNumber = 0;
    private Optional<String> requestId = Optional.absent();

    private NewGameBuilder(String gameId, boolean localMultiplayer) {
      this.gameId = gameId;
      this.localMultiplayer = localMultiplayer;
    }

    public NewGameBuilder addPlayers(ImmutableList<String> players) {
      this.players.addAll(players);
      return this;
    }

    public NewGameBuilder addProfiles(ImmutableList<Profile> profiles) {
      this.profiles.addAll(profiles);
      return this;
    }

    public NewGameBuilder setViewerPlayerNumber(int playerNumber) {
      this.playerNumber = playerNumber;
      return this;
    }

    public NewGameBuilder setRequestId(String requestId) {
      this.requestId = Optional.of(requestId);
      return this;
    }

    public Promise<Game> create() {
      return makeNewGame(playerNumber, players, profiles, gameId, requestId, localMultiplayer);
    }
  }

  private final Bus bus;
  private final ErrorService errorService;
  private final AnalyticsService analyticsService;
  private final TimeService timeService;
  private final JoinGameService joinGameService;

  public NewGameService(Injector injector) {
    bus = injector.get(Bus.class);
    errorService = injector.get(ErrorService.class);
    analyticsService = injector.get(AnalyticsService.class);
    timeService = injector.get(TimeService.class);
    joinGameService = injector.get(JoinGameService.class);
  }

  public NewGameBuilder newGameBuilder(String gameId) {
    return new NewGameBuilder(gameId, false /* localMultiplayer */);
  }

  public NewGameBuilder newLocalMultiplayerGameBuilder(String gameId) {
    return new NewGameBuilder(gameId, true /* localMultiplayer */);
  }

  private Promise<Game> makeNewGame(final int viewerPlayerNumber, final List<String> players,
      final List<Profile> profiles, final String gameId, final Optional<String> requestId,
      final boolean localMultiplayer) {
    final Deferred<Game> result = Deferreds.newDeferred();
    final Deferred<Void> gameValueSet = Deferreds.newDeferred();
    final Deferred<Void> requestIdSet = Deferreds.newDeferred();

    bus.once(TinKeys.VIEWER_ID, TinKeys.FIREBASE_REFERENCES,
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

        setGameValue(gameValueSet, firebaseReferences, game.build());
        setRequestId(requestIdSet, firebaseReferences, requestId, gameId);
      }
    });

    return Promises.awaitVoid(gameValueSet, requestIdSet).then(new Callable<Promise<Game>>() {
      @Override
      public Promise<Game> call() {
        analyticsService.trackEvent("makeNewGame", ImmutableMap.of(
            "players", players.toString(), "profiles", profiles.toString(),
            "gameId", gameId, "localMultiplayer", localMultiplayer + ""));
        Promise<Game> p = joinGameService.joinGame(viewerPlayerNumber, gameId, Optional.<Profile>absent());
        return p;
      }
    });
  }

  private void setRequestId(final Deferred<Void> requestIdSet,
      FirebaseReferences firebaseReferences, final Optional<String> requestId,
      final String gameId) {
    if (requestId.isPresent()) {
      firebaseReferences.requestReference(requestId.get()).setValue(gameId,
          new CompletionListener() {
        @Override
        public void onComplete(FirebaseError firebaseError, Firebase firebase) {
          if (firebaseError != null) {
            requestIdSet.fail(errorService.error("Error associating request ID '%s' with game " +
                "'%s'. %s", requestId.get(), gameId, firebaseError));
          } else {
            requestIdSet.resolve();
          }
        }
      });
    } else {
      requestIdSet.resolve();
    }
  }

  private void setGameValue(final Deferred<Void> gameValueSet,
      FirebaseReferences firebaseReferences, final Game game) {
    firebaseReferences.gameReference(game.getId()).setValue(game.serialize(),
        new CompletionListener() {
      @Override
      public void onComplete(FirebaseError firebaseError, Firebase firebase) {
        if (firebaseError != null) {
          gameValueSet.fail(errorService.error("Error setting value of new game for game " +
              "'%s'. %s", game.getId(), firebaseError));
        } else {
          gameValueSet.resolve();
        }
      }
    });
  }
}