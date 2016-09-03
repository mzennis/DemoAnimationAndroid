package com.mzennis.demo;

import android.app.Application;
import android.content.Context;

/**
 * Created by mzennis on 9/4/16.
 */
public class MainApplication extends Application {

	public static Context CONTEXT = null;

	@Override
	public void onCreate() {
		super.onCreate();

		CONTEXT = getApplicationContext();
	}

}