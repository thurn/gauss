#import "FacebookInviteViewController.h"
#import "AppDelegate.h"
#import "Model.h"
#import "QueryParsing.h"
#import <SDWebImage/UIImageView+WebCache.h>
#import "java/util/ArrayList.h"
#import "NotificationManager.h"
#import "Identifiers.h"
#import "JavaUtils.h"
#import "FacebookUtils.h"
#import <FacebookSDK/FacebookSDK.h>
#import "PushNotificationHandler.h"
#import "MBProgressHUD.h"

@interface FacebookInviteViewController ()
@property(strong,nonatomic) NSArray *friends;
@property(strong,nonatomic) FBFrictionlessRecipientCache *friendCache;
@property(strong,nonatomic) PushNotificationHandler* pushHandler;
@end

@implementation FacebookInviteViewController

- (void)viewDidLoad {
  [[NotificationManager getInstance] loadValueForNotification:kFacebookFriendsLoadedNotification
                                                    withBlock:
   ^(NSArray *friends) {
     _friends = friends;
     [self.tableView reloadData];
   }];
  [[NotificationManager getInstance] loadValueForNotification:kRecipientCacheLoadedNotification
                                                    withBlock:
   ^(FBFrictionlessRecipientCache *friendCache) {
     _friendCache = friendCache;
   }];
  _pushHandler = [PushNotificationHandler new];  
}

- (void)viewWillAppear:(BOOL)animated {
  [_pushHandler registerHandler];  
}

- (void)viewWillDisappear:(BOOL)animated {
  [_pushHandler unregisterHandler];
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
  [MBProgressHUD showHUDAddedTo:self.view animated:YES];
  NSNumber *uid = _friends[indexPath.row][@"uid"];
  [FBWebDialogs
   presentRequestsDialogModallyWithSession:nil
   message:@"Invitation to play noughts"
   title:@"noughts"
   parameters:@{@"to": [uid stringValue]}
   handler: ^(FBWebDialogResult result, NSURL *resultURL, NSError *error){
     if (!error && result != FBWebDialogResultDialogNotCompleted) {
       NSDictionary *query = [QueryParsing dictionaryFromQueryComponents:resultURL];
       NTSProfile *profile = [FacebookUtils profileFromFacebookDictionary:_friends[indexPath.row]];
       [self onFacebookInvite:query[@"request"][0] withProfile:profile];
     }
   }
   friendCache:_friendCache];
}

- (void)onFacebookInvite:(NSString*)requestId withProfile:(NTSProfile*)profile {
  NTSModel *model = [AppDelegate getModel];
  [[NotificationManager getInstance] loadValueForNotification:kFacebookProfileLoadedNotification
                                                    withBlock:
   ^(id userProfile) {
     NSString *gameId = [model newGameWithJavaUtilList:
                         [JavaUtils nsArrayToJavaUtilList:@[userProfile, profile]]];
     [model putFacebookRequestIdWithNSString:requestId withNSString:gameId];
     [MBProgressHUD hideHUDForView:self.view animated:YES];
     [[NSNotificationCenter defaultCenter] postNotification:
      [NSNotification notificationWithName:kGameRequestedNotification
                                    object:gameId]];
   }];
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
  return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
  return [_friends count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView
         cellForRowAtIndexPath:(NSIndexPath *)indexPath {
  UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"FacebookCell"
                                                          forIndexPath:indexPath];
  NSDictionary *friend = [_friends objectAtIndex:indexPath.row];
  cell.textLabel.text = friend[@"name"];
  NSString *format = @"https://graph.facebook.com/%@/picture?width=100&height=100";
  NSURL *photoUrl = [NSURL URLWithString:
                     [NSString
                      stringWithFormat:format, friend[@"uid"]]];
  [cell.imageView setImageWithURL:photoUrl
                 placeholderImage:[UIImage imageNamed:@"profile_placeholder_medium"]];
  return cell;
}

@end
