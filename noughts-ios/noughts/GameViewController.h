//
//  GameViewController.h
//  noughts
//
//  Created by Derek Thurn on 12/21/13.
//  Copyright (c) 2013 Derek Thurn. All rights reserved.
//

#import "Model.h"
#import "GameView.h"
#import "HasModel.h"

@interface GameViewController : UIViewController <NTSModel_GameUpdateListener,
                                                  GameViewDelegate,
                                                  HasModel>
@property (strong,nonatomic) NSString *currentGameId;
- (void)createLocalMultiplayerGame;
@end
