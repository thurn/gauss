#import <UIKit/UIKit.h>

FOUNDATION_EXPORT NSString *const kP1LocalNameKey;
FOUNDATION_EXPORT NSString *const kP2LocalNameKey;
FOUNDATION_EXPORT NSString *const kSawTutorialKey;
FOUNDATION_EXPORT NSString *const kPreferredDifficulty;

@interface NewLocalGameViewController : UIViewController
@property (nonatomic) BOOL playVsComputerMode;
@end
