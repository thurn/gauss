#import "InterfaceUtils.h"

@implementation InterfaceUtils
+ (UIStoryboard*)mainStoryboard {
  NSString *storyboardName = UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPad ?
      @"Main_iPad" : @"Main_iPhone";
  return [UIStoryboard storyboardWithName:storyboardName bundle: nil];
}

+ (void)error:(NSString*)message {
#ifdef DEBUG
  NSLog(@"%@", message);
  @throw message;
#else
  UIAlertView *alert = [[UIAlertView alloc]
                        initWithTitle:@"Error"
                        message:message
                        delegate:self
                        cancelButtonTitle:@"Ok"
                        otherButtonTitles:nil];
  [alert show];
#endif
}
@end
