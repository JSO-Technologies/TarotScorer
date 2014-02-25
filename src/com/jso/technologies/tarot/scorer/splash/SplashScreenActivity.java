package com.jso.technologies.tarot.scorer.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.jso.technologies.tarot.scorer.R;
import com.jso.technologies.tarot.scorer.home.HomePageActivity;

public class SplashScreenActivity extends Activity {
	 protected int _splashTime = 2000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        
        final SplashScreenActivity splashScreen = this;

        new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				Intent i = new Intent();
    			i.setClass(splashScreen, HomePageActivity.class);
    			startActivity(i);
    			finish();
			}
		}, _splashTime);

    }
}
