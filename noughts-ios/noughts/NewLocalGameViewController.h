#import <UIKit/UIKit.h>
#import "HasModel.h"

FOUNDATION_EXPORT NSString *const kP1LocalNameKey;
FOUNDATION_EXPORT NSString *const kP2LocalNameKey;
FOUNDATION_EXPORT NSString *const kSawTutorialKey;
FOUNDATION_EXPORT NSString *const kPreferredDifficulty;

@interface NewLocalGameViewController : UIViewController <HasModel>
@property (nonatomic) BOOL playVsComputerMode;
@end
