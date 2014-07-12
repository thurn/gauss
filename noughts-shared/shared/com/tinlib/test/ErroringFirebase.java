package com.tinlib.test;

import com.firebase.client.*;

import java.util.Map;

public class ErroringFirebase extends Firebase {
  private final FirebaseError error = new FirebaseError(0, "");

  public ErroringFirebase(String url) {
    super(url);
  }

  @Override
  public ChildEventListener addChildEventListener(ChildEventListener listener) {
    listener.onCancelled(error);
    return listener;
  }

  @Override
  public void addListenerForSingleValueEvent(ValueEventListener listener) {
  }

  @Override
  public ValueEventListener addValueEventListener(ValueEventListener listener) {
    listener.onCancelled(error);
    return listener;
  }

  @Override
  public void auth(String credential, AuthListener listener) {
    listener.onAuthError(error);
  }

  @Override
  public Firebase child(String pathString) {
    return new ErroringFirebase(super.child(pathString).toString());
  }

  @Override
  public Firebase getParent() {
    return new ErroringFirebase(super.getParent().toString());
  }

  @Override
  public Firebase getRoot() {
    return new ErroringFirebase(super.getRoot().toString());
  }

  @Override
  public Firebase push() {
    return new ErroringFirebase(super.push().toString());
  }

  @Override
  public void removeValue(Firebase.CompletionListener listener) {
    listener.onComplete(error, this);
  }

  @Override
  public void runTransaction(Transaction.Handler handler) {
    handler.onComplete(error, false, null);
  }

  @Override
  public void runTransaction(Transaction.Handler handler, boolean fireLocalEvents) {
    handler.onComplete(error, false, null);
  }

  @Override
  public void setPriority(Object priority, Firebase.CompletionListener listener) {
    listener.onComplete(error, this);
  }

  @Override
  public void setValue(Object value, CompletionListener listener) {
    listener.onComplete(error, this);
  }

  @Override
  public void setValue(Object value, Object priority, CompletionListener listener) {
    listener.onComplete(error, this);
  }

  @Override
  public void updateChildren(Map<String, Object> children, Firebase.CompletionListener listener) {
    listener.onComplete(error, this);
  }

  @Override
  public String toString() {
    return "[ErroringFirebase]";
  }
}
