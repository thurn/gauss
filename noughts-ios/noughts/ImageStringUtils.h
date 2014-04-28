#import <Foundation/Foundation.h>
#import "ImageString.h"

@interface ImageStringUtils : NSObject
+ (void)setLargeImage:(UIImageView*)imageView imageString:(NTSImageString*)imageString;

+ (void)setImageForList:(UIImageView*)imageView
              imageList:(NSArray*)imageList
              withScale:(float)scale;
@end
