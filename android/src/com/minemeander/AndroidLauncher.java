package com.minemeander;

import android.os.Bundle;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.minemeander.MyMineMeander;

import java.io.File;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		Constant.ZOOM_FACTOR = 0.25f;
		initialize(new MyMineMeander(), config);
	}

}
