#import "AnalyticsServiceImpl.h"
#import <Parse/Parse.h>
#import "JavaUtils.h"

@implementation AnalyticsServiceImpl
- (void)trackEventWithNSString:(NSString *)name {
  [PFAnalytics trackEvent:name];
}

- (void)trackEventWithNSString:(NSString *)name withJavaUtilMap:(id<JavaUtilMap>)dimensions {
  [PFAnalytics trackEvent:name dimensions:[JavaUtils javaUtilMapToNsDictionary:dimensions]];
}

@end
