// ================================
// GENERATED CODE -- DO NOT MODIFY!
// ================================

package com.tinlib.generated;

import java.util.*;

import com.tinlib.entities.Entity;

/**
 * A row movement within the game list.
 */
public final class GameListUpdate extends Entity<GameListUpdate> {
  /**
   * Class to create GameListUpdate instances from their serialized representation.
   */
  public static final class Deserializer extends EntityDeserializer<GameListUpdate> {
    private Deserializer() {
    }

    /**
     * Takes a map (e.g one returned from {@link GameListUpdate#serialize()}) and returns a new GameListUpdate instance.
     */
    @Override
    public GameListUpdate deserialize(Map<String, Object> gameListUpdateMap) {
      return new GameListUpdate(gameListUpdateMap);
    }
  }

  /**
   * Helper utility class to create new GameListUpdate instances.
   */
  public static final class Builder extends EntityBuilder<GameListUpdate> {
    private final GameListUpdate gameListUpdate;

    private Builder() {
      this.gameListUpdate = new GameListUpdate();
    }

    private Builder(GameListUpdate gameListUpdate) {
      this.gameListUpdate = new GameListUpdate(gameListUpdate);
    }

    /**
     * Returns a new immutable GameListUpdate instance based on the current state of this Builder.
     */
    @Override
    public GameListUpdate build() {
      return new GameListUpdate(gameListUpdate);
    }

    @Override
    protected GameListUpdate getInternalEntity() {
      return gameListUpdate;
    }

    /**
     * Returns true if a value has been set for from
     */
    public boolean hasFrom() {
      return gameListUpdate.from != null;
    }

    /**
     * Gets the value of from
     *
     * @return The source of the row move.
     */
    public IndexPath getFrom() {
      checkNotNull(gameListUpdate.from);
      return gameListUpdate.from;
    }

    /**
     * setFrom with a Builder argument
     */
    public Builder setFrom(EntityBuilder<IndexPath> from) {
      return setFrom(from.build());
    }
    /**
     * Sets the value of from.
     *
     * @param from The source of the row move.
     */
    public Builder setFrom(IndexPath from) {
      checkNotNull(from);
      gameListUpdate.from = from;
      return this;
    }

    /**
     * Unsets the value of from.
     */
    public Builder clearFrom() {
      gameListUpdate.from = null;
      return this;
    }

    /**
     * Returns true if a value has been set for to
     */
    public boolean hasTo() {
      return gameListUpdate.to != null;
    }

    /**
     * Gets the value of to
     *
     * @return The destination of the row move.
     */
    public IndexPath getTo() {
      checkNotNull(gameListUpdate.to);
      return gameListUpdate.to;
    }

    /**
     * setTo with a Builder argument
     */
    public Builder setTo(EntityBuilder<IndexPath> to) {
      return setTo(to.build());
    }
    /**
     * Sets the value of to.
     *
     * @param to The destination of the row move.
     */
    public Builder setTo(IndexPath to) {
      checkNotNull(to);
      gameListUpdate.to = to;
      return this;
    }

    /**
     * Unsets the value of to.
     */
    public Builder clearTo() {
      gameListUpdate.to = null;
      return this;
    }

  }

  /**
   * Returns a new Builder class to help you create GameListUpdate instances.
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  /**
   * Returns a new Deserializer class to help you create GameListUpdate instances from their serialized form.
   */
  public static Deserializer newDeserializer() {
    return new Deserializer();
  }

  private IndexPath from;
  private IndexPath to;

  private GameListUpdate() {
  }

  private GameListUpdate(GameListUpdate gameListUpdate) {
    from = gameListUpdate.from;
    to = gameListUpdate.to;
  }

  private GameListUpdate(Map<String, Object> map) {
    from = get(map, "from", IndexPath.newDeserializer());
    to = get(map, "to", IndexPath.newDeserializer());
  }

  /**
   * Returns the name of this entity for use in toString().
   */
  @Override
  public String entityName() {
    return "GameListUpdate";
  }

  /**
   * Creates a Map representation of this GameListUpdate.
   */
  @Override
  public Map<String, Object> serialize() {
    Map<String, Object> result = new HashMap<>();
    putSerialized(result, "from", from);
    putSerialized(result, "to", to);
    return result;
  }

  /**
   * Creates a new Builder initialized with the current contents of this GameListUpdate.
   */
  @Override
  public Builder toBuilder() {
    return new Builder(this);
  }

  /**
   * Returns true if a value has been set for from
   */
  public boolean hasFrom() {
    return from != null;
  }

  /**
   * Gets the value of from
   *
   * @return The source of the row move.
   */
  public IndexPath getFrom() {
    checkNotNull(from);
    return from;
  }

  /**
   * Returns true if a value has been set for to
   */
  public boolean hasTo() {
    return to != null;
  }

  /**
   * Gets the value of to
   *
   * @return The destination of the row move.
   */
  public IndexPath getTo() {
    checkNotNull(to);
    return to;
  }

}
