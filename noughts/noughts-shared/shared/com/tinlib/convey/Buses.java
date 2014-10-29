package com.tinlib.convey;

public final class Buses {
  private Buses() {}

  public static Bus newBus() {
    return new BusImpl();
  }
}
