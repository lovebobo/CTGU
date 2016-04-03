package com.ctguer.controller;

import com.umeng.analytics.MobclickAgent;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public abstract class BackHandledFragment extends Fragment {

	/**
	 * 所有继承BackHandledFragment的子类都将在这个方法中实现物理Back键按下后的逻辑
	 * FragmentActivity捕捉到物理返回键点击事件后会首先询问Fragment是否消费该事件
	 * 如果没有Fragment消息时FragmentActivity自己才会消费该事件
	 */
	public abstract boolean onBackPressed();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		//告诉FragmentActivity，当前Fragment在栈顶

	}
	
	@Override
	public void onResume() {
	    super.onResume();
	    MobclickAgent.onPageStart("MainScreen"); //统计页面
	}
	@Override
	public void onPause() {
	    super.onPause();
	    MobclickAgent.onPageEnd("MainScreen"); //统计页面
	}
}