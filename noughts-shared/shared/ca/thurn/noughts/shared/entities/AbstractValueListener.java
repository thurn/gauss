package ca.thurn.noughts.shared.entities;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.ExportPackage;
import org.timepedia.exporter.client.Exportable;

import com.firebase.client.FirebaseError;

@Export
@ExportPackage("nts")
public class AbstractValueListener<T> implements ValueListener<T>, Exportable {

  @Override
  public void onUpdate(T value) {}

  @Override
  public void onError(FirebaseError error) {}

}
