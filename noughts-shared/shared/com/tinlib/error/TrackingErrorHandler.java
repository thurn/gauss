package com.tinlib.error;

import com.google.common.collect.ImmutableMap;
import com.tinlib.core.TinKeys2;
import com.tinlib.infuse.Injector;
import com.tinlib.analytics.AnalyticsService;

public class TrackingErrorHandler implements ErrorHandler {
  private final AnalyticsService analyticsService;

  public TrackingErrorHandler(Injector injector) {
    analyticsService = injector.get(TinKeys2.ANALYTICS_SERVICE);
  }

  @Override
  public void error(String message, Object[] args) {
    ImmutableMap.Builder<String, String> map = ImmutableMap.builder();
    for (int i = 0; i < args.length; ++i) {
      map.put("[" + i + "]", args[i] + "");
    }
    analyticsService.trackEvent(message, map.build());
  }
}
