package com.ctguer;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ctguer.controller.Codes;
import com.ctguer.controller.FileIO;
import com.ctguer.controller.RelativeUser;
import com.ctguer.controller.URLs;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LaunchActivity extends Activity {

	private Button launchButton;
	private EditText editTextTheme;
	private EditText editTextPlace;
	private EditText editTextTime;
	private EditText editTextNum;
	private EditText editTextDescribe;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launch);
		
		initView();
		initData();
	}


	private void initData() {
		// TODO Auto-generated method stub
		
	}


	private void initView() {
		// TODO Auto-generated method stub
		launchButton=(Button)findViewById(R.id.launchactivity_button);
		launchButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(editTextTheme.getText().toString().trim().length()<1)
				{
					Toast.makeText(LaunchActivity.this, "主题不能为空", Toast.LENGTH_SHORT).show();
				}
				
				else if(editTextPlace.getText().toString().trim().length()<1)
				{
					Toast.makeText(LaunchActivity.this, "地点不能为空", Toast.LENGTH_SHORT).show();
				}
				
				else if(editTextTime.getText().toString().trim().length()<1)
				{
					Toast.makeText(LaunchActivity.this, "时间不能为空", Toast.LENGTH_SHORT).show();
				}
				
				else if(editTextNum.getText().toString().trim().length()<1)
				{
					Toast.makeText(LaunchActivity.this, "人数不能为空", Toast.LENGTH_SHORT).show();
				}
				else if(editTextDescribe.getText().toString().trim().length()<1)
				{
					Toast.makeText(LaunchActivity.this, "描述内容不得少于20字", Toast.LENGTH_SHORT).show();
				}
				else{
					SimpleDateFormat    formatter    =   new    SimpleDateFormat    ("yyyy年MM月dd日    HH:mm:ss     ");       
					Date    curDate    =   new    Date(System.currentTimeMillis());//获取当前时间       
					String    str    =    formatter.format(curDate);       
					
					String string=FileIO.getObjectFromFile(LaunchActivity.this, URLs.uresfile).toString();
					Object object = RelativeUser.getNameFromJson(string, "Id");
					int Id=Integer.parseInt(object.toString());
					
					RelativeUser.launchActivity(handler, Id, Integer.parseInt(editTextNum.getText().toString()), editTextDescribe.getText().toString(), 
							editTextTheme.getText().toString(), editTextTheme.getText().toString(), editTextTime.getText().toString(), str);
				}
				
			}
		});
		
		editTextTheme=(EditText)findViewById(R.id.launchactivity_2);
		editTextPlace=(EditText)findViewById(R.id.launchactivity_5);
		editTextTime=(EditText)findViewById(R.id.launchactivity_8);
		editTextNum=(EditText)findViewById(R.id.launchactivity_11);
		editTextDescribe=(EditText)findViewById(R.id.launchactivity_13);
	}
	
	Handler handler=new Handler()
	{

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case Codes.launchActivityListSuc:
				Toast.makeText(LaunchActivity.this, "发布活动成功", Toast.LENGTH_SHORT).show();
				
				Intent intent =new Intent(LaunchActivity.this,com.ctguer.model.Activity.class);
				LaunchActivity.this.setResult(Codes.launchActivityListSuc);
				finish();
				break;

			case Codes.launchActivityListFail:
				Toast.makeText(LaunchActivity.this, "发布活动失败", Toast.LENGTH_SHORT).show();
				break;
			}
		}
		
	};
}
