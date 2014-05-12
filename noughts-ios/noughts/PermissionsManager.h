#import <Foundation/Foundation.h>

@interface PermissionsManager : NSObject
- (id)init;
- (void)registerForPushIfRequested;
+ (void)requestToEnablePushNotifications;
@end
