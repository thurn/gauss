package com.tinlib.infuse;

class CachingInitializer implements Initializer {
  private final Initializer initializer;
  private Object cached = null;

  CachingInitializer(Initializer initializer) {
    this.initializer = initializer;
  }

  @Override
  public Object initialize(Injector injector) {
    if (cached == null) {
      cached = initializer.initialize(injector);
    }
    return cached;
  }
}
