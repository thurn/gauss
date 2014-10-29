#import "BlockInitializer.h"

@interface BlockInitializer ()
@property(nonatomic,copy) id (^block)(id<NFUSInjector> injector);
@end

@implementation BlockInitializer

- (id)initWithBlock:(id (^)(id<NFUSInjector> injector))block {
  self = [super init];
  if (self) {
    _block = block;
  }
  return self;
}

- (id)initialize__WithNFUSInjector:(id<NFUSInjector>)injector {
  return _block(injector);
}

+ (instancetype)fromBlock:(id (^)(id <NFUSInjector> injector))block {
  return [[BlockInitializer alloc] initWithBlock:block];
}

@end
