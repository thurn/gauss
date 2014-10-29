package com.tinlib.validator;

import com.tinlib.generated.Game;
import com.tinlib.infuse.Injector;

import java.util.Set;

public class JoinGameValidatorService {
  private final Set<JoinGameValidator> validators;

  public JoinGameValidatorService(Injector injector) {
    validators = injector.getMultiple(JoinGameValidator.class);
  }

  public boolean canJoinGame(String viewerId, Game game) {
    for (JoinGameValidator validator : validators) {
      if (!validator.canJoinGame(viewerId, game)) return false;
    }
    return true;
  }
}
