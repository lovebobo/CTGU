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
	String[] titles = { "ʧ����Ϣ", "������Ϣ" };
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
		
		// ��PagerSlidingTabStrip��ϸ�ڽ�������
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
     * ��PagerSlidingTabStrip�ĸ������Խ��и�ֵ��
     */
    private void setTabsValue() {
        // ����Tab���Զ��������Ļ��
        booktabs.setShouldExpand(true);
        // ����Tab�ķָ�����͸����
        booktabs.setDividerColor(Color.TRANSPARENT);
        // ����Tab�ײ��ߵĸ߶�
        booktabs.setUnderlineHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 1, dm));
        // ����Tab Indicator�ĸ߶�
        booktabs.setIndicatorHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 4, dm));
        // ����Tab�������ֵĴ�С
        booktabs.setTextSize((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 16, dm));
        // ����Tab Indicator����ɫ
        booktabs.setIndicatorColor(Color.parseColor("#45c01a"));
        // ����ѡ��Tab���ֵ���ɫ (�������Զ����һ������)
        booktabs.setSelectedTextColor(Color.parseColor("#45c01a"));
        // ȡ�����Tabʱ�ı���ɫ
        booktabs.setTabBackground(0);
    }
	
	
	//����ʱ���ô˺���
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
