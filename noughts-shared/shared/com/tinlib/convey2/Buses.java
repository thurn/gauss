package com.tinlib.convey2;

import com.tinlib.services.FirebaseReferences;

public final class Buses {
  private Buses() {}

  public static Bus newBus() {
    return new BusImpl();
  }

  public static void foo() {
    Bus bus = newBus();
    bus.await(TinKeys.VIEWER_ID, TinKeys.FIREBASE_REFERENCES).addSubscriber(
        new Subscriber2<String, FirebaseReferences>() {
      @Override
      public void onMessage(String viewerId, FirebaseReferences firebaseReferences) {

      }
    }).addErrorCallback(new Runnable() {
      @Override
      public void run() {
        throw new RuntimeException();
      }
    });
  }
}
