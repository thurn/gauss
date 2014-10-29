#import <Foundation/Foundation.h>

@interface InterfaceUtils : NSObject
// Return a reference to the iPhone or iPad storyboard as appropriate.
+ (UIStoryboard*)mainStoryboard;

// Throw an exception with an error (in development) or display an alert (in production).
+ (void)error:(NSString*)message;
@end
