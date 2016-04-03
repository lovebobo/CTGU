package com.ctguer;

import com.umeng.analytics.MobclickAgent;

import android.app.Activity;

public class BaseActivity extends Activity{

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);
		
	}
	
}
