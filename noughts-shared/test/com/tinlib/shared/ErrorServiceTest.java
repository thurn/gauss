package com.tinlib.shared;

import com.tinlib.analytics.AnalyticsHandler;
import com.tinlib.error.ErrorHandler;
import com.tinlib.error.ErrorService;
import com.tinlib.test.TestHelper;
import com.tinlib.test.TinTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ErrorServiceTest extends TinTestCase {
  @Mock
  AnalyticsHandler mockAnalyticsHandler;

  @Test
  public void testError() {
    beginAsyncTestBlock();
    TestHelper.Builder builder = TestHelper.newBuilder(this);
    builder.setAnalyticsHandler(mockAnalyticsHandler);
    builder.setErrorHandler(new ErrorHandler() {
      @Override
      public void error(String message, Object[] args) {
        assertEquals("Error %s", message);
        finished();
      }
    });
    builder.runTest(new TestHelper.Test() {
      @Override
      public void run(TestHelper helper) {
        ErrorService errorService = new ErrorService(helper.injector());
        errorService.error("Error %s", "Error arg");
      }
    });
    endAsyncTestBlock();

    TestHelper.verifyTrackedEvent(mockAnalyticsHandler);
  }
}
