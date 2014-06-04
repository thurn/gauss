package ca.thurn.noughts.shared.entities;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class AbstractValueEventListener implements ValueEventListener {
  @Override
  public void onCancelled(FirebaseError error) {}

  @Override
  public void onDataChange(DataSnapshot snapshot) {}
}
