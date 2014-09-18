package com.tinlib.beget;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

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
      Preconditions.checkNotNull(snapshot.getValue());
      Map<String, Object> map = (Map<String, Object>)snapshot.getValue();
      return deserialize(map);
    }

    @SuppressWarnings("unchecked")
    public T fromMutableData(MutableData data) {
      Preconditions.checkNotNull(data.getValue());
      Map<String, Object> map = (Map<String, Object>)data.getValue();
      return deserialize(map);
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

  /**
   * @param object Object to check for nullness.
   * @return The object passed in.
   * @throws NullPointerException if object is null.
   */
  protected static <T> T checkNotNull(T object) {
    if (object == null) {
      throw new NullPointerException();
    }
    return object;
  }

  protected static void checkListForNull(List<?> list) {
    for (Object object : list) {
      checkNotNull(object);
    }
  }

  protected static void checkMapForNull(Map<?, ?> map) {
    for (Entry<?, ?> entry : map.entrySet()) {
      checkNotNull(entry.getKey());
      checkNotNull(entry.getValue());
    }
  }

  @SuppressWarnings("unchecked")
  protected static <T> T get(Map<String, Object> map, String key) {
    if (map.containsKey(key) && map.get(key) != null) {
      return (T)map.get(key);
    } else {
      return null;
    }
  }

  @SuppressWarnings("unchecked")
  protected static <T> T get(Map<String, Object> map, String key, Class<T> outputType) {
    if (map.containsKey(key) && map.get(key) != null) {
      return processPrimitive(map.get(key), outputType);
    } else {
      return null;
    }
  }

  @SuppressWarnings("unchecked")
  private static <T> T processPrimitive(Object primitive, Class<T> outputType) {
    if (primitive instanceof Number) {
      Number value = (Number)primitive;
      if (outputType == Long.class) {
        return (T) new Long(value.longValue());
      } else if (outputType == Integer.class) {
        return (T) new Integer(value.intValue());
      } else if (outputType == Short.class) {
        return (T) new Short(value.shortValue());
      } else if (outputType == Byte.class) {
        return (T) new Byte(value.byteValue());
      } else if (outputType == Double.class) {
        return (T) new Double(value.doubleValue());
      } else if (outputType == Float.class) {
        return (T) new Float(value.floatValue());
      } else {
        throw new RuntimeException("Unknown Number type " + outputType);
      }
    } else {
      return (T)primitive;
    }
  }

  @SuppressWarnings("unchecked")
  protected static <T extends Entity<T>> T get(Map<String, Object> map, String key,
    EntityDeserializer<T> deserializer) {
    if (map.containsKey(key) && map.get(key) != null) {
      return deserializer.deserialize((Map<String, Object>)map.get(key));
    } else {
      return null;
    }
  }

  @SuppressWarnings("unchecked")
  protected static <T> List<T> getRepeated(Map<String, Object> map, String key,
      Class<T> outputType) {
    List<T> result = Lists.newArrayList();
    if (map.containsKey(key) && map.get(key) != null) {
      for (T t : (List<T>)map.get(key)) {
        result.add(processPrimitive(t, outputType));
      }
    }
    return result;
  }

  @SuppressWarnings("unchecked")
  protected static <T> List<T> getRepeated(Map<String, Object> map, String key) {
    List<T> result = Lists.newArrayList();
    if (map.containsKey(key) && map.get(key) != null) {
      result = (List<T>)map.get(key);
    }
    return result;
  }

  @SuppressWarnings("unchecked")
  protected static <T extends Entity<T>> List<T> getRepeated(Map<String, Object> map, String key,
      EntityDeserializer<T> deserializer) {
    List<T> result = Lists.newArrayList();
    if (map.containsKey(key) && map.get(key) != null) {
      for (Map<String, Object> object : (List<Map<String, Object>>)map.get(key)) {
        result.add(deserializer.deserialize(object));
      }
    }
    return result;
  }

  protected static <T extends Enum<T>> T getEnum(Map<String, Object> map, String key,
                                                 Class<T> enumClass) {
    if (map.containsKey(key) && map.get(key) != null) {
      return Enum.valueOf(enumClass, map.get(key).toString());
    } else {
      return null;
    }
  }

  @SuppressWarnings("unchecked")
  protected static <T> List<T> getList(Map<String, Object> map, String key) {
    if (map.containsKey(key) && map.get(key) != null) {
      return (List<T>)map.get(key);
    } else {
      return Lists.newArrayList();
    }
  }

  protected static <T> List<Integer> getIntegerList(List<T> list) {
    List<Integer> result = Lists.newArrayList();
    for (T t : list) {
      result.add(((Number)t).intValue());
    }
    return result;
  }

  @SuppressWarnings("unchecked")
  protected static <K,V> Map<K,V> getMap(Map<String, Object> map, String key) {
    if (map.containsKey(key) && map.get(key) != null) {
      return (Map<K,V>)map.get(key);
    } else {
      return Maps.newHashMap();
    }
  }

  protected static void checkExists(Map<String, Object> map, String key) {
    if (!map.containsKey(key) || map.get(key) == null) {
      throw new IllegalArgumentException("Missing key " + key + "!");
    }
  }

  protected static void putSerialized(Map<String, Object> map, String key, Number object) {
    putSerializedObject(map, key, object);
  }

  protected static void putSerialized(Map<String, Object> map, String key, String object) {
    putSerializedObject(map, key, object);
  }

  protected static void putSerialized(Map<String, Object> map, String key, Boolean object) {
    putSerializedObject(map, key, object);
  }

  protected static void putSerialized(Map<String, Object> map, String key, List<?> list) {
    List<Map<String, Object>> maps = Lists.newArrayList();
    List<Object> objects = Lists.newArrayList();
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

  protected static <T extends Entity<T>> void putSerialized(Map<String, Object> map, String key,
      Map<String, T> entities) {
    Map<String, Object> result = Maps.newHashMap();
    for (Entry<String, T> entry : entities.entrySet()) {
      result.put(entry.getKey(), entry.getValue().serialize());
    }
    if (result.size() > 0) {
      map.put(key, result);
    }
  }

  protected static <T extends Entity<T>> void putSerialized(Map<String, Object> map, String key,
      Entity<T> entity) {
    if (entity != null) {
      map.put(key, entity.serialize());
    }
  }

  protected static <T extends Enum<T>> void putSerialized(Map<String, Object> map, String key,
      Enum<T> enumObject) {
    if (enumObject != null) {
      map.put(key, enumObject.name());
    }
  }

  protected static void putSerializedObject(Map<String, Object> map, String key, Object object) {
    if (object != null) {
      map.put(key, object);
    }
  }

  protected static String getString(Map<String, Object> map, String key) {
    if (map.containsKey(key)) {
      return (String)map.get(key);
    } else {
      return null;
    }
  }

  protected static Integer getInteger(Map<String, Object> map, String key) {
    if (map.containsKey(key) && map.get(key) != null) {
      return ((Number) map.get(key)).intValue();
    } else {
      return null;
    }
  }

  protected static Long getLong(Map<String, Object> map, String key) {
    if (map.containsKey(key)) {
      return (Long)map.get(key);
    } else {
      return null;
    }
  }

  protected static boolean getBoolean(Map<String, Object> map, String key) {
    if (!map.containsKey(key) || map.get(key) == null) {
      return false;
    } else {
      String string = (String)map.get(key);
      return string.equals("true");
    }
  }

  @SuppressWarnings("unchecked")
  protected static <T extends Entity<T>> T getEntity(Map<String, Object> map, String key,
      EntityDeserializer<T> deserializer) {
    if (map.containsKey(key) && map.get(key) != null) {
      return deserializer.deserialize((Map<String, Object>)map.get(key));
    } else {
      return null;
    }
  }

  @SuppressWarnings("unchecked")
  protected static <T extends Entity<T>> List<T> getEntities(Map<String, Object> map, String key,
      EntityDeserializer<T> deserializer) {
    List<T> result = Lists.newArrayList();
    if (map.containsKey(key)) {
      for (Map<String, Object> object : (List<Map<String, Object>>)map.get(key)) {
        result.add(deserializer.deserialize(object));
      }
    }
    return result;
  }

  @SuppressWarnings("unchecked")
  protected static <T extends Entity<T>> Map<String, T> getEntityMap(Map<String, Object> output,
      String key, EntityDeserializer<T> deserializer) {
    Map<String, T> result = Maps.newHashMap();
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
    if (this == object) return true;
    if (object == null || !(object instanceof Entity)) return false;
    return serialize().equals(((Entity<?>)object).serialize());
  }
}
