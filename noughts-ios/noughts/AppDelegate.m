#import "AppDelegate.h"
#import <Parse/Parse.h>
#import "Firebase.h"
#import "GameViewController.h"
#import "MainMenuViewController.h"
#import <CommonCrypto/CommonDigest.h>
#import "PushNotificationListener.h"
#import <Firebase/Firebase.h>
#import <FacebookSDK/FacebookSDK.h>
#import "QueryParsing.h"
#import "RequestLoadedCallback.h"
#import "Identifiers.h"
#import "NotificationManager.h"
#import "Profile.h"
#import "FacebookUtils.h"
#import "InterfaceUtils.h"

@interface AppDelegate () <NTSPushNotificationListener, NTSRequestLoadedCallback>
@property BOOL runningQuery;
@property(strong, nonatomic) FirebaseSimpleLogin *firebaseLogin;
@property(strong, nonatomic) NTSModel *model;
@end

@implementation AppDelegate

- (BOOL)application:(UIApplication *)application
    didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
  [NotificationManager initializeWithNotifications:@[kFacebookProfileLoadedNotification,
                                                     kFacebookFriendsLoadedNotification,
                                                     kRecipientCacheLoadedNotification]];

  [Parse setApplicationId:@"mYK2MgBp6q7fjLEyulrqlUkQ8tf3qsSrbtlfh6Je"
                clientKey:@"7Sne8QqyGnoHm3AAUhI2OxpOVjGYvkBG2bEXKkbi"];
  [application registerForRemoteNotificationTypes:UIRemoteNotificationTypeBadge |
      UIRemoteNotificationTypeAlert |
      UIRemoteNotificationTypeSound];

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
  if ([FacebookUtils isFacebookUser]) {
    [self createFacebookModel:firebase withUserId:[FacebookUtils getFacebookId]];
    [_firebaseLogin checkAuthStatusWithBlock:^(NSError *error, FAUser *user) {
      if (user != nil) {
        [[NSNotificationCenter defaultCenter] postNotification:
         [NSNotification notificationWithName:kFacebookLoginNotification
                                       object:user.userId]];
      } else {
        @throw @"Error logging in to facebook";
      }
    }];
  } else {
    [self createAnonymousModel:firebase];
  }

  [_model setPushNotificationListenerWithNTSPushNotificationListener:self];

  NSString *gameId = launchOptions[UIApplicationLaunchOptionsRemoteNotificationKey][@"gameId"];
  if (gameId) {
    [[NSNotificationCenter defaultCenter] postNotification:
     [NSNotification notificationWithName:kGameRequestedNotification
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

- (void)createAnonymousModel:(FCFirebase*)firebase {
  NSString *userKey = [[[UIDevice currentDevice] identifierForVendor] UUIDString];
#if TARGET_IPHONE_SIMULATOR
  userKey = UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPad ? @"Pad" : @"Phone";
#endif
  
  NSString *userId = [self sha1:userKey];
  _model = [[NTSModel alloc] initWithNSString:userId
                                 withNSString:userKey
                               withFCFirebase:firebase];
}

- (void)createFacebookModel:(FCFirebase*)firebase withUserId:(NSString*)userId {
  _model = [[NTSModel alloc] initWithNSString:userId withNSString:userId withFCFirebase:firebase];
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
  NSLog(@"didReceiveRemoteNotification %@", userInfo);
  [[NSNotificationCenter defaultCenter] postNotification:
   [NSNotification notificationWithName:kPushNotificationReceivedNotification
                                 object:userInfo]];
}

- (BOOL)application:(UIApplication *)application
            openURL:(NSURL *)url
  sourceApplication:(NSString *)sourceApplication
         annotation:(id)annotation {

  if ([[url scheme] isEqualToString:@"noughts"]) {
    NSArray *paths = [url pathComponents];
    NSString *gameId = paths[1];
    [[NSNotificationCenter defaultCenter] postNotification:
     [NSNotification notificationWithName:kGameRequestedNotification
                                   object:gameId]];
    return YES;
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
          [_model subscribeToRequestIdsWithNSString:idsArray[i]
                      withNTSRequestLoadedCallback:self];
        } else {
          // Old request -- delete it
          NSString *facebookId = [FacebookUtils getFacebookId];
          NSString *fullId = [NSString stringWithFormat:@"%@_%@", idsArray[i], facebookId];
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
  [[NSNotificationCenter defaultCenter] postNotification:
   [NSNotification notificationWithName:kGameRequestedNotification
                                 object:gameId]];
}

- (void)gameRequested:(NSNotification*)notification {
  NSString *gameId = notification.object;
  [_model subscribeViewerToGameWithNSString:gameId];
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

- (void)onFacebookLogin:(NSNotification*)notification {
  [self getUserFacebookProfile];
  [self getFacebookFriends];
  
  FBFrictionlessRecipientCache *friendCache = [FBFrictionlessRecipientCache new];
  [friendCache prefetchAndCacheForSession:nil];
  [[NSNotificationCenter defaultCenter] postNotification:
   [NSNotification notificationWithName:kRecipientCacheLoadedNotification
                                 object:friendCache]];
}

- (void)getUserFacebookProfile {
  [[FBRequest requestForMe] startWithCompletionHandler:^(FBRequestConnection *connection,
                                                         id result,
                                                         NSError *error) {
    if (error) {
      @throw @"Error loading facebook profile";
    } else {
      NTSProfile *profile = [FacebookUtils profileFromFacebookDictionary:result];
      [[NSNotificationCenter defaultCenter] postNotification:
       [NSNotification notificationWithName:kFacebookProfileLoadedNotification
                                     object:profile]];
    }
  }];
}

- (void)getFacebookFriends {
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
                          if (error) {
                            @throw @"Error loading facebook friends";
                          } else {
                            [self handleFriendsResult:result[@"data"]];
                          }
                        }];
}

- (void)handleFriendsResult:(NSArray*)array {
  NSSortDescriptor *appUser = [[NSSortDescriptor alloc] initWithKey:@"is_app_user"
                                                          ascending:NO];
  NSSortDescriptor *mutalFriends = [[NSSortDescriptor alloc] initWithKey:@"mutual_friend_count"
                                                               ascending:NO];
  NSArray *friends = [array sortedArrayUsingDescriptors:@[appUser, mutalFriends]];
  [[NSNotificationCenter defaultCenter] postNotification:
   [NSNotification notificationWithName:kFacebookFriendsLoadedNotification
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
