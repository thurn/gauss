#import "NewGameViewController.h"
#import "NewLocalGameViewController.h"
#import "HasModel.h"
#import <FacebookSDK/FacebookSDK.h>
#import "QueryParsing.h"
#import "AppDelegate.h"

@interface NewGameViewController ()
@property(weak,nonatomic) NTSModel* model;
@end

@implementation NewGameViewController

- (void)setNTSModel:(NTSModel *)model {
  _model = model;
}

- (void)viewDidLoad {
  [super viewDidLoad];
}

- (IBAction)onFacebookInviteClicked {
  FBFriendPickerViewController *picker = [FBFriendPickerViewController new];
  [picker loadData];
  picker.allowsMultipleSelection = NO;
  [self presentViewController:picker animated:YES completion:nil];
  return;
  AppDelegate* appDelegate = (AppDelegate*)[UIApplication sharedApplication].delegate;

  [FBWebDialogs
   presentRequestsDialogModallyWithSession:nil
   message:@"Invitation to play noughts"
   title:@"noughts"
   parameters:@{@"to": @"122610483"}
   handler: ^(FBWebDialogResult result, NSURL *resultURL, NSError *error){
     if (result != FBWebDialogResultDialogNotCompleted) {
       NSLog(@"result url %@", resultURL);
       NSDictionary *query = [QueryParsing dictionaryFromQueryComponents:resultURL];
       NSLog(@"fbid %@", query[@"to[0]"]);
       NSLog(@"requestid %@", query[@"request"]);
     }
   }
   friendCache:appDelegate.friendCache];
}

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
  UIView *sendingView = sender;
  [(id <HasModel>)segue.destinationViewController setNTSModel:_model];
  switch (sendingView.tag) {
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
