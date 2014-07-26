package com.tinlib.test;

import com.firebase.client.DataSnapshot;

public class FakeDataSnapshot extends DataSnapshot {
  private final Object value;
  private final String name;

  public FakeDataSnapshot(Object value, String name) {
    super(null /* ref */, null /* node */);
    this.value = value;
    this.name = name;
  }

  @Override
  public Object getValue() {
    return value;
  }

  @Override
  public String getName() {
    return name;
  }
}
