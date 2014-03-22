#import <AudioToolbox/AudioToolbox.h>
#import "GameCanvas.h"
#import "Action.h"
#import "SVGKit.h"
#import "Command.h"
#import "Model.h"
#import "Games.h"
#import "J2obcUtils.h"

@interface GameCanvas ()
@property(strong,nonatomic) SVGKImage *backgroundSvg;
@property(strong,nonatomic) SVGKImage *xSvg;
@property(strong,nonatomic) SVGKImage *oSvg;
@property(nonatomic) CGPoint originalViewCenter;
@property(strong,nonatomic) NSMutableDictionary* views;
@property(strong,nonatomic) NSArray *viewerPlayerNumbers;
@property(nonatomic) SystemSoundID addCommandSound;
@property(nonatomic) SystemSoundID removeCommandSound;
@property(nonatomic) SystemSoundID submitCommandSound;
@property(nonatomic) SystemSoundID gameOverSound;
@property(nonatomic) int topOffset;
@property(nonatomic) int squareSize;
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

- (void)onRegisteredWithNSString:(NSString*)viewerId withNTSGame:(NTSGame *)game {
  id<JavaUtilList> playerNumbers = [NTSGames playerNumbersForPlayerIdWithNTSGame:game
                                                                    withNSString:viewerId];
  _squareSize = self.frame.size.width / 3;
  _topOffset = (self.frame.size.height - self.frame.size.width) / 2;
  
  _viewerPlayerNumbers = [J2obcUtils javaUtilListToNsArray:playerNumbers];

  [[self subviews] makeObjectsPerformSelector:@selector(removeFromSuperview)];
  int backgroundWidth = 320;
  int backgroundHeight = 480;
  UIView *backgroundView = [self drawSvg:_backgroundSvg
                                  inRect:CGRectMake(0,
                                                    (self.frame.size.height - backgroundHeight) / 2,
                                                    backgroundWidth,
                                                    backgroundHeight)];

  [self addSubview:backgroundView];
  for (NTSAction *action in (id<NSFastEnumeration>)[game getSubmittedActionList]) {
    [self drawAction:action animate:NO draggable:NO];
  }
  
  if ([game hasCurrentAction]) {
    BOOL draggable = [self belongsToViewer:[game getCurrentAction]];
    [self drawAction:[game getCurrentAction] animate:YES draggable:draggable];
  }
}

- (BOOL)belongsToViewer:(NTSAction*) action {
  int playerNumber= [action getPlayerNumber];
  return [_viewerPlayerNumbers containsObject:[[NSNumber alloc] initWithInt:playerNumber]];
}

- (void)onCommandAddedWithNTSAction:(NTSAction *)action withNTSCommand:(NTSCommand *)command {
  BOOL draggable = [self belongsToViewer:action];
  [self drawCommand:command playerNumber:[action getPlayerNumber] animate:YES draggable:draggable];
  AudioServicesPlaySystemSound(_addCommandSound);
}

- (void)onCommandRemovedWithNTSAction:(NTSAction *)action withNTSCommand:(NTSCommand *)command {
  UIView *view = _views[command];
  [UIView animateWithDuration:0.2 animations:^{
    view.transform = CGAffineTransformScale(view.transform, 0.1, 0.1);
  } completion:^(BOOL finished) {
    [view removeFromSuperview];
  }];
  AudioServicesPlaySystemSound(_removeCommandSound);
}

- (void)onCommandChangedWithNTSAction:(NTSAction *)action withNTSCommand:(NTSCommand *)oldCommand
    withNTSCommand:(NTSCommand *)newCommand {
  _views[newCommand] = _views[oldCommand];
  [_views removeObjectForKey:oldCommand];
  AudioServicesPlaySystemSound(_addCommandSound);
}

- (void)onCommandSubmittedWithNTSAction:(NTSAction *)action withNTSCommand:(NTSCommand *)command {
  [self removeAllGestureRecognizers:_views[command]];
  AudioServicesPlaySystemSound(_submitCommandSound);
}

- (void)onGameOverWithNTSGame:(NTSGame *)game {
  AudioServicesPlaySystemSound(_gameOverSound);
}

- (void)drawAction:(NTSAction*)action animate:(BOOL)animate draggable:(BOOL)draggable {
  for (NTSCommand* command in (id<NSFastEnumeration>)[action getCommandList]) {
    [self drawCommand:command
         playerNumber:[action getPlayerNumber]
              animate:animate
            draggable:draggable];
  }
}

- (void)drawCommand:(NTSCommand*)command playerNumber:(int)playerNumber animate:(BOOL)animate
          draggable:(BOOL)draggable {
  CGRect rect = CGRectMake([command getColumn] * _squareSize,
                           [command getRow] * _squareSize + _topOffset,
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
      CGRect rect = CGRectMake(i * _squareSize,
                               _topOffset + (j * _squareSize),
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
    if (point.y < _topOffset || point.y > _topOffset + 3 * _squareSize ||
        point.x < 0 || point.x > 3 * _squareSize) {
      return; // Out of bounds
    }
    [_delegate handleSquareTapAtX:point.x / _squareSize
                              AtY:(point.y - _topOffset) / _squareSize];
  }
}

- (void)dealloc {
  AudioServicesDisposeSystemSoundID(_addCommandSound);
  AudioServicesDisposeSystemSoundID(_removeCommandSound);
  AudioServicesDisposeSystemSoundID(_submitCommandSound);
  AudioServicesDisposeSystemSoundID(_gameOverSound);
}

@end
