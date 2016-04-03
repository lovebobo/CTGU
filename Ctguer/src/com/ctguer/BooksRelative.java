package com.ctguer;

import com.ctguer.controller.BackHandledFragment;
import com.ctguer.controller.PagerSlidingTabStrip;
import com.umeng.analytics.MobclickAgent;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;

public class BooksRelative extends FragmentActivity{

	
	public static BackHandledFragment mBackHandedFragment;  
    PagerSlidingTabStrip booktabs;
	ViewPager bookpager;
	DisplayMetrics dm;

	
	String[] titles = { "ͼ�����", "�軹���" };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_books_relative);
	
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
		public class MyAdapter extends FragmentStatePagerAdapter{
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
			public void destroyItem(View container, int position, Object object) {
				// TODO Auto-generated method stub
				super.destroyItem(container, position, object);
			}

			@Override
			public int getItemPosition(Object object) {
				// TODO Auto-generated method stub
				return super.getItemPosition(object);
			}

			@Override
			public int getCount() {
				return _titles.length;
			}

			@Override
			public Fragment getItem(int position) {		
				//return null;
				if(position==0){
					Fragment sea = new BookSearchFragment();
				
					
				
					
					return sea;
				}
				else{
					Fragment bo = new BookBorrowFragment();
				
					return bo;
				}
					
				
			}
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

		@Override  
		    public void onBackPressed() {  
		        if(mBackHandedFragment == null || !mBackHandedFragment.onBackPressed()){  
		            if(getSupportFragmentManager().getBackStackEntryCount() == 0){  
		                super.onBackPressed();  
		            }else{  
		                getSupportFragmentManager().popBackStack();  
		            }  
		        }  
		    }
			
}
