package com.ctguer;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout.LayoutParams;

import com.ctguer.controller.Codes;
import com.ctguer.controller.FileIO;
import com.ctguer.controller.RTPullListView;
import com.ctguer.controller.RelateCtgu;
import com.ctguer.controller.URLs;
import com.ctguer.controller.RTPullListView.OnRefreshListener;
import com.ctguer.model.LostFoundDetail;
import com.umeng.analytics.MobclickAgent;

public class FoundFragment extends Fragment{


private int lastItem;
private View contextView;
private int type = 1;//1代表招领
protected ProgressDialog progressDialog;
private int startPage=1;
private List<LostFoundDetail> lost=new ArrayList<LostFoundDetail>();
private LostFoundDetail showlost = new LostFoundDetail();
private listviewAdapter<LostFoundDetail> lostListviewAdapter;
AlertDialog.Builder builder;
private RTPullListView lostlistView;//书籍列表
View footerView;
private ProgressBar moreProgressBar;
boolean statu = false;


private List<LostFoundDetail> savafound=new ArrayList<LostFoundDetail>();
Handler handler=new Handler()
{

	@Override
	public void handleMessage(Message msg) {
		
		//不管得到结果怎么样都让progressDialog.dismiss();

		// TODO Auto-generated method stub
		switch (msg.what) {
		case Codes.getLostSuc:		
			if(statu==true){
				lostlistView.onRefreshComplete();
				Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_LONG).show();
				lost.clear();
				statu = false;
				startPage=1;
			}
			if(msg.obj!=null)
			{

				for (LostFoundDetail tmp : (List<LostFoundDetail>)msg.obj) {
					lost.add(tmp);
				}
				
			}
			if(moreProgressBar.getVisibility()==View.VISIBLE)
			    moreProgressBar.setVisibility(View.GONE);
			lostListviewAdapter.notifyDataSetChanged();
			 
			break;
		
			//查找失败
		case Codes.getLostFail:
		case Codes.getLostDetailFail:
			if(statu == true){
				lostlistView.onRefreshComplete();
			}
			Toast.makeText(getActivity(), "请检查网络设置！", Toast.LENGTH_LONG).show();
			break;
			//获得图书详情
		case Codes.getLostDetailSuc:
			progressDialog.dismiss();
			showlost=(LostFoundDetail)msg.obj;
			showLostDetail();
			
		
			break;
			
		default:
			break;
		}
		super.handleMessage(msg);
	}
	
};
protected void showLostDetail() {
	// TODO Auto-generated method stub
	ArrayList<String> s = new ArrayList<String>();

	builder = new AlertDialog.Builder(getActivity());
	builder.setTitle("招领详情");
	
		s.add("\n物件名称：" + showlost.getName() + "\n物件特征：" + showlost.getDetail() + "\n丢失时间：" + showlost.getTime()
				+"\n联系电话："+showlost.getTele()+"\n姓名："+showlost.getPostName());

	
	int lenth = s.size();
	String[] a = new String[lenth];
	for (int i = 0; i < lenth; i++) {
		a[i] = s.get(i);
	}
	builder.setItems(a, null);
	builder.create().show();
}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		LayoutParams params = new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		contextView= inflater.inflate(R.layout.lostfound_fragment, container, false);  
		lostlistView = (RTPullListView) (contextView).findViewById(R.id.lostlist);
		 
		 
		 footerView = inflater.inflate(R.layout.list_footview, null);
			moreProgressBar = (ProgressBar) footerView.findViewById(R.id.footer_progress);
			

			 Object object= FileIO.getObjectFromFile(getActivity(), URLs.foundDetailfile);
			 
		
			 if(object!=null){
				 lost = (List<LostFoundDetail>) object;
			 }
			 
			if(lost.size() != 0){
				 lostlistView.addFooterView(footerView);
			}
		 
			
			
		findLost();
		binddate();
		return contextView;
	}


	private void binddate() {
		// TODO Auto-generated method stub
   lostListviewAdapter=new listviewAdapter<LostFoundDetail>(getActivity(), R.layout.lost_found_item, lost);
		
  lostlistView.setAdapter(lostListviewAdapter);
		
	}

	private void findLost() {
		// TODO Auto-generated method stub
		progressDialog = new ProgressDialog(getActivity());
		progressDialog.setTitle("正在加载");
		progressDialog.setMessage("正在努力加载数据中，请耐心等候...");
		
	
	lostlistView.setonRefreshListener(new OnRefreshListener() {
			
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
							RelateCtgu.lost_detail(getActivity(),handler,1,type);
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
				RelateCtgu.lost_detail(getActivity(),handler,++startPage,type);
				
			}
		});
			

		lostlistView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				LostFoundDetail lost = lostListviewAdapter.getItem(position-1);
				progressDialog.show();
				RelateCtgu.getlost_detail(getActivity(), handler, lost.getUrl(),type);
				
			}
		});
lostlistView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				if (lastItem > lostListviewAdapter.getCount() && scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
			      if(lostlistView.getFooterViewsCount() == 0)
					  lostlistView.addFooterView(footerView);
				
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
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub		
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onDestroy() {
		if(lost.size()!=0){
		for(int i = 0 ;i < 18 ;i++ )
		{
		savafound.add(lost.get((i)));
		}
		}
		
		FileIO.saveObjectToFile(getActivity(), URLs.foundDetailfile, savafound);
		super.onDestroy();
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

