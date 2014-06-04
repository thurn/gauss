package ca.thurn.noughts.shared.entities;

import com.firebase.client.FirebaseError;

public class AbstractValueListener<T> implements ValueListener<T> {

  @Override
  public void onUpdate(T value) {}

  @Override
  public void onError(FirebaseError error) {}

}
