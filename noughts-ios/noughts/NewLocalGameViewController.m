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

@interface NewLocalGameViewController () <UITextFieldDelegate>
@property (weak, nonatomic) IBOutlet UITextField *p1TextField;
@property (weak, nonatomic) IBOutlet UITextField *p2TextField;
@property (weak, nonatomic) IBOutlet UIButton *p1Image;
@property (weak, nonatomic) IBOutlet UIButton *p2Image;
@property (weak, nonatomic) NTSModel *model;
@property (nonatomic) BOOL isEditing;
@property (strong, nonatomic) NSArray *playerImages;
@property (nonatomic) int p1ImageIndex;
@property (nonatomic) int p2ImageIndex;
@end

@implementation NewLocalGameViewController

-(void)awakeFromNib {
  self.playerImages = @[@"player_bull", @"player_chick", @"player_cow", @"player_donkey",
                        @"player_goat", @"player_goose", @"player_chicken", @"player_sheep"];
  self.p1ImageIndex = rand() % [self.playerImages count];
  self.p2ImageIndex = rand() % [self.playerImages count];
  while (self.p1ImageIndex == self.p2ImageIndex) {
    self.p2ImageIndex = rand() % [self.playerImages count];
  }
}

- (void)viewDidLoad {
  UIImage *image1 = [UIImage imageNamed:[self.playerImages objectAtIndex:self.p1ImageIndex]];
  [self.p1Image setImage:image1 forState:UIControlStateNormal];
  UIImage *image2 = [UIImage imageNamed:[self.playerImages objectAtIndex:self.p2ImageIndex]];
  [self.p2Image setImage:image2 forState:UIControlStateNormal];
}

- (void)animateViewByDeltaY:(int)deltaY {
  [UIView beginAnimations:nil context:NULL];
  [UIView setAnimationDuration:0.25];
  CGPoint center = self.view.center;
  self.view.center = CGPointMake(center.x, center.y + deltaY);
  [UIView commitAnimations];
}

- (void)textFieldDidBeginEditing:(UITextField *)textField {
  self.isEditing = true;
  [self animateViewByDeltaY:-100];

  __weak UITextField *weakSelf = textField;
  dispatch_time_t delay = dispatch_time(DISPATCH_TIME_NOW, 0.25 * NSEC_PER_SEC);
  dispatch_after(delay, dispatch_get_main_queue(), ^{
    __strong UITextField *strongSelf = weakSelf;
    UITextRange *range = [strongSelf textRangeFromPosition:strongSelf.beginningOfDocument
                                                toPosition:strongSelf.endOfDocument];
    [strongSelf setSelectedTextRange:range];
  });
}

-(void)willAnimateRotationToInterfaceOrientation:(UIInterfaceOrientation)toInterfaceOrientation
                                        duration:(NSTimeInterval)duration {
  if (self.isEditing) {
    [self animateViewByDeltaY:-100];
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

- (void)textFieldDidEndEditing:(UITextField *)textField {
  self.isEditing = false;
  [self animateViewByDeltaY:100];
}

- (BOOL)textFieldShouldReturn:(UITextField *)textField {
  [textField resignFirstResponder];
  return NO;
}

-(void)setNTSModel:(NTSModel *)model {
  self.model = model;
}

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
  GameViewController *destination = segue.destinationViewController;
  NTSProfile *p1Profile = [NTSProfile new];
  [p1Profile setNameWithNSString:self.p1TextField.text];
  [p1Profile setPhotoUrlWithNSString:[self.playerImages objectAtIndex:self.p1ImageIndex]];
  NTSProfile *p2Profile = [NTSProfile new];
  [p2Profile setNameWithNSString:self.p2TextField.text];
  [p2Profile setPhotoUrlWithNSString:[self.playerImages objectAtIndex:self.p2ImageIndex]];
  NSDictionary *localProfiles = @{@0 : p1Profile,
                                  @1 : p2Profile};
  NSString *gameId = [self.model newLocalMultiplayerGameWithJavaUtilMap:
                      [J2obcUtils nsDictionaryToJavaUtilMap:localProfiles]];
  [destination setNTSModel:self.model];
  destination.currentGameId = gameId;
}

@end
