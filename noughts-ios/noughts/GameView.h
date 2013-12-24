//
//  GameView.h
//  noughts
//
//  Created by Derek Thurn on 12/22/13.
//  Copyright (c) 2013 Derek Thurn. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "game.h"

@protocol GameViewDelegate <UIActionSheetDelegate>
- (void)handleSquareTapAtX: (int)x AtY: (int)y;
@end

@interface GameView : UIView
@property (weak,nonatomic) id<GameViewDelegate> delegate;
- (void)drawGame: (NTSGame*)game;
@end
