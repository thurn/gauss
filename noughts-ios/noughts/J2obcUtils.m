//
//  J2obcUtils.m
//  noughts
//
//  Created by Derek Thurn on 2/17/14.
//  Copyright (c) 2014 Derek Thurn. All rights reserved.
//

#import "J2obcUtils.h"
#import "java/util/HashMap.h"
#import "java/util/ArrayList.h"

@implementation J2obcUtils
+(id<JavaUtilMap>)nsDictionaryToJavaUtilMap:(NSDictionary*)dictionary {
  id<JavaUtilMap> result = [[JavaUtilHashMap alloc] init];
  for (id key in dictionary) {
    [result putWithId:key withId:[dictionary objectForKey:key]];
  }
  return result;
}

+(id<JavaUtilList>)nsArrayToJavaUtilList:(NSArray*)array {
  id<JavaUtilList> result = [[JavaUtilArrayList alloc] init];
  for (id value in array) {
    [result addWithId:value];
  }
  return result;
}
@end
