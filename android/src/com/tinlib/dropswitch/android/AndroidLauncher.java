package com.tinlib.dropswitch.android;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.tinlib.dropswitch.CurrentLauncher;
import com.tinlib.dropswitch.DropswitchMain;
import com.tinlib.dropswitch.Launcher;

public class AndroidLauncher extends AndroidApplication implements Launcher {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    CurrentLauncher.set(this);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
    config.hideStatusBar = false;

    RelativeLayout layout = new RelativeLayout(this);
    layout.addView(initializeForView(new DropswitchMain(), config));
    setContentView(layout);
	}

  @Override
  public void log(String string, Object... args) {
    Log.e("LOGGING", String.format(string, args));
  }
}
