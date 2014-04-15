package ca.thurn.noughts.shared;

import ca.thurn.noughts.shared.entities.Action;
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
   * Called with the new value whenever the viewer's current action changes.
   *
   * @param currentAction
   */
  public void onCurrentActionUpdate(Action currentAction);
  
  /**
   * Called when the "status" of the game changes (game ends, new player's
   * turn, etc)
   *
   * @param status Game status object summarizing change.
   */
  public void onGameStatusChanged(GameStatus status);
  
  /**
   * Called when the viewer needs to be prompted to enter a profile.
   *
   * @param game The game. 
   */
  public void onProfileRequired(String gameId);
}