package com.avgusti.circularclock;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

public class CircularClockApplication extends Application {
	public static final String TAG ="CircularClockApplication";
	private BroadcastReceiver receiver=new ScreenReceiver();
	private static IntentFilter screenFilter = new IntentFilter();
	public class ScreenReceiver extends BroadcastReceiver {
	    @Override
	    public void onReceive(Context context, Intent intent) {
	     Log.d(TAG, "Screen: "+intent.getAction());
	    }
	}
	@Override
	public void onCreate() {
		super.onCreate();
		screenFilter.addAction(Intent.ACTION_SCREEN_OFF);
		screenFilter.addAction(Intent.ACTION_SCREEN_ON);
		registerReceiver(receiver, screenFilter);
	}

	@Override
	public void onTerminate() {
		unregisterReceiver(receiver);
		super.onTerminate();
	}

}
