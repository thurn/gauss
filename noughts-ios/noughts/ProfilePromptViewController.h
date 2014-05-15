#import <UIKit/UIKit.h>

@protocol ProfilePromptViewControllerDelegate
@end

@interface ProfilePromptViewController : UIViewController
- (id)initWithGameId:(NSString*)gameId withProposedName:(NSString*)proposedName;
@end
