#import "AppDelegate.h"

#import <WebKit/WebKit.h>

#import "GCDWebServer.h"
#import "GCDWebServerDataResponse.h"

@interface AppDelegate ()
@property(nonatomic) GCDWebServer* webServer;
@property(nonatomic) WKWebView *webView;
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
                                  cacheAge:0
                        allowRangeRequests:YES];
  [self.webServer startWithPort:8080 bonjourName:nil];

  WKWebViewConfiguration *configuration = [[WKWebViewConfiguration alloc] init];
  self.webView = [[WKWebView alloc] initWithFrame:rootController.view.frame
                                          configuration:configuration];
  [rootController.view addSubview:self.webView];

  NSURL *url = [[NSURL alloc] initWithString:@"http://0.0.0.0:8080/ipc.html"];
  NSURLRequest *request = [[NSURLRequest alloc] initWithURL:url];
  
  [self.webView loadRequest:request];

  return YES;
}

- (void)callJs {
  dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(3 * NSEC_PER_SEC)),
                 dispatch_get_main_queue(), ^{
                   [self.webView evaluateJavaScript:@"tin.alert([1,2,3])"
                                  completionHandler:^(id result, NSError *error) {
                                    NSLog(@"%@", result);
                                  }];
                 });
}

@end
