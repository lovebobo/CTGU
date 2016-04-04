package com.ctguer;

import com.ctguer.controller.Codes;
import com.ctguer.controller.FileIO;
import com.ctguer.controller.RelativeUser;
import com.ctguer.controller.URLs;
import com.ctguer.model.Management;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LoginAccount extends Activity {

	private TextView textView;//现在注册
	private EditText editText_usename;//用户名
	private EditText editText_password;//密码
	private Button button_Login;//登陆按钮
	
	private String usename="";
	private String passwd="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_account);
		
		
		initView();
		initData();
	}
	
	Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case Codes.login_app_Suc:
				//TODO 用文件存储用户信息
				FileIO.saveObjectToFile(LoginAccount.this, URLs.uresfile, msg.obj);
				
				Toast.makeText(getApplicationContext(), "登陆成功", Toast.LENGTH_SHORT).show();
				/*SharedPreferences sharedPreferences1 = getSharedPreferences("ifLoginStatus", getApplicationContext().MODE_PRIVATE); //私有数据
				Editor editor = sharedPreferences1.edit();//获取编辑器
				Management.curUser.setLoginstatusBoolean(false);
				editor.putBoolean("status", Management.curUser.loginstatusBoolean);
				editor.commit();//提交修改
*/				startActivity(new Intent(LoginAccount.this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
				break;
			case Codes.login_app_Fail:
				Toast.makeText(getApplicationContext(), "登陆失败，检查用户名和密码", Toast.LENGTH_SHORT).show();
				break;
			}
		}
		
	};
	
	
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		 finish();  
	     System.exit(0);  
	}

	private void initData() {
		// TODO Auto-generated method stub
		usename=getIntent().getStringExtra("username");
		passwd=getIntent().getStringExtra("password");
		
		editText_usename.setText(usename);
		editText_password.setText(passwd);
	}

	private void initView() {
		// TODO Auto-generated method stub
		editText_usename=(EditText)findViewById(R.id.account_login_name);
		editText_password=(EditText)findViewById(R.id.account_login_pw);
		textView=(TextView)findViewById(R.id.login_textview);
		textView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(LoginAccount.this,Register.class));
			}
		});
		
		button_Login=(Button)findViewById(R.id.login_button);
		button_Login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				RelativeUser.handleLogin(handler, editText_usename.getText().toString(), editText_password.getText().toString());
			}
		});
	}
}
