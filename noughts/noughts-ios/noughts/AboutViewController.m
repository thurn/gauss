#import "AboutViewController.h"

@interface AboutViewController ()
@property (weak, nonatomic) IBOutlet UIWebView *webView;
@end

@implementation AboutViewController

- (void)viewDidLoad {
  [super viewDidLoad];
  NSURL *url = [NSURL URLWithString:@"https://noughts.firebaseapp.com/about"];
  NSURLRequest *request = [NSURLRequest requestWithURL:url];
  [_webView loadRequest:request];
}

@end
