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
@end

@implementation GameView

- (id)initWithFrame:(CGRect)frame {
  self = [super initWithFrame:frame];
  NSLog(@"init with frame");
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


    NSDictionary *views = NSDictionaryOfVariableBindings(gameMenuButton,
                                                         submitButton,
                                                         undoButton,
                                                         redoButton);
    NSNumber *statusBarHeight = [NSNumber numberWithFloat:
                                 [UIApplication sharedApplication].statusBarFrame.size.height];
    NSDictionary *metrics = @{@"statusBarHeight": statusBarHeight};
    [self visualConstraint:@"H:|-5-[gameMenuButton]" views:views metrics:metrics];
    [self visualConstraint:@"V:|-(statusBarHeight)-[gameMenuButton]" views:views metrics:metrics];
    [self visualConstraint:@"H:[undoButton]-8-[redoButton]-10-[submitButton]-5-|"
                     views:views
                   metrics:metrics];
    [self visualConstraint:@"V:|-(statusBarHeight)-[submitButton]" views:views metrics:metrics];
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
  }
  return self;
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
  UIActionSheet *sheet = [[UIActionSheet alloc] initWithTitle:nil
                                                     delegate:self
                                            cancelButtonTitle:@"Cancel"
                                       destructiveButtonTitle:@"Resign"
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
      selection = kResignGame;
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

- (void)drawRect:(CGRect)rect {
  NSLog(@"draw rect");
  [self.back drawAtPoint:CGPointZero];
  if (self.currentGame) {
    for (NTSAction *action in [self.currentGame getActions]) {
      for (NTSCommand* command in [action getCommands]) {
        CGPoint point = CGPointMake([command getColumn] * SQUARE_SIZE,
                                    [command getRow] * SQUARE_SIZE + TOP_OFFSET);
        if ([[action getPlayerNumber] intValue] == [NTSModel X_PLAYER]) {
          [self.x drawAtPoint:point];
        } else {
          [self.o drawAtPoint:point];
        }
      }
    }
  }
}

@end
