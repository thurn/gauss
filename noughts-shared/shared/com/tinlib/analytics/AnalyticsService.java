package com.tinlib.analytics;

import com.google.common.collect.Maps;
import com.tinlib.core.TinKeys2;
import com.tinlib.infuse.Injector;

import java.util.Map;
import java.util.Set;

/**
 * Service interface for tracking user events.
 */
public class AnalyticsService {
  private final Set<AnalyticsHandler> analyticsHandlers;

  public AnalyticsService(Injector injector) {
    analyticsHandlers = injector.getMultiple(TinKeys2.ANALYTICS_HANDLERS);
  }

  /**
   * Tracks a user event with the given name.
   */
  public void trackEvent(String name) {
    trackEvent(name, Maps.<String, String>newHashMap());
  }

  /**
   * Tracks a user event with the given name and dimensions (event parameters).
   */
  public void trackEvent(String name, Map<String, String> dimensions) {
    for (AnalyticsHandler handler : analyticsHandlers) {
      handler.trackEvent(name, dimensions);
    }
  }
}
