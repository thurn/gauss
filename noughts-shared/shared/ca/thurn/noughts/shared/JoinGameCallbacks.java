package ca.thurn.noughts.shared;

import ca.thurn.noughts.shared.entities.Game;

public interface JoinGameCallbacks {
  /**
   * Called when the user successfully joins a game.
   *
   * @param game Game the user joined.
   */
  public void onJoinedGame(Game game);

  /**
   * Called if there was an error joining the game.
   *
   * @param errorMessage Explanation of the error.
   */
  public void onErrorJoiningGame(String errorMessage);
}
