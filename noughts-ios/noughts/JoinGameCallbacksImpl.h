#import <Foundation/Foundation.h>
#import "JoinGameCallbacks.h"

@interface JoinGameCallbacksImpl : NSObject<NTSJoinGameCallbacks>
// Called when the user joins a game
- (void)onJoinedGameWithNTSGame:(NTSGame *)game;

// Called when there's an error joining a game
- (void)onErrorJoiningGameWithNSString:(NSString *)errorMessage;
@end
