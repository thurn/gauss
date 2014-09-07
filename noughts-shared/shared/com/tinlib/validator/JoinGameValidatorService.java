package com.tinlib.validator;

import com.tinlib.core.TinKeys2;
import com.tinlib.generated.Game;
import com.tinlib.infuse.Injector;

import java.util.Set;

public class JoinGameValidatorService {
  private final Set<JoinGameValidator> validators;

  public JoinGameValidatorService(Injector injector) {
    validators = injector.getMultiple(TinKeys2.JOIN_GAME_VALIDATORS);
  }

  public boolean canJoinGame(String viewerId, Game game) {
    for (JoinGameValidator validator : validators) {
      if (!validator.canJoinGame(viewerId, game)) return false;
    }
    return true;
  }
}
