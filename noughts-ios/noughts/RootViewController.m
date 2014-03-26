#import "RootViewController.h"
#import "Model.h"
#import "Firebase.h"
#import "HasModel.h"
#import "GameViewController.h"
#import "AppDelegate.h"
#import "LoginHelper.h"

@interface RootViewController ()
@property(weak,nonatomic) IBOutlet UIButton *savedGamesButton;
@property(strong,nonatomic) NTSModel *model;
@property(strong,nonatomic) LoginHelper *loginHelper;
@end

@implementation RootViewController

- (void)awakeFromNib {
  UIImage *logo = [UIImage imageNamed:@"logo_title_bar"];
  self.navigationItem.titleView = [[UIImageView alloc] initWithImage:logo];
  
//  if (FBSession.activeSession.state == FBSessionStateCreatedTokenLoaded) {
//    [FBSession openActiveSessionWithReadPermissions:@[@"basic_info"]
//                                       allowLoginUI:NO
//                                  completionHandler:
//     ^(FBSession *session, FBSessionState state, NSError *error) {
//       NSLog(@"logged in");
//       AppDelegate* appDelegate = (AppDelegate*)[UIApplication sharedApplication].delegate;
//       [appDelegate sessionStateChanged:session state:state error:error];
//     }];
//  }
}

- (IBAction)onFacebookLoginClicked {
  [_loginHelper loginViaFacebook:^(NSString *userId) {
    NSLog(@"callback");
  }];
  
//  [FBSession openActiveSessionWithReadPermissions:@[@"basic_info"]
//                                     allowLoginUI:YES
//                                completionHandler:
//   ^(FBSession *session, FBSessionState state, NSError *error) {
//     AppDelegate* appDelegate = (AppDelegate*)[UIApplication sharedApplication].delegate;
//     [appDelegate sessionStateChanged:session state:state error:error];
//   }];
}

- (void)viewDidLoad {
  [super viewDidLoad];
//  NSLog(@"facebook? %d", FBSession.activeSession.state == FBSessionStateOpen);
  FCFirebase *firebase = [[FCFirebase alloc]
                          initWithNSString:@"https://noughts.firebaseio.com"];
  NSString *userKey = [[[UIDevice currentDevice] identifierForVendor] UUIDString];
  NSString *userId = [LoginHelper sha1:userKey];
  _model = [[NTSModel alloc] initWithNSString:userId
                                 withNSString:userKey
                               withFCFirebase:firebase];
//  _loginHelper = [[LoginHelper alloc] initWithFirebase:firebase];  
//  if (FBSession.activeSession.state == FBSessionStateOpen) {
//    [_loginHelper loginViaFacebook:^(NSString *userId) {
//      NSLog(@"view did load - logged in via facebook");
//    }];
//  } else {
//    [_loginHelper loginToFirebase:^(NSString *userId) {
//      NSLog(@"view did load - anon login %@", userId);
//      _model = [[NTSModel alloc] initWithNSString:userId
//                                     withNSString:@"anonymous"
//                                   withFCFirebase:firebase];
//      [_model setGameListListenerWithNTSGameListListener:self];
//    }];
//  }
}

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
  [_loginHelper printAuthStatus];
  [(id <HasModel>)segue.destinationViewController setNTSModel:_model];
}

@end
