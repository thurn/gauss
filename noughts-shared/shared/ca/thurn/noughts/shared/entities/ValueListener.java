package ca.thurn.noughts.shared.entities;

import com.firebase.client.FirebaseError;

public interface ValueListener<T> {
  public void onUpdate(T value);

  public void onError(FirebaseError error);
}
