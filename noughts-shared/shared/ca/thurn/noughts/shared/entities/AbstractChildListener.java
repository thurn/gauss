package ca.thurn.noughts.shared.entities;

import com.firebase.client.FirebaseError;

public class AbstractChildListener<T> implements ChildListener<T> {
  @Override
  public void onChildAdded(T child, String previousChildName) {}

  @Override
  public void onChildChanged(T child, String previousChildName) {}

  @Override
  public void onChildMoved(T child, String previousChildName) {}

  @Override
  public void onChildRemoved(T child) {}

  @Override
  public void onError(FirebaseError error) {}
}