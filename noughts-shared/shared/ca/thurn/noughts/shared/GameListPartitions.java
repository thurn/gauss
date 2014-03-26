package ca.thurn.noughts.shared;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import ca.thurn.noughts.shared.entities.Game;
import ca.thurn.noughts.shared.entities.GameListSection;

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
      switch (getSection(game, userId)) {
        case GAME_OVER:
          gameOver.add(game);
          break;
        case YOUR_TURN:
          yourTurn.add(game);
          break;
        case THEIR_TURN:
          theirTurn.add(game);
          break;
      }
    }
    Collections.sort(yourTurn, Games.comparator());
    Collections.sort(theirTurn, Games.comparator());
    Collections.sort(gameOver, Games.comparator());
  }
  
  public static GameListSection getSection(Game game, String viewerId) {
    if (game.isGameOver()) {
      return GameListSection.GAME_OVER;
    } else if (game.isLocalMultiplayer() || Games.currentPlayerId(game).equals(viewerId)) {
      return GameListSection.YOUR_TURN;
    } else {
      return GameListSection.THEIR_TURN;
    }     
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
