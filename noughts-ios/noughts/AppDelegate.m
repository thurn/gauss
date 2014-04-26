#import "AppDelegate.h"
#import <Parse/Parse.h>
#import "Firebase.h"
#import "GameViewController.h"
#import "MainMenuViewController.h"
#import <CommonCrypto/CommonDigest.h>
#import "PushNotificationListener.h"
#import <Firebase/Firebase.h>
#import <FirebaseSimpleLogin/FirebaseSimpleLogin.h>
#import <FacebookSDK/FacebookSDK.h>
#import "QueryParsing.h"
#import "AFNetworking.h"
#import "RequestLoadedCallback.h"

NSString *const kFacebookId = @"kFacebookId";

@interface AppDelegate () <NTSPushNotificationListener, NTSRequestLoadedCallback>
@property BOOL runningQuery;
@property(strong, nonatomic) FirebaseSimpleLogin *firebaseLogin;
@property(strong, nonatomic) NSMutableArray *onModelReadyListeners;
@end

@implementation AppDelegate

- (BOOL)application:(UIApplication *)application
    didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
  _friendPhotos = [NSMutableDictionary new];
  [Parse setApplicationId:@"mYK2MgBp6q7fjLEyulrqlUkQ8tf3qsSrbtlfh6Je"
                clientKey:@"7Sne8QqyGnoHm3AAUhI2OxpOVjGYvkBG2bEXKkbi"];
  [application registerForRemoteNotificationTypes:UIRemoteNotificationTypeBadge |
      UIRemoteNotificationTypeAlert |
      UIRemoteNotificationTypeSound];

  FCFirebase *firebase = [[FCFirebase alloc]
                          initWithNSString:@"https://noughts.firebaseio.com"];
  if (FBSession.activeSession.state == FBSessionStateCreatedTokenLoaded) {
    [FBSession.activeSession openWithCompletionHandler:nil];
  }
  _firebaseLogin = [[FirebaseSimpleLogin alloc] initWithRef:[firebase getWrappedFirebase]];
  _onModelReadyListeners = [NSMutableArray new];
  [self createAnonymousModel:firebase];
  [_firebaseLogin checkAuthStatusWithBlock:^(NSError *error, FAUser *user) {
    if (user != nil) {
      NSLog(@"Facebook User %@", user.userId);
      [self createFacebookModel:firebase withUserId:user.userId];
      [self onFacebookLoginWithCallback:nil];
    } else {
      NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
      if ([userDefaults valueForKey:kFacebookId]) {
        @throw @"Error logging in to facebook";
      } else {
        NSLog(@"Anonymous User");
        [self createAnonymousModel:firebase];
      }
    }
  }];

  NSString *gameId = launchOptions[@"UIApplicationLaunchOptionsRemoteNotificationKey"][@"gameId"];
  if (gameId) {
    [self pushGameViewWithId:gameId];
  }
  return YES;
}

- (void)onModelReady {
  [_model setPushNotificationListenerWithNTSPushNotificationListener:self];
  for (id <OnModelLoaded> listener in _onModelReadyListeners) {
    [listener onModelLoaded:_model];
  }
}

- (void)alert:(NSString*)message {
  [[[UIAlertView alloc] initWithTitle:@"Error"
                              message:message
                             delegate:nil
                    cancelButtonTitle:@"OK"
                    otherButtonTitles:nil] show];
}

- (void)applicationDidBecomeActive:(UIApplication *)application {
  PFInstallation *currentInstallation = [PFInstallation currentInstallation];
  if (currentInstallation.badge != 0) {
    currentInstallation.badge = 0;
    [currentInstallation saveEventually];
  }
}

- (BOOL)isFacebookAuthorized {
  FBSessionState state = FBSession.activeSession.state;
  return state == FBSessionStateOpen ||
      state == FBSessionStateCreatedTokenLoaded ||
      state == FBSessionStateOpenTokenExtended;
}

- (void)createAnonymousModel:(FCFirebase*)firebase {
  NSString *userKey = [[[UIDevice currentDevice] identifierForVendor] UUIDString];
#if TARGET_IPHONE_SIMULATOR
  userKey = UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPad ? @"Pad" : @"Phone";
#endif
  
  NSString *userId = [self sha1:userKey];
  _model = [[NTSModel alloc] initWithNSString:userId
                                 withNSString:userKey
                               withFCFirebase:firebase];
  [self onModelReady];
}

- (void)createFacebookModel:(FCFirebase*)firebase withUserId:(NSString*)userId {
  _model = [[NTSModel alloc] initWithNSString:userId withNSString:userId withFCFirebase:firebase];
  [self onModelReady];
}

- (NSString*)sha1:(NSString*)input {
  const char *cstr = [input cStringUsingEncoding:NSUTF8StringEncoding];
  NSData *data = [NSData dataWithBytes:cstr length:input.length];
  uint8_t digest[CC_SHA1_DIGEST_LENGTH];
  CC_SHA1(data.bytes, (CC_LONG)data.length, digest);
  NSMutableString* output = [NSMutableString stringWithCapacity:CC_SHA1_DIGEST_LENGTH * 2];
  
  for(int i = 0; i < CC_SHA1_DIGEST_LENGTH; i++) {
    [output appendFormat:@"%02x", digest[i]];
  }
  return output;
}

- (void)application:(UIApplication *)application
    didRegisterForRemoteNotificationsWithDeviceToken:(NSData *)deviceToken {
  PFInstallation *currentInstallation = [PFInstallation currentInstallation];
  [currentInstallation setDeviceTokenFromData:deviceToken];
  [currentInstallation saveInBackground];
}

- (void)application:(UIApplication *)application
    didReceiveRemoteNotification:(NSDictionary *)userInfo {
  NSLog(@"didReceiveNotification %@", userInfo);
//  if (userInfo[@"gameId"]) {
//    [self pushGameViewWithId:userInfo[@"gameId"]];
//  } else {
//    [PFPush handlePush:userInfo];
//  }
}

- (BOOL)application:(UIApplication *)application
            openURL:(NSURL *)url
  sourceApplication:(NSString *)sourceApplication
         annotation:(id)annotation {
  if ([[url scheme] isEqualToString:@"noughts"]) {
    NSArray *paths = [url pathComponents];
    NSString *gameId = paths[1];
    [self pushGameViewWithId:gameId];
  }
  [FBAppCall handleOpenURL:url
         sourceApplication:sourceApplication
           fallbackHandler:^(FBAppCall *call) {
    if (call.appLinkData && call.appLinkData.targetURL) {
      NSURL *target = call.appLinkData.targetURL;
      NSString *requestIds = [QueryParsing dictionaryFromQueryComponents:target][@"request_ids"][0];
      NSArray *idsArray = [requestIds componentsSeparatedByString:@","];
      for (int i = 0; i < [idsArray count]; ++i) {
        if (i == [idsArray count] - 1) {
          // Most recent request -- load it
          NSLog(@"load game %@", idsArray[i]);
          [AppDelegate registerForOnModelLoadedWithCallback:^(NTSModel *model) {
            [model subscribeToRequestIdsWithNSString:idsArray[i]
                        withNTSRequestLoadedCallback:self];
          }];
        } else {
          // Old request -- delete it
          NSString *fullId = [idsArray[i] stringByAppendingString:@"_100008083952081"];
          NSLog(@"delete game %@", fullId);
          FBRequest *request = [FBRequest requestWithGraphPath:fullId
                                                    parameters:@{}
                                                    HTTPMethod:@"DELETE"];
          [request startWithCompletionHandler:^(FBRequestConnection *connection,
                                                id result, NSError *error) {
          }];
        }
      }
    }
  }];
  return YES;
}

- (void)onRequestLoadedWithNSString:(NSString *)gameId {
  [self pushGameViewWithId:gameId];
}

- (void)pushGameViewWithId:(NSString*)gameId {
  [_model subscribeViewerToGameWithNSString:gameId];
  UINavigationController *root = (UINavigationController*)self.window.rootViewController;
  root.viewControllers = @[];
  MainMenuViewController *mainMenu =
  [[self mainStoryboard] instantiateViewControllerWithIdentifier:@"MainMenuViewController"];
  [root pushViewController:mainMenu animated:NO];
  GameViewController *gameViewController =
  [[self mainStoryboard] instantiateViewControllerWithIdentifier:@"GameViewController"];
  gameViewController.currentGameId = gameId;
  [root pushViewController:gameViewController animated:YES];
}

- (UIStoryboard*)mainStoryboard {
  NSString *storyboardName = UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPad ?
      @"Main_iPad" : @"Main_iPhone";
  return [UIStoryboard storyboardWithName:storyboardName bundle: nil];
}

- (void)logInToFacebook:(void(^)())callback {
  [_firebaseLogin loginToFacebookAppWithId:@"419772734774541"
                           permissions:@[@"basic_info"]
                              audience:ACFacebookAudienceOnlyMe
                   withCompletionBlock:^(NSError *error, FAUser *user) {
                     if (error == nil) {
                       NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
                       [userDefaults setObject:user.userId forKey:kFacebookId];
                       [userDefaults synchronize];
                       [self onFacebookLoginWithCallback:callback];
                       // handle account upgrade
                     }
                   }];
}

- (void)onFacebookLoginWithCallback:(void(^)())callback {
  [self getFacebookFriendsWithCallback:callback];
  if (!_friendCache) {
    _friendCache = [FBFrictionlessRecipientCache new];
  }
  [_friendCache prefetchAndCacheForSession:nil];
}

- (void)getFacebookFriendsWithCallback:(void(^)())callback {
  if (_friends) {
    callback();
    return;
  }
  if (_runningQuery) return;
  _runningQuery = YES;
  NSString *query = @"SELECT uid,mutual_friend_count,name,first_name,sex,is_app_user "
                    @"FROM user WHERE uid IN "
                    @"( SELECT uid2 FROM friend WHERE uid1=me() )";
  NSDictionary *queryParam = @{ @"q": query };
  [FBRequestConnection startWithGraphPath:@"/fql"
                               parameters:queryParam
                               HTTPMethod:@"GET"
                        completionHandler:^(FBRequestConnection *connection,
                                            id result,
                                            NSError *error) {
                          if (!error) {
                            [self handleFriendsResult:result[@"data"] withCallback:callback];
                          }
                          _runningQuery = NO;
                        }];
}

- (void)handleFriendsResult:(NSArray*)array withCallback:(void(^)())callback {
  NSSortDescriptor *appUser = [[NSSortDescriptor alloc] initWithKey:@"is_app_user"
                                                          ascending:NO];
  NSSortDescriptor *mutalFriends = [[NSSortDescriptor alloc] initWithKey:@"mutual_friend_count"
                                                               ascending:NO];
  _friends = [array sortedArrayUsingDescriptors:@[appUser, mutalFriends]];
  if (callback) {
    callback();
  }
}

- (void)onJoinedGameWithJavaUtilList:(id<JavaUtilList>)channelIds {
  PFInstallation *currentInstallation = [PFInstallation currentInstallation];
  for (NSString *channel in (id<NSFastEnumeration>)channelIds) {
    [currentInstallation addUniqueObject:channel forKey:@"channels"];
  }
  [currentInstallation saveInBackground];
}

- (void)onLeftGameWithJavaUtilList:(id<JavaUtilList>)channelIds {
  PFInstallation *currentInstallation = [PFInstallation currentInstallation];
  for (NSString *channel in (id<NSFastEnumeration>)channelIds) {
    [currentInstallation removeObject:channel forKey:@"channels"];
  }
  [currentInstallation saveInBackground];
}

- (void)onPushRequiredWithNSString:(NSString*)channelId
                      withNSString:(NSString*)gameId
                      withNSString:(NSString *)message {
  NSDictionary* data = @{@"alert": message,
                         @"badge": @"Increment",
                         @"gameId": gameId,
                         @"title": @"noughts"};
  NSLog(@"sending push %@", message);
  PFPush *push = [PFPush new];
  [push setData:data];
  [push setChannel:channelId];
  [push sendPushInBackground];
}

+ (void)registerForOnModelLoaded:(id<OnModelLoaded>)object {
  AppDelegate* appDelegate = (AppDelegate*)[UIApplication sharedApplication].delegate;
  if (appDelegate.model) {
    [object onModelLoaded:appDelegate.model];
  } else {
    [appDelegate.onModelReadyListeners addObject:object];
  }
}

+ (void)registerForOnModelLoadedWithCallback:(void(^)(NTSModel* model))callback {
  AppDelegate* appDelegate = (AppDelegate*)[UIApplication sharedApplication].delegate;
  if (appDelegate.model) {
    callback(appDelegate.model);
  } else {
    callback(appDelegate.model);
  }
}

+ (NTSModel*)getModel {
  AppDelegate* appDelegate = (AppDelegate*)[UIApplication sharedApplication].delegate;
  return appDelegate.model;
}

@end
