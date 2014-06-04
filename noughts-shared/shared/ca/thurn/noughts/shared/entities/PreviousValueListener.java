package ca.thurn.noughts.shared.entities;

import com.firebase.client.FirebaseError;

public interface PreviousValueListener<T> {
  public void onInitialValue(T value);

  public void onUpdate(T value, T oldValue);

  public void onError(FirebaseError error);
}
