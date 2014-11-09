#import "AppDelegate.h"

#import <WebKit/WebKit.h>

#import "GCDWebServer.h"
#import "GCDWebServerDataResponse.h"

@interface AppDelegate ()
@property(nonatomic) GCDWebServer* webServer;
@end

@implementation AppDelegate

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
  self.window = [[UIWindow alloc] initWithFrame:[[UIScreen
                                                  mainScreen] bounds]];
  self.window.backgroundColor = [UIColor whiteColor];
  [self.window makeKeyAndVisible];

  UIViewController *rootController = [[UIViewController alloc] init];
  self.window.rootViewController = rootController;

  NSBundle *bundle = [NSBundle mainBundle];
  NSString *bundlePath = bundle.bundlePath;
  NSLog(@"bundle path %@", bundlePath);
  self.webServer = [[GCDWebServer alloc] init];
  [self.webServer addGETHandlerForBasePath:@"/"
                             directoryPath:[bundlePath stringByAppendingString:@"/Test"]
                             indexFilename:@"index.html"
                                  cacheAge:3600
                        allowRangeRequests:YES];
  [self.webServer startWithPort:8080 bonjourName:nil];

  WKWebViewConfiguration *configuration = [[WKWebViewConfiguration alloc] init];
  WKWebView *webView = [[WKWebView alloc] initWithFrame:rootController.view.frame
                                          configuration:configuration];
  [rootController.view addSubview:webView];

  NSURL *url = [[NSURL alloc] initWithString:@"http://0.0.0.0:8080/"];
  NSURLRequest *request = [[NSURLRequest alloc] initWithURL:url];
  
  [webView loadRequest:request];

  return YES;
}

@end
