#import "AppDelegate.h"
#import <Parse/Parse.h>
#import "Firebase.h"
#import "GameViewController.h"
#import "MainMenuViewController.h"
#import <CommonCrypto/CommonDigest.h>
#import "PushNotificationService.h"
#import <Firebase/Firebase.h>
#import <FacebookSDK/FacebookSDK.h>
#import "Identifiers.h"
#import "NotificationManager.h"
#import "Profile.h"
#import "FacebookUtils.h"
#import "InterfaceUtils.h"
#import "PermissionsManager.h"
#import "PushNotificationsServiceImpl.h"
#import "AnalyticsServiceImpl.h"
#import "NSURL+QueryDictionary.h"
#import "JoinGameCallbacks.h"
#import "JoinGameCallbacksImpl.h"
#import "Injectors.h"
#import "Injector.h"
#import "NoughtsModule.h"
#import "JavaUtils.h"

@interface AppDelegate () <NTSJoinGameCallbacks>
@property BOOL runningQuery;
@property(strong, nonatomic) FirebaseSimpleLogin *firebaseLogin;
@property(strong, nonatomic) NTSModel *model;
@property(strong, nonatomic) id<NFUSInjector> injector;
@property(strong, nonatomic) PermissionsManager *permissionsManager;
@end

@implementation AppDelegate

- (BOOL)application:(UIApplication *)application
    didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
  [NotificationManager initializeWithNotifications:@[kFacebookProfileLoadedNotification,
                                                     kFacebookFriendsLoadedNotification,
                                                     kRecipientCacheLoadedNotification]];

  [Parse setApplicationId:@"mYK2MgBp6q7fjLEyulrqlUkQ8tf3qsSrbtlfh6Je"
                clientKey:@"7Sne8QqyGnoHm3AAUhI2OxpOVjGYvkBG2bEXKkbi"];
  [PFAnalytics trackAppOpenedWithLaunchOptions:launchOptions];
  
  _permissionsManager = [PermissionsManager new];
  [Firebase setOption:@"persistence" to:@YES];

  FCFirebase *firebase = [[FCFirebase alloc]
                          initWithNSString:@"https://noughts.firebaseio.com"];
  
  [[NSNotificationCenter defaultCenter] addObserver:self
                                           selector:@selector(gameRequested:)
                                               name:kGameRequestedNotification
                                             object:nil];
  [[NSNotificationCenter defaultCenter] addObserver:self
                                           selector:@selector(onFacebookLogin:)
                                               name:kFacebookLoginNotification
                                             object:nil];

  // Resume pre-existing Facebook session
  if (FBSession.activeSession.state == FBSessionStateCreatedTokenLoaded) {
    [FBSession.activeSession openWithCompletionHandler:nil];
  }

  _firebaseLogin = [[FirebaseSimpleLogin alloc] initWithRef:[firebase getWrappedFirebase]];
  NSArray *modules = @[[NoughtsModule new]];
  _injector = [NFUSInjectors
               newInjectorFromListWithJavaUtilList:[JavaUtils nsArrayToJavaUtilList:modules]];
  
  if ([FacebookUtils isFacebookUser]) {
    _model = [NTSModel facebookModelWithNFUSInjector:_injector
                                        withNSString:[FacebookUtils getFacebookId]
                                        withNSString:@"https://noughts.firebaseio.com"
                      withNTSPushNotificationService:[PushNotificationsServiceImpl new]];
    [_firebaseLogin checkAuthStatusWithBlock:^(NSError *error, FAUser *user) {
        if (user != nil) {
          [[NSNotificationCenter defaultCenter]
              postNotification:[NSNotification notificationWithName:kFacebookLoginNotification
                                                             object:user.userId]];
        } else {
          [InterfaceUtils error:@"Error logging in to facebook"];
        }
    }];
  } else {
    NSString *userKey = [[[UIDevice currentDevice] identifierForVendor] UUIDString];
    NSString *userId = [self sha1:userKey];
    _model = [NTSModel anonymousModelWithNFUSInjector:_injector
                                         withNSString:userId
                                         withNSString:userKey
                                         withNSString:@"https://noughts.firebaseio.com"
                       withNTSPushNotificationService:[PushNotificationsServiceImpl new]];
  }

  NSString *gameId = launchOptions[UIApplicationLaunchOptionsRemoteNotificationKey][@"gameId"];
  if (gameId) {
    [[NSNotificationCenter defaultCenter]
        postNotification:[NSNotification notificationWithName:kGameRequestedNotification
                                                       object:gameId]];
  }

  return YES;
}

- (void)applicationDidBecomeActive:(UIApplication *)application {
  PFInstallation *currentInstallation = [PFInstallation currentInstallation];
  if (currentInstallation.badge != 0) {
    currentInstallation.badge = 0;
    [currentInstallation saveEventually];
  }
}

- (BOOL)application:(UIApplication *)application shouldSaveApplicationState:(NSCoder *)coder {
  return NO;
}

- (BOOL)application:(UIApplication *)application shouldRestoreApplicationState:(NSCoder *)coder {
  return NO;
}

// Computes the SHA1 hash of the provided string
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
  UIApplicationState state = [application applicationState];
  if (state == UIApplicationStateActive) {
    [[NSNotificationCenter defaultCenter] postNotification:
     [NSNotification notificationWithName:kPushNotificationReceivedNotification
                                   object:userInfo]];
  } else {
    NSString *gameId = userInfo[@"gameId"];
    [[NSNotificationCenter defaultCenter] postNotification:
     [NSNotification notificationWithName:kGameRequestedNotification
                                   object:gameId]];
  }
}

- (BOOL)application:(UIApplication *)application
            openURL:(NSURL *)url
  sourceApplication:(NSString *)sourceApplication
         annotation:(id)annotation {
  if ([[url scheme] isEqualToString:@"noughts"]) {
    NSArray *paths = [url pathComponents];
    NSString *gameId = paths[1];
    if ([FacebookUtils isFacebookUser]) {
      NotificationManager *notificationManager = [NotificationManager getInstance];
      void (^profileCallback)(id) = ^(id notificationObject) {
        NTSProfile *profile = notificationObject;
        [_model joinGameWithNSString:gameId
                      withNTSProfile:profile
            withNTSJoinGameCallbacks:[JoinGameCallbacksImpl new]];
      };
      [notificationManager loadValueForNotification:kFacebookProfileLoadedNotification
                                          withBlock:profileCallback];
    } else {
      [_model joinGameWithNSString:gameId
                    withNTSProfile:nil
          withNTSJoinGameCallbacks:[JoinGameCallbacksImpl new]];
    }
    return YES;
  }

  // Handle launch from facebook notification
  [FBAppCall handleOpenURL:url
         sourceApplication:sourceApplication
           fallbackHandler:^(FBAppCall *call) {
      if (call.appLinkData && call.appLinkData.targetURL) {
        [FacebookUtils handleFacebookRequest:call.appLinkData.targetURL];
      }
  }];
  return YES;
}

// Load |GameViewController| with a game ID from this notification
- (void)gameRequested:(NSNotification*)notification {
  NSString *gameId = notification.object;
  UINavigationController *root = (UINavigationController*)self.window.rootViewController;
  root.viewControllers = @[];
  MainMenuViewController *mainMenu =
      [[InterfaceUtils mainStoryboard]
          instantiateViewControllerWithIdentifier:@"MainMenuViewController"];
  [root pushViewController:mainMenu animated:NO];
  GameViewController *gameViewController =
      [[InterfaceUtils mainStoryboard]
          instantiateViewControllerWithIdentifier:@"GameViewController"];
  gameViewController.currentGameId = gameId;
  [root pushViewController:gameViewController animated:YES];
}

- (void)onJoinedGameWithNTSGame:(NTSGame *)game {
  
}

- (void)onErrorJoiningGameWithNSString:(NSString *)errorMessage {
  
}

// Called after the user has been logged in to Facebook
- (void)onFacebookLogin:(NSNotification*)notification {
  [self getUserFacebookProfile];
  [self getFacebookFriends];
  FBFrictionlessRecipientCache *friendCache = [FBFrictionlessRecipientCache new];
  [friendCache prefetchAndCacheForSession:nil];
  [[NSNotificationCenter defaultCenter]
      postNotification:[NSNotification notificationWithName:kRecipientCacheLoadedNotification
                                                     object:friendCache]];
}

// Loads the user's Facebook profile and publishes a notification with the result
- (void)getUserFacebookProfile {
  [[FBRequest requestForMe] startWithCompletionHandler:^(FBRequestConnection *connection,
                                                         id result,
                                                         NSError *error) {
      if (error) {
        [InterfaceUtils error:@"Error loading facebook profile"];
      } else {
        NTSProfile *profile = [FacebookUtils profileFromFacebookDictionary:result];
        [[NSNotificationCenter defaultCenter]
            postNotification:[NSNotification notificationWithName:kFacebookProfileLoadedNotification
                                                           object:profile]];
      }
  }];
}

// Loads the user's Facebook friends and pusblishes a notification with the result
- (void)getFacebookFriends {
  NSString *query = @"SELECT uid,mutual_friend_count,name,first_name,sex,is_app_user "
      @"FROM user WHERE uid IN "
      @"( SELECT uid2 FROM friend WHERE uid1=me() )";
  NSDictionary *queryParam = @{ @"q" : query };
  [FBRequestConnection startWithGraphPath:@"/fql"
                               parameters:queryParam
                               HTTPMethod:@"GET"
                        completionHandler:^(FBRequestConnection *connection,
                                            id result,
                                            NSError *error) {
                            if (error) {
                              [InterfaceUtils error:@"Error loading facebook friends"];
                            } else {
                              [self handleFriendsResult:result[@"data"]];
                            }
                        }];
}

// Sorts the user's friends by mutual_friend_count and publishes a notification with the result
- (void)handleFriendsResult:(NSArray*)array {
  NSSortDescriptor *appUser = [[NSSortDescriptor alloc] initWithKey:@"is_app_user"
                                                          ascending:NO];
  NSSortDescriptor *mutalFriends = [[NSSortDescriptor alloc] initWithKey:@"mutual_friend_count"
                                                               ascending:NO];
  NSArray *friends = [array sortedArrayUsingDescriptors:@[appUser, mutalFriends]];
  [[NSNotificationCenter defaultCenter]
      postNotification:[NSNotification notificationWithName:kFacebookFriendsLoadedNotification
                                                     object:friends]];
}

+ (NTSModel*)getModel {
  AppDelegate *delegate = (AppDelegate*)[UIApplication sharedApplication].delegate;
  return delegate.model;
}

+ (FirebaseSimpleLogin*)getFirebaseSimpleLogin {
  AppDelegate *delegate = (AppDelegate*)[UIApplication sharedApplication].delegate;
  return delegate.firebaseLogin;
}

@end
