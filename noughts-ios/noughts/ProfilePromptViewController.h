#import <UIKit/UIKit.h>
#import "Game.h"

FOUNDATION_EXPORT NSString *const kPlayerLocalNameKey;

@protocol ProfilePromptViewControllerDelegate
@end

@interface ProfilePromptViewController : UIViewController
- (id)initWithGameId:(NSString*)gameId withProposedName:(NSString*)proposedName;
@end
