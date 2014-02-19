package ca.thurn.noughts.shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.firebase.client.DataSnapshot;
import com.firebase.client.MutableData;

public abstract class Entity {
  
  public static abstract class EntityDeserializer<T extends Entity> {
    /**
     * You must define this method to instantiate a new instance of your class
     * from the supplied map argument.
     *
     * @param object The arguments to your entity.
     * @return A newly instantiated entity.
     */
    public abstract T deserialize(Map<String, Object> object);
    
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
  public abstract Map<String, Object> serialize();
  
  /**
   * You must define this method to render your entity's name in toString(). 
   *
   * @return The name of your entity.
   */
  abstract String entityName();

  @SuppressWarnings("unchecked")
  <T> List<T> getList(Map<String, Object> map, String key) {
    if (map.containsKey(key) && map.get(key) != null) {
      return (List<T>)map.get(key);
    } else {
      return new ArrayList<T>();
    }
  }
  
  <T> List<Integer> getIntegerList(List<T> list) {
    List<Integer> result = new ArrayList<Integer>();
    for (T t : list) {
      result.add(((Number)t).intValue());
    }
    return result;
  }

  @SuppressWarnings("unchecked")
  <K,V> Map<K,V> getMap(Map<String, Object> map, String key) {
    if (map.containsKey(key) && map.get(key) != null) {
      return (Map<K,V>)map.get(key);
    } else {
      return new HashMap<K,V>();
    }
  }

  String getString(Map<String, Object> map, String key) {
    if (map.containsKey(key)) {
      return (String)map.get(key);
    } else {
      return null;
    }
  }

  Integer getInteger(Map<String, Object> map, String key) {
    if (map.containsKey(key) && map.get(key) != null) {
      return new Integer(((Number)map.get(key)).intValue());
    } else {
      return null;
    }
  }
  
  Long getLong(Map<String, Object> map, String key) {
    if (map.containsKey(key)) {
      return (Long)map.get(key);
    } else {
      return null;
    }
  }
  
  boolean getBoolean(Map<String, Object> map, String key) {
    if (map.containsKey(key)) {
      return (Boolean)map.get(key);
    } else {
      return false;
    }
  }
  
  @SuppressWarnings("unchecked")
  <T extends Entity> List<T> getEntities(Map<String, Object> map, String key,
      EntityDeserializer<T> deserializer) {
    ArrayList<T> result = new ArrayList<T>();
    if (map.containsKey(key)) {
      for (Map<String, Object> object : (List<Map<String, Object>>)map.get(key)) {
        result.add(deserializer.deserialize(object));
      }
    }
    return (List<T>)result;
  }
  
  List<Map<String, Object>> serializeEntities(List<? extends Entity> list) {
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
