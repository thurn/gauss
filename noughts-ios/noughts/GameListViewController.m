#import "GameListViewController.h"
#import "Model.h"
#import "Game.h"
#import "Games.h"
#import "GameListPartitions.h"
#import "GameViewController.h"
#import "java/util/List.h"
#import "Toast+UIView.h"
#import <objc/runtime.h>
#import "ImageString.h"
#import "GameListEntry.h"
#import "GameListEntryView.h"

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
    }
    return self;
}

- (void)viewDidLoad {
  [super viewDidLoad];
  // Uncomment the following line to preserve selection between presentations.
  // self.clearsSelectionOnViewWillAppear = NO;
  // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
  self.navigationItem.rightBarButtonItem = self.editButtonItem;
  self.tableView.rowHeight = 50;
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
  NTSGameListEntry *listEntry = [NTSGames gameListEntryWithNTSGame:game
                                                      withNSString:[self.model getUserId]];
//  cell.textLabel.text = [listEntry getVsString];
//  UILabel *label2 = [UILabel new];
//  label2.text = [listEntry getVsString];
//  [label2 sizeToFit];
//  [cell.contentView addSubview:label2];
//  
//  UILabel *label = [UILabel new];
//  label.text = [listEntry getModifiedString];
//  [label sizeToFit];
//  [cell.contentView addSubview:label];
  
  NSArray *subviewArray = [[NSBundle mainBundle]
                           loadNibNamed:@"GameListEntryView"
                           owner:self
                           options:nil];
  GameListEntryView *entry = [subviewArray objectAtIndex:0];
  entry.primaryLabel.text = [listEntry getVsString];
  entry.secondaryLabel.text = [listEntry getModifiedString];
  [cell.contentView addSubview:entry];
  NSLog(@"entry frame %@", NSStringFromCGRect(entry.frame));
  
  id <JavaUtilList> imageList = [listEntry getImageStringList];
  UIImage *image;
  if ([imageList size] == 2) {
    image = [self imageForTwoPhotos:imageList];
  } else {
    // not implemented;
    @throw @"remember to do this";
  }
  entry.imageView.image = image;
//  cell.imageView.image = image;
  return cell;
}

- (UIImage*)imageForPhotoList:(id<JavaUtilList>)photoList index:(int)index {
  NTSImageString *imageString = [photoList getWithInt:index];
  return [UIImage imageNamed:[[imageString getString] stringByAppendingString:@"_20"]];
}

- (UIImage*)imageForTwoPhotos:(id<JavaUtilList>)photoList {
  UIImage *image1 = [self imageForPhotoList:photoList index:0];
  UIImage *image2 = [self imageForPhotoList:photoList index:1];
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

-(NSString *)tableView:(UITableView *)tableView titleForDeleteConfirmationButtonForRowAtIndexPath:
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
     forRowAtIndexPath:(NSIndexPath *)indexPath
{
  if (editingStyle == UITableViewCellEditingStyleDelete) {
    NTSGame *game = [self gameForIndexPath:indexPath];
    if ([game isGameOver]) {
      [self.model archiveGameWithNTSGame:game];
      self.gameListPartitions = [self.model getGameListPartitions];
      [tableView deleteRowsAtIndexPaths:@[indexPath] withRowAnimation:UITableViewRowAnimationFade];
    } else {
//      [self.tableView moveRowAtIndexPath:indexPath toIndexPath:newPath];
//      [tableView reloadRowsAtIndexPaths:[NSArray arrayWithObject:indexPath]
//                       withRowAnimation:UITableViewRowAnimationFade];
//      dispatch_time_t delay = dispatch_time(DISPATCH_TIME_NOW, 300 * NSEC_PER_MSEC);
//      dispatch_after(delay, dispatch_get_main_queue(), ^{
//        [self.model resignGameWithNTSGame:game];
//        self.gameListPartitions = [self.model getGameListPartitions];
//        NSIndexPath *newPath = [NSIndexPath indexPathForItem:0 inSection:GAME_OVER_SECTION];
//        [self.tableView moveRowAtIndexPath:indexPath toIndexPath:newPath];
//      });
//      [self.model resignGameWithNTSGame:game];
//      self.gameListPartitions = [self.model getGameListPartitions];
//      NSIndexPath *newPath = [NSIndexPath indexPathForItem:0 inSection:GAME_OVER_SECTION];
//      [self.tableView moveRowAtIndexPath:indexPath toIndexPath:newPath];
//      [tableView reloadRowsAtIndexPaths:[NSArray arrayWithObject:newPath]
//                       withRowAnimation:UITableViewRowAnimationNone];
    }
  }
}

@end
