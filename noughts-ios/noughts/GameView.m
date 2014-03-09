#import "GameView.h"
#import "SVGKit.h"
#import "Model.h"
#import "Game.h"
#import "Action.h"
#import "Command.h"
#include "java/lang/Integer.h"
#import "SMCalloutView.h"
#import "UIView+AutoLayout.h"

#define TOP_OFFSET 80
#define SQUARE_SIZE 107

@interface GameView ()
@property(strong,nonatomic) UIImage *logo;
@property(strong,nonatomic) UIImage *x;
@property(strong,nonatomic) UIImage *o;
@property(strong,nonatomic) UIImage *backgroundImage;
@property(strong,nonatomic) NTSGame *currentGame;
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
@property double taskId;
@end

@implementation GameView

- (id)initWithFrame:(CGRect)frame {
  self = [super initWithFrame:frame];
  if (self) {
    // Initialization code
    self.backgroundColor = [UIColor whiteColor];
    SVGKImage *svg = [SVGKImage imageNamed:@"logo.svg"];
    _logo = svg.UIImage;
    SVGKImage *xsvg = [SVGKImage imageNamed:@"x.svg"];
    xsvg.size = CGSizeMake(SQUARE_SIZE, SQUARE_SIZE);
    _x = xsvg.UIImage;
    SVGKImage *osvg = [SVGKImage imageNamed:@"o.svg"];
    osvg.size = CGSizeMake(SQUARE_SIZE, SQUARE_SIZE);
    _o = osvg.UIImage;
    SVGKImage *bgsvg = [SVGKImage imageNamed:@"background.svg"];
    _backgroundImage = bgsvg.UIImage;
    
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

    [self addGestureRecognizer:
     [[UITapGestureRecognizer alloc] initWithTarget:self
                                             action:@selector(handleTap:)]];

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
    _gameStatusConstraint = [_gameStatusView autoPinEdgeToSuperviewEdge:ALEdgeBottom withInset:-75];
    
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

- (void)updateConstraints {
  [super updateConstraints];
  CGFloat statusBarHeight = [[NSNumber numberWithFloat:
                              [UIApplication sharedApplication].statusBarFrame.size.height]
                             floatValue];
  [_gameMenuButton autoPinEdgeToSuperviewEdge:ALEdgeLeading withInset:5];
  [_gameMenuButton autoPinEdgeToSuperviewEdge:ALEdgeTop withInset:statusBarHeight];
  [_submitButton autoPinEdgeToSuperviewEdge:ALEdgeTop withInset:statusBarHeight];
  [_gameStatusView autoSetDimension:ALDimensionHeight toSize:75];
  [_gameStatusView autoPinEdgeToSuperviewEdge:ALEdgeLeading withInset:0];
  [_gameStatusView autoPinEdgeToSuperviewEdge:ALEdgeTrailing withInset:0];
  [_activityView autoSetDimension:ALDimensionWidth toSize:200];
  [_activityView autoSetDimension:ALDimensionHeight toSize:50];
  [_activityView autoCenterInSuperview];
}

- (void)displayGameStatusWithImage:(UIImage*)image
                       withString:(NSString*)string
                        withColor:(UIColor*)color {
  _gameStatusImage.image = image;
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
      _gameStatusConstraint.constant = 75;
      [self layoutIfNeeded];
    }];
    }
  });
}

- (void)handleGameStatusTap:(UITapGestureRecognizer *)recognizer {
  // Tap game status to dismiss.
  [UIView animateWithDuration:0.3 animations:^{
    _gameStatusConstraint.constant = 75;
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
    [_delegate handleGameMenuSelection: selection];
  }
}

- (void)drawGame: (NTSGame*)game {
  _currentGame = game;
  [self setButtonsEnabledSubmit:[_delegate canSubmit]
                           undo:[_delegate canUndo]
                           redo:[_delegate canRedo]];
  [self setNeedsDisplay];
}

- (void)setButtonsEnabledSubmit:(BOOL)submitEnabled undo:(BOOL)undoEnabled redo:(BOOL)redoEnabled {
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

- (void)handleTap:(UITapGestureRecognizer*)sender {
  if (sender.state == UIGestureRecognizerStateEnded) {
    CGPoint point = [sender locationInView: self];
    if (point.y < TOP_OFFSET || point.y > TOP_OFFSET + 3 * SQUARE_SIZE ||
        point.x < 0 || point.x > 3 * SQUARE_SIZE) {
      return; // Out of bounds
    }
    [_delegate handleSquareTapAtX:point.x / SQUARE_SIZE
                                  AtY:(point.y - TOP_OFFSET) / SQUARE_SIZE];
  }
}

- (void)drawRect:(CGRect)rect {
  [_backgroundImage drawAtPoint:CGPointZero];
  if (_currentGame) {
    for (NTSAction *action in (id<NSFastEnumeration>)[_currentGame getSubmittedActionList]) {
      [self drawAction:action animate:NO];
    }
  }
  if ([_currentGame hasCurrentAction]) {
    [self drawAction:[_currentGame getCurrentAction] animate:YES];
  }
}

- (void)drawAction:(NTSAction*)action animate:(BOOL)animate {
  for (NTSCommand* command in (id<NSFastEnumeration>)[action getCommandList]) {
    CGPoint point = CGPointMake([command getColumn] * SQUARE_SIZE,
                                [command getRow] * SQUARE_SIZE + TOP_OFFSET);
    if ([action getPlayerNumber] == [NTSModel X_PLAYER]) {
      [_x drawAtPoint:point];
    } else {
      [_o drawAtPoint:point];
    }
  }
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
  } completion: ^(BOOL finished){
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
