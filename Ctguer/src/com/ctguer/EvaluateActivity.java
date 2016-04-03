package com.ctguer;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


import com.ctguer.controller.Codes;
import com.ctguer.controller.NetworkTask;
import com.ctguer.controller.RelateCtgu;
import com.ctguer.controller.Utility;
import com.ctguer.model.EvaluateTeaching;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class EvaluateActivity extends BaseActivity {

	List<EvaluateTeaching> EList=new ArrayList<EvaluateTeaching>();
	
	String MenuStr="�ύ";
	String[] ScoreList = new String[6];
	private Menu BarMenu=null;
	ProgressDialog progressDialog;
	private NetworkTask taskPool;
	public  List<EvaluateTeaching> evaluation = new LinkedList<EvaluateTeaching>();
	ListView listView;
	private SimpleArrayAdapter<EvaluateTeaching> EvaluateAdapter;
	
	Handler handler = new Handler()
	{
		@Override 
		public void handleMessage(Message msg)
		{   
			switch (msg.what){   
	            case Codes.getEvaluationInfo:
				{
					loadData();
					EvaluateAdapter.notifyDataSetChanged();
				}
					break;
	            case Codes.CodeSuc:
				{
					progressDialog.setMessage("�����ύ:"
							+ msg.obj);
				}
					break;
				default:
					break;
	            }   
	           super.handleMessage(msg);  
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.activity_evaluate);
		
		listView = (ListView)findViewById(R.id.list);
		
		for (int i = 1; i <= 5; i++)
			ScoreList[i] = "" +(i*8);
		ScoreList[0] = "��";	
	
		progressDialog = new ProgressDialog(this);
	
		EvaluateAdapter=new SimpleArrayAdapter<EvaluateTeaching>(this,
				R.layout.list_class, EList);
		listView.setAdapter(EvaluateAdapter);
		loadData();
	}
	
	private void loadData(){
		
		 evaluation = ( List<EvaluateTeaching>)getIntent().getSerializableExtra("list");
		
		
			if (evaluation != null & evaluation.size() != 0)
			{
				EList.clear();
				for(EvaluateTeaching e: evaluation)
				{
					EList.add(e);
				}
				EvaluateTeaching end=new EvaluateTeaching();
				end.isEvaluated=false;
				end.CourseName="ͳһ����,��������";
				end.Teacher="��д��������ύ����";
				end.id="0";
				EList.add(end);
			}
			else
				Toast.makeText(this, "û�пγ̽���,��������",
						Toast.LENGTH_SHORT).show();
			
			
			
		
	}





	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		progressDialog.dismiss();
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
		MenuItem mi=menu.add(MenuStr);
		mi.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        	
        	if(EList.size()<1) {
        		Toast.makeText(this, "û����ʦʣ��Ŷ!",
						Toast.LENGTH_SHORT).show();
        		return true;
        		}
        	progressDialog=ProgressDialog.show(
    				this, "Ŭ��������",
    				"��֮�ָߣ���֮�ּᡣ\n��������������Ӻ���", true, true);
        	processEvaluation();
        
        
        return true;
    }

    private void processEvaluation(){       
    	int last=EList.get(EList.size()-1).Value;
    	final List<EvaluateTeaching> pList=new ArrayList<EvaluateTeaching>();
    	if(last!=0){//ͳһ����
    		for(EvaluateTeaching e:EList)
        		if(!e.isEvaluated&&!e.id.equals("0")){
        			e.Value=last;
        			pList.add(e);
        			}
    	}else{
	    	for(EvaluateTeaching e:EList)
	    		if(!e.isEvaluated && e.Value!=0){
	    			pList.add(e);
	    			}
    	}
    	if(pList.size()<1) {
    		progressDialog.dismiss();
    		Toast.makeText(this, "û����ʦʣ��Ŷ!",
					Toast.LENGTH_SHORT).show();
    		return;}
    	
    	new Thread(new Runnable(){
    		//int CountPer=5;
    		int Count=0;
    		//int CountRec=0;
    		//int CountSuc=0;
    		WeakReference<Handler>th_handler;
			@Override
			public void run() {
				
		        Looper.prepare();//��ʼ����Ϣѭ�����У���Ҫ��Handler����֮ǰ
		        Handler mChildHandler = new Handler(){

					@Override
					public void handleMessage(Message msg) {
						//CountRec++;
						switch (msg.what)
			            {   
			            case Codes.getEvaluationInfo:
			            	
			            case Codes.getEvaluationInfoErr:
						default:
							Count++;
							break;
			            }   
						
						if (Count < pList.size()) {
								RelateCtgu.getCourseEvaluationDetail(th_handler, pList.get(Count));
								Utility.sendMsg(handler, Codes.CodeSuc,pList.get(Count).CourseName);
						}
						else
						//if(CountRec>=pList.size())
						{			    		
				    		RelateCtgu.getEvaluationInfo(handler);
				    		progressDialog.dismiss();
				    		Toast.makeText(EvaluateActivity.this, "�������!",
									Toast.LENGTH_SHORT).show();
				    	}
						super.handleMessage(msg);
					}
		        	
		        };
		        th_handler=new WeakReference<Handler>(mChildHandler);	        
		        //for(int i=0;i<CountPer&&i<pList.size();i++,Count++){
		        	RelateCtgu.getCourseEvaluationDetail(th_handler,pList.get(Count));//.PostToEvaluation(w_handler,pList.get(Count));
		        	Utility.sendMsg(handler, Codes.CodeSuc,pList.get(Count).CourseName);
		        //}
		        Looper.loop();//�������߳���Ϣѭ������
		    	
	            
				
			}
    		
    	}).start();
    	
            
    }
    

	class SimpleArrayAdapter<T> extends ArrayAdapter<T> {
		private Context mContext;
		private int mViewResourceID = 0;
		private List<T> mViewList;

		public SimpleArrayAdapter(Context context, int resource, List<T> objects) {
			super(context, resource, objects);
			mContext = context;
			mViewResourceID = resource;
			mViewList = objects;
		}

		@Override
		public boolean areAllItemsEnabled() {
			return false;
		}
		
		@Override
		public int getCount() {
			
			return mViewList.size();
		}

		@Override
		public T getItem(int position) {
			return mViewList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						mViewResourceID, null);
			}

			if (mViewList.get(0).getClass() == EvaluateTeaching.class)
				bindingEvaluate((EvaluateTeaching) mViewList.get(position),
						convertView,position);
		
			return convertView;
		}
		
		private void bindingEvaluate(EvaluateTeaching item, View convertView,int position) {
		
			ArrayAdapter<String> ad= new
			ArrayAdapter<String>(convertView.getContext(),android.R.layout.simple_spinner_item,ScoreList);
		
			
			
			TextView textView = (TextView)convertView.findViewById(R.id.UI_Item_text);
			textView.setText(item.CourseName);
			TextView textView2 = (TextView)convertView.findViewById(R.id.UI_Item_text_detail);
			textView2.setText(item.Teacher);
			TextView textView3  =   (TextView)convertView.findViewById(R.id.UI_Is_True);
			Spinner sp=((Spinner)convertView.findViewById(R.id.UI_Evaluate));
			if(item.isEvaluated){
				
				textView3.setText("��");
				textView3.setVisibility(View.VISIBLE);
				sp.setVisibility(View.GONE);
				
			}
			else {
				textView3.setVisibility(View.GONE);
				
				sp.setVisibility(View.VISIBLE);
				sp.setAdapter(ad);
				sp.setTag(position);
				sp.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						int pos=(int)parent.getTag();
						EList.get(pos).Value=position*8;
					}
					@Override
					public void onNothingSelected(AdapterView<?> parent) {	
					}
				});
				
				
			}
			
		}
		
		

	}

}
