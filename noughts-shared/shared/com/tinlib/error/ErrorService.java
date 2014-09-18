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
  public TinException error(String message) {
    return error(message, new Object[]{});
  }

  /**
   * Reports an application error with the provided error and format arguments,
   * as in {@link String#format(String, Object...)}.
   */
  public TinException error(String message, Object... args) {
    for (ErrorHandler handler : errorHandlers) {
      handler.error(message, args);
    }
    return new TinException(message, args);
  }
}
