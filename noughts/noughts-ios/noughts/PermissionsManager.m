#import "PermissionsManager.h"
#import "Identifiers.h"

@interface PermissionsManager () <UIAlertViewDelegate>
@end

#define REQUESTED_PUSH_ENABLED 1
#define REQUESTED_NO_PUSH 2
#define SECONDS_IN_1_WEEK 604800

@implementation PermissionsManager
- (id)init {
  self = [super init];
  if (self) {
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(showPushEnableDialog)
                                                 name:kRequestEnablePushNotification
                                               object:nil];
  }
  return self;
}

- (void)registerForPush {
  UIApplication *application = [UIApplication sharedApplication];
  [application registerForRemoteNotificationTypes:UIRemoteNotificationTypeBadge |
   UIRemoteNotificationTypeAlert |
   UIRemoteNotificationTypeSound];
}

- (void)registerForPushIfRequested {
  if ([self isPushRequested]) {
    [self registerForPush];
  }
}

- (BOOL)isPushRequested {
  NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
  return [[userDefaults valueForKey:kEnabledNotificationsKey] intValue] == REQUESTED_PUSH_ENABLED;
}

- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex {
  NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
  if (buttonIndex == 1) {
    [self registerForPush];
    [userDefaults setObject:@(REQUESTED_PUSH_ENABLED) forKey:kEnabledNotificationsKey];
  } else {
    [userDefaults setObject:@(REQUESTED_NO_PUSH) forKey:kEnabledNotificationsKey];
    [userDefaults setObject:[NSDate date] forKey:kDeclinedPushDateKey];
  }
  [userDefaults synchronize];
}

- (void)showPushEnableDialog {
  NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
  switch ([[userDefaults valueForKey:kEnabledNotificationsKey] intValue]) {
    case REQUESTED_PUSH_ENABLED: {
      [self registerForPush];
      break;
    }
    case REQUESTED_NO_PUSH: {
      NSLog(@"req no push");
      NSDate *lastRequested = [userDefaults valueForKey:kDeclinedPushDateKey];
      // Show the prompt if it's been more than a week since the user last declined
      if ([[NSDate date] timeIntervalSinceDate:lastRequested] > SECONDS_IN_1_WEEK) {
        [self showEnablePushAlert];
      }
      break;
    }
    default: {
      [self showEnablePushAlert];
      break;
    }
  }
}

- (void)showEnablePushAlert {
  UIAlertView *alert =
      [[UIAlertView alloc] initWithTitle:@"Enable notifications?"
                                 message:@"Would you like to get notified when it's your turn?"
                                delegate:self
                       cancelButtonTitle:@"No Thanks"
                       otherButtonTitles:@"Notify Me", nil];
  [alert show];
}

+ (void)requestToEnablePushNotifications {
  [[NSNotificationCenter defaultCenter]
      postNotification: [NSNotification notificationWithName:kRequestEnablePushNotification
                                                      object:nil]];
}

@end
