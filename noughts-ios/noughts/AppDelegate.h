#import <UIKit/UIKit.h>
#import <FacebookSDK/FacebookSDK.h>
#import "Model.h"

FOUNDATION_EXPORT NSString *const kLoggedInToFacebook;

@interface AppDelegate : UIResponder <UIApplicationDelegate>
@property(strong, nonatomic) UIWindow *window;
@property(strong, nonatomic) NSArray *friends;
@property(strong, nonatomic) NSMutableDictionary *friendPhotos;
@property(strong, nonatomic) FBFrictionlessRecipientCache *friendCache;
@property(strong, nonatomic) NTSModel *model;

+ (NTSModel*)getModel;
- (UIStoryboard*)mainStoryboard;
- (void)logInToFacebook:(void(^)())callback;
@end
