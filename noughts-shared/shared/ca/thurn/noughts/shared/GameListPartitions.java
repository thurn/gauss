package ca.thurn.noughts.shared;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import ca.thurn.noughts.shared.entities.Game;

/**
 * A value object consisting of three game lists: games where it is the
 * provided user's turn, games where it is not their turn, and games which
 * are over.
 */
public class GameListPartitions {
  private final List<Game> yourTurn;
  private final List<Game> theirTurn;
  private final List<Game> gameOver;
  
  GameListPartitions(String userId, Collection<Game> games) {
    yourTurn = new ArrayList<Game>();
    theirTurn = new ArrayList<Game>();
    gameOver = new ArrayList<Game>();
    for (Game game : games) {
      if (game.isGameOver()) {
        gameOver.add(game);
      } else if (game.isLocalMultiplayer() || Games.currentPlayerId(game).equals(userId)) {
        // Treat local multiplayer as always "your turn"
        yourTurn.add(game);
      } else {
        theirTurn.add(game);
      }      
    }
    Collections.sort(yourTurn, Games.comparator());
    Collections.sort(theirTurn, Games.comparator());
    Collections.sort(gameOver, Games.comparator());
  }
  
  /**
   * @return An unmodifiable list view of games where it is your turn.
   */
  public List<Game> yourTurn() {
    return Collections.unmodifiableList(yourTurn);
  }
  
  /**
   * @return An unmodifiable list view of games where it is not your turn.
   */  
  public List<Game> theirTurn() {
    return Collections.unmodifiableList(theirTurn);
  }
  
  /**
   * @return An unmodifiable list view of games where the game is over.
   */  
  public List<Game> gameOver() {
    return Collections.unmodifiableList(gameOver);
  }
  
}
