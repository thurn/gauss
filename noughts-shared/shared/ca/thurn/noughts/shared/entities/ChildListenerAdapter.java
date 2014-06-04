package ca.thurn.noughts.shared.entities;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;

public final class ChildListenerAdapter<T extends Entity<T>> implements ChildEventListener {
  private final ChildListener<T> listener;
  private final Entity.EntityDeserializer<T> deserializer;

  public ChildListenerAdapter(Entity.EntityDeserializer<T> deserializer,
      ChildListener<T> listener) {
    this.listener = listener;
    this.deserializer = deserializer;
  }

  @Override
  public final void onCancelled(FirebaseError error) {
    listener.onError(error);
  }

  @Override
  public final void onChildAdded(DataSnapshot snapshot, String previousChildName) {
    System.out.println("val() " + snapshot.getValue());
    listener.onChildAdded(deserializer.fromDataSnapshot(snapshot), previousChildName);
  }

  @Override
  public final void onChildChanged(DataSnapshot snapshot, String previousChildName) {
    listener.onChildChanged(deserializer.fromDataSnapshot(snapshot), previousChildName);
  }

  @Override
  public final void onChildMoved(DataSnapshot snapshot, String previousChildName) {
    listener.onChildMoved(deserializer.fromDataSnapshot(snapshot), previousChildName);
  }

  @Override
  public final void onChildRemoved(DataSnapshot snapshot) {
    listener.onChildRemoved(deserializer.fromDataSnapshot(snapshot));
  }
}