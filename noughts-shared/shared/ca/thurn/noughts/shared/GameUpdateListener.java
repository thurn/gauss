package ca.thurn.noughts.shared;

import ca.thurn.noughts.shared.entities.Game;
import ca.thurn.noughts.shared.entities.GameStatus;

/**
 * Interface to implement to listen for game updates.
 */
public interface GameUpdateListener {
  public void onGameUpdate(Game game);
  
  public void onGameStatusChanged(GameStatus status);
}