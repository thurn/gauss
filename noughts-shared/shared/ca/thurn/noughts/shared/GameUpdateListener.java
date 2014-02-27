package ca.thurn.noughts.shared;

/**
 * Interface to implement to listen for game updates.
 */
public interface GameUpdateListener {
  public void onGameUpdate(Game game);
  
  public void onGameStatusChanged(GameStatus status);
}