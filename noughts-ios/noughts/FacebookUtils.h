#import <Foundation/Foundation.h>
#import "Profile.h"

@interface FacebookUtils : NSObject
+ (NTSProfile*)profileFromFacebookDictionary:(NSDictionary*)dictionary;
+ (BOOL)isFacebookUser;
+ (NSString*)getFacebookId;
+ (void)logInToFacebook:(UIView*)view withCallback:(void(^)())callback;
@end
