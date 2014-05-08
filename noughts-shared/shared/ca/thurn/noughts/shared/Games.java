package ca.thurn.noughts.shared;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import ca.thurn.noughts.shared.entities.Game;
import ca.thurn.noughts.shared.entities.GameListEntry;
import ca.thurn.noughts.shared.entities.GameStatus;
import ca.thurn.noughts.shared.entities.ImageString;
import ca.thurn.noughts.shared.entities.ImageType;
import ca.thurn.noughts.shared.entities.Profile;

public class Games {
  public static final ImageString GAME_OVER_IMAGE_STRING = ImageString.newBuilder()
      .setString("game_over")
      .setType(ImageType.LOCAL)
      .build();
  public static final ImageString NO_OPPONENT_IMAGE_STRING = ImageString.newBuilder()
      .setString("no_opponent")
      .setType(ImageType.LOCAL).build();
  private static final long ONE_SECOND = 1000;
  private static final long SECONDS = 60;
  private static final long ONE_MINUTE = SECONDS * ONE_SECOND;
  private static final long MINUTES = 60;
  private static final long ONE_HOUR = MINUTES * ONE_MINUTE;
  private static final long HOURS = 24;
  private static final long ONE_DAY = HOURS * ONE_HOUR;
  private static final long DAYS = 7;
  private static final long ONE_WEEK = DAYS * ONE_DAY;
  private static final double WEEKS = 4.34812;
  private static final long ONE_MONTH = (long)WEEKS * ONE_WEEK;
  private static final long MONTHS = 12;
  private static final long ONE_YEAR = MONTHS * ONE_MONTH;

  /**
   * @return A Comparator for Games.
   */
  public static Comparator<Game> comparator() {
    return new Comparator<Game>() {
      @Override
      public int compare(Game o1, Game o2) {
        return compareGames(o1, o2);
      }
    };
  }

  /**
   * @param game A game.
   * @return True if this game has a current player ID.
   */
  public static boolean hasCurrentPlayerId(Game game) {
    return game.hasCurrentPlayerNumber() &&
        game.getCurrentPlayerNumber() < game.getPlayerCount();
  }

  /**
   * @param game A game.
   * @return The ID of the current player, or null if there is no current
   *     player.
   */
  public static String currentPlayerId(Game game) {
    if (!Games.hasCurrentPlayerId(game)) {
      throw new IllegalArgumentException("Game does not have a current player");
    }
    return game.getPlayer(game.getCurrentPlayerNumber());
  }

  /**
   * @return A GameStatus object summarizing whose turn it is in the game (or
   *     if the game is over), along with an associated image string and player
   *     number.
   */
  public static GameStatus gameStatus(Game game) {
    if (game.isGameOver()) {
      if (game.getVictorCount() == 1) {
        int winnerNumber = game.getVictor(0);
        Profile winnerProfile = game.getProfile(winnerNumber);
        String winner = winnerProfile.getName();
        ImageString imageString = winnerProfile.hasImageString() ?
            winnerProfile.getImageString() : NO_OPPONENT_IMAGE_STRING;
        return GameStatus.newBuilder()
            .setStatusString(winner + " won the game!")
            .setStatusImageString(imageString)
            .setStatusPlayer(winnerNumber)
            .setIsComputerThinking(false)
            .build();
      } else if (game.getVictorCount() == 2) {
        return GameStatus.newBuilder()
            .setStatusString("Game drawn.")
            .setStatusImageString(GAME_OVER_IMAGE_STRING)
            .setIsComputerThinking(false)
            .build();
      } else {
        return GameStatus.newBuilder()
          .setStatusString("Game over.")
          .setStatusImageString(GAME_OVER_IMAGE_STRING)
          .setIsComputerThinking(false)
          .build();
      }
    } else {
      String name = "Player " + (game.getCurrentPlayerNumber() + 1);
      Profile currentPlayerProfile = game.getProfile(game.getCurrentPlayerNumber());
      if (currentPlayerProfile.hasName()) {
        name = currentPlayerProfile.getName();
      }
      return GameStatus.newBuilder()
          .setStatusString(name + "'s turn")
          .setStatusImageString(currentPlayerProfile.hasImageString() ?
              currentPlayerProfile.getImageString() : NO_OPPONENT_IMAGE_STRING)
          .setStatusPlayer(game.getCurrentPlayerNumber())
          .setIsComputerThinking(currentPlayerProfile.hasIsComputerPlayer() ?
              currentPlayerProfile.isComputerPlayer() : false)
          .build();
    }
  }

  public static GameListEntry gameListEntry(Game game, String viewerId) {
    return GameListEntry.newBuilder()
        .setVsString(vsString(game, viewerId))
        .setModifiedString(lastUpdatedString(game, viewerId))
        .addAllImageString(imageList(game, viewerId))
        .build();
  }

  /**
   * Gets the Parse channel ID for this viewer. Cannot be called on a local
   * multiplayer game.
   *
   * @param game game to construct channel ID for.
   * @param viewerId Viewer to construct the channel for.
   * @return Channel ID to use with Parse for this player.
   */
  public static String channelIdForViewer(Game game, String viewerId) {
    return channelIdForPlayer(game.getId(), playerNumberForPlayerId(game, viewerId));
  }

  /**
   * @param gameId Game ID
   * @param playerNumber Player number
   * @return A Parse Channel ID representing this player number within the game
   *     with the provided ID.
   */
  public static String channelIdForPlayer(String gameId, int playerNumber) {
    return "G" + gameId + "___" + playerNumber;
  }

  /**
   * @param viewerId viewer's player ID
   * @return The player number of your opponent.
   * @throws IllegalStateException If this method is called on a local
   *     multiplayer game.
   */
  static int opponentPlayerNumber(Game game, String viewerId) {
    if (game.isLocalMultiplayer()) {
      throw new IllegalStateException("Tried to get opponent player number in a local " +
          "multiplayer game.");
    } else if (game.getPlayerCount() == 0) {
      throw new IllegalStateException("Tried to get opponent player number in a game with no " +
          "players");
    } else if (game.getPlayer(0).equals(viewerId)) {
      return 1;
    } else {
      return 0;
    }
  }

  public static int playerNumberForPlayerId(Game game, String playerId) {
    if (game.isLocalMultiplayer()) {
      throw new IllegalArgumentException("Can't call playerNumberForPlayerId on a local " +
          "multiplayer game.");
    }
    int index = game.getPlayerList().indexOf(playerId);
    if (index == -1) {
      throw new IllegalArgumentException("Player '" + playerId +
          "' is not a player in this game.");
    } else {
      return index;
    }
  }

  /**
   * @param viewerId viewer's player ID
   * @return The profile of your opponent.
   * @throws IllegalStateException If this method is called on a local
   *     multiplayer game.
   */
  static Profile opponentProfile(Game game, String viewerId) {
    int opponentNumber = opponentPlayerNumber(game, viewerId);
    return game.getProfile(opponentNumber);
  }

  /**
   * @param game A Game.
   * @param viewerId Viewer's player ID.
   * @return The viewer's profile.
   * @throws IllegalArgumentException If the viewer has multiple player numbers.
   */
  public static Profile viewerProfile(Game game, String viewerId) {
    return game.getProfile(playerNumberForPlayerId(game, viewerId));
  }

  /**
   * @param viewerId viewer's player ID
   * @return A string describing the opponent of this game, such as
   *     "vs. Frank".
   */
  public static String vsString(Game game, String viewerId) {
    if (game.isLocalMultiplayer()) {
      if (game.getProfile(0).hasName() && game.getProfile(1).hasName()) {
        return game.getProfile(0).getName() + " vs. " + game.getProfile(1).getName();
      } else {
        return "Local Multiplayer Game";
      }
    } else if (opponentProfile(game, viewerId).hasName()) {
      return "vs. " + opponentProfile(game, viewerId).getName();
    } else {
      if (game.isGameOver()) {
        return "vs. (No Opponent)";
      } else {
        return "vs. (No Opponent Yet)";
      }
    }
  }

  /**
   * @param viewerId viewer's player ID
   * @return A string describing the last state of the game, such as "Updated 1
   *     second ago" or "You won 4 years ago".
   */
  static String lastUpdatedString(Game game, String viewerId) {
    long duration = Math.max(Clock.getInstance().currentTimeMillis() - game.getLastModified(), 0);
    long number;
    number = duration / ONE_YEAR;
    if (number > 0) {
      return timeAgoString(game, viewerId, number, "year");
    }
    number = duration / ONE_MONTH;
    if (number > 0) {
      return timeAgoString(game, viewerId, number, "month");
    }
    number = duration / ONE_WEEK;
    if (number > 0) {
      return timeAgoString(game, viewerId, number, "week");
    }
    number = duration / ONE_DAY;
    if (number > 0) {
      return timeAgoString(game, viewerId, number, "day");
    }
    number = duration / ONE_HOUR;
    if (number > 0) {
      return timeAgoString(game, viewerId, number, "hour");
    }
    number = duration / ONE_MINUTE;
    if (number > 0) {
      return timeAgoString(game, viewerId, number, "minute");
    }
    number = duration / ONE_SECOND;
    return timeAgoString(game, viewerId, number, "second");
  }

  /**
   * @param viewerId Viewer's ID.
   * @param number The number of "unit"s ago the game was last modified.
   * @param unit The unit. For example, if the game was modified 3 hours ago,
   *     "unit" should be "hour" and "number" should be "3".
   * @return A string describing the status of the game in the past tense,
   *     along with how long ago the last update was. For example: "He won 3
   *     hours ago".
   */
  private static String timeAgoString(Game game, String viewerId, long number, String unit) {
    String statusString;
    if (game.isGameOver()) {
      if (game.isLocalMultiplayer()) {
        statusString = "Game ended";
      } else {
        if (game.getVictorCount() == 2) {
          statusString = "Game tied";
        } else if (game.getVictorList().contains(playerNumberForPlayerId(game, viewerId))) {
          statusString = "You won";
        } else if (game.getVictorList().contains(opponentPlayerNumber(game, viewerId))) {
          if (opponentProfile(game, viewerId).hasPronoun()) {
            Profile opponentProfile = opponentProfile(game, viewerId);
            statusString = Pronouns.getNominativePronoun(
                opponentProfile.getPronoun(), true /* capitalize */) + " won";
          } else {
            statusString = "They won";
          }
        } else {
          statusString = "You lost";
        }
      }
    } else {
      statusString = "Updated";
    }
    if (number <= 1) {
      String article = unit.equals("hour") ? "an" : "a";
      return statusString + " " + article + " " + unit + " ago";
    } else {
      return statusString + " " + number + " " + unit + "s ago";
    }
  }

  /**
   * @return A list of image strings to use to represent this game in the game
   *     list.
   */
  static List<ImageString> imageList(Game game, String viewerId) {
    List<ImageString> result = new ArrayList<ImageString>();
    if (game.isLocalMultiplayer()) {
      for (Profile profile : game.getProfileList()) {
        if (profile.hasImageString()) {
          result.add(profile.getImageString());
        } else {
          result.add(NO_OPPONENT_IMAGE_STRING);
        }
      }
    } else {
      if (opponentProfile(game, viewerId).hasImageString()) {
        result.add(opponentProfile(game, viewerId).getImageString());
      } else {
        result.add(NO_OPPONENT_IMAGE_STRING);
      }
    }
    return result;
  }

  /**
   * @param old Previous game state.
   * @param next New game state.
   * @return True if the two game states have different "status" (different
   *     player's turn, game is over, etc).
   */
  static boolean differentStatus(Game old, Game next) {
    return (old.hasIsGameOver() && next.hasIsGameOver() && old.isGameOver() != next.isGameOver()) ||
        (old.hasCurrentPlayerNumber() && next.hasCurrentPlayerNumber() &&
            old.getCurrentPlayerNumber() != next.getCurrentPlayerNumber());
  }

  static int compareGames(Game game, Game other) {
    if (other == null) {
      throw new NullPointerException("Null game in compareTo()");
    } else if (game.equals(other)) {
      return 0;
    } else if (game.getLastModified() < other.getLastModified()) {
      return 1;
    } else if (game.getLastModified() > other.getLastModified()) {
      return -1;
    } else {
      // Different games, same lastModified, order by hashCode
      return other.hashCode() - game.hashCode();
    }
  }
}
