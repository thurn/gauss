//
//  GameView.h
//  noughts
//
//  Created by Derek Thurn on 12/22/13.
//  Copyright (c) 2013 Derek Thurn. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "game.h"

typedef NS_ENUM(NSUInteger, GameMenuSelection) {
  kUnknownSelection,
  kNewGameMenu,
  kMainMenu,
  kGameList,
  kResignOrArchive
};

@protocol GameViewDelegate
- (void)handleSquareTapAtX: (int)x AtY: (int)y;
- (void)handleGameMenuSelection: (GameMenuSelection)selection;
- (BOOL)canSubmit;
- (BOOL)canUndo;
- (BOOL)canRedo;
- (void)handleSubmit;
- (void)handleUndo;
- (void)handleRedo;
- (BOOL)isGameOver;
@end

@interface GameView : UIView <UIActionSheetDelegate>
@property (weak,nonatomic) id<GameViewDelegate> delegate;
- (void)drawGame:(NTSGame *)game;
@end
