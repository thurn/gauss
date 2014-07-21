package com.tinlib.inject;

import java.util.Arrays;
import java.util.List;

public class OverridingInjector {
  public static Injector newOverridingInjector(List<Module> modules) {
    return new InjectorImpl(modules, true /* allowDuplicates */);
  }

  public static Injector newOverridingInjector(Module... modules) {
    return OverridingInjector.newOverridingInjector(Arrays.asList(modules));
  }
}
