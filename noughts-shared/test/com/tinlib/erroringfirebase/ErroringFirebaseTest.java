package com.tinlib.erroringfirebase;

import com.firebase.client.*;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertNotNull;
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

  @Test
  public void testFailSingleValueEventListener() {
    final AtomicBoolean ran = new AtomicBoolean(false);
    ErroringFirebase erroringFirebase = new ErroringFirebase(URL, "",
        "addListenerForSingleValueEvent");
    erroringFirebase.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
      }

      @Override
      public void onCancelled(FirebaseError firebaseError) {
        ran.set(true);
      }
    });
    assertTrue(ran.get());
  }

  @Test
  public void testFailValueEventListener() {
    final AtomicBoolean ran = new AtomicBoolean(false);
    ErroringFirebase erroringFirebase = new ErroringFirebase(URL, "", "addValueEventListener");
    erroringFirebase.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
      }

      @Override
      public void onCancelled(FirebaseError firebaseError) {
        ran.set(true);
      }
    });
    assertTrue(ran.get());
  }

  @Test
  public void testFailAuth() {
    final AtomicBoolean ran = new AtomicBoolean(false);
    ErroringFirebase erroringFirebase = new ErroringFirebase(URL, "", "auth");
    erroringFirebase.auth("credential", new Firebase.AuthListener() {
      @Override
      public void onAuthError(FirebaseError firebaseError) {
        ran.set(true);
      }
      @Override
      public void onAuthSuccess(Object o) {}
      @Override
      public void onAuthRevoked(FirebaseError firebaseError) {}
    });
    assertTrue(ran.get());
  }

  @Test
  public void testChild() {
    final AtomicBoolean ran = new AtomicBoolean(false);
    ErroringFirebase erroringFirebase = new ErroringFirebase(URL, "child", "addValueEventListener");
    Firebase child = erroringFirebase.child("child");
    child.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
      }

      @Override
      public void onCancelled(FirebaseError firebaseError) {
        ran.set(true);
      }
    });
    assertTrue(ran.get());
  }

  @Test
  public void testGetParent() {
    final AtomicBoolean ran = new AtomicBoolean(false);
    ErroringFirebase erroringFirebase = new ErroringFirebase(URL, "", "addValueEventListener");
    Firebase parent = erroringFirebase.child("child").getParent();
    parent.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {}

      @Override
      public void onCancelled(FirebaseError firebaseError) {
        ran.set(true);
      }
    });
    assertTrue(ran.get());
  }

  @Test
  public void testGetRoot() {
    final AtomicBoolean ran = new AtomicBoolean(false);
    ErroringFirebase erroringFirebase = new ErroringFirebase(URL, "", "addValueEventListener");
    Firebase parent = erroringFirebase.child("child").getRoot();
    parent.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {}

      @Override
      public void onCancelled(FirebaseError firebaseError) {
        ran.set(true);
      }
    });
    assertTrue(ran.get());
  }

  @Test
  public void testPush() {
    final AtomicBoolean ran = new AtomicBoolean(false);
    ErroringFirebase erroringFirebase = new ErroringFirebase(URL, "", "addValueEventListener");
    Firebase pushed = erroringFirebase.push();
    pushed.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {}

      @Override
      public void onCancelled(FirebaseError firebaseError) {
        ran.set(true);
      }
    });
    assertTrue(ran.get());
  }

  @Test
  public void testRemoveValue() {
    final AtomicBoolean ran = new AtomicBoolean(false);
    ErroringFirebase erroringFirebase = new ErroringFirebase(URL, "", "removeValue");
    erroringFirebase.removeValue(new Firebase.CompletionListener() {
      @Override
      public void onComplete(FirebaseError firebaseError, Firebase firebase) {
        assertNotNull(firebaseError);
        ran.set(true);
      }
    });
    assertTrue(ran.get());
  }

  @Test
  public void testRunTransaction() {
    final AtomicBoolean ran = new AtomicBoolean(false);
    ErroringFirebase erroringFirebase = new ErroringFirebase(URL, "", "runTransaction");
    erroringFirebase.runTransaction(new Transaction.Handler() {
      @Override
      public Transaction.Result doTransaction(MutableData mutableData) {
        return Transaction.success(mutableData);
      }

      @Override
      public void onComplete(FirebaseError firebaseError, boolean b, DataSnapshot dataSnapshot) {
        assertNotNull(firebaseError);
        ran.set(true);
      }
    });
    assertTrue(ran.get());
  }

  @Test
  public void testSetPriority() {
    final AtomicBoolean ran = new AtomicBoolean(false);
    ErroringFirebase erroringFirebase = new ErroringFirebase(URL, "", "setPriority");
    erroringFirebase.setPriority(12, new Firebase.CompletionListener() {
      @Override
      public void onComplete(FirebaseError firebaseError, Firebase firebase) {
        assertNotNull(firebaseError);
        ran.set(true);
      }
    });
    assertTrue(ran.get());
  }

  @Test
  public void testSetValue() {
    final AtomicBoolean ran = new AtomicBoolean(false);
    ErroringFirebase erroringFirebase = new ErroringFirebase(URL, "", "setValue");
    erroringFirebase.setValue(12, new Firebase.CompletionListener() {
      @Override
      public void onComplete(FirebaseError firebaseError, Firebase firebase) {
        assertNotNull(firebaseError);
        ran.set(true);
      }
    });
    assertTrue(ran.get());
  }

  @Test
  public void testUpdateChildren() {
    final AtomicBoolean ran = new AtomicBoolean(false);
    ErroringFirebase erroringFirebase = new ErroringFirebase(URL, "", "updateChildren");
    erroringFirebase.updateChildren(Maps.<String, Object>newHashMap(),
        new Firebase.CompletionListener() {
      @Override
      public void onComplete(FirebaseError firebaseError, Firebase firebase) {
        assertNotNull(firebaseError);
        ran.set(true);
      }
    });
    assertTrue(ran.get());
  }
}
