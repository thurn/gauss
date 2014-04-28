#import "EmailInviteViewController.h"
#import <Parse/Parse.h>
#import "java/util/ArrayList.h"
#import "GameViewController.h"
#import "AppDelegate.h"

@interface EmailInviteViewController () <UITextFieldDelegate, UITextViewDelegate>
@property(weak, nonatomic) IBOutlet UITextField *toEmail;
@property(weak, nonatomic) IBOutlet UITextView *message;
@property(weak, nonatomic) IBOutlet UIButton *doneEditingButton;
@property(nonatomic) int viewDeltaY;
@property (weak, nonatomic) IBOutlet UILabel *urlLabel;
@property(weak, nonatomic) IBOutlet UIButton *sendButton;
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
  _urlLabel.text = [NSString stringWithFormat:@"http://noughts.thurn.ca/open?\nid=%@",
                    _preliminaryGameId];

  UITapGestureRecognizer *recognizer = [[UITapGestureRecognizer alloc]
                                        initWithTarget:self
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
}

- (BOOL)textFieldShouldReturn:(UITextField *)textField {
  [textField resignFirstResponder];
  return NO;
}

- (IBAction)onDoneClicked {
  [self dismissKeyboard];
}

-(void)dismissKeyboard {
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
  NSString *gameId = [model newGameWithJavaUtilList:[JavaUtilArrayList new]
                                       withNSString:_preliminaryGameId];
  NSDictionary *params = @{@"message": _message.text,
                           @"email": _toEmail.text,
                           @"gameId": gameId};
  [PFCloud callFunctionInBackground:@"emailInvite"
                     withParameters:params
                              block:^(id result, NSError *error) {
                                if (error) {
                                  [[[UIAlertView alloc]
                                    initWithTitle:@"Error"
                                    message:@"Error sending email"
                                    delegate:nil
                                    cancelButtonTitle:@"Ok"
                                    otherButtonTitles:nil] show];
                                }
                              }];
  GameViewController *gameViewController = (GameViewController*)segue.destinationViewController;
  gameViewController.currentGameId = gameId;
}

@end
