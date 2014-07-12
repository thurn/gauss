package com.tinlib.shared;

import java.util.Map;

/**
 * Service interface for tracking user events.
 */
public interface AnalyticsService {
  /**
   * Tracks a user event with the given name.
   */
  public void trackEvent(String name);

  /**
   * Tracks a user event with the given name and dimensions (event parameters).
   */
  public void trackEvent(String name, Map<String, String> dimensions);
}
