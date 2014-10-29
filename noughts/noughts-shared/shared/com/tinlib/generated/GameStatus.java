// ================================
// GENERATED CODE -- DO NOT MODIFY!
// ================================

package com.tinlib.generated;

import java.util.*;

import com.tinlib.beget.Entity;

/**
 * Description of the current status of a game.
 */
public final class GameStatus extends Entity<GameStatus> {
  /**
   * Class to create GameStatus instances from their serialized representation.
   */
  public static final class Deserializer extends EntityDeserializer<GameStatus> {
    private Deserializer() {
    }

    /**
     * Takes a map (e.g one returned from {@link GameStatus#serialize()}) and returns a new GameStatus instance.
     */
    @Override
    public GameStatus deserialize(Map<String, Object> gameStatusMap) {
      return new GameStatus(gameStatusMap);
    }
  }

  /**
   * Helper utility class to create new GameStatus instances.
   */
  public static final class Builder extends EntityBuilder<GameStatus> {
    private final GameStatus gameStatus;

    private Builder() {
      this.gameStatus = new GameStatus();
    }

    private Builder(GameStatus gameStatus) {
      this.gameStatus = new GameStatus(gameStatus);
    }

    /**
     * Returns a new immutable GameStatus instance based on the current state of this Builder.
     */
    @Override
    public GameStatus build() {
      return new GameStatus(gameStatus);
    }

    @Override
    protected GameStatus getInternalEntity() {
      return gameStatus;
    }

    /**
     * Returns true if a value has been set for statusString
     */
    public boolean hasStatusString() {
      return gameStatus.statusString != null;
    }

    /**
     * Gets the value of statusString
     *
     * @return A string describing the current status of the game.
     */
    public String getStatusString() {
      checkNotNull(gameStatus.statusString);
      return gameStatus.statusString;
    }

    /**
     * Sets the value of statusString.
     *
     * @param statusString A string describing the current status of the game.
     */
    public Builder setStatusString(String statusString) {
      checkNotNull(statusString);
      gameStatus.statusString = statusString;
      return this;
    }

    /**
     * Unsets the value of statusString.
     */
    public Builder clearStatusString() {
      gameStatus.statusString = null;
      return this;
    }

    /**
     * Returns true if a value has been set for statusImageString
     */
    public boolean hasStatusImageString() {
      return gameStatus.statusImageString != null;
    }

    /**
     * Gets the value of statusImageString
     *
     * @return An image representing the current status of the game.
     */
    public ImageString getStatusImageString() {
      checkNotNull(gameStatus.statusImageString);
      return gameStatus.statusImageString;
    }

    /**
     * setStatusImageString with a Builder argument
     */
    public Builder setStatusImageString(EntityBuilder<ImageString> statusImageString) {
      return setStatusImageString(statusImageString.build());
    }
    /**
     * Sets the value of statusImageString.
     *
     * @param statusImageString An image representing the current status of the game.
     */
    public Builder setStatusImageString(ImageString statusImageString) {
      checkNotNull(statusImageString);
      gameStatus.statusImageString = statusImageString;
      return this;
    }

    /**
     * Unsets the value of statusImageString.
     */
    public Builder clearStatusImageString() {
      gameStatus.statusImageString = null;
      return this;
    }

    /**
     * Returns true if a value has been set for statusPlayer
     */
    public boolean hasStatusPlayer() {
      return gameStatus.statusPlayer != null;
    }

    /**
     * Gets the value of statusPlayer
     *
     * @return The number of the player associated with the current game status e.g. because it is their turn.
     */
    public int getStatusPlayer() {
      checkNotNull(gameStatus.statusPlayer);
      return gameStatus.statusPlayer;
    }

    /**
     * Sets the value of statusPlayer.
     *
     * @param statusPlayer The number of the player associated with the current game status e.g. because it is their turn.
     */
    public Builder setStatusPlayer(int statusPlayer) {
      gameStatus.statusPlayer = statusPlayer;
      return this;
    }

    /**
     * Unsets the value of statusPlayer.
     */
    public Builder clearStatusPlayer() {
      gameStatus.statusPlayer = null;
      return this;
    }

    /**
     * Returns true if a value has been set for isComputerThinking
     */
    public boolean hasIsComputerThinking() {
      return gameStatus.isComputerThinking != null;
    }

    /**
     * Gets the value of isComputerThinking
     *
     * @return True if the AI is currently searching for a move in this game.
     */
    public boolean getIsComputerThinking() {
      checkNotNull(gameStatus.isComputerThinking);
      return gameStatus.isComputerThinking;
    }

    /**
     * Sets the value of isComputerThinking.
     *
     * @param isComputerThinking True if the AI is currently searching for a move in this game.
     */
    public Builder setIsComputerThinking(boolean isComputerThinking) {
      gameStatus.isComputerThinking = isComputerThinking;
      return this;
    }

    /**
     * Unsets the value of isComputerThinking.
     */
    public Builder clearIsComputerThinking() {
      gameStatus.isComputerThinking = null;
      return this;
    }

  }

  /**
   * Returns a new Builder class to help you create GameStatus instances.
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  /**
   * Returns a new Deserializer class to help you create GameStatus instances from their serialized form.
   */
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

  /**
   * Returns the name of this entity for use in toString().
   */
  @Override
  public String entityName() {
    return "GameStatus";
  }

  /**
   * Creates a Map representation of this GameStatus.
   */
  @Override
  public Map<String, Object> serialize() {
    Map<String, Object> result = new HashMap<>();
    putSerialized(result, "statusString", statusString);
    putSerialized(result, "statusImageString", statusImageString);
    putSerialized(result, "statusPlayer", statusPlayer);
    putSerialized(result, "isComputerThinking", isComputerThinking);
    return result;
  }

  /**
   * Creates a new Builder initialized with the current contents of this GameStatus.
   */
  @Override
  public Builder toBuilder() {
    return new Builder(this);
  }

  /**
   * Returns true if a value has been set for statusString
   */
  public boolean hasStatusString() {
    return statusString != null;
  }

  /**
   * Gets the value of statusString
   *
   * @return A string describing the current status of the game.
   */
  public String getStatusString() {
    checkNotNull(statusString);
    return statusString;
  }

  /**
   * Returns true if a value has been set for statusImageString
   */
  public boolean hasStatusImageString() {
    return statusImageString != null;
  }

  /**
   * Gets the value of statusImageString
   *
   * @return An image representing the current status of the game.
   */
  public ImageString getStatusImageString() {
    checkNotNull(statusImageString);
    return statusImageString;
  }

  /**
   * Returns true if a value has been set for statusPlayer
   */
  public boolean hasStatusPlayer() {
    return statusPlayer != null;
  }

  /**
   * Gets the value of statusPlayer
   *
   * @return The number of the player associated with the current game status e.g. because it is their turn.
   */
  public int getStatusPlayer() {
    checkNotNull(statusPlayer);
    return statusPlayer;
  }

  /**
   * Returns true if a value has been set for isComputerThinking
   */
  public boolean hasIsComputerThinking() {
    return isComputerThinking != null;
  }

  /**
   * Gets the value of isComputerThinking
   *
   * @return True if the AI is currently searching for a move in this game.
   */
  public boolean getIsComputerThinking() {
    checkNotNull(isComputerThinking);
    return isComputerThinking;
  }

}
