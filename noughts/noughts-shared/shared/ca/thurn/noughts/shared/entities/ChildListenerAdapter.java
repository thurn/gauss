package ca.thurn.noughts.shared.entities;

import com.tinlib.beget.Entity;
import com.tinlib.entities.FirebaseDeserializer;
import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.ExportPackage;
import org.timepedia.exporter.client.Exportable;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;

@Export
@ExportPackage("nts")
public final class ChildListenerAdapter<T extends Entity<T>>
    implements ChildEventListener, Exportable {
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
    listener.onChildAdded(FirebaseDeserializer.fromDataSnapshot(deserializer, snapshot),
        previousChildName);
  }

  @Override
  public final void onChildChanged(DataSnapshot snapshot, String previousChildName) {
    listener.onChildChanged(FirebaseDeserializer.fromDataSnapshot(deserializer, snapshot),
        previousChildName);
  }

  @Override
  public final void onChildMoved(DataSnapshot snapshot, String previousChildName) {
    listener.onChildMoved(FirebaseDeserializer.fromDataSnapshot(deserializer, snapshot),
        previousChildName);
  }

  @Override
  public final void onChildRemoved(DataSnapshot snapshot) {
    listener.onChildRemoved(FirebaseDeserializer.fromDataSnapshot(deserializer, snapshot));
  }
}