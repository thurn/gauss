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
#import "GameListListener.h"
#import "GameListSection.h"
#import "AppDelegate.h"
#import "IndexPath.h"

@interface GameListViewController () <UIAlertViewDelegate, NTSGameListListener, OnModelLoaded>
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
  [AppDelegate registerForOnModelLoaded:self];
}

- (void)onModelLoaded:(NTSModel *)model {
  _model = model;
  [_model setGameListListenerWithNTSGameListListener:self];
  _gameListPartitions = [_model getGameListPartitions];
  [self.tableView reloadData];
}

-(void)onGameAddedWithNTSGame:(NTSGame*)game {
  NSLog(@"onGameAdded");
  if (self.isViewLoaded && self.view.window) {
    [self.tableView beginUpdates];
    int section = [[NTSGameListPartitions getSectionWithNTSGame:game
                                                  withNSString:[_model getUserId]] ordinal];
    NSIndexPath *path = [NSIndexPath indexPathForItem:0 inSection:section];
    [self.tableView insertRowsAtIndexPaths:@[path]
                          withRowAnimation:UITableViewRowAnimationFade];
    _gameListPartitions = [_model getGameListPartitions];
    [self.tableView endUpdates];
  }
}

- (void)onGameChangedWithNTSGame:(NTSGame *)game {
  NSLog(@"onGameChanged");
  NTSIndexPath *indexPath = [_gameListPartitions getGameIndexPathWithNSString:[game getId]];
  int newSection = [[NTSGameListPartitions getSectionWithNTSGame:game
                                                    withNSString:[_model getUserId]] ordinal];
  if ([indexPath getSection] != newSection && [indexPath getSection] != -1) {
    NSLog(@"moving from path %@", indexPath);
    NSLog(@"to section %d", newSection);
    [self.tableView beginUpdates];
    [self.tableView moveRowAtIndexPath:[NSIndexPath indexPathForItem:[indexPath getRow]
                                                           inSection:[indexPath getSection]]
                           toIndexPath:[NSIndexPath indexPathForItem:0
                                                           inSection:newSection]];
    _gameListPartitions = [_model getGameListPartitions];
    [self.tableView endUpdates];
  }
}

- (void)onGameRemovedWithNSString:(NSString*)gameId {
  [self.tableView beginUpdates];
  NTSIndexPath *indexPath = [_gameListPartitions getGameIndexPathWithNSString:gameId];
  [self.tableView deleteRowsAtIndexPaths:@[[NSIndexPath
                                            indexPathForItem:[indexPath getRow]
                                                   inSection:[indexPath getSection]]]
                        withRowAnimation:UITableViewRowAnimationFade];
  _gameListPartitions = [_model getGameListPartitions];
  [self.tableView endUpdates];
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

- (NTSGame *)gameForIndexPath:(NSIndexPath *)indexPath {
  id<JavaUtilList> games = [self listForSectionNumber:indexPath.section];
  return [games getWithInt:(int)indexPath.row];
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
  } else if ([imageList size] == 1) {
    NSString *imageName = [[imageList getWithInt:0] getString];
    image = [UIImage imageNamed:imageName];
  } else {
    @throw @"Can't render image list";
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
  controller.currentGameId = [game getId];
  [_model setGameListListenerWithNTSGameListListener:nil];
  [self.navigationController pushViewController:controller animated:YES];
}

- (void)tableView:(UITableView *)tableView
    commitEditingStyle:(UITableViewCellEditingStyle)editingStyle
     forRowAtIndexPath:(NSIndexPath *)indexPath {
  if (editingStyle == UITableViewCellEditingStyleDelete) {

    NTSGame *game = [self gameForIndexPath:indexPath];
    if ([game isGameOver]) {
      [_model archiveGameWithNSString:[game getId]];
      [tableView reloadRowsAtIndexPaths:[NSArray arrayWithObject:indexPath]
                       withRowAnimation:UITableViewRowAnimationLeft];
    } else {
      [_model resignGameWithNSString:[game getId]];
      [tableView reloadRowsAtIndexPaths:[NSArray arrayWithObject:indexPath]
                       withRowAnimation:UITableViewRowAnimationNone];
    }
  }
}

@end
