#import "ImageStringUtils.h"
#import "ImageType.h"
#import <SDWebImage/UIImageView+WebCache.h>
#import "InterfaceUtils.h"

@implementation ImageStringUtils
+ (void)setImage:(UIImageView*)imageView
     imageString:(NTSImageString*)imageString
            size:(int)size {
  if ([[imageString getType] isEqual:[NTSImageTypeEnum LOCAL]]) {
    NSString *imageName = [self localString:[imageString getString] size:size];
    imageView.image = [UIImage imageNamed:imageName];
  } else if ([[imageString getType] isEqual:[NTSImageTypeEnum FACEBOOK]]) {
    int scale = [self getScale];
    NSString *imageUrl = [NSString stringWithFormat:@"%@?width=%d&height=%d",
                          [imageString getString],
                          scale * size,
                          scale * size];
    [imageView setImageWithURL:[NSURL URLWithString:imageUrl]
              placeholderImage:[UIImage imageNamed:@"profile_placeholder"]];
  } else {
    [InterfaceUtils error:[NSString stringWithFormat:@"Unknown image type: %@",
                           [imageString getType]]];
  }
}

+ (UIImage*)getLocalImage:(NSString*)name size:(int)size {
  return [UIImage imageNamed:[self localString:name size:size]];
}

+ (NSString*)localString:(NSString*)string size:(int)size {
  NSString *sizeString = [NSString stringWithFormat:@"_%d", size];
  return [string stringByAppendingString:sizeString];
}

+ (int)getScale {
  int result = (int)[UIScreen mainScreen].scale;
  if (UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPad) {
    result *= 2;
  }
  return result;
}

+ (void)setImageForList:(UIImageView*)imageView
              imageList:(NSArray*)imageList {
  if ([imageList count] == 2) {
    imageView.image = [self imageForTwoPhotos:imageList];
  } else if ([imageList count] == 1) {
    NTSImageString *imageString = imageList[0];
    [self setImage:imageView imageString:imageString size:40];
  } else {
    [InterfaceUtils error:@"Can't render image list"];
  }
}


+ (UIImage*)imageForList:(NSArray*)imageList index:(int)index {
  NTSImageString *imageString = imageList[index];
  if ([[imageString getType] isEqual:[NTSImageTypeEnum FACEBOOK]]) {
    [InterfaceUtils error:@"Can't make a composite image from a facebook profile"];
  }
  return [self getLocalImage:[imageString getString] size:20];
}

+ (UIImage*)imageForTwoPhotos:(NSArray*)imageList {
  int scale = UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPad ? 2 : 1;
  UIImage *image1 = [self imageForList:imageList index:0];
  UIImage *image2 = [self imageForList:imageList index:1];
  UIGraphicsBeginImageContextWithOptions(CGSizeMake(40 * scale, 40 * scale), NO, 0);
  [image1 drawAtPoint:CGPointMake(0, 10 * scale)];
  [image2 drawAtPoint:CGPointMake(20 * scale, 10 * scale)];
  UIImage *result = UIGraphicsGetImageFromCurrentImageContext();
  UIGraphicsEndImageContext();
  return result;
}
@end
