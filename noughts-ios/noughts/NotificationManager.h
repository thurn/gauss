#import <Foundation/Foundation.h>

@interface NotificationManager : NSObject
+ (void)initializeWithNotifications:(NSArray*)notifications;
+ (NotificationManager*)getInstance;

- (void)loadValueForNotification:(NSString*)notificationName
                       withBlock:(void(^)(id notificationObject))block;
- (void)unregisterAll;
@end
