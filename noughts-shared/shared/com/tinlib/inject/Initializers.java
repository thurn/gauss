package com.tinlib.inject;

public class Initializers {
  public static Initializer returnValue(final Object object) {
    return new Initializer() {
      @Override
      public Object initialize(Injector injector) {
        return object;
      }
    };
  }
}
