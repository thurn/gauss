package ca.thurn.noughts.shared;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Action extends Entity<Action> {
  public static class Deserializer extends EntityDeserializer<Action> {
    private Deserializer() {
    }

    @Override
    Action deserialize(Map<String, Object> actionMap) {
      return new Action(actionMap);
    }    
  }
  
  public static class Builder implements EntityBuilder<Action> {
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

    public boolean hasPlayerNumber() {
      return action.hasPlayerNumber();
    }
    
    public int getPlayerNumber() {
      return action.getPlayerNumber();
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
      return action.hasGameId();
    }
    
    public String getGameId() {
      return action.getGameId();
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

    public boolean hasSubmitted() {
      return action.hasSubmitted();
    }
    
    public boolean isSubmitted() {
      return action.isSubmitted();
    }
    
    public Builder setSubmitted(boolean submitted) {
      action.submitted = submitted;
      return this;
    }
    
    public Builder clearSubmitted() {
      action.submitted = null;
      return this;
    }
    
    public int getCommandCount() {
      return action.getCommandCount();
    }
    
    public Command getCommand(int index) {
      return action.getCommand(index);
    }
    
    public List<Command> getCommandList() {
      return action.commands;
    }
    
    public Builder setCommand(int index, EntityBuilder<Command> command) {
      return setCommand(index, command);
    }

    public Builder setCommand(int index, Command command) {
      checkNotNull(command);
      action.commands.set(index, command);
      return this;
    }
    
    public Builder addCommand(EntityBuilder<Command> command) {
      return addCommand(command.build());
    }
    
    public Builder addCommand(Command command) {
      checkNotNull(command);
      action.commands.add(command);
      return this;
    }
    
    public Builder addAllCommand(List<Command> commands) {
      checkListForNull(commands);
      action.commands.addAll(commands);
      return this;
    }
    
    public Builder clearCommandList() {
      action.commands.clear();
      return this;
    }
    
    public int getFutureCommandCount() {
      return action.getFutureCommandCount();
    }
    
    public Command getFutureCommand(int index) {
      return action.getFutureCommand(index);
    }
    
    public List<Command> getFutureCommandList() {
      return action.futureCommands;
    }
    
    public Builder setFutureCommand(int index, EntityBuilder<Command> futureCommand) {
      return setFutureCommand(index, futureCommand.build());
    }
    
    public Builder setFutureCommand(int index, Command futureCommand) {
      checkNotNull(futureCommand);
      action.futureCommands.set(index, futureCommand);
      return this;
    }
    
    public Builder addFutureCommand(EntityBuilder<Command> futureCommand) {
      return addFutureCommand(futureCommand.build());
    }
    
    public Builder addFutureCommand(Command futureCommand) {
      checkNotNull(futureCommand);
      action.futureCommands.add(futureCommand);
      return this;
    }

    public Builder addAllFutureCommand(List<Command> futureCommands) {
      checkListForNull(futureCommands);
      action.futureCommands.addAll(futureCommands);
      return this;
    }
    
    public Builder clearFutureCommandList() {
      action.futureCommands.clear();
      return this;
    }
  }
  
  public static Builder newBuilder() {
    return new Builder();
  }
  
  public static Builder newBuilder(Action action) {
    return new Builder(action);
  }
  
  public static Deserializer newDeserializer() {
    return new Deserializer();
  }

  private Integer playerNumber;
  private String gameId;
  private Boolean submitted;
  private final List<Command> commands;
  private final List<Command> futureCommands;  

  private Action() {
    this.commands = new ArrayList<Command>();
    this.futureCommands = new ArrayList<Command>();
  }
  
  private Action(Action action) {
    this.playerNumber = action.playerNumber;
    this.gameId = action.gameId;
    this.submitted = action.submitted;
    this.commands = new ArrayList<Command>(action.commands);
    this.futureCommands = new ArrayList<Command>(action.futureCommands);
  }

  private Action(Map<String, Object> actionMap) {
    playerNumber = getInteger(actionMap, "playerNumber"); 
    gameId = getString(actionMap, "gameId");
    submitted = getBoolean(actionMap, "submitted");
    commands = getEntities(actionMap, "commands", Command.newDeserializer());
    futureCommands = getEntities(actionMap, "futureCommands", Command.newDeserializer());
  }

  @Override
  public String entityName() {
    return "Action";
  }
  
  @Override
  Map<String, Object> serialize() {
    Map<String, Object> result = new HashMap<String, Object>();
    putSerialized(result, "playerNumber", playerNumber);
    putSerialized(result, "gameId", gameId);
    putSerialized(result, "submitted", submitted);
    putSerialized(result, "commands", commands);
    putSerialized(result, "futureCommands", futureCommands);
    return result;
  }
  
  @Override
  public Builder toBuilder() {
    return new Builder(this);
  }
  
  public boolean hasSubmitted() {
    return submitted != null;
  }
  
  public boolean isSubmitted() {
    checkNotNull(submitted);
    return submitted;
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
  
  public int getCommandCount() {
    return commands.size();
  }
  
  public Command getCommand(int index) {
    return commands.get(index);
  }
  
  public List<Command> getCommandList() {
    return Collections.unmodifiableList(commands);
  }

  public int getFutureCommandCount() {
    return futureCommands.size();
  }
  
  public Command getFutureCommand(int index) {
    return futureCommands.get(index);
  }
  
  public List<Command> getFutureCommandList() {
    return Collections.unmodifiableList(futureCommands);
  }
}
