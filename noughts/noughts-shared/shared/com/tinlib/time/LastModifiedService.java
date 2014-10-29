package com.tinlib.time;

import com.firebase.client.FirebaseError;
import com.tinlib.error.ErrorService;
import com.tinlib.generated.Game;
import com.tinlib.infuse.Injector;
import com.tinlib.services.FirebaseReferences;
import com.tinlib.services.GameMutator;

public class LastModifiedService {
  private final GameMutator gameMutator;
  private final TimeService timeService;
  private final ErrorService errorService;

  public LastModifiedService(Injector injector) {
    gameMutator = injector.get(GameMutator.class);
    timeService = injector.get(TimeService.class);
    errorService = injector.get(ErrorService.class);
  }

  public void updateLastModified(final String gameId) {
    gameMutator.mutateCurrentGame(new GameMutator.GameMutation() {
      @Override
      public void mutate(String viewerId, Game.Builder game) {
        game.setLastModified(timeService.currentTimeMillis());
      }

      @Override
      public void onComplete(String viewerId, FirebaseReferences references, Game game) {
      }

      @Override
      public void onError(String viewerId, FirebaseError error) {
        errorService.error("Error updating last modified for game %s. %s", gameId, error);
      }
    });
  }
}
