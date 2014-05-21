package ca.thurn.noughts.shared.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Profile getProfile(int index) {
      return game.getProfile(index);
    }

    public List<Profile> getProfileList() {
      return game.profileList;
    }

    public Builder setProfile(int index, EntityBuilder<Profile> profile) {
      return setProfile(index, profile.build());
    }

    public Builder setProfile(int index, Profile profile) {
      checkNotNull(profile);
      game.profileList.set(index, profile);
      return this;
    }

    public Builder addProfile(EntityBuilder<Profile> profile) {
      return addProfile(profile.build());
    }

    public Builder addProfile(Profile profile) {
      checkNotNull(profile);
      game.profileList.add(profile);
      return this;
    }

    public Builder addAllProfile(List<Profile> profileList) {
      checkListForNull(profileList);
      game.profileList.addAll(profileList);
      return this;
    }

    public Builder clearProfileList() {
      game.profileList.clear();
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
   * from Player Number to Player ID.
   */
  private final List<String> playerList;

  /**
   * List of player profiles in the same order as the player list.
   */
  private final List<Profile> profileList;

  /**
   * The number of the player whose turn it is, that is, their index within
   * the players array. Null when the game is not in progress.
   */
  private Integer currentPlayerNumber;

  /**
   * Actions taken in this game, in the order in which they were submitted.
   */
  private final List<Action> submittedActionList;

  /**
   * UNIX timestamp of time when game was last modified.
   */
  private Long lastModified;

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

  public Game() {
    playerList = new ArrayList<String>();
    profileList = new ArrayList<Profile>();
    submittedActionList = new ArrayList<Action>();
    victorList = new ArrayList<Integer>();
  }

  public Game(Game game) {
    this.id = game.id;
    this.playerList = new ArrayList<String>(game.playerList);
    this.profileList = new ArrayList<Profile>(game.profileList);
    this.currentPlayerNumber = game.currentPlayerNumber;
    this.submittedActionList = new ArrayList<Action>(game.submittedActionList);
    this.lastModified = game.lastModified;
    this.victorList = new ArrayList<Integer>(game.victorList);
    this.gameOver = game.gameOver;
    this.localMultiplayer = game.localMultiplayer;
  }

  private Game(Map<String, Object> gameMap) {
    checkExists(gameMap, "id");
    id = getString(gameMap, "id");
    playerList = getList(gameMap, "playerList");
    profileList = getEntities(gameMap, "profileList", Profile.newDeserializer());
    currentPlayerNumber = getInteger(gameMap, "currentPlayerNumber");
    submittedActionList = getEntities(gameMap, "submittedActionList", Action.newDeserializer());
    lastModified = getLong(gameMap, "lastModified");
    victorList = getIntegerList(getList(gameMap, "victorList"));
    gameOver = getBoolean(gameMap, "gameOver");
    localMultiplayer = getBoolean(gameMap, "localMultiplayer");
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
    putSerialized(result, "profileList", profileList);
    putSerialized(result, "currentPlayerNumber", currentPlayerNumber);
    putSerialized(result, "submittedActionList", submittedActionList);
    putSerialized(result, "lastModified", lastModified);
    putSerialized(result, "victorList", victorList);
    putSerialized(result, "gameOver", gameOver);
    putSerialized(result, "localMultiplayer", localMultiplayer);
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
    return profileList.size();
  }

  public Profile getProfile(int index) {
    return profileList.get(index);
  }

  public List<Profile> getProfileList() {
    return Collections.unmodifiableList(profileList);
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

  public boolean hasLastModified() {
    return lastModified != null;
  }

  public long getLastModified() {
    checkNotNull(lastModified);
    return lastModified;
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
}
