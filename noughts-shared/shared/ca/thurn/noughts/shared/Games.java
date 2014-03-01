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
  public static final ImageString GAME_OVER_IMAGE_STRING = 
      ImageString.newBuilder().setString("game_over").setType(ImageType.LOCAL).build();
  public static final ImageString NO_OPPONENT_IMAGE_STRING = 
      ImageString.newBuilder().setString("no_opponent").setType(ImageType.LOCAL).build();
  public static final ImageString ANONYMOUS_OPPONENT_IMAGE_STRING =
      ImageString.newBuilder().setString("anonymous_opponent").setType(ImageType.LOCAL).build();
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
  
  public static Comparator<Game> comparator() {
    return new Comparator<Game>() {
      @Override
      public int compare(Game o1, Game o2) {
        return compareGames(o1, o2);
      }
    };
  }

  /**
   * @return The ID of the current player, or null if there is no current
   *     player.
   */
  public static String currentPlayerId(Game game) {
    if (!game.hasCurrentPlayerNumber()) return null;
    return playerIdFromPlayerNumber(game, game.getCurrentPlayerNumber());
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
        Profile winnerProfile = playerProfile(game, winnerNumber);
        String winner = winnerProfile.getName();
        ImageString imageString = winnerProfile.hasImageString() ?
            winnerProfile.getImageString() : ANONYMOUS_OPPONENT_IMAGE_STRING;  
        return GameStatus.newBuilder()
            .setStatusString(winner + " won the game!")
            .setStatusImageString(imageString)
            .setStatusPlayer(winnerNumber)
            .setIsComputerThinking(false)
            .build();
      } else {
        return GameStatus.newBuilder()
            .setStatusString("Game drawn.")
            .setStatusImageString(GAME_OVER_IMAGE_STRING)
            .setIsComputerThinking(false)
            .build();
      }
    } else {
      Profile currentPlayerProfile = playerProfile(game, game.getCurrentPlayerNumber());
      ImageString imageString = currentPlayerProfile.hasImageString() ?
          currentPlayerProfile.getImageString() : ANONYMOUS_OPPONENT_IMAGE_STRING;
      return GameStatus.newBuilder()
          .setStatusString(currentPlayerProfile.getName() + "'s turn")
          .setStatusImageString(imageString)
          .setStatusPlayer(game.getCurrentPlayerNumber())
          .setIsComputerThinking(currentPlayerProfile.isComputerPlayer())
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
   * @return A representation of this game suitable for inclusion in a user's
   *     game list, with the game actions omitted.
   */
  public static Game minimalGame(Game game) {
    return game.toBuilder()
        .clearSubmittedActionList()
        .setIsMinimal(true)
        .build();
  }

  /**
   * @param viewerId viewer's player ID
   * @return True if there is an opponent in this game who is distinct from
   *     the viewer. False if there's no opponent or the viewer is playing
   *     both sides in this game. 
   */
  static boolean hasOpponent(Game game, String viewerId) {
    return game.getPlayerCount() == 2 && !game.getPlayer(0).equals(game.getPlayer(1));
  }

  /**
   * @param viewerId viewer's player ID
   * @return The player number of your opponent.
   * @throws IllegalStateException If there is no opponent as defined by
   *     {@link Game#hasOpponent(String)}.
   */
  static int opponentPlayerNumber(Game game, String viewerId) {
    if (!hasOpponent(game, viewerId)) {
      throw new IllegalStateException("No opponent or viewer is both players.");
    } else if (game.getPlayer(0).equals(viewerId)) {
      return 1;
    } else {
      return 0;
    }
  }

  /**
   * @param playerNumber A player number currently in this game.
   * @return The Profile object for that player, with local profiles taking
   *     precedence over regular profiles.
   * @throws IllegalArgumentException If there is no profile for this player.
   */
  static Profile playerProfile(Game game, int playerNumber) {
    if (hasLocalProfile(game, playerNumber)) {
      return game.getLocalProfile(playerNumber);
    }
    Profile result = game.getProfile(playerIdFromPlayerNumber(game, playerNumber));
    if (result != null) {
      return result;
    } else {
      throw new IllegalArgumentException("No profile for player " + playerNumber);
    }
  }  

  /**
   * @param playerNumber A player's player number
   * @return That player's player ID
   * @throws IllegalArgumentException if the player number is not currently in
   *     the game.
   */
  // Visible for testing
  static String playerIdFromPlayerNumber(Game game, int playerNumber) {
    try {
      return game.getPlayer(playerNumber);
    } catch (IndexOutOfBoundsException exception) {
      throw new IllegalArgumentException(exception);
    }
  }
  
  /**
   * @param playerId A player ID
   * @return All player numbers (if any) associated with this player ID
   */
  // Visible for testing
  static List<Integer> playerNumbersForPlayerId(Game game, String playerId) {
    if (playerId == null) throw new IllegalArgumentException("Null playerId");
    List<Integer> results = new ArrayList<Integer>();
    for (int i = 0; i < game.getPlayerCount(); ++i) {
      if (game.getPlayer(i).equals(playerId)) {
        results.add(i);
      }
    }
    return results;
  } 
  
  /**
   * @param playerNumber A player number.
   * @return True if the provided player has a local profile.
   */
  // Visible for testing
  static boolean hasLocalProfile(Game game, int playerNumber) {
    if (playerNumber < 0 || playerNumber >= game.getLocalProfileCount()) return false;
    return game.getLocalProfile(playerNumber) != null;
  }
  
  /**
   *
   * @param viewerId viewer's player ID
   * @return True if the game has an opponent who has a profile, false
   *     otherwise.
   */
  // Visible for testing
  static boolean hasOpponentProfile(Game game, String viewerId) {
    if (!hasOpponent(game, viewerId)) return false;
    int opponentNumber = opponentPlayerNumber(game, viewerId);
    if (hasLocalProfile(game, opponentNumber)) {
      return true;
    }
    String opponentId = playerIdFromPlayerNumber(game, opponentNumber);
    return game.getProfileMap().containsKey(opponentId);
  }

  /**
   * @param viewerId viewer's player ID
   * @return The profile of your opponent or null if there isn't one. Local
   *     profiles take precedence over regular profiles.
   * @throws IllegalStateException If there is no opponent as defined by
   *     {@link Game#hasOpponent(String)} or there is no opponent profile.
   */
  // Visible for testing
  static Profile opponentProfile(Game game, String viewerId) {
    if (!hasOpponentProfile(game, viewerId)) {
      throw new IllegalStateException("No opponent profile found.");
    }
    int opponentNumber = opponentPlayerNumber(game, viewerId);
    if (hasLocalProfile(game, opponentNumber)) {
      return game.getLocalProfile(opponentNumber);
    } else {
      String opponentId = playerIdFromPlayerNumber(game, opponentNumber);
      return game.getProfile(opponentId);
    }
  }

  /**
   * @param viewerId viewer's player ID
   * @return A string describing the opponent of this game, such as
   *     "vs. Frank".
   */
  // Visible for testing
  static String vsString(Game game, String viewerId) {
    if (game.isLocalMultiplayer()) {
      if (game.getLocalProfileCount() == 2) {
        return game.getLocalProfile(0).getName() + " vs. " + game.getLocalProfile(1).getName();
      } else {
        return "Local Multiplayer Game";
      }
    }
    else if (hasOpponentProfile(game, viewerId)) {
      return "vs. " + opponentProfile(game, viewerId).getName();
    } else {
      return "vs. (No Opponent Yet)";
    }
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
  // Visible for testing
  static String timeAgoString(Game game, String viewerId, long number, String unit) {
    String statusString;
    if (game.isGameOver()) {
      if (game.isLocalMultiplayer()) {
        statusString = "Game ended";
      } else {
        List<Integer> playerNumbers = playerNumbersForPlayerId(game, viewerId);
        if (game.getVictorCount() == 2) {
          statusString = "Game tied";
        } else if (playerNumbers.size() == 1 &&
            game.getVictorList().contains(playerNumbers.get(0))) {
          statusString = "You won";
        } else if (game.getVictorList().contains(opponentPlayerNumber(game, viewerId))) {
          if (hasOpponentProfile(game, viewerId)) {
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
   * @param viewerId viewer's player ID
   * @return A string describing the last state of the game, such as "Updated 1
   *     second ago" or "You won 4 years ago". 
   */
  // Visible for testing
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
  
  static boolean differentStatus(Game g1, Game g2) {
    return (g1.hasIsGameOver() && g2.hasIsGameOver() && g1.isGameOver() != g2.isGameOver()) ||
        (g1.hasCurrentPlayerNumber() && g2.hasCurrentPlayerNumber() &&
            g1.getCurrentPlayerNumber() != g2.getCurrentPlayerNumber());
  }

  // Visible for testing
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
  
  /**
   * @return A list of image strings to use to represent this game in the game
   *     list.
   */
  // Visible for testing
  static List<ImageString> imageList(Game game, String viewerId) {
    List<ImageString> result = new ArrayList<ImageString>();
    if (game.isLocalMultiplayer()) {
      for (Profile profile : game.getLocalProfileList()) {
        result.add(profile.getImageString());
      }
    } else {
      if (hasOpponentProfile(game, viewerId)) {
        result.add(opponentProfile(game, viewerId).getImageString());      
      } else {
        result.add(NO_OPPONENT_IMAGE_STRING);
      }
    }
    return result;
  }
}
