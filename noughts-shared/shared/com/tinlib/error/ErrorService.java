package com.tinlib.error;

import com.tinlib.infuse.Injector;

import java.util.Set;

/**
 * Handles application errors by logging them.
 */
public class ErrorService {
  private final Set<ErrorHandler> errorHandlers;

  public ErrorService(Injector injector) {
    errorHandlers = injector.getMultiple(ErrorHandler.class);
  }

  /**
   * Reports an application error with the provided message.
   */
  public void error(String message) {
    error(message, new Object[]{});
  }

  /**
   * Reports an application error with the provided error and format arguments,
   * as in {@link String#format(String, Object...)}.
   */
  public void error(String message, Object... args) {
    for (ErrorHandler handler : errorHandlers) {
      handler.error(message, args);
    }
  }

  public TinException newTinException(String message, Object... args) {
    error(message, args);
    return new TinException(message, args);
  }
}
