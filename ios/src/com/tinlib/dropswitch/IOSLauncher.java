package com.tinlib.dropswitch;

import org.robovm.apple.foundation.Foundation;
import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.foundation.NSDictionary;
import org.robovm.apple.foundation.NSString;
import org.robovm.apple.uikit.UIApplication;

import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;
import com.tinlib.dropswitch.DropswitchMain;
import org.robovm.apple.uikit.UIStatusBarStyle;

public class IOSLauncher extends IOSApplication.Delegate implements Launcher {
  @Override
  protected IOSApplication createApplication() {
    CurrentLauncher.set(this);
    IOSApplicationConfiguration config = new IOSApplicationConfiguration();
    return new IOSApplication(new DropswitchMain(), config);
  }

  public static void main(String[] argv) {
      NSAutoreleasePool pool = new NSAutoreleasePool();
      UIApplication.main(argv, null, IOSLauncher.class);
      pool.close();
  }

  @Override
  public void log(String string, Object... args) {
    Foundation.log(String.format(string, args));
  }
}