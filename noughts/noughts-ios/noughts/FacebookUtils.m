#import "FacebookUtils.h"
#import "Pronoun.h"
#import "ImageString.h"
#import "ImageType.h"
#import "Identifiers.h"
#import <FirebaseSimpleLogin/FirebaseSimpleLogin.h>
#import "AppDelegate.h"
#import "MBProgressHUD.h"
#import "Model.h"
#import "OnUpgradeCompleted.h"
#import "InterfaceUtils.h"
#import "NotificationManager.h"
#import "JoinGameCallbacksImpl.h"
#import "NSURL+QueryDictionary.h"
#import <FacebookSDK/FacebookSDK.h>
#import <Parse/Parse.h>

@interface UpgradeCallback : NSObject<NTSOnUpgradeCompleted>
@property (nonatomic, copy) void (^completionBlock)();
- (void)onUpgradeCompleted;
@end

@implementation UpgradeCallback
- (void)onUpgradeCompleted {
  if (_completionBlock) {
    _completionBlock();
  }
}

- (void)onUpgradeErrorWithNSString:(NSString *)errorMessage {
  [InterfaceUtils error:errorMessage];
}
@end

@implementation FacebookUtils

+ (void)logInToFacebookWithCallback:(void(^)())callback {
  FirebaseSimpleLogin *firebaseLogin = [AppDelegate getFirebaseSimpleLogin];
  UpgradeCallback *onUpgrade = [UpgradeCallback new];
  onUpgrade.completionBlock = callback;
  void (^completionBlock)(NSError*, FAUser*) = ^(NSError *error, FAUser *user) {
      if (!error && user.userId) {
        NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
        [userDefaults setObject:user.userId forKey:kFacebookIdKey];
        [userDefaults synchronize];
        [[NSNotificationCenter defaultCenter]
         postNotification:[NSNotification notificationWithName:kFacebookLoginNotification
                                                        object:user.userId]];
        NTSModel *model = [AppDelegate getModel];
        [model upgradeAccountToFacebookWithNSString:user.userId
                          withNTSOnUpgradeCompleted:onUpgrade];
      } else {
        [PFAnalytics trackEvent:@"Facebook login declined"];
      }
  };
  [firebaseLogin loginToFacebookAppWithId:@"419772734774541"
                               permissions:@[@"basic_info"]
                                  audience:ACFacebookAudienceOnlyMe
                       withCompletionBlock:completionBlock];
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
  [imageBuilder setTypeWithNTSImageTypeEnum:[NTSImageTypeEnum FACEBOOK]];
  NSString *facebookId = dictionary[@"id"];
  if (!facebookId) {
    // FQL calls this field something different just to be annoying
    facebookId = dictionary[@"uid"];
  }
  [imageBuilder
      setStringWithNSString:[NSString stringWithFormat:@"https://graph.facebook.com/%@/picture",
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

+ (void)handleFacebookRequest:(NSURL*)targetUrl {
  if (![self isFacebookUser]) {
    [self logInToFacebookWithCallback:^{
      [self joinGameFromFacebookUrl:targetUrl];
    }];
  } else {
    [self joinGameFromFacebookUrl:targetUrl];
  }
}

+ (void)joinGameFromFacebookUrl:(NSURL*)targetUrl {
  NSString *requestIds = [targetUrl uq_queryDictionary][@"request_ids"];
  NSArray *idsArray = [requestIds componentsSeparatedByString:@","];
  for (int i = 0; i < [idsArray count]; ++i) {
    if (i == [idsArray count] - 1) {
      // Most recent request -- load it
      [self joinGameFromRequestId:idsArray[i]];
    } else {
      // Old request -- delete it
      NSString *facebookId = [FacebookUtils getFacebookId];
      NSString *fullId = [NSString stringWithFormat:@"%@_%@", idsArray[i], facebookId];
      FBRequest *request = [FBRequest requestWithGraphPath:fullId
                                                parameters:@{}
                                                HTTPMethod:@"DELETE"];
      [request startWithCompletionHandler:^(FBRequestConnection *connection,
                                            id result, NSError *error) {}];
    }
  }
}

+ (void)joinGameFromRequestId:(NSString*)requestId {
  NotificationManager *notificationManager = [NotificationManager getInstance];
  void (^profileCallback)(id) = ^(id notificationObject) {
      NTSProfile *profile = notificationObject;
      NTSModel *model = [AppDelegate getModel];
      [model joinFromRequestIdWithNSString:requestId
                            withNTSProfile:profile
                  withNTSJoinGameCallbacks:[JoinGameCallbacksImpl new]];
  };
  [notificationManager loadValueForNotification:kFacebookProfileLoadedNotification
                                      withBlock:profileCallback];
}

@end
