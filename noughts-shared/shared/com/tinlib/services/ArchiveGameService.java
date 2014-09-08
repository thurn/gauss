package com.tinlib.services;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.common.collect.ImmutableMap;
import com.tinlib.analytics.AnalyticsService;
import com.tinlib.core.TinKeys;
import com.tinlib.core.TinKeys2;
import com.tinlib.error.ErrorService;
import com.tinlib.infuse.Injector;
import com.tinlib.convey.Bus;
import com.tinlib.convey.Subscriber1;

public class ArchiveGameService{
  private final Bus bus;
  private final ErrorService errorService;
  private final AnalyticsService analyticsService;

  public ArchiveGameService(Injector injector) {
    bus = injector.get(TinKeys2.BUS);
    errorService = injector.get(TinKeys2.ERROR_SERVICE);
    analyticsService = injector.get(TinKeys2.ANALYTICS_SERVICE);
  }

  public void archiveGame(final String gameId) {
    bus.once(TinKeys.FIREBASE_REFERENCES, new Subscriber1<FirebaseReferences>() {
      @Override
      public void onMessage(FirebaseReferences references) {
        references.userReferenceForGame(gameId).removeValue(new Firebase.CompletionListener() {
          @Override
          public void onComplete(FirebaseError firebaseError, Firebase firebase) {
            analyticsService.trackEvent("archiveGame", ImmutableMap.of("gameId", gameId));
            if (firebaseError != null) {
              errorService.error("Error archiving game '%s'. %s", gameId, firebaseError);
            } else {
              bus.post(TinKeys.ARCHIVE_GAME_COMPLETED, gameId);
            }
          }
        });
      }
    });
  }
}
