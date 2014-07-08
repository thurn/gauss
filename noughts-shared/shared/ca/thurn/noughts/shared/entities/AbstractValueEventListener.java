package ca.thurn.noughts.shared.entities;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.ExportPackage;
import org.timepedia.exporter.client.Exportable;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

@Export
@ExportPackage("nts")
public class AbstractValueEventListener implements ValueEventListener, Exportable {
  @Override
  public void onCancelled(FirebaseError error) {}

  @Override
  public void onDataChange(DataSnapshot snapshot) {}
}
