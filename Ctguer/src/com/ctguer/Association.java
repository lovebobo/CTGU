package com.ctguer;

import java.util.ArrayList;
import java.util.List;

import com.ctguer.controller.Codes;
import com.ctguer.controller.FileIO;
import com.ctguer.controller.RelativeUser;
import com.ctguer.controller.URLs;
import com.ctguer.model.Activity;
import com.ctguer.model.Course;
import com.ctguer.model.LostFoundDetail;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Association extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{

	private ListView listView;
	private listviewAdapter<Activity> listviewadapter;
	private ArrayList<Activity> activityList=new ArrayList<>();
	private SwipeRefreshLayout mSwipeLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_association);
		MainActivity.resideMenu.addIgnoredView(MainActivity.FragmentView);
		
		
		initView();
		
		initData();
	}

	
	


	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		FileIO.saveObjectToFile(Association.this, URLs.activityfile, activityList);
		super.onPause();
	}





	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		FileIO.saveObjectToFile(Association.this, URLs.activityfile, activityList);
		super.onStop();
	}


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		FileIO.saveObjectToFile(Association.this, URLs.activityfile, activityList);
		super.onDestroy();
	}


	private void initData() {
		// TODO Auto-generated method stub
		/*for(int i=0;i<5;i++)
		{
			activityList.add(new Activity(16,10,""," ",1,20,15," "," "));
		}*/
		Object object=FileIO.getObjectFromFile(Association.this, URLs.activityfile);
		if(object!=null){
			activityList = (ArrayList<Activity>) object;
		 }
		
		mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.id_swipe_ly);

		mSwipeLayout.setOnRefreshListener(this);
		mSwipeLayout.setColorScheme(android.R.color.holo_green_dark, android.R.color.holo_green_light,
				android.R.color.holo_orange_light, android.R.color.holo_red_light);
		listviewadapter=new listviewAdapter<>(Association.this, R.layout.party_item, activityList);
		listView.setAdapter(listviewadapter);

	}

	Handler handler=new Handler()
	{

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case Codes.getActivityListSuc:
				if(msg.obj!=null)
				{
					activityList.clear();
					for (Activity tmp : (List<Activity>)msg.obj) {
						activityList.add(tmp);
					}
				}
				listviewadapter.notifyDataSetChanged();		
				Toast.makeText(Association.this, "跟新成功", Toast.LENGTH_SHORT).show();
				mSwipeLayout.setRefreshing(false);
				break;
			case Codes.getActivityListFail:
				Toast.makeText(Association.this, "跟新失败", Toast.LENGTH_SHORT).show();
				mSwipeLayout.setRefreshing(false);
				break;

			}
		}
		
	};
	
	private void initView() {
		// TODO Auto-generated method stub
		listView=(ListView)findViewById(R.id.listview_activity);
	}


	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		RelativeUser.getActivityList(handler);
	}
}
