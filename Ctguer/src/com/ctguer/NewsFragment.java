package com.ctguer;


import java.util.ArrayList;
import java.util.List;

import com.ctguer.model.homepage;
import com.umeng.analytics.MobclickAgent;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.FrameLayout.LayoutParams;

public class NewsFragment extends Fragment{
	//列表适配器
	listviewAdapter<homepage> listviewAdapter;
	//列表
	ListView listView;
	//新闻简介列表
	private List<homepage> newsSummaryList =new ArrayList<homepage>();
	//界面视图对象
	private View contextView;
	
    private Handler hanle = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch(msg.what){
				 
			}
			super.handleMessage(msg);
		}
    };
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		LayoutParams params = new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		contextView= inflater.inflate(R.layout.fragment_news, container, false);  
		initdata();
		findnews();
		return contextView;
	}
	 @Override  
     public void onCreate(Bundle savedInstanceState) {  
          super.onCreate(savedInstanceState);          
     } 
	@Override
	public void onStart(){
		// TODO Auto-generated method stub
		
		MainActivity.resideMenu.addIgnoredView(MainActivity.FragmentView);
		super.onStart();
	}
	private void initdata(){
		// TODO Auto-generated method stub
		listView=(ListView)contextView.findViewById(R.id.news_listview);
		//清空newsSummaryList
		newsSummaryList.clear();
		for(int i=0;i<20;i++)
		newsSummaryList.add(new homepage("aa", R.drawable.icon));
		//绑定适配器
		listviewAdapter=new listviewAdapter<homepage>(getActivity(), R.layout.news_item, newsSummaryList);
		listView.setAdapter(listviewAdapter);
    }
	public void findnews(){
	
		
	}
	@Override
	public void onResume() {
	    super.onResume();
	    MobclickAgent.onPageStart("MainScreen"); //统计页面
	}
	@Override
	public void onPause() {
	    super.onPause();
	    MobclickAgent.onPageEnd("MainScreen"); 
	}

}
