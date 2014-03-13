package ca.thurn.noughts.shared;

import ca.thurn.noughts.shared.entities.Game;
import ca.thurn.noughts.shared.entities.GameStatus;

/**
 * Interface to implement to listen for game updates.
 */
public interface GameUpdateListener {
  /**
   * Called with the new value whenever the game changes.
   *
   * @param game New game state.
   */
  public void onGameUpdate(Game game);
  
  /**
   * Called when the "status" of the game changes (game ends, new player's
   * turn, etc)
   *
   * @param status Game status object summarizing change.
   */
  public void onGameStatusChanged(GameStatus status);
}