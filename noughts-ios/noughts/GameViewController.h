#import "GameView.h"
#import "HasModel.h"
#import "GameUpdateListener.h"
#import "GameCanvas.h"

@interface GameViewController : UIViewController <NTSGameUpdateListener,
                                                  GameViewDelegate,
                                                  GameCanvasDelegate,
                                                  HasModel>
@property(strong,nonatomic) NSString *currentGameId;
@property(nonatomic) BOOL tutorialMode;
@end
