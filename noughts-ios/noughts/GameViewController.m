#import "GameViewController.h"
#import "GameView.h"
#import "NewGameViewController.h"
#import "GameListViewController.h"
#import "Model.h"
#import "Firebase.h"
#import "Command.h"
#import "Profile.h"
#import "GameStatus.h"
#import "Games.H"
#import "java/lang/Integer.h"
#import "ImageString.h"
#import "NewLocalGameViewController.h"
#import "ProfilePromptViewController.h"
#import "AppDelegate.h"

@interface GameViewController () <UIAlertViewDelegate, OnModelLoaded>
@property(weak,nonatomic) NTSModel *model;
@property(strong,nonatomic) GameView *gameView;
@property(strong,nonatomic) NTSGame *game;
@property(strong,nonatomic) NTSAction *action;
@end

@implementation GameViewController

- (void)loadView {
  [super loadView];
  _gameView = [GameView new];
  _gameView.delegate = self;
  [_gameView updateButtons];
  [_gameView setGameCanvasDelegate:self];
  self.view = _gameView;
}

- (void)viewWillAppear:(BOOL)animated {
  [AppDelegate registerForOnModelLoaded:self];
}

- (void)onModelLoaded:(NTSModel *)model {
  _model = [AppDelegate getModel];
  [_model setCommandUpdateListenerWithNSString:_currentGameId
                  withNTSCommandUpdateListener:[_gameView getCommandUpdateListener]];
  [_model setGameUpdateListenerWithNSString:_currentGameId withNTSGameUpdateListener:self];
}

- (void)viewDidAppear:(BOOL)animated {
  [[self navigationController] setNavigationBarHidden:YES animated:animated];
  if (_tutorialMode) {
    [_gameView showTapSquareCallout];
  }
}

- (void)displayGameStatus:(NTSGameStatus*)status {
  UIColor *color;
  if (![status hasStatusPlayer]) {
    color = [UIColor grayColor];
  } else {
    color = [self colorFromPlayerNumber:[status getStatusPlayer]];
  }
  UIImage *image;
  image = [UIImage imageNamed:[[status getStatusImageString] getString]];
  [_gameView displayGameStatusWithImage:image
                             withString:[status getStatusString]
                              withColor:color];
  if ([status isComputerThinking]) {
    [_gameView showComputerThinkingIndicator];
  } else {
    [_gameView hideComputerThinkingIndicator];
  }
}

- (UIColor*)colorFromPlayerNumber:(int)playerNumber {
  switch (playerNumber) {
    case 0: {
      return [UIColor blackColor];
    }
    case 1: {
      return [UIColor colorWithRed:203.0/256.0
                             green:68.0/256.0
                              blue:55.0/256.0
                             alpha:1.0];
    }
  }
  return nil;
}

- (void)handleGameMenuSelection:(GameMenuSelection)selection {
  switch (selection) {
    case kResignOrArchive: {
      if ([_game isGameOver]) {
        [_model archiveGameWithNSString:_currentGameId];
        [[self navigationController] setNavigationBarHidden:NO animated:YES];
        [self.navigationController popToRootViewControllerAnimated:YES];
      } else {
        UIAlertView *alert = [[UIAlertView alloc]
                              initWithTitle:@"Confirm"
                                    message:@"Are you sure you want to leave the game?"
                                   delegate:self
                          cancelButtonTitle:@"Cancel"
                          otherButtonTitles:@"Resign", nil];
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
        UIViewController *rootController = self.navigationController.viewControllers[0];
        [self.navigationController setViewControllers:@[rootController, newGameController, self]
                                             animated:NO];
      }
      [self.navigationController popToViewController:newGameController animated:YES];
      break;
    }
    case kGameList: {
      [[self navigationController] setNavigationBarHidden:NO animated:YES];
      GameListViewController *savedGamesController =
          (GameListViewController*)[self findViewController:[GameListViewController class]];
      if (savedGamesController == nil) {
        // Add saved games controller to back stack
        savedGamesController =
            [self.storyboard instantiateViewControllerWithIdentifier:@"SavedGamesViewController"];
        UIViewController *rootController = self.navigationController.viewControllers[0];
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
    [_model resignGameWithNSString:_currentGameId];
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

- (void)invalidateCommandListener {
  [_model invalidateCommandListenerWithNSString:_currentGameId];
}

- (NTSCommand*)commandFromX:(int)x fromY:(int)y {
  return [[[[NTSCommand newBuilder]
            setColumnWithInt:x]
           setRowWithInt:y]
          build];
}

- (void)handleSquareTapAtX:(int)x AtY:(int)y {
  NTSCommand *command = [self commandFromX:x fromY:y];
  if ([_model couldAddCommandWithNTSGame:_game
                           withNTSAction:_action
                           withNTSCommand:command]) {
    if (_tutorialMode) {
      [_gameView hideTapSquareCallout];
      [_gameView showSubmitCallout];
    }
    [_model addCommandWithNSString:_currentGameId withNTSCommand:command];
  }
}

- (void)handleDragToX:(int)x toY:(int)y {
  NTSCommand *command = [self commandFromX:x fromY:y];
  [_model updateLastCommandWithNSString:_currentGameId withNTSCommand:command];
}

- (BOOL)allowDragToX:(int)x toY:(int)y {
  NTSCommand *command = [self commandFromX:x fromY:y];
  return [_model couldUpdateLastCommandWithNTSGame:_game
                                     withNTSAction:_action
                                    withNTSCommand:command];
}

- (void)onGameUpdateWithNTSGame:(NTSGame*)game {
  [_model joinGameIfPossibleWithNSString:_currentGameId];
  [_model handleComputerActionWithNSString:_currentGameId];
  _game = game;
}

- (void)onCurrentActionUpdateWithNTSAction:(NTSAction*)currentAction {
  [_gameView updateButtons];
  _action = currentAction;
}

- (void)onProfileRequiredWithNSString:(NSString*)gameId {
  ProfilePromptViewController *ppvc = [[ProfilePromptViewController alloc] initWithGameId:gameId];
  [self presentViewController:ppvc animated:YES completion:nil];
}

- (void)onGameStatusChangedWithNTSGameStatus:(NTSGameStatus*)status {
  [self displayGameStatus:status];
}

- (BOOL)canSubmit {
  return [_model canSubmitWithNTSGame:_game withNTSAction:_action];
}

- (BOOL)canUndo {
  return [_model canUndoWithNTSGame:_game withNTSAction:_action];
}

- (BOOL)canRedo {
  return [_model canRedoWithNTSGame:_game withNTSAction:_action];
}

- (void)handleSubmit {
  if (_tutorialMode) {
    [_gameView hideSubmitCallout];
    _tutorialMode = NO;
    NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
    [userDefaults setObject:@YES forKey:kSawTutorialKey];
    [userDefaults synchronize];
  }
  [_model submitCurrentActionWithNSString:_currentGameId];
}

- (void)handleUndo {
  [_model undoCommandWithNSString:_currentGameId];
}

- (void)handleRedo {
  [_model redoCommandWithNSString:_currentGameId];
}

- (BOOL)isGameOver {
  return [_game isGameOver];
}

@end