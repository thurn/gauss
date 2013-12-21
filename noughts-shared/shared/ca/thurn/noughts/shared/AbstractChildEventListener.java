package ca.thurn.noughts.shared;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;

public class AbstractChildEventListener implements ChildEventListener {

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
