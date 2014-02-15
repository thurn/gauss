//
//  NewLocalGameViewController.m
//  noughts
//
//  Created by Derek Thurn on 2/14/14.
//  Copyright (c) 2014 Derek Thurn. All rights reserved.
//

#import "NewLocalGameViewController.h"
#import "GameViewController.h"

@interface NewLocalGameViewController ()
@property(weak,nonatomic) NTSModel* model;
@end

@implementation NewLocalGameViewController

-(void)setNTSModel:(NTSModel *)model {
  self.model = model;
}

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
  if (((UIView*)sender).tag == 100) {
    GameViewController* destination = segue.destinationViewController;
    [destination setNTSModel:self.model];
    [destination createLocalMultiplayerGame];
  }
}

@end
