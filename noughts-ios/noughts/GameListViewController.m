#import "GameListViewController.h"
#import "Model.h"
#import "Game.h"
#import "Games.h"
#import "GameListPartitions.h"
#import "GameViewController.h"
#import "java/util/List.h"
#import <objc/runtime.h>
#import "ImageString.h"
#import "GameListEntry.h"
#import "GameListEntryCell.h"
#import "GameListUpdateListener.h"
#import "GameListSection.h"

@interface GameListViewController () <UIAlertViewDelegate, NTSGameListUpdateListener>
@property(weak,nonatomic) NTSModel *model;
@property(strong,nonatomic) NTSGameListPartitions *gameListPartitions;
@property(nonatomic) float scale;
@end

#define YOUR_GAMES_SECTION 0
#define THEIR_GAMES_SECTION 1
#define GAME_OVER_SECTION 2

@implementation GameListViewController

- (void)viewDidLoad {
  [super viewDidLoad];
  self.navigationItem.rightBarButtonItem = self.editButtonItem;
  _scale = UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPad ? 2 : 1;
  self.tableView.rowHeight = UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPad ? 80 : 50;
}

- (void)viewWillAppear:(BOOL)animated {
  if (self.model) {
    self.gameListPartitions = [self.model getGameListPartitions];
    [self.tableView reloadData];
  }
}

- (void)setNTSModel:(NTSModel *)model {
  self.model = model;
  [_model setGameListUpdateListenerWithNTSGameListUpdateListener:self];
  self.gameListPartitions = [self.model getGameListPartitions];
}

-(void)onGameAddedWithNTSGame:(NTSGame*)game {
  [self.tableView beginUpdates];
  int section = [self sectionNumberForGame:game withViewer:[_model getUserId]];
  NSIndexPath *path = [NSIndexPath indexPathForItem:0 inSection:section];
  [self.tableView insertRowsAtIndexPaths:@[path]
                        withRowAnimation:UITableViewRowAnimationFade];
  self.gameListPartitions = [self.model getGameListPartitions];
  [self.tableView endUpdates];
}

- (void)onGameChangedWithNTSGame:(NTSGame *)game {
  
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
  return 3;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
  return [[self listForSectionNumber:section] size];
}

- (id<JavaUtilList>)listForSectionNumber:(NSInteger)section {
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

- (int)sectionNumberForGame:(NTSGame*)game withViewer:(NSString*)viewer {
  NTSGameListSectionEnum *section =
    [NTSGameListPartitions getSectionWithNTSGame:game withNSString:viewer];
  return [section ordinal];
}

- (NTSGame *)gameForIndexPath:(NSIndexPath *)indexPath {
  id<JavaUtilList> games = [self listForSectionNumber:indexPath.section];
  return [games getWithInt:indexPath.row];
}

- (UITableViewCell *)tableView:(UITableView *)tableView
         cellForRowAtIndexPath:(NSIndexPath *)indexPath {
  static NSString *CellIdentifier = @"Cell";
  GameListEntryCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier
                                                            forIndexPath:indexPath];
  NTSGame *game = [self gameForIndexPath:indexPath];
  NTSGameListEntry *listEntry = [NTSGames gameListEntryWithNTSGame:game
                                                      withNSString:[self.model getUserId]];
  cell.vsLabel.text = [listEntry getVsString];
  cell.modifiedLabel.text = [listEntry getModifiedString];
  
  id <JavaUtilList> imageList = [listEntry getImageStringList];
  UIImage *image;
  if ([imageList size] == 2) {
    image = [self imageForTwoPhotos:imageList];
  } else {
    // not implemented;
    @throw @"remember to do this";
  }
  cell.vsImage.image = image;
  return cell;
}

- (UIImage*)imageForPhotoList:(id<JavaUtilList>)photoList index:(int)index {
  NTSImageString *imageString = [photoList getWithInt:index];
  return [UIImage imageNamed:[[imageString getString] stringByAppendingString:@"_20"]];
}

- (UIImage*)imageForTwoPhotos:(id<JavaUtilList>)photoList {
  UIImage *image1 = [self imageForPhotoList:photoList index:0];
  UIImage *image2 = [self imageForPhotoList:photoList index:1];
  UIGraphicsBeginImageContextWithOptions(CGSizeMake(40 * _scale, 40 * _scale), NO, 0);
  [image1 drawAtPoint:CGPointMake(0, 10 * _scale)];
  [image2 drawAtPoint:CGPointMake(20 * _scale, 10 * _scale)];
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

- (NSString *)tableView:(UITableView *)tableView titleForDeleteConfirmationButtonForRowAtIndexPath:
    (NSIndexPath *)indexPath {
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
     forRowAtIndexPath:(NSIndexPath *)indexPath {
  if (editingStyle == UITableViewCellEditingStyleDelete) {
    NTSGame *game = [self gameForIndexPath:indexPath];
    if ([game isGameOver]) {
      [self.model archiveGameWithNTSGame:game];
      self.gameListPartitions = [self.model getGameListPartitions];
      [tableView deleteRowsAtIndexPaths:@[indexPath] withRowAnimation:UITableViewRowAnimationFade];
    } else {
      [tableView beginUpdates];
      [self.model resignGameWithNTSGame:game];
      [tableView deleteRowsAtIndexPaths:@[indexPath] withRowAnimation:UITableViewRowAnimationBottom];
      NSIndexPath *newPath = [NSIndexPath indexPathForItem:0 inSection:GAME_OVER_SECTION];
      [tableView insertRowsAtIndexPaths:@[newPath] withRowAnimation:UITableViewRowAnimationTop];
      self.gameListPartitions = [self.model getGameListPartitions];
      [tableView endUpdates];
    }
  }
}

@end
