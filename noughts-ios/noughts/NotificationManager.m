#import "NotificationManager.h"
#import "Identifiers.h"

static NotificationManager* gInstance;

@interface NotificationManager ()
@property(strong, nonatomic) NSMutableDictionary *values;
@property(strong, nonatomic) NSMutableDictionary *blocks;
@end

@implementation NotificationManager

+ (void)initializeWithNotifications:(NSArray*)notifications {
  @synchronized(self) {
    if (!gInstance) {
      gInstance = [[NotificationManager alloc] initWithNotifications:notifications];
    }
  }
}

+ (NotificationManager*)getInstance {
  return gInstance;
}

- (id)initWithNotifications:(NSArray*)notifications {
  self = [super init];
  if (self) {
    _values = [NSMutableDictionary new];
    _blocks = [NSMutableDictionary new];
    for (NSString* notification in notifications) {
      [[NSNotificationCenter defaultCenter]
       addObserver:self
       selector:@selector(notificationReceived:)
       name:notification
       object:nil];
    }
  }
  return self;
}

- (void)notificationReceived:(NSNotification*)notification {
  _values[notification.name] = notification.object;
  if (_blocks[notification.name]) {
    for(void(^block)(id) in _blocks[notification.name]) {
      block(notification.object);
    }
    [_blocks[notification.name] removeAllObjects];
  }
}

- (void)loadValueForNotification:(NSString*)notificationName
                       withBlock:(void(^)(id notificationObject))block {
  if (_values[notificationName]) {
    block(_values[notificationName]);
  } else {
    if (!_blocks[notificationName]) {
      _blocks[notificationName] = [NSMutableArray new];
    }
    [_blocks[notificationName] addObject:block];
  }
}

- (void)unregisterAll {
  [_blocks removeAllObjects];
  [[NSNotificationCenter defaultCenter] removeObserver:self];
}

@end
