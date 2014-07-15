package com.tinlib.inject;

import java.util.Set;

public interface Injector {
  public <T> T get(String key);

  public <T> Set<T> getMultiple(String key);
}
