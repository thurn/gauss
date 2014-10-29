#import "GameView.h"
#import "SMCalloutView.h"
#import "UIView+AutoLayout.h"
#import "GameCanvas.h"
#import "ImageStringUtils.h"
#import "CommandUpdateListener.h"

#define TOP_OFFSET 80
#define SQUARE_SIZE 107

@interface GameView ()
@property(strong,nonatomic) UIButton *gameMenuButton;
@property(strong,nonatomic) UIButton *submitButton;
@property(strong,nonatomic) UIButton *undoButton;
@property(strong,nonatomic) UIButton *redoButton;
@property(weak, nonatomic) IBOutlet UIImageView *gameStatusImage;
@property(weak, nonatomic) IBOutlet UIView *gameStatusColorView;
@property(weak, nonatomic) IBOutlet UILabel *gameStatusLabel;
@property(strong, nonatomic) UIView *gameStatusView;
@property(strong, nonatomic) NSLayoutConstraint *gameStatusConstraint;
@property(strong, nonatomic) NSLayoutConstraint *submitButtonConstraint;
@property(strong, nonatomic) NSLayoutConstraint *undoButtonConstraint;
@property(strong, nonatomic) NSLayoutConstraint *redoButtonConstraint;
@property(strong, nonatomic) UIActivityIndicatorView *activityView;
@property(strong, nonatomic) SMCalloutView *squareTapCallout;
@property(strong, nonatomic) SMCalloutView *submitCallout;
@property(strong, nonatomic) GameCanvas *gameCanvas;
@property(nonatomic) float scale;
@property double taskId;
@end

@implementation GameView

- (id)initWithFrame:(CGRect)frame {
  self = [super initWithFrame:frame];
  if (self) {
    self.backgroundColor = [UIColor whiteColor];
    _scale = UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPad ? 2 : 1;
    _gameCanvas = [GameCanvas new];
    _gameCanvas.translatesAutoresizingMaskIntoConstraints = NO;
    [self addSubview:_gameCanvas];
    
    _activityView = [[UIActivityIndicatorView alloc]
                        initWithActivityIndicatorStyle:UIActivityIndicatorViewStyleWhiteLarge];
    _activityView.center = self.center;
    _activityView.color = [UIColor blackColor];
    _activityView.backgroundColor = [UIColor whiteColor];
    _activityView.layer.cornerRadius = 5;
    _activityView.translatesAutoresizingMaskIntoConstraints = NO;
    _activityView.hidesWhenStopped = NO;
    _activityView.opaque = NO;
    _activityView.hidden = YES;
    [self addSubview:_activityView];
    
    _squareTapCallout = [SMCalloutView new];
    _squareTapCallout.title = @"Tap a square";
    _squareTapCallout.subtitle = @"to make your move";
    _squareTapCallout.permittedArrowDirection = SMCalloutArrowDirectionAny;
    
    _submitCallout = [SMCalloutView new];
    _submitCallout.title = @"Hit submit";
    _submitCallout.subtitle = @"to confirm";
    _submitCallout.permittedArrowDirection = SMCalloutArrowDirectionAny;
    
    _gameMenuButton = [UIButton buttonWithType:UIButtonTypeSystem];
    UIImage *gameMenuIcon = [UIImage imageNamed:@"ic_game_menu.png"];
    [_gameMenuButton setImage:gameMenuIcon forState:UIControlStateNormal];
    [_gameMenuButton addTarget:self
                        action:@selector(menuButtonClicked:)
              forControlEvents:UIControlEventTouchUpInside];
    [_gameMenuButton sizeToFit];
    _gameMenuButton.translatesAutoresizingMaskIntoConstraints = NO;
    [self addSubview:_gameMenuButton];

    _submitButton = [UIButton buttonWithType:UIButtonTypeSystem];
    [_submitButton setTitle:@"Submit" forState:UIControlStateNormal];
    if(UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPad) {
      _submitButton.titleLabel.font = [UIFont systemFontOfSize:30];
    }
    [_submitButton addTarget:self
                      action:@selector(submitClicked:)
            forControlEvents:UIControlEventTouchUpInside];
    [_submitButton sizeToFit];
    _submitButton.translatesAutoresizingMaskIntoConstraints = NO;
    [self addSubview:_submitButton];

    _undoButton = [UIButton buttonWithType:UIButtonTypeSystem];
    UIImage *undoIcon = [UIImage imageNamed:@"ic_undo.png"];
    [_undoButton setImage:undoIcon forState:UIControlStateNormal];
    [_undoButton addTarget:self
                    action:@selector(undoClicked:)
          forControlEvents:UIControlEventTouchUpInside];
    [_undoButton sizeToFit];
    _undoButton.translatesAutoresizingMaskIntoConstraints = NO;
    [self addSubview:_undoButton];

    _redoButton = [UIButton buttonWithType:UIButtonTypeSystem];
    UIImage *redoIcon = [UIImage imageNamed:@"ic_redo.png"];
    [_redoButton setImage:redoIcon forState:UIControlStateNormal];
    [_redoButton addTarget:self
                    action:@selector(redoClicked:)
          forControlEvents:UIControlEventTouchUpInside];
    [_redoButton sizeToFit];
    _redoButton.translatesAutoresizingMaskIntoConstraints = NO;
    [self addSubview:_redoButton];
    
    NSArray *subviewArray = [[NSBundle mainBundle]
                             loadNibNamed:@"GameStatusView"
                                    owner:self
                                  options:nil];
    _gameStatusView = [subviewArray objectAtIndex:0];
    _gameStatusView.translatesAutoresizingMaskIntoConstraints = NO;
    UITapGestureRecognizer *singleTap =
        [[UITapGestureRecognizer alloc] initWithTarget:self
                                                action:@selector(handleGameStatusTap:)];
    [_gameStatusView addGestureRecognizer:singleTap];
    [self addSubview:_gameStatusView];

    CGFloat statusBarHeight = [self statusBarHeight];
    [_gameCanvas autoPinEdgesToSuperviewEdgesWithInsets:UIEdgeInsetsZero];
    
    [_gameMenuButton autoPinEdgeToSuperviewEdge:ALEdgeLeading withInset:5];
    [_gameMenuButton autoPinEdgeToSuperviewEdge:ALEdgeTop withInset:statusBarHeight];
    [_submitButton autoPinEdgeToSuperviewEdge:ALEdgeTop withInset:statusBarHeight];
    [_gameStatusView autoSetDimension:ALDimensionHeight toSize:75*_scale];
    [_gameStatusView autoPinEdgeToSuperviewEdge:ALEdgeLeading withInset:0];
    [_gameStatusView autoPinEdgeToSuperviewEdge:ALEdgeTrailing withInset:0];
    [_activityView autoSetDimension:ALDimensionWidth toSize:200*_scale];
    [_activityView autoSetDimension:ALDimensionHeight toSize:50*_scale];
    [_activityView autoCenterInSuperview];
    
    _gameStatusConstraint = [_gameStatusView autoPinEdgeToSuperviewEdge:ALEdgeBottom
                                                              withInset:-75*_scale];
    
    int submitInset = 5;
    int redoInset = 5 + _submitButton.bounds.size.width + submitInset;
    int undoInset = 2 + _redoButton.bounds.size.width + redoInset;
    
    _submitButtonConstraint = [_submitButton autoPinEdgeToSuperviewEdge:ALEdgeTrailing
                                                              withInset:submitInset];
    _redoButtonConstraint = [_redoButton autoPinEdgeToSuperviewEdge:ALEdgeTrailing
                                                          withInset:redoInset];
    _undoButtonConstraint = [_undoButton autoPinEdgeToSuperviewEdge:ALEdgeTrailing
                                                          withInset:undoInset];
    [@[_undoButton, _redoButton, _submitButton] autoAlignViewsToAxis:ALAxisHorizontal];
  }
  return self;
}

- (CGFloat)statusBarHeight {
  return MIN([UIApplication sharedApplication].statusBarFrame.size.height,
             [UIApplication sharedApplication].statusBarFrame.size.width);
}

- (id<NTSCommandUpdateListener>)getCommandUpdateListener {
  return _gameCanvas;
}

- (void)displayGameStatusWithImageString:(NTSImageString*)imageString
                              withString:(NSString*)string
                               withColor:(UIColor*)color {
  [ImageStringUtils setImage:_gameStatusImage imageString:imageString size:75];
  _gameStatusLabel.text = string;
  _gameStatusColorView.backgroundColor = color;
  [self layoutIfNeeded];
  [UIView animateWithDuration:0.3 animations:^{
      _gameStatusConstraint.constant = 0;
      [self layoutIfNeeded];
  }];
  dispatch_time_t delay = dispatch_time(DISPATCH_TIME_NOW, 3 * NSEC_PER_SEC);
  double taskId = rand();
  _taskId = taskId;
  // We keep track of the ID of the last scheduled task and only animate
  // the view going back down if we're still the last scheduled task when
  // it's time to run.
  dispatch_after(delay, dispatch_get_main_queue(), ^{
      if (taskId == _taskId) {
      [UIView animateWithDuration:0.3 animations:^{
          _gameStatusConstraint.constant = 75*_scale;
          [self layoutIfNeeded];
      }];
      }
  });
}

- (void)handleGameStatusTap:(UITapGestureRecognizer *)recognizer {
  // Tap game status to dismiss.
  [UIView animateWithDuration:0.3 animations:^{
      _gameStatusConstraint.constant = 75*_scale;
      [self layoutIfNeeded];
  }];
}

- (void)menuButtonClicked:(UIButton*)button {
  NSString *destructiveTitle = [_delegate isGameOver] ? @"Archive Game" : @"Resign";
  UIActionSheet *sheet = [[UIActionSheet alloc] initWithTitle:nil
                                                     delegate:self
                                            cancelButtonTitle:@"Cancel"
                                       destructiveButtonTitle:destructiveTitle
                                            otherButtonTitles:@"Main Menu", @"Game List",
                                                              @"New Game", nil];
  [sheet showInView:self];
}

- (void)submitClicked:(UIButton*)button {
  [_delegate handleSubmit];
}

- (void)undoClicked:(UIButton*)button {
  [_delegate handleUndo];
}

- (void)redoClicked:(UIButton*)button {
  [_delegate handleRedo];
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
    [_delegate handleGameMenuSelection:selection];
  }
}

- (void)updateButtons {
  BOOL submitEnabled = [_delegate canSubmit];
  BOOL undoEnabled = [_delegate canUndo];
  BOOL redoEnabled = [_delegate canRedo];
  _submitButton.enabled = submitEnabled;
  _undoButton.enabled = undoEnabled;
  _redoButton.enabled = redoEnabled;

  int submitWidth = _submitButton.bounds.size.width;
  int redoWidth = _redoButton.bounds.size.width;
  int undoWidth = _undoButton.bounds.size.width;
  int submitConstant = submitEnabled ? 5 : -submitWidth;
  int redoConstant = redoEnabled ? submitWidth + submitConstant + 5 : -redoWidth;
  int undoConstant = undoEnabled ? submitWidth + submitConstant + 5 : -undoWidth;

  [UIView animateWithDuration:0.3 animations:^{
      // Invert constants to convert from insets
      _submitButtonConstraint.constant = -submitConstant;
      _redoButtonConstraint.constant = -redoConstant;
      _undoButtonConstraint.constant = -undoConstant;
      [self layoutIfNeeded];
  }];
}

-(void)setGameCanvasDelegate:(id<GameCanvasDelegate>)delegate {
  _gameCanvas.delegate = delegate;
}

- (void)showComputerThinkingIndicator {
  _activityView.hidden = NO;
  _activityView.alpha = 0.0;
  [_activityView startAnimating];
  [UIView animateWithDuration:0.3 animations:^{
      _activityView.alpha = 0.95;
  }];
}

- (void)hideComputerThinkingIndicator {
  [_activityView stopAnimating];
  [UIView animateWithDuration:0.3 animations:^{
      _activityView.alpha = 0.0;
  } completion:^(BOOL finished){
      _activityView.hidden = YES;
  }];
}

- (void)showTapSquareCallout {
  CGRect calloutRect = CGRectMake(1.5 * SQUARE_SIZE,
                                  SQUARE_SIZE + TOP_OFFSET + 5,
                                  1,
                                  1);
  [_squareTapCallout presentCalloutFromRect:calloutRect
                               inView:self
                    constrainedToView:self
                             animated:YES];
}

- (void)hideTapSquareCallout {
  [_squareTapCallout dismissCalloutAnimated:YES];
}

- (void)showSubmitCallout {
  // Allow time for the submit animation to finish.
  dispatch_time_t delay = dispatch_time(DISPATCH_TIME_NOW, 0.5 * NSEC_PER_SEC);
  dispatch_after(delay, dispatch_get_main_queue(), ^{
      [_submitCallout presentCalloutFromRect:_submitButton.frame
                                      inView:self
                           constrainedToView:self
                                    animated:YES];
  });
}

- (void)hideSubmitCallout {
  [_submitCallout dismissCalloutAnimated:YES];
}

@end
