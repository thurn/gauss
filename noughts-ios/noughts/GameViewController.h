#import "Model.h"
#import "GameView.h"
#import "HasModel.h"

@interface GameViewController : UIViewController <NTSModel_GameUpdateListener,
                                                  GameViewDelegate,
                                                  HasModel>
@property (strong,nonatomic) NSString *currentGameId;
@end
