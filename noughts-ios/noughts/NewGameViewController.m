//
//  NewGameViewController.m
//  noughts
//
//  Created by Derek Thurn on 12/24/13.
//  Copyright (c) 2013 Derek Thurn. All rights reserved.
//

#import "NewGameViewController.h"
#import "HasModel.h"

@interface NewGameViewController ()
@property(weak,nonatomic) NTSModel* model;
@end

@implementation NewGameViewController

- (void)setNTSModel:(NTSModel *)model {
  self.model = model;
}

- (void)viewDidLoad
{
  [super viewDidLoad];
}

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
  [(id <HasModel>)segue.destinationViewController setNTSModel:self.model];
}

- (void)didReceiveMemoryWarning
{
  [super didReceiveMemoryWarning];
}

@end
