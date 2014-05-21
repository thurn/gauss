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
  private final String userId;

  GameListPartitions(String userId, Collection<Game> games) {
    this.userId = userId;
    yourTurn = new ArrayList<Game>();
    theirTurn = new ArrayList<Game>();
    gameOver = new ArrayList<Game>();
    for (Game game : games) {
      listForSection(getSection(game)).add(game);
    }
    Collections.sort(yourTurn, Games.comparator());
    Collections.sort(theirTurn, Games.comparator());
    Collections.sort(gameOver, Games.comparator());
  }

  /**
   * @param game A game.
   * @return The section which this game belongs in.
   */
  public GameListSection getSection(Game game) {
    if (game.isGameOver()) {
      return GameListSection.GAME_OVER;
    }
    if (game.isLocalMultiplayer() ||
        (Games.hasCurrentPlayerId(game) && Games.currentPlayerId(game).equals(userId))) {
      return GameListSection.YOUR_TURN;
    } else {
      return GameListSection.THEIR_TURN;
    }
  }

  /**
   * @param game A game
   * @param viewerId The viewer's ID
   * @return True if a game with the same ID as the provided game exists in the
   *     correct section.
   */
  public boolean isGameInCorrectSection(Game game) {
    GameListSection section = getSection(game);
    for (Game existingGame : listForSection(section)) {
      if (existingGame.getId().equals(game.getId())) {
        return true;
      }
    }
    return false;
  }

  public List<Game> listForSection(GameListSection section) {
    switch (section) {
      case GAME_OVER:
        return gameOver;
      case YOUR_TURN:
        return yourTurn;
      case THEIR_TURN:
        return theirTurn;
      default:
        throw new RuntimeException("Unknown section: " + section);
    }
  }

  public IndexPath getGameIndexPath(String gameId) {
    int index = getGameIndex(gameId, GameListSection.GAME_OVER);
    if (index != -1) return new IndexPath(GameListSection.GAME_OVER.ordinal(), index);
    index = getGameIndex(gameId, GameListSection.YOUR_TURN);
    if (index != -1) return new IndexPath(GameListSection.YOUR_TURN.ordinal(), index);
    index = getGameIndex(gameId, GameListSection.THEIR_TURN);
    if (index != -1) return new IndexPath(GameListSection.THEIR_TURN.ordinal(), index);
    return IndexPath.NOT_FOUND;
  }

  private int getGameIndex(String gameId, GameListSection section) {
    List<Game> list = listForSection(section);
    for (int i = 0; i < list.size(); ++i) {
      if (list.get(i).getId().equals(gameId)) {
        return i;
      }
    }
    return -1;
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

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("GameListPartitions [yourTurn=");
    builder.append(yourTurn);
    builder.append(", theirTurn=");
    builder.append(theirTurn);
    builder.append(", gameOver=");
    builder.append(gameOver);
    builder.append(", userId=");
    builder.append(userId);
    builder.append("]");
    return builder.toString();
  }

}
