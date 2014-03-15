#import "NewLocalGameViewController.h"
#import "GameViewController.h"
#import "J2obcUtils.h"
#import "Profile.h"
#import "ImageString.h"
#import "ImageType.h"

NSString *const kP1LocalNameKey = @"kP1LocalNameKey";
NSString *const kP2LocalNameKey = @"kP2LocalNameKey";
NSString *const kSawTutorialKey = @"kSawTutorialKey";

@interface NewLocalGameViewController () <UITextFieldDelegate,
                                          UIPickerViewDataSource,
                                          UIPickerViewDelegate>
@property (weak, nonatomic) IBOutlet UITextField *p1TextField;
@property (weak, nonatomic) IBOutlet UITextField *p2TextField;
@property (weak, nonatomic) IBOutlet UIButton *p1Image;
@property (weak, nonatomic) IBOutlet UIButton *p2Image;
@property (weak, nonatomic) IBOutlet UIPickerView *difficultyPicker;
@property (weak, nonatomic) NTSModel *model;
@property (strong, nonatomic) NSArray *playerImages;
@property (strong, nonatomic) NSArray *computerImages;
@property (nonatomic) int p1ImageIndex;
@property (nonatomic) int p2ImageIndex;
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
  if (_playVsComputerMode) {
    _p2ImageIndex = 0;
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
  UIImage *image1 = [UIImage imageNamed:[_playerImages objectAtIndex:_p1ImageIndex]];
  [_p1Image setImage:image1 forState:UIControlStateNormal];
  UIImage *image2;
  if (_playVsComputerMode) {
    image2 = [UIImage imageNamed:[_computerImages objectAtIndex:_p2ImageIndex]];
  } else {
    image2 = [UIImage imageNamed:[_playerImages objectAtIndex:_p2ImageIndex]];
  }
  [_p2Image setImage:image2 forState:UIControlStateNormal];
  
  NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
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
}

- (void)animateViewByDeltaY:(int)deltaY {
  [UIView beginAnimations:nil context:NULL];
  [UIView setAnimationDuration:0.25];
  CGPoint center = self.view.center;
  self.view.center = CGPointMake(center.x, center.y + deltaY);
  [UIView commitAnimations];
}

- (void)keyboardWillBeShown:(NSNotification*)aNotification
{
  [self animateViewByDeltaY:-100];
}

- (void)keyboardWillBeHidden:(NSNotification*)aNotification
{
  [self animateViewByDeltaY:100];
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
  UIImage *image = [UIImage imageNamed:[_playerImages objectAtIndex:_p1ImageIndex]];
  [sender setImage:image forState:UIControlStateNormal];
}

- (IBAction)onP2ImageClicked:(UIButton *)sender {
  _p2ImageIndex = (_p2ImageIndex + 1) % [_playerImages count];
  if (_p1ImageIndex == _p2ImageIndex) {
    _p2ImageIndex = (_p2ImageIndex + 1) % [_playerImages count];
  }
  UIImage *image = [UIImage imageNamed:[_playerImages objectAtIndex:_p2ImageIndex]];
  [sender setImage:image forState:UIControlStateNormal];
}

- (BOOL)textFieldShouldReturn:(UITextField *)textField {
  [textField resignFirstResponder];
  return NO;
}

-(void)setNTSModel:(NTSModel *)model {
  _model = model;
}

- (NSInteger)numberOfComponentsInPickerView:(UIPickerView *)pickerView {
  return 1;
}

- (NSInteger)pickerView:(UIPickerView *)pickerView numberOfRowsInComponent:(NSInteger)component {
  return 3;
}

- (UIView*)pickerView:(UIPickerView *)pickerView
           viewForRow:(NSInteger)row
         forComponent:(NSInteger)component
          reusingView:(UIView *)view {
  UILabel* label = (UILabel*)view;
  if (!label) {
    label = [[UILabel alloc] init];
    [label setFont:[UIFont systemFontOfSize:14]];
  }
  label.text = [self nameForDifficultyLevel:row];
  return label;
}

- (CGFloat)pickerView:(UIPickerView *)pickerView rowHeightForComponent:(NSInteger)component {
  return 25.0;
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
      @throw @"Unknown difficulty level";
    }
  }
}

- (void)pickerView:(UIPickerView *)pickerView
      didSelectRow:(NSInteger)row
       inComponent:(NSInteger)component {
  _p2ImageIndex = row;
  UIImage *image = [UIImage imageNamed:[_computerImages objectAtIndex:_p2ImageIndex]];
  // Button is disabled for vs. computer
  [_p2Image setImage:image forState:UIControlStateDisabled];
}

- (BOOL)isAllWhitespace:(NSString*)string {
  NSCharacterSet *whitespace = [NSCharacterSet whitespaceAndNewlineCharacterSet];
  return [[string stringByTrimmingCharactersInSet:whitespace] length] == 0;
}

- (NTSImageString*)localImageString:(NSString*)name {
  return [[[[NTSImageString newBuilder]
            setStringWithNSString:name]
           setTypeWithNTSImageTypeEnum:[NTSImageTypeEnum LOCAL]]
          build];
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
  [userDefaults synchronize];
  
  NTSProfile_Builder *p1Profile = [NTSProfile newBuilder];
  [p1Profile setNameWithNSString:p1Name];
  [p1Profile setImageStringWithNTSImageString:
   [self localImageString:[_playerImages objectAtIndex:_p1ImageIndex]]];
  NTSProfile_Builder *p2Profile = [NTSProfile newBuilder];
  if (_playVsComputerMode) {
    int difficultyLevel = [_difficultyPicker selectedRowInComponent:0];
    [p2Profile setNameWithNSString:[self nameForDifficultyLevel:difficultyLevel]];
    [p2Profile setImageStringWithNTSImageString:
     [self localImageString:[_computerImages objectAtIndex:difficultyLevel]]];
    [p2Profile setIsComputerPlayerWithBoolean:YES];
    [p2Profile setComputerDifficultyLevelWithInt:difficultyLevel];
  } else {
    [p2Profile setNameWithNSString:p2Name];
    [p2Profile setImageStringWithNTSImageString:
     [self localImageString:[_playerImages objectAtIndex:_p2ImageIndex]]];
  }

   NSString *gameId = [_model newLocalMultiplayerGameWithJavaUtilList:
                       [J2obcUtils nsArrayToJavaUtilList:@[[p1Profile build], [p2Profile build]]]];
  [destination setNTSModel:_model];
  id sawTutorial = [userDefaults objectForKey:kSawTutorialKey];
  if (sawTutorial == nil) {
    destination.tutorialMode = YES;
  }
  destination.currentGameId = gameId;
}

@end
