#import <Foundation/Foundation.h>
#import "ImageString.h"

@interface ImageStringUtils : NSObject
+ (void)setImage:(UIImageView*)imageView
     imageString:(NTSImageString*)imageString
            size:(int)size;
+ (UIImage*)getLocalImage:(NSString*)name size:(int)size;
+ (void)setImageForList:(UIImageView*)imageView imageList:(NSArray*)imageList;
@end
