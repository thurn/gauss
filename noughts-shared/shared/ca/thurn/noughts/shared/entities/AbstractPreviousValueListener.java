package ca.thurn.noughts.shared.entities;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.ExportPackage;
import org.timepedia.exporter.client.Exportable;

import com.firebase.client.FirebaseError;

@Export
@ExportPackage("nts")
public class AbstractPreviousValueListener<T> implements PreviousValueListener<T>, Exportable {
  @Override
  public void onInitialValue(T value) {}

  @Override
  public void onUpdate(T value, T oldValue) {}

  @Override
  public void onError(FirebaseError error) {}
}
