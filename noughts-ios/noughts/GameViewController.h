//
//  GameViewController.h
//  noughts
//
//  Created by Derek Thurn on 12/21/13.
//  Copyright (c) 2013 Derek Thurn. All rights reserved.
//

#import "ViewController.h"
#import "Model.h"
#import "GameView.h"

@interface GameViewController : ViewController <NTSModel_GameUpdateListener, GameViewDelegate>
@end
