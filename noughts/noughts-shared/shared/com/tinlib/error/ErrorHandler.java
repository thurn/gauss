package com.tinlib.error;

public interface ErrorHandler {
  /**
   * Reports an application error with the provided error and format arguments,
   * as in {@link String#format(String, Object...)}.
   */
  public void error(String message, Object[] args);
}
