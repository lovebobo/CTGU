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
	String[] titles = { "��Ŀ��ҳ", "У԰����", "����ר��", "�������"};
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
		// ��PagerSlidingTabStrip��ϸ�ڽ�������
		setTabsValue();
	}
	
	 /**
     * ��PagerSlidingTabStrip�ĸ������Խ��и�ֵ��
     */
    private void setTabsValue() {
        // ����Tab���Զ��������Ļ��
        tabs.setShouldExpand(true);
        // ����Tab�ķָ�����͸����
        tabs.setDividerColor(Color.TRANSPARENT);
        // ����Tab�ײ��ߵĸ߶�
        tabs.setUnderlineHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 1, dm));
        // ����Tab Indicator�ĸ߶�
        tabs.setIndicatorHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 4, dm));
        // ����Tab�������ֵĴ�С
        tabs.setTextSize((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 16, dm));
        // ����Tab Indicator����ɫ
        tabs.setIndicatorColor(Color.parseColor("#45c01a"));
        // ����ѡ��Tab���ֵ���ɫ (�������Զ����һ������)
        tabs.setSelectedTextColor(Color.parseColor("#45c01a"));
        // ȡ�����Tabʱ�ı���ɫ
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
		new AlertDialog.Builder(this).setTitle("ȷ���˳�������") 
        .setIcon(android.R.drawable.ic_dialog_info) 
        .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() { 
            @Override 
            public void onClick(DialogInterface dialog, int which) { 
            // �����ȷ�ϡ���Ĳ��� 
               CtguNews.this.finish(); 
     
            } 
        }) 
        .setNegativeButton("����", new DialogInterface.OnClickListener() { 
     
            @Override 
            public void onClick(DialogInterface dialog, int which) { 
            // ��������ء���Ĳ���,���ﲻ����û���κβ��� 
            } 
        }).show(); 
	}
	*/
}