package com.tinlib.inject;

import com.google.common.collect.Sets;

import java.util.Set;

class MultibindingInitializer implements Initializer {
  private final Set<Initializer> initializers = Sets.newHashSet();

  @Override
  public Object initialize(Injector injector) {
    Set<Object> result = Sets.newHashSet();
    for (Initializer initializer : initializers) {
      result.add(initializer.initialize(injector));
    }
    return result;
  }

  void addInitializer(Initializer initializer) {
    initializers.add(initializer);
  }
}
