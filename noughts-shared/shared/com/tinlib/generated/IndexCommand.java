// ================================
// GENERATED CODE -- DO NOT MODIFY!
// ================================

package com.tinlib.generated;

import java.util.*;

import com.tinlib.entities.Entity;

/**
 * A command and its associated index.
 */
public final class IndexCommand extends Entity<IndexCommand> {
  /**
   * Class to create IndexCommand instances from their serialized representation.
   */
  public static final class Deserializer extends EntityDeserializer<IndexCommand> {
    private Deserializer() {
    }

    /**
     * Takes a map (e.g one returned from {@link IndexCommand#serialize()}) and returns a new IndexCommand instance.
     */
    @Override
    public IndexCommand deserialize(Map<String, Object> indexCommandMap) {
      return new IndexCommand(indexCommandMap);
    }
  }

  /**
   * Helper utility class to create new IndexCommand instances.
   */
  public static final class Builder extends EntityBuilder<IndexCommand> {
    private final IndexCommand indexCommand;

    private Builder() {
      this.indexCommand = new IndexCommand();
    }

    private Builder(IndexCommand indexCommand) {
      this.indexCommand = new IndexCommand(indexCommand);
    }

    /**
     * Returns a new immutable IndexCommand instance based on the current state of this Builder.
     */
    @Override
    public IndexCommand build() {
      return new IndexCommand(indexCommand);
    }

    @Override
    protected IndexCommand getInternalEntity() {
      return indexCommand;
    }

    /**
     * Returns true if a value has been set for command
     */
    public boolean hasCommand() {
      return indexCommand.command != null;
    }

    /**
     * Gets the value of command
     *
     * @return The command.
     */
    public Command getCommand() {
      checkNotNull(indexCommand.command);
      return indexCommand.command;
    }

    /**
     * setCommand with a Builder argument
     */
    public Builder setCommand(EntityBuilder<Command> command) {
      return setCommand(command.build());
    }
    /**
     * Sets the value of command.
     *
     * @param command The command.
     */
    public Builder setCommand(Command command) {
      checkNotNull(command);
      indexCommand.command = command;
      return this;
    }

    /**
     * Unsets the value of command.
     */
    public Builder clearCommand() {
      indexCommand.command = null;
      return this;
    }

    /**
     * Returns true if a value has been set for index
     */
    public boolean hasIndex() {
      return indexCommand.index != null;
    }

    /**
     * Gets the value of index
     *
     * @return The command's index.
     */
    public int getIndex() {
      checkNotNull(indexCommand.index);
      return indexCommand.index;
    }

    /**
     * Sets the value of index.
     *
     * @param index The command's index.
     */
    public Builder setIndex(int index) {
      indexCommand.index = index;
      return this;
    }

    /**
     * Unsets the value of index.
     */
    public Builder clearIndex() {
      indexCommand.index = null;
      return this;
    }

  }

  /**
   * Returns a new Builder class to help you create IndexCommand instances.
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  /**
   * Returns a new Deserializer class to help you create IndexCommand instances from their serialized form.
   */
  public static Deserializer newDeserializer() {
    return new Deserializer();
  }

  private Command command;
  private Integer index;

  private IndexCommand() {
  }

  private IndexCommand(IndexCommand indexCommand) {
    command = indexCommand.command;
    index = indexCommand.index;
  }

  private IndexCommand(Map<String, Object> map) {
    command = get(map, "command", Command.newDeserializer());
    index = get(map, "index", Integer.class);
  }

  /**
   * Returns the name of this entity for use in toString().
   */
  @Override
  public String entityName() {
    return "IndexCommand";
  }

  /**
   * Creates a Map representation of this IndexCommand.
   */
  @Override
  public Map<String, Object> serialize() {
    Map<String, Object> result = new HashMap<>();
    putSerialized(result, "command", command);
    putSerialized(result, "index", index);
    return result;
  }

  /**
   * Creates a new Builder initialized with the current contents of this IndexCommand.
   */
  @Override
  public Builder toBuilder() {
    return new Builder(this);
  }

  /**
   * Returns true if a value has been set for command
   */
  public boolean hasCommand() {
    return command != null;
  }

  /**
   * Gets the value of command
   *
   * @return The command.
   */
  public Command getCommand() {
    checkNotNull(command);
    return command;
  }

  /**
   * Returns true if a value has been set for index
   */
  public boolean hasIndex() {
    return index != null;
  }

  /**
   * Gets the value of index
   *
   * @return The command's index.
   */
  public int getIndex() {
    checkNotNull(index);
    return index;
  }

}
