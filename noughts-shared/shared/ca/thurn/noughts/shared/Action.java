package ca.thurn.noughts.shared;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.thurn.noughts.shared.Command.CommandDeserializer;

public class Action extends Entity {
  public static class ActionDeserializer extends EntityDeserializer<Action> {
    @Override
    public Action deserialize(Map<String, Object> actionMap) {
      return new Action(actionMap);
    }    
  }
  
  private final Integer playerNumber;
  
  private String gameId;
  
  private Boolean submitted;
  
  private final List<Command> commands;
  
  private final List<Command> futureCommands;  
  
  public Action(int playerNumber) {
    commands = new ArrayList<Command>();
    futureCommands = new ArrayList<Command>();
    this.playerNumber = playerNumber;
  }
  
  public Action(Map<String, Object> actionMap) {
    this.playerNumber = getInteger(actionMap, "playerNumber"); 
    setGameId(getString(actionMap, "gameId"));
    submitted = getBoolean(actionMap, "submitted");
    commands = getEntities(actionMap, "commands", new CommandDeserializer());
    futureCommands = getEntities(actionMap, "futureCommands", new CommandDeserializer());
  }

  @Override
  public String entityName() {
    return "Action";
  }
  
  @Override
  public Map<String, Object> serialize() {
    Map<String, Object> result = new HashMap<String, Object>();
    result.put("playerNumber", getPlayerNumber());
    result.put("gameId", getGameId());
    result.put("submitted", submitted);
    result.put("commands", serializeEntities(getCommandsMutable()));
    result.put("futureCommands", serializeEntities(getFutureCommandsMutable()));
    return result;
  }
  
  public boolean isSubmitted() {
    return submitted != null && submitted == true;
  }

  void setSubmitted(Boolean submitted) {
    this.submitted = submitted;
  }

  public Integer getPlayerNumber() {
    return playerNumber;
  }

  public String getGameId() {
    return gameId;
  }

  void setGameId(String gameId) {
    this.gameId = gameId;
  }

  public List<Command> getCommands() {
    return Collections.unmodifiableList(getCommandsMutable());
  }
  
  List<Command> getCommandsMutable() {
    return commands;
  }

  public List<Command> getFutureCommands() {
    return Collections.unmodifiableList(getFutureCommandsMutable());
  }
  
  List<Command> getFutureCommandsMutable() {
    return futureCommands;
  }
}
