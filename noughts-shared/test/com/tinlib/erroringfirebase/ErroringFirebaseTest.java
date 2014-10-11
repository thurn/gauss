package com.tinlib.erroringfirebase;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class ErroringFirebaseTest {
  private final String URL = "https://erroringfirebase.firebaseio-demo.com";

  @Test
  public void testFailChildListener() {
    final AtomicBoolean ran = new AtomicBoolean(false);
    ErroringFirebase erroringFirebase = new ErroringFirebase(URL, "", "addChildEventListener");
    erroringFirebase.addChildEventListener(new ChildEventListener() {
      @Override
      public void onChildAdded(DataSnapshot dataSnapshot, String s) {}
      @Override
      public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
      @Override
      public void onChildRemoved(DataSnapshot dataSnapshot) {}
      @Override
      public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
      @Override
      public void onCancelled(FirebaseError firebaseError) {
        ran.set(true);
      }
    });
    assertTrue(ran.get());
  }
}
