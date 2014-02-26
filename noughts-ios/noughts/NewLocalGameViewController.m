//
//  NewLocalGameViewController.m
//  noughts
//
//  Created by Derek Thurn on 2/14/14.
//  Copyright (c) 2014 Derek Thurn. All rights reserved.
//

#import "NewLocalGameViewController.h"
#import "GameViewController.h"
#import "J2obcUtils.h"
#import "Profile.h"
#import "ImageString.h"

NSString *const kP1LocalNameKey = @"kP1LocalNameKey";
NSString *const kP2LocalNameKey = @"kP2LocalNameKey";

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
  self.playerImages = @[@"player_bull", @"player_chick", @"player_cow", @"player_donkey",
                        @"player_goat", @"player_goose", @"player_chicken", @"player_sheep"];
  self.computerImages = @[@"computer_easy", @"computer_medium", @"computer_hard"];
  self.p1ImageIndex = arc4random() % [self.playerImages count];
  self.p2ImageIndex = arc4random() % [self.playerImages count];
  while (self.p1ImageIndex == self.p2ImageIndex) {
    self.p2ImageIndex = rand() % [self.playerImages count];
  }
}

- (void)viewDidLoad {
  if (self.playVsComputerMode) {
    self.p2ImageIndex = 0;
    [self.difficultyPicker selectRow:self.p2ImageIndex inComponent:0 animated:YES];
  }
  [[NSNotificationCenter defaultCenter] addObserver:self
                                           selector:@selector(keyboardWillBeShown:)
                                               name:UIKeyboardWillShowNotification
                                             object:nil];
  
  [[NSNotificationCenter defaultCenter] addObserver:self
                                           selector:@selector(keyboardWillBeHidden:)
                                               name:UIKeyboardWillHideNotification
                                             object:nil];
  UIImage *image1 = [UIImage imageNamed:[self.playerImages objectAtIndex:self.p1ImageIndex]];
  [self.p1Image setImage:image1 forState:UIControlStateNormal];
  UIImage *image2;
  if (self.playVsComputerMode) {
    image2 = [UIImage imageNamed:[self.computerImages objectAtIndex:self.p2ImageIndex]];
  } else {
    image2 = [UIImage imageNamed:[self.playerImages objectAtIndex:self.p2ImageIndex]];
  }
  [self.p2Image setImage:image2 forState:UIControlStateNormal];
  
  NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
  NSString *p1Name = [userDefaults objectForKey:kP1LocalNameKey];
  if (!p1Name) {
    p1Name = @"Player 1";
  }
  self.p1TextField.placeholder = p1Name;
  self.p1TextField.text = p1Name;
  
  NSString *p2Name = [userDefaults objectForKey:kP2LocalNameKey];
  if (!p2Name) {
    p2Name = @"Player 2";
  }
  self.p2TextField.placeholder = p2Name;
  self.p2TextField.text = p2Name;
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
  self.p1ImageIndex = (self.p1ImageIndex + 1) % [self.playerImages count];
  if (self.p1ImageIndex == self.p2ImageIndex) {
    self.p1ImageIndex = (self.p1ImageIndex + 1) % [self.playerImages count];
  }
  UIImage *image = [UIImage imageNamed:[self.playerImages objectAtIndex:self.p1ImageIndex]];
  [sender setImage:image forState:UIControlStateNormal];
}

- (IBAction)onP2ImageClicked:(UIButton *)sender {
  self.p2ImageIndex = (self.p2ImageIndex + 1) % [self.playerImages count];
  if (self.p1ImageIndex == self.p2ImageIndex) {
    self.p2ImageIndex = (self.p2ImageIndex + 1) % [self.playerImages count];
  }
  UIImage *image = [UIImage imageNamed:[self.playerImages objectAtIndex:self.p2ImageIndex]];
  [sender setImage:image forState:UIControlStateNormal];
}

- (BOOL)textFieldShouldReturn:(UITextField *)textField {
  [textField resignFirstResponder];
  return NO;
}

-(void)setNTSModel:(NTSModel *)model {
  self.model = model;
}

- (NSInteger)numberOfComponentsInPickerView:(UIPickerView *)pickerView {
  return 1;
}

- (NSInteger)pickerView:(UIPickerView *)pickerView numberOfRowsInComponent:(NSInteger)component {
  return 3;
}

-(UIView*)pickerView:(UIPickerView *)pickerView
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
  self.p2ImageIndex = row;
  UIImage *image = [UIImage imageNamed:[self.computerImages objectAtIndex:self.p2ImageIndex]];
  [self.p2Image setImage:image forState:UIControlStateNormal];
}

- (BOOL)isAllWhitespace:(NSString*)string {
  NSCharacterSet *whitespace = [NSCharacterSet whitespaceAndNewlineCharacterSet];
  return [[string stringByTrimmingCharactersInSet:whitespace] length] == 0;
}

- (NTSImageString*)localImageString:(NSString*)name {
  return [[NTSImageString alloc] initWithNSString:name
                 withNTSImageString_ImageTypeEnum:[NTSImageString_ImageTypeEnum LOCAL]];
}

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
  GameViewController *destination = segue.destinationViewController;
  NSString *p1Name = self.p1TextField.text;
  if (!p1Name || [self isAllWhitespace:p1Name]) {
    p1Name = self.p1TextField.placeholder;
  }
  NSString *p2Name = self.p2TextField.text;
  if (!p2Name || [self isAllWhitespace:p2Name]) {
    p2Name = self.p2TextField.placeholder;
  }
  
  NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
  [userDefaults setObject:p1Name forKey:kP1LocalNameKey];
  [userDefaults setObject:p2Name forKey:kP2LocalNameKey];
  [userDefaults synchronize];
  
  NTSProfile *p1Profile = [[NTSProfile alloc] initWithNSString:p1Name];
  [p1Profile setImageStringWithNTSImageString:
   [self localImageString: [self.playerImages objectAtIndex:self.p1ImageIndex]]];
  NTSProfile *p2Profile;
  if (self.playVsComputerMode) {
    int difficultyLevel = [self.difficultyPicker selectedRowInComponent:0];
    p2Profile = [[NTSProfile alloc] initWithNSString:[self nameForDifficultyLevel:difficultyLevel]];
    
    [p2Profile setImageStringWithNTSImageString:
     [self localImageString:[self.computerImages objectAtIndex:difficultyLevel]]];
    [p2Profile setIsComputerPlayerWithBoolean:YES];
    [p2Profile setComputerDifficultyLevelWithInt:difficultyLevel];
  } else {
    p2Profile = [[NTSProfile alloc] initWithNSString:p2Name];
    [p2Profile setImageStringWithNTSImageString:
     [self localImageString: [self.playerImages objectAtIndex:self.p2ImageIndex]]];
  }

   NSString *gameId = [self.model newLocalMultiplayerGameWithJavaUtilList:
                       [J2obcUtils nsArrayToJavaUtilList:@[p1Profile, p2Profile]]];
  [destination setNTSModel:self.model];
  destination.currentGameId = gameId;
}

@end
