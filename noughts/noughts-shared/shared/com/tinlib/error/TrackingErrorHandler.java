package com.tinlib.error;

import com.google.common.collect.ImmutableMap;
import com.tinlib.analytics.AnalyticsService;
import com.tinlib.infuse.Injector;

public class TrackingErrorHandler implements ErrorHandler {
  private final AnalyticsService analyticsService;

  public TrackingErrorHandler(Injector injector) {
    analyticsService = injector.get(AnalyticsService.class);
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
