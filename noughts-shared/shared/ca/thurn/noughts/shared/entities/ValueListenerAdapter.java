package ca.thurn.noughts.shared.entities;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class ValueListenerAdapter<T extends Entity<T>> implements ValueEventListener {
  private final ValueListener<T> listener;
  private final Entity.EntityDeserializer<T> deserializer;

  public ValueListenerAdapter(Entity.EntityDeserializer<T> deserializer,
      ValueListener<T> listener) {
    this.listener = listener;
    this.deserializer = deserializer;
  }

  @Override
  public void onCancelled(FirebaseError error) {
    listener.onError(error);
  }

  @Override
  public void onDataChange(DataSnapshot snapshot) {
    listener.onUpdate(deserializer.fromDataSnapshot(snapshot));
  }
}
