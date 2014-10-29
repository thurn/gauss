// ================================
// GENERATED CODE -- DO NOT MODIFY!
// ================================

package com.tinlib.generated;

import java.util.*;

import com.tinlib.beget.Entity;

/**
 * A single game, represented as a sequence of submitted Actions and data about the players who performed them along with general information about the current status the game.
 */
public final class Game extends Entity<Game> {
  /**
   * Class to create Game instances from their serialized representation.
   */
  public static final class Deserializer extends EntityDeserializer<Game> {
    private Deserializer() {
    }

    /**
     * Takes a map (e.g one returned from {@link Game#serialize()}) and returns a new Game instance.
     */
    @Override
    public Game deserialize(Map<String, Object> gameMap) {
      return new Game(gameMap);
    }
  }

  /**
   * Helper utility class to create new Game instances.
   */
  public static final class Builder extends EntityBuilder<Game> {
    private final Game game;

    private Builder() {
      this.game = new Game();
    }

    private Builder(Game game) {
      this.game = new Game(game);
    }

    /**
     * Returns a new immutable Game instance based on the current state of this Builder.
     */
    @Override
    public Game build() {
      return new Game(game);
    }

    @Override
    protected Game getInternalEntity() {
      return game;
    }

    /**
     * Returns true if a value has been set for id
     */
    public boolean hasId() {
      return game.id != null;
    }

    /**
     * Gets the value of id
     *
     * @return The ID of this game.
     */
    public String getId() {
      checkNotNull(game.id);
      return game.id;
    }

    /**
     * Sets the value of id.
     *
     * @param id The ID of this game.
     */
    public Builder setId(String id) {
      checkNotNull(id);
      game.id = id;
      return this;
    }

    /**
     * Unsets the value of id.
     */
    public Builder clearId() {
      game.id = null;
      return this;
    }

    /**
     * Returns the size of the playerList
     */
    public int getPlayerCount() {
      return game.playerList.size();
    }

    /**
     * Returns the player at the provided index.
     *
     * @return A player in this game.
     */
    public String getPlayer(int index) {
      return game.playerList.get(index);
    }

    /**
     * Returns the playerList.
     *
     * Values: A player in this game.
     */
    public List<String> getPlayerList() {
      return game.playerList;
    }

    /**
     * Sets the player at the given index.
     *
     * @param player A player in this game.
     */
    public Builder setPlayer(int index, String player) {
      checkNotNull(player);
      game.playerList.set(index, player);
      return this;
    }

    /**
     * Adds a new player to the end of the playerList.
     *
     * @param player A player in this game.
     */
    public Builder addPlayer(String player) {
      checkNotNull(player);
      game.playerList.add(player);
      return this;
    }

    /**
     * Adds all player instances from the provided list to the playerList.
     *
     * Values: A player in this game.
     */
    public Builder addAllPlayer(List<String> playerList) {
      checkListForNull(playerList);
      game.playerList.addAll(playerList);
      return this;
    }

    /**
     * Removes all values from the playerList
     */
    public Builder clearPlayerList() {
      game.playerList.clear();
      return this;
    }

    /**
     * Returns the size of the profileList
     */
    public int getProfileCount() {
      return game.profileList.size();
    }

    /**
     * Returns the profile at the provided index.
     *
     * @return The profile for the corresponding player in the player list
     */
    public Profile getProfile(int index) {
      return game.profileList.get(index);
    }

    /**
     * Returns the profileList.
     *
     * Values: The profile for the corresponding player in the player list
     */
    public List<Profile> getProfileList() {
      return game.profileList;
    }

    /**
     * setProfile with a Builder argument
     */
    public Builder setProfile(int index, EntityBuilder<Profile> profile) {
      return setProfile(index, profile.build());
    }

    /**
     * Sets the profile at the given index.
     *
     * @param profile The profile for the corresponding player in the player list
     */
    public Builder setProfile(int index, Profile profile) {
      checkNotNull(profile);
      game.profileList.set(index, profile);
      return this;
    }

    /**
     * addProfile with a Builder argument
     */
    public Builder addProfile(EntityBuilder<Profile> profile) {
      return addProfile(profile.build());
    }

    /**
     * Adds a new profile to the end of the profileList.
     *
     * @param profile The profile for the corresponding player in the player list
     */
    public Builder addProfile(Profile profile) {
      checkNotNull(profile);
      game.profileList.add(profile);
      return this;
    }

    /**
     * Adds all profile instances from the provided list to the profileList.
     *
     * Values: The profile for the corresponding player in the player list
     */
    public Builder addAllProfile(List<Profile> profileList) {
      checkListForNull(profileList);
      game.profileList.addAll(profileList);
      return this;
    }

    /**
     * Removes all values from the profileList
     */
    public Builder clearProfileList() {
      game.profileList.clear();
      return this;
    }

    /**
     * Returns true if a value has been set for currentPlayerNumber
     */
    public boolean hasCurrentPlayerNumber() {
      return game.currentPlayerNumber != null;
    }

    /**
     * Gets the value of currentPlayerNumber
     *
     * @return The player number of the player whose turn it is, or null if there is no current player.
     */
    public int getCurrentPlayerNumber() {
      checkNotNull(game.currentPlayerNumber);
      return game.currentPlayerNumber;
    }

    /**
     * Sets the value of currentPlayerNumber.
     *
     * @param currentPlayerNumber The player number of the player whose turn it is, or null if there is no current player.
     */
    public Builder setCurrentPlayerNumber(int currentPlayerNumber) {
      game.currentPlayerNumber = currentPlayerNumber;
      return this;
    }

    /**
     * Unsets the value of currentPlayerNumber.
     */
    public Builder clearCurrentPlayerNumber() {
      game.currentPlayerNumber = null;
      return this;
    }

    /**
     * Returns the size of the submittedActionList
     */
    public int getSubmittedActionCount() {
      return game.submittedActionList.size();
    }

    /**
     * Returns the submittedAction at the provided index.
     *
     * @return An action which has been submitted in this game.
     */
    public Action getSubmittedAction(int index) {
      return game.submittedActionList.get(index);
    }

    /**
     * Returns the submittedActionList.
     *
     * Values: An action which has been submitted in this game.
     */
    public List<Action> getSubmittedActionList() {
      return game.submittedActionList;
    }

    /**
     * setSubmittedAction with a Builder argument
     */
    public Builder setSubmittedAction(int index, EntityBuilder<Action> submittedAction) {
      return setSubmittedAction(index, submittedAction.build());
    }

    /**
     * Sets the submittedAction at the given index.
     *
     * @param submittedAction An action which has been submitted in this game.
     */
    public Builder setSubmittedAction(int index, Action submittedAction) {
      checkNotNull(submittedAction);
      game.submittedActionList.set(index, submittedAction);
      return this;
    }

    /**
     * addSubmittedAction with a Builder argument
     */
    public Builder addSubmittedAction(EntityBuilder<Action> submittedAction) {
      return addSubmittedAction(submittedAction.build());
    }

    /**
     * Adds a new submittedAction to the end of the submittedActionList.
     *
     * @param submittedAction An action which has been submitted in this game.
     */
    public Builder addSubmittedAction(Action submittedAction) {
      checkNotNull(submittedAction);
      game.submittedActionList.add(submittedAction);
      return this;
    }

    /**
     * Adds all submittedAction instances from the provided list to the submittedActionList.
     *
     * Values: An action which has been submitted in this game.
     */
    public Builder addAllSubmittedAction(List<Action> submittedActionList) {
      checkListForNull(submittedActionList);
      game.submittedActionList.addAll(submittedActionList);
      return this;
    }

    /**
     * Removes all values from the submittedActionList
     */
    public Builder clearSubmittedActionList() {
      game.submittedActionList.clear();
      return this;
    }

    /**
     * Returns true if a value has been set for lastModified
     */
    public boolean hasLastModified() {
      return game.lastModified != null;
    }

    /**
     * Gets the value of lastModified
     *
     * @return The timestamp at which the last modification to this game occurred.
     */
    public long getLastModified() {
      checkNotNull(game.lastModified);
      return game.lastModified;
    }

    /**
     * Sets the value of lastModified.
     *
     * @param lastModified The timestamp at which the last modification to this game occurred.
     */
    public Builder setLastModified(long lastModified) {
      game.lastModified = lastModified;
      return this;
    }

    /**
     * Unsets the value of lastModified.
     */
    public Builder clearLastModified() {
      game.lastModified = null;
      return this;
    }

    /**
     * Returns the size of the victorList
     */
    public int getVictorCount() {
      return game.victorList.size();
    }

    /**
     * Returns the victor at the provided index.
     *
     * @return The number of a player who has won this game.
     */
    public int getVictor(int index) {
      return game.victorList.get(index);
    }

    /**
     * Returns the victorList.
     *
     * Values: The number of a player who has won this game.
     */
    public List<Integer> getVictorList() {
      return game.victorList;
    }

    /**
     * Sets the victor at the given index.
     *
     * @param victor The number of a player who has won this game.
     */
    public Builder setVictor(int index, int victor) {
      game.victorList.set(index, victor);
      return this;
    }

    /**
     * Adds a new victor to the end of the victorList.
     *
     * @param victor The number of a player who has won this game.
     */
    public Builder addVictor(int victor) {
      game.victorList.add(victor);
      return this;
    }

    /**
     * Adds all victor instances from the provided list to the victorList.
     *
     * Values: The number of a player who has won this game.
     */
    public Builder addAllVictor(List<Integer> victorList) {
      checkListForNull(victorList);
      game.victorList.addAll(victorList);
      return this;
    }

    /**
     * Removes all values from the victorList
     */
    public Builder clearVictorList() {
      game.victorList.clear();
      return this;
    }

    /**
     * Returns true if a value has been set for isGameOver
     */
    public boolean hasIsGameOver() {
      return game.isGameOver != null;
    }

    /**
     * Gets the value of isGameOver
     *
     * @return True if this game has ended.
     */
    public boolean getIsGameOver() {
      checkNotNull(game.isGameOver);
      return game.isGameOver;
    }

    /**
     * Sets the value of isGameOver.
     *
     * @param isGameOver True if this game has ended.
     */
    public Builder setIsGameOver(boolean isGameOver) {
      game.isGameOver = isGameOver;
      return this;
    }

    /**
     * Unsets the value of isGameOver.
     */
    public Builder clearIsGameOver() {
      game.isGameOver = null;
      return this;
    }

    /**
     * Returns true if a value has been set for isLocalMultiplayer
     */
    public boolean hasIsLocalMultiplayer() {
      return game.isLocalMultiplayer != null;
    }

    /**
     * Gets the value of isLocalMultiplayer
     *
     * @return True if this is a local multiplayer game.
     */
    public boolean getIsLocalMultiplayer() {
      checkNotNull(game.isLocalMultiplayer);
      return game.isLocalMultiplayer;
    }

    /**
     * Sets the value of isLocalMultiplayer.
     *
     * @param isLocalMultiplayer True if this is a local multiplayer game.
     */
    public Builder setIsLocalMultiplayer(boolean isLocalMultiplayer) {
      game.isLocalMultiplayer = isLocalMultiplayer;
      return this;
    }

    /**
     * Unsets the value of isLocalMultiplayer.
     */
    public Builder clearIsLocalMultiplayer() {
      game.isLocalMultiplayer = null;
      return this;
    }

  }

  /**
   * Returns a new Builder class to help you create Game instances.
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  /**
   * Returns a new Deserializer class to help you create Game instances from their serialized form.
   */
  public static Deserializer newDeserializer() {
    return new Deserializer();
  }

  private String id;
  private final List<String> playerList;
  private final List<Profile> profileList;
  private Integer currentPlayerNumber;
  private final List<Action> submittedActionList;
  private Long lastModified;
  private final List<Integer> victorList;
  private Boolean isGameOver;
  private Boolean isLocalMultiplayer;

  private Game() {
    playerList = new ArrayList<>();
    profileList = new ArrayList<>();
    submittedActionList = new ArrayList<>();
    victorList = new ArrayList<>();
  }

  private Game(Game game) {
    id = game.id;
    playerList = new ArrayList<>(game.playerList);
    profileList = new ArrayList<>(game.profileList);
    currentPlayerNumber = game.currentPlayerNumber;
    submittedActionList = new ArrayList<>(game.submittedActionList);
    lastModified = game.lastModified;
    victorList = new ArrayList<>(game.victorList);
    isGameOver = game.isGameOver;
    isLocalMultiplayer = game.isLocalMultiplayer;
  }

  private Game(Map<String, Object> map) {
    id = get(map, "id", String.class);
    playerList = getRepeated(map, "player", String.class);
    profileList = getRepeated(map, "profile", Profile.newDeserializer());
    currentPlayerNumber = get(map, "currentPlayerNumber", Integer.class);
    submittedActionList = getRepeated(map, "submittedAction", Action.newDeserializer());
    lastModified = get(map, "lastModified", Long.class);
    victorList = getRepeated(map, "victor", Integer.class);
    isGameOver = get(map, "isGameOver", Boolean.class);
    isLocalMultiplayer = get(map, "isLocalMultiplayer", Boolean.class);
  }

  /**
   * Returns the name of this entity for use in toString().
   */
  @Override
  public String entityName() {
    return "Game";
  }

  /**
   * Creates a Map representation of this Game.
   */
  @Override
  public Map<String, Object> serialize() {
    Map<String, Object> result = new HashMap<>();
    putSerialized(result, "id", id);
    putSerialized(result, "player", playerList);
    putSerialized(result, "profile", profileList);
    putSerialized(result, "currentPlayerNumber", currentPlayerNumber);
    putSerialized(result, "submittedAction", submittedActionList);
    putSerialized(result, "lastModified", lastModified);
    putSerialized(result, "victor", victorList);
    putSerialized(result, "isGameOver", isGameOver);
    putSerialized(result, "isLocalMultiplayer", isLocalMultiplayer);
    return result;
  }

  /**
   * Creates a new Builder initialized with the current contents of this Game.
   */
  @Override
  public Builder toBuilder() {
    return new Builder(this);
  }

  /**
   * Returns true if a value has been set for id
   */
  public boolean hasId() {
    return id != null;
  }

  /**
   * Gets the value of id
   *
   * @return The ID of this game.
   */
  public String getId() {
    checkNotNull(id);
    return id;
  }

  /**
   * Returns the size of the playerList
   */
  public int getPlayerCount() {
    return playerList.size();
  }

  /**
   * Returns the player at the provided index.
   *
   * @return A player in this game.
   */
  public String getPlayer(int index) {
    return playerList.get(index);
  }

  /**
   * Returns the playerList.
   *
   * Values: A player in this game.
   */
  public List<String> getPlayerList() {
    return Collections.unmodifiableList(playerList);
  }

  /**
   * Returns the size of the profileList
   */
  public int getProfileCount() {
    return profileList.size();
  }

  /**
   * Returns the profile at the provided index.
   *
   * @return The profile for the corresponding player in the player list
   */
  public Profile getProfile(int index) {
    return profileList.get(index);
  }

  /**
   * Returns the profileList.
   *
   * Values: The profile for the corresponding player in the player list
   */
  public List<Profile> getProfileList() {
    return Collections.unmodifiableList(profileList);
  }

  /**
   * Returns true if a value has been set for currentPlayerNumber
   */
  public boolean hasCurrentPlayerNumber() {
    return currentPlayerNumber != null;
  }

  /**
   * Gets the value of currentPlayerNumber
   *
   * @return The player number of the player whose turn it is, or null if there is no current player.
   */
  public int getCurrentPlayerNumber() {
    checkNotNull(currentPlayerNumber);
    return currentPlayerNumber;
  }

  /**
   * Returns the size of the submittedActionList
   */
  public int getSubmittedActionCount() {
    return submittedActionList.size();
  }

  /**
   * Returns the submittedAction at the provided index.
   *
   * @return An action which has been submitted in this game.
   */
  public Action getSubmittedAction(int index) {
    return submittedActionList.get(index);
  }

  /**
   * Returns the submittedActionList.
   *
   * Values: An action which has been submitted in this game.
   */
  public List<Action> getSubmittedActionList() {
    return Collections.unmodifiableList(submittedActionList);
  }

  /**
   * Returns true if a value has been set for lastModified
   */
  public boolean hasLastModified() {
    return lastModified != null;
  }

  /**
   * Gets the value of lastModified
   *
   * @return The timestamp at which the last modification to this game occurred.
   */
  public long getLastModified() {
    checkNotNull(lastModified);
    return lastModified;
  }

  /**
   * Returns the size of the victorList
   */
  public int getVictorCount() {
    return victorList.size();
  }

  /**
   * Returns the victor at the provided index.
   *
   * @return The number of a player who has won this game.
   */
  public int getVictor(int index) {
    return victorList.get(index);
  }

  /**
   * Returns the victorList.
   *
   * Values: The number of a player who has won this game.
   */
  public List<Integer> getVictorList() {
    return Collections.unmodifiableList(victorList);
  }

  /**
   * Returns true if a value has been set for isGameOver
   */
  public boolean hasIsGameOver() {
    return isGameOver != null;
  }

  /**
   * Gets the value of isGameOver
   *
   * @return True if this game has ended.
   */
  public boolean getIsGameOver() {
    checkNotNull(isGameOver);
    return isGameOver;
  }

  /**
   * Returns true if a value has been set for isLocalMultiplayer
   */
  public boolean hasIsLocalMultiplayer() {
    return isLocalMultiplayer != null;
  }

  /**
   * Gets the value of isLocalMultiplayer
   *
   * @return True if this is a local multiplayer game.
   */
  public boolean getIsLocalMultiplayer() {
    checkNotNull(isLocalMultiplayer);
    return isLocalMultiplayer;
  }

}
