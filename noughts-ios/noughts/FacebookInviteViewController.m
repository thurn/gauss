#import "FacebookInviteViewController.h"
#import "AppDelegate.h"
#import "Model.h"
#import "QueryParsing.h"
#import <SDWebImage/UIImageView+WebCache.h>

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
     if (error) {
       NSLog(@"Error with request: %@", error);
     } else if (result == FBWebDialogResultDialogNotCompleted) {
       NSLog(@"Dialog closed");
     } else {
       NSLog(@"result url %@", resultURL);
       NSDictionary *query = [QueryParsing dictionaryFromQueryComponents:resultURL];
       NSLog(@"fbid %@", query[@"to[0]"]);
       NSLog(@"requestid %@", query[@"request"]);
     }
   }
   friendCache:appDelegate.friendCache];
}

- (void)didReceiveMemoryWarning
{
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
                 placeholderImage:[UIImage imageNamed:@"facebook_default"]];
  return cell;
}


/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
