package com.ctguer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends BaseActivity {

	private static final long DELAY_TIME = 1000L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_splash);
	redirectByTime();
    }

    private void redirectByTime() {
	new Handler().postDelayed(new Runnable() {
	    @Override
	    public void run() {
		Intent intent = new Intent();
		intent.setClass(SplashActivity.this, MainActivity.class);
		startActivity(intent);
		finish();
	    }
	}, DELAY_TIME);
    }
}

