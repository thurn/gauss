#import <AudioToolbox/AudioToolbox.h>
#import "GameCanvas.h"
#import "Game.h"
#import "Action.h"
#import "SVGKit.h"
#import "Command.h"
#import "Model.h"
#import "Games.h"
#import "JavaUtils.h"
#import "Identifiers.h"

@interface GameCanvas ()
@property(strong,nonatomic) SVGKImage *backgroundSvg;
@property(strong,nonatomic) SVGKImage *xSvg;
@property(strong,nonatomic) SVGKImage *oSvg;
@property(nonatomic) CGPoint originalViewCenter;
@property(strong,nonatomic) NSMutableDictionary* views;
@property(nonatomic) SystemSoundID addCommandSound;
@property(nonatomic) SystemSoundID removeCommandSound;
@property(nonatomic) SystemSoundID submitCommandSound;
@property(nonatomic) SystemSoundID gameOverSound;
@property(nonatomic) int topOffset;
@property(nonatomic) int squareSize;
@property(nonatomic) int sideMargin;
@property(nonatomic) int topMargin;
@property(nonatomic) BOOL mute;
@end

@implementation GameCanvas

- (id)initWithFrame:(CGRect)frame {
  self = [super initWithFrame:frame];
  if (self) {
    _backgroundSvg = [SVGKImage imageNamed:@"background.svg"];
    _xSvg = [SVGKImage imageNamed:@"x.svg"];
    _oSvg = [SVGKImage imageNamed:@"o.svg"];
    _views = [NSMutableDictionary new];

    AudioServicesCreateSystemSoundID(
        (__bridge CFURLRef)[[NSBundle mainBundle] URLForResource:@"addCommand"
                                                   withExtension:@"wav"],
        &_addCommandSound);
    AudioServicesCreateSystemSoundID(
        (__bridge CFURLRef)[[NSBundle mainBundle] URLForResource:@"removeCommand"
                                                   withExtension:@"wav"],
        &_removeCommandSound);
    AudioServicesCreateSystemSoundID(
        (__bridge CFURLRef)[[NSBundle mainBundle] URLForResource:@"commandSubmitted"
                                                   withExtension:@"wav"],
        &_submitCommandSound);
    AudioServicesCreateSystemSoundID(
        (__bridge CFURLRef)[[NSBundle mainBundle] URLForResource:@"gameOver"
                                                   withExtension:@"wav"],
        &_gameOverSound);
    [self addGestureRecognizer:
     [[UITapGestureRecognizer alloc] initWithTarget:self
                                             action:@selector(handleTap:)]];
    
  }
  return self;
}

- (void)onRegisteredWithNSString:(NSString *)viewerId
                     withNTSGame:(NTSGame *)game
                   withNTSAction:(NTSAction *)currentAction {
  float scale = [self computeScaleFactorWithWidth:320 withHeight:480];
  int backgroundWidth = 320 * scale;
  int backgroundHeight = 480 * scale;
  _squareSize = backgroundWidth / 3;
  _topOffset = 80 * scale;
  _sideMargin = (self.frame.size.width - backgroundWidth) / 2;
  _topMargin = (self.frame.size.height - backgroundHeight) / 2;

  [[self subviews] makeObjectsPerformSelector:@selector(removeFromSuperview)];
  UIView *backgroundView = [self drawSvg:_backgroundSvg
                                  inRect:CGRectMake(_sideMargin,
                                                    _topMargin,
                                                    backgroundWidth,
                                                    backgroundHeight)];

  [self addSubview:backgroundView];
  for (NTSAction *action in (id<NSFastEnumeration>)[game getSubmittedActionList]) {
    [self drawAction:action animate:NO draggable:NO];
  }

  if (currentAction) {
    [self drawAction:currentAction animate:YES draggable:YES];
  }
}

- (float)computeScaleFactorWithWidth:(int)width withHeight:(int)height {
  float widthRatio = self.frame.size.width / width;
  float heightRatio = self.frame.size.height / height;
  return MIN(widthRatio, heightRatio);
}

- (int)longEdgeLength {
  return MAX(self.frame.size.width, self.frame.size.height);
}

- (int)shortEdgeLength {
  return MIN(self.frame.size.width, self.frame.size.height);
}

- (void)onCommandAddedWithNTSAction:(NTSAction *)action withNTSCommand:(NTSCommand *)command {
  [self drawCommand:command playerNumber:[action getPlayerNumber] animate:YES draggable:YES];
  [self playSoundIfEnabled:_addCommandSound];
}

- (void)onCommandRemovedWithNTSAction:(NTSAction *)action withNTSCommand:(NTSCommand *)command {
  UIView *view = _views[command];
  [UIView animateWithDuration:0.2 animations:^{
      view.transform = CGAffineTransformScale(view.transform, 0.1, 0.1);
  } completion:^(BOOL finished) {
      [view removeFromSuperview];
  }];
  [self playSoundIfEnabled:_removeCommandSound];
}

- (void)onCommandChangedWithNTSAction:(NTSAction *)action withNTSCommand:(NTSCommand *)oldCommand
    withNTSCommand:(NTSCommand *)newCommand {
  _views[newCommand] = _views[oldCommand];
  [_views removeObjectForKey:oldCommand];
  [self playSoundIfEnabled:_addCommandSound];
}

-(void)onActionSubmittedWithNTSAction:(NTSAction *)action {
  for (NTSCommand *command in [action getCommandList]) {
    [self removeAllGestureRecognizers:_views[command]];
  }
  [self playSoundIfEnabled:_submitCommandSound];
  [self drawAction:action animate:YES draggable:NO];
}

- (void)onGameOverWithNTSGame:(NTSGame *)game {
  [self playSoundIfEnabled:_gameOverSound];
}

- (void)drawAction:(NTSAction*)action animate:(BOOL)animate draggable:(BOOL)draggable {
  for (NTSCommand* command in (id<NSFastEnumeration>)[action getCommandList]) {
    if (!_views[command]) {
      [self drawCommand:command
           playerNumber:[action getPlayerNumber]
                animate:animate
              draggable:draggable];
    }
  }
}

- (void)drawCommand:(NTSCommand*)command
       playerNumber:(int)playerNumber
            animate:(BOOL)animate
          draggable:(BOOL)draggable {
  CGRect rect = CGRectMake(([command getColumn] * _squareSize) + _sideMargin,
                           ([command getRow] * _squareSize) + _topOffset + _topMargin,
                           _squareSize,
                           _squareSize);
  SVGKImage *image = playerNumber == [NTSModel X_PLAYER] ? _xSvg : _oSvg;
  UIView *newView = [self drawSvg:image inRect:rect];
  if (animate) {
    CGAffineTransform transform = newView.transform;
    newView.transform = CGAffineTransformScale(transform, 0.1, 0.1);
    [UIView animateWithDuration:0.2 animations:^{
        newView.transform = CGAffineTransformScale(transform, 1.1, 1.1);
    } completion:^(BOOL finished) {
        [UIView animateWithDuration:0.1 animations:^{
            newView.transform = CGAffineTransformScale(transform, 0.9, 0.9);
        } completion:^(BOOL finished) {
            [UIView animateWithDuration:0.1 animations:^{
                newView.transform = transform;
            }];
        }];
    }];
  }
  if (draggable) {
    UIPanGestureRecognizer *panRecognizer = [[UIPanGestureRecognizer alloc]
                                             initWithTarget:self action:@selector(move:)];
    [panRecognizer setMinimumNumberOfTouches:1];
    [newView addGestureRecognizer:panRecognizer];
  }
  _views[command] = newView;
  [self addSubview:newView];
}

- (void)removeAllGestureRecognizers:(UIView*)view {
  for (UIGestureRecognizer *recognizer in view.gestureRecognizers) {
    [view removeGestureRecognizer:recognizer];
  }
}

- (void)move:(id)sender {
  UIPanGestureRecognizer *recognizer = (UIPanGestureRecognizer*)sender;
  UIView *view = [recognizer view];
  [self bringSubviewToFront:view];
  CGPoint newPoint = [recognizer translationInView:self];
  
  if ([recognizer state] == UIGestureRecognizerStateBegan) {
    _originalViewCenter = view.center;
  }
  CGPoint translated = CGPointMake(_originalViewCenter.x + newPoint.x,
                                   _originalViewCenter.y + newPoint.y);
  if ([recognizer state] == UIGestureRecognizerStateChanged) {
    view.center = translated;
  } else if ([recognizer state] == UIGestureRecognizerStateEnded) {
    CGPoint newCenter = [self handleFinalPoint:translated];
    [UIView animateWithDuration:0.1 animations:^{
      view.center = newCenter;
    }];
  }
}

- (CGPoint)handleFinalPoint:(CGPoint)final {
  for (int i = 0; i < 3; ++i) {
    for (int j = 0; j < 3; ++j) {
      CGRect rect = CGRectMake((i * _squareSize) + _sideMargin,
                               _topMargin + _topOffset + (j * _squareSize),
                               _squareSize,
                               _squareSize);
      if (CGRectContainsPoint(rect, final) && [_delegate allowDragToX:i toY:j]) {
        [_delegate handleDragToX:i toY:j];
        return CGPointMake(rect.origin.x + (rect.size.width / 2),
                           rect.origin.y + (rect.size.height / 2));
      }
    }
  }
  return _originalViewCenter;
}

- (SVGKImageView*)drawSvg:(SVGKImage*)svg inRect:(CGRect)rect {
  SVGKImageView *view = [[SVGKFastImageView alloc] initWithSVGKImage:svg];
  view.frame = rect;
  return view;
}

- (void)handleTap:(UITapGestureRecognizer*)sender {
  if (sender.state == UIGestureRecognizerStateEnded) {
    CGPoint point = [sender locationInView: self];
    if (point.y < _topOffset + _topMargin ||
        point.y > _topMargin + _topOffset + (3 * _squareSize) ||
        point.x < _sideMargin ||
        point.x > (3 * _squareSize) + _sideMargin) {
      return; // Out of bounds
    }
    [_delegate handleSquareTapAtX:(point.x - _sideMargin) / _squareSize
                              AtY:(point.y - _topOffset - _topMargin) / _squareSize];
  }
}

- (void)playSoundIfEnabled:(SystemSoundID)sound {
  NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
  if(![[userDefaults valueForKey:kDisableSoundsKey] boolValue] && !_mute) {
    AudioServicesPlaySystemSound(sound);
    // Temporarily mute sounds after playing to prevent stacking sounds on top of each other.
    _mute = YES;
    dispatch_time_t delay = dispatch_time(DISPATCH_TIME_NOW, 0.5 * NSEC_PER_SEC);
    dispatch_after(delay, dispatch_get_main_queue(), ^{
      _mute = NO;
    });
  }
}

- (void)dealloc {
  AudioServicesDisposeSystemSoundID(_addCommandSound);
  AudioServicesDisposeSystemSoundID(_removeCommandSound);
  AudioServicesDisposeSystemSoundID(_submitCommandSound);
  AudioServicesDisposeSystemSoundID(_gameOverSound);
}

@end
