//
//  GameView.m
//  noughts
//
//  Created by Derek Thurn on 12/22/13.
//  Copyright (c) 2013 Derek Thurn. All rights reserved.
//

#import "GameView.h"
#import "SVGKit.h"

@interface GameView ()
@property (strong,nonatomic) UIImage* logo;
@property (strong,nonatomic) UIImage* x;
@property (strong,nonatomic) UIImage* o;
@property (strong,nonatomic) UIImage* back;
@end

@implementation GameView

- (id)initWithFrame:(CGRect)frame
{
  self = [super initWithFrame:frame];
  if (self) {
    // Initialization code
    self.backgroundColor = [UIColor whiteColor];
    SVGKImage *svg = [SVGKImage imageNamed: @"logo.svg"];
    self.logo = svg.UIImage;
    SVGKImage *xsvg = [SVGKImage imageNamed: @"x.svg"];
    xsvg.size = CGSizeMake(107, 107);
    self.x = xsvg.UIImage;
    SVGKImage *osvg = [SVGKImage imageNamed: @"o.svg"];
    osvg.size = CGSizeMake(107, 107);
    self.o = osvg.UIImage;
    SVGKImage *bgsvg = [SVGKImage imageNamed: @"background.svg"];
    self.back = bgsvg.UIImage;
  }
  return self;
}

- (void)drawRect:(CGRect)rect
{
  NSLog(@"draw rect");
  [self.back drawAtPoint: CGPointZero];
  [self.x drawAtPoint: CGPointMake(0, 86)];
  [self.x drawAtPoint: CGPointMake(107, 193)];
  [self.o drawAtPoint: CGPointMake(214, 300)];
}

@end
