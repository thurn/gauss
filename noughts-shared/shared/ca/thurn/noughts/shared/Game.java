package ca.thurn.noughts.shared;

import java.util.ArrayList;
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
  public final String id;

  /**
   * An array of the players in the game, which can be though of as a bimap
   * from Player Number to Player ID. A player who leaves the game will have
   * her entry in this array replaced with null.
   */
  public final List<String> players;

  /**
   * A mapping from player IDs to profile information about the player.
   */
  public final Map<String, Map<String, String>> profiles;

  /**
   * The number of the player whose turn it is, that is, their index within
   * the players array. -1 when the game is not in progress.
   */
  public Integer currentPlayerNumber;

  /**
   * Actions taken in this game, in the order in which they were taken.
   * Potentially includes an unsubmitted action of the current player.
   */
  public final List<Action> actions;

  /**
   * Index in actions list of action currently being constructed, null when
   * there is no current action.
   */
  public Integer currentActionNumber;

  /**
   * UNIX timestamp of time when game was last modified.
   */
  public Long lastModified;

  /**
   * Facebook request ID associated with this game.
   */
  public String requestId;

  /**
   * List of IDs of the players who won this game. In the case of a draw, it
   * should contain all of the drawing players. In the case of a "nobody
   * wins" situation, an empty list should be present. This field cannot be
   * present on a game which is still in progress.
   */
  public final List<String> victors;

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
  public final List<String> resignedPlayers;
  
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
    currentPlayerNumber = getInteger(gameMap, "currentPlayerNumber");
    actions = getEntities(gameMap, "actions", new ActionDeserializer());
    currentActionNumber = getInteger(gameMap, "currentActionNumber");
    lastModified = getLong(gameMap, "lastModified");
    requestId = getString(gameMap, "requestId");
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
    result.put("id", id);
    result.put("players", players);
    result.put("profiles", profiles);
    result.put("currentPlayerNumber", currentPlayerNumber);
    result.put("actions", serializeEntities(actions));
    result.put("currentActionNumber", currentActionNumber);
    result.put("lastModified", lastModified);
    result.put("requestId", requestId);
    result.put("victors", victors);
    result.put("gameOver", gameOver);
    result.put("localMultiplayer", localMultiplayer);
    result.put("resignedPlayers", resignedPlayers);
    return result;
  }
  
  public String currentPlayerId() {
    if (currentPlayerNumber == null) return null;
    return players.get(currentPlayerNumber);
  }
  
  public boolean hasCurrentAction() {
    return currentActionNumber != null;
  }
  
  public Action currentAction() {
    return actions.get(currentActionNumber);
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
}
