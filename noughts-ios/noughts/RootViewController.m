#import "RootViewController.h"
#import "Model.h"
#import "Firebase.h"
#import "HasModel.h"

@interface RootViewController () <NTSModel_GameListListener>
@property (weak, nonatomic) IBOutlet UIButton *savedGamesButton;
@property(strong,nonatomic) NTSModel *model;
@end

@implementation RootViewController

- (void)viewDidLoad
{
  [super viewDidLoad];
  FCFirebase *firebase = [[FCFirebase alloc]
                          initWithNSString:@"https://noughts.firebaseio-demo.com"];
  self.model = [[NTSModel alloc] initWithNSString:@"dthurn" withFCFirebase:firebase];
  [self.model setGameListListenerWithNTSModel_GameListListener:self];
}

- (void)viewDidAppear:(BOOL)animated {
  [self toggleSaveButtonEnabled];
}

- (void)toggleSaveButtonEnabled {
  if ([self.model gameCount] == 0) {
    [self.savedGamesButton setEnabled:NO];
  } else {
    [self.savedGamesButton setEnabled:YES];
  }
}

- (void)onGameAddedWithNTSGame:(NTSGame *)game {
  [self toggleSaveButtonEnabled];
}

- (void)onGameChangedWithNTSGame:(NTSGame *)game {
  [self toggleSaveButtonEnabled];
}

- (void)onGameRemovedWithNTSGame:(NTSGame *)game {
  [self toggleSaveButtonEnabled];
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
