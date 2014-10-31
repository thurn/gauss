package com.tinlib.dropswitch.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tinlib.dropswitch.DropswitchMain;
import org.lwjgl.Sys;

import java.awt.*;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
    config.width = 480;
    config.height = 768;
		new LwjglApplication(new DropswitchMain(), config);
	}
}
