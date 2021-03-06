package com.tinlib.validator;

import com.tinlib.util.Games;
import com.tinlib.generated.Action;
import com.tinlib.generated.Command;
import com.tinlib.generated.Game;

public class DefaultActionValidator implements ActionValidator {
  @Override
  public boolean canAddCommand(String viewerId, Game game, Action currentAction, Command command) {
    if (game.getIsGameOver() || !Games.hasCurrentPlayer(game)) return false;
    return Games.currentPlayerId(game).equals(viewerId);
  }

  @Override
  public boolean canSetCommand(String viewerId, Game game, Action currentAction, Command command,
      int index) {
    if (game.getIsGameOver() || !Games.hasCurrentPlayer(game)) return false;
    if (index < 0 || index >= currentAction.getCommandCount()) return false;
    return Games.currentPlayerId(game).equals(viewerId);
  }

  @Override
  public boolean canSubmitAction(String viewerId, Game game, Action currentAction) {
    if (game.getIsGameOver() || !Games.hasCurrentPlayer(game)) return false;
    if (!currentAction.getGameId().equals(game.getId())) return false;
    if (currentAction.hasIsSubmitted() && currentAction.getIsSubmitted()) return false;
    if (currentAction.getCommandCount() == 0) return false;
    return Games.currentPlayerId(game).equals(viewerId);
  }
}
