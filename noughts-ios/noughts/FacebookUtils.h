#import <Foundation/Foundation.h>
#import "Profile.h"

@interface FacebookUtils : NSObject
+ (NTSProfile*)profileFromFacebookDictionary:(NSDictionary*)dictionary;
+ (BOOL)isFacebookUser;
+ (NSString*)getFacebookId;
+ (void)logInToFacebook:(void(^)())callback;
@end
