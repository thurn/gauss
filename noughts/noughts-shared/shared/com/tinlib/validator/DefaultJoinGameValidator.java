package com.tinlib.validator;

import com.tinlib.generated.Game;

public class DefaultJoinGameValidator implements JoinGameValidator {
  @Override
  public boolean canJoinGame(String viewerId, Game game) {
    return !game.getIsGameOver() && !game.getPlayerList().contains(viewerId);
  }
}
