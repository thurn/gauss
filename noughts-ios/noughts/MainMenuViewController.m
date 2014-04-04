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
  if (FBSession.activeSession.state == FBSessionStateCreatedTokenLoaded) {
    [FBSession openActiveSessionWithReadPermissions:@[@"basic_info"]
                                       allowLoginUI:NO
                                  completionHandler:
     ^(FBSession *session, FBSessionState state, NSError *error) {
       NSLog(@"logged in");
       AppDelegate* appDelegate = (AppDelegate*)[UIApplication sharedApplication].delegate;
       [appDelegate sessionStateChanged:session state:state error:error];
     }];
  }
}

- (IBAction)onFacebookLoginClicked {
  [FBSession openActiveSessionWithReadPermissions:@[@"basic_info"]
                                     allowLoginUI:YES
                                completionHandler:
   ^(FBSession *session, FBSessionState state, NSError *error) {
     AppDelegate* appDelegate = (AppDelegate*)[UIApplication sharedApplication].delegate;
     [appDelegate sessionStateChanged:session state:state error:error];
   }];
}

- (void)viewDidLoad {
  [super viewDidLoad];
}

@end
