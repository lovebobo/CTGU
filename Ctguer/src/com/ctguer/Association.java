package com.ctguer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ctguer.controller.Codes;
import com.ctguer.controller.FileIO;
import com.ctguer.controller.RelativeUser;
import com.ctguer.controller.URLs;
import com.ctguer.model.Activity;
import com.ctguer.model.Course;
import com.ctguer.model.LostFoundDetail;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Association extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{

	private ListView listView;
	private listviewAdapter<Activity> listviewadapter;
	private ArrayList<Activity> activityList=new ArrayList<>();
	private SwipeRefreshLayout mSwipeLayout;
	private ImageView imageView_add;
	
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
		imageView_add=(ImageView)findViewById(R.id.title_btn);
		
		imageView_add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(Association.this,LaunchActivity.class);
				startActivityForResult(intent, Codes.launchActivityListSuc);
				//startActivity(intent);
			}
		});
		
		mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.id_swipe_ly);

		mSwipeLayout.setOnRefreshListener(this);
		mSwipeLayout.setColorScheme(android.R.color.holo_green_dark, android.R.color.holo_green_light,
				android.R.color.holo_orange_light, android.R.color.holo_red_light);
		listviewadapter=new listviewAdapter<>(Association.this, R.layout.party_item, activityList);
		listView.setAdapter(listviewadapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(Association.this, Activity_Detail.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("Comment_obj", listviewadapter.getItem(position));
				intent.putExtras(bundle);
				startActivity(intent);	
			}
		});

	}
	
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
		   case Codes.launchActivityListSuc:
			   mSwipeLayout.post(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					mSwipeLayout.setRefreshing(true);
				}
			});
			onRefresh();				  
		    break;
		default:
		    break;
		    }
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
					Collections.reverse(activityList);
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
