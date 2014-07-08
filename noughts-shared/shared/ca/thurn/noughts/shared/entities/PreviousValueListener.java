package ca.thurn.noughts.shared.entities;

import org.timepedia.exporter.client.ExportPackage;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.ExportedInterface;

import com.firebase.client.FirebaseError;

@ExportedInterface
@ExportPackage("nts")
public interface PreviousValueListener<T> extends Exportable {
  public void onInitialValue(T value);

  public void onUpdate(T value, T oldValue);

  public void onError(FirebaseError error);
}
