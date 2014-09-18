package com.tinlib.services;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import com.tinlib.analytics.AnalyticsService;
import com.tinlib.beget.EntityMutator;
import com.tinlib.convey.Bus;
import com.tinlib.convey.Subscriber3;
import com.tinlib.core.TinKeys;
import com.tinlib.defer.Deferred;
import com.tinlib.defer.Deferreds;
import com.tinlib.defer.Promise;
import com.tinlib.defer.Promises;
import com.tinlib.error.ErrorService;
import com.tinlib.generated.Game;
import com.tinlib.infuse.Injector;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class FacebookService {
  private final Bus bus;
  private final Firebase firebase;
  private final ErrorService errorService;
  private final AnalyticsService analyticsService;
  private final ViewerService viewerService;
  private final AtomicInteger numGameMigrations = new AtomicInteger();

  public FacebookService(Injector injector) {
    bus = injector.get(Bus.class);
    firebase = injector.get(Firebase.class);
    errorService = injector.get(ErrorService.class);
    analyticsService = injector.get(AnalyticsService.class);
    viewerService = injector.get(ViewerService.class);
  }

  public Promise<Void> upgradeAccountToFacebook(final String facebookId) {
    final Deferred<Void> gamesMigrated = Deferreds.newDeferred();
    final Deferred<Void> actionsMigrated = Deferreds.newDeferred();

    bus.once(TinKeys.VIEWER_ID, TinKeys.FIREBASE_REFERENCES, TinKeys.GAME_LIST,
        new Subscriber3<String, FirebaseReferences, GameList>() {
      @Override
      public void onMessage(String viewerId, FirebaseReferences references, GameList gameList) {
        migrateGames(gamesMigrated, viewerId, facebookId, gameList, references);
        migrateActions(actionsMigrated, facebookId, references);
      }
    });

    return Promises.awaitVoid(gamesMigrated, actionsMigrated).then(new Runnable() {
      @Override
      public void run() {
        analyticsService.trackEvent("upgradeAccountToFacebook",
            ImmutableMap.of("facebookId", facebookId));
        viewerService.setViewerFacebookId(facebookId);
      }
    });
  }

  private void migrateGames(Deferred<Void> key, String oldViewerId, String facebookId,
      GameList gameList, FirebaseReferences references) {
    Set<String> gameIds = allKnownGameIds(gameList);
    numGameMigrations.set(gameIds.size());
    for(String gameId : gameIds) {
      migrateGame(key, oldViewerId, facebookId, gameId, references);
    }
  }

  private Set<String> allKnownGameIds(GameList gameList) {
    gameList.getListLock().lock();
    try {
      Set<String> result = Sets.newHashSet();
      for (int section : GameList.allSections()) {
        for (int row = 0; row < gameList.gameCountForSection(section); ++row) {
          result.add(gameList.getGame(section, row).getId());
        }
      }
      return result;
    } finally {
      gameList.getListLock().unlock();
    }
  }

  private void migrateGame(final Deferred<Void> gamesMigrated, final String oldViewerId,
      final String newViewerId, final String gameId, FirebaseReferences references) {
    EntityMutator.mutateEntity(references.gameReference(gameId), Game.newDeserializer(),
        new EntityMutator.Mutation<Game, Game.Builder>() {
          @Override
          public void mutate(Game.Builder game) {
            for (int i = 0; i < game.getPlayerCount(); ++i) {
              if (game.getPlayer(i).equals(oldViewerId)) {
                game.setPlayer(i, newViewerId);
              }
            }
          }

          @Override
          public void onComplete(Game entity) {
            if (numGameMigrations.decrementAndGet() == 0) {
              gamesMigrated.resolve();
            }
          }

          @Override
          public void onError(FirebaseError error, boolean committed) {
            gamesMigrated.fail(errorService.error("Error migrating game '%s' from viewer '%s' to " +
                "viewer '%s'. %s", gameId, oldViewerId, newViewerId, error));
          }
        }
    );
  }

  private void migrateActions(final Deferred<Void> actionsMigrated, final String facebookId,
      FirebaseReferences references) {
    references.userGames().addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        Firebase newUserGames = FirebaseReferences.facebook(facebookId, firebase).userGames();
        newUserGames.setValue(dataSnapshot.getValue(), new Firebase.CompletionListener() {
          @Override
          public void onComplete(FirebaseError firebaseError, Firebase firebase) {
            if (firebaseError != null) {
              actionsMigrated.fail(errorService.error("Error setting the userGames value for " +
                  "viewer '%s'. %s", facebookId, firebaseError));
            } else {
              actionsMigrated.resolve();
            }
          }
        });
      }

      @Override
      public void onCancelled(FirebaseError firebaseError) {
        actionsMigrated.fail(errorService.error("Listener for viewer '%s' user games cancelled " +
            "when migrating actions. %s", facebookId, firebaseError));
      }
    });
  }
}
