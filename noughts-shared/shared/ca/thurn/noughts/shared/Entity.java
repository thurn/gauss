package ca.thurn.noughts.shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Entity {

  /**
   * You must define this method to instantiate a new instance of your class
   * from the supplied map argument.
   *
   * @param object The arguments to your entity.
   * @return A newly instantiated entity.
   */
  public abstract Entity deserialize(Map<String, Object> object);
  
  /**
   * You must define this method to convert this entity into a Map. It should
   * be possible to get back an equivalent entity by calling deserialize() on
   * the resulting map.
   * 
   * @return This entity serialized to a map.
   */
  public abstract Map<String, Object> serialize();
  
  /**
   * You must define this method to render your entity's name in toString(). 
   *
   * @return The name of your entity.
   */
  public abstract String entityName();

  @SuppressWarnings("unchecked")
  public <T> List<T> getList(Map<String, Object> map, String key) {
    if (map.containsKey(key)) {
      return (List<T>)map.get(key);
    } else {
      return new ArrayList<T>();
    }
  }

  @SuppressWarnings("unchecked")
  public <K,V> Map<K,V> getMap(Map<String, Object> map, String key) {
    if (map.containsKey(key)) {
      return (Map<K,V>)map.get(key);
    } else {
      return new HashMap<K,V>();
    }
  }

  public String getString(Map<String, Object> map, String key) {
    return (String)map.get(key);
  }

  public Integer getInteger(Map<String, Object> map, String key) {
    if (map.containsKey(key)) {
      return new Integer(((Number)map.get(key)).intValue());
    } else {
      return null;
    }
  }
  
  public Long getLong(Map<String, Object> map, String key) {
    return (Long)map.get(key);
  }
  
  public Boolean getBoolean(Map<String, Object> map, String key) {
    return (Boolean)map.get(key);
  }
  
  @SuppressWarnings("unchecked")
  public <T> List<T> getEntities(Map<String, Object> map, String key, Entity type) {
    ArrayList<Entity> result = new ArrayList<Entity>();
    if (map.containsKey(key)) {
      for (Map<String, Object> object : (List<Map<String, Object>>)map.get(key)) {
        result.add(type.deserialize(object));
      }
    }
    return (List<T>)result;
  }
  
  public List<Map<String, Object>> serializeEntities(List<? extends Entity> list) {
    List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
    for (Entity entity : list) {
      result.add(entity.serialize());
    }
    return result;
  }
  
  @Override
  public String toString() {
    return entityName() + ": " + serialize().toString();
  }
  
  @Override
  public int hashCode() {
    return serialize().hashCode();
  }
  
  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null) {
      return false;
    }
    if (getClass() != object.getClass()) {
      return false;
    }
    Entity other = (Entity)object;
    return serialize().equals(other.serialize());
  }
}
