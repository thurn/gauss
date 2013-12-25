#import "ViewController.h"

@interface ViewController ()
@end

@implementation ViewController

- (void)viewDidLoad
{
  [super viewDidLoad];
}

- (IBAction)onIncrement:(id)sender {
}

- (void)viewDidAppear:(BOOL)animated {
  [[self navigationController] setNavigationBarHidden: NO animated: animated];
}

- (void)didReceiveMemoryWarning
{
  [super didReceiveMemoryWarning];
  // Dispose of any resources that can be recreated.
}

@end