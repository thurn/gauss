package ca.thurn.noughts.shared.entities;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.ExportPackage;
import org.timepedia.exporter.client.Exportable;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;

@Export
@ExportPackage("nts")
public class AbstractChildEventListener implements ChildEventListener, Exportable {
  @Override
  public void onCancelled(FirebaseError error) {
  }

  @Override
  public void onChildAdded(DataSnapshot snapshot, String previous) {
  }

  @Override
  public void onChildChanged(DataSnapshot snapshot, String previous) {
  }

  @Override
  public void onChildMoved(DataSnapshot snapshot, String previous) {
  }

  @Override
  public void onChildRemoved(DataSnapshot snapshot) {
  }
}
