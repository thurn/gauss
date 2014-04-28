#import "FacebookInviteViewController.h"
#import "AppDelegate.h"
#import "Model.h"
#import "QueryParsing.h"
#import <SDWebImage/UIImageView+WebCache.h>
#import "java/util/ArrayList.h"
#import "NotificationManager.h"
#import "Identifiers.h"
#import "J2obcUtils.h"
#import "FacebookUtils.h"

@interface FacebookInviteViewController ()
@end

@implementation FacebookInviteViewController

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
  AppDelegate* appDelegate = (AppDelegate*)[UIApplication sharedApplication].delegate;
  NSNumber *uid = appDelegate.friends[indexPath.row][@"uid"];
  [FBWebDialogs
   presentRequestsDialogModallyWithSession:nil
   message:@"Invitation to play noughts"
   title:@"noughts"
   parameters:@{@"to": [uid stringValue]}
   handler: ^(FBWebDialogResult result, NSURL *resultURL, NSError *error){
     if (!error && result != FBWebDialogResultDialogNotCompleted) {
       NSDictionary *query = [QueryParsing dictionaryFromQueryComponents:resultURL];
       NTSProfile *profile = [FacebookUtils
                              profileFromFacebookDictionary:appDelegate.friends[indexPath.row]];
       [self onFacebookInvite:query[@"request"][0] withProfile:profile];
     }
   }
   friendCache:nil /*appDelegate.friendCache*/];
}

- (void)onFacebookInvite:(NSString*)requestId withProfile:(NTSProfile*)profile {
  NTSModel *model = [AppDelegate getModel];
  NTSProfile *userProfile = [[NotificationManager getInstance]
                             getLatestValueForNotification:kFacebookProfileLoadedNotification];
  NSLog(@"profile %@", profile);
  NSString *gameId = [model newGameWithJavaUtilList:
                     [J2obcUtils nsArrayToJavaUtilList:@[userProfile, profile]]];
  [model putFacebookRequestIdWithNSString:requestId withNSString:gameId];
  AppDelegate* appDelegate = (AppDelegate*)[UIApplication sharedApplication].delegate;
  [appDelegate pushGameViewWithId:gameId];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
  return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
  AppDelegate* appDelegate = (AppDelegate*)[UIApplication sharedApplication].delegate;
  return [appDelegate.friends count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView
         cellForRowAtIndexPath:(NSIndexPath *)indexPath {
  AppDelegate* appDelegate = (AppDelegate*)[UIApplication sharedApplication].delegate;
  UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"FacebookCell"
                                                          forIndexPath:indexPath];
  NSDictionary *friend = [appDelegate.friends objectAtIndex:indexPath.row];
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
