package ca.thurn.noughts.shared;

import java.util.ArrayList;
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

  public final String player;
  
  public Integer playerNumber;
  
  public String gameId;
  
  private Boolean submitted;
  
  public final List<Command> commands;
  
  public final List<Command> futureCommands;  
  
  public Action(String player) {
    commands = new ArrayList<Command>();
    futureCommands = new ArrayList<Command>();
    this.player = player;
  }
  
  public Action(Map<String, Object> actionMap) {
    player = getString(actionMap, "player");
    playerNumber = getInteger(actionMap, "playerNumber");
    gameId = getString(actionMap, "gameId");
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
    result.put("player", player);
    result.put("playerNumber", playerNumber);
    result.put("gameId", gameId);
    result.put("submitted", submitted);
    result.put("commands", serializeEntities(commands));
    result.put("futureCommands", serializeEntities(futureCommands));
    return result;
  }
  
  public boolean isSubmitted() {
    return submitted != null && submitted == true;
  }

  void setSubmitted(Boolean submitted) {
    this.submitted = submitted;
  }
}
