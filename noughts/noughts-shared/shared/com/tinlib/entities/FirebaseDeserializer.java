package com.tinlib.entities;

import com.firebase.client.DataSnapshot;
import com.firebase.client.MutableData;
import com.google.common.base.Preconditions;
import com.tinlib.beget.Entity;

import java.util.Map;

public class FirebaseDeserializer {
  @SuppressWarnings("unchecked")
  public static <T extends Entity<T>> T fromDataSnapshot(Entity.EntityDeserializer<T> deserializer,
      DataSnapshot snapshot) {
    Preconditions.checkNotNull(snapshot.getValue());
    Map<String, Object> map = (Map<String, Object>)snapshot.getValue();
    return deserializer.deserialize(map);
  }

  @SuppressWarnings("unchecked")
  public static <T extends Entity<T>> T fromMutableData(Entity.EntityDeserializer<T> deserializer,
      MutableData data) {
    Preconditions.checkNotNull(data.getValue());
    Map<String, Object> map = (Map<String, Object>)data.getValue();
    return deserializer.deserialize(map);
  }
}
