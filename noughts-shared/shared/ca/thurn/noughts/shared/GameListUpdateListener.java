package ca.thurn.noughts.shared;

import ca.thurn.noughts.shared.entities.Game;

public interface GameListUpdateListener {
  public void onGameAdded(Game game);
  
  public void onGameChanged(Game game);
}
