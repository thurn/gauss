#import <UIKit/UIKit.h>
#import <FirebaseSimpleLogin/FirebaseSimpleLogin.h>
#import "Model.h"

// AppDelegate for noughts
@interface AppDelegate : UIResponder<UIApplicationDelegate>
@property(strong, nonatomic) UIWindow *window;

// Get the global Model object
+ (NTSModel*)getModel;

// Get the global Firebase login helper object
+ (FirebaseSimpleLogin*)getFirebaseSimpleLogin;
@end
