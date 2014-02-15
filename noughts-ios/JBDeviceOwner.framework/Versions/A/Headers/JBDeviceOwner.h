//
//  JBDeviceOwner.h
//  JBDeviceOwner
//
//  Created by Jake Boxer on 4/3/12.
//  Copyright (c) 2012 Jake Boxer. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "UIDevice+JBDeviceOwner.h"

@interface JBDeviceOwner : NSObject

- (id)initWithDevice:(UIDevice *)aDevice;

@property (strong, nonatomic, readonly) NSString *email;
@property (strong, nonatomic, readonly) NSString *firstName;
@property (strong, nonatomic, readonly) NSString *fullName;
@property (assign, nonatomic, readonly) BOOL hasAddressBookMatch;
@property (strong, nonatomic, readonly) NSString *lastName;
@property (strong, nonatomic, readonly) NSString *middleName;
@property (strong, nonatomic, readonly) NSString *phone;

@end
