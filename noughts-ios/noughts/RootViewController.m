#import "RootViewController.h"
#import "Model.h"
#import "Firebase.h"
#import "HasModel.h"

@interface RootViewController ()
@property (weak, nonatomic) IBOutlet UIButton *savedGamesButton;
@property(strong,nonatomic) NTSModel *model;
@end

@implementation RootViewController

- (void)viewDidLoad
{
  [super viewDidLoad];
  FCFirebase *firebase = [[FCFirebase alloc]
                          initWithNSString:@"https://noughts.firebaseio-demo.com"];
  self.model = [[NTSModel alloc] initWithNSString:@"userid" withFCFirebase:firebase];
}

- (void)viewDidAppear:(BOOL)animated {
  [[self navigationController] setNavigationBarHidden: NO animated: animated];
  if ([self.model gameCount] == 0) {
    [self.savedGamesButton setEnabled:NO];
  } else {
    [self.savedGamesButton setEnabled:YES];
  }
  NSLog(@"Num Games %d", [self.model gameCount]);
}

- (void)didReceiveMemoryWarning
{
  [super didReceiveMemoryWarning];
  // Dispose of any resources that can be recreated.
}

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
  [(id <HasModel>)segue.destinationViewController setNTSModel:self.model];
}

@end