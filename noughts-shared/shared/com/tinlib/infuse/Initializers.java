package com.tinlib.infuse;

import static com.google.common.base.Preconditions.checkNotNull;

public class Initializers {
  public static Initializer returnValue(final Object object) {
    checkNotNull(object);
    return new Initializer() {
      @Override
      public Object initialize(Injector injector) {
        return object;
      }
    };
  }
}
