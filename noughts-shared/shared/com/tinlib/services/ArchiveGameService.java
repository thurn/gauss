package com.tinlib.services;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.common.collect.ImmutableMap;
import com.tinlib.analytics.AnalyticsService;
import com.tinlib.core.TinKeys;
import com.tinlib.core.TinMessages;
import com.tinlib.error.ErrorService;
import com.tinlib.inject.Injector;
import com.tinlib.message.Bus;
import com.tinlib.message.Subscriber1;

public class ArchiveGameService {
  private final Bus bus;
  private final ErrorService errorService;
  private final AnalyticsService analyticsService;

  public ArchiveGameService(Injector injector) {
    bus = injector.get(TinKeys.BUS);
    errorService = injector.get(TinKeys.ERROR_SERVICE);
    analyticsService = injector.get(TinKeys.ANALYTICS_SERVICE);
  }

  public void archiveGame(final String gameId) {
    bus.once(TinMessages.FIREBASE_REFERENCES, new Subscriber1<FirebaseReferences>() {
      @Override
      public void onMessage(FirebaseReferences references) {
        references.userReferenceForGame(gameId).removeValue(new Firebase.CompletionListener() {
          @Override
          public void onComplete(FirebaseError firebaseError, Firebase firebase) {
            analyticsService.trackEvent("archiveGame", ImmutableMap.of("gameId", gameId));
            if (firebaseError != null) {
              errorService.error("Error archiving game '%s'. %s", gameId, firebaseError);
            } else {
              bus.produce(TinMessages.GAME_ARCHIVED, gameId);
            }
          }
        });
      }
    });
  }
}
