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
@property (strong,nonatomic) UIImage* logo;
@property (strong,nonatomic) UIImage* x;
@property (strong,nonatomic) UIImage* o;
@property (strong,nonatomic) UIImage* back;
@property (strong,nonatomic) NTSGame* currentGame;
@end

@implementation GameView

- (id)initWithFrame:(CGRect)frame
{
  self = [super initWithFrame:frame];
  NSLog(@"init with frame");
  if (self) {
    // Initialization code
    self.backgroundColor = [UIColor whiteColor];
    SVGKImage *svg = [SVGKImage imageNamed: @"logo.svg"];
    self.logo = svg.UIImage;
    SVGKImage *xsvg = [SVGKImage imageNamed: @"x.svg"];
    xsvg.size = CGSizeMake(SQUARE_SIZE, SQUARE_SIZE);
    self.x = xsvg.UIImage;
    SVGKImage *osvg = [SVGKImage imageNamed: @"o.svg"];
    osvg.size = CGSizeMake(SQUARE_SIZE, SQUARE_SIZE);
    self.o = osvg.UIImage;
    SVGKImage *bgsvg = [SVGKImage imageNamed: @"background.svg"];
    self.back = bgsvg.UIImage;
    [self addGestureRecognizer:
     [[UITapGestureRecognizer alloc] initWithTarget: self
                                             action: @selector(handleTap:)]];
    UIView* derekView = [[UIView alloc] initWithFrame: CGRectMake(0, 0, 100, 100)];
    derekView.backgroundColor = [UIColor yellowColor];
    UIButton *button = [UIButton buttonWithType: UIButtonTypeSystem];
    button.center = CGPointMake(5, 20);
    UIImage *icon = [UIImage imageNamed: @"ic_game_menu.png"];
    [button setImage: icon forState: UIControlStateNormal];
    [button sizeToFit];
    [button addTarget: self
               action: @selector(menuButtonClicked:)
     forControlEvents: UIControlEventTouchUpInside];
    [self addSubview: button];
  }
  return self;
}

- (void)menuButtonClicked: (UIButton*)button {
  NSLog(@"menubuttonclicked");
  UIActionSheet *sheet = [[UIActionSheet alloc] initWithTitle: nil
                                                     delegate: self.delegate
                                            cancelButtonTitle: @"Cancel"
                                       destructiveButtonTitle: @"Resign"
                                            otherButtonTitles: @"Main Menu", @"Game List",
                                                               @"New Game", nil];
  [sheet showInView: self];
}

- (void)drawGame: (NTSGame*)game {
  self.currentGame = game;
  [self setNeedsDisplay];
}

- (void)handleTap:(UITapGestureRecognizer *)sender {
  if (sender.state == UIGestureRecognizerStateEnded) {
    CGPoint point = [sender locationInView: self];
    if (point.y < TOP_OFFSET || point.y > TOP_OFFSET + 3 * SQUARE_SIZE ||
        point.x < 0 || point.x > 3 * SQUARE_SIZE) {
      return; // Out of bounds
    }
    if (self.delegate) {
      [self.delegate handleSquareTapAtX: point.x / SQUARE_SIZE
                                    AtY: (point.y - TOP_OFFSET) / SQUARE_SIZE];
    }
  }
}

- (void)drawRect:(CGRect)rect
{
  NSLog(@"draw rect");
  [self.back drawAtPoint: CGPointZero];
  if (self.currentGame) {
    for (NTSAction *action in [self.currentGame getActions]) {
      for (NTSCommand* command in [action getCommands]) {
        CGPoint point = CGPointMake([command getColumn] * SQUARE_SIZE,
                                    [command getRow] * SQUARE_SIZE + TOP_OFFSET);
        if ([[action getPlayerNumber] intValue] == [NTSModel X_PLAYER]) {
          [self.x drawAtPoint: point];
        } else {
          [self.o drawAtPoint: point];
        }
      }
    }
  }
}

@end
