package com.tinlib.shared;

import com.google.common.collect.ImmutableMap;
import com.tinlib.inject.Injector;
import com.tinlib.message.Bus;

/**
 * Handles application errors by logging them.
 *
 * <h1>Dependencies</h1>
 * <ul>
 *   <li>TinKeys.BUS</li>
 *   <li>TinKeys.ANALYTICS_SERVICE</li>
 * </ul>
 *
 * <h1>Output Events</h1>
 * <ul>
 *   <li>TinMessages.ERROR</li>
 * </ul>
 */
public class ErrorService {
  private final Bus bus;
  private final AnalyticsService analyticsService;

  public ErrorService(Injector injector) {
    bus = (Bus)injector.get(TinKeys.BUS);
    analyticsService = (AnalyticsService)injector.get(TinKeys.ANALYTICS_SERVICE);
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
    ImmutableMap.Builder<String, String> map = ImmutableMap.builder();
    for (int i = 0; i < args.length; ++i) {
      map.put("[" + i + "]", args[i].toString());
    }
    analyticsService.trackEvent(message, map.build());
    bus.post(TinMessages.ERROR, String.format(message, args));
  }
}
