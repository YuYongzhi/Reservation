package com.reservation.act;

import com.reservation.R;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import static com.reservation.utils.StringUtils.*;

public class WelcomeActivity extends Activity {

	public static final int FLAG_HOMEKEY_DISPATCHED = 0x80000000;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initFullScreen();
		setContentView(R.layout.activity_welcome_layout);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(500);
					SharedPreferences preferences = getSharedPreferences(PREFS_GUIDE_STRING, MODE_PRIVATE);
					boolean isFirstGuide = preferences.getBoolean(PrefsGuideItem.IS_FIRST_GUIDE, true);
					if (isFirstGuide == true) {
						Intent intent = new Intent(WelcomeActivity.this, GuideActivity.class);
						startActivity(intent);
						WelcomeActivity.this.finish();
					} else {
						Intent intent = new Intent(WelcomeActivity.this, MainFragmentActivity.class);
						startActivity(intent);
						WelcomeActivity.this.finish();
					}
					System.gc();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
		}).start();
	}
	
	private void initFullScreen() {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.getWindow().setFormat(PixelFormat.TRANSPARENT);
		this.getWindow().setFlags(FLAG_HOMEKEY_DISPATCHED, FLAG_HOMEKEY_DISPATCHED);
	}
}
