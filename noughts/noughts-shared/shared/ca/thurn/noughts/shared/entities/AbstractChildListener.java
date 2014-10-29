package ca.thurn.noughts.shared.entities;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.ExportPackage;
import org.timepedia.exporter.client.Exportable;

import com.firebase.client.FirebaseError;

@Export
@ExportPackage("nts")
public class AbstractChildListener<T> implements ChildListener<T>, Exportable {
  @Override
  public void onChildAdded(T child, String previousChildName) {}

  @Override
  public void onChildChanged(T child, String previousChildName) {}

  @Override
  public void onChildMoved(T child, String previousChildName) {}

  @Override
  public void onChildRemoved(T child) {}

  @Override
  public void onError(FirebaseError error) {}
}