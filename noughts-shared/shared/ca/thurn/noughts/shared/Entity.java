package ca.thurn.noughts.shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.firebase.client.DataSnapshot;
import com.firebase.client.MutableData;

public abstract class Entity<T extends Entity<T>> {
  
  public static abstract class EntityDeserializer<T extends Entity<T>> {    
    /**
     * You must define this method to instantiate a new instance of your class
     * from the supplied map argument.
     *
     * @param map The map representing your entity.
     * @return A newly instantiated entity.
     */    
    public abstract T deserialize(Map<String, Object> map);
    
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
  
  public static abstract class EntityBuilder<T extends Entity<T>> {
    public abstract T build();
    
    protected abstract T getInternalEntity();
    
    @Override
    public String toString() {
      T entity = getInternalEntity();
      return entity.entityName() + "Builder: " + entity.serialize().toString();
    }
    
    @Override
    public int hashCode() {
      return getInternalEntity().serialize().hashCode();
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
      EntityBuilder<?> other = (EntityBuilder<?>)object;
      return getInternalEntity().serialize().equals(other.getInternalEntity().serialize());
    }    
  }

  protected Entity() {
  }
  
  /**
   * You must define this method to render your entity's name in toString(). 
   *
   * @return The name of your entity.
   */
  public abstract String entityName();  
  
  /**
   * You must define this method to convert this entity into a Map. It should
   * be possible to get back an equivalent entity by calling 
   * {@link EntityDeserializer#deserialize(Map)} on the result.
   * 
   * @return This entity serialized to a map.
   */  
  public abstract Map<String, Object> serialize();
  
  /**
   * You must define this method to convert this entity into an EntityBuilder.
   * This allows users to initialize the builder with a copy of this Entity.
   *
   * @return A new EntityBuilder initialized with this entity.
   */
  public abstract EntityBuilder<T> toBuilder();
  
  public static void checkNotNull(Object object) {
    if (object == null) {
      throw new NullPointerException();
    }
  }
  
  public static void checkListForNull(List<?> list) {
    for (Object object : list) {
      checkNotNull(object);
    }
  }
  
  public static void checkMapForNull(Map<?, ?> map) {
    for (Entry<?, ?> entry : map.entrySet()) {
      checkNotNull(entry.getKey());
      checkNotNull(entry.getValue());
    }
  }
  
  @SuppressWarnings("unchecked")
  public static <T> List<T> getList(Map<String, Object> map, String key) {
    if (map.containsKey(key) && map.get(key) != null) {
      return (List<T>)map.get(key);
    } else {
      return new ArrayList<T>();
    }
  }
  
  public static <T> List<Integer> getIntegerList(List<T> list) {
    List<Integer> result = new ArrayList<Integer>();
    for (T t : list) {
      result.add(((Number)t).intValue());
    }
    return result;
  }

  @SuppressWarnings("unchecked")
  public static <K,V> Map<K,V> getMap(Map<String, Object> map, String key) {
    if (map.containsKey(key) && map.get(key) != null) {
      return (Map<K,V>)map.get(key);
    } else {
      return new HashMap<K,V>();
    }
  }
  
  public static void checkExists(Map<String, Object> map, String key) {
    if (!map.containsKey(key) || map.get(key) == null) {
      throw new IllegalArgumentException("Missing key " + key + "!");
    }
  }
  
  public static void putSerialized(Map<String, Object> map, String key, Number object) {
    putSerializedObject(map, key, object);
  }  
  
  public static void putSerialized(Map<String, Object> map, String key, String object) {
    putSerializedObject(map, key, object);
  }
  
  public static void putSerialized(Map<String, Object> map, String key, Boolean object) {
    putSerializedObject(map, key, object);
  }
  
  public static void putSerialized(Map<String, Object> map, String key, List<?> list) {
    List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
    List<Object> objects = new ArrayList<Object>();
    for (Object object : list) {
      if (object instanceof Entity) {
        maps.add(((Entity<?>)object).serialize());
      } else {
        objects.add(object);
      }
    }
    if (maps.size() > 0 && objects.size() > 0) {
      throw new IllegalArgumentException("Cannot mix entities & non-entities in serialized list");
    }
    if (maps.size() > 0) {
      map.put(key, maps);
    }
    if (objects.size() > 0) {
      map.put(key, objects);
    }
  }  
  
  public static <T extends Entity<T>> void putSerialized(Map<String, Object> map, String key,
      Map<String, T> entities) {
    Map<String, Object> result = new HashMap<String, Object>();
    for (Entry<String, T> entry : entities.entrySet()) {
      result.put(entry.getKey(), entry.getValue().serialize());
    }
    if (result.size() > 0) {
      map.put(key, result);
    }
  }  
  
  public static <T extends Entity<T>> void putSerialized(Map<String, Object> map, String key, Entity<T> entity) {
    if (entity != null) {
      map.put(key, entity.serialize());
    }
  }
  
  public static <T extends Enum<T>> void putSerialized(Map<String, Object> map, String key, Enum<T> enumObject) {
    if (enumObject != null) {
      map.put(key, enumObject.name());
    }
  }  
  
  private static void putSerializedObject(Map<String, Object> map, String key, Object object) {
    if (object != null) {
      map.put(key, object);
    }
  }
  
  public static String getString(Map<String, Object> map, String key) {
    if (map.containsKey(key)) {
      return (String)map.get(key);
    } else {
      return null;
    }
  }

  public static Integer getInteger(Map<String, Object> map, String key) {
    if (map.containsKey(key) && map.get(key) != null) {
      return new Integer(((Number)map.get(key)).intValue());
    } else {
      return null;
    }
  }
  
  public static Long getLong(Map<String, Object> map, String key) {
    if (map.containsKey(key)) {
      return (Long)map.get(key);
    } else {
      return null;
    }
  }
  
  public static boolean getBoolean(Map<String, Object> map, String key) {
    if (!map.containsKey(key) || map.get(key) == null) {
      return false;
    } else {
      return ((Boolean)map.get(key));
    }
  }
  
  public static <T extends Enum<T>> T getEnum(Map<String, Object> map, String key, Class<T> enumClass) {
    if (map.containsKey(key) && map.get(key) != null) {
      return Enum.valueOf(enumClass, map.get(key).toString());
    } else {
      return null;
    }
  }
  
  @SuppressWarnings("unchecked")
  public static <T extends Entity<T>> T getEntity(Map<String, Object> map, String key,
      EntityDeserializer<T> deserializer) {
    if (map.containsKey(key) && map.get(key) != null) {
      return deserializer.deserialize((Map<String, Object>)map.get(key));
    } else {
      return null;
    }
  }

  @SuppressWarnings("unchecked")
  public static <T extends Entity<T>> List<T> getEntities(Map<String, Object> map, String key,
      EntityDeserializer<T> deserializer) {
    ArrayList<T> result = new ArrayList<T>();
    if (map.containsKey(key)) {
      for (Map<String, Object> object : (List<Map<String, Object>>)map.get(key)) {
        result.add(deserializer.deserialize(object));
      }
    }
    return (List<T>)result;
  }

  @SuppressWarnings("unchecked")
  public static <T extends Entity<T>> Map<String, T> getEntityMap(Map<String, Object> output, String key,
      EntityDeserializer<T> deserializer) {
    Map<String, T> result = new HashMap<String, T>();
    if (output.containsKey(key)) {
      Map<String, Object> map = (Map<String, Object>)output.get(key);
      for(Entry<String, Object> entry : map.entrySet()) {
        Map<String, Object> entity = (Map<String, Object>)entry.getValue();
        result.put(entry.getKey(), deserializer.deserialize(entity));
      }
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
    Entity<?> other = (Entity<?>)object;
    return serialize().equals(other.serialize());
  }
}
