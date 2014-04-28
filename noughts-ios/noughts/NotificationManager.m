#import "NotificationManager.h"
#import "Identifiers.h"

static NotificationManager* gInstance;

@interface NotificationManager ()
@property(strong, nonatomic) NSMutableDictionary* values;
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
    for (NSString* notification in notifications) {
      [self registerForNotification:notification
                          withBlock:^(id notificationObject) {
        _values[notification] = notificationObject;
      }];
    }
  }
  return self;
}

- (void)dealloc {
  [self unregisterForAllNotifications:self];
}

- (void)registerForNotification:(NSString*)notificationName
                      withBlock:(void(^)(id notificationObject))block {
  [self registerForNotification:notificationName withBlock:block withLoadingBlock:nil];
}

- (void)registerForNotification:(NSString*)notificationName
                      withBlock:(void(^)(id notificationObject))block
               withLoadingBlock:(void(^)())loading {
  // Future features:
  // 1) Show a spinner while waiting
  // 2) Time out intelligently and display an error.
  id value = _values[notificationName];
  if (value) {
    block(value);
  } else if (loading) {
    loading();
  }
  [[NSNotificationCenter defaultCenter]
   addObserverForName:notificationName
   object:nil
   queue:nil
   usingBlock:^(NSNotification *notification) {
     block(notification.object);
   }];
}

- (id)getLatestValueForNotification:(NSString*)notificationName {
  return _values[notificationName];
}

- (void)unregisterForAllNotifications:(id)observer {
  [[NSNotificationCenter defaultCenter] removeObserver:observer];
}

@end
