package ca.thurn.noughts.shared.entities;

import org.timepedia.exporter.client.ExportPackage;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.ExportedInterface;

import com.firebase.client.FirebaseError;

@ExportedInterface
@ExportPackage("nts")
public interface ChildListener<T> extends Exportable {
  public void onChildAdded(T child, String previousChildName);

  public void onChildChanged(T child, String previousChildName);

  public void onChildMoved(T child, String previousChildName);

  public void onChildRemoved(T child);

  public void onError(FirebaseError error);
}