#import "EmailInviteViewController.h"
#import <Parse/Parse.h>
#import "GameViewController.h"
#import "AppDelegate.h"
#import "FacebookUtils.h"
#import "NotificationManager.h"
#import "Identifiers.h"
#import "JavaUtils.h"
#import "Pronoun.h"
#import "PushNotificationHandler.h"
#import "InterfaceUtils.h"

@interface EmailInviteViewController () <UITextFieldDelegate, UITextViewDelegate>
@property(weak, nonatomic) IBOutlet UITextField *toEmail;
@property(weak, nonatomic) IBOutlet UITextView *message;
@property(weak, nonatomic) IBOutlet UIButton *doneEditingButton;
@property(nonatomic) int viewDeltaY;
@property(weak, nonatomic) IBOutlet UILabel *urlLabel;
@property(weak, nonatomic) IBOutlet UIButton *sendButton;
@property(strong, nonatomic) NTSProfile *facebookProfile;
@property(strong,nonatomic) PushNotificationHandler* pushHandler;
@end

@implementation EmailInviteViewController

- (void)viewDidLoad {
  [super viewDidLoad];
  _message.layer.borderColor = [[UIColor colorWithRed:230.0/255.0
                                                    green:230.0/255.0
                                                     blue:230.0/255.0
                                                    alpha:1.0] CGColor];
  _message.layer.borderWidth = 1;
  _message.layer.cornerRadius = 5;
  _urlLabel.text = [NSString stringWithFormat:@"http://noughts.firebaseapp.com/open?\nid=%@",
                    _preliminaryGameId];

  UITapGestureRecognizer *recognizer =
      [[UITapGestureRecognizer alloc] initWithTarget:self
                                              action:@selector(dismissKeyboard)];
  [[NSNotificationCenter defaultCenter] addObserver:self
                                           selector:@selector(keyboardWillBeShown:)
                                               name:UIKeyboardWillShowNotification
                                             object:nil];
  
  [[NSNotificationCenter defaultCenter] addObserver:self
                                           selector:@selector(keyboardWillBeHidden:)
                                               name:UIKeyboardWillHideNotification
                                             object:nil];
  [self.view addGestureRecognizer:recognizer];
  
  if ([FacebookUtils isFacebookUser]) {
    [[NotificationManager getInstance] loadValueForNotification:kFacebookProfileLoadedNotification
                                                      withBlock:^(id notificationObject) {
                                                          _facebookProfile = notificationObject;
                                                      }];
  }
  _pushHandler = [PushNotificationHandler new];  
}

- (void)viewWillAppear:(BOOL)animated {
  [_pushHandler registerHandler];  
}

- (void)viewWillDisappear:(BOOL)animated {
  [_pushHandler unregisterHandler];
}

- (BOOL)textFieldShouldReturn:(UITextField *)textField {
  [textField resignFirstResponder];
  return NO;
}

- (IBAction)onDoneClicked {
  [self dismissKeyboard];
}

- (void)dismissKeyboard {
  [_message resignFirstResponder];
  [_toEmail resignFirstResponder];
}

- (void)keyboardWillBeShown:(NSNotification*)aNotification {
  if ([_message isFirstResponder]) {
    _viewDeltaY = [self viewAnimationDelta];
    [self animateViewByDeltaY:-_viewDeltaY];
    [UIView animateWithDuration:0.3 animations:^{
        _doneEditingButton.alpha = 1.0;
    }];
  } else {
    _viewDeltaY = 0;
  }
}

- (void)keyboardWillBeHidden:(NSNotification*)aNotification {
  [self animateViewByDeltaY:_viewDeltaY];
  [UIView animateWithDuration:0.1 animations:^{
      _doneEditingButton.alpha = 0.0;
  }];
}

- (void)textViewDidBeginEditing:(UITextView *)textView {
  if (_viewDeltaY == 0) {
    _viewDeltaY = [self viewAnimationDelta];
    [self animateViewByDeltaY:-_viewDeltaY];
    [UIView animateWithDuration:0.3 animations:^{
        _doneEditingButton.alpha = 1.0;
    }];
  }
}

- (void)textFieldDidEndEditing:(UITextField *)textField {
  _sendButton.enabled = [_toEmail.text rangeOfString:@"@"].location != NSNotFound;
}

- (int)viewAnimationDelta {
  UIInterfaceOrientation orientation = [UIApplication sharedApplication].statusBarOrientation;
  if (UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPad &&
      UIInterfaceOrientationIsLandscape(orientation)) {
    return 160;
  } else {
    return 0;
  }
}

- (void)animateViewByDeltaY:(int)deltaY {
  [UIView beginAnimations:nil context:NULL];
  [UIView setAnimationDuration:0.25];
  CGPoint center = self.view.center;
  self.view.center = CGPointMake(center.x, center.y + deltaY);
  [UIView commitAnimations];
}

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
  NTSModel *model = [AppDelegate getModel];
  NSArray *profiles;
  NTSProfile_Builder *opponentProfile = [NTSProfile newBuilder];
  NSRange range = [_toEmail.text rangeOfString:@"@"];
  NSString *opponentName = [_toEmail.text substringToIndex:range.location];
  [opponentProfile setNameWithNSString:opponentName];
  [opponentProfile setPronounWithNTSPronounEnum:[NTSPronounEnum NEUTRAL]];
  if (_facebookProfile) {
    profiles = @[_facebookProfile, [opponentProfile build]];
  } else {
    NTSProfile_Builder *viewerProfile = [NTSProfile newBuilder];
    [viewerProfile setPronounWithNTSPronounEnum:[NTSPronounEnum NEUTRAL]];
    profiles = @[[viewerProfile build], [opponentProfile build]];
  }
  NSString *gameId = [model newGameWithJavaUtilList:[JavaUtils nsArrayToJavaUtilList:profiles]
                                       withNSString:_preliminaryGameId];
  NSDictionary *params = @{@"message" : _message.text,
                           @"email"   : _toEmail.text,
                           @"gameId"  : gameId};
  [PFCloud callFunctionInBackground:@"emailInvite"
                     withParameters:params
                              block:^(id result, NSError *error) {
                                  if (error) {
                                    [InterfaceUtils error:@"Error sending email"];
                                  }
                              }];
  GameViewController *gameViewController = (GameViewController*)segue.destinationViewController;
  gameViewController.currentGameId = gameId;
}

@end
