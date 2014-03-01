package ca.thurn.noughts.shared;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.thurn.uct.core.Copyable;

// 2 fields:
// under construction action
// submitted actions

public class Game extends Entity<Game> implements Comparable<Game>, Copyable {
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

  public static class Deserializer extends EntityDeserializer<Game> {
    private Deserializer() {
    }

    @Override 
    Game deserialize(Map<String, Object> gameMap) {
      return new Game(gameMap);
    }    
  }  

  public static class Builder implements EntityBuilder<Game> {
    private final Game game;
    
    private Builder() {
      this.game = new Game();
    }
    
    private Builder(Game game) {
      this.game = new Game(game);
    }
    
    @Override
    public Game build() {
      return new Game(game);
    }

    public boolean hasId() {
      return game.hasId();
    }

    public String getId() {
      return game.getId();
    }
    
    public Builder setId(String id) {
      checkNotNull(id);
      game.id = id;
      return this;
    }
    
    public Builder clearId() {
      game.id = null;
      return this;
    }

    public int getPlayerCount() {
      return game.getPlayerCount();
    }

    public String getPlayer(int index) {
      return game.getPlayer(index);
    }

    public List<String> getPlayerList() {
      return game.players;
    }
    
    public Builder setPlayer(int index, String player) {
      checkNotNull(player);
      game.players.set(index, player);
      return this;
    }
    
    public Builder addPlayer(String player) {
      checkNotNull(player);
      game.players.add(player);
      return this;
    }
    
    public Builder addAllPlayer(List<String> playerList) {
      checkListForNull(playerList);
      game.players.addAll(playerList);
      return this;
    }
    
    public Builder clearPlayerList() {
      game.players.clear();
      return this;
    }

    public int getProfileCount() {
      return game.getProfileCount();
    }

    public Profile getProfile(String key) {
      return game.getProfile(key);
    }

    public Map<String, Profile> getProfileMap() {
      return game.profiles;
    }
    
    public Builder putProfile(String key, EntityBuilder<Profile> profile) {
      return putProfile(key, profile.build());
    }
    
    public Builder putProfile(String key, Profile profile) {
      checkNotNull(key);
      checkNotNull(profile);
      game.profiles.put(key, profile);
      return this;
    }
    
    public Builder putAllProfile(Map<String, Profile> profileMap) {
      checkMapForNull(profileMap);
      game.profiles.putAll(profileMap);
      return this;
    }
    
    public Builder clearProfileMap() {
      game.profiles.clear();
      return this;
    }

    public int getLocalProfileCount() {
      return game.getLocalProfileCount();
    }

    public Profile getLocalProfile(int index) {
      return game.getLocalProfile(index);
    }

    public List<Profile> getLocalProfileList() {
      return game.localProfiles;
    }
    
    public Builder setLocalProfile(int index, EntityBuilder<Profile> profile) {
      return setLocalProfile(index, profile.build());
    }
    
    public Builder setLocalProfile(int index, Profile profile) {
      checkNotNull(profile);
      game.localProfiles.set(index, profile);
      return this;
    }
    
    public Builder addLocalProfile(EntityBuilder<Profile> profile) {
      return addLocalProfile(profile.build());
    }
    
    public Builder addLocalProfile(Profile localProfile) {
      checkNotNull(localProfile);
      game.localProfiles.add(localProfile);
      return this;
    }
    
    public Builder addAllLocalProfile(List<Profile> localProfileList) {
      checkListForNull(localProfileList);
      game.localProfiles.addAll(localProfileList);
      return this;
    }
    
    public Builder clearLocalProfileList() {
      game.localProfiles.clear();
      return this;
    }

    public boolean hasCurrentPlayerNumber() {
      return game.hasCurrentPlayerNumber();
    }

    public int getCurrentPlayerNumber() {
      return game.getCurrentPlayerNumber();
    }
    
    public Builder setCurrentPlayerNumber(int currentPlayerNumber) {
      game.currentPlayerNumber = currentPlayerNumber;
      return this;
    }
    
    public Builder clearCurrentPlayerNumber() {
      game.currentPlayerNumber = null;
      return this;
    }

    public int getActionCount() {
      return game.getActionCount();
    }

    public Action getAction(int index) {
      return game.getAction(index);
    }

    public List<Action> getActionList() {
      return game.actions;
    }
    
    public Builder setAction(int index, EntityBuilder<Action> action) {
      return setAction(index, action.build());
    }
    
    public Builder setAction(int index, Action action) {
      checkNotNull(action);
      game.actions.set(index, action);
      return this;
    }
    
    public Builder addAction(EntityBuilder<Action> action) {
      return addAction(action.build());
    }
    
    public Builder addAction(Action action) {
      checkNotNull(action);
      game.actions.add(action);
      return this;
    }
    
    public Builder addAllAction(List<Action> actionList) {
      checkListForNull(actionList);
      game.actions.addAll(actionList);
      return this;
    }
    
    public Builder clearActionList() {
      game.actions.clear();
      return this;
    }

    public boolean hasCurrentActionNumber() {
      return game.hasCurrentActionNumber();
    }

    public int getCurrentActionNumber() {
      return game.getCurrentActionNumber();
    }
    
    public Builder setCurrentActionNumber(int currentActionNumber) {
      game.currentActionNumber = currentActionNumber;
      return this;
    }
    
    public Builder clearCurrentActionNumber() {
      game.currentActionNumber = null;
      return this;
    }

    public boolean hasLastModified() {
      return game.hasLastModified();
    }

    public long getLastModified() {
      return game.getLastModified();
    }
    
    public Builder setLastModified(long lastModified) {
      game.lastModified = lastModified;
      return this;
    }
    
    public Builder clearLastModified() {
      game.lastModified = null;
      return this;
    }

    public boolean hasRequestId() {
      return game.hasRequestId();
    }

    public String getRequestId() {
      return game.getRequestId();
    }
    
    public Builder setRequestId(String requestId) {
      checkNotNull(requestId);
      game.requestId = requestId;
      return this;
    }
    
    public Builder clearRequestId() {
      game.requestId = null;
      return this;
    }

    public int getVictorCount() {
      return game.getVictorCount();
    }

    public int getVictor(int index) {
      return game.getVictor(index);
    }

    public List<Integer> getVictorList() {
      return game.victors;
    }
    
    public Builder setVictor(int index, int victor) {
      game.victors.set(index, victor);
      return this;
    }
    
    public Builder addVictor(int victor) {
      game.victors.add(victor);
      return this;
    }
    
    public Builder addAllVictor(List<Integer> victorList) {
      checkListForNull(victorList);
      game.victors.addAll(victorList);
      return this;
    }
    
    public Builder clearVictorList() {
      game.victors.clear();
      return this;
    }

    public boolean hasGameOver() {
      return game.hasGameOver();
    }

    public boolean isGameOver() {
      return game.isGameOver();
    }
    
    public Builder setGameOver(boolean gameOver) {
      game.gameOver = gameOver;
      return this;
    }
    
    public Builder clearGameOver() {
      game.gameOver = null;
      return this;
    }

    public boolean hasLocalMultiplayer() {
      return game.hasLocalMultiplayer();
    }

    public boolean isLocalMultiplayer() {
      return game.isLocalMultiplayer();
    }
    
    public Builder setLocalMultiplayer(boolean localMultiplayer) {
      game.localMultiplayer = localMultiplayer;
      return this;
    }
    
    public Builder clearLocalMultiplayer() {
      game.localMultiplayer = null;
      return this;
    }
    
    public boolean hasMinimal() {
      return game.hasMinimal();
    }

    public boolean isMinimal() {
      return game.isMinimal();
    }
    
    public Builder setMinimal(boolean isMinimal) {
      game.minimal = isMinimal;
      return this;
    }
    
    public Builder clearMinimal() {
      game.minimal = null;
      return this;
    }

    public int getResignedPlayerCount() {
      return game.getResignedPlayerCount();
    }

    public int getResignedPlayer(int index) {
      return game.getResignedPlayer(index);
    }

    public List<Integer> getResignedPlayerList() {
      return game.resignedPlayers;
    }
    
    public Builder setResignedPlayer(int index, int resignedPlayer) {
      game.resignedPlayers.set(index, resignedPlayer);
      return this;
    }
    
    public Builder addResignedPlayer(int resignedPlayer) {
      game.resignedPlayers.add(resignedPlayer);
      return this;
    }
    
    public Builder addAllResignedPlayer(List<Integer> resignedPlayer) {
      checkListForNull(resignedPlayer);
      game.resignedPlayers.addAll(resignedPlayer);
      return this;
    }
    
    public Builder clearResignedPlayerList() {
      game.resignedPlayers.clear();
      return this;
    }
  }
  
  public static Builder newBuilder() {
    return new Builder();
  }
  
  public static Builder newBuilder(Game game) {
    return new Builder(game);
  }
  
  public static Deserializer newDeserializer() {
    return new Deserializer();
  }
  
  /**
   * The game ID
   */
  private String id;

  /**
   * An array of the players in the game, which can be though of as a bimap
   * from Player Number to Player ID. A player who leaves the game will have
   * their entry in this array replaced with null.
   */
  private final List<String> players;

  /**
   * A mapping from player IDs to profile information about the player.
   */
  private final Map<String, Profile> profiles;
  
  /**
   * List of player profiles in the same order as the player list, these
   * profiles takes precedence over the ID-based profiles above. Null
   * indicates a missing profile.
   */
  private final List<Profile> localProfiles;

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
  private Boolean gameOver;

  /**
   * True if this game is in local multiplayer mode
   */
  private Boolean localMultiplayer;
  
  /**
   * True if this is a minimal game representation, as returned by
   * {@link Game#minimalGame()}.
   */
  private Boolean minimal;

  /**
   * An array of player numbers who have resigned the game.
   */
  private final List<Integer> resignedPlayers;
  
  public Game() {
    players = new ArrayList<String>();
    profiles = new HashMap<String, Profile>();
    localProfiles = new ArrayList<Profile>();
    actions = new ArrayList<Action>();
    victors = new ArrayList<Integer>();
    resignedPlayers = new ArrayList<Integer>();
  }
  
  public Game(Game game) {
    this.id = game.id;
    this.players = new ArrayList<String>(game.players);
    this.profiles = new HashMap<String, Profile>(game.profiles);
    this.localProfiles = new ArrayList<Profile>(game.localProfiles);
    this.currentPlayerNumber = game.currentPlayerNumber;
    this.actions = new ArrayList<Action>(game.actions);
    this.currentActionNumber = game.currentActionNumber;
    this.lastModified = game.lastModified;
    this.requestId = game.requestId;
    this.victors = new ArrayList<Integer>(game.victors);
    this.gameOver = game.gameOver;
    this.localMultiplayer = game.localMultiplayer;
    this.minimal = game.minimal;
    this.resignedPlayers = new ArrayList<Integer>(game.resignedPlayers);
  }

  private Game(Map<String, Object> gameMap) {
    checkExists(gameMap, "id");
    id = getString(gameMap, "id");
    players = getList(gameMap, "players");
    profiles = getEntityMap(gameMap, "profiles", Profile.newDeserializer());
    localProfiles = getEntities(gameMap, "localProfiles", Profile.newDeserializer());
    currentPlayerNumber = getInteger(gameMap, "currentPlayerNumber");
    actions = getEntities(gameMap, "actions", Action.newDeserializer());
    currentActionNumber = getInteger(gameMap, "currentActionNumber");
    lastModified = getLong(gameMap, "lastModified");
    requestId = getString(gameMap, "requestId");
    victors = getIntegerList(getList(gameMap, "victors"));
    gameOver = getBoolean(gameMap, "gameOver");
    localMultiplayer = getBoolean(gameMap, "localMultiplayer");
    minimal = getBoolean(gameMap, "isMinimal");
    resignedPlayers = getIntegerList(getList(gameMap, "resignedPlayers"));
  }
  
  @Override
  public String entityName() {
    return "Game";
  }  
  
  @Override
  Map<String, Object> serialize() {
    Map<String, Object> result = new HashMap<String, Object>();
    putSerialized(result, "id", id);
    putSerialized(result, "players", players);
    putSerialized(result, "profiles", profiles);
    putSerialized(result, "localProfiles", localProfiles);
    putSerialized(result, "currentPlayerNumber", currentPlayerNumber);
    putSerialized(result, "actions", actions);
    putSerialized(result, "currentActionNumber", currentActionNumber);
    putSerialized(result, "lastModified", lastModified);
    putSerialized(result, "requestId", requestId);
    putSerialized(result, "victors", victors);
    putSerialized(result, "gameOver", gameOver);
    putSerialized(result, "localMultiplayer", localMultiplayer);
    putSerialized(result, "isMinimal", minimal);
    putSerialized(result, "resignedPlayers", resignedPlayers);
    return result;
  }
  
  @Override
  public Builder toBuilder() {
    return new Builder(this);
  }
  
  public boolean hasId() {
    return id != null;
  }
  
  public String getId() {
    checkNotNull(id);
    return id;
  }
  
  public int getPlayerCount() {
    return players.size();
  }
  
  public String getPlayer(int index) {
    return players.get(index);
  }
  
  public List<String> getPlayerList() {
    return Collections.unmodifiableList(players);
  }
  
  public int getProfileCount() {
    return profiles.size();
  }
  
  public Profile getProfile(String key) {
    checkNotNull(key);
    return profiles.get(key);
  }
  
  public Map<String, Profile> getProfileMap() {
    return Collections.unmodifiableMap(profiles);
  }
  
  public int getLocalProfileCount() {
    return localProfiles.size();
  }
  
  public Profile getLocalProfile(int index) {
    return localProfiles.get(index);
  }
  
  public List<Profile> getLocalProfileList() {
    return Collections.unmodifiableList(localProfiles);
  }
  
  public boolean hasCurrentPlayerNumber() {
    return currentPlayerNumber != null;
  }
  
  public int getCurrentPlayerNumber() {
    checkNotNull(currentPlayerNumber);
    return currentPlayerNumber;
  }
  
  public int getActionCount() {
    return actions.size();
  }
  
  public Action getAction(int index) {
    return actions.get(index);
  }
  
  public List<Action> getActionList() {
    return Collections.unmodifiableList(actions);
  }
  
  public boolean hasCurrentActionNumber() {
    return currentActionNumber != null;
  }
  
  public int getCurrentActionNumber() {
    checkNotNull(currentActionNumber);
    return currentActionNumber;
  }
  
  public boolean hasLastModified() {
    return lastModified != null;
  }
  
  public long getLastModified() {
    checkNotNull(lastModified);
    return lastModified;
  }
  
  public boolean hasRequestId() {
    return requestId != null;
  }
  
  public String getRequestId() {
    checkNotNull(requestId);
    return requestId;
  }
  
  public int getVictorCount() {
    return victors.size();
  }
  
  public int getVictor(int index) {
    return victors.get(index);
  }
  
  public List<Integer> getVictorList() {
    return Collections.unmodifiableList(victors);
  }
  
  public boolean hasGameOver() {
    return gameOver != null;
  }
  
  public boolean isGameOver() {
    checkNotNull(gameOver);
    return gameOver;
  }
  
  public boolean hasLocalMultiplayer() {
    return localMultiplayer != null;
  }
  
  public boolean isLocalMultiplayer() {
    checkNotNull(localMultiplayer);
    return localMultiplayer;
  }
  
  public boolean hasMinimal() {
    return minimal != null;
  }
  
  public boolean isMinimal() {
    checkNotNull(minimal);
    return minimal;
  }
  
  public int getResignedPlayerCount() {
    return resignedPlayers.size();
  }
  
  public int getResignedPlayer(int index) {
    return resignedPlayers.get(index);
  }
  
  public List<Integer> getResignedPlayerList() {
    return Collections.unmodifiableList(resignedPlayers);
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
    if (!hasCurrentPlayerNumber()) return null;
    return playerIdFromPlayerNumber(currentPlayerNumber);
  }
  
  /**
   * @param playerNumber A player's player number
   * @return That player's player ID
   * @throws IllegalArgumentException if the player number is not currently in
   *     the game.
   */
  public String playerIdFromPlayerNumber(int playerNumber) {
    try {
      return players.get(playerNumber);
    } catch (IndexOutOfBoundsException exception) {
      throw new IllegalArgumentException(exception);
    }
  }
  
  /**
   * @param playerId A player ID
   * @return All player numbers (if any) associated with this player ID
   */
  public List<Integer> playerNumbersForPlayerId(String playerId) {
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
   * @throws IllegalStateException If there is no opponent as defined by
   *     {@link Game#hasOpponent(String)}.
   */
  public int opponentPlayerNumber(String viewerId) {
    if (!hasOpponent(viewerId)) {
      throw new IllegalStateException("No opponent or viewer is both players.");
    } else if (players.get(0).equals(viewerId)) {
      return 1;
    } else {
      return 0;
    }
  }
  
  /**
   * @param playerNumber A player number.
   * @return True if the provided player has a local profile.
   */
  public boolean hasLocalProfile(int playerNumber) {
    if (playerNumber < 0 || playerNumber >= localProfiles.size()) return false;
    return localProfiles.get(playerNumber) != null;
  }
  
  /**
   * @param playerNumber A player number.
   * @return The local profile for this player.
   * @throws IllegalArgumentException if this player does not have a local
   *     profile as defined by {@link Game#hasLocalProfile(int)}.
   */
  public Profile xgetLocalProfile(int playerNumber) {
    if (!hasLocalProfile(playerNumber)) {
      throw new IllegalArgumentException("No profile for player " + playerNumber);
    }
    return localProfiles.get(playerNumber);
  }
  
  /**
   *
   * @param viewerId viewer's player ID
   * @return True if the game has an opponent who has a profile, false
   *     otherwise.
   */
  public boolean hasOpponentProfile(String viewerId) {
    if (!hasOpponent(viewerId)) return false;
    int opponentNumber = opponentPlayerNumber(viewerId);
    if (hasLocalProfile(opponentNumber)) {
      return true;
    }
    String opponentId = playerIdFromPlayerNumber(opponentNumber);
    return profiles.containsKey(opponentId);
  }

  /**
   * @param viewerId viewer's player ID
   * @return The profile of your opponent or null if there isn't one. Local
   *     profiles take precedence over regular profiles.
   * @throws IllegalStateException If there is no opponent as defined by
   *     {@link Game#hasOpponent(String)} or there is no opponent profile.
   */
  public Profile opponentProfile(String viewerId) {
    if (!hasOpponentProfile(viewerId)) {
      throw new IllegalStateException("No opponent profile found.");
    }
    int opponentNumber = opponentPlayerNumber(viewerId);
    if (hasLocalProfile(opponentNumber)) {
      return getLocalProfile(opponentNumber);
    } else {
      String opponentId = playerIdFromPlayerNumber(opponentNumber);
      return profiles.get(opponentId);
    }
  }

  /**
   * @param playerNumber A player number currently in this game.
   * @return The Profile object for that player, with local profiles taking
   *     precedence over regular profiles.
   * @throws IllegalArgumentException If there is no profile for this player.
   */
  public Profile playerProfile(int playerNumber) {
    if (hasLocalProfile(playerNumber)) {
      return getLocalProfile(playerNumber);
    }
    Profile result = profiles.get(playerIdFromPlayerNumber(playerNumber));
    if (result != null) {
      return result;
    } else {
      throw new IllegalArgumentException("No profile for player " + playerNumber);
    }
  }
  
  /**
   * @return A GameStatus object summarizing whose turn it is in the game (or
   *     if the game is over), along with an associated image string and player
   *     number.
   */
  public GameStatus gameStatus() {
    if (isGameOver()) {
      if (getVictorCount() == 1) {
        int winnerNumber = getVictor(0);
        Profile winnerProfile = playerProfile(winnerNumber);
        String winner = winnerProfile.getName();
        ImageString imageString = winnerProfile.hasImageString() ?
            winnerProfile.getImageString() : ANONYMOUS_OPPONENT_IMAGE_STRING;  
        return GameStatus.newBuilder()
            .setStatusString(winner + " won the game!")
            .setStatusImageString(imageString)
            .setStatusPlayer(winnerNumber)
            .build();
      } else {
        return GameStatus.newBuilder()
            .setStatusString("Game drawn.")
            .setStatusImageString(GAME_OVER_IMAGE_STRING)
            .build();
      }
    } else {
      Profile currentPlayerProfile = playerProfile(getCurrentPlayerNumber());
      ImageString imageString = currentPlayerProfile.hasImageString() ?
          currentPlayerProfile.getImageString() : ANONYMOUS_OPPONENT_IMAGE_STRING;
      return GameStatus.newBuilder()
          .setStatusString(currentPlayerProfile.getName() + "'s turn")
          .setStatusImageString(imageString)
          .setStatusPlayer(getCurrentPlayerNumber())
          .build();
    }
  }
  
  public GameListEntry gameListEntry(String viewerId) {
    return GameListEntry.newBuilder()
        .setVsString(viewerId)
        .setModifiedString(lastUpdatedString(viewerId))
        .addAllImageString(imageList(viewerId))
        .build();
  }

  /**
   * @param viewerId viewer's player ID
   * @return A string describing the opponent of this game, such as
   *     "vs. Frank".
   */
  String vsString(String viewerId) {
    if (isLocalMultiplayer()) {
      if (localProfiles.size() == 2) {
        return getLocalProfile(0).getName() + " vs. " + getLocalProfile(1).getName();
      } else {
        return "Local Multiplayer Game";
      }
    }
    else if (hasOpponentProfile(viewerId)) {
      return "vs. " + opponentProfile(viewerId).getName();
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
        List<Integer> playerNumbers = playerNumbersForPlayerId(viewerId);
        if (victors.size() == 2) {
          statusString = "Game tied";
        } else if (playerNumbers.size() == 1 && victors.contains(playerNumbers.get(0))) {
          statusString = "You won";
        } else if (victors.contains(opponentPlayerNumber(viewerId))){
          if (hasOpponentProfile(viewerId)) {
            Profile opponentProfile = opponentProfile(viewerId);
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
  String lastUpdatedString(String viewerId) {
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
   * @return A list of image strings to use to represent this game in the game
   *     list.
   */
  List<ImageString> imageList(String viewerId) {
    List<ImageString> result = new ArrayList<ImageString>();
    if (isLocalMultiplayer()) {
      for (Profile profile : localProfiles) {
        result.add(profile.getImageString());
      }
    } else {
      if (hasOpponentProfile(viewerId)) {
        result.add(opponentProfile(viewerId).getImageString());      
      } else {
        result.add(NO_OPPONENT_IMAGE_STRING);
      }
    }
    return result;
  }
  public Action currentAction() {
    return getAction(getCurrentActionNumber());    
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
