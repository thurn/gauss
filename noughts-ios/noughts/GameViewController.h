#import "GameView.h"
#import "HasModel.h"
#import "GameUpdateListener.h"

@interface GameViewController : UIViewController <NTSGameUpdateListener,
                                                  GameViewDelegate,
                                                  HasModel>
@property(strong,nonatomic) NSString *currentGameId;
@property(nonatomic) BOOL tutorialMode;
@end
