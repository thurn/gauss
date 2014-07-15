package com.tinlib.shared;

import com.tinlib.inject.Binder;
import com.tinlib.inject.Initializers;
import com.tinlib.inject.Module;

public class TestModules {
  public static <T> Module multibinding(final String key, final T value) {
    return new Module() {
      @Override
      public void configure(Binder binder) {
        binder.multibindKey(key, Initializers.returnValue(value));
      }
    };
  }
}
