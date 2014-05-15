#import <Foundation/Foundation.h>

@interface PermissionsManager : NSObject
// Designated initializer
- (id)init;

// Registers the app to receive remote push notifications if the user has requested them
- (void)registerForPushIfRequested;

// Prompts the user to turn on push notifications (if appropriate)
+ (void)requestToEnablePushNotifications;
@end
