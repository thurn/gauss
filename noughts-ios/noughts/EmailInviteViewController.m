#import "EmailInviteViewController.h"

@interface EmailInviteViewController () <UITextFieldDelegate, UITextViewDelegate>
@property(strong,nonatomic) NTSModel *model;
@property (weak, nonatomic) IBOutlet UITextField *toEmail;
@property (weak, nonatomic) IBOutlet UITextField *fromEmail;
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

- (void)textViewDidBeginEditing:(UITextView *)textView {
  [self animateViewByDeltaY:-120];
  [UIView animateWithDuration:0.3 animations:^{
    _doneEditingButton.alpha = 1.0;
  }];
}

- (void)textViewDidEndEditing:(UITextView *)textView {
  [self animateViewByDeltaY:120];
  [UIView animateWithDuration:0.1 animations:^{
    _doneEditingButton.alpha = 0.0;
  }];
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
