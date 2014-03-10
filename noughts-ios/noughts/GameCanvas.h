#import <UIKit/UIKit.h>
#import "Game.h"

@protocol GameCanvasDelegate
- (void)handleSquareTapAtX:(int)x AtY:(int)y;
- (void)handleDragToX:(int)x toY:(int)y;
- (BOOL)allowDragToX:(int)x toY:(int)y;
@end

@interface GameCanvas : UIView
@property(weak,nonatomic) id<GameCanvasDelegate> delegate;
- (void)drawGame:(NTSGame *)game;
@end
