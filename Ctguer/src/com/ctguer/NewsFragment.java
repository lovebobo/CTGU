package com.ctguer;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ctguer.controller.Codes;
import com.ctguer.controller.FileIO;
import com.ctguer.controller.RelativeUser;
import com.ctguer.controller.URLs;
import com.ctguer.model.Activity;
import com.ctguer.model.NewsContent;
import com.ctguer.model.homepage;
import com.umeng.analytics.MobclickAgent;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout.LayoutParams;

public class NewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
	//列表适配器
	listviewAdapter<NewsContent> listviewAdapter;
	//列表
	ListView listView;
	//新闻简介列表
	private List<NewsContent> newsSummaryList =new ArrayList<NewsContent>();
	//界面视图对象
	private View contextView;
	
	private SwipeRefreshLayout mSwipeLayout;
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
		contextView= inflater.inflate(R.layout.ajb_new_fragment, container, false);  
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
		
		mSwipeLayout = (SwipeRefreshLayout)contextView.findViewById(R.id.id_swipe_ly_news);

		mSwipeLayout.setOnRefreshListener(this);
		mSwipeLayout.setColorScheme(android.R.color.holo_green_dark, android.R.color.holo_green_light,
				android.R.color.holo_orange_light, android.R.color.holo_red_light);
		// TODO Auto-generated method stub
		listView=(ListView)contextView.findViewById(R.id.listview_activity);
		
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				Intent intent=new Intent(getActivity(),WebViewActivity.class);
				intent.putExtra("url",newsSummaryList.get(position).getHref());
				startActivity(intent);
			}
		});
		
		Object ob = FileIO.getObjectFromFile(getActivity(), URLs.ajbnewsfile);
  		if(ob != null){
  			newsSummaryList = (List<NewsContent>)ob;
  			Log.d("hh", "文件取出来");
  		}
		//清空newsSummaryList
		//绑定适配器
		listviewAdapter=new listviewAdapter<NewsContent>(getActivity(), R.layout.news_item, newsSummaryList);
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
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		RelativeUser.getNewsList(handler);
	}

	
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		if(newsSummaryList.size() > 0){
			FileIO.saveObjectToFile(getActivity(), URLs.ajbnewsfile, newsSummaryList);
		}
		super.onDestroyView();
	}



	Handler handler=new Handler()
	{
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case Codes.getNewsListSuc:
				if(msg.obj!=null)
				{
					newsSummaryList.clear();
					for (NewsContent tmp : (List<NewsContent>)msg.obj) {
						newsSummaryList.add(tmp);
					}
				}
				listviewAdapter.notifyDataSetChanged();		
				Toast.makeText(getActivity(), "跟新成功", Toast.LENGTH_SHORT).show();
				mSwipeLayout.setRefreshing(false);
				
				break;
			case Codes.getNewListFail:
				Toast.makeText(getActivity(), "跟新失败", Toast.LENGTH_SHORT).show();
				mSwipeLayout.setRefreshing(false);
				break;

			}
		}
	};
	
	
}
