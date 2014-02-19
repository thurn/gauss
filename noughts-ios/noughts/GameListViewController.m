//
//  SavedGamesViewController.m
//  noughts
//
//  Created by Derek Thurn on 12/28/13.
//  Copyright (c) 2013 Derek Thurn. All rights reserved.
//

#import "GameListViewController.h"
#import "Model.h"
#import "Game.h"
#import "GameListPartitions.h"
#import "GameViewController.h"
#import "java/util/List.h"
#import "Toast+UIView.h"
#import <objc/runtime.h>

@interface GameListViewController () <UIAlertViewDelegate>
@property(weak,nonatomic) NTSModel *model;
@property(strong,nonatomic) NTSGameListPartitions *gameListPartitions;
@end

#define YOUR_GAMES_SECTION 0
#define THEIR_GAMES_SECTION 1
#define GAME_OVER_SECTION 2

@implementation GameListViewController

- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad {
  [super viewDidLoad];
  // Uncomment the following line to preserve selection between presentations.
  // self.clearsSelectionOnViewWillAppear = NO;
  // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
  self.navigationItem.rightBarButtonItem = self.editButtonItem;
}

- (void)viewWillAppear:(BOOL)animated {
  if (self.model) {
    self.gameListPartitions = [self.model getGameListPartitions];
    [self.tableView reloadData];
  }
}

- (void)setNTSModel:(NTSModel *)model {
  self.model = model;
  self.gameListPartitions = [self.model getGameListPartitions];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 3;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
  return [[self listForSectionNumber:section] size];
}

- (id<JavaUtilList>)listForSectionNumber:(NSInteger) section {
  switch (section) {
    case YOUR_GAMES_SECTION:
      return [self.gameListPartitions yourTurn];
    case THEIR_GAMES_SECTION:
      return [self.gameListPartitions theirTurn];
    case GAME_OVER_SECTION:
      return [self.gameListPartitions gameOver];
  }
  return nil;
}

- (NTSGame *)gameForIndexPath:(NSIndexPath *)indexPath {
  id<JavaUtilList> games = [self listForSectionNumber:indexPath.section];
  return [games getWithInt:indexPath.row];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
  static NSString *CellIdentifier = @"Cell";
  UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier forIndexPath:indexPath];
  NTSGame *game = [self gameForIndexPath:indexPath];
  cell.textLabel.text = [game vsStringWithNSString:[self.model getUserId]];
  cell.detailTextLabel.text = [game lastUpdatedStringWithNSString:[self.model getUserId]];
  id <JavaUtilList> photoList = [game photoListWithNSString:[self.model getUserId]];
  UIImage *image;
  if ([photoList size] == 2) {
    image = [self imageForTwoPhotos:photoList];
  } else {
    // not implemented;
    @throw @"remember to do this";
  }
  cell.imageView.image = image;
  return cell;
}

- (UIImage*)imageForTwoPhotos:(id<JavaUtilList>)photoList {
  NSString *image1Name = [[photoList getWithInt:0] stringByAppendingString:@"_20"];
  UIImage *image1 = [UIImage imageNamed:image1Name];
  NSString *image2Name = [[photoList getWithInt:1] stringByAppendingString:@"_20"];
  UIImage *image2 = [UIImage imageNamed:image2Name];
  UIGraphicsBeginImageContextWithOptions(CGSizeMake(40,40), NO, 0);
  [image1 drawAtPoint:CGPointMake(0, 10)];
  [image2 drawAtPoint:CGPointMake(20, 10)];
  UIImage *result = UIGraphicsGetImageFromCurrentImageContext();
  UIGraphicsEndImageContext();
  return result;
}

- (NSString *)tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section {
  switch (section) {
    case YOUR_GAMES_SECTION:
      return @"Games - Your Turn";
    case THEIR_GAMES_SECTION:
      return @"Games - Their Turn";
    case GAME_OVER_SECTION:
      return @"Games - Game Over";
  }
  return nil;
}

-(NSString *)tableView:(UITableView *)tableView titleForDeleteConfirmationButtonForRowAtIndexPath:(NSIndexPath *)indexPath {
  NTSGame *game = [self gameForIndexPath:indexPath];
  return [game isGameOver] ? @"Archive" : @"Resign";
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
  GameViewController *controller =
      [self.storyboard instantiateViewControllerWithIdentifier:@"GameViewController"];
  NTSGame *game = [self gameForIndexPath:indexPath];
  [controller setNTSModel:self.model];
  controller.currentGameId = [game getId];
  [self.navigationController pushViewController:controller animated:YES];
}

- (void)tableView:(UITableView *)tableView
    commitEditingStyle:(UITableViewCellEditingStyle)editingStyle
     forRowAtIndexPath:(NSIndexPath *)indexPath
{
  if (editingStyle == UITableViewCellEditingStyleDelete) {
    NTSGame *game = [self gameForIndexPath:indexPath];
    if ([game isGameOver]) {
      [self.model archiveGameWithNTSGame:game];
      self.gameListPartitions = [self.model getGameListPartitions];
      [tableView deleteRowsAtIndexPaths:@[indexPath] withRowAnimation:UITableViewRowAnimationFade];
    } else {
      [self.model resignGameWithNTSGame:game];
      self.gameListPartitions = [self.model getGameListPartitions];
      NSIndexPath *newPath = [NSIndexPath indexPathForItem:0 inSection:GAME_OVER_SECTION];
      [self.tableView moveRowAtIndexPath:indexPath toIndexPath:newPath];
      
      // Hack to hide the resign button after you click it:
      dispatch_time_t delay = dispatch_time(DISPATCH_TIME_NOW, 100 * NSEC_PER_MSEC);
      dispatch_after(delay, dispatch_get_main_queue(), ^{
        [self.tableView setEditing:NO animated:NO];
        [self.tableView setEditing:YES animated:NO];
      });
    }
  }
}

@end
