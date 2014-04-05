#import "NewGameViewController.h"
#import "NewLocalGameViewController.h"
#import <FacebookSDK/FacebookSDK.h>
#import "QueryParsing.h"
#import "AppDelegate.h"
#import "EmailInviteViewController.h"

@interface NewGameViewController ()
@end

@implementation NewGameViewController

- (void)viewDidLoad {
  [super viewDidLoad];
}

- (IBAction)onInviteViaFacebook:(id)sender {
  AppDelegate* appDelegate = (AppDelegate*)[UIApplication sharedApplication].delegate;
  NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
  if ([userDefaults valueForKey:kLoggedInToFacebook]) {
    [self.navigationController
     pushViewController:[[appDelegate mainStoryboard]
                         instantiateViewControllerWithIdentifier:@"FacebookInviteViewController"]
     animated:YES];
  } else {
    [appDelegate logInToFacebook:^{
      [self.navigationController
       pushViewController:[[appDelegate mainStoryboard]
                           instantiateViewControllerWithIdentifier:@"FacebookInviteViewController"]
       animated:YES];
    }];
  }
}

- (void)onFacebookInviteClicked {
//  FBFriendPickerViewController *picker = [FBFriendPickerViewController new];
//  [picker loadData];
//  picker.allowsMultipleSelection = NO;
//  [self presentViewController:picker animated:YES completion:nil];
//  return;
//  AppDelegate* appDelegate = (AppDelegate*)[UIApplication sharedApplication].delegate;
//
//  [FBWebDialogs
//   presentRequestsDialogModallyWithSession:nil
//   message:@"Invitation to play noughts"
//   title:@"noughts"
//   parameters:@{@"to": @"122610483"}
//   handler: ^(FBWebDialogResult result, NSURL *resultURL, NSError *error){
//     if (result != FBWebDialogResultDialogNotCompleted) {
//       NSLog(@"result url %@", resultURL);
//       NSDictionary *query = [QueryParsing dictionaryFromQueryComponents:resultURL];
//       NSLog(@"fbid %@", query[@"to[0]"]);
//       NSLog(@"requestid %@", query[@"request"]);
//     }
//   }
//   friendCache:appDelegate.friendCache];
}

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
  UIView *sendingView = sender;
  switch (sendingView.tag) {
    case 99: {
      EmailInviteViewController *controller =
          (EmailInviteViewController*)segue.destinationViewController;
      NTSModel *model = [AppDelegate getModel];
      controller.preliminaryGameId = [model getPreliminaryGameId];
      break;
    }
    case 100: {
      NewLocalGameViewController *controller =
          (NewLocalGameViewController*)segue.destinationViewController;
      controller.playVsComputerMode = NO;
      break;
    }
    case 101: {
      NewLocalGameViewController *controller =
          (NewLocalGameViewController*)segue.destinationViewController;
      controller.playVsComputerMode = YES;
      break;
    }
  }
}

- (void)didReceiveMemoryWarning {
  [super didReceiveMemoryWarning];
}

@end
