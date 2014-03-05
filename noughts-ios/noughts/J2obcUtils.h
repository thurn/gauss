#import <Foundation/Foundation.h>
#import "java/util/Map.h"
#import "java/util/List.h"

@interface J2obcUtils : NSObject
+(id<JavaUtilMap>)nsDictionaryToJavaUtilMap:(NSDictionary*)dictionary;
+(id<JavaUtilList>)nsArrayToJavaUtilList:(NSArray*)array;
@end
