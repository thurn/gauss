#import "FacebookUtils.h"
#import "Pronoun.h"
#import "ImageString.h"
#import "ImageType.h"
#import "Identifiers.h"
#import <FirebaseSimpleLogin/FirebaseSimpleLogin.h>
#import "AppDelegate.h"

@implementation FacebookUtils

+ (void)logInToFacebook:(void(^)())callback {
  FirebaseSimpleLogin *firebaseLogin = [AppDelegate getFirebaseSimpleLogin];
  [firebaseLogin loginToFacebookAppWithId:@"419772734774541"
                               permissions:@[@"basic_info"]
                                  audience:ACFacebookAudienceOnlyMe
                       withCompletionBlock:^(NSError *error, FAUser *user) {
                         if (error) {
                           @throw @"Error logging in to Facebook!";
                         } else {
                           NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
                           [userDefaults setObject:user.userId forKey:kFacebookIdKey];
                           [userDefaults synchronize];
                           [[NSNotificationCenter defaultCenter] postNotification:
                            [NSNotification notificationWithName:kFacebookLoginNotification
                                                          object:user.userId]];
                           // handle account upgrade
                           if (callback) {
                             callback();
                           }
                         }
                       }];
}

+ (NTSProfile*)profileFromFacebookDictionary:(NSDictionary*)dictionary {
  NTSProfile_Builder *builder = [NTSProfile newBuilder];
  [builder setNameWithNSString:dictionary[@"first_name"]];
  [builder setIsComputerPlayerWithBoolean:NO];
  NSString *gender = dictionary[@"gender"];
  if ([gender isEqualToString:@"male"]) {
    [builder setPronounWithNTSPronounEnum:[NTSPronounEnum MALE]];
  } else if ([gender isEqualToString:@"female"]) {
    [builder setPronounWithNTSPronounEnum:[NTSPronounEnum FEMALE]];
  } else {
    [builder setPronounWithNTSPronounEnum:[NTSPronounEnum NEUTRAL]];
  }
  NTSImageString_Builder *imageBuilder = [NTSImageString newBuilder];
  [imageBuilder setTypeWithNTSImageTypeEnum:[NTSImageTypeEnum URL]];
  NSString *facebookId = dictionary[@"id"];
  if (!facebookId) {
    // FQL calls this field something different just to be annoying
    facebookId = dictionary[@"uid"];
  }
  [imageBuilder setLargeStringWithNSString:
   [NSString stringWithFormat:@"https://graph.facebook.com/%@/picture?width=362&height=362",
    facebookId]];
  [imageBuilder setMediumStringWithNSString:
   [NSString stringWithFormat:@"https://graph.facebook.com/%@/picture?width=80&height=80",
    facebookId]];
  [imageBuilder setSmallStringWithNSString:
   [NSString stringWithFormat:@"https://graph.facebook.com/%@/picture?width=40&height=40",
    facebookId]];
  [builder setImageStringWithNTSImageString:[imageBuilder build]];
  return [builder build];
}

+ (BOOL)isFacebookUser {
  return [self getFacebookId] != nil;
}

+ (NSString*)getFacebookId {
  NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
  return [userDefaults valueForKey:kFacebookIdKey];
}
@end
