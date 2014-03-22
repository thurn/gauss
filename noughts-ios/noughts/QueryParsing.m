#import "QueryParsing.h"

@implementation QueryParsing
+ (NSString *)stringByDecodingURLFormat:(NSString*)string{
  NSString *result = [string stringByReplacingOccurrencesOfString:@"+" withString:@" "];
  result = [result stringByReplacingPercentEscapesUsingEncoding:NSUTF8StringEncoding];
  return result;
}

+ (NSMutableDictionary *)dictionaryFromQueryComponents:(NSURL*)url {
  NSString *query = [url query];
  NSMutableDictionary *queryComponents = [NSMutableDictionary dictionary];
  for(NSString *keyValuePairString in [query componentsSeparatedByString:@"&"])
  {
    NSArray *keyValuePairArray = [keyValuePairString componentsSeparatedByString:@"="];
    // Verify that there is at least one key, and at least one value.  Ignore extra = signs
    if ([keyValuePairArray count] < 2) continue;
    NSString *key = [self stringByDecodingURLFormat:[keyValuePairArray objectAtIndex:0] ];
    NSString *value = [self stringByDecodingURLFormat:[keyValuePairArray objectAtIndex:1]];
    // URL spec says that multiple values are allowed per key
    NSMutableArray *results = [queryComponents objectForKey:key];
    if(!results) // First object
    {
      results = [NSMutableArray arrayWithCapacity:1];
      [queryComponents setObject:results forKey:key];
    }
    [results addObject:value];
  }
  return queryComponents;
}
@end
