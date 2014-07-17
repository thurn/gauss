// ================================
// GENERATED CODE -- DO NOT MODIFY!
// ================================

package com.tinlib.generated;

import java.util.*;

import com.tinlib.entities.Entity;

public final class GameStatus extends Entity<GameStatus> {
  public static final class Deserializer extends EntityDeserializer<GameStatus> {
    private Deserializer() {
    }

    @Override
    public GameStatus deserialize(Map<String, Object> gameStatusMap) {
      return new GameStatus(gameStatusMap);
    }
  }

  public static final class Builder extends EntityBuilder<GameStatus> {
    private final GameStatus gameStatus;

    private Builder() {
      this.gameStatus = new GameStatus();
    }

    private Builder(GameStatus gameStatus) {
      this.gameStatus = new GameStatus(gameStatus);
    }

    @Override
    public GameStatus build() {
      return new GameStatus(gameStatus);
    }

    @Override
    protected GameStatus getInternalEntity() {
      return gameStatus;
    }

    public boolean hasStatusString() {
      return gameStatus.statusString != null;
    }

    public String getStatusString() {
      checkNotNull(gameStatus.statusString);
      return gameStatus.statusString;
    }

    public Builder setStatusString(String statusString) {
      checkNotNull(statusString);
      gameStatus.statusString = statusString;
      return this;
    }

    public Builder clearStatusString() {
      gameStatus.statusString = null;
      return this;
    }

    public boolean hasStatusImageString() {
      return gameStatus.statusImageString != null;
    }

    public ImageString getStatusImageString() {
      checkNotNull(gameStatus.statusImageString);
      return gameStatus.statusImageString;
    }

    public Builder setStatusImageString(EntityBuilder<ImageString> statusImageString) {
      return setStatusImageString(statusImageString.build());
    }
    public Builder setStatusImageString(ImageString statusImageString) {
      checkNotNull(statusImageString);
      gameStatus.statusImageString = statusImageString;
      return this;
    }

    public Builder clearStatusImageString() {
      gameStatus.statusImageString = null;
      return this;
    }

    public boolean hasStatusPlayer() {
      return gameStatus.statusPlayer != null;
    }

    public int getStatusPlayer() {
      checkNotNull(gameStatus.statusPlayer);
      return gameStatus.statusPlayer;
    }

    public Builder setStatusPlayer(int statusPlayer) {
      gameStatus.statusPlayer = statusPlayer;
      return this;
    }

    public Builder clearStatusPlayer() {
      gameStatus.statusPlayer = null;
      return this;
    }

    public boolean hasIsComputerThinking() {
      return gameStatus.isComputerThinking != null;
    }

    public boolean getIsComputerThinking() {
      checkNotNull(gameStatus.isComputerThinking);
      return gameStatus.isComputerThinking;
    }

    public Builder setIsComputerThinking(boolean isComputerThinking) {
      gameStatus.isComputerThinking = isComputerThinking;
      return this;
    }

    public Builder clearIsComputerThinking() {
      gameStatus.isComputerThinking = null;
      return this;
    }

  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public static Deserializer newDeserializer() {
    return new Deserializer();
  }

  private String statusString;
  private ImageString statusImageString;
  private Integer statusPlayer;
  private Boolean isComputerThinking;

  private GameStatus() {
  }

  private GameStatus(GameStatus gameStatus) {
    statusString = gameStatus.statusString;
    statusImageString = gameStatus.statusImageString;
    statusPlayer = gameStatus.statusPlayer;
    isComputerThinking = gameStatus.isComputerThinking;
  }

  private GameStatus(Map<String, Object> map) {
    statusString = get(map, "statusString", String.class);
    statusImageString = get(map, "statusImageString", ImageString.newDeserializer());
    statusPlayer = get(map, "statusPlayer", Integer.class);
    isComputerThinking = get(map, "isComputerThinking", Boolean.class);
  }

  @Override
  public String entityName() {
    return "GameStatus";
  }

  @Override
  public Map<String, Object> serialize() {
    Map<String, Object> result = new HashMap<>();
    putSerialized(result, "statusString", statusString);
    putSerialized(result, "statusImageString", statusImageString);
    putSerialized(result, "statusPlayer", statusPlayer);
    putSerialized(result, "isComputerThinking", isComputerThinking);
    return result;
  }

  @Override
  public Builder toBuilder() {
    return new Builder(this);
  }

  public boolean hasStatusString() {
    return statusString != null;
  }

  public String getStatusString() {
    checkNotNull(statusString);
    return statusString;
  }

  public boolean hasStatusImageString() {
    return statusImageString != null;
  }

  public ImageString getStatusImageString() {
    checkNotNull(statusImageString);
    return statusImageString;
  }

  public boolean hasStatusPlayer() {
    return statusPlayer != null;
  }

  public int getStatusPlayer() {
    checkNotNull(statusPlayer);
    return statusPlayer;
  }

  public boolean hasIsComputerThinking() {
    return isComputerThinking != null;
  }

  public boolean getIsComputerThinking() {
    checkNotNull(isComputerThinking);
    return isComputerThinking;
  }

}
