#import <Foundation/Foundation.h>

@protocol PushNotificationHandlerDelegate
// Return whether or not a remote notification about this game ID should be shown
- (BOOL)shouldDisplayNotification:(NSString*)gameId;
@end

@interface PushNotificationHandler : NSObject
@property(weak,nonatomic) id<PushNotificationHandlerDelegate> delegate;

// Start listening for remote push notifications
- (void)registerHandler;

// Stop listening
- (void)unregisterHandler;
@end
