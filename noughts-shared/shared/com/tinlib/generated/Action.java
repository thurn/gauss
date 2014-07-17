// ================================
// GENERATED CODE -- DO NOT MODIFY!
// ================================

package com.tinlib.generated;

import java.util.*;

import com.tinlib.entities.Entity;

public final class Action extends Entity<Action> {
  public static final class Deserializer extends EntityDeserializer<Action> {
    private Deserializer() {
    }

    @Override
    public Action deserialize(Map<String, Object> actionMap) {
      return new Action(actionMap);
    }
  }

  public static final class Builder extends EntityBuilder<Action> {
    private final Action action;

    private Builder() {
      this.action = new Action();
    }

    private Builder(Action action) {
      this.action = new Action(action);
    }

    @Override
    public Action build() {
      return new Action(action);
    }

    @Override
    protected Action getInternalEntity() {
      return action;
    }

    public boolean hasPlayerNumber() {
      return action.playerNumber != null;
    }

    public int getPlayerNumber() {
      checkNotNull(action.playerNumber);
      return action.playerNumber;
    }

    public Builder setPlayerNumber(int playerNumber) {
      action.playerNumber = playerNumber;
      return this;
    }

    public Builder clearPlayerNumber() {
      action.playerNumber = null;
      return this;
    }

    public boolean hasGameId() {
      return action.gameId != null;
    }

    public String getGameId() {
      checkNotNull(action.gameId);
      return action.gameId;
    }

    public Builder setGameId(String gameId) {
      checkNotNull(gameId);
      action.gameId = gameId;
      return this;
    }

    public Builder clearGameId() {
      action.gameId = null;
      return this;
    }

    public boolean hasIsSubmitted() {
      return action.isSubmitted != null;
    }

    public boolean getIsSubmitted() {
      checkNotNull(action.isSubmitted);
      return action.isSubmitted;
    }

    public Builder setIsSubmitted(boolean isSubmitted) {
      action.isSubmitted = isSubmitted;
      return this;
    }

    public Builder clearIsSubmitted() {
      action.isSubmitted = null;
      return this;
    }

    public int getCommandCount() {
      return action.commandList.size();
    }

    public Command getCommand(int index) {
      return action.commandList.get(index);
    }

    public List<Command> getCommandList() {
      return Collections.unmodifiableList(action.commandList);
    }

    public Builder setCommand(int index, EntityBuilder<Command> command) {
      return setCommand(index, command.build());
    }

    public Builder setCommand(int index, Command command) {
      checkNotNull(command);
      action.commandList.set(index, command);
      return this;
    }

    public Builder addCommand(EntityBuilder<Command> command) {
      return addCommand(command.build());
    }

    public Builder addCommand(Command command) {
      checkNotNull(command);
      action.commandList.add(command);
      return this;
    }

    public Builder addAllCommand(List<Command> commandList) {
      checkListForNull(commandList);
      action.commandList.addAll(commandList);
      return this;
    }

    public Builder clearCommandList() {
      action.commandList.clear();
      return this;
    }

    public int getFutureCommandCount() {
      return action.futureCommandList.size();
    }

    public Command getFutureCommand(int index) {
      return action.futureCommandList.get(index);
    }

    public List<Command> getFutureCommandList() {
      return Collections.unmodifiableList(action.futureCommandList);
    }

    public Builder setFutureCommand(int index, EntityBuilder<Command> futureCommand) {
      return setFutureCommand(index, futureCommand.build());
    }

    public Builder setFutureCommand(int index, Command futureCommand) {
      checkNotNull(futureCommand);
      action.futureCommandList.set(index, futureCommand);
      return this;
    }

    public Builder addFutureCommand(EntityBuilder<Command> futureCommand) {
      return addFutureCommand(futureCommand.build());
    }

    public Builder addFutureCommand(Command futureCommand) {
      checkNotNull(futureCommand);
      action.futureCommandList.add(futureCommand);
      return this;
    }

    public Builder addAllFutureCommand(List<Command> futureCommandList) {
      checkListForNull(futureCommandList);
      action.futureCommandList.addAll(futureCommandList);
      return this;
    }

    public Builder clearFutureCommandList() {
      action.futureCommandList.clear();
      return this;
    }

  }

  public static Builder newBuilder() {
    return new Builder();
  }

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

  @Override
  public String entityName() {
    return "Action";
  }

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

  @Override
  public Builder toBuilder() {
    return new Builder(this);
  }

  public boolean hasPlayerNumber() {
    return playerNumber != null;
  }

  public int getPlayerNumber() {
    checkNotNull(playerNumber);
    return playerNumber;
  }

  public boolean hasGameId() {
    return gameId != null;
  }

  public String getGameId() {
    checkNotNull(gameId);
    return gameId;
  }

  public boolean hasIsSubmitted() {
    return isSubmitted != null;
  }

  public boolean getIsSubmitted() {
    checkNotNull(isSubmitted);
    return isSubmitted;
  }

  public int getCommandCount() {
    return commandList.size();
  }

  public Command getCommand(int index) {
    return commandList.get(index);
  }

  public List<Command> getCommandList() {
    return Collections.unmodifiableList(commandList);
  }

  public int getFutureCommandCount() {
    return futureCommandList.size();
  }

  public Command getFutureCommand(int index) {
    return futureCommandList.get(index);
  }

  public List<Command> getFutureCommandList() {
    return Collections.unmodifiableList(futureCommandList);
  }

}
