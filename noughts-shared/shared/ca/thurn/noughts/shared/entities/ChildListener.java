package ca.thurn.noughts.shared.entities;

import com.firebase.client.FirebaseError;

public interface ChildListener<T> {
  public void onChildAdded(T child, String previousChildName);

  public void onChildChanged(T child, String previousChildName);

  public void onChildMoved(T child, String previousChildName);

  public void onChildRemoved(T child);

  public void onError(FirebaseError error);
}