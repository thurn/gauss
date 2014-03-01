//
//  GameView.m
//  noughts
//
//  Created by Derek Thurn on 12/22/13.
//  Copyright (c) 2013 Derek Thurn. All rights reserved.
//

#import "GameView.h"
#import "SVGKit.h"
#import "Model.h"
#import "Game.h"
#import "Action.h"
#import "Command.h"
#include "java/lang/Integer.h"

#define TOP_OFFSET 80
#define SQUARE_SIZE 107

@interface GameView ()
@property (strong,nonatomic) UIImage *logo;
@property (strong,nonatomic) UIImage *x;
@property (strong,nonatomic) UIImage *o;
@property (strong,nonatomic) UIImage *back;
@property (strong,nonatomic) NTSGame *currentGame;
@property (strong,nonatomic) UIButton *gameMenuButton;
@property (strong,nonatomic) UIButton *submitButton;
@property (strong,nonatomic) UIButton *undoButton;
@property (strong,nonatomic) UIButton *redoButton;
@property (weak, nonatomic) IBOutlet UIImageView *gameStatusImage;
@property (weak, nonatomic) IBOutlet UIView *gameStatusColorView;
@property (weak, nonatomic) IBOutlet UILabel *gameStatusLabel;
@property (strong, nonatomic) UIView *gameStatusView;
@property (strong, nonatomic) NSLayoutConstraint* gameStatusConstraint;
@property double taskId;
@end

@implementation GameView

- (id)initWithFrame:(CGRect)frame {
  self = [super initWithFrame:frame];
  if (self) {
    // Initialization code
    self.backgroundColor = [UIColor whiteColor];
    SVGKImage *svg = [SVGKImage imageNamed:@"logo.svg"];
    self.logo = svg.UIImage;
    SVGKImage *xsvg = [SVGKImage imageNamed:@"x.svg"];
    xsvg.size = CGSizeMake(SQUARE_SIZE, SQUARE_SIZE);
    self.x = xsvg.UIImage;
    SVGKImage *osvg = [SVGKImage imageNamed:@"o.svg"];
    osvg.size = CGSizeMake(SQUARE_SIZE, SQUARE_SIZE);
    self.o = osvg.UIImage;
    SVGKImage *bgsvg = [SVGKImage imageNamed:@"background.svg"];
    self.back = bgsvg.UIImage;

    [self addGestureRecognizer:
     [[UITapGestureRecognizer alloc] initWithTarget:self
                                             action:@selector(handleTap:)]];

    UIButton *gameMenuButton = [UIButton buttonWithType:UIButtonTypeSystem];
    UIImage *gameMenuIcon = [UIImage imageNamed:@"ic_game_menu.png"];
    [gameMenuButton setImage:gameMenuIcon forState:UIControlStateNormal];
    [gameMenuButton addTarget:self
                       action:@selector(menuButtonClicked:)
             forControlEvents:UIControlEventTouchUpInside];
    [gameMenuButton sizeToFit];
    gameMenuButton.translatesAutoresizingMaskIntoConstraints = NO;
    [self addSubview:gameMenuButton];

    UIButton *submitButton = [UIButton buttonWithType:UIButtonTypeSystem];
    [submitButton setTitle:@"Submit" forState:UIControlStateNormal];
    [submitButton addTarget:self
                     action:@selector(submitClicked:)
           forControlEvents:UIControlEventTouchUpInside];
    [submitButton sizeToFit];
    submitButton.translatesAutoresizingMaskIntoConstraints = NO;
    [self addSubview:submitButton];

    UIButton *undoButton = [UIButton buttonWithType:UIButtonTypeSystem];
    UIImage *undoIcon = [UIImage imageNamed:@"ic_undo.png"];
    [undoButton setImage:undoIcon forState:UIControlStateNormal];
    [undoButton addTarget:self
                     action:@selector(undoClicked:)
           forControlEvents:UIControlEventTouchUpInside];
    [undoButton sizeToFit];
    undoButton.translatesAutoresizingMaskIntoConstraints = NO;
    [self addSubview:undoButton];

    UIButton *redoButton = [UIButton buttonWithType:UIButtonTypeSystem];
    UIImage *redoIcon = [UIImage imageNamed:@"ic_redo.png"];
    [redoButton setImage:redoIcon forState:UIControlStateNormal];
    [redoButton addTarget:self
                     action:@selector(redoClicked:)
           forControlEvents:UIControlEventTouchUpInside];
    [redoButton sizeToFit];
    redoButton.translatesAutoresizingMaskIntoConstraints = NO;
    [self addSubview:redoButton];

    NSArray *subviewArray = [[NSBundle mainBundle]
                             loadNibNamed:@"GameStatusView"
                             owner:self
                             options:nil];
    UIView *gameStatusView = [subviewArray objectAtIndex:0];
    gameStatusView.translatesAutoresizingMaskIntoConstraints = NO;
    UITapGestureRecognizer *singleTap =
        [[UITapGestureRecognizer alloc] initWithTarget:self
                                                action:@selector(handleGameStatusTap:)];
    [gameStatusView addGestureRecognizer:singleTap];
    
    [self addSubview:gameStatusView];

    NSDictionary *views = NSDictionaryOfVariableBindings(gameMenuButton,
                                                         submitButton,
                                                         undoButton,
                                                         redoButton,
                                                         gameStatusView);
    NSNumber *statusBarHeight = [NSNumber numberWithFloat:
                                 [UIApplication sharedApplication].statusBarFrame.size.height];
    NSDictionary *metrics = @{@"statusBarHeight": statusBarHeight};
    [self visualConstraint:@"H:|-5-[gameMenuButton]" views:views metrics:metrics];
    [self visualConstraint:@"V:|-(statusBarHeight)-[gameMenuButton]" views:views metrics:metrics];
    [self visualConstraint:@"H:[undoButton]-8-[redoButton]-10-[submitButton]-5-|"
                     views:views
                   metrics:metrics];
    [self visualConstraint:@"V:|-(statusBarHeight)-[submitButton]" views:views metrics:metrics];
    [self visualConstraint:@"V:[gameStatusView(==75)]" views:views metrics:metrics];
    [self visualConstraint:@"H:|[gameStatusView]|" views:views metrics:metrics];
    self.gameStatusConstraint = [NSLayoutConstraint constraintWithItem:gameStatusView
                                 attribute:NSLayoutAttributeBottom
                                 relatedBy:NSLayoutRelationEqual
                                    toItem:self
                                 attribute:NSLayoutAttributeBottom
                                multiplier:1
                                  constant:75];
    [self addConstraint:self.gameStatusConstraint];
    [self addConstraint:
     [NSLayoutConstraint constraintWithItem:undoButton
                                  attribute:NSLayoutAttributeCenterY
                                  relatedBy:NSLayoutRelationEqual
                                     toItem:submitButton
                                  attribute:NSLayoutAttributeCenterY
                                 multiplier:1
                                   constant:0]];
    [self addConstraint:
     [NSLayoutConstraint constraintWithItem:redoButton
                                  attribute:NSLayoutAttributeCenterY
                                  relatedBy:NSLayoutRelationEqual
                                     toItem:undoButton
                                  attribute:NSLayoutAttributeCenterY
                                 multiplier:1
                                   constant:0]];

    self.gameMenuButton = gameMenuButton;
    self.submitButton = submitButton;
    self.undoButton = undoButton;
    self.redoButton = redoButton;
    self.gameStatusView = gameStatusView;
  }
  return self;
}

-(void)displayGameStatusWithImage:(UIImage*)image
                       withString:(NSString*)string
                        withColor:(UIColor*)color {
  self.gameStatusImage.image = image;
  self.gameStatusLabel.text = string;
  self.gameStatusColorView.backgroundColor = color;
  [self layoutIfNeeded];
  [UIView animateWithDuration:0.3 animations:^{
    self.gameStatusConstraint.constant = 0;
    [self layoutIfNeeded];
  }];
  dispatch_time_t delay = dispatch_time(DISPATCH_TIME_NOW, 3 * NSEC_PER_SEC);
  double taskId = rand();
  self.taskId = taskId;
  // We keep track of the ID of the last scheduled task and only animate
  // the view going back down if we're still the last scheduled task when
  // it's time to run.
  dispatch_after(delay, dispatch_get_main_queue(), ^{
    if (taskId == self.taskId) {
    [UIView animateWithDuration:0.3 animations:^{
      self.gameStatusConstraint.constant = 75;
      [self layoutIfNeeded];
    }];
    }
  });
}

- (void)handleGameStatusTap:(UITapGestureRecognizer *)recognizer {
  // Tap game status to dismiss.
  [UIView animateWithDuration:0.3 animations:^{
    self.gameStatusConstraint.constant = 75;
    [self layoutIfNeeded];
  }];
}

- (void)visualConstraint:(NSString*)visualFormat
                   views:(NSDictionary*)views
                 metrics:(NSDictionary*)metrics{
    [self addConstraints: [NSLayoutConstraint
                           constraintsWithVisualFormat:visualFormat
                           options:0
                           metrics:metrics
                           views:views]];
}

- (void)menuButtonClicked:(UIButton*)button {
  NSString *destructiveTitle = [self.delegate isGameOver] ? @"Archive Game" : @"Resign";
  UIActionSheet *sheet = [[UIActionSheet alloc] initWithTitle:nil
                                                     delegate:self
                                            cancelButtonTitle:@"Cancel"
                                       destructiveButtonTitle:destructiveTitle
                                            otherButtonTitles:@"Main Menu", @"Game List",
                                                              @"New Game", nil];
  [sheet showInView:self];
}

- (void)submitClicked:(UIButton*)button {
  [self.delegate handleSubmit];
}

- (void)undoClicked:(UIButton*)button {
  [self.delegate handleUndo];
}

- (void)redoClicked:(UIButton*)button {
  [self.delegate handleRedo];
}

- (void)actionSheet:(UIActionSheet*)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex {
  GameMenuSelection selection = kUnknownSelection;
  switch (buttonIndex) {
    case 0: {
      selection = kResignOrArchive;
      break;
    }
    case 1: {
      selection = kMainMenu;
      break;
    }
    case 2: {
      selection = kGameList;
      break;
    }
    case 3: {
      selection = kNewGameMenu;
      break;
    }
  }
  if (selection != kUnknownSelection) {
    [self.delegate handleGameMenuSelection: selection];
  }
}

- (void)drawGame: (NTSGame*)game {
  self.currentGame = game;
  [self.submitButton setEnabled:[self.delegate canSubmit]];
  [self.undoButton setEnabled:[self.delegate canUndo]];
  [self.redoButton setEnabled:[self.delegate canRedo]];
  [self setNeedsDisplay];
}

- (void)handleTap:(UITapGestureRecognizer*)sender {
  if (sender.state == UIGestureRecognizerStateEnded) {
    CGPoint point = [sender locationInView: self];
    if (point.y < TOP_OFFSET || point.y > TOP_OFFSET + 3 * SQUARE_SIZE ||
        point.x < 0 || point.x > 3 * SQUARE_SIZE) {
      return; // Out of bounds
    }
    [self.delegate handleSquareTapAtX:point.x / SQUARE_SIZE
                                  AtY:(point.y - TOP_OFFSET) / SQUARE_SIZE];
  }
}

-(void)drawAction:(NTSAction*)action {
  for (NTSCommand* command in [action getCommandList]) {
    CGPoint point = CGPointMake([command getColumn] * SQUARE_SIZE,
                                [command getRow] * SQUARE_SIZE + TOP_OFFSET);
    if ([action getPlayerNumber] == [NTSModel X_PLAYER]) {
      [self.x drawAtPoint:point];
    } else {
      [self.o drawAtPoint:point];
    }
  }
}

- (void)drawRect:(CGRect)rect {
  [self.back drawAtPoint:CGPointZero];
  if (self.currentGame) {
    for (NTSAction *action in [self.currentGame getSubmittedActionList]) {
      [self drawAction:action];
    }
  }
  if ([self.currentGame hasCurrentAction]) {
    [self drawAction:[self.currentGame getCurrentAction]];
  }
}

@end
