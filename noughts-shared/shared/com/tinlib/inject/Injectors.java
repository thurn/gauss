package com.tinlib.inject;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public final class Injectors {

  private Injectors() {}

  public static Injector newInjector(List<Module> modules) {
    return new InjectorImpl(modules);
  }

  public static Injector newInjector(Module... modules) {
    return Injectors.newInjector(Arrays.asList(modules));
  }
}
