package com.tinlib.message;

public class Keys {
  public static <T> Key<T> createKey(Class<T> classObject) {
    return new Key<T>(){};
  }

  public static Key<Void> createVoidKey() {
    return new Key<Void>(){};
  }
}
