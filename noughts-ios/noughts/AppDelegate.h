#import <UIKit/UIKit.h>
#import <FirebaseSimpleLogin/FirebaseSimpleLogin.h>
#import "Model.h"

@interface AppDelegate : UIResponder <UIApplicationDelegate>
@property(strong, nonatomic) UIWindow *window;

+ (NTSModel*)getModel;
+ (FirebaseSimpleLogin*)getFirebaseSimpleLogin;
@end
