#import <Foundation/Foundation.h>

@interface NotificationManager : NSObject
+ (void)initializeWithNotifications:(NSArray*)notifications;
+ (NotificationManager*)getInstance;

- (id)initWithNotifications:(NSArray*)notifications;
- (void)registerForNotification:(NSString*)notificationName
                      withBlock:(void(^)(id notificationObject))block;
- (void)registerForSingleNotification:(NSString*)notificationName
                            withBlock:(void(^)(id notificationObject))block;
- (void)registerForNotification:(NSString*)notificationName
                      withBlock:(void(^)(id notificationObject))block
               withLoadingBlock:(void(^)())loading;
- (id)getLatestValueForNotification:(NSString*)notificationName;
- (void)unregisterForAllNotifications:(id)observer;
@end
