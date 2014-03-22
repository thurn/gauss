#import <Foundation/Foundation.h>

// Code from StackOverflow user BadPirate (http://stackoverflow.com/users/285694/badpirate)
// http://stackoverflow.com/questions/3997976/parse-nsurl-query-property
@interface QueryParsing : NSObject
+ (NSMutableDictionary *)dictionaryFromQueryComponents:(NSURL*)url;
@end
