#import "PushNotificationHandler.h"
#import "Identifiers.h"
#import <CRToast/CRToast.h>

@implementation PushNotificationHandler

- (void)registerHandler {
  [[NSNotificationCenter defaultCenter] addObserver:self
                                           selector:@selector(pushReceived:)
                                               name:kPushNotificationReceivedNotification
                                             object:nil];
}

- (void)pushReceived:(NSNotification*)notification {
  NSDictionary *data = notification.object;
  NSString *gameId = data[@"gameId"];
  if (_delegate && ![_delegate shouldDisplayNotification:gameId]) {
    return;
  }

  CRToastInteractionResponder *responder = [CRToastInteractionResponder
    interactionResponderWithInteractionType:CRToastInteractionTypeAll
                       automaticallyDismiss:YES
                                      block:
      ^(CRToastInteractionType interactionType) {
        [[NSNotificationCenter defaultCenter] postNotification:
         [NSNotification notificationWithName:kGameRequestedNotification
                                       object:gameId]];
      }];
  NSDictionary *options =
    @{
      kCRToastTextKey: data[@"aps"][@"alert"],
      kCRToastNotificationTypeKey: @(CRToastTypeNavigationBar),
      kCRToastTextAlignmentKey: @(NSTextAlignmentCenter),
      kCRToastBackgroundColorKey: [UIColor grayColor],
      kCRToastInteractionRespondersKey: @[responder],
      kCRToastTimeIntervalKey: @3.0,
      kCRToastFontKey: [UIFont systemFontOfSize:15.0],
      kCRToastAnimationInTypeKey :@(CRToastAnimationTypeGravity),
      kCRToastAnimationOutTypeKey :@(CRToastAnimationTypeGravity),
      kCRToastAnimationInDirectionKey :@(CRToastAnimationDirectionTop),
      kCRToastAnimationOutDirectionKey :@(CRToastAnimationDirectionTop)
    };
  [CRToastManager showNotificationWithOptions:options completionBlock:nil];
}
   
- (void)unregisterHandler {
  [[NSNotificationCenter defaultCenter] removeObserver:self
                                                  name:kPushNotificationReceivedNotification
                                                object:nil];
}

@end
