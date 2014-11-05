package com.tinlib.dropswitch.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.tinlib.dropswitch.CurrentLauncher;
import com.tinlib.dropswitch.DropswitchMain;
import com.tinlib.dropswitch.Launcher;

public class HtmlLauncher extends GwtApplication implements Launcher {
  @Override
  public GwtApplicationConfiguration getConfig () {
    return new GwtApplicationConfiguration(400, 680);
  }

  @Override
  public ApplicationListener getApplicationListener () {
    CurrentLauncher.set(this);
    return new DropswitchMain();
  }

  @Override
  public void log(String string, Object... args) {
    consoleLog(format(string, args));
  }

  public static String format(final String format, final Object... args) {
    String[] split = format.split("%s");
    final StringBuilder msg = new StringBuilder();
    for (int pos = 0; pos < split.length - 1; pos += 1) {
      msg.append(split[pos]);
      msg.append(args[pos]);
    }
    msg.append(split[split.length - 1]);
    return msg.toString();
  }
}