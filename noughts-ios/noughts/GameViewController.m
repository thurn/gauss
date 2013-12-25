//
//  GameViewController.m
//  noughts
//
//  Created by Derek Thurn on 12/21/13.
//  Copyright (c) 2013 Derek Thurn. All rights reserved.
//

#import "GameViewController.h"
#import "GameView.h"
#import "NewGameViewController.h"
#import "Model.h"
#import "Firebase.h"
#import "Command.h"

@interface GameViewController ()
@property (strong,nonatomic) NTSModel *model;
@property (strong,nonatomic) NTSGame *currentGame;
@property (weak,nonatomic) GameView *gameView;
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

- (void)viewDidAppear:(BOOL)animated {
  [[self navigationController] setNavigationBarHidden: YES animated: animated];
}

- (void)actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex {
  if (buttonIndex == 1) {

  }
}

- (void)handleGameMenuSelection:(GameMenuSelection)selection {
  switch (selection) {
    case kMainMenu: {
      [self.navigationController popToRootViewControllerAnimated:YES];
      break;
    }
    case kNewGameMenu: {
      UIViewController *newGameController = [self findViewController:[NewGameViewController class]];
      [self.navigationController popToViewController:newGameController animated:YES];
      break;
    }
    default: {
      // no action
    }
  }
}

- (UIViewController*)findViewController:(Class)theClass{
  for (UIViewController *viewController in self.navigationController.viewControllers) {
    if ([viewController isMemberOfClass:theClass]) {
      return viewController;
    }
  }
  return nil;
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

- (BOOL)canSubmit {
  return [self.model canSubmitWithNTSGame:self.currentGame];
}

- (BOOL)canUndo {
  return [self.model canUndoWithNTSGame:self.currentGame];
}

- (BOOL)canRedo {
  return [self.model canRedoWithNTSGame:self.currentGame];
}

- (void)handleSubmit {
  NSLog(@"hs");
  [self.model submitCurrentActionWithNTSGame:self.currentGame];
}

- (void)handleUndo {
  NSLog(@"hu");
  [self.model undoCommandWithNTSGame:self.currentGame];
}

- (void)handleRedo {
  [self.model redoCommandWithNTSGame:self.currentGame];
}

@end
