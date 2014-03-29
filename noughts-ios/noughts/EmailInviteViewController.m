#import "EmailInviteViewController.h"

@interface EmailInviteViewController () <UITextFieldDelegate, UITextViewDelegate>
@property(strong,nonatomic) NTSModel *model;
@property(weak, nonatomic) IBOutlet UITextField *toEmail;
@property(weak, nonatomic) IBOutlet UITextView *message;
@property(weak, nonatomic) IBOutlet UIButton *doneEditingButton;
@property(nonatomic) int viewDeltaY;
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
  
  _message.text = [NSString stringWithFormat: @"Invitation to play noughts\n\n"
      @"You've been invited to a game of noughts! To accept this invitation, go here:\n\n"
      @"http://noughts.thurn.ca/open?id=%@", _preliminaryGameId];
  
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

- (void)setNTSModel:(NTSModel *)model {
  _model = model;
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

- (void)textViewDidBeginEditing:(UITextField *)textField {
  if (_viewDeltaY == 0) {
    _viewDeltaY = [self viewAnimationDelta];
    [self animateViewByDeltaY:-_viewDeltaY];
    [UIView animateWithDuration:0.3 animations:^{
      _doneEditingButton.alpha = 1.0;
    }];
  }
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

- (IBAction)onSendClicked {
}

@end
