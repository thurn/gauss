package com.tinlib.validator;

import com.tinlib.generated.Action;
import com.tinlib.generated.Command;
import com.tinlib.generated.Game;
import com.tinlib.infuse.Injector;

import java.util.Set;

public class ActionValidatorService {
  private final Set<ActionValidator> actionValidators;

  public ActionValidatorService(Injector injector) {
    actionValidators = injector.getMultiple(ActionValidator.class);
  }

  public boolean canAddCommand(String viewerId, Game game, Action currentAction, Command command) {
    for (ActionValidator validator : actionValidators) {
      if (!validator.canAddCommand(viewerId, game, currentAction, command)) return false;
    }
    return true;
  }

  public boolean canSetCommand(String viewerId, Game game, Action currentAction, Command command,
      int index) {
    for (ActionValidator validator : actionValidators) {
      if (!validator.canSetCommand(viewerId, game, currentAction, command, index)) return false;
    }
    return true;
  }

  public boolean canSubmitAction(String viewerId, Game game, Action currentAction) {
    for (ActionValidator validator : actionValidators) {
      if (!validator.canSubmitAction(viewerId, game, currentAction)) return false;
    }
    return true;
  }
}
