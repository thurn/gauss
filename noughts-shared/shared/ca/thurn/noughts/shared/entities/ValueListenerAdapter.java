package ca.thurn.noughts.shared.entities;

import com.tinlib.beget.Entity;
import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.ExportPackage;
import org.timepedia.exporter.client.Exportable;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

@Export
@ExportPackage("nts")
public final class ValueListenerAdapter<T extends Entity<T>> implements ValueEventListener, Exportable {
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
