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
#import "Toast+UIView.h"
#import "java/lang/Integer.h"
#import "ImageString.h"
#import "NewLocalGameViewController.h"

@interface GameViewController () <UIAlertViewDelegate>
@property(weak,nonatomic) NTSModel *model;
@property(weak,nonatomic) GameView *gameView;
@property(strong,nonatomic) NTSGame *currentGame;
@end

@implementation GameViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
  self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
  if (self) {
  }
  return self;
}

- (void)loadView {
  [super loadView];
  GameView *view = [GameView new];
  view.delegate = self;
  [view setGameCanvasDelegate:self];
  self.view = view;
  _gameView = view;
}

- (void)setNTSModel:(NTSModel *)model {
  _model = model;
}

- (void)viewWillAppear:(BOOL)animated {
  [super viewWillAppear:animated];
  [_model setGameUpdateListenerWithNSString:_currentGameId
                      withNTSGameUpdateListener:self];
  [_model setCommandUpdateListenerWithNSString:_currentGameId
                  withNTSCommandUpdateListener:[_gameView getCommandUpdateListener]];
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
      if ([_currentGame isGameOver]) {
        [_model archiveGameWithNTSGame:_currentGame];
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
        [newGameController setNTSModel:_model];
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
      GameListViewController *savedGamesController =
          (GameListViewController*)[self findViewController:[GameListViewController class]];
      if (savedGamesController == nil) {
        // Add saved games controller to back stack
        savedGamesController =
            [self.storyboard instantiateViewControllerWithIdentifier:@"SavedGamesViewController"];
        [savedGamesController setNTSModel:_model];
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
    [_model resignGameWithNTSGame:_currentGame];
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

- (NTSCommand*)commandFromX:(int)x fromY:(int)y {
  return [[[[NTSCommand newBuilder]
            setColumnWithInt:x]
           setRowWithInt:y]
          build];
}

- (void)handleSquareTapAtX:(int)x AtY:(int)y {
  NTSCommand *command = [self commandFromX:x fromY:y];
  if ([_model couldSubmitCommandWithNTSGame:_currentGame withNTSCommand:command]) {
    if (_tutorialMode) {
      [_gameView hideTapSquareCallout];
      [_gameView showSubmitCallout];
    }
    [_model addCommandWithNTSGame:_currentGame withNTSCommand:command];
  }
}

- (void)handleDragToX:(int)x toY:(int)y {
  NTSCommand *command = [self commandFromX:x fromY:y];
  [_model updateLastCommandWithNTSGame:_currentGame withNTSCommand:command];
}

- (BOOL)allowDragToX:(int)x toY:(int)y {
  NTSCommand *command = [self commandFromX:x fromY:y];
  return [_model couldUpdateLastCommandWithNTSGame:_currentGame withNTSCommand:command];
}

- (void)onGameUpdateWithNTSGame:(NTSGame*)game {
  _currentGame = game;
  [_model handleComputerActionWithNTSGame:_currentGame];
  [_gameView drawGame:game];
}

- (void)onGameStatusChangedWithNTSGameStatus:(NTSGameStatus*)status {
  [self displayGameStatus:status];
}

- (BOOL)canSubmit {
  return [_model canSubmitWithNTSGame:_currentGame];
}

- (BOOL)canUndo {
  return [_model canUndoWithNTSGame:_currentGame];
}

- (BOOL)canRedo {
  return [_model canRedoWithNTSGame:_currentGame];
}

- (void)handleSubmit {
  if (_tutorialMode) {
    [_gameView hideSubmitCallout];
    _tutorialMode = NO;
    NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
    [userDefaults setObject:@YES forKey:kSawTutorialKey];
  }
  [_model submitCurrentActionWithNTSGame:_currentGame];
}

- (void)handleUndo {
  [_model undoCommandWithNTSGame:_currentGame];
}

- (void)handleRedo {
  [_model redoCommandWithNTSGame:_currentGame];
}

- (BOOL)isGameOver {
  return [_currentGame isGameOver];
}

@end