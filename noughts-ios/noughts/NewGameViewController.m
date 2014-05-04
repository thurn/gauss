#import "NewGameViewController.h"
#import "NewLocalGameViewController.h"
#import <FacebookSDK/FacebookSDK.h>
#import "QueryParsing.h"
#import "AppDelegate.h"
#import "EmailInviteViewController.h"
#import "FacebookUtils.h"
#import "InterfaceUtils.h"
#import "PushNotificationHandler.h"

@interface NewGameViewController ()
@property(strong,nonatomic) PushNotificationHandler* pushHandler;
@end

@implementation NewGameViewController

- (void)viewDidLoad {
  [super viewDidLoad];
  _pushHandler = [PushNotificationHandler new];  
}

- (void)viewWillAppear:(BOOL)animated {
  [_pushHandler registerHandler];  
}

- (void)viewWillDisappear:(BOOL)animated {
  [_pushHandler unregisterHandler];
}

- (IBAction)onInviteViaFacebook:(id)sender {
  if ([FacebookUtils isFacebookUser]) {
    [self.navigationController
     pushViewController:[[InterfaceUtils mainStoryboard]
                         instantiateViewControllerWithIdentifier:@"FacebookInviteViewController"]
     animated:YES];
  } else {
    [FacebookUtils logInToFacebook:self.view withCallback:^{
      [self.navigationController
       pushViewController:[[InterfaceUtils mainStoryboard]
                           instantiateViewControllerWithIdentifier:@"FacebookInviteViewController"]
       animated:YES];
    }];
  }
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
