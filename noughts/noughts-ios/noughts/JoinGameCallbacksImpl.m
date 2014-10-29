#import "JoinGameCallbacksImpl.h"
#import "Identifiers.h"
#import "Game.h"
#import "InterfaceUtils.h"

@implementation JoinGameCallbacksImpl
- (void)onJoinedGameWithNTSGame:(NTSGame *)game {
  [[NSNotificationCenter defaultCenter]
   postNotification:[NSNotification notificationWithName:kGameRequestedNotification
                                                  object:[game getId]]];
}

- (void)onErrorJoiningGameWithNSString:(NSString *)errorMessage {
  NSString *string = @"Error joining game:";
  [InterfaceUtils error:[string stringByAppendingString:errorMessage]];
}
@end
