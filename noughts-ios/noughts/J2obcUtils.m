#import "J2obcUtils.h"
#import "java/util/HashMap.h"
#import "java/util/ArrayList.h"

@implementation J2obcUtils
+ (id<JavaUtilMap>)nsDictionaryToJavaUtilMap:(NSDictionary*)dictionary {
  id<JavaUtilMap> result = [[JavaUtilHashMap alloc] init];
  for (id key in dictionary) {
    [result putWithId:key withId:[dictionary objectForKey:key]];
  }
  return result;
}

+ (id<JavaUtilList>)nsArrayToJavaUtilList:(NSArray*)array {
  id<JavaUtilList> result = [[JavaUtilArrayList alloc] init];
  for (id value in array) {
    [result addWithId:value];
  }
  return result;
}

+ (NSArray*)javaUtilListToNsArray:(id<JavaUtilList>)list {
  NSMutableArray *result = [NSMutableArray new];
  for (id value in list) {
    [result addObject:value];
  }
  return result;
}

+ (NSDictionary*)javaUtilMapToNsDictionary:(id<JavaUtilMap>)map {
  NSMutableDictionary *result = [NSMutableDictionary new];
  for (id<JavaUtilMap_Entry> entry in [map entrySet]) {
    [result setObject:[entry getValue] forKey:[entry getKey]];
  }
  return result;
}
@end
