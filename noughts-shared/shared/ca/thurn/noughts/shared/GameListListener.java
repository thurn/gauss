package ca.thurn.noughts.shared;

/**
 * Interface to implement to listen for game list updates.
 */
public interface GameListListener {
  public void onGameAdded(Game game);
  
  public void onGameChanged(Game game);
      
  public void onGameRemoved(Game game);
}