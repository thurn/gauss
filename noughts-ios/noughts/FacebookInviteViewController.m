#import "FacebookInviteViewController.h"
#import "AppDelegate.h"
#import "Model.h"
#import "QueryParsing.h"

@interface FacebookInviteViewController ()
@property(strong,nonatomic) NTSModel *model;
@end

@implementation FacebookInviteViewController

- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    // Uncomment the following line to preserve selection between presentations.
    // self.clearsSelectionOnViewWillAppear = NO;
    
    // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
    // self.navigationItem.rightBarButtonItem = self.editButtonItem;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
//  AppDelegate* appDelegate = (AppDelegate*)[UIApplication sharedApplication].delegate;
//  NSNumber *uid = appDelegate.friends[indexPath.row][@"uid"];
//  [FBWebDialogs
//   presentRequestsDialogModallyWithSession:nil
//   message:@"Invitation to play noughts"
//   title:@"noughts"
//   parameters:@{@"to": [uid stringValue]}
//   handler: ^(FBWebDialogResult result, NSURL *resultURL, NSError *error){
//     if (result != FBWebDialogResultDialogNotCompleted) {
//       NSLog(@"result url %@", resultURL);
//       NSDictionary *query = [QueryParsing dictionaryFromQueryComponents:resultURL];
//       NSLog(@"fbid %@", query[@"to[0]"]);
//       NSLog(@"requestid %@", query[@"request"]);
//     }
//   }
//   friendCache:appDelegate.friendCache];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
  return 1;
}

- (void)setNTSModel:(NTSModel *)model {
  _model = model;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
  AppDelegate* appDelegate = (AppDelegate*)[UIApplication sharedApplication].delegate;
  return [appDelegate.friends count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView
         cellForRowAtIndexPath:(NSIndexPath *)indexPath {
  AppDelegate* appDelegate = (AppDelegate*)[UIApplication sharedApplication].delegate;
  UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"FacebookCell"
                                                          forIndexPath:indexPath];
  NSDictionary *friend = [appDelegate.friends objectAtIndex:indexPath.row];
  cell.textLabel.text = friend[@"name"];
  cell.imageView.image = appDelegate.friendPhotos[friend[@"uid"]];
  return cell;
}


/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
