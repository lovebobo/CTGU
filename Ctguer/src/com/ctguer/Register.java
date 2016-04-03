package com.ctguer;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends Activity {

 	private RelativeLayout relativeLayoutregist;//注册布局
 	private RelativeLayout relativeLayouttijiao;//验证码布局
 	private EditText edittext_phone;//手机号码
 	private EditText edittext_pas;//密码
 	private EditText edittext_verfy;//验证码
 	private TextView textview_phone ;//
 	
 	private Button regist_button;//注册
 	private Button tijiao_button;//提交
 	private Button verfy_button;//验证码
 	
 	private String verfyCode;//得到的验证码
 	private boolean ready=false;
 	private int time=30;
 	
 	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register);
		
		initData();
		//
	}

	//初始化数据
	private void initData() {
		//初始化短信相关
		
		
		// TODO Auto-generated method stub
		relativeLayoutregist=(RelativeLayout)findViewById(R.id.ctgu_relativelout);
		relativeLayouttijiao=(RelativeLayout)findViewById(R.id.ctgu_relativelout_verfy);
		edittext_phone=(EditText)findViewById(R.id.username_edit);
		edittext_pas=(EditText)findViewById(R.id.password_edit);
		edittext_verfy=(EditText)findViewById(R.id.password_edit_verfy);
		textview_phone=(TextView)findViewById(R.id.username_TextView);
		regist_button=(Button)findViewById(R.id.regist_button);
		regist_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(edittext_phone.length()!=11)
				{
					Toast.makeText(getBaseContext(), "手机号码有误!", Toast.LENGTH_LONG).show();
					return;
				}
				if(edittext_pas.length()<1)
				{
					Toast.makeText(getBaseContext(), "请输入密码!", Toast.LENGTH_LONG).show();
					return;
				}
			
				verfy_button.setEnabled(false);
				time=30;
				relativeLayoutregist.setVisibility(View.INVISIBLE);
				relativeLayouttijiao.setVisibility(View.VISIBLE);
				//传电话号码
				textview_phone.setText(edittext_phone.getText().toString());
				
				
				//Todo  发送短信,获取验证码
				initSDK();
				SMSSDK.getVerificationCode("86",textview_phone.getText().toString());
					
				handler.postDelayed(runnable, 1000); 
				 
			}
		});
		
		tijiao_button=(Button)findViewById(R.id.tijiao_button);
		tijiao_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(edittext_verfy.getText().toString().length()<1)
				{
					Toast.makeText(getBaseContext(), "请填写验证码", Toast.LENGTH_LONG).show();
				}
				else {
					verfyCode=edittext_verfy.getText().toString();
					SMSSDK.submitVerificationCode("86", textview_phone.getText().toString(),verfyCode);
				}
			}
		});
		verfy_button=(Button)findViewById(R.id.huoqu_button);
		
		verfy_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				time=30;
				verfy_button.setEnabled(false);
				handler.postDelayed(runnable, 1000); 
				initSDK();
				SMSSDK.getVerificationCode("86",textview_phone.getText().toString());
				
			}
		});
	}
	
	protected void initSDK() {
		// TODO Auto-generated method stub
		SMSSDK.initSDK(this,"672e0e8c7203","c3728cc22e8e1d75501de2a100b4586c");
		EventHandler eventHandler = new EventHandler() {
			public void afterEvent(int event, int result, Object data) {
				Message msg = new Message();
				msg.arg1 = event;
				msg.arg2 = result;
				msg.obj = data;
				handler.sendMessage(msg);
			}
		};
	
		SMSSDK.registerEventHandler(eventHandler);
		ready=true;
	}

		//handle的定义
	 	Handler handler = new Handler()
	 	{
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				int event = msg.arg1;
				int result = msg.arg2;
				Object data = msg.obj;
				if (result == SMSSDK.RESULT_COMPLETE) {
					
					if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
						Toast.makeText(getApplicationContext(), "验证成功", Toast.LENGTH_SHORT).show();
						
					} else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
						Toast.makeText(getApplicationContext(), "验证码已经发送", Toast.LENGTH_SHORT).show();
						
					}
					else {
						Toast.makeText(getBaseContext(), "请检查网络原因", Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(getBaseContext(), "请检查网络原因", Toast.LENGTH_SHORT).show();
					((Throwable) data).printStackTrace();
				}				
			}
	 		
	 	};  
	 	
	    Runnable runnable = new Runnable() {  
	        @Override  
	        public void run() {  
	            time--;  
	            if(time>0){
	            	
	            	verfy_button.setText(time+"s后重新发送");  
	            	handler.postDelayed(this, 1000);  
	            }
	            else{
	            	verfy_button.setText("重新发送"); 
	            	verfy_button.setEnabled(true);
	            }
	        }  
	    };  

	
	    protected void onDestroy() {
			if (ready) {
				SMSSDK.unregisterAllEventHandler();
			}
			super.onDestroy();
		}		
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if(relativeLayoutregist.getVisibility()==View.INVISIBLE)
		{
			relativeLayoutregist.setVisibility(View.VISIBLE);
			relativeLayouttijiao.setVisibility(View.INVISIBLE);
		}
		else {
			super.onBackPressed();
		}

	}		
	
	 
	
}
