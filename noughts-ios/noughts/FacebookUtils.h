#import <Foundation/Foundation.h>
#import "Profile.h"

@interface FacebookUtils : NSObject
// Converts a dictionary of facebook profile attributes into an NTSProfile
+ (NTSProfile*)profileFromFacebookDictionary:(NSDictionary*)dictionary;

// Returns true if the user has previously logged in with Facebook
+ (BOOL)isFacebookUser;

// Gets the user's facebook ID from disk
+ (NSString*)getFacebookId;

// Logs the user in to Facebook, invoking the provided callback on success. It
// is possible that the callback will never be invoked, e.g. if the user declines
// to authorize the app.
+ (void)logInToFacebookWithCallback:(void(^)())callback;

// Handles a URL from a facebook request notification
+ (void)handleFacebookRequest:(NSURL*)targetUrl;
@end
