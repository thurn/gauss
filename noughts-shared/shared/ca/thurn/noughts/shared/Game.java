package ca.thurn.noughts.shared;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.thurn.noughts.shared.Action.ActionDeserializer;

public class Game extends Entity {
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
  private final Map<String, Map<String, String>> profiles;

  /**
   * The number of the player whose turn it is, that is, their index within
   * the players array. -1 when the game is not in progress.
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
   * List of IDs of the players who won this game. In the case of a draw, it
   * should contain all of the drawing players. In the case of a "nobody
   * wins" situation, an empty list should be present. This field cannot be
   * present on a game which is still in progress.
   */
  private final List<String> victors;

  /**
   * True if this game has ended.
   */
  private Boolean gameOver;

  /**
   * True if this game is in local multiplayer mode
   */
  private Boolean localMultiplayer;

  /**
   * An array of player IDs who have resigned the game.
   */
  private final List<String> resignedPlayers;
  
  public Game(String id) {
    players = new ArrayList<String>();
    profiles = new HashMap<String, Map<String, String>>();
    actions = new ArrayList<Action>();
    victors = new ArrayList<String>();
    resignedPlayers = new ArrayList<String>();
    this.id = id;
  }

  public Game(Map<String, Object> gameMap) {
    id = getString(gameMap, "id");
    players = getList(gameMap, "players");
    profiles = getMap(gameMap, "profiles");
    setCurrentPlayerNumber(getInteger(gameMap, "currentPlayerNumber"));
    actions = getEntities(gameMap, "actions", new ActionDeserializer());
    setCurrentActionNumber(getInteger(gameMap, "currentActionNumber"));
    setLastModified(getLong(gameMap, "lastModified"));
    setRequestId(getString(gameMap, "requestId"));
    victors = getList(gameMap, "victors");
    gameOver = getBoolean(gameMap, "gameOver");
    localMultiplayer = getBoolean(gameMap, "localMultiplayer");
    resignedPlayers = getList(gameMap, "resignedPlayers");
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
    result.put("profiles", getProfilesMutable());
    result.put("currentPlayerNumber", getCurrentPlayerNumber());
    result.put("actions", serializeEntities(getActionsMutable()));
    result.put("currentActionNumber", getCurrentActionNumber());
    result.put("lastModified", getLastModified());
    result.put("requestId", getRequestId());
    result.put("victors", getVictorsMutable());
    result.put("gameOver", gameOver);
    result.put("localMultiplayer", localMultiplayer);
    result.put("resignedPlayers", getResignedPlayersMutable());
    return result;
  }
  
  public String currentPlayerId() {
    if (getCurrentPlayerNumber() == null) return null;
    return getPlayersMutable().get(getCurrentPlayerNumber());
  }
  
  public boolean hasCurrentAction() {
    return getCurrentActionNumber() != null;
  }
  
  public Action currentAction() {
    return getActionsMutable().get(getCurrentActionNumber());
  }
  
  public boolean isGameOver() {
    return gameOver != null && gameOver == true;
  }
  
  public boolean isLocalMultiplayer() {
    return localMultiplayer != null && localMultiplayer == true;
  }

  void setGameOver(Boolean gameOver) {
    this.gameOver = gameOver;
  }

  void setLocalMultiplayer(Boolean localMultiplayer) {
    this.localMultiplayer = localMultiplayer;
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

  public Map<String, Map<String, String>> getProfiles() {
    return Collections.unmodifiableMap(getProfilesMutable());
  }
  
  Map<String, Map<String, String>> getProfilesMutable() {
    return profiles;
  }

  public Integer getCurrentPlayerNumber() {
    return currentPlayerNumber;
  }

  void setCurrentPlayerNumber(Integer currentPlayerNumber) {
    this.currentPlayerNumber = currentPlayerNumber;
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

  void setCurrentActionNumber(Integer currentActionNumber) {
    this.currentActionNumber = currentActionNumber;
  }

  public Long getLastModified() {
    return lastModified;
  }

  void setLastModified(Long lastModified) {
    this.lastModified = lastModified;
  }

  public String getRequestId() {
    return requestId;
  }

  void setRequestId(String requestId) {
    this.requestId = requestId;
  }
  
  public List<String> getVictors() {
    return Collections.unmodifiableList(getVictorsMutable());
  }

  List<String> getVictorsMutable() {
    return victors;
  }
  
  public List<String> getResignedPlayers() {
    return Collections.unmodifiableList(getResignedPlayers());
  }

  List<String> getResignedPlayersMutable() {
    return resignedPlayers;
  }
}
