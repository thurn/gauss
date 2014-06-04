package ca.thurn.noughts.shared.entities;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public abstract class GameValueListener implements ValueEventListener {

  @Override
  public final void onCancelled(FirebaseError error) {
    onError(error);
  }

  @Override
  public final void onDataChange(DataSnapshot snapshot) {
    onGameChanged(Game.newDeserializer().fromDataSnapshot(snapshot));
  }

  public void onError(FirebaseError error) {}

  public void onGameChanged(Game game) {}
}
