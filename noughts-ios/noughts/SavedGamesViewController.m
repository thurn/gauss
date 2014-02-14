//
//  SavedGamesViewController.m
//  noughts
//
//  Created by Derek Thurn on 12/28/13.
//  Copyright (c) 2013 Derek Thurn. All rights reserved.
//

#import "SavedGamesViewController.h"
#import "Model.h"
#import "Game.h"
#import "GameListPartitions.h"
#import "GameViewController.h"
#import "java/util/List.h"

@interface SavedGamesViewController ()
@property(weak,nonatomic) NTSModel *model;
@property(strong,nonatomic) NTSGameListPartitions *gameListPartitions;
@end

#define YOUR_GAMES_SECTION 0
#define THEIR_GAMES_SECTION 1
#define GAME_OVER_SECTION 2

@implementation SavedGamesViewController

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
  if (self.model) {
    self.gameListPartitions = [self.model getGameListPartitions];
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
  cell.imageView.image = [UIImage imageNamed:@"ic_local_multiplayer"];
  return cell;
}

- (NSString *)tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section {
  if ([self tableView:tableView numberOfRowsInSection:section] == 0) {
    // Suppress title for empty sections
    return nil;
  }
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

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
  GameViewController *controller =
      [self.storyboard instantiateViewControllerWithIdentifier:@"GameViewController"];
  NTSGame *game = [self gameForIndexPath:indexPath];
  [controller setNTSModel:self.model];
  controller.currentGameId = [game getId];
  [self.navigationController pushViewController:controller animated:YES];
}

/*
// Override to support conditional editing of the table view.
- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the specified item to be editable.
    return YES;
}
*/

/*
// Override to support editing the table view.
- (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (editingStyle == UITableViewCellEditingStyleDelete) {
        // Delete the row from the data source
        [tableView deleteRowsAtIndexPaths:@[indexPath] withRowAnimation:UITableViewRowAnimationFade];
    }   
    else if (editingStyle == UITableViewCellEditingStyleInsert) {
        // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
    }   
}
*/

/*
// Override to support rearranging the table view.
- (void)tableView:(UITableView *)tableView moveRowAtIndexPath:(NSIndexPath *)fromIndexPath toIndexPath:(NSIndexPath *)toIndexPath
{
}
*/

/*
// Override to support conditional rearranging of the table view.
- (BOOL)tableView:(UITableView *)tableView canMoveRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the item to be re-orderable.
    return YES;
}
*/

/*
#pragma mark - Navigation

// In a story board-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}

 */

@end
