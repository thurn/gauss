package ca.thurn.noughts.shared.entities;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;

public final class GameChildListener implements ChildEventListener {

  @Override
  public final void onCancelled(FirebaseError error) {
    onError(error);
  }

  @Override
  public final void onChildAdded(DataSnapshot snapshot, String name) {
    onChildAdded(Game.newDeserializer().fromDataSnapshot(snapshot), name);
  }

  @Override
  public final void onChildChanged(DataSnapshot snapshot, String name) {
    onChildChanged(Game.newDeserializer().fromDataSnapshot(snapshot), name);
  }

  @Override
  public final void onChildMoved(DataSnapshot snapshot, String name) {
    onChildMoved(Game.newDeserializer().fromDataSnapshot(snapshot), name);
  }

  @Override
  public final void onChildRemoved(DataSnapshot snapshot) {
    onChildRemoved(Game.newDeserializer().fromDataSnapshot(snapshot));
  }

  public void onChildAdded(Game game, String name) {}

  public void onChildChanged(Game game, String name) {}

  public void onChildMoved(Game game, String name) {}

  public void onChildRemoved(Game game) {}

  public void onError(FirebaseError error) {}
}
