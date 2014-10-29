package com.tinlib.util;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.tinlib.error.ErrorService;

public class AbstractChildEventListener implements ChildEventListener {
  private final ErrorService errorService;
  private final String listenerName;

  public AbstractChildEventListener(ErrorService errorService, String listenerName) {
    this.errorService = errorService;
    this.listenerName = listenerName;
  }

  @Override
  public void onCancelled(FirebaseError error) {
    errorService.error("Listener " + listenerName + " cancelled. %s", error);
  }

  @Override
  public void onChildAdded(DataSnapshot dataSnapshot, String s) {
  }

  @Override
  public void onChildChanged(DataSnapshot dataSnapshot, String s) {
  }

  @Override
  public void onChildRemoved(DataSnapshot dataSnapshot) {
  }

  @Override
  public void onChildMoved(DataSnapshot dataSnapshot, String s) {
  }
}
