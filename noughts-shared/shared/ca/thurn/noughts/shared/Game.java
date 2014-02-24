package ca.thurn.noughts.shared;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ca.thurn.noughts.shared.Action.ActionDeserializer;
import ca.thurn.uct.core.Copyable;

public class Game extends Entity implements Comparable<Game>, Copyable {
  private static final String GAME_OVER_PHOTO_STRING = "game_over";
  private static final String NO_OPPONENT_PHOTO_STRING = "no_opponent";
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
   * Represents the current status of the game.
   */
  public static class GameStatus {
    private final static int NO_PLAYER_NUMBER = -1;
    private final String statusString;
    private final String statusPhotoString;
    private final boolean photoIsUrl;
    private final int statusColor;
    
    private GameStatus(String statusString, String statusPhotoString, boolean photoIsUrl, 
        int statusColor) {
      this.statusString = statusString;
      this.statusPhotoString = statusPhotoString;
      this.photoIsUrl = photoIsUrl;
      this.statusColor = statusColor;
    }
    
    /**
     * @return True if the value for "photo string" corresponds to an image
     *     URL, false if the value corresponds to a local resource.
     */
    public boolean photoIsUrl() {
      return photoIsUrl;
    }

    /**
     * @return True if no player color is associated with the current game state.
     */
    public boolean useDefaultColor() {
      return statusColor == NO_PLAYER_NUMBER;
    }

    /**
     * @return A short string describing the current game state.
     */
    public String getStatusString() {
      return statusString;
    }

    /**
     * @return An image string for the current game state, either a URL or a
     *     local resource string depending on the value of photoIsUrl().
     */
    public String getStatusPhotoString() {
      return statusPhotoString;
    }

    /**
     * @return The player whose color should be associated with this status.
     */
    public int getStatusPlayerColor() {
      if (useDefaultColor()) {
        throw new IllegalStateException("No status color, use the default color.");
      }
      return statusColor;
    }
  }
  
  /**
   * Represents a game entry in the game list.
   */
  public static class GameListEntry {
    private final String vsString;
    private final String modifiedString;
    private final List<PhotoString> photoStrings;
    
    private GameListEntry(String vsString, String modifiedString,
        List<PhotoString> photoStrings) {
      this.vsString = vsString;
      this.modifiedString = modifiedString;
      this.photoStrings = photoStrings;
    }
    
    /**
     * @return A short string describing who the players are in this game.
     */
    public String getVsString() {
      return vsString;
    }

    /**
     * @return A short string describing the current status of this game and
     *     when it was last modified.
     */
    public String getModifiedString() {
      return modifiedString;
    }

    /**
     * @return A list of photo strings of players in this game.
     */
    public List<PhotoString> getPhotoStrings() {
      return photoStrings;
    }
  }
  
  public static class GameDeserializer extends EntityDeserializer<Game> {
    @Override 
    public Game deserialize(Map<String, Object> map) {
      return new Game(map);
    }    
  }
  
  /**
   * The game ID
   */
  private final String id;

  /**
   * An array of the players in the game, which can be though of as a bimap
   * from Player Number to Player ID. A player who leaves the game will have
   * her entry in this array replaced with null.
   */
  private final List<String> players;

  /**
   * A mapping from player IDs to profile information about the player.
   */
  private final Map<String, Profile> profiles;
  
  /**
   * A mapping from player numbers to profile information about the player,
   * takes precedence over ID-based profiles.
   */
  private final Map<Integer, Profile> localProfiles;

  /**
   * The number of the player whose turn it is, that is, their index within
   * the players array. Null when the game is not in progress.
   */
  private Integer currentPlayerNumber;

  /**
   * Actions taken in this game, in the order in which they were taken.
   * Potentially includes an unsubmitted action of the current player.
   */
  private final List<Action> actions;

  /**
   * Index in actions list of action currently being constructed, null when
   * there is no current action.
   */
  private Integer currentActionNumber;

  /**
   * UNIX timestamp of time when game was last modified.
   */
  private Long lastModified;

  /**
   * Facebook request ID associated with this game.
   */
  private String requestId;

  /**
   * List of player numbers of the players who won this game. In the case of a
   * draw, it should contain all of the drawing players. In the case of a
   * "nobody wins" situation, an empty list should be present. This field
   * cannot be present on a game which is still in progress.
   */
  private final List<Integer> victors;

  /**
   * True if this game has ended.
   */
  private boolean gameOver;

  /**
   * True if this game is in local multiplayer mode
   */
  private boolean localMultiplayer;
  
  /**
   * True if this is a minimal game representation, as returned by
   * {@link Game#minimalGame()}.
   */
  private boolean isMinimal;

  /**
   * An array of player IDs who have resigned the game.
   */
  private final List<String> resignedPlayers;
  
  public Game(String id) {
    players = new ArrayList<String>();
    profiles = new HashMap<String, Profile>();
    localProfiles = new HashMap<Integer, Profile>();
    actions = new ArrayList<Action>();
    victors = new ArrayList<Integer>();
    resignedPlayers = new ArrayList<String>();
    this.id = id;
  }

  public Game(Map<String, Object> gameMap) {
    id = getString(gameMap, "id");
    players = getList(gameMap, "players");
    profiles = extractProfiles(gameMap);
    localProfiles = extractLocalProfiles(gameMap);
    setCurrentPlayerNumber(getInteger(gameMap, "currentPlayerNumber"));
    actions = getEntities(gameMap, "actions", new ActionDeserializer());
    setCurrentActionNumber(getInteger(gameMap, "currentActionNumber"));
    setLastModified(getLong(gameMap, "lastModified"));
    setRequestId(getString(gameMap, "requestId"));
    victors = getIntegerList(getList(gameMap, "victors"));
    gameOver = getBoolean(gameMap, "gameOver");
    localMultiplayer = getBoolean(gameMap, "localMultiplayer");
    isMinimal = getBoolean(gameMap, "isMinimal");
    resignedPlayers = getList(gameMap, "resignedPlayers");
  }

  @SuppressWarnings("unchecked")
  private Map<String, Profile> extractProfiles(Map<String, Object> gameMap) {
    Map<String, Profile> result = new HashMap<String, Profile>();
    if (gameMap.containsKey("profiles")) {
      Map<String, Object> profilesMap = (Map<String, Object>)gameMap.get("profiles");
      for(Entry<String, Object> entry : profilesMap.entrySet()) {
        Map<String, Object> profileObject = (Map<String, Object>)entry.getValue();
        result.put(entry.getKey(), new Profile(profileObject));
      }
    }
    return result;    
  }
  
  private Map<String, Object> serializeProfiles() {
    Map<String, Object> result = new HashMap<String, Object>();
    for (Entry<String, Profile> entry : profiles.entrySet()) {
      result.put(entry.getKey(), entry.getValue().serialize());
    }
    return result;
  }
  
  @SuppressWarnings("unchecked")
  private Map<Integer, Profile> extractLocalProfiles(Map<String, Object> gameMap) {
    Map<Integer, Profile> result = new HashMap<Integer, Profile>();
    if (gameMap.containsKey("localProfiles")) {
      List<Object> mapProfiles = (List<Object>)gameMap.get("localProfiles");
      for (int i = 0; i < mapProfiles.size(); ++i) {
        Map<String, Object> localProfile = (Map<String, Object>)mapProfiles.get(i);
        if (localProfile != null) {
          result.put(i, new Profile(localProfile));
        }
      }
    }
    return result;    
  }
  
  private List<Object> serializeLocalProfiles() {
    List<Object> result = new ArrayList<Object>();
    for (int i = 0; i < localProfiles.size(); ++i) {
      result.add(null);
    }
    for (Entry<Integer, Profile> entry : localProfiles.entrySet()) {
      result.set(entry.getKey(), entry.getValue().serialize());
    }
    return result;
  }
  
  @Override
  public String entityName() {
    return "Game";
  }
  
  @Override 
  public Map<String, Object> serialize() {
    Map<String, Object> result = new HashMap<String, Object>();
    result.put("id", getId());
    result.put("players", getPlayersMutable());
    result.put("profiles", serializeProfiles());
    result.put("localProfiles", serializeLocalProfiles());
    result.put("currentPlayerNumber", getCurrentPlayerNumber());
    result.put("actions", serializeEntities(getActionsMutable()));
    result.put("currentActionNumber", getCurrentActionNumber());
    result.put("lastModified", getLastModified());
    result.put("requestId", getRequestId());
    result.put("victors", getVictorsMutable());
    result.put("gameOver", gameOver);
    result.put("localMultiplayer", localMultiplayer);
    result.put("isMinimal", isMinimal);
    result.put("resignedPlayers", getResignedPlayersMutable());
    return result;
  }

  public boolean hasCurrentAction() {
    return getCurrentActionNumber() != null;
  }
  
  public Action currentAction() {
    return getActions().get(getCurrentActionNumber());
  }
  
  public boolean isGameOver() {
    return gameOver;
  }
  
  public boolean isLocalMultiplayer() {
    return localMultiplayer;
  }
  
  public boolean isMinimal() {
    return isMinimal;
  }

  Game setGameOver(boolean gameOver) {
    this.gameOver = gameOver;
    return this;
  }

  Game setLocalMultiplayer(boolean localMultiplayer) {
    this.localMultiplayer = localMultiplayer;
    return this;
  }

  public String getId() {
    return id;
  }
  
  public List<String> getPlayers() {
    return Collections.unmodifiableList(getPlayersMutable());
  }

  List<String> getPlayersMutable() {
    return players;
  }

  public Map<String, Profile> getProfiles() {
    return Collections.unmodifiableMap(getProfilesMutable());
  }
  
  Map<String, Profile> getProfilesMutable() {
    return profiles;
  }
  
  public Map<Integer, Profile> getLocalProfiles() {
    return Collections.unmodifiableMap(getLocalProfilesMutable());
  }
  
  Map<Integer, Profile> getLocalProfilesMutable() {
    return localProfiles;
  }

  public Integer getCurrentPlayerNumber() {
    return currentPlayerNumber;
  }

  Game setCurrentPlayerNumber(Integer currentPlayerNumber) {
    this.currentPlayerNumber = currentPlayerNumber;
    return this;
  }

  public List<Action> getActions() {
    return Collections.unmodifiableList(getActionsMutable());
  }
  
  List<Action> getActionsMutable() {
    return actions;
  }

  public Integer getCurrentActionNumber() {
    return currentActionNumber;
  }

  Game setCurrentActionNumber(Integer currentActionNumber) {
    this.currentActionNumber = currentActionNumber;
    return this;
  }

  public Long getLastModified() {
    return lastModified;
  }

  Game setLastModified(Long lastModified) {
    this.lastModified = lastModified;
    return this;
  }

  public String getRequestId() {
    return requestId;
  }

  Game setRequestId(String requestId) {
    this.requestId = requestId;
    return this;
  }
  
  public List<Integer> getVictors() {
    return Collections.unmodifiableList(getVictorsMutable());
  }

  List<Integer> getVictorsMutable() {
    return victors;
  }
  
  public List<String> getResignedPlayers() {
    return Collections.unmodifiableList(getResignedPlayersMutable());
  }

  List<String> getResignedPlayersMutable() {
    return resignedPlayers;
  }

  @Override
  public int compareTo(Game other) {
    if (other == null) {
      throw new NullPointerException("Null game in compareTo()");
    } else if (equals(other)) {
      return 0;
    } else if (lastModified < other.lastModified) {
      return 1;
    } else if (lastModified > other.lastModified) {
      return -1;
    } else {
      // Different games, same lastModified, order by hashCode
      return other.hashCode() - hashCode();
    }
  }

  /**
   * @return The ID of the current player, or null if there is no current
   *     player.
   */
  public String currentPlayerId() {
    if (getCurrentPlayerNumber() == null) return null;
    return getPlayerIdFromPlayerNumber(currentPlayerNumber);
  }
  
  /**
   * @param playerNumber A player's player number
   * @return That player's player ID
   * @throw {@link IndexOutOfBoundsException} if the player number is not
   *     currently in the game.
   */
  public String getPlayerIdFromPlayerNumber(int playerNumber) {
    return players.get(playerNumber);
  }
  
  /**
   * @param playerId A player ID
   * @return All player numbers (if any) associated with this player ID
   */
  public List<Integer> getPlayerNumbersForPlayerId(String playerId) {
    if (playerId == null) throw new IllegalArgumentException("Null playerId");
    List<Integer> results = new ArrayList<Integer>();
    for (int i = 0; i < players.size(); ++i) {
      if (players.get(i).equals(playerId)) {
        results.add(i);
      }
    }
    return results;
  }
  
  /**
   * @param viewerId viewer's player ID
   * @return True if there is an opponent in this game who is distinct from
   *     the viewer. False if there's no opponent or the viewer is playing
   *     both sides in this game. 
   */
  public boolean hasOpponent(String viewerId) {
    return players.size() == 2 && !players.get(0).equals(players.get(1));
  }

  /**
   * @param viewerId viewer's player ID
   * @return The player number of your opponent.
   * @throw IllegalStateException If there is no opponent as defined by
   *     {@link Game#hasOpponent(String)}.
   */
  public int getOpponentPlayerNumber(String viewerId) {
    if (!hasOpponent(viewerId)) {
      throw new IllegalStateException("No opponent or viewer is both players.");
    } else if (players.get(0).equals(viewerId)) {
      return 1;
    } else {
      return 0;
    }
  }
  
  /**
   *
   * @param viewerId viewer's player ID
   * @return True if the game has an opponent who has a profile, false
   *     otherwise.
   */
  public boolean hasOpponentProfile(String viewerId) {
    if (!hasOpponent(viewerId)) return false;
    int opponentNumber = getOpponentPlayerNumber(viewerId);
    if (localProfiles.containsKey(opponentNumber)) {
      return true;
    }
    String opponentId = getPlayerIdFromPlayerNumber(opponentNumber);
    return profiles.containsKey(opponentId);
  }

  /**
   * @param viewerId viewer's player ID
   * @return The profile of your opponent or null if there isn't one. Local
   *     profiles take precedence over regular profiles.
   * @throw IllegalStateException If there is no opponent as defined by
   *     {@link Game#hasOpponent(String)} or there is no opponent profile.
   */
  public Profile getOpponentProfile(String viewerId) {
    if (!hasOpponentProfile(viewerId)) {
      throw new IllegalStateException("No opponent profile found.");
    }
    int opponentNumber = getOpponentPlayerNumber(viewerId);
    if (localProfiles.containsKey(opponentNumber)) {
      return localProfiles.get(opponentNumber);
    } else {
      String opponentId = getPlayerIdFromPlayerNumber(opponentNumber);
      return profiles.get(opponentId);
    }
  }

  /**
   * @param playerNumber A player number current in this game.
   * @return The Profile object for that player, with local profiles taking
   *     precedence over regular profiles.
   * @throw IllegalArgumentException If there is no profile for this player.
   */
  public Profile getPlayerProfile(int playerNumber) {
    if (localProfiles.containsKey(playerNumber)) {
      return localProfiles.get(playerNumber);
    }
    Profile result = profiles.get(getPlayerIdFromPlayerNumber(playerNumber));
    if (result != null) {
      return result;
    } else {
      throw new IllegalArgumentException("No profile for player " + playerNumber);
    }
  }
  
  /**
   * @return A GameStatus object summarizing whose turn it is in the game (or
   *     if the game is over), along with an associated photo string and player
   *     number.
   */
  public GameStatus getGameStatus() {
    if (isGameOver()) {
      if (getVictors().size() == 1) {
        int winnerNumber = getVictors().get(0);
        Profile winnerProfile = getPlayerProfile(winnerNumber);
        String winner = winnerProfile.getName();
        return new GameStatus(winner + " won the game!", winnerProfile.getPhotoString(),
            !isLocalMultiplayer(), winnerNumber);
      } else {
        return new GameStatus("Game drawn.", GAME_OVER_PHOTO_STRING, false /* photoIsUrl */,
            GameStatus.NO_PLAYER_NUMBER);
      }
    } else {
      Profile currentPlayerProfile = getPlayerProfile(getCurrentPlayerNumber());
      return new GameStatus(currentPlayerProfile.getName() + "'s turn",
          currentPlayerProfile.getPhotoString(), !isLocalMultiplayer() /* photoIsUrl*/,
          getCurrentPlayerNumber());
    }
  }

  /**
   * @param viewerId viewer's player ID
   * @return A string describing the opponent of this game, such as
   *     "vs. Frank".
   */
  public String vsString(String viewerId) {
    if (isLocalMultiplayer()) {
      if (localProfiles.size() == 2) {
        return localProfiles.get(0).getName() + " vs. " + localProfiles.get(1).getName();
      } else {
        return "Local Multiplayer Game";
      }
    }
    else if (hasOpponentProfile(viewerId)) {
      return "vs. " + getOpponentProfile(viewerId).getName();
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
  private String timeAgoString(String viewerId, long number, String unit) {
    String statusString;
    if (isGameOver()) {
      if (isLocalMultiplayer()) {
        statusString = "Game ended";
      } else {
        List<Integer> playerNumbers = getPlayerNumbersForPlayerId(viewerId);
        if (victors.size() == 2) {
          statusString = "Game tied";
        } else if (playerNumbers.size() == 1 && victors.contains(playerNumbers.get(0))) {
          statusString = "You won";
        } else if (victors.contains(getOpponentPlayerNumber(viewerId))){
          if (hasOpponentProfile(viewerId)) {
            Profile opponentProfile = getOpponentProfile(viewerId);
            statusString = opponentProfile.getNominativePronoun(true /* capitalize */) + " won";
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
  public String lastUpdatedString(String viewerId) {
    long duration = Math.max(Clock.getInstance().currentTimeMillis() - lastModified, 0);
    long number;
    number = duration / ONE_YEAR;
    if (number > 0) {
      return timeAgoString(viewerId, number, "year");
    }
    number = duration / ONE_MONTH;
    if (number > 0) {
      return timeAgoString(viewerId, number, "month");
    }
    number = duration / ONE_WEEK;
    if (number > 0) {
      return timeAgoString(viewerId, number, "week");
    }
    number = duration / ONE_DAY;
    if (number > 0) {
      return timeAgoString(viewerId, number, "day");
    }
    number = duration / ONE_HOUR;
    if (number > 0) {
      return timeAgoString(viewerId, number, "hour");
    }
    number = duration / ONE_MINUTE;
    if (number > 0) {
      return timeAgoString(viewerId, number, "minute");
    }
    number = duration / ONE_SECOND;
    return timeAgoString(viewerId, number, "second");
  }
  
  /**
   * @return A list of photo strings to use to represent this game in the game
   *     list.
   */
  public List<String> photoList(String viewerId) {
    List<String> result = new ArrayList<String>();
    if (isLocalMultiplayer()) {
      for (Profile profile : localProfiles.values()) {
        result.add(profile.getPhotoString());
      }
    } else {
      if (hasOpponentProfile(viewerId)) {
        result.add(getOpponentProfile(viewerId).getPhotoString());      
      } else {
        result.add(NO_OPPONENT_PHOTO_STRING);
      }
      
    }
    return result;
  }
  
  /**
   * @return A representation of this game suitable for inclusion in a user's
   *     game list, with the game actions omitted.
   */
  public Game minimalGame() {
    Map<String, Object> serialized = serialize();
    serialized.remove("actions");
    serialized.remove("currentActionNumber");
    serialized.put("isMinimal", true);
    return new Game(serialized);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Game copy() {
    return new Game(serialize());
  }
}
