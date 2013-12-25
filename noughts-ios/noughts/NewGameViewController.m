//
//  NewGameViewController.m
//  noughts
//
//  Created by Derek Thurn on 12/24/13.
//  Copyright (c) 2013 Derek Thurn. All rights reserved.
//

#import "NewGameViewController.h"

@interface NewGameViewController ()

@end

@implementation NewGameViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidAppear:(BOOL)animated {
  [[self navigationController] setNavigationBarHidden: NO animated: animated];
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

@end
