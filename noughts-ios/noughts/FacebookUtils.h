#import <Foundation/Foundation.h>
#import "Profile.h"

@interface FacebookUtils : NSObject
+ (NTSProfile*)profileFromFacebookDictionary:(NSDictionary*)dictionary;
@end
