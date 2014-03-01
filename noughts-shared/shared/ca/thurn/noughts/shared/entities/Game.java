package ca.thurn.noughts.shared.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.thurn.noughts.shared.Entity;

// 2 fields:
// under construction action
// submitted actions

public class Game extends Entity<Game> {
  public static class Deserializer extends EntityDeserializer<Game> {
    private Deserializer() {
    }

    @Override 
    public Game deserialize(Map<String, Object> gameMap) {
      return new Game(gameMap);
    }    
  }  

  public static class Builder extends EntityBuilder<Game> {
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

    @Override protected Game getInternalEntity() {
      return game;
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
      return game.playerList;
    }
    
    public Builder setPlayer(int index, String player) {
      checkNotNull(player);
      game.playerList.set(index, player);
      return this;
    }
    
    public Builder addPlayer(String player) {
      checkNotNull(player);
      game.playerList.add(player);
      return this;
    }
    
    public Builder addAllPlayer(List<String> playerList) {
      checkListForNull(playerList);
      game.playerList.addAll(playerList);
      return this;
    }
    
    public Builder clearPlayerList() {
      game.playerList.clear();
      return this;
    }

    public int getProfileCount() {
      return game.getProfileCount();
    }

    public Profile getProfile(String key) {
      return game.getProfile(key);
    }

    public Map<String, Profile> getProfileMap() {
      return game.profileMap;
    }
    
    public Builder putProfile(String key, EntityBuilder<Profile> profile) {
      return putProfile(key, profile.build());
    }
    
    public Builder putProfile(String key, Profile profile) {
      checkNotNull(key);
      checkNotNull(profile);
      game.profileMap.put(key, profile);
      return this;
    }
    
    public Builder putAllProfile(Map<String, Profile> profileMap) {
      checkMapForNull(profileMap);
      game.profileMap.putAll(profileMap);
      return this;
    }
    
    public Builder clearProfileMap() {
      game.profileMap.clear();
      return this;
    }

    public int getLocalProfileCount() {
      return game.getLocalProfileCount();
    }

    public Profile getLocalProfile(int index) {
      return game.getLocalProfile(index);
    }

    public List<Profile> getLocalProfileList() {
      return game.localProfileList;
    }
    
    public Builder setLocalProfile(int index, EntityBuilder<Profile> profile) {
      return setLocalProfile(index, profile.build());
    }
    
    public Builder setLocalProfile(int index, Profile profile) {
      checkNotNull(profile);
      game.localProfileList.set(index, profile);
      return this;
    }
    
    public Builder addLocalProfile(EntityBuilder<Profile> profile) {
      return addLocalProfile(profile.build());
    }
    
    public Builder addLocalProfile(Profile localProfile) {
      checkNotNull(localProfile);
      game.localProfileList.add(localProfile);
      return this;
    }
    
    public Builder addAllLocalProfile(List<Profile> localProfileList) {
      checkListForNull(localProfileList);
      game.localProfileList.addAll(localProfileList);
      return this;
    }
    
    public Builder clearLocalProfileList() {
      game.localProfileList.clear();
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

    public int getSubmittedActionCount() {
      return game.getSubmittedActionCount();
    }

    public Action getSubmittedAction(int index) {
      return game.getSubmittedAction(index);
    }

    public List<Action> getSubmittedActionList() {
      return game.submittedActionList;
    }
    
    public Builder setSubmittedAction(int index, EntityBuilder<Action> action) {
      return setSubmittedAction(index, action.build());
    }
    
    public Builder setSubmittedAction(int index, Action action) {
      checkNotNull(action);
      game.submittedActionList.set(index, action);
      return this;
    }
    
    public Builder addSubmittedAction(EntityBuilder<Action> action) {
      return addSubmittedAction(action.build());
    }
    
    public Builder addSubmittedAction(Action action) {
      checkNotNull(action);
      game.submittedActionList.add(action);
      return this;
    }
    
    public Builder addAllSubmittedAction(List<Action> actionList) {
      checkListForNull(actionList);
      game.submittedActionList.addAll(actionList);
      return this;
    }
    
    public Builder clearSubmittedActionList() {
      game.submittedActionList.clear();
      return this;
    }

    public boolean hasCurrentAction() {
      return game.hasCurrentAction();
    }

    public Action getCurrentAction() {
      return game.getCurrentAction();
    }
    
    public Builder setCurrentAction(EntityBuilder<Action> currentAction) {
      return setCurrentAction(currentAction.build());
    }
    
    public Builder setCurrentAction(Action currentAction) {
      checkNotNull(currentAction);
      game.currentAction = currentAction;
      return this;
    }
    
    public Builder clearCurrentAction() {
      game.currentAction = null;
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
      return game.victorList;
    }
    
    public Builder setVictor(int index, int victor) {
      game.victorList.set(index, victor);
      return this;
    }
    
    public Builder addVictor(int victor) {
      game.victorList.add(victor);
      return this;
    }
    
    public Builder addAllVictor(List<Integer> victorList) {
      checkListForNull(victorList);
      game.victorList.addAll(victorList);
      return this;
    }
    
    public Builder clearVictorList() {
      game.victorList.clear();
      return this;
    }

    public boolean hasIsGameOver() {
      return game.hasIsGameOver();
    }

    public boolean isGameOver() {
      return game.isGameOver();
    }
    
    public Builder setIsGameOver(boolean gameOver) {
      game.gameOver = gameOver;
      return this;
    }
    
    public Builder clearIsGameOver() {
      game.gameOver = null;
      return this;
    }

    public boolean hasIsLocalMultiplayer() {
      return game.hasIsLocalMultiplayer();
    }

    public boolean isLocalMultiplayer() {
      return game.isLocalMultiplayer();
    }
    
    public Builder setIsLocalMultiplayer(boolean localMultiplayer) {
      game.localMultiplayer = localMultiplayer;
      return this;
    }
    
    public Builder clearIsLocalMultiplayer() {
      game.localMultiplayer = null;
      return this;
    }
    
    public boolean hasIsMinimal() {
      return game.hasIsMinimal();
    }

    public boolean isMinimal() {
      return game.isMinimal();
    }
    
    public Builder setIsMinimal(boolean isMinimal) {
      game.minimal = isMinimal;
      return this;
    }
    
    public Builder clearIsMinimal() {
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
      return game.resignedPlayerList;
    }
    
    public Builder setResignedPlayer(int index, int resignedPlayer) {
      game.resignedPlayerList.set(index, resignedPlayer);
      return this;
    }
    
    public Builder addResignedPlayer(int resignedPlayer) {
      game.resignedPlayerList.add(resignedPlayer);
      return this;
    }
    
    public Builder addAllResignedPlayer(List<Integer> resignedPlayer) {
      checkListForNull(resignedPlayer);
      game.resignedPlayerList.addAll(resignedPlayer);
      return this;
    }
    
    public Builder clearResignedPlayerList() {
      game.resignedPlayerList.clear();
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
  private final List<String> playerList;

  /**
   * A mapping from player IDs to profile information about the player.
   */
  private final Map<String, Profile> profileMap;
  
  /**
   * List of player profiles in the same order as the player list, these
   * profiles takes precedence over the ID-based profiles above. Null
   * indicates a missing profile.
   */
  private final List<Profile> localProfileList;

  /**
   * The number of the player whose turn it is, that is, their index within
   * the players array. Null when the game is not in progress.
   */
  private Integer currentPlayerNumber;

  /**
   * Actions taken in this game, in the order in which they were taken.
   * Potentially includes an unsubmitted action of the current player.
   */
  private final List<Action> submittedActionList;

  /**
   * Action currently being constructed, null when there is no action under
   * construction.
   */
  private Action currentAction;

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
  private final List<Integer> victorList;

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
  private final List<Integer> resignedPlayerList;
  
  public Game() {
    playerList = new ArrayList<String>();
    profileMap = new HashMap<String, Profile>();
    localProfileList = new ArrayList<Profile>();
    submittedActionList = new ArrayList<Action>();
    victorList = new ArrayList<Integer>();
    resignedPlayerList = new ArrayList<Integer>();
  }
  
  public Game(Game game) {
    this.id = game.id;
    this.playerList = new ArrayList<String>(game.playerList);
    this.profileMap = new HashMap<String, Profile>(game.profileMap);
    this.localProfileList = new ArrayList<Profile>(game.localProfileList);
    this.currentPlayerNumber = game.currentPlayerNumber;
    this.submittedActionList = new ArrayList<Action>(game.submittedActionList);
    this.currentAction = game.currentAction;
    this.lastModified = game.lastModified;
    this.requestId = game.requestId;
    this.victorList = new ArrayList<Integer>(game.victorList);
    this.gameOver = game.gameOver;
    this.localMultiplayer = game.localMultiplayer;
    this.minimal = game.minimal;
    this.resignedPlayerList = new ArrayList<Integer>(game.resignedPlayerList);
  }

  private Game(Map<String, Object> gameMap) {
    checkExists(gameMap, "id");
    id = getString(gameMap, "id");
    playerList = getList(gameMap, "playerList");
    profileMap = getEntityMap(gameMap, "profileMap", Profile.newDeserializer());
    localProfileList = getEntities(gameMap, "localProfileMap", Profile.newDeserializer());
    currentPlayerNumber = getInteger(gameMap, "currentPlayerNumber");
    submittedActionList = getEntities(gameMap, "submittedActionList", Action.newDeserializer());
    currentAction = getEntity(gameMap, "currentAction", Action.newDeserializer());
    lastModified = getLong(gameMap, "lastModified");
    requestId = getString(gameMap, "requestId");
    victorList = getIntegerList(getList(gameMap, "victorList"));
    gameOver = getBoolean(gameMap, "gameOver");
    localMultiplayer = getBoolean(gameMap, "localMultiplayer");
    minimal = getBoolean(gameMap, "minimal");
    resignedPlayerList = getIntegerList(getList(gameMap, "resignedPlayerList"));
  }
  
  @Override
  public String entityName() {
    return "Game";
  }  
  
  @Override
  public Map<String, Object> serialize() {
    Map<String, Object> result = new HashMap<String, Object>();
    putSerialized(result, "id", id);
    putSerialized(result, "playerList", playerList);
    putSerialized(result, "profileMap", profileMap);
    putSerialized(result, "localProfileMap", localProfileList);
    putSerialized(result, "currentPlayerNumber", currentPlayerNumber);
    putSerialized(result, "submittedActionList", submittedActionList);
    putSerialized(result, "currentAction", currentAction);
    putSerialized(result, "lastModified", lastModified);
    putSerialized(result, "requestId", requestId);
    putSerialized(result, "victorList", victorList);
    putSerialized(result, "gameOver", gameOver);
    putSerialized(result, "localMultiplayer", localMultiplayer);
    putSerialized(result, "minimal", minimal);
    putSerialized(result, "resignedPlayerList", resignedPlayerList);
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
    return playerList.size();
  }
  
  public String getPlayer(int index) {
    return playerList.get(index);
  }
  
  public List<String> getPlayerList() {
    return Collections.unmodifiableList(playerList);
  }
  
  public int getProfileCount() {
    return profileMap.size();
  }
  
  public Profile getProfile(String key) {
    checkNotNull(key);
    return profileMap.get(key);
  }
  
  public Map<String, Profile> getProfileMap() {
    return Collections.unmodifiableMap(profileMap);
  }
  
  public int getLocalProfileCount() {
    return localProfileList.size();
  }
  
  public Profile getLocalProfile(int index) {
    return localProfileList.get(index);
  }
  
  public List<Profile> getLocalProfileList() {
    return Collections.unmodifiableList(localProfileList);
  }
  
  public boolean hasCurrentPlayerNumber() {
    return currentPlayerNumber != null;
  }
  
  public int getCurrentPlayerNumber() {
    checkNotNull(currentPlayerNumber);
    return currentPlayerNumber;
  }
  
  public int getSubmittedActionCount() {
    return submittedActionList.size();
  }
  
  public Action getSubmittedAction(int index) {
    return submittedActionList.get(index);
  }
  
  public List<Action> getSubmittedActionList() {
    return Collections.unmodifiableList(submittedActionList);
  }
  
  public boolean hasCurrentAction() {
    return currentAction != null;
  }
  
  public Action getCurrentAction() {
    checkNotNull(currentAction);
    return currentAction;
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
    return victorList.size();
  }
  
  public int getVictor(int index) {
    return victorList.get(index);
  }
  
  public List<Integer> getVictorList() {
    return Collections.unmodifiableList(victorList);
  }
  
  public boolean hasIsGameOver() {
    return gameOver != null;
  }
  
  public boolean isGameOver() {
    checkNotNull(gameOver);
    return gameOver;
  }
  
  public boolean hasIsLocalMultiplayer() {
    return localMultiplayer != null;
  }
  
  public boolean isLocalMultiplayer() {
    checkNotNull(localMultiplayer);
    return localMultiplayer;
  }
  
  public boolean hasIsMinimal() {
    return minimal != null;
  }
  
  public boolean isMinimal() {
    checkNotNull(minimal);
    return minimal;
  }
  
  public int getResignedPlayerCount() {
    return resignedPlayerList.size();
  }
  
  public int getResignedPlayer(int index) {
    return resignedPlayerList.get(index);
  }
  
  public List<Integer> getResignedPlayerList() {
    return Collections.unmodifiableList(resignedPlayerList);
  }
}
