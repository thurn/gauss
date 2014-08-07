package com.tinlib.services;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import com.tinlib.analytics.AnalyticsService;
import com.tinlib.core.TinKeys;
import com.tinlib.core.TinMessages;
import com.tinlib.entities.EntityMutator;
import com.tinlib.error.ErrorService;
import com.tinlib.generated.Game;
import com.tinlib.inject.Injector;
import com.tinlib.message.Bus;
import com.tinlib.message.Subscriber2;
import com.tinlib.message.Subscriber3;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class FacebookService {
  private static final String GAMES_MIGRATED = "FacebookService.GAMES_MIGRATED";
  private static final String ACTIONS_MIGRATED = "FacebookService.ACTIONS_MIGRATED";

  private final Bus bus;
  private final Firebase firebase;
  private final ErrorService errorService;
  private final AnalyticsService analyticsService;
  private final ViewerService viewerService;
  private final AtomicInteger numGameMigrations = new AtomicInteger();

  public FacebookService(Injector injector) {
    bus = injector.get(TinKeys.BUS);
    firebase = injector.get(TinKeys.FIREBASE);
    errorService = injector.get(TinKeys.ERROR_SERVICE);
    analyticsService = injector.get(TinKeys.ANALYTICS_SERVICE);
    viewerService = injector.get(TinKeys.VIEWER_SERVICE);
  }

  public void upgradeAccountToFacebook(final String facebookId) {
    bus.once(GAMES_MIGRATED, ACTIONS_MIGRATED, new Subscriber2<Object, Object>() {
      @Override
      public void onMessage(Object v1, Object v2) {
        bus.invalidate(GAMES_MIGRATED);
        bus.invalidate(ACTIONS_MIGRATED);

        analyticsService.trackEvent("upgradeAccountToFacebook",
            ImmutableMap.of("facebookId", facebookId));
        viewerService.setViewerFacebookId(facebookId);
        bus.produce(TinMessages.ACCOUNT_UPGRADE_COMPLETED);
      }
    });

    bus.once(TinMessages.VIEWER_ID, TinMessages.FIREBASE_REFERENCES, TinMessages.GAME_LIST,
        new Subscriber3<String, FirebaseReferences, GameList>() {
      @Override
      public void onMessage(String viewerId, FirebaseReferences references, GameList gameList) {
        migrateGames(viewerId, facebookId, gameList, references);
        migrateActions(facebookId, references);
      }
    });
  }

  private void migrateGames(String oldViewerId, String facebookId, GameList gameList,
      FirebaseReferences references) {
    Set<String> gameIds = allKnownGameIds(gameList);
    numGameMigrations.set(gameIds.size());
    for(String gameId : gameIds) {
      migrateGame(oldViewerId, facebookId, gameId, references);
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

  private void migrateGame(final String oldViewerId, final String newViewerId, final String gameId,
      FirebaseReferences references) {
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
              bus.produce(GAMES_MIGRATED);
            }
          }

          @Override
          public void onError(FirebaseError error, boolean committed) {
            errorService.error("Error migrating game '%s' from viewer '%s' to viewer '%s'. %s",
                gameId, oldViewerId, newViewerId, error);
          }
        }
    );
  }

  private void migrateActions(final String facebookId, FirebaseReferences references) {
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
            } else {
              bus.produce(ACTIONS_MIGRATED);
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
