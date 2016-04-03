package com.ctguer;

import java.io.InputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;




import com.ctguer.controller.AbsHttpTask;
import com.ctguer.controller.Codes;
import com.ctguer.controller.FileIO;
import com.ctguer.controller.NetworkTask;
import com.ctguer.controller.RelateCtgu;
import com.ctguer.controller.URLs;
import com.ctguer.controller.Utility;
import com.ctguer.model.EvaluateTeaching;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class LoginCtgu extends BaseActivity {

	
	
	public  List<EvaluateTeaching> evaluation = new LinkedList<EvaluateTeaching>();
	private NetworkTask taskPool;

	AlertDialog verCode;//验证码框
	
	EditText stuNumEdiText;//学号编辑框
	
	EditText pasNumEdiText;//密码框
	
	ImageButton imgbtn;//验证码图标
	
	EditText et;//输入验证码框
	
	Button verbtn;//验证按钮
	
	int type;// 0错误 1成绩 2课表 3评教
	
	ProgressDialog progressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login_ctgu);
		
		taskPool = new NetworkTask();	//实例化taskpool
		try {
			type = (int) getIntent().getExtras().get("type");
		}catch (NullPointerException e) {//
			type = 0;
		} 
		catch (Exception e) {// NullPointerException
			type = 0;
		}
		
		findById();//找出Xml控件函数
	}
	
	private Handler handler = new Handler()
	{
		@Override 
		public void handleMessage(Message msg)
		{   
			switch (msg.what)
            {   
            case Codes.getVerificationCode:
			{
				Bitmap bitmap = (Bitmap) msg.obj;
				imgbtn.setImageBitmap(bitmap);
			}
			break;
			
            case Codes.loginSwitchJwc:
            {
            	Toast.makeText(LoginCtgu.this, "切换至备用教务系统", Toast.LENGTH_SHORT).show();
            }
            break;
            
            case Codes.getVerificationCodeErr:
            {
            	Toast.makeText(LoginCtgu.this, "验证码获取失败,请点击刷新!", Toast.LENGTH_SHORT).show();
            }
            break;
            
            case Codes.returnmsg:
			{
				progressDialog.dismiss();
				getVerificationCode();
				Toast.makeText(LoginCtgu.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
			}
			break;
			
            case Codes.loginJwc:
			{
				int next=(int) msg.obj;
				switch(next){
				case 1:
					Toast.makeText(LoginCtgu.this, "登陆成功,正在查询成绩...", Toast.LENGTH_SHORT).show();
					RelateCtgu.getScoreInfo(getApplicationContext(),handler, true, "", "");// 年，学期还有点问题
					break;			
				case 2:
					Toast.makeText(LoginCtgu.this, "登陆成功,正在获取课表...", Toast.LENGTH_SHORT).show();
					RelateCtgu.getCourseTable(getApplicationContext(),handler, true, "2015", "1");//String.valueOf(Management.ThisTerm)
					break;
				case 3:
					Toast.makeText(LoginCtgu.this, "登陆成功,正在获取评教信息...", Toast.LENGTH_SHORT).show();
 					RelateCtgu.getEvaluationInfo(handler);//评教
					break;
				}
			}
			break;
			
            case Codes.loginJwcErr:{
				if(progressDialog!=null) progressDialog.dismiss();
				if(verCode!=null) {
					verCode.dismiss();
					}
				Toast.makeText(LoginCtgu.this,"请重试，教务可能未开放", Toast.LENGTH_SHORT).show();
			}
			break;
			
			//获取成绩成功
            case Codes.getScoreInfo:
			{
				Toast.makeText(LoginCtgu.this,"获取成绩成功，请返回我的成绩界面查看！！", Toast.LENGTH_SHORT).show();
				progressDialog.dismiss();
				verCode.dismiss();
		        LoginCtgu.this.finish();
		        
		    }
			break;
			
			//获取信息失败
            case Codes.getScoreInfoErr:
			{
				//稍后给出失败信息
		    }
			break;
			
            case Codes.getCourseTable://成功拿到课表
			{
				FileIO.saveObjectToFile(LoginCtgu.this, URLs.courseTables, msg.obj);
				
				Toast.makeText(getApplicationContext(), "拿到课表咯，请返回我的课表查看", Toast.LENGTH_SHORT).show();
				progressDialog.dismiss();
				verCode.dismiss();
				LoginCtgu.this.finish();
			}
			break;
			
            case Codes.CodeErr://课表拉取失败
            	progressDialog.dismiss();
				verCode.dismiss();
				
				
        	case Codes.getEvaluationInfo://评教信息
			{
				progressDialog.dismiss();
				verCode.dismiss();
				if(msg.obj!=null)
				{

					for (EvaluateTeaching tmp : (List<EvaluateTeaching>)msg.obj) {
						evaluation.add(tmp);
					}
					
				}
				Intent intent = new Intent(LoginCtgu.this, EvaluateActivity.class);
				 Bundle mBundle = new Bundle();    
			        mBundle.putSerializable("list",(Serializable) evaluation);    
			       intent.putExtras(mBundle);  
		        intent.putExtra("type", 3);
		        startActivity(intent);
				LoginCtgu.this.finish();
				
				
	        }	
			break;
				
			default:
				break;
            }   
           super.handleMessage(msg);  
		}
	};
	
	//找出Xml控件
		private void findById() {
			// TODO Auto-generated method stub
			stuNumEdiText=(EditText)findViewById(R.id.username_edit);
			pasNumEdiText=(EditText)findViewById(R.id.password_edit);
			
		}

	//登陆教务处登陆按钮
	public void ctgu_onclick_login(View view)
	{
		if(stuNumEdiText.getText().toString().length()<1||pasNumEdiText.getText().toString().length()<1)
		{
			Toast.makeText(LoginCtgu.this, "信息没有填写完整!", Toast.LENGTH_SHORT).show();			
			return;
		}
		
		else {
			View Dialog = LayoutInflater.from(this).inflate(
					R.layout.pop_login, null);
			
			imgbtn = (ImageButton) Dialog.findViewById(R.id.UI_VerImg);
			et = (EditText) Dialog.findViewById(R.id.UI_VerTxt);
			verbtn = (Button) Dialog.findViewById(R.id.UI_VerBtn);//验证码按钮
			getVerificationCode();
			verCode = new AlertDialog.Builder(this).setView(Dialog).show();	
			
			imgbtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					getVerificationCode();
					Toast.makeText(LoginCtgu.this, "刷新验证码!",
							Toast.LENGTH_SHORT).show();
				}
			});
			
			verbtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (et.getText().length() > 0) {
						progressDialog = ProgressDialog.show(
								LoginCtgu.this, "接入教务网中",
								"耐心和恒心总会得到报酬的\n——爱因斯坦", true, true);
						RelateCtgu.loginJwc_Ctgu(handler, et.getText()
								.toString(), stuNumEdiText.getText().toString(),
								pasNumEdiText.getText().toString(), type);
																	
					}
					else {
						Toast.makeText(LoginCtgu.this, "要输入验证码哦!",
								Toast.LENGTH_SHORT).show();
					}
				}
			});
		}
	}

	
	//获取验证码函数
	private void getVerificationCode() {
		// TODO Auto-generated method stub
		this.taskPool.addHttpPostTask(URLs.getURL(URLs.verificationCodeImg), null, new AbsHttpTask()
		{
			@Override
			public void onComplete(InputStream paramInputStream)
			{
				Bitmap localBitmap = BitmapFactory.decodeStream(paramInputStream);
				Utility.sendMsg(handler,Codes.getVerificationCode, localBitmap);
			}
			
			@Override
			public void onError(Object msg)
			{
				System.out.println("获取验证码失败！" + msg);
				if(++NetworkTask.ConnectCount < NetworkTask.ConnectMax){
					getVerificationCode();
					Utility.sendMsg(handler,Codes.loginSwitchJwc, null);
					}
				else
					Utility.sendMsg(handler,Codes.getVerificationCodeErr, null);
			}
			
			@Override
			public void onError()
			{
				System.out.println("getver failed");
				if(++NetworkTask.ConnectCount < NetworkTask.ConnectMax){
					getVerificationCode();
					Utility.sendMsg(handler,Codes.loginSwitchJwc, null);
					}
				else
					Utility.sendMsg(handler,Codes.getVerificationCodeErr, null);//后面参数根据需要传递，或者重载sendmsg
			}
		});
	}
	
	
	
}
