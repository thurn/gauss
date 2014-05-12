package ca.thurn.noughts.shared;

import java.util.Map;

public interface AnalyticsService {
  public void trackEvent(String name);

  public void trackEvent(String name, Map<String, String> dimensions);
}
