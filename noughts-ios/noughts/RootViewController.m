#import "RootViewController.h"
#import "Model.h"
#import "Firebase.h"
#import "GameListListener.h"
#import "HasModel.h"
#import "GameViewController.h"

@interface RootViewController () <NTSGameListListener>
@property (weak, nonatomic) IBOutlet UIButton *savedGamesButton;
@property(strong,nonatomic) NTSModel *model;
@end

@implementation RootViewController

-(void)awakeFromNib {
  UIImage *logo = [UIImage imageNamed:@"logo_title_bar"];
  self.navigationItem.titleView = [[UIImageView alloc] initWithImage:logo];
}

- (void)viewDidLoad
{
  [super viewDidLoad];
  FCFirebase *firebase = [[FCFirebase alloc]
                          initWithNSString:@"https://noughts.firebaseio-demo.com"];
  self.model = [[NTSModel alloc] initWithNSString:@"dthurn" withFCFirebase:firebase];
  [self.model setGameListListenerWithNTSGameListListener:self];
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
  if (((UIView*)sender).tag == 102) {
    NSLog(@"tutorail");
    GameViewController *gvc = segue.destinationViewController;
    gvc.tutorialMode = YES;
  }
}

@end
