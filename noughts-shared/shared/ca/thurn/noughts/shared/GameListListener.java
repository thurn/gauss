package ca.thurn.noughts.shared;

import ca.thurn.noughts.shared.entities.Game;


/**
 * Interface to implement to listen for game list updates.
 */
public interface GameListListener {
  public void onGameAdded(Game game);
  
  public void onGameChanged(Game game);
      
  public void onGameRemoved(Game game);
}