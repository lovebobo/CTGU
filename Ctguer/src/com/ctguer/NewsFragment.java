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
	//�б�������
	listviewAdapter<homepage> listviewAdapter;
	//�б�
	ListView listView;
	//���ż���б�
	private List<homepage> newsSummaryList =new ArrayList<homepage>();
	//������ͼ����
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
		//���newsSummaryList
		newsSummaryList.clear();
		for(int i=0;i<20;i++)
		newsSummaryList.add(new homepage("aa", R.drawable.icon));
		//��������
		listviewAdapter=new listviewAdapter<homepage>(getActivity(), R.layout.news_item, newsSummaryList);
		listView.setAdapter(listviewAdapter);
    }
	public void findnews(){
	
		
	}
	@Override
	public void onResume() {
	    super.onResume();
	    MobclickAgent.onPageStart("MainScreen"); //ͳ��ҳ��
	}
	@Override
	public void onPause() {
	    super.onPause();
	    MobclickAgent.onPageEnd("MainScreen"); 
	}

}
