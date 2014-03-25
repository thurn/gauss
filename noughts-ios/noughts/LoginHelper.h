#import <Foundation/Foundation.h>
#import "Firebase.h"

@protocol LoginHelperDelegate
- (void)onLoggedIn:(NSString*)userId withFirebase:(FCFirebase*)firebase;
@end

@interface LoginHelper : NSObject
- (id)initWithFirebase:(FCFirebase*)firebase;
- (void)loginToFirebase:(void(^)(NSString*))onLogin;
- (void)loginViaFacebook:(void(^)(NSString*))onLogin;
- (void)printAuthStatus;
@end