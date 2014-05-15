#import <Foundation/Foundation.h>

@interface NotificationManager : NSObject
// Designated initializer
+ (void)initializeWithNotifications:(NSArray*)notifications;
+ (NotificationManager*)getInstance;

// Invokes the callback with the last known object associated with this notification name.
// If no object has been associated with this name, the callback will be invoked when one
// becomes available.
- (void)loadValueForNotification:(NSString*)notificationName
                       withBlock:(void(^)(id notificationObject))block;

// Remove all pending callbacks
- (void)unregisterAll;
@end
