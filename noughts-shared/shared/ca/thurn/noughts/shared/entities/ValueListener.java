package ca.thurn.noughts.shared.entities;

import org.timepedia.exporter.client.ExportPackage;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.ExportedInterface;

import com.firebase.client.FirebaseError;

@ExportedInterface
@ExportPackage("nts")
public interface ValueListener<T> extends Exportable {
  public void onUpdate(T value);

  public void onError(FirebaseError error);
}
