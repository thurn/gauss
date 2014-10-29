package com.tinlib.convey;

import com.google.common.base.Optional;

public class Keys {
  public static <T> Key<T> createKey(Class<T> classObject) {
    return makeKey(classObject, Optional.<String>absent());
  }

  public static <T> Key<T> createKey(Class<T> classObject, String name) {
    return makeKey(classObject, Optional.of(name));
  }

  public static Key<Void> createVoidKey() {
    return makeKey(Void.class, Optional.<String>absent());
  }

  public static Key<Void> createVoidKey(String name) {
    return makeKey(Void.class, Optional.of(name));
  }

  private static <T> Key<T> makeKey(final Class<T> classObject, final Optional<String> name) {
    return new Key<T>() {
      @Override
      public String toString() {
        if (name.isPresent()) {
          return "[" + name.get() + " (Key<" + classObject.getSimpleName() + ">)]";
        } else {
          return "[Key<" + classObject.getSimpleName() + ">]";
        }
      }
    };
  }
}
