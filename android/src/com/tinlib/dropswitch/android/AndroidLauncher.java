package com.tinlib.dropswitch.android;

import android.os.Bundle;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.tinlib.dropswitch.DropswitchMain;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
    config.hideStatusBar = false;

    RelativeLayout layout = new RelativeLayout(this);
    layout.addView(initializeForView(new DropswitchMain(), config));
    setContentView(layout);
	}
}
