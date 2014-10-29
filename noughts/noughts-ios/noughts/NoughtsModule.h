#import <Foundation/Foundation.h>

#import "Module.h"

@interface NoughtsModule : NSObject <NFUSModule>
- (void)configureWithNFUSBinder:(id<NFUSBinder>)binder;
@end
