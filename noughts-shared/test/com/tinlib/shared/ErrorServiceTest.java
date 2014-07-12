package com.tinlib.shared;

import com.google.common.collect.ImmutableMap;
import com.tinlib.inject.Binder;
import com.tinlib.inject.Initializers;
import com.tinlib.inject.Module;
import com.tinlib.test.TinTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ErrorServiceTest extends TinTestCase {
  ErrorService errorService;

  @Mock
  AnalyticsService mockAnalyticsService;

  @Override
  public void setUp(Runnable done) {
    errorService = new ErrorService(newTestInjector(new Module() {
      @Override
      public void configure(Binder binder) {
        binder.bindKey(TinKeys.ANALYTICS_SERVICE, Initializers.returnValue(mockAnalyticsService));
      }
    }));
    done.run();
  }

  @Override
  public void tearDown(Runnable done) {
    done.run();
  }

  @Test
  public void testError() {
    beginAsyncTestBlock();
    expectMessage(TinMessages.ERROR, new ValueListener() {
      @Override
      public void onValue(Object object) {
        assertEquals("Error arg", object);
        finished();
      }
    });
    errorService.error("Error %s", "arg");
    verify(mockAnalyticsService, times(1)).trackEvent(eq("Error %s"),
        eq(ImmutableMap.of("[0]", "arg")));
    endAsyncTestBlock();
  }
}
