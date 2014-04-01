#import "GameView.h"
#import "GameUpdateListener.h"
#import "GameCanvas.h"

@interface GameViewController : UIViewController <NTSGameUpdateListener,
                                                  GameViewDelegate,
                                                  GameCanvasDelegate>
@property(strong,nonatomic) NSString *currentGameId;
@property(nonatomic) BOOL tutorialMode;
@end
