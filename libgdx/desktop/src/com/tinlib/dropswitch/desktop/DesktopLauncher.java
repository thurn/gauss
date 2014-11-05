package com.tinlib.dropswitch.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tinlib.dropswitch.CurrentLauncher;
import com.tinlib.dropswitch.DropswitchMain;
import com.tinlib.dropswitch.Launcher;
import org.lwjgl.Sys;

import java.awt.*;

public class DesktopLauncher implements Launcher {
	public static void main (String[] arg) {
    CurrentLauncher.set(new DesktopLauncher());
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
    config.width = 400;
    config.height = 680;
		new LwjglApplication(new DropswitchMain(), config);
	}

  @Override
  public void log(String string, Object... args) {
    System.err.println(String.format(string, args));
  }
}
