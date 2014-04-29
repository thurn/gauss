#import <Foundation/Foundation.h>
#import "java/util/Map.h"
#import "java/util/List.h"

@interface JavaUtils : NSObject
+ (id<JavaUtilMap>)nsDictionaryToJavaUtilMap:(NSDictionary*)dictionary;
+ (id<JavaUtilList>)nsArrayToJavaUtilList:(NSArray*)array;
+ (NSArray*)javaUtilListToNsArray:(id<JavaUtilList>)list;
+ (NSDictionary*)javaUtilMapToNsDictionary:(id<JavaUtilMap>)map;
@end
