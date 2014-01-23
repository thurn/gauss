//
//  HasModel.h
//  noughts
//
//  Created by Derek Thurn on 1/4/14.
//  Copyright (c) 2014 Derek Thurn. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Model.h"

@protocol HasModel <NSObject>
-(void)setNTSModel:(NTSModel *)model;
@end