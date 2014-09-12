package com.tinlib.defer;

import com.google.common.base.Function;

public interface Promise<V> {
  public enum State {
    PENDING,
    RESOLVED,
    FAILED
  }

  public void addSuccessHandler(SuccessHandler<V> successHandler);

  public void addSuccessHandler(Runnable runnable);

  public void addFailureHandler(FailureHandler failureHandler);

  public void addCompletionHandler(Runnable onComplete);

  public State getState();

  public <K> Promise<K> then(Function<V, K> function);

  public Promise<Void> then(Runnable runnable);
}
