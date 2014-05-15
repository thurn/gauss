#import "NewLocalGameViewController.h"
#import "GameViewController.h"
#import "JavaUtils.h"
#import "Profile.h"
#import "ImageString.h"
#import "ImageType.h"
#import "AppDelegate.h"
#import "Identifiers.h"
#import "PushNotificationHandler.h"
#import "InterfaceUtils.h"
#import "ImageStringUtils.h"


#define kPadPickerRowHeight 40.0
#define kPhonePickerRowHeight 25.0
#define kPadPickerFontSize 24
#define kPhonePickerFontSize 14
#define kPickerRowCount 3
#define kPickerComponentCount 1
#define kKeyboardViewOffset 100
#define kAvatarSize 100
#define kKeyboardAnimationDuration 0.25

@interface NewLocalGameViewController () <UITextFieldDelegate,
                                          UIPickerViewDataSource,
                                          UIPickerViewDelegate>
@property(weak, nonatomic) IBOutlet UITextField *p1TextField;
@property(weak, nonatomic) IBOutlet UITextField *p2TextField;
@property(weak, nonatomic) IBOutlet UIButton *p1Image;
@property(weak, nonatomic) IBOutlet UIButton *p2Image;
@property(weak, nonatomic) IBOutlet UIPickerView *difficultyPicker;
@property(strong, nonatomic) NSArray *playerImages;
@property(strong, nonatomic) NSArray *computerImages;
@property(nonatomic) int p1ImageIndex;
@property(nonatomic) int p2ImageIndex;
@property(strong,nonatomic) PushNotificationHandler* pushHandler;
@end

@implementation NewLocalGameViewController

-(void)awakeFromNib {
  _playerImages = @[@"player_bull", @"player_chick", @"player_cow", @"player_donkey",
                      @"player_goat", @"player_goose", @"player_chicken", @"player_sheep"];
  _computerImages = @[@"computer_easy", @"computer_medium", @"computer_hard"];
  _p1ImageIndex = arc4random() % [_playerImages count];
  _p2ImageIndex = arc4random() % [_playerImages count];
  while (_p1ImageIndex == _p2ImageIndex) {
    _p2ImageIndex = rand() % [_playerImages count];
  }
}

- (void)viewDidLoad {
  NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
  if (_playVsComputerMode) {
    NSNumber *number = [userDefaults objectForKey:kPreferredDifficultyKey];
    if (!number) {
      number = @0;
    }
    _p2ImageIndex = [number intValue];
    [_difficultyPicker selectRow:_p2ImageIndex inComponent:0 animated:YES];
  }
  [[NSNotificationCenter defaultCenter] addObserver:self
                                           selector:@selector(keyboardWillBeShown:)
                                               name:UIKeyboardWillShowNotification
                                             object:nil];
  [[NSNotificationCenter defaultCenter] addObserver:self
                                           selector:@selector(keyboardWillBeHidden:)
                                               name:UIKeyboardWillHideNotification
                                             object:nil];
  UIImage *image1 = [ImageStringUtils getLocalImage:[_playerImages objectAtIndex:_p1ImageIndex]
                                               size:kAvatarSize];
  [_p1Image setImage:image1 forState:UIControlStateNormal];
  UIImage *image2;
  if (_playVsComputerMode) {
    image2 = [ImageStringUtils getLocalImage:[_computerImages objectAtIndex:_p2ImageIndex]
                                        size:kAvatarSize];
  } else {
    image2 = [ImageStringUtils getLocalImage:[_playerImages objectAtIndex:_p2ImageIndex]
                                        size:kAvatarSize];
  }
  [_p2Image setImage:image2 forState:UIControlStateNormal];
  
  NSString *p1Name = [userDefaults objectForKey:kP1LocalNameKey];
  if (!p1Name) {
    p1Name = @"Player 1";
  }
  _p1TextField.placeholder = p1Name;
  _p1TextField.text = p1Name;
  
  NSString *p2Name = [userDefaults objectForKey:kP2LocalNameKey];
  if (!p2Name) {
    p2Name = @"Player 2";
  }
  _p2TextField.placeholder = p2Name;
  _p2TextField.text = p2Name;
  _pushHandler = [PushNotificationHandler new];  
}

- (void)viewWillAppear:(BOOL)animated {
  [_pushHandler registerHandler];  
}

- (void)viewWillDisappear:(BOOL)animated {
  [_pushHandler unregisterHandler];
}

// Animates the movement of the main view by the given amount in the y dimension
- (void)animateViewByDeltaY:(int)deltaY {
  [UIView beginAnimations:nil context:NULL];
  [UIView setAnimationDuration:kKeyboardAnimationDuration];
  CGPoint center = self.view.center;
  self.view.center = CGPointMake(center.x, center.y + deltaY);
  [UIView commitAnimations];
}

- (void)keyboardWillBeShown:(NSNotification*)aNotification {
  [self animateViewByDeltaY:-kKeyboardViewOffset];
}

- (void)keyboardWillBeHidden:(NSNotification*)aNotification {
  [self animateViewByDeltaY:kKeyboardViewOffset];
}

- (void)textFieldDidBeginEditing:(UITextField *)textField {
  if ([textField.text isEqualToString:textField.placeholder]) {
    textField.text = @"";
  }
}

- (void)textFieldDidEndEditing:(UITextField *)textField {
  if ([textField.text isEqualToString:@""]) {
    textField.text = textField.placeholder;
  }
}

- (IBAction)onP1ImageClicked:(UIButton *)sender {
  _p1ImageIndex = (_p1ImageIndex + 1) % [_playerImages count];
  if (_p1ImageIndex == _p2ImageIndex) {
    _p1ImageIndex = (_p1ImageIndex + 1) % [_playerImages count];
  }
  UIImage *image = [ImageStringUtils getLocalImage:[_playerImages objectAtIndex:_p1ImageIndex]
                                              size:kAvatarSize];
  [sender setImage:image forState:UIControlStateNormal];
}

- (IBAction)onP2ImageClicked:(UIButton *)sender {
  _p2ImageIndex = (_p2ImageIndex + 1) % [_playerImages count];
  if (_p1ImageIndex == _p2ImageIndex) {
    _p2ImageIndex = (_p2ImageIndex + 1) % [_playerImages count];
  }
  UIImage *image = [ImageStringUtils getLocalImage:[_playerImages objectAtIndex:_p2ImageIndex]
                                              size:kAvatarSize];
  [sender setImage:image forState:UIControlStateNormal];
}

- (BOOL)textFieldShouldReturn:(UITextField *)textField {
  [textField resignFirstResponder];
  return NO;
}

- (NSInteger)numberOfComponentsInPickerView:(UIPickerView *)pickerView {
  return kPickerComponentCount;
}

- (NSInteger)pickerView:(UIPickerView *)pickerView numberOfRowsInComponent:(NSInteger)component {
  return kPickerRowCount;
}

- (UIView*)pickerView:(UIPickerView *)pickerView
           viewForRow:(NSInteger)row
         forComponent:(NSInteger)component
          reusingView:(UIView *)view {
  UILabel* label = (UILabel*)view;
  if (!label) {
    label = [[UILabel alloc] init];
    int fontSize = UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPad ?
        kPadPickerFontSize : kPhonePickerFontSize;
    [label setFont:[UIFont systemFontOfSize:fontSize]];
  }
  label.text = [self nameForDifficultyLevel:row];
  return label;
}

- (CGFloat)pickerView:(UIPickerView *)pickerView rowHeightForComponent:(NSInteger)component {
  return UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPad ?
      kPadPickerRowHeight : kPhonePickerRowHeight;
}

- (NSString *)nameForDifficultyLevel:(NSInteger)level {
  switch (level) {
    case 0: {
      return @"Easy Computer";
    }
    case 1: {
      return @"Medium Computer";
    }
    case 2: {
      return @"Difficult Computer";
    }
    default: {
      [InterfaceUtils error:@"Unknown difficulty level"];
      return @"";
    }
  }
}

- (void)pickerView:(UIPickerView *)pickerView
      didSelectRow:(NSInteger)row
       inComponent:(NSInteger)component {
  _p2ImageIndex = (int)row;
  UIImage *image = [ImageStringUtils getLocalImage:[_computerImages objectAtIndex:_p2ImageIndex]
                                              size:kAvatarSize];
  // Button is disabled for vs. computer
  [_p2Image setImage:image forState:UIControlStateDisabled];
}

- (BOOL)isAllWhitespace:(NSString*)string {
  NSCharacterSet *whitespace = [NSCharacterSet whitespaceAndNewlineCharacterSet];
  return [[string stringByTrimmingCharactersInSet:whitespace] length] == 0;
}

- (NTSImageString*)localImageString:(NSString*)name {
  NTSImageString_Builder *result = [NTSImageString newBuilder];
  [result setStringWithNSString:name];
  [result setTypeWithNTSImageTypeEnum:[NTSImageTypeEnum LOCAL]];
  return [result build];
}

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
  GameViewController *destination = segue.destinationViewController;
  NSString *p1Name = _p1TextField.text;
  if (!p1Name || [self isAllWhitespace:p1Name]) {
    p1Name = _p1TextField.placeholder;
  }
  NSString *p2Name = _p2TextField.text;
  if (!p2Name || [self isAllWhitespace:p2Name]) {
    p2Name = _p2TextField.placeholder;
  }
  
  NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
  [userDefaults setObject:p1Name forKey:kP1LocalNameKey];
  [userDefaults setObject:p2Name forKey:kP2LocalNameKey];
  
  NTSProfile_Builder *p1Profile = [NTSProfile newBuilder];
  [p1Profile setNameWithNSString:p1Name];
  [p1Profile setImageStringWithNTSImageString:
   [self localImageString:[_playerImages objectAtIndex:_p1ImageIndex]]];
  NTSProfile_Builder *p2Profile = [NTSProfile newBuilder];
  if (_playVsComputerMode) {
    int difficultyLevel = (int)[_difficultyPicker selectedRowInComponent:0];
    [userDefaults setObject:[[NSNumber alloc] initWithInt:difficultyLevel]
                     forKey:kPreferredDifficultyKey];
    [p2Profile setNameWithNSString:[self nameForDifficultyLevel:difficultyLevel]];
    NTSImageString *imageString =
        [self localImageString:[_computerImages objectAtIndex:difficultyLevel]];
    [p2Profile setImageStringWithNTSImageString:imageString];
    [p2Profile setIsComputerPlayerWithBoolean:YES];
    [p2Profile setComputerDifficultyLevelWithInt:difficultyLevel];
  } else {
    [p2Profile setNameWithNSString:p2Name];
    NTSImageString *imageString =
        [self localImageString:[_playerImages objectAtIndex:_p2ImageIndex]];
    [p2Profile setImageStringWithNTSImageString:imageString];
  }
  NTSModel *model = [AppDelegate getModel];
  [userDefaults synchronize];
  NSArray *profiles = @[[p1Profile build], [p2Profile build]];
  NSString *gameId =
      [model newLocalMultiplayerGameWithJavaUtilList:[JavaUtils nsArrayToJavaUtilList:profiles]];
  id sawTutorial = [userDefaults objectForKey:kSawTutorialKey];
  if (sawTutorial == nil) {
    destination.tutorialMode = YES;
  }
  destination.currentGameId = gameId;
}

@end
