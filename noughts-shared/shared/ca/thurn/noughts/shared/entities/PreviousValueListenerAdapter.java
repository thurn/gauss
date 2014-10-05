package ca.thurn.noughts.shared.entities;

import com.tinlib.beget.Entity;
import com.tinlib.entities.FirebaseDeserializer;
import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.ExportPackage;
import org.timepedia.exporter.client.Exportable;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

@Export
@ExportPackage("nts")
public final class PreviousValueListenerAdapter<T extends Entity<T>>
    implements ValueEventListener, Exportable {
  private final PreviousValueListener<T> listener;
  private final Entity.EntityDeserializer<T> deserializer;
  private T previousValue;

  public PreviousValueListenerAdapter(Entity.EntityDeserializer<T> deserializer,
      PreviousValueListener<T> listener) {
    this.listener = listener;
    this.deserializer = deserializer;
    this.previousValue = null;
  }

  @Override
  public final void onCancelled(FirebaseError error) {
    listener.onError(error);
  }

  @Override
  public final void onDataChange(DataSnapshot snapshot) {
    T entity = FirebaseDeserializer.fromDataSnapshot(deserializer, snapshot);
    if (previousValue == null) {
      listener.onInitialValue(entity);
    } else {
      listener.onUpdate(entity, previousValue);
    }
    previousValue = entity;
  }

}
