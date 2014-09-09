package com.tinlib.infuse;

import com.google.common.collect.*;

import java.util.List;
import java.util.Map;

class InjectorImpl implements Binder, Injector {
  private final Map<Class<?>, Initializer<?>> initializers = Maps.newHashMap();
  private final SetMultimap<Class<?>, Initializer<?>> multiInitializers = HashMultimap.create();
  private final ImmutableMap<Class<?>, ?> classMap;
  private final boolean allowDuplicates;

  InjectorImpl(List<Module> modules) {
    this(modules, false /* allowDuplicates */);
  }

  InjectorImpl(List<Module> modules, boolean allowDuplicates) {
    this.allowDuplicates = allowDuplicates;

    for (Module module : modules) {
      module.configure(this);
    }

    ImmutableMap.Builder<Class<?>, Object> classMapBuilder = ImmutableMap.builder();
    for (Map.Entry<Class<?>, Initializer<?>> entry : initializers.entrySet()) {
      classMapBuilder.put(entry.getKey(), entry.getValue().initialize(this));
    }

    for (Class<?> key : multiInitializers.keySet()) {
      ImmutableSet.Builder<?> set = ImmutableSet.builder();
      for (Initializer<?> initializer : multiInitializers.get(key)) {
        set.add(initializer.initialize(this));
      }
      classMapBuilder.put(key, set.build());
    }
    classMap = classMapBuilder.build();
  }

  @Override
  public <T> void bindClass(Class<T> classObject, Initializer<T> initializer) {
    if ((initializers.containsKey(classObject) || multiInitializers.containsKey(classObject))
        && !allowDuplicates) {
      throw new RuntimeException("Attempted to bind previously bound class " + classObject);
    }
    initializers.put(classObject, initializer);
  }

  @Override
  public <T> void multibindClass(Class<T> classObject, Initializer<T> initializer) {
    if (initializers.containsKey(classObject)) {
      throw new RuntimeException("Attempted to multibind previously bound class " + classObject);
    }
    multiInitializers.put(classObject, initializer);
  }

  @Override
  public void includeModule(Module module) {
    module.configure(this);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T get(Class<T> classObject) {
    if (classMap.containsKey(classObject)) {
      return (T)classMap.get(classObject);
    } else {
      throw new RuntimeException("No instance bound for class " + classObject);
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> ImmutableSet<T> getMultiple(Class<T> classObject) {
    if (classMap.containsKey(classObject)) {
      return (ImmutableSet<T>)classMap.get(classObject);
    } else {
      throw new RuntimeException("No instance multibound for class " + classObject);
    }
  }
}
