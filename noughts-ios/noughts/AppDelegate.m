#import "AppDelegate.h"

@interface AppDelegate ()
@property BOOL runningQuery;
@end

@implementation AppDelegate

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
  _friendPhotos = [NSMutableDictionary new];
  return YES;
}
//
//- (BOOL)application:(UIApplication *)application
//            openURL:(NSURL *)url
//  sourceApplication:(NSString *)sourceApplication
//         annotation:(id)annotation {
//  [FBSession.activeSession setStateChangeHandler:
//   ^(FBSession *session, FBSessionState state, NSError *error) {
//     AppDelegate* appDelegate = (AppDelegate*)[UIApplication sharedApplication].delegate;
//     [appDelegate sessionStateChanged:session state:state error:error];
//   }];
//  return [FBAppCall handleOpenURL:url sourceApplication:sourceApplication];
//}
//
//- (void)sessionStateChanged:(FBSession*)session state:(FBSessionState)state error:(NSError*)error {
//  if (!error && state == FBSessionStateOpen) {
//    [self getFacebookFriends];
//    if (!_friendCache) {
//      _friendCache = [FBFrictionlessRecipientCache new];
//    }
//    [_friendCache prefetchAndCacheForSession:nil];
//  }
//}
//
//- (void)getFacebookFriends {
//  if (_friends || _runningQuery) return;
//  _runningQuery = YES;
//  NSString *query = @"SELECT uid,mutual_friend_count,name,first_name,sex,is_app_user "
//                    @"FROM user WHERE uid IN "
//                    @"( SELECT uid2 FROM friend WHERE uid1=me() )";
//  NSDictionary *queryParam = @{ @"q": query };
//  [FBRequestConnection startWithGraphPath:@"/fql"
//                               parameters:queryParam
//                               HTTPMethod:@"GET"
//                        completionHandler:^(FBRequestConnection *connection,
//                                            id result,
//                                            NSError *error) {
//                          if (!error) {
//                            [self handleFriendsResult:result[@"data"]];
//                          }
//                          _runningQuery = NO;
//                        }];
//}
//
//- (void)handleFriendsResult:(NSArray*)array {
//  NSSortDescriptor *appUser = [[NSSortDescriptor alloc] initWithKey:@"is_app_user"
//                                                          ascending:NO];
//  NSSortDescriptor *mutalFriends = [[NSSortDescriptor alloc] initWithKey:@"mutual_friend_count"
//                                                               ascending:NO];
//  _friends = [array sortedArrayUsingDescriptors:@[appUser, mutalFriends]];
//  dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT,
//                                           (unsigned long)NULL), ^(void) {
//    [self populateFriendPhotos];
//  });
//}
//
//- (void)populateFriendPhotos {
//  for (NSDictionary *friend in _friends) {
//    NSURL *photoUrl = [NSURL URLWithString:
//                       [NSString
//                        stringWithFormat:
//                            @"https://graph.facebook.com/%@/picture?width=100&height=100",
//                        friend[@"uid"]]];
//    NSData *data = [NSData dataWithContentsOfURL:photoUrl];
//    _friendPhotos[friend[@"uid"]] = [[UIImage alloc] initWithData:data];
//  }
//}

- (void)applicationWillResignActive:(UIApplication *)application
{
  // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
  // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
}

- (void)applicationDidEnterBackground:(UIApplication *)application
{
  // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later. 
  // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
}

- (void)applicationWillEnterForeground:(UIApplication *)application
{
  // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
}

- (void)applicationDidBecomeActive:(UIApplication *)application {
//  [FBAppCall handleDidBecomeActive];
}

- (void)applicationWillTerminate:(UIApplication *)application
{
  // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
}

@end
