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
#import "SavedGamesViewController.h"
#import "Model.h"
#import "Firebase.h"
#import "Command.h"
#import "Toast+UIView.h"

@interface GameViewController () <UIAlertViewDelegate>
@property (weak,nonatomic) NTSModel *model;
@property (weak,nonatomic) GameView *gameView;
@property (strong,nonatomic) NTSGame *currentGame;
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

- (void)setNTSModel:(NTSModel *)model {
  self.model = model;
}

- (void)viewDidLoad {
  [super viewDidLoad];
  [self.model setGameUpdateListenerWithNSString: self.currentGameId
                withNTSModel_GameUpdateListener: self];
}

- (void)createLocalMultiplayerGame {
  NSString *gameId = [self.model newGameWithBoolean: YES
                                    withJavaUtilMap: nil
                                    withJavaUtilMap: nil];
  self.currentGameId = gameId;
}

- (void)viewDidAppear:(BOOL)animated {
  [[self navigationController] setNavigationBarHidden: YES animated: animated];
}

- (void)handleGameMenuSelection:(GameMenuSelection)selection {
  switch (selection) {
    case kResignOrArchive: {
      if ([self.currentGame isGameOver]) {
        [self.model archiveGameWithNTSGame:self.currentGame];
        [[self navigationController] setNavigationBarHidden:NO animated:YES];
        [self.navigationController popToRootViewControllerAnimated:YES];
        UIViewController *rootController =
        [self.navigationController.viewControllers objectAtIndex:0];
        [rootController.view makeToast:@"Archived game."];
      } else {
        UIAlertView *alert = [[UIAlertView alloc]
                              initWithTitle:@"Confirm"
                                    message:@"Are you sure you want to leave the game?"
                                   delegate:self
                          cancelButtonTitle:@"Cancel"
                          otherButtonTitles: @"Resign", nil];
        [alert show];
      }
      break;
    }
    case kMainMenu: {
      [[self navigationController] setNavigationBarHidden:NO animated:YES];
      [self.navigationController popToRootViewControllerAnimated:YES];
      break;
    }
    case kNewGameMenu: {
      [[self navigationController] setNavigationBarHidden:NO animated:YES];
      NewGameViewController *newGameController =
          (NewGameViewController*)[self findViewController:[NewGameViewController class]];
      if (newGameController == nil) {
        // Add new game controller to back stack
        newGameController =
            [self.storyboard instantiateViewControllerWithIdentifier:@"NewGameViewController"];
        [newGameController setNTSModel:self.model];
        UIViewController *rootController =
            [self.navigationController.viewControllers objectAtIndex:0];
        [self.navigationController setViewControllers:@[rootController, newGameController, self]
                                             animated:NO];
      }
      [self.navigationController popToViewController:newGameController animated:YES];
      break;
    }
    case kGameList: {
      [[self navigationController] setNavigationBarHidden:NO animated:YES];
      SavedGamesViewController *savedGamesController =
          (SavedGamesViewController*)[self findViewController:[SavedGamesViewController class]];
      if (savedGamesController == nil) {
        // Add saved games controller to back stack
        savedGamesController =
            [self.storyboard instantiateViewControllerWithIdentifier:@"SavedGamesViewController"];
        [savedGamesController setNTSModel:self.model];
        UIViewController *rootController =
            [self.navigationController.viewControllers objectAtIndex:0];
        [self.navigationController setViewControllers:@[rootController, savedGamesController, self]
                                             animated:NO];
      }
      [self.navigationController popToViewController:savedGamesController animated:YES];
      break;
    }
    default: {
      // no action
    }
  }
}

- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex {
  if (buttonIndex == 1) {
    [self.model resignGameWithNTSGame:self.currentGame];
    [self.view makeToast:@"Resigned from game."];
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
  [self.model submitCurrentActionWithNTSGame:self.currentGame];
}

- (void)handleUndo {
  [self.model undoCommandWithNTSGame:self.currentGame];
}

- (void)handleRedo {
  [self.model redoCommandWithNTSGame:self.currentGame];
}

- (BOOL)isGameOver {
  return [self.currentGame isGameOver];
}

@end