#import <Foundation/Foundation.h>

#import "Initializer.h"
#import "Injector.h"

@interface BlockInitializer : NSObject <NFUSInitializer>

- (id)initWithBlock:(id (^)(id <NFUSInjector> injector))block;

- (id)initialize__WithNFUSInjector:(id<NFUSInjector>)injector;

+ (instancetype)fromBlock:(id (^)(id <NFUSInjector> injector))block;

@end
