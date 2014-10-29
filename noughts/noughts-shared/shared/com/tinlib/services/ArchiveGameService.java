package com.tinlib.services;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.common.collect.ImmutableMap;
import com.tinlib.analytics.AnalyticsService;
import com.tinlib.convey.Bus;
import com.tinlib.convey.Subscriber1;
import com.tinlib.core.TinKeys;
import com.tinlib.defer.Deferred;
import com.tinlib.defer.Deferreds;
import com.tinlib.defer.Promise;
import com.tinlib.error.ErrorService;
import com.tinlib.infuse.Injector;

public class ArchiveGameService{
  private final Bus bus;
  private final ErrorService errorService;
  private final AnalyticsService analyticsService;

  public ArchiveGameService(Injector injector) {
    bus = injector.get(Bus.class);
    errorService = injector.get(ErrorService.class);
    analyticsService = injector.get(AnalyticsService.class);
  }

  public Promise<Void> archiveGame(final String gameId) {
    final Deferred<Void> result = Deferreds.newDeferred();
    bus.once(TinKeys.FIREBASE_REFERENCES, new Subscriber1<FirebaseReferences>() {
      @Override
      public void onMessage(FirebaseReferences references) {
        references.userReferenceForGame(gameId).removeValue(new Firebase.CompletionListener() {
          @Override
          public void onComplete(FirebaseError firebaseError, Firebase firebase) {
            analyticsService.trackEvent("archiveGame", ImmutableMap.of("gameId", gameId));
            if (firebaseError != null) {
              result.fail(errorService.error("Error archiving game '%s'. %s", gameId,
                  firebaseError));
            } else {
              result.resolve();
            }
          }
        });
      }
    });
    return result;
  }
}
