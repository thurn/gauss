#import <Foundation/Foundation.h>

#import "PushNotificationService.h"

@interface PushNotificationsServiceImpl : NSObject <NTSPushNotificationService>
- (void)addChannelWithNSString:(NSString *)channelId;
- (void)removeChannelWithNSString:(NSString *)channelId;
- (void)sendPushNotificationWithNSString:(NSString *)channelId
                         withJavaUtilMap:(id<JavaUtilMap>)data;
@end
