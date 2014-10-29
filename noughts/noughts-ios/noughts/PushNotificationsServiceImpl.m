#import "PushNotificationsServiceImpl.h"
#import <Parse/Parse.h>
#import "JavaUtils.h"

@implementation PushNotificationsServiceImpl

- (void)addChannelWithNSString:(NSString *)channelId {
  PFInstallation *currentInstallation = [PFInstallation currentInstallation];
  [currentInstallation addUniqueObject:channelId forKey:@"channels"];
  [currentInstallation saveInBackground];
}

- (void)removeChannelWithNSString:(NSString *)channelId {
  PFInstallation *currentInstallation = [PFInstallation currentInstallation];
  [currentInstallation removeObject:channelId forKey:@"channels"];
  [currentInstallation saveInBackground];
}

- (void)sendPushNotificationWithNSString:(NSString *)channelId
                         withJavaUtilMap:(id<JavaUtilMap>)data {
  PFPush *push = [PFPush new];
  [push setData:[JavaUtils javaUtilMapToNsDictionary:data]];
  [push setChannel:channelId];
  [push sendPushInBackground];
}

@end
