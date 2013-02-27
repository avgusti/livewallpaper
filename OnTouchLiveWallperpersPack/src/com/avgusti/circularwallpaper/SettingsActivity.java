package com.avgusti.circularwallpaper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;



/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends Activity {

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int test=1;
		SharedPreferences prefs = this.getPreferences(Context.MODE_PRIVATE);
	    test = prefs.getInt("PREF_DIFFICULTY", 0);
		
		LinearLayout base=new LinearLayout(this);
		base.setOrientation(LinearLayout.VERTICAL);
		//base.setShowDividers(LinearLayout.SHOW_DIVIDER_BEGINNING | LinearLayout.SHOW_DIVIDER_MIDDLE |LinearLayout.SHOW_DIVIDER_END);
		setContentView(base);
		TextView tv=new TextView(this);
		tv.setText(test+"");
		base.addView(tv);
		
		
	}
	
}
