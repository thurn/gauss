package com.tinlib.inject;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

class InjectorImpl implements Binder, Injector {
  private final Map<String, Initializer> initializers = Maps.newHashMap();
  private final boolean allowDuplicates;

  InjectorImpl(List<Module> modules) {
    this(modules, false /* allowDuplicates */);
  }

  InjectorImpl(List<Module> modules, boolean allowDuplicates) {
    this.allowDuplicates = allowDuplicates;
    for (Module module : modules) {
      module.configure(this);
    }
  }

  @Override
  public void bindKey(String key, Initializer initializer) {
    checkForDuplicate(key);
    initializers.put(key, initializer);
  }

  @Override
  public void bindSingletonKey(String key, Initializer initializer) {
    checkForDuplicate(key);
    initializers.put(key, new CachingInitializer(initializer));
  }

  @Override
  public void multibindKey(String key, Initializer initializer) {
    if (!initializers.containsKey(key)) {
      initializers.put(key, new MultibindingInitializer());
    }
    if (!(initializers.get(key) instanceof MultibindingInitializer)) {
      throw new IllegalArgumentException(
          "Attempted to multibind previously bound key '" + key + "'");
    }
    MultibindingInitializer multibindingInitializer =
        (MultibindingInitializer)initializers.get(key);
    multibindingInitializer.addInitializer(initializer);
  }

  @Override
  public void includeModule(Module module) {
    module.configure(this);
  }

  private void checkForDuplicate(String key) {
    if (initializers.containsKey(key) && !allowDuplicates) {
      throw new IllegalStateException("Attempted to bind duplicate key '" + key + "'");
    }
  }

  @Override
  public <T> T get(String key) {
    if (initializers.containsKey(key)) {
      try {
        // We catch ClassCastException below.
        @SuppressWarnings("unchecked")
        T result = (T) initializers.get(key).initialize(this);
        return result;
      } catch (ClassCastException cce) {
        throw new RuntimeException("Initializer bound to key '" + key +
            "' did not return correct object type.");
      }
    } else {
      throw new IllegalArgumentException("No instance bound for key '" + key + "'");
    }
  }

  @Override
  public <T> Set<T> getMultiple(String key) {
    if (initializers.containsKey(key)) {
      return get(key);
    } else {
      return Sets.newHashSet();
    }
  }
}
