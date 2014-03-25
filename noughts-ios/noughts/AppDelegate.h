#import <UIKit/UIKit.h>

@interface AppDelegate : UIResponder <UIApplicationDelegate>
@property(strong, nonatomic) UIWindow *window;
@property(strong, nonatomic) NSArray *friends;
@property(strong, nonatomic) NSMutableDictionary *friendPhotos;
//@property(strong, nonatomic) FBFrictionlessRecipientCache *friendCache;
//- (void)sessionStateChanged:(FBSession*)session state:(FBSessionState)state error:(NSError*)error;
@end
