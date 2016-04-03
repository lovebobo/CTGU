package com.ctguer;
/*此类为即时消息类,链接教务处的即时新闻*/

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout.LayoutParams;

import com.ctguer.controller.BackHandledFragment;
import com.ctguer.controller.Codes;
import com.ctguer.controller.FileIO;
import com.ctguer.controller.RTPullListView;
import com.ctguer.controller.RelateCtgu;
import com.ctguer.controller.URLs;
import com.ctguer.controller.RTPullListView.OnRefreshListener;
import com.ctguer.model.NewsContent;

public class InstantMessageFragment extends BackHandledFragment {

	private boolean status = false;
	//列表适配器
	listviewAdapter<NewsContent> listviewAdapter;
	//列表
	RTPullListView listView;
	//新闻简介列表
	private List<NewsContent> newsSummaryList =new ArrayList<NewsContent>();
	//对话框
	protected ProgressDialog progressDialog;
	
	//保存前20条
	private List<NewsContent> saveList =new ArrayList<NewsContent>();
	
	//界面视图对象
	private View contextView;
	//总页数
    public int pages = 1;
	
	//表示的是教务处的哪个栏目,0表示即时消息，1表示考试栏目
	private int type = 0;
	
	private ProgressBar moreprogress;//上拉加载更多按钮
	private View footerview;
    private Handler hanle = new Handler(){
  		@Override
  		public void handleMessage(Message msg) {
  			// TODO Auto-generated method stub
  			switch(msg.what){
  			case Codes.instantnews:
  				if(status == true){
  				pages = 1;
  				listView.onRefreshComplete();
  				newsSummaryList.clear();
  				status = false;
  				Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();	
  				if(msg.obj != null){
  					for(NewsContent temp:(ArrayList<NewsContent>)msg.obj){
  						newsSummaryList.add(temp);
  					}
  				}
  				}
  				else{
  					if(msg.obj != null){
  						
  						for(NewsContent temp:(ArrayList<NewsContent>)msg.obj){
  								newsSummaryList.add(temp);
  	  					}
  	  				}
  				}
  				if(moreprogress.getVisibility()==View.VISIBLE)
  				    moreprogress.setVisibility(View.GONE);
  				listviewAdapter.notifyDataSetChanged();
  				break;	
  			case Codes.schoolnewsdetail:
  				progressDialog.dismiss();
  				Intent todetail = new Intent();
  				Bundle data = new Bundle();
  				if(msg.obj != null){
  					data.putSerializable("detail", (String)msg.obj);
  				}
  				todetail.putExtras(data);
  				todetail.setClass(getActivity(), NewsWebviewActivity.class);
  				startActivity(todetail);
  				break;
  			}
  			super.handleMessage(msg);
  		}
      };
  	@Override
  	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
  		LayoutParams params = new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT);
  		contextView= inflater.inflate(R.layout.fragment_news, container, false);
  		footerview = inflater.inflate(R.layout.list_footview, null);
  		initdata();
  		findSchoolNews();
  		return contextView;
  	}
  	 @Override  
       public void onCreate(Bundle savedInstanceState) {  
            super.onCreate(savedInstanceState);          
       }
  	 
  	@Override
	public void onStart() {
		// TODO Auto-generated method stub
  		
		super.onStart();
	}
	private void initdata(){
  		moreprogress = (ProgressBar)footerview.findViewById(R.id.footer_progress);
  		// TODO Auto-generated method stub
  		listView=(RTPullListView)contextView.findViewById(R.id.news_listview);
  		progressDialog = new ProgressDialog(getActivity());
		progressDialog.setTitle("正在加载");
		progressDialog.setMessage("正在努力加载数据中，请耐心等候...");
  		Object ob = FileIO.getObjectFromFile(getActivity(), URLs.instantnewsfile);
  		if(ob != null){
  			newsSummaryList = (List<NewsContent>)ob;
  			Log.d("hh", "文件取出来");
  		}
  		if(newsSummaryList.size() >0){
  			listView.addFooterView(footerview);
  		}
  		//绑定适配器
  		listviewAdapter=new listviewAdapter<NewsContent>(getActivity(), R.layout.news_item, newsSummaryList);
  		listView.setAdapter(listviewAdapter);
    }
  	public void findSchoolNews(){
  		
  		listView.setonRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try{
						Thread.sleep(2000);
						status = true;
						RelateCtgu.getJiaowuNews(getActivity(),hanle, type, 1);
						}
						catch(Exception e){
							e.printStackTrace();
						}
					}
				}).start();	
			}
		});
  		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				NewsContent newid = listviewAdapter.getItem(position-1);
				progressDialog.show();
				
				RelateCtgu.getJiaowuNews_Detail(getActivity(), hanle, URLs.jiaowudetail + newid.getHref());
				
			}
		});
  		footerview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				status = false;
				moreprogress.setVisibility(View.VISIBLE);
				RelateCtgu.getJiaowuNews(getActivity(),hanle,type,++pages);
				
			}
		});
			
  		listView.setOnScrollListener(new AbsListView.OnScrollListener() {
  			boolean isLastView = false;
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				if(isLastView && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
					if(listView.getFooterViewsCount() == 0)
					listView.addFooterView(footerview);
					isLastView = false;
				}
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				if(firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 0){
					isLastView = true;
				}
				
			}
		});
  	}
  
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		if(newsSummaryList.size() > 0){
			for(int i = 0; i < 20; i++){
				saveList.add(newsSummaryList.get(i));
			}
			FileIO.saveObjectToFile(getActivity(), URLs.instantnewsfile, saveList);
		}
		super.onDestroyView();
	}
	
	
	@Override
	public boolean onBackPressed() {
		// TODO Auto-generated method stub
		

		return false;
	}


}
