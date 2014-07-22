package com.tinlib.validator;

import com.tinlib.generated.Game;

public interface JoinGameValidator {
  public boolean canJoinGame(String viewerId, Game game);
}
