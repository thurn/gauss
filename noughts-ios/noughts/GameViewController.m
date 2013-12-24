//
//  GameViewController.m
//  noughts
//
//  Created by Derek Thurn on 12/21/13.
//  Copyright (c) 2013 Derek Thurn. All rights reserved.
//

#import "GameViewController.h"
#import "GameView.h"
#import "Model.h"
#import "Firebase.h"
#import "Command.h"

@interface GameViewController ()
@property (strong,nonatomic) NTSModel *model;
@property (strong,nonatomic) NTSGame *currentGame;
@property (strong,nonatomic) GameView *gameView;
@end

@implementation GameViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
  self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
  if (self) {
  }
  return self;
}

- (void)loadView {
  GameView *view = [GameView new];
  view.delegate = self;
  self.view = view;
  self.gameView = view;
}

- (void)viewDidLoad {
  [super viewDidLoad];
  FCFirebase *firebase = [[FCFirebase alloc]
                          initWithNSString: @"https://noughts.firebaseio-demo.com"];
  self.model = [[NTSModel alloc] initWithNSString: @"derek"
                                   withFCFirebase: firebase];
  NSString *gameId = [self.model newGameWithBoolean: YES
                                    withJavaUtilMap: nil
                                    withJavaUtilMap: nil];
  [self.model setGameUpdateListenerWithNSString: gameId
                withNTSModel_GameUpdateListener: self];

}

- (void)actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex {
  if (buttonIndex == 1) {
    NSLog(@"pop");
    [self dismissViewControllerAnimated:YES completion:^{
      [self.navigationController popToRootViewControllerAnimated:YES];
    }];
  }
}

- (void)handleSquareTapAtX: (int)x AtY: (int)y {
  NTSCommand *command = [[NTSCommand alloc] initWithInt:x withInt:y];
  if ([self.model couldSubmitCommandWithNTSGame: self.currentGame withNTSCommand: command]) {
    [self.model addCommandWithNTSGame: self.currentGame withNTSCommand: command];
  }
}

- (void)onGameUpdateWithNTSGame:(NTSGame *)game {
  self.currentGame = game;
  [self.gameView drawGame: game];
}

- (void)didReceiveMemoryWarning {
  [super didReceiveMemoryWarning];
}

@end
