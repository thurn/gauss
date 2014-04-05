#import "MainMenuViewController.h"
#import "AppDelegate.h"
#import <FacebookSDK/FacebookSDK.h>

@interface MainMenuViewController ()
@property(weak,nonatomic) IBOutlet UIButton *savedGamesButton;
@end

@implementation MainMenuViewController

- (void)awakeFromNib {
  UIImage *logo = [UIImage imageNamed:@"logo_title_bar"];
  self.navigationItem.titleView = [[UIImageView alloc] initWithImage:logo];
}

- (void)viewWillAppear:(BOOL)animated {
  NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
  if ([userDefaults valueForKey:kLoggedInToFacebook]) {
    [self removeLoginLink: NO];
  }
}

- (void)removeLoginLink:(BOOL)animated {
  if (animated) {
    [UIView animateWithDuration:0.3 animations:^{
      [self.view viewWithTag:5].alpha = 0.0;
      [self.view viewWithTag:6].alpha = 0.0;
    } completion:^(BOOL finished) {
      [[self.view viewWithTag:5] removeFromSuperview];
      [[self.view viewWithTag:6] removeFromSuperview];
    }];
  } else {
    [[self.view viewWithTag:5] removeFromSuperview];
    [[self.view viewWithTag:6] removeFromSuperview];
  }
}

- (IBAction)onFacebookLoginClicked {
//  [FBSession openActiveSessionWithReadPermissions:@[@"basic_info"]
//                                     allowLoginUI:YES
//                                completionHandler:
//   ^(FBSession *session, FBSessionState state, NSError *error) {
//     AppDelegate* appDelegate = (AppDelegate*)[UIApplication sharedApplication].delegate;
//     [appDelegate sessionStateChanged:session state:state error:error];
//   }];
  AppDelegate* appDelegate = (AppDelegate*)[UIApplication sharedApplication].delegate;
  [appDelegate logInToFacebook:^{
    [self removeLoginLink: YES];
  }];
}

- (void)viewDidLoad {
  [super viewDidLoad];
}

@end
