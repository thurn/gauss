#import "ProfilePromptViewController.h"
#import "ImageType.h"
#import "Profile.h"
#import "ImageString.h"
#import "OnMutationCompleted.h"
#import "AppDelegate.h"

NSString *const kPlayerLocalNameKey = @"kPlayerLocalNameKey";

@interface ProfilePromptViewController () <UITextFieldDelegate, NTSOnMutationCompleted>
@property(weak, nonatomic) IBOutlet UITextField *nameField;
@property(strong, nonatomic) NSArray *playerImages;
@property(nonatomic) int playerImageIndex;
@property(weak, nonatomic) IBOutlet UIButton *avatarButton;
@property(weak, nonatomic) IBOutlet UIButton *doneButton;
@property(strong, nonatomic) NSString *gameId;
@end

@implementation ProfilePromptViewController

- (id)initWithGameId:(NSString*)gameId {
  self = [super initWithNibName:@"ProfilePrompt" bundle:nil];
  if (self) {
    _gameId = gameId;
  }
  return self;
}

- (void)viewDidLoad {
  [super viewDidLoad];
  _playerImages = @[@"player_bull", @"player_chick", @"player_cow", @"player_donkey",
                    @"player_goat", @"player_goose", @"player_chicken", @"player_sheep"];
  _playerImageIndex = arc4random() % [_playerImages count];
  NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
  UIImage *avatar = [UIImage imageNamed:[_playerImages objectAtIndex:_playerImageIndex]];
  [_avatarButton setImage:avatar forState:UIControlStateNormal];
  _nameField.delegate = self;
  NSString *name = [userDefaults objectForKey:kPlayerLocalNameKey];
  [_nameField addTarget:self
                 action:@selector(textFieldDidChange)
       forControlEvents:UIControlEventEditingChanged];
  if (name) {
    _nameField.text = name;
  }
}

- (BOOL)isAllWhitespace:(NSString*)string {
  NSCharacterSet *whitespace = [NSCharacterSet whitespaceAndNewlineCharacterSet];
  return [[string stringByTrimmingCharactersInSet:whitespace] length] == 0;
}

- (void)textFieldDidChange {
  _doneButton.enabled = [_nameField.text length] > 0 && ![self isAllWhitespace:_nameField.text];
}

- (BOOL)textFieldShouldReturn:(UITextField *)textField {
  [textField resignFirstResponder];
  return NO;
}

- (IBAction)onAvatarClicked:(id)sender {
  _playerImageIndex = (_playerImageIndex + 1) % [_playerImages count];
  UIImage *image = [UIImage imageNamed:[_playerImages objectAtIndex:_playerImageIndex]];
  [sender setImage:image forState:UIControlStateNormal];
}

- (IBAction)onDoneClicked:(id)sender {
  NTSModel *model = [AppDelegate getModel];
  NTSImageString_Builder *imageString = [NTSImageString newBuilder];
  [imageString setStringWithNSString:[_playerImages objectAtIndex:_playerImageIndex]];
  [imageString setTypeWithNTSImageTypeEnum:[NTSImageTypeEnum LOCAL]];
  NTSProfile_Builder *profile = [NTSProfile newBuilder];
  [profile setNameWithNSString:_nameField.text];
  [profile setImageStringWithNTSImageString:[imageString build]];
  [model setProfileForViewerWithNSString:_gameId
                          withNTSProfile:[profile build]
              withNTSOnMutationCompleted:self];
}

- (void)onMutationCompletedWithNTSGame:(NTSGame *)game {
  [self dismissViewControllerAnimated:YES completion:nil];
}

- (IBAction)onSignInClicked:(id)sender {
}

@end
