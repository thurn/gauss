package com.tinlib.error;

import com.firebase.client.Firebase;
import com.tinlib.analytics.AnalyticsHandler;
import com.tinlib.test.TestHelperTwo;
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
    TestHelperTwo.Builder builder = TestHelperTwo.newBuilder(this);
    builder.setFirebase(new Firebase(TestHelperTwo.FIREBASE_URL));
    builder.setAnalyticsHandler(mockAnalyticsHandler);
    builder.setErrorHandler(new ErrorHandler() {
      @Override
      public void error(String message, Object[] args) {
        assertEquals("Error %s", message);
        finished();
      }
    });
    builder.runTest(new TestHelperTwo.Test() {
      @Override
      public void run(TestHelperTwo helper) {
        ErrorService errorService = new ErrorService(helper.injector());
        errorService.error("Error %s", "Error arg");
      }
    });
    endAsyncTestBlock();

    TestHelperTwo.verifyTrackedEvent(mockAnalyticsHandler);
  }
}
