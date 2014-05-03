#import "ImageStringUtils.h"
#import "ImageType.h"
#import <SDWebImage/UIImageView+WebCache.h>
#import "InterfaceUtils.h"

@implementation ImageStringUtils
+ (void)setLargeImage:(UIImageView*)imageView imageString:(NTSImageString*)imageString {
  if ([[imageString getType] isEqual:[NTSImageTypeEnum LOCAL]]) {
    imageView.image = [UIImage imageNamed:[imageString getLargeString]];
  } else {
    [imageView setImageWithURL:[NSURL URLWithString:[imageString getLargeString]]
              placeholderImage:[UIImage imageNamed:@"profile_placeholder"]];
  }
}

+ (void)setImageForList:(UIImageView*)imageView
              imageList:(NSArray*)imageList
              withScale:(float)scale{
  if ([imageList count] == 2) {
    imageView.image = [self imageForTwoPhotos:imageList withScale:scale];
  } else if ([imageList count] == 1) {
    NTSImageString *imageString = imageList[0];
    if ([[imageString getType] isEqual:[NTSImageTypeEnum LOCAL]]) {
      imageView.image = [UIImage imageNamed:[imageString getMediumString]];
    } else {
      [imageView setImageWithURL:[NSURL URLWithString:[imageString getMediumString]]
                placeholderImage:[UIImage imageNamed:@"profile_placeholder_medium"]];
    }
  } else {
    [InterfaceUtils error:@"Can't render image list"];
  }
}


+ (UIImage*)imageForList:(NSArray*)imageList index:(int)index {
  NTSImageString *imageString = imageList[index];
  return [UIImage imageNamed:[imageString getSmallString]];
}

+ (UIImage*)imageForTwoPhotos:(NSArray*)imageList withScale:(float)scale {
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
