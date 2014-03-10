#import "NewGameViewController.h"
#import "NewLocalGameViewController.h"
#import "HasModel.h"

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
    default: {
      @throw @"Unknown sender tag";
    }
  }
}

- (void)didReceiveMemoryWarning {
  [super didReceiveMemoryWarning];
}

@end
