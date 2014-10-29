#import "NoughtsModule.h"
#import "AnalyticsServiceImpl.h"
#import "BlockInitializer.h"
#import "Binder.h"

@implementation NoughtsModule
- (void)configureWithNFUSBinder:(id<NFUSBinder>)binder {
  [binder bindKeyWithNSString:@"AnalyticsService"
          withNFUSInitializer:[BlockInitializer fromBlock:^id(id<NFUSInjector>injector) {
      return [AnalyticsServiceImpl new];
  }]];
}
@end
