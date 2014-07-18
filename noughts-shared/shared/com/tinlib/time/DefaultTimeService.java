package com.tinlib.time;

public class DefaultTimeService implements TimeService {
  @Override
  public long currentTimeMillis() {
    return System.currentTimeMillis();
  }
}
