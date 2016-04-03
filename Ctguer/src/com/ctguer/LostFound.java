package com.ctguer;


import com.ctguer.controller.PagerSlidingTabStrip;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;

public class LostFound extends FragmentActivity {

	PagerSlidingTabStrip booktabs;
	ViewPager bookpager;
	DisplayMetrics dm;
	ImageButton postButton;
	String[] titles = { "失物信息", "招领信息" };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_lost_found);
		findById();
		init();
	}
	


	private void init() {
		// TODO Auto-generated method stub
		
		dm = getResources().getDisplayMetrics();
		
		bookpager.setAdapter(new MyAdapter(getSupportFragmentManager(),titles));
		booktabs.setViewPager(bookpager);
		
		// 对PagerSlidingTabStrip的细节进行配置
		setTabsValue();
	}

	private void findById() {
		// TODO Auto-generated method stub
		
		
		booktabs=(PagerSlidingTabStrip)findViewById(R.id.booktabs);
		bookpager=(ViewPager)findViewById(R.id.bookpager);
		postButton = (ImageButton) findViewById(R.id.lost_find_edit_button);
		postButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getApplication(), PostLostFoundActivity.class);
				startActivity(intent);
				
				
			}
		});
		
		
	}
	
	/**
     * 对PagerSlidingTabStrip的各项属性进行赋值。
     */
    private void setTabsValue() {
        // 设置Tab是自动填充满屏幕的
        booktabs.setShouldExpand(true);
        // 设置Tab的分割线是透明的
        booktabs.setDividerColor(Color.TRANSPARENT);
        // 设置Tab底部线的高度
        booktabs.setUnderlineHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 1, dm));
        // 设置Tab Indicator的高度
        booktabs.setIndicatorHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 4, dm));
        // 设置Tab标题文字的大小
        booktabs.setTextSize((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 16, dm));
        // 设置Tab Indicator的颜色
        booktabs.setIndicatorColor(Color.parseColor("#45c01a"));
        // 设置选中Tab文字的颜色 (这是我自定义的一个方法)
        booktabs.setSelectedTextColor(Color.parseColor("#45c01a"));
        // 取消点击Tab时的背景色
        booktabs.setTabBackground(0);
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
				//return null;
				
				
				 switch (position)
			      {
			      case 0:
			        return new LostFragment();
			       
			      case 1:
			        return new FoundFragment();
			       
			     
			      }
			      return null;
			  
				
				
			}
		}
}
