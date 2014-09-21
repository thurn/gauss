package com.tinlib.services;

import com.firebase.client.FirebaseError;
import com.tinlib.analytics.AnalyticsService;
import com.tinlib.convey.Bus;
import com.tinlib.core.TinKeys;
import com.tinlib.defer.Deferred;
import com.tinlib.defer.Deferreds;
import com.tinlib.defer.Promise;
import com.tinlib.error.ErrorService;
import com.tinlib.error.TinException;
import com.tinlib.generated.Action;
import com.tinlib.generated.Command;
import com.tinlib.generated.Game;
import com.tinlib.infuse.Injector;
import com.tinlib.time.LastModifiedService;
import com.tinlib.util.Games;

public class UndoService {
  private final ErrorService errorService;
  private final AnalyticsService analyticsService;
  private final GameMutator gameMutator;
  private final LastModifiedService lastModifiedService;

  public UndoService(Injector injector) {
    errorService = injector.get(ErrorService.class);
    analyticsService = injector.get(AnalyticsService.class);
    gameMutator = injector.get(GameMutator.class);
    lastModifiedService = injector.get(LastModifiedService.class);
  }

  public boolean canUndo(String viewerId, Game game, Action currentAction) {
    if (!Games.isCurrentPlayer(viewerId, game)) return false;
    return currentAction.getCommandCount() > 0;
  }

  public boolean canRedo(String viewerId, Game game, Action currentAction) {
    if (!Games.isCurrentPlayer(viewerId, game)) return false;
    return currentAction.getFutureCommandCount() > 0;
  }

  public Promise<Command> undo() {
    final Deferred<Command> result = Deferreds.newDeferred();
    gameMutator.mutateCurrentAction(new GameMutator.ActionMutation() {
      @Override
      public void mutate(String viewerId, Action.Builder action, Game currentGame) {
        if (!canUndo(viewerId, currentGame, action.build())) {
          throw new TinException("Can't undo in action '%s'", action);
        }
        Command command = action.getCommandList().remove(action.getCommandCount() - 1);
        action.addFutureCommand(command);
      }

      @Override
      public void onComplete(String viewerId, FirebaseReferences references, Action action,
          Game currentGame) {
        analyticsService.trackEvent("Undo");
        lastModifiedService.updateLastModified(action.getGameId());
        result.resolve(action.getFutureCommand(action.getFutureCommandCount() - 1));
      }

      @Override
      public void onError(String viewerId, FirebaseError error) {
        result.fail(errorService.error("Error undoing command. %s", error));
      }
    });

    return result;
  }

  public Promise<Command> redo() {
    final Deferred<Command> result = Deferreds.newDeferred();
    gameMutator.mutateCurrentAction(new GameMutator.ActionMutation() {
      @Override
      public void mutate(String viewerId, Action.Builder action, Game currentGame) {
        if (!canRedo(viewerId, currentGame, action.build())) {
          throw new TinException("Can't redo in action '%s'", action);
        }
        Command command = action.getFutureCommandList().remove(action.getFutureCommandCount() - 1);
        action.addCommand(command);
      }

      @Override
      public void onComplete(String viewerId, FirebaseReferences references, Action action,
                             Game currentGame) {
        analyticsService.trackEvent("Redo");
        lastModifiedService.updateLastModified(action.getGameId());
        result.resolve(action.getCommand(action.getCommandCount() - 1));
      }

      @Override
      public void onError(String viewerId, FirebaseError error) {
        result.fail(errorService.error("Error undoing command. %s", error));
      }
    });

    return result;
  }
}
