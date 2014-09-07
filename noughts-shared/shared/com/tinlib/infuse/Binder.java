package com.tinlib.infuse;

public interface Binder {
  public void bindKey(String key, Initializer initializer);

  public void bindSingletonKey(String key, Initializer initializer);

  public void multibindKey(String key, Initializer initializer);

  public void includeModule(Module module);
}
