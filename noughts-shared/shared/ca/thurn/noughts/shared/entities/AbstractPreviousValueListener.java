package ca.thurn.noughts.shared.entities;

import com.firebase.client.FirebaseError;

public class AbstractPreviousValueListener<T> implements PreviousValueListener<T> {
  @Override
  public void onInitialValue(T value) {}

  @Override
  public void onUpdate(T value, T oldValue) {}

  @Override
  public void onError(FirebaseError error) {}
}
