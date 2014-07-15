package com.tinlib.error;

import com.tinlib.inject.Injector;
import com.tinlib.core.TinKeys;

import java.util.Set;

/**
 * Handles application errors by logging them.
 *
 * <h1>Dependencies</h1>
 * <ul>
 *   <li>{@link com.tinlib.core.TinKeys#BUS}</li>
 *   <li>{@link com.tinlib.core.TinKeys#ANALYTICS_SERVICE}</li>
 * </ul>
 *
 * <h1>Output Messages</h1>
 * <ul>
 *   <li>{@link com.tinlib.core.TinMessages#ERROR}</li>
 * </ul>
 */
public class ErrorService {
  private final Set<ErrorHandler> errorHandlers;

  public ErrorService(Injector injector) {
    errorHandlers = injector.getMultiple(TinKeys.ERROR_HANDLERS);
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

  /**
   * Reports an error as in {@link ErrorService#error(String)} and then returns
   * an exception initialized with the error message.
   */
  public RuntimeException die(String message) {
    return die(message, new Object[]{});
  }

  /**
   * Reports an error as in {@link ErrorService#error(String, Object...)} and
   * then returns an exception initialized with the error message.
   */
  public RuntimeException die(String message, Object... args) {
    error(message, args);
    return new RuntimeException(String.format(message, args));
  }
}
