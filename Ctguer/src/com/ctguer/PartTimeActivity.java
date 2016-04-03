package com.ctguer;

import java.util.ArrayList;

import com.ctguer.controller.ImageInfo;
import com.ctguer.controller.MyAdapter;




import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;


public class PartTimeActivity extends BaseActivity {
	 ArrayList<ImageInfo> data; // �˵�����
	 GridView grid;
	 
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        setContentView(R.layout.activity_part_time);
	      
	        // ��ʼ������
	        initData();
	       grid = (GridView) findViewById(R.id.gridView1) ;
	       grid.setNumColumns(2);
			grid.setVerticalSpacing(5);
			grid.setHorizontalSpacing(5);
	 
	     grid.setAdapter(new MyAdapter<ImageInfo>(PartTimeActivity.this,R.layout.grid_item,data));
	        
	     grid.setOnItemClickListener(new OnItemClickListener() {

	    		@Override
	    		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	    			// TODO Auto-generated method stub
	    			Intent intent ;
	    			switch (arg2) {
	    			case 0:
	    				
	    				break;
	    			case 1:
	    				 intent = new Intent(PartTimeActivity.this, JobActivity.class);
	    				intent.putExtra("type", "1");
	    				startActivity(intent);
	    				break;
	    				
	    			case 2:
	    				intent = new Intent(PartTimeActivity.this, JobActivity.class);
	    				intent.putExtra("type", "2");
	    				startActivity(intent);
	    				
	    				break;
	    			case 3:
	    				intent = new Intent(PartTimeActivity.this, JobActivity.class);
	    				intent.putExtra("type", "3");
	    				startActivity(intent);
	    				break;
	    			case 4:
	    				intent = new Intent(PartTimeActivity.this, JobActivity.class);
	    				intent.putExtra("type", "4");
	    				startActivity(intent);
	    				break;
	    			default:
	    				break;
	    			}
	    			
	    		}
	    	});
	    }
	 
	    private void initData() {
	        data = new ArrayList<ImageInfo>();
	       
	        data.add(new ImageInfo("������ְ", R.drawable.icon1, R.drawable.icon_bg02));
	        data.add(new ImageInfo("У���ְ", R.drawable.icon2, R.drawable.icon_bg02));
	        data.add(new ImageInfo("�ҽ���Ϣ", R.drawable.icon3, R.drawable.icon_bg02));
	        data.add(new ImageInfo("У����Ƹ", R.drawable.icon4, R.drawable.icon_bg02));
	        data.add(new ImageInfo("�ڹ���̬", R.drawable.icon5, R.drawable.icon_bg02));
	        data.add(new ImageInfo("����", R.drawable.icon6, R.drawable.icon_bg02));
	      
	      
	    }
	 
	
	    
	 
}
