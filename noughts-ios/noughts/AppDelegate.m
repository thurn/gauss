#import "AppDelegate.h"
#import <Parse/Parse.h>
#import "Firebase.h"
#import "GameViewController.h"
#import "MainMenuViewController.h"
#import <CommonCrypto/CommonDigest.h>
#import "PushNotificationListener.h"

@interface AppDelegate () <NTSPushNotificationListener>
@property BOOL runningQuery;
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
  NSString *userKey = [[[UIDevice currentDevice] identifierForVendor] UUIDString];
#if TARGET_IPHONE_SIMULATOR
  userKey = UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPad ? @"Pad" : @"Phone";
  #endif

  NSString *userId = [self sha1:userKey];
  _model = [[NTSModel alloc] initWithNSString:userId
                                 withNSString:userKey
                               withFCFirebase:firebase];
  [_model setPushNotificationListenerWithNTSPushNotificationListener:self];
  return YES;
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
  [PFPush handlePush:userInfo];
}


- (BOOL)application:(UIApplication *)application
            openURL:(NSURL *)url
  sourceApplication:(NSString *)sourceApplication
         annotation:(id)annotation {
  if ([[url scheme] isEqualToString:@"noughts"]) {
    NSArray *paths = [url pathComponents];
    NSString *gameId = paths[1];
    [_model subscribeViewerToGameWithNSString:gameId];
    UINavigationController *root =
        (UINavigationController*)[[[UIApplication sharedApplication] keyWindow ]rootViewController];
    MainMenuViewController *mainMenu =
        [[self mainStoryboard] instantiateViewControllerWithIdentifier:@"MainMenuViewController"];
    [root pushViewController:mainMenu animated:NO];
    GameViewController *gameViewController =
        [[self mainStoryboard] instantiateViewControllerWithIdentifier:@"GameViewController"];
    gameViewController.currentGameId = gameId;
    [root pushViewController:gameViewController animated:YES];
  }
  [FBSession.activeSession setStateChangeHandler:
   ^(FBSession *session, FBSessionState state, NSError *error) {
     AppDelegate* appDelegate = (AppDelegate*)[UIApplication sharedApplication].delegate;
     [appDelegate sessionStateChanged:session state:state error:error];
   }];
  return [FBAppCall handleOpenURL:url sourceApplication:sourceApplication];
}

- (UIStoryboard*)mainStoryboard {
  NSString *storyboardName = UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPad ?
      @"Main_iPad" : @"Main_iPhone";
  return [UIStoryboard storyboardWithName:storyboardName bundle: nil];
}

- (void)sessionStateChanged:(FBSession*)session state:(FBSessionState)state error:(NSError*)error {
  if (!error && state == FBSessionStateOpen) {
    [self getFacebookFriends];
    if (!_friendCache) {
      _friendCache = [FBFrictionlessRecipientCache new];
    }
    [_friendCache prefetchAndCacheForSession:nil];
  }
}

- (void)getFacebookFriends {
  if (_friends || _runningQuery) return;
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
                            [self handleFriendsResult:result[@"data"]];
                          }
                          _runningQuery = NO;
                        }];
}

- (void)handleFriendsResult:(NSArray*)array {
  NSSortDescriptor *appUser = [[NSSortDescriptor alloc] initWithKey:@"is_app_user"
                                                          ascending:NO];
  NSSortDescriptor *mutalFriends = [[NSSortDescriptor alloc] initWithKey:@"mutual_friend_count"
                                                               ascending:NO];
  _friends = [array sortedArrayUsingDescriptors:@[appUser, mutalFriends]];
  dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT,
                                           (unsigned long)NULL), ^(void) {
    [self populateFriendPhotos];
  });
}

- (void)populateFriendPhotos {
  for (NSDictionary *friend in _friends) {
    NSURL *photoUrl = [NSURL URLWithString:
                       [NSString
                        stringWithFormat:
                            @"https://graph.facebook.com/%@/picture?width=100&height=100",
                        friend[@"uid"]]];
    NSData *data = [NSData dataWithContentsOfURL:photoUrl];
    _friendPhotos[friend[@"uid"]] = [[UIImage alloc] initWithData:data];
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

- (void)onPushRequiredWithNSString:(NSString*)channelId withNSString:(NSString *)message {
  PFPush *push = [PFPush new];
  [push setChannel:channelId];
  [push setMessage:message];
  [push sendPushInBackground];
}

+ (NTSModel*)getModel {
  AppDelegate* appDelegate = (AppDelegate*)[UIApplication sharedApplication].delegate;
  return appDelegate.model;
}

@end
