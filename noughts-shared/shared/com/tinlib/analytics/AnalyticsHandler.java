package com.tinlib.analytics;

import java.util.Map;

public interface AnalyticsHandler {
  /**
   * Tracks a user event with the given name and dimensions (event parameters).
   */
  public void trackEvent(String name, Map<String, String> dimensions);
}
