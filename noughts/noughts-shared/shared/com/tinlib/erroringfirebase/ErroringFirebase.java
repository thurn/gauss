package com.tinlib.erroringfirebase;

import com.firebase.client.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ErroringFirebase extends Firebase {
  private final FirebaseError error = new FirebaseError(FirebaseError.USER_CODE_EXCEPTION,
      "ErroringFirebase Error");
  private final String errorLocation;
  private final Set<String> errorMethods;

  private static Set<String> toSet(String string) {
    Set<String> result = new HashSet<>();
    result.add(string);
    return result;
  }

  public ErroringFirebase(String url, String errorLocation, String errorMethod) {
    this(url, errorLocation, toSet(errorMethod));
  }

  public ErroringFirebase(String url, String errorLocation, Set<String> errorMethods) {
    super(url);
    this.errorLocation = errorLocation;
    this.errorMethods = errorMethods;
  }

  @Override
  public ChildEventListener addChildEventListener(ChildEventListener listener) {
    if (shouldError("addChildEventListener")) {
      listener.onCancelled(error);
      return listener;
    } else {
      return super.addChildEventListener(listener);
    }
  }

  @Override
  public void addListenerForSingleValueEvent(ValueEventListener listener) {
    if (shouldError("addListenerForSingleValueEvent")) {
      listener.onCancelled(error);
    } else {
      super.addListenerForSingleValueEvent(listener);
    }
  }

  @Override
  public ValueEventListener addValueEventListener(ValueEventListener listener) {
    if (shouldError("addValueEventListener")) {
      listener.onCancelled(error);
      return listener;
    } else {
      return super.addValueEventListener(listener);
    }
  }

  @Override
  public void auth(String credential, AuthListener listener) {
    if (shouldError("auth")) {
      listener.onAuthError(error);
    } else {
      super.auth(credential, listener);
    }
  }

  @Override
  public Firebase child(String pathString) {
    return new ErroringFirebase(super.child(pathString).toString(), errorLocation, errorMethods);
  }

  @Override
  public Firebase getParent() {
    if (super.getParent() == null) return null;
    return new ErroringFirebase(super.getParent().toString(), errorLocation, errorMethods);
  }

  @Override
  public Firebase getRoot() {
    return new ErroringFirebase(super.getRoot().toString(), errorLocation, errorMethods);
  }

  @Override
  public Firebase push() {
    return new ErroringFirebase(super.push().toString(), errorLocation, errorMethods);
  }

  @Override
  public void removeValue(Firebase.CompletionListener listener) {
    if (shouldError("removeValue")) {
      listener.onComplete(error, this);
    } else {
      super.removeValue(listener);
    }
  }

  @Override
  public void runTransaction(Transaction.Handler handler) {
    if (shouldError("runTransaction")) {
      handler.onComplete(error, false, null);
    } else {
      super.runTransaction(handler);
    }
  }

  @Override
  public void runTransaction(Transaction.Handler handler, boolean fireLocalEvents) {
    if (shouldError("runTransaction")) {
      handler.onComplete(error, false, null);
    } else {
      super.runTransaction(handler, fireLocalEvents);
    }
  }

  @Override
  public void setPriority(Object priority, Firebase.CompletionListener listener) {
    if (shouldError("setPriority")) {
      listener.onComplete(error, this);
    } else {
      super.setPriority(priority, listener);
    }
  }

  @Override
  public void setValue(Object value, CompletionListener listener) {
    if (shouldError("setValue")) {
      listener.onComplete(error, this);
    } else {
      super.setValue(value, listener);
    }
  }

  @Override
  public void setValue(Object value, Object priority, CompletionListener listener) {
    if (shouldError("setValue")) {
      listener.onComplete(error, this);
    } else {
      super.setValue(value, priority, listener);
    }
  }

  @Override
  public void updateChildren(Map<String, Object> children, Firebase.CompletionListener listener) {
    if (shouldError("updateChildren")) {
      listener.onComplete(error, this);
    } else {
      super.updateChildren(children, listener);
    }
  }

  private boolean shouldError(String methodName) {
    return (super.toString().contains(errorLocation)) && errorMethods.contains(methodName);
  }
}
