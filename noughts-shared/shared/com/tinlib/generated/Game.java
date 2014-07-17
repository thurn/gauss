// ================================
// GENERATED CODE -- DO NOT MODIFY!
// ================================

package com.tinlib.generated;

import java.util.*;

import com.tinlib.entities.Entity;

public final class Game extends Entity<Game> {
  public static final class Deserializer extends EntityDeserializer<Game> {
    private Deserializer() {
    }

    @Override
    public Game deserialize(Map<String, Object> gameMap) {
      return new Game(gameMap);
    }
  }

  public static final class Builder extends EntityBuilder<Game> {
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

    @Override
    protected Game getInternalEntity() {
      return game;
    }

    public boolean hasId() {
      return game.id != null;
    }

    public String getId() {
      checkNotNull(game.id);
      return game.id;
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
      return game.playerList.size();
    }

    public String getPlayer(int index) {
      return game.playerList.get(index);
    }

    public List<String> getPlayerList() {
      return Collections.unmodifiableList(game.playerList);
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
      return game.profileList.size();
    }

    public Profile getProfile(int index) {
      return game.profileList.get(index);
    }

    public List<Profile> getProfileList() {
      return Collections.unmodifiableList(game.profileList);
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
      return game.currentPlayerNumber != null;
    }

    public int getCurrentPlayerNumber() {
      checkNotNull(game.currentPlayerNumber);
      return game.currentPlayerNumber;
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
      return game.submittedActionList.size();
    }

    public Action getSubmittedAction(int index) {
      return game.submittedActionList.get(index);
    }

    public List<Action> getSubmittedActionList() {
      return Collections.unmodifiableList(game.submittedActionList);
    }

    public Builder setSubmittedAction(int index, EntityBuilder<Action> submittedAction) {
      return setSubmittedAction(index, submittedAction.build());
    }

    public Builder setSubmittedAction(int index, Action submittedAction) {
      checkNotNull(submittedAction);
      game.submittedActionList.set(index, submittedAction);
      return this;
    }

    public Builder addSubmittedAction(EntityBuilder<Action> submittedAction) {
      return addSubmittedAction(submittedAction.build());
    }

    public Builder addSubmittedAction(Action submittedAction) {
      checkNotNull(submittedAction);
      game.submittedActionList.add(submittedAction);
      return this;
    }

    public Builder addAllSubmittedAction(List<Action> submittedActionList) {
      checkListForNull(submittedActionList);
      game.submittedActionList.addAll(submittedActionList);
      return this;
    }

    public Builder clearSubmittedActionList() {
      game.submittedActionList.clear();
      return this;
    }

    public boolean hasLastModified() {
      return game.lastModified != null;
    }

    public long getLastModified() {
      checkNotNull(game.lastModified);
      return game.lastModified;
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
      return game.victorList.size();
    }

    public int getVictor(int index) {
      return game.victorList.get(index);
    }

    public List<Integer> getVictorList() {
      return Collections.unmodifiableList(game.victorList);
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
      return game.isGameOver != null;
    }

    public boolean getIsGameOver() {
      checkNotNull(game.isGameOver);
      return game.isGameOver;
    }

    public Builder setIsGameOver(boolean isGameOver) {
      game.isGameOver = isGameOver;
      return this;
    }

    public Builder clearIsGameOver() {
      game.isGameOver = null;
      return this;
    }

    public boolean hasIsLocalMultiplayer() {
      return game.isLocalMultiplayer != null;
    }

    public boolean getIsLocalMultiplayer() {
      checkNotNull(game.isLocalMultiplayer);
      return game.isLocalMultiplayer;
    }

    public Builder setIsLocalMultiplayer(boolean isLocalMultiplayer) {
      game.isLocalMultiplayer = isLocalMultiplayer;
      return this;
    }

    public Builder clearIsLocalMultiplayer() {
      game.isLocalMultiplayer = null;
      return this;
    }

  }

  public static Builder newBuilder() {
    return new Builder();
  }

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

  @Override
  public String entityName() {
    return "Game";
  }

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
    return isGameOver != null;
  }

  public boolean getIsGameOver() {
    checkNotNull(isGameOver);
    return isGameOver;
  }

  public boolean hasIsLocalMultiplayer() {
    return isLocalMultiplayer != null;
  }

  public boolean getIsLocalMultiplayer() {
    checkNotNull(isLocalMultiplayer);
    return isLocalMultiplayer;
  }

}
