#import <Foundation/Foundation.h>
#import "ImageString.h"

@interface ImageStringUtils : NSObject
// Assign an image of the provided size to |imageView| from |imageString|
+ (void)setImage:(UIImageView*)imageView
     imageString:(NTSImageString*)imageString
            size:(int)size;

// Return the local image with the provided name and size
+ (UIImage*)getLocalImage:(NSString*)name size:(int)size;

// Sets an image in |imageView| based on all of the NTSImageStrings present in |imageList|
+ (void)setImageForList:(UIImageView*)imageView imageList:(NSArray*)imageList;
@end
