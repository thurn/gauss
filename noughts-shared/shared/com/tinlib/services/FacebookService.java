package com.tinlib.services;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import com.tinlib.analytics.AnalyticsService;
import com.tinlib.core.TinKeys;
import com.tinlib.core.TinKeys2;
import com.tinlib.entities.EntityMutator;
import com.tinlib.error.ErrorService;
import com.tinlib.generated.Game;
import com.tinlib.infuse.Injector;
import com.tinlib.convey.*;

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
    bus = injector.get(TinKeys2.BUS2);
    firebase = injector.get(TinKeys2.FIREBASE);
    errorService = injector.get(TinKeys2.ERROR_SERVICE);
    analyticsService = injector.get(TinKeys2.ANALYTICS_SERVICE);
    viewerService = injector.get(TinKeys2.VIEWER_SERVICE);
  }

  public void upgradeAccountToFacebook(final String facebookId) {
    final Key<Void> gamesMigrated = Keys.createVoidKey();
    final Key<Void> actionsMigrated = Keys.createVoidKey();

    bus.once(ImmutableList.<Key<?>>of(gamesMigrated, actionsMigrated), new Subscriber0() {
      @Override
      public void onMessage() {
        analyticsService.trackEvent("upgradeAccountToFacebook",
            ImmutableMap.of("facebookId", facebookId));
        viewerService.setViewerFacebookId(facebookId);
        bus.post(TinKeys.ACCOUNT_UPGRADE_COMPLETED);
      }
    });

    bus.once(TinKeys.VIEWER_ID, TinKeys.FIREBASE_REFERENCES, TinKeys.GAME_LIST,
        new Subscriber3<String, FirebaseReferences, GameList>() {
      @Override
      public void onMessage(String viewerId, FirebaseReferences references, GameList gameList) {
        migrateGames(gamesMigrated, viewerId, facebookId, gameList, references);
        migrateActions(actionsMigrated, facebookId, references);
      }
    });
  }

  private void migrateGames(Key<Void> key, String oldViewerId, String facebookId, GameList gameList,
      FirebaseReferences references) {
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

  private void migrateGame(final Key<Void> gamesMigrated, final String oldViewerId,
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
              bus.post(gamesMigrated);
            }
          }

          @Override
          public void onError(FirebaseError error, boolean committed) {
            errorService.error("Error migrating game '%s' from viewer '%s' to viewer '%s'. %s",
                gameId, oldViewerId, newViewerId, error);
            bus.fail(gamesMigrated);
          }
        }
    );
  }

  private void migrateActions(final Key<Void> actionsMigrated, final String facebookId,
      FirebaseReferences references) {
    references.userGames().addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        Firebase newUserGames = FirebaseReferences.facebook(facebookId, firebase).userGames();
        newUserGames.setValue(dataSnapshot.getValue(), new Firebase.CompletionListener() {
          @Override
          public void onComplete(FirebaseError firebaseError, Firebase firebase) {
            if (firebaseError != null) {
              errorService.error("Error setting the userGames value for viewer '%s'. %s",
                  facebookId, firebaseError);
              bus.fail(actionsMigrated);
            } else {
              bus.post(actionsMigrated);
            }
          }
        });
      }

      @Override
      public void onCancelled(FirebaseError firebaseError) {
        errorService.error("Listener for viewer '%s' user games cancelled when migrating " +
            "actions. %s", facebookId, firebaseError);
      }
    });
  }
}
