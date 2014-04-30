package ca.thurn.noughts.shared;

import ca.thurn.noughts.shared.entities.Game;

public class AbstractGameListListener implements GameListListener {

  @Override
  public void onGameAdded(Game game) {}

  @Override
  public void onGameChanged(Game game) {}

  @Override
  public void onGameRemoved(String gameId) {}

}
