#import "SettingsViewController.h"
#import "Identifiers.h"
#import "PushNotificationHandler.h"

@interface SettingsViewController ()
@property (weak, nonatomic) IBOutlet UISwitch *enableSoundsSwitch;
@property (weak, nonatomic) IBOutlet UISwitch *enableAutosubmitSwitch;
@property(strong,nonatomic) PushNotificationHandler* pushHandler;
@end

@implementation SettingsViewController

- (void)viewDidLoad {
  _pushHandler = [PushNotificationHandler new];
}

- (void)viewWillAppear:(BOOL)animated {
  NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
  _enableSoundsSwitch.on = ![[userDefaults valueForKey:kDisableSoundsKey] boolValue];
  _enableAutosubmitSwitch.on = [[userDefaults valueForKey:kEnableAutosubmitKey] boolValue];
  [_pushHandler registerHandler];
}

- (void)viewWillDisappear:(BOOL)animated {
  [_pushHandler unregisterHandler];
}

- (IBAction)soundToggled:(UISwitch *)sender {
  NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
  [userDefaults setObject:@(!sender.on) forKey:kDisableSoundsKey];
  [userDefaults synchronize];
}

- (IBAction)autosubmitToggled:(UISwitch *)sender {
  NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
  [userDefaults setObject:@(sender.on) forKey:kEnableAutosubmitKey];
  [userDefaults synchronize];
}

@end
