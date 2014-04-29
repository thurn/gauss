#import <Foundation/Foundation.h>

@protocol PushNotificationHandlerDelegate
- (BOOL)shouldDisplayNotification:(NSString*)gameId;
@end

@interface PushNotificationHandler : NSObject
@property(weak,nonatomic) id<PushNotificationHandlerDelegate> delegate;
- (void)registerHandler;
- (void)unregisterHandler;
@end
