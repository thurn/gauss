package ca.thurn.noughts.shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.firebase.client.DataSnapshot;
import com.firebase.client.MutableData;

public abstract class Entity {
  
  public static abstract class EntityDeserializer<T extends Entity> {    
    /**
     * You must define this method to instantiate a new instance of your class
     * from the supplied map argument.
     *
     * @param map The map representing your entity.
     * @return A newly instantiated entity.
     */    
    abstract T deserialize(Map<String, Object> map);
    
    @SuppressWarnings("unchecked")
    public T fromDataSnapshot(DataSnapshot snapshot) {
      Map<String, Object> map = (Map<String, Object>)snapshot.getValue();
      return (T)deserialize(map);
    }
    
    @SuppressWarnings("unchecked")
    public T fromMutableData(MutableData data) {
      Map<String, Object> map = (Map<String, Object>)data.getValue();
      return (T)deserialize(map);
    }
  }
  
  /**
   * You must define this method to convert this entity into a Map. It should
   * be possible to get back an equivalent entity by calling deserialize() on
   * the resulting map.
   * 
   * @return This entity serialized to a map.
   */  
  abstract Map<String, Object> serialize();
  
  /**
   * You must define this method to render your entity's name in toString(). 
   *
   * @return The name of your entity.
   */
  abstract String entityName();

  @SuppressWarnings("unchecked")
  static <T> List<T> getList(Map<String, Object> map, String key) {
    if (map.containsKey(key) && map.get(key) != null) {
      return (List<T>)map.get(key);
    } else {
      return new ArrayList<T>();
    }
  }
  
  static <T> List<Integer> getIntegerList(List<T> list) {
    List<Integer> result = new ArrayList<Integer>();
    for (T t : list) {
      result.add(((Number)t).intValue());
    }
    return result;
  }

  @SuppressWarnings("unchecked")
  static <K,V> Map<K,V> getMap(Map<String, Object> map, String key) {
    if (map.containsKey(key) && map.get(key) != null) {
      return (Map<K,V>)map.get(key);
    } else {
      return new HashMap<K,V>();
    }
  }
  
  static void checkExists(Map<String, Object> map, String key) {
    if (!map.containsKey(key) || map.get(key) == null) {
      throw new IllegalArgumentException("Missing key " + key + "!");
    }
  }

  static String getString(Map<String, Object> map, String key) {
    if (map.containsKey(key)) {
      return (String)map.get(key);
    } else {
      return null;
    }
  }

  static Integer getInteger(Map<String, Object> map, String key) {
    if (map.containsKey(key) && map.get(key) != null) {
      return new Integer(((Number)map.get(key)).intValue());
    } else {
      return null;
    }
  }
  
  static Long getLong(Map<String, Object> map, String key) {
    if (map.containsKey(key)) {
      return (Long)map.get(key);
    } else {
      return null;
    }
  }
  
  static boolean getBoolean(Map<String, Object> map, String key) {
    if (!map.containsKey(key) || map.get(key) == null) {
      return false;
    } else {
      return ((Boolean)map.get(key));
    }
  }
  
  @SuppressWarnings("unchecked")
  static <T extends Entity> T getEntity(Map<String, Object> map, String key,
      EntityDeserializer<T> deserializer) {
    if (map.containsKey(key)) {
      return deserializer.deserialize((Map<String, Object>)map.get(key));
    } else {
      return null;
    }
  }
  
  @SuppressWarnings("unchecked")
  static <T extends Entity> List<T> getEntities(Map<String, Object> map, String key,
      EntityDeserializer<T> deserializer) {
    ArrayList<T> result = new ArrayList<T>();
    if (map.containsKey(key)) {
      for (Map<String, Object> object : (List<Map<String, Object>>)map.get(key)) {
        result.add(deserializer.deserialize(object));
      }
    }
    return (List<T>)result;
  }
  
  static List<Map<String, Object>> serializeEntities(List<? extends Entity> list) {
    List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
    for (Entity entity : list) {
      result.add(entity.serialize());
    }
    return result;
  }
  
  @SuppressWarnings("unchecked")
  static <T extends Entity> Map<String, T> getEntityMap(Map<String, Object> gameMap, String key,
      EntityDeserializer<T> deserializer) {
    Map<String, T> result = new HashMap<String, T>();
    if (gameMap.containsKey(key)) {
      Map<String, Object> map = (Map<String, Object>)gameMap.get(key);
      for(Entry<String, Object> entry : map.entrySet()) {
        Map<String, Object> entity = (Map<String, Object>)entry.getValue();
        result.put(entry.getKey(), deserializer.deserialize(entity));
      }
    }
    return result;    
  }
  
  static <T extends Entity> Map<String, Object> serializeEntityMap(Map<String, T> entities) {
    Map<String, Object> result = new HashMap<String, Object>();
    for (Entry<String, T> entry : entities.entrySet()) {
      result.put(entry.getKey(), entry.getValue().serialize());
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
