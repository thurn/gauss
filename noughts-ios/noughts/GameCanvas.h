#import <UIKit/UIKit.h>
#import "Game.h"
#import "CommandUpdateListener.h"

@protocol GameCanvasDelegate
- (void)handleSquareTapAtX:(int)x AtY:(int)y;
- (void)handleDragToX:(int)x toY:(int)y;
- (BOOL)allowDragToX:(int)x toY:(int)y;
@end

@interface GameCanvas : UIView <NTSCommandUpdateListener>
@property(weak,nonatomic) id<GameCanvasDelegate> delegate;
@end
