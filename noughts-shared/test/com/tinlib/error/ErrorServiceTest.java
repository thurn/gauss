package com.tinlib.error;

import com.firebase.client.Firebase;
import com.tinlib.analytics.AnalyticsHandler;
import com.tinlib.asynctest.AsyncTestCase;
import com.tinlib.test.TestConfiguration;
import com.tinlib.test.TestHelper;
import com.tinlib.util.Procedure;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ErrorServiceTest extends AsyncTestCase {
  @Mock
  AnalyticsHandler mockAnalyticsHandler;

  @Test
  public void testError() {
    beginAsyncTestBlock();
    TestConfiguration.Builder builder = TestConfiguration.newBuilder();
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    builder.multibindInstance(AnalyticsHandler.class, mockAnalyticsHandler);
    builder.setFailOnError(false);
    builder.multibindInstance(ErrorHandler.class, new ErrorHandler() {
      @Override
      public void error(String message, Object[] args) {
        assertEquals("Error %s", message);
        finished();
      }
    });
    TestHelper.runTest(this, builder.build(), new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
        ErrorService errorService = helper.injector().get(ErrorService.class);
        errorService.error("Error %s", "Error arg");
      }
    });
    endAsyncTestBlock();

    TestHelper.verifyTrackedEvent(mockAnalyticsHandler);
  }
}
