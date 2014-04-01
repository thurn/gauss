#import <UIKit/UIKit.h>
#import <FacebookSDK/FacebookSDK.h>
#import "Model.h"

@interface AppDelegate : UIResponder <UIApplicationDelegate>
@property(strong, nonatomic) UIWindow *window;
@property(strong, nonatomic) NSArray *friends;
@property(strong, nonatomic) NSMutableDictionary *friendPhotos;
@property(strong, nonatomic) FBFrictionlessRecipientCache *friendCache;
@property(strong, nonatomic) NTSModel *model;

+ (NTSModel*)getModel;
- (void)sessionStateChanged:(FBSession*)session state:(FBSessionState)state error:(NSError*)error;
@end
