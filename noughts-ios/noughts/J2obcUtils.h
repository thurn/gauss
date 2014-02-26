//
//  J2obcUtils.h
//  noughts
//
//  Created by Derek Thurn on 2/17/14.
//  Copyright (c) 2014 Derek Thurn. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "java/util/Map.h"
#import "java/util/List.h"

@interface J2obcUtils : NSObject
+(id<JavaUtilMap>)nsDictionaryToJavaUtilMap:(NSDictionary*)dictionary;
+(id<JavaUtilList>)nsArrayToJavaUtilList:(NSArray*)array;
@end
