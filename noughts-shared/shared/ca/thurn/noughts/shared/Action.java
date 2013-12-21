package ca.thurn.noughts.shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Action extends Entity {

  public String player;
  
  public Integer playerNumber;
  
  public String gameId;
  
  public Boolean submitted;
  
  public final List<Command> commands;
  
  public final List<Command> futureCommands;  
  
  public Action() {
    commands = new ArrayList<Command>();
    futureCommands = new ArrayList<Command>();
  }
  
  public Action(Map<String, Object> actionMap) {
    player = getString(actionMap, "player");
    playerNumber = getInteger(actionMap, "playerNumber");
    gameId = getString(actionMap, "gameID");
    submitted = getBoolean(actionMap, "submitted");
    commands = getEntities(actionMap, "commands", new Command());
    futureCommands = getEntities(actionMap, "futureCommands", new Command());
  }

  @Override
  public String entityName() {
    return "Action";
  }
  
  @Override
  public Action deserialize(Map<String, Object> actionMap) {
    return new Action(actionMap);
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
}
