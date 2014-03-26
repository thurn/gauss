#import "LoginHelper.h"
#import <CommonCrypto/CommonDigest.h>
#import <FirebaseSimpleLogin/FirebaseSimpleLogin.h>

@interface LoginHelper ()
@property(strong,nonatomic) FCFirebase *firebase;
@property(strong,nonatomic) FirebaseSimpleLogin *login;
@end

@implementation LoginHelper

- (id)initWithFirebase:(FCFirebase*)firebase {
  _firebase = firebase;
  _login = [[FirebaseSimpleLogin alloc] initWithRef:[_firebase getWrappedFirebase]];
  return self;
}

- (void)loginToFirebase:(void(^)(NSString*))onLogin {
  NSString *password = [[[UIDevice currentDevice] identifierForVendor] UUIDString];
  NSString *userId = [self sha1:password];
  NSString *email = [userId stringByAppendingString:@"@example.com"];
  NSLog(@"trying to log in");
  [_login loginWithEmail:email
            andPassword:password
    withCompletionBlock:^(NSError *error, FAUser *user) {
      NSLog(@"LoginHelper: logged in");
      if (error) {
        NSLog(@"creating user");
        [self createUserWithEmail:email andPassword:password];
      } else {
        onLogin(userId);
      }
    }];
}

- (void)loginViaFacebook:(void(^)(NSString*))onLogin {
  NSLog(@"logging in via facebook");
  [_login loginToFacebookAppWithId:@"419772734774541"
                       permissions:@[]
                          audience:ACFacebookAudienceOnlyMe
               withCompletionBlock:^(NSError *error, FAUser *user) {
                 if (error) {
                   NSLog(@"loginViaFacebook error");
                 } else {
                   NSLog(@"loginViaFacebook Complete");
                 }
                 onLogin(user.userId);
               }];
}

- (void)createUserWithEmail:(NSString*)email andPassword:(NSString*)password {
  [_login createUserWithEmail:email
                    password:password
          andCompletionBlock:^(NSError *error, FAUser *user) {
            if (error) {
               UIAlertView *alert = [[UIAlertView alloc]
                                     initWithTitle:@"ERROR"
                                     message:@"Unable to authenticate!"
                                     delegate:nil
                                     cancelButtonTitle:@"Ok"
                                     otherButtonTitles:nil];
              [alert show];
            } else {
              [self login];
            }
          }];
}

- (void)printAuthStatus {
  [_login checkAuthStatusWithBlock:^(NSError *error, FAUser *user) {
    NSLog(@"user %@", user);
  }];
}

- (NSString*)sha1:(NSString*)input {
  const char *cstr = [input cStringUsingEncoding:NSUTF8StringEncoding];
  NSData *data = [NSData dataWithBytes:cstr length:input.length];
  uint8_t digest[CC_SHA1_DIGEST_LENGTH];
  CC_SHA1(data.bytes, data.length, digest);
  NSMutableString* output = [NSMutableString stringWithCapacity:CC_SHA1_DIGEST_LENGTH * 2];
  
  for(int i = 0; i < CC_SHA1_DIGEST_LENGTH; i++) {
    [output appendFormat:@"%02x", digest[i]];
  }
  return output;
}
@end
