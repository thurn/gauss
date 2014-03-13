#import <UIKit/UIKit.h>
#import "Game.h"
#import "GameCanvas.h"

typedef NS_ENUM(NSUInteger, GameMenuSelection) {
  kUnknownSelection,
  kNewGameMenu,
  kMainMenu,
  kGameList,
  kResignOrArchive
};

@protocol GameViewDelegate
- (void)handleGameMenuSelection:(GameMenuSelection)selection;
- (BOOL)canSubmit;
- (BOOL)canUndo;
- (BOOL)canRedo;
- (void)handleSubmit;
- (void)handleUndo;
- (void)handleRedo;
- (BOOL)isGameOver;
@end

@interface GameView : UIView <UIActionSheetDelegate>
@property (weak,nonatomic) id<GameViewDelegate> delegate;
-(void)setGameCanvasDelegate:(id<GameCanvasDelegate>)delegate;
- (id<NTSCommandUpdateListener>)getCommandUpdateListener;
-(void)drawGame:(NTSGame *)game;
-(void)displayGameStatusWithImage:(UIImage*)image
                       withString:(NSString*)string
                        withColor:(UIColor*)color;
-(void)showTapSquareCallout;
-(void)hideTapSquareCallout;
-(void)showSubmitCallout;
-(void)hideSubmitCallout;
-(void)showComputerThinkingIndicator;
-(void)hideComputerThinkingIndicator;
@end
