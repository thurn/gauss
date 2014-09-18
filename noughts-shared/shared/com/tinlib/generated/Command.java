// ================================
// GENERATED CODE -- DO NOT MODIFY!
// ================================

package com.tinlib.generated;

import java.util.*;

import com.tinlib.beget.Entity;

/**
 * An atomic change that a user can make on their turn which can be independently undone.
 */
public final class Command extends Entity<Command> {
  /**
   * Class to create Command instances from their serialized representation.
   */
  public static final class Deserializer extends EntityDeserializer<Command> {
    private Deserializer() {
    }

    /**
     * Takes a map (e.g one returned from {@link Command#serialize()}) and returns a new Command instance.
     */
    @Override
    public Command deserialize(Map<String, Object> commandMap) {
      return new Command(commandMap);
    }
  }

  /**
   * Helper utility class to create new Command instances.
   */
  public static final class Builder extends EntityBuilder<Command> {
    private final Command command;

    private Builder() {
      this.command = new Command();
    }

    private Builder(Command command) {
      this.command = new Command(command);
    }

    /**
     * Returns a new immutable Command instance based on the current state of this Builder.
     */
    @Override
    public Command build() {
      return new Command(command);
    }

    @Override
    protected Command getInternalEntity() {
      return command;
    }

    /**
     * Returns true if a value has been set for playerNumber
     */
    public boolean hasPlayerNumber() {
      return command.playerNumber != null;
    }

    /**
     * Gets the value of playerNumber
     *
     * @return The number of the player who performed this command.
     */
    public int getPlayerNumber() {
      checkNotNull(command.playerNumber);
      return command.playerNumber;
    }

    /**
     * Sets the value of playerNumber.
     *
     * @param playerNumber The number of the player who performed this command.
     */
    public Builder setPlayerNumber(int playerNumber) {
      command.playerNumber = playerNumber;
      return this;
    }

    /**
     * Unsets the value of playerNumber.
     */
    public Builder clearPlayerNumber() {
      command.playerNumber = null;
      return this;
    }

  }

  /**
   * Returns a new Builder class to help you create Command instances.
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  /**
   * Returns a new Deserializer class to help you create Command instances from their serialized form.
   */
  public static Deserializer newDeserializer() {
    return new Deserializer();
  }

  private Integer playerNumber;

  private Command() {
  }

  private Command(Command command) {
    playerNumber = command.playerNumber;
  }

  private Command(Map<String, Object> map) {
    playerNumber = get(map, "playerNumber", Integer.class);
  }

  /**
   * Returns the name of this entity for use in toString().
   */
  @Override
  public String entityName() {
    return "Command";
  }

  /**
   * Creates a Map representation of this Command.
   */
  @Override
  public Map<String, Object> serialize() {
    Map<String, Object> result = new HashMap<>();
    putSerialized(result, "playerNumber", playerNumber);
    return result;
  }

  /**
   * Creates a new Builder initialized with the current contents of this Command.
   */
  @Override
  public Builder toBuilder() {
    return new Builder(this);
  }

  /**
   * Returns true if a value has been set for playerNumber
   */
  public boolean hasPlayerNumber() {
    return playerNumber != null;
  }

  /**
   * Gets the value of playerNumber
   *
   * @return The number of the player who performed this command.
   */
  public int getPlayerNumber() {
    checkNotNull(playerNumber);
    return playerNumber;
  }

}
