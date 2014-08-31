package com.tinlib.services;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.common.collect.ImmutableMap;
import com.tinlib.analytics.AnalyticsService;
import com.tinlib.core.TinKeys;
import com.tinlib.core.TinMessages2;
import com.tinlib.error.ErrorService;
import com.tinlib.inject.Injector;
import com.tinlib.message.Bus2;
import com.tinlib.message.Subscriber1;

public class ArchiveGameService{
  private final Bus2 bus;
  private final ErrorService errorService;
  private final AnalyticsService analyticsService;

  public ArchiveGameService(Injector injector) {
    bus = injector.get(TinKeys.BUS2);
    errorService = injector.get(TinKeys.ERROR_SERVICE);
    analyticsService = injector.get(TinKeys.ANALYTICS_SERVICE);
  }

  public void archiveGame(final String gameId) {
    bus.once(new Subscriber1<FirebaseReferences>() {
      @Override
      public void onMessage(FirebaseReferences references) {
        references.userReferenceForGame(gameId).removeValue(new Firebase.CompletionListener() {
          @Override
          public void onComplete(FirebaseError firebaseError, Firebase firebase) {
            analyticsService.trackEvent("archiveGame", ImmutableMap.of("gameId", gameId));
            if (firebaseError != null) {
              errorService.error("Error archiving game '%s'. %s", gameId, firebaseError);
            } else {
              bus.produce(TinMessages2.ARCHIVE_GAME_COMPLETED, gameId);
            }
          }
        });
      }
    }, TinMessages2.FIREBASE_REFERENCES);
  }
}
