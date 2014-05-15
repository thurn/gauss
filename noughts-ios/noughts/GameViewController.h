#import "GameView.h"
#import "GameUpdateListener.h"
#import "GameCanvas.h"

@interface GameViewController : UIViewController<NTSGameUpdateListener,
                                                 GameViewDelegate,
                                                 GameCanvasDelegate>
@property(nonatomic) BOOL tutorialMode;
@property(strong,nonatomic) NSString *currentGameId;
@end
