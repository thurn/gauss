package ca.thurn.noughts.shared;

import java.util.HashMap;
import java.util.Map;

public class Command extends Entity {
  
  public Integer column;
  
  public Integer row;
  
  public Command() {
  }
  
  public Command(Map<String, Object> commandMap) {
    column = getInteger(commandMap, "column");
    row = getInteger(commandMap, "row");
  }

  @Override
  public Entity deserialize(Map<String, Object> commandMap) {
    return new Command(commandMap);
  }

  @Override
  public Map<String, Object> serialize() {
    Map<String, Object> result = new HashMap<String, Object>();
    result.put("column", column);
    result.put("row", row);
    return result;
  }

  @Override
  public String entityName() {
    return "Command";
  }

}
