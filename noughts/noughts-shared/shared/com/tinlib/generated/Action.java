// ================================
// GENERATED CODE -- DO NOT MODIFY!
// ================================

package com.tinlib.generated;

import java.util.*;

import com.tinlib.beget.Entity;

/**
 * A series of {@link Command}s which the user creates during their turn. At the end of a turn, the Action is submitted and it becomes the next player's turn.
 */
public final class Action extends Entity<Action> {
  /**
   * Class to create Action instances from their serialized representation.
   */
  public static final class Deserializer extends EntityDeserializer<Action> {
    private Deserializer() {
    }

    /**
     * Takes a map (e.g one returned from {@link Action#serialize()}) and returns a new Action instance.
     */
    @Override
    public Action deserialize(Map<String, Object> actionMap) {
      return new Action(actionMap);
    }
  }

  /**
   * Helper utility class to create new Action instances.
   */
  public static final class Builder extends EntityBuilder<Action> {
    private final Action action;

    private Builder() {
      this.action = new Action();
    }

    private Builder(Action action) {
      this.action = new Action(action);
    }

    /**
     * Returns a new immutable Action instance based on the current state of this Builder.
     */
    @Override
    public Action build() {
      return new Action(action);
    }

    @Override
    protected Action getInternalEntity() {
      return action;
    }

    /**
     * Returns true if a value has been set for playerNumber
     */
    public boolean hasPlayerNumber() {
      return action.playerNumber != null;
    }

    /**
     * Gets the value of playerNumber
     *
     * @return The number of the player who performed this action.
     */
    public int getPlayerNumber() {
      checkNotNull(action.playerNumber);
      return action.playerNumber;
    }

    /**
     * Sets the value of playerNumber.
     *
     * @param playerNumber The number of the player who performed this action.
     */
    public Builder setPlayerNumber(int playerNumber) {
      action.playerNumber = playerNumber;
      return this;
    }

    /**
     * Unsets the value of playerNumber.
     */
    public Builder clearPlayerNumber() {
      action.playerNumber = null;
      return this;
    }

    /**
     * Returns true if a value has been set for gameId
     */
    public boolean hasGameId() {
      return action.gameId != null;
    }

    /**
     * Gets the value of gameId
     *
     * @return The ID of the game this Action belongs to.
     */
    public String getGameId() {
      checkNotNull(action.gameId);
      return action.gameId;
    }

    /**
     * Sets the value of gameId.
     *
     * @param gameId The ID of the game this Action belongs to.
     */
    public Builder setGameId(String gameId) {
      checkNotNull(gameId);
      action.gameId = gameId;
      return this;
    }

    /**
     * Unsets the value of gameId.
     */
    public Builder clearGameId() {
      action.gameId = null;
      return this;
    }

    /**
     * Returns true if a value has been set for isSubmitted
     */
    public boolean hasIsSubmitted() {
      return action.isSubmitted != null;
    }

    /**
     * Gets the value of isSubmitted
     *
     * @return Whether or not this action has been submitted.
     */
    public boolean getIsSubmitted() {
      checkNotNull(action.isSubmitted);
      return action.isSubmitted;
    }

    /**
     * Sets the value of isSubmitted.
     *
     * @param isSubmitted Whether or not this action has been submitted.
     */
    public Builder setIsSubmitted(boolean isSubmitted) {
      action.isSubmitted = isSubmitted;
      return this;
    }

    /**
     * Unsets the value of isSubmitted.
     */
    public Builder clearIsSubmitted() {
      action.isSubmitted = null;
      return this;
    }

    /**
     * Returns the size of the commandList
     */
    public int getCommandCount() {
      return action.commandList.size();
    }

    /**
     * Returns the command at the provided index.
     *
     * @return A command for this action.
     */
    public Command getCommand(int index) {
      return action.commandList.get(index);
    }

    /**
     * Returns the commandList.
     *
     * Values: A command for this action.
     */
    public List<Command> getCommandList() {
      return action.commandList;
    }

    /**
     * setCommand with a Builder argument
     */
    public Builder setCommand(int index, EntityBuilder<Command> command) {
      return setCommand(index, command.build());
    }

    /**
     * Sets the command at the given index.
     *
     * @param command A command for this action.
     */
    public Builder setCommand(int index, Command command) {
      checkNotNull(command);
      action.commandList.set(index, command);
      return this;
    }

    /**
     * addCommand with a Builder argument
     */
    public Builder addCommand(EntityBuilder<Command> command) {
      return addCommand(command.build());
    }

    /**
     * Adds a new command to the end of the commandList.
     *
     * @param command A command for this action.
     */
    public Builder addCommand(Command command) {
      checkNotNull(command);
      action.commandList.add(command);
      return this;
    }

    /**
     * Adds all command instances from the provided list to the commandList.
     *
     * Values: A command for this action.
     */
    public Builder addAllCommand(List<Command> commandList) {
      checkListForNull(commandList);
      action.commandList.addAll(commandList);
      return this;
    }

    /**
     * Removes all values from the commandList
     */
    public Builder clearCommandList() {
      action.commandList.clear();
      return this;
    }

    /**
     * Returns the size of the futureCommandList
     */
    public int getFutureCommandCount() {
      return action.futureCommandList.size();
    }

    /**
     * Returns the futureCommand at the provided index.
     *
     * @return A command for this action which has been undone.
     */
    public Command getFutureCommand(int index) {
      return action.futureCommandList.get(index);
    }

    /**
     * Returns the futureCommandList.
     *
     * Values: A command for this action which has been undone.
     */
    public List<Command> getFutureCommandList() {
      return action.futureCommandList;
    }

    /**
     * setFutureCommand with a Builder argument
     */
    public Builder setFutureCommand(int index, EntityBuilder<Command> futureCommand) {
      return setFutureCommand(index, futureCommand.build());
    }

    /**
     * Sets the futureCommand at the given index.
     *
     * @param futureCommand A command for this action which has been undone.
     */
    public Builder setFutureCommand(int index, Command futureCommand) {
      checkNotNull(futureCommand);
      action.futureCommandList.set(index, futureCommand);
      return this;
    }

    /**
     * addFutureCommand with a Builder argument
     */
    public Builder addFutureCommand(EntityBuilder<Command> futureCommand) {
      return addFutureCommand(futureCommand.build());
    }

    /**
     * Adds a new futureCommand to the end of the futureCommandList.
     *
     * @param futureCommand A command for this action which has been undone.
     */
    public Builder addFutureCommand(Command futureCommand) {
      checkNotNull(futureCommand);
      action.futureCommandList.add(futureCommand);
      return this;
    }

    /**
     * Adds all futureCommand instances from the provided list to the futureCommandList.
     *
     * Values: A command for this action which has been undone.
     */
    public Builder addAllFutureCommand(List<Command> futureCommandList) {
      checkListForNull(futureCommandList);
      action.futureCommandList.addAll(futureCommandList);
      return this;
    }

    /**
     * Removes all values from the futureCommandList
     */
    public Builder clearFutureCommandList() {
      action.futureCommandList.clear();
      return this;
    }

  }

  /**
   * Returns a new Builder class to help you create Action instances.
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  /**
   * Returns a new Deserializer class to help you create Action instances from their serialized form.
   */
  public static Deserializer newDeserializer() {
    return new Deserializer();
  }

  private Integer playerNumber;
  private String gameId;
  private Boolean isSubmitted;
  private final List<Command> commandList;
  private final List<Command> futureCommandList;

  private Action() {
    commandList = new ArrayList<>();
    futureCommandList = new ArrayList<>();
  }

  private Action(Action action) {
    playerNumber = action.playerNumber;
    gameId = action.gameId;
    isSubmitted = action.isSubmitted;
    commandList = new ArrayList<>(action.commandList);
    futureCommandList = new ArrayList<>(action.futureCommandList);
  }

  private Action(Map<String, Object> map) {
    playerNumber = get(map, "playerNumber", Integer.class);
    gameId = get(map, "gameId", String.class);
    isSubmitted = get(map, "isSubmitted", Boolean.class);
    commandList = getRepeated(map, "command", Command.newDeserializer());
    futureCommandList = getRepeated(map, "futureCommand", Command.newDeserializer());
  }

  /**
   * Returns the name of this entity for use in toString().
   */
  @Override
  public String entityName() {
    return "Action";
  }

  /**
   * Creates a Map representation of this Action.
   */
  @Override
  public Map<String, Object> serialize() {
    Map<String, Object> result = new HashMap<>();
    putSerialized(result, "playerNumber", playerNumber);
    putSerialized(result, "gameId", gameId);
    putSerialized(result, "isSubmitted", isSubmitted);
    putSerialized(result, "command", commandList);
    putSerialized(result, "futureCommand", futureCommandList);
    return result;
  }

  /**
   * Creates a new Builder initialized with the current contents of this Action.
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
   * @return The number of the player who performed this action.
   */
  public int getPlayerNumber() {
    checkNotNull(playerNumber);
    return playerNumber;
  }

  /**
   * Returns true if a value has been set for gameId
   */
  public boolean hasGameId() {
    return gameId != null;
  }

  /**
   * Gets the value of gameId
   *
   * @return The ID of the game this Action belongs to.
   */
  public String getGameId() {
    checkNotNull(gameId);
    return gameId;
  }

  /**
   * Returns true if a value has been set for isSubmitted
   */
  public boolean hasIsSubmitted() {
    return isSubmitted != null;
  }

  /**
   * Gets the value of isSubmitted
   *
   * @return Whether or not this action has been submitted.
   */
  public boolean getIsSubmitted() {
    checkNotNull(isSubmitted);
    return isSubmitted;
  }

  /**
   * Returns the size of the commandList
   */
  public int getCommandCount() {
    return commandList.size();
  }

  /**
   * Returns the command at the provided index.
   *
   * @return A command for this action.
   */
  public Command getCommand(int index) {
    return commandList.get(index);
  }

  /**
   * Returns the commandList.
   *
   * Values: A command for this action.
   */
  public List<Command> getCommandList() {
    return Collections.unmodifiableList(commandList);
  }

  /**
   * Returns the size of the futureCommandList
   */
  public int getFutureCommandCount() {
    return futureCommandList.size();
  }

  /**
   * Returns the futureCommand at the provided index.
   *
   * @return A command for this action which has been undone.
   */
  public Command getFutureCommand(int index) {
    return futureCommandList.get(index);
  }

  /**
   * Returns the futureCommandList.
   *
   * Values: A command for this action which has been undone.
   */
  public List<Command> getFutureCommandList() {
    return Collections.unmodifiableList(futureCommandList);
  }

}
