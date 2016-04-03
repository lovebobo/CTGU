package com.ctguer;


import java.util.ArrayList;
import java.util.List;

import com.ctguer.controller.PagerSlidingTabStrip;
import com.umeng.analytics.MobclickAgent;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;


public class CtguNews extends FragmentActivity {
	PagerSlidingTabStrip tabs;
	ViewPager pager;
	DisplayMetrics dm;
	Fragment afrag = null;
	String[] titles = { "栏目首页", "校园新闻", "考试专栏", "教务相关"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ctgu_news);
		//getSupportFragmentManager().beginTransaction().hide(fragslist.get(0)).hide(fragslist.get(2)).hide(fragslist.get(3)).show(fragslist.get(1)).commit();
		initView();
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		 MainActivity.resideMenu.addIgnoredView(MainActivity.FragmentView);
		 super.onStart();
	}
	private void initView(){
		dm = getResources().getDisplayMetrics();
		pager = (ViewPager) findViewById(R.id.pager);
		tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		pager.setAdapter(new MyAdapter(getSupportFragmentManager(),titles));
		tabs.setViewPager(pager);
		// 对PagerSlidingTabStrip的细节进行配置
		setTabsValue();
	}
	
	 /**
     * 对PagerSlidingTabStrip的各项属性进行赋值。
     */
    private void setTabsValue() {
        // 设置Tab是自动填充满屏幕的
        tabs.setShouldExpand(true);
        // 设置Tab的分割线是透明的
        tabs.setDividerColor(Color.TRANSPARENT);
        // 设置Tab底部线的高度
        tabs.setUnderlineHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 1, dm));
        // 设置Tab Indicator的高度
        tabs.setIndicatorHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 4, dm));
        // 设置Tab标题文字的大小
        tabs.setTextSize((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 16, dm));
        // 设置Tab Indicator的颜色
        tabs.setIndicatorColor(Color.parseColor("#45c01a"));
        // 设置选中Tab文字的颜色 (这是我自定义的一个方法)
        tabs.setSelectedTextColor(Color.parseColor("#45c01a"));
        // 取消点击Tab时的背景色
        tabs.setTabBackground(0);
    }
	
    
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
    //滑动时调用此函数
	public class MyAdapter extends FragmentPagerAdapter{
		String[] _titles;
		public MyAdapter(FragmentManager fm,String[] titles) {
			super(fm);
			_titles=titles;
		}
		
		@Override
		public CharSequence getPageTitle(int position) {
			return _titles[position];
		}
	
		
		@Override
		public int getCount() {
			return _titles.length;
		}

		@Override
		public Fragment getItem(int position) {
			switch(position){	
			case 0:
				afrag = new NewsFragment();
				break;
			case 1: 
				afrag = new SchoolNewsFragment();
				break;
			case 2:
				afrag =  new ExamMessageFragment();
				break;
			case 3:
				afrag =  new InstantMessageFragment();
				break;
			}
			return afrag;
			
		}
		
	}
	/*
	public void onBackPressed() {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(this).setTitle("确认退出新闻吗？") 
        .setIcon(android.R.drawable.ic_dialog_info) 
        .setPositiveButton("确定", new DialogInterface.OnClickListener() { 
            @Override 
            public void onClick(DialogInterface dialog, int which) { 
            // 点击“确认”后的操作 
               CtguNews.this.finish(); 
     
            } 
        }) 
        .setNegativeButton("返回", new DialogInterface.OnClickListener() { 
     
            @Override 
            public void onClick(DialogInterface dialog, int which) { 
            // 点击“返回”后的操作,这里不设置没有任何操作 
            } 
        }).show(); 
	}
	*/
}