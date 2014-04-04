package ca.thurn.noughts.shared;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.thurn.noughts.shared.entities.Action;
import ca.thurn.noughts.shared.entities.Command;
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
      ImageString.newBuilder().setString("no_opponent_40").setType(ImageType.LOCAL).build();
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
    if (game.getCurrentPlayerNumber() >= game.getPlayerCount()) return null;
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
      String name = "Opponent";
      ImageString imageString = NO_OPPONENT_IMAGE_STRING;
      boolean isComputerPlayer = false;
      if (playerHasProfile(game, game.getCurrentPlayerNumber())) {
        Profile currentPlayerProfile = playerProfile(game, game.getCurrentPlayerNumber());
        if (currentPlayerProfile.hasImageString()) {
          imageString = currentPlayerProfile.getImageString();
        }
        if (currentPlayerProfile.hasName()) {
          name = currentPlayerProfile.getName();
        }
        if (currentPlayerProfile.hasIsComputerPlayer()) {
          isComputerPlayer = currentPlayerProfile.isComputerPlayer();
        }
      }
      return GameStatus.newBuilder()
          .setStatusString(name + "'s turn")
          .setStatusImageString(imageString)
          .setStatusPlayer(game.getCurrentPlayerNumber())
          .setIsComputerThinking(isComputerPlayer)
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
   * Constructs a list of Parse notification channel IDs for the viewer for
   * this game.
   *
   * @param game game to construct channel ID for.
   * @param viewerId Viewer to construct the channel for.
   * @return Channel IDs to use with Parse, one per player number that the
   *     viewer is acting as in this game. 
   */
  public static List<String> channelIdsForViewer(Game game, String viewerId) {
    List<String> ids = new ArrayList<String>();
    for (Integer number : playerNumbersForPlayerId(game, viewerId)) {
      ids.add(channelIdForPlayer(game.getId(), number));
    }
    return ids;
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
   * @param game The game.
   * @param playerNumber A player number currently in this game.
   * @return The Profile object for that player, with local profiles taking
   *     precedence over regular profiles.
   * @throws IllegalArgumentException If there is no profile for this player.
   */
  static Profile playerProfile(Game game, int playerNumber) {
    if (!playerHasProfile(game, playerNumber)) {
      throw new IllegalArgumentException("No profile for player " + playerNumber);
    }
    if (hasLocalProfile(game, playerNumber)) {
      return game.getLocalProfile(playerNumber);
    } else {
      return game.getProfile(playerIdFromPlayerNumber(game, playerNumber));
    }
  }

  /**
   * @param game The game.
   * @param playerNumber A player number current in this game.
   * @return True if the provided player has a profile in this game.
   */
  static boolean playerHasProfile(Game game, int playerNumber) {
    if (playerNumber >= game.getPlayerCount()) return false;
    return hasLocalProfile(game, playerNumber) ||
        game.hasProfile(playerIdFromPlayerNumber(game, playerNumber));
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
  public static  List<Integer> playerNumbersForPlayerId(Game game, String playerId) {
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
      if (game.isGameOver()) {
        return "vs. (No Opponent)";        
      } else {
        return "vs. (No Opponent Yet)";
      }
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
        } else if (hasOpponent(game, viewerId) &&
            game.getVictorList().contains(opponentPlayerNumber(game, viewerId))) {
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
  
  /**
   * @param old Previous game state.
   * @param next New game state.
   * @return A map of all the commands that have been submitted in the new
   *     game state.
   */
  static Map<Command, Action> commandsSubmitted(Game old, Game next) {
    Map<Command, Action> result = new HashMap<Command, Action>();
    int numOldActions = old.getSubmittedActionCount();
    int numNewActions = next.getSubmittedActionCount();
    while (numNewActions > numOldActions) {
      Action action = next.getSubmittedAction(numNewActions - 1);
      for (Command command : action.getCommandList()) {
        result.put(command, action);
      }
      numNewActions--;
    }    
    return result;
  }
  
  /**
   * @param old Previous game state.
   * @param next New game state.
   * @return A map of all commands that have been added to the current action
   *     in the new game state.
   */
  static Map<Command, Action> commandsAdded(Game old, Game next) {
    Map<Command, Action> result = new HashMap<Command, Action>();
    if (old.getSubmittedActionCount() != next.getSubmittedActionCount()) {
      return Collections.emptyMap();
    }
    if (old.hasCurrentAction() && next.hasCurrentAction()) {
      int numOldCommands = old.getCurrentAction().getCommandCount();
      int numNewCommands = next.getCurrentAction().getCommandCount();
      while (numNewCommands > numOldCommands) {
        result.put(next.getCurrentAction().getCommand(numNewCommands - 1),
            next.getCurrentAction());
        numNewCommands--;
      }
    } else if (next.hasCurrentAction()) {
      for (Command command : next.getCurrentAction().getCommandList()) {
        result.put(command, next.getCurrentAction());
      }
    }
    return result;
  }
  
  /**
   * @param old Previous game state.
   * @param next New game state.
   * @return True if the most recent command has been modified in the new
   *     game state.
   */
  static boolean currentCommandChanged(Game old, Game next) {
    if (!old.hasCurrentAction() || !next.hasCurrentAction() ||
        old.getCurrentAction().getCommandCount() != next.getCurrentAction().getCommandCount()) {
      return false;
    } else {
      Action oldAction = old.getCurrentAction();
      Action newAction = next.getCurrentAction();
      if (oldAction.getCommandCount() == 0 || newAction.getCommandCount() == 0) {
        return false;
      }
      return !oldAction.getCommand(oldAction.getCommandCount() - 1).equals(
          newAction.getCommand(newAction.getCommandCount() - 1));
    }
  }
  
  /**
   * @param old Previous game state.
   * @param next New game state.
   * @return A map of all commands that have been removed from the current
   *     action in the new game state.
   */  
  static Map<Command, Action> commandsRemoved(Game old, Game next) {
    return commandsAdded(next, old);
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
