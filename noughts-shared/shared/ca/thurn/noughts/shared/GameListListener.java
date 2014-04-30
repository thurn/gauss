package ca.thurn.noughts.shared;

import ca.thurn.noughts.shared.entities.Game;

public interface GameListListener {
  public void onGameAdded(Game game);
  
  public void onGameChanged(Game game);
  
  public void onGameRemoved(String gameId);
}
