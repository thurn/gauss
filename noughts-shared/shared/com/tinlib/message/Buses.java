package com.tinlib.message;

public final class Buses {
  private Buses() {}

  public static Bus newBus() {
    return new BusImpl();
  }

  public static Bus2 newBus2() {
    return new Bus2Impl();
  }
}
