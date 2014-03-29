#import "EmailInviteViewController.h"

@interface EmailInviteViewController () <UITextFieldDelegate, UITextViewDelegate>
@property(strong,nonatomic) NTSModel *model;
@property (weak, nonatomic) IBOutlet UITextField *toEmail;
@property (weak, nonatomic) IBOutlet UITextView *message;
@property (weak, nonatomic) IBOutlet UIButton *doneEditingButton;
@end

@implementation EmailInviteViewController

- (void)viewDidLoad {
  [super viewDidLoad];
  self.message.layer.borderColor = [[UIColor colorWithRed:230.0/255.0
                                                    green:230.0/255.0
                                                     blue:230.0/255.0
                                                    alpha:1.0] CGColor];
  self.message.layer.borderWidth = 1;
  self.message.layer.cornerRadius = 5;
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
}

- (void)keyboardWillBeShown:(NSNotification*)aNotification {
  if (![_message isFirstResponder]) {
    return;
  }
  [self animateViewByDeltaY:-[self viewAnimationDelta]];
  [UIView animateWithDuration:0.3 animations:^{
    _doneEditingButton.alpha = 1.0;
  }];
}

- (void)keyboardWillBeHidden:(NSNotification*)aNotification {
  if (![_message isFirstResponder]) {
    return;
  }
  [self animateViewByDeltaY:[self viewAnimationDelta]];
  [UIView animateWithDuration:0.1 animations:^{
    _doneEditingButton.alpha = 0.0;
  }];
}

- (int)viewAnimationDelta {
  if (UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPad) {
    UIInterfaceOrientation orientation = [UIApplication sharedApplication].statusBarOrientation;
    if (UIInterfaceOrientationIsLandscape(orientation)) {
      return 160;
    } else {
      return 0;
    }
  } else {
    return 120;
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
