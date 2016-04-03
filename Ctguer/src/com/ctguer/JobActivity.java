package com.ctguer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ctguer.controller.Codes;
import com.ctguer.controller.FileIO;
import com.ctguer.controller.RTPullListView;
import com.ctguer.controller.RelateCtgu;
import com.ctguer.controller.URLs;
import com.ctguer.controller.RTPullListView.OnRefreshListener;
import com.ctguer.model.Jiajiao;
import com.ctguer.model.PartTimeJob;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

public class JobActivity extends Activity {

	private List<PartTimeJob> pjob=new ArrayList<PartTimeJob>();
	
	private List<PartTimeJob> savapjob=new ArrayList<PartTimeJob>();
	
	private PartTimeJob showJob = new PartTimeJob();
	private Jiajiao showjiajiaoJob = new Jiajiao();
	
	protected ProgressDialog progressDialog;
	private listviewAdapter<PartTimeJob> jobListviewAdapter;
	String type ;
	private RTPullListView joblist;
	View footerView;
	private ProgressBar moreProgressBar;
	private int lastItem;
	private int page = 1;
	private boolean statu =false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent=getIntent();
		 type=intent.getStringExtra("type");
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_job);
		progressDialog = new ProgressDialog(JobActivity.this);
		progressDialog.setTitle("正在加载");
		progressDialog.setMessage("正在努力加载数据中，请耐心等候...");
		
		
	
		
		findId();
		
	
		
	}
	
	private void listonclick() {
		// TODO Auto-generated method stub
	
			
			
		joblist.setonRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							Thread.sleep(2000);
							 statu = true;
							RelateCtgu.xiaowai(JobActivity.this,handler,type,1);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					
				}).start();
				
				
			}
		});
	
	
			footerView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					moreProgressBar.setVisibility(View.VISIBLE);
					
					RelateCtgu.xiaowai(JobActivity.this,handler,type,++page);
					
				}
			});
				

			joblist.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position,
						long id) {
					// TODO Auto-generated method stub
					PartTimeJob job = jobListviewAdapter.getItem(position-1);
					
					if(type.equals("4")){
						Intent todetail = new Intent();
		  				
		  			
		  				todetail.putExtra("url", job.getUrl());
		  				todetail.setClass(JobActivity.this, ShowdongtaiActivity.class);
		  				startActivity(todetail);
		  			
						
					}else{
						progressDialog.show();
						RelateCtgu.getjob_detail(JobActivity.this, handler, job.getUrl(),type);
					}
					
					
				}
			});
			joblist.setOnScrollListener(new OnScrollListener() {
				
				@Override
				public void onScrollStateChanged(AbsListView view, int scrollState) {
					// TODO Auto-generated method stub
					if (lastItem > jobListviewAdapter.getCount() && scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
				      if(joblist.getFooterViewsCount() == 0)
				    	  joblist.addFooterView(footerView);
					
					}
				}
				
				@Override
				public void onScroll(AbsListView view, int firstVisibleItem,
						int visibleItemCount, int totalItemCount) {
					// TODO Auto-generated method stub
					lastItem = firstVisibleItem + visibleItemCount + 1;
					
				}
			});
			
		
		
	}

	private void findId() {
		// TODO Auto-generated method stub
		joblist = (RTPullListView) findViewById(R.id.joblist);
		footerView = View.inflate(JobActivity.this, R.layout.list_footview, null) ;
	    moreProgressBar = (ProgressBar) footerView.findViewById(R.id.footer_progress);
	    
	    if(type.equals("1")){
			 Object object= FileIO.getObjectFromFile(JobActivity.this, URLs.xiaowaiDetailfile);
			 
				
			 if(object!=null){
				 pjob = (List<PartTimeJob>) object;
			 }
			 
			if(pjob.size() != 0){
				joblist.addFooterView(footerView);
			}
			}
	    if(type.equals("2")){
			 Object object= FileIO.getObjectFromFile(JobActivity.this, URLs.jiajiaoDetailfile);
			 
				
			 if(object!=null){
				 pjob = (List<PartTimeJob>) object;
			 }
			 
			if(pjob.size() != 0){
				joblist.addFooterView(footerView);
			}
			}
	    
	
    if(type.equals("3")){
		 Object object= FileIO.getObjectFromFile(JobActivity.this, URLs.xiaoneiDetailfile);
		 
			
		 if(object!=null){
			 pjob = (List<PartTimeJob>) object;
		 }
		 
		if(pjob.size() != 0){
			joblist.addFooterView(footerView);
		}
		}
    if(type.equals("4")){
		 Object object= FileIO.getObjectFromFile(JobActivity.this, URLs.dongtaiDetailfile);
		 
			
		 if(object!=null){
			 pjob = (List<PartTimeJob>) object;
		 }
		 
		if(pjob.size() != 0){
			joblist.addFooterView(footerView);
		}
		}
    
		listonclick();
	
		jobListviewAdapter=new listviewAdapter<PartTimeJob>(JobActivity.this, R.layout.joblist_item, pjob);		
		  joblist.setAdapter(jobListviewAdapter);
		  
		  

	}

	Handler handler=new Handler()
	{

		@Override
		public void handleMessage(Message msg) {
			

			progressDialog.dismiss();
			joblist.onRefreshComplete();
			// TODO Auto-generated method stub
			switch (msg.what) {
			case Codes.getjobSuc:	
				if(statu == true){
				
					pjob.clear();
					Toast.makeText(JobActivity.this, "刷新成功", Toast.LENGTH_LONG).show();
					statu = false;
					page=1;
				}
				
				if(msg.obj!=null)
				{

					for (PartTimeJob tmp : (List<PartTimeJob>)msg.obj) {
					
						pjob.add(tmp);
					}
					
				}
				if(moreProgressBar.getVisibility()==View.VISIBLE)
				    moreProgressBar.setVisibility(View.GONE);
				jobListviewAdapter.notifyDataSetChanged();
				break;
			case Codes.getjobdetail:{
				
				showJob=(PartTimeJob)msg.obj;
				
				Intent intent = new Intent(JobActivity.this, ShowJobDetail.class);
			 Bundle mBundle = new Bundle();    
			        mBundle.putSerializable("job",showJob);    
			       intent.putExtras(mBundle);
		        
		        startActivity(intent);
			
				
				
	        }	
				
				
			break;
		
			case Codes.getjiajiaodetail:{
				
				showjiajiaoJob=(Jiajiao)msg.obj;
				
				Intent intent = new Intent(JobActivity.this, ShowJiajiaoDetail.class);
			 Bundle mBundle = new Bundle();    
			        mBundle.putSerializable("job",showjiajiaoJob);    
			       intent.putExtras(mBundle);
		        
		        startActivity(intent);
				
			}break;
				//查找失败
			case Codes.getjobFail:	
			
				Toast.makeText(JobActivity.this, "请检查网络设置！", Toast.LENGTH_LONG).show();			
				break;
				
			default:
				break;
			}
			super.handleMessage(msg);
		}
		
	};
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		if(pjob.size()!=0){
		for(int i = 0 ;i < 20 ;i++ )
		{
			savapjob.add(pjob.get((i)));
		}
		}
		if(type.equals("1"))
		FileIO.saveObjectToFile(JobActivity.this, URLs.xiaowaiDetailfile, savapjob);
		if(type.equals("2"))
			FileIO.saveObjectToFile(JobActivity.this, URLs.jiajiaoDetailfile, savapjob);
		if(type.equals("3"))
			FileIO.saveObjectToFile(JobActivity.this, URLs.xiaoneiDetailfile, savapjob);
		if(type.equals("4"))
			FileIO.saveObjectToFile(JobActivity.this, URLs.dongtaiDetailfile, savapjob);
		super.onDestroy();
	}
}
