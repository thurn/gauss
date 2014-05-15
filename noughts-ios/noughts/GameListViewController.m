#import "GameListViewController.h"
#import "Model.h"
#import "Game.h"
#import "Games.h"
#import "GameListPartitions.h"
#import "GameViewController.h"
#import "java/util/List.h"
#import "GameListEntry.h"
#import "GameListEntryCell.h"
#import "GameListListener.h"
#import "GameListSection.h"
#import "AppDelegate.h"
#import "IndexPath.h"
#import "ImageStringUtils.h"
#import "JavaUtils.h"
#import "PushNotificationHandler.h"

@interface GameListViewController () <UIAlertViewDelegate, NTSGameListListener>
@property(weak,nonatomic) NTSModel *model;
@property(strong,nonatomic) NTSGameListPartitions *gameListPartitions;
@property(nonatomic) float scale;
@property(strong,nonatomic) PushNotificationHandler* pushHandler;
@end

#define kYourGamesSection 0
#define kTheirGamesSection 1
#define kGameOverSection 2
#define kPadRowHeight 80
#define kPhoneRowHeight 50
#define kNumberOfSections 3

@implementation GameListViewController

- (void)viewDidLoad {
  [super viewDidLoad];
  self.navigationItem.rightBarButtonItem = self.editButtonItem;
  _scale = UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPad ? 2 : 1;
  self.tableView.rowHeight =
      UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPad ? kPadRowHeight : kPhoneRowHeight;
  _pushHandler = [PushNotificationHandler new];
}

- (void)viewWillAppear:(BOOL)animated {
  _model = [AppDelegate getModel];
  [_model setGameListListenerWithNTSGameListListener:self];
  _gameListPartitions = [_model getGameListPartitions];
  [self.tableView reloadData];
  [_pushHandler registerHandler];  
}

- (void)viewWillDisappear:(BOOL)animated {
  [_pushHandler unregisterHandler];
}

-(void)onGameAddedWithNTSGame:(NTSGame*)game {
  if (self.isViewLoaded && self.view.window) {
    if ([_gameListPartitions isGameInCorrectSectionWithNTSGame:game]) {
      _gameListPartitions = [_model getGameListPartitions];
    } else {
      [self.tableView beginUpdates];
      int section = [[_gameListPartitions getSectionWithNTSGame:game] ordinal];
      NSIndexPath *path = [NSIndexPath indexPathForItem:0 inSection:section];
      [self.tableView insertRowsAtIndexPaths:@[path]
                            withRowAnimation:UITableViewRowAnimationFade];
      _gameListPartitions = [_model getGameListPartitions];
      [self.tableView endUpdates];
    }
  }
}

- (void)onGameChangedWithNTSGame:(NTSGame *)game {
  NTSIndexPath *indexPath = [_gameListPartitions getGameIndexPathWithNSString:[game getId]];
  int newSection = [[_gameListPartitions getSectionWithNTSGame:game] ordinal];
  if ([indexPath getSection] != newSection && [indexPath getSection] != -1) {
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
  [self.tableView deleteRowsAtIndexPaths:@[[NSIndexPath indexPathForItem:[indexPath getRow]
                                                               inSection:[indexPath getSection]]]
                        withRowAnimation:UITableViewRowAnimationFade];
  _gameListPartitions = [_model getGameListPartitions];
  [self.tableView endUpdates];
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
  return kNumberOfSections;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
  return [[self listForSectionNumber:section] size];
}

- (id<JavaUtilList>)listForSectionNumber:(NSInteger)section {
  switch (section) {
    case kYourGamesSection:
      return [self.gameListPartitions yourTurn];
    case kTheirGamesSection:
      return [self.gameListPartitions theirTurn];
    case kGameOverSection:
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
  
  NSArray *imageList = [JavaUtils javaUtilListToNsArray:[listEntry getImageStringList]];
  [ImageStringUtils setImageForList:cell.vsImage imageList:imageList];
  return cell;
}

- (NSString *)tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section {
  switch (section) {
    case kYourGamesSection:
      return @"Games - Your Turn";
    case kTheirGamesSection:
      return @"Games - Their Turn";
    case kGameOverSection:
      return @"Games - Game Over";
  }
  return nil;
}

- (NSString *)tableView:(UITableView *)tableView
    titleForDeleteConfirmationButtonForRowAtIndexPath:(NSIndexPath *)indexPath {
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
