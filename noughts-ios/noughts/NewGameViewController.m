//
//  NewGameViewController.m
//  noughts
//
//  Created by Derek Thurn on 12/24/13.
//  Copyright (c) 2013 Derek Thurn. All rights reserved.
//

#import "NewGameViewController.h"
#import "GameViewController.h"

@interface NewGameViewController ()
@property(weak,nonatomic) NTSModel* model;
@end

@implementation NewGameViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
  self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
  if (self) {
  }
  return self;
}

- (void)viewDidAppear:(BOOL)animated {
  [[self navigationController] setNavigationBarHidden: NO animated: animated];
}

- (void)setNTSModel:(NTSModel *)model {
  self.model = model;
}

- (void)viewDidLoad
{
  [super viewDidLoad];
}

- (void)didReceiveMemoryWarning
{
  [super didReceiveMemoryWarning];
  // Dispose of any resources that can be recreated.
}

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
  if (((UIView*)sender).tag == 100) {
    GameViewController* destination = segue.destinationViewController;
    [destination setNTSModel:self.model];
    [destination createLocalMultiplayerGame];
  }
}

@end
