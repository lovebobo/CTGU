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

	AlertDialog verCode;//��֤���
	
	EditText stuNumEdiText;//ѧ�ű༭��
	
	EditText pasNumEdiText;//�����
	
	ImageButton imgbtn;//��֤��ͼ��
	
	EditText et;//������֤���
	
	Button verbtn;//��֤��ť
	
	int type;// 0���� 1�ɼ� 2�α� 3����
	
	ProgressDialog progressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login_ctgu);
		
		taskPool = new NetworkTask();	//ʵ����taskpool
		try {
			type = (int) getIntent().getExtras().get("type");
		}catch (NullPointerException e) {//
			type = 0;
		} 
		catch (Exception e) {// NullPointerException
			type = 0;
		}
		
		findById();//�ҳ�Xml�ؼ�����
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
            	Toast.makeText(LoginCtgu.this, "�л������ý���ϵͳ", Toast.LENGTH_SHORT).show();
            }
            break;
            
            case Codes.getVerificationCodeErr:
            {
            	Toast.makeText(LoginCtgu.this, "��֤���ȡʧ��,����ˢ��!", Toast.LENGTH_SHORT).show();
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
					Toast.makeText(LoginCtgu.this, "��½�ɹ�,���ڲ�ѯ�ɼ�...", Toast.LENGTH_SHORT).show();
					RelateCtgu.getScoreInfo(getApplicationContext(),handler, true, "", "");// �꣬ѧ�ڻ��е�����
					break;			
				case 2:
					Toast.makeText(LoginCtgu.this, "��½�ɹ�,���ڻ�ȡ�α�...", Toast.LENGTH_SHORT).show();
					RelateCtgu.getCourseTable(getApplicationContext(),handler, true, "2015", "1");//String.valueOf(Management.ThisTerm)
					break;
				case 3:
					Toast.makeText(LoginCtgu.this, "��½�ɹ�,���ڻ�ȡ������Ϣ...", Toast.LENGTH_SHORT).show();
 					RelateCtgu.getEvaluationInfo(handler);//����
					break;
				}
			}
			break;
			
            case Codes.loginJwcErr:{
				if(progressDialog!=null) progressDialog.dismiss();
				if(verCode!=null) {
					verCode.dismiss();
					}
				Toast.makeText(LoginCtgu.this,"�����ԣ��������δ����", Toast.LENGTH_SHORT).show();
			}
			break;
			
			//��ȡ�ɼ��ɹ�
            case Codes.getScoreInfo:
			{
				Toast.makeText(LoginCtgu.this,"��ȡ�ɼ��ɹ����뷵���ҵĳɼ�����鿴����", Toast.LENGTH_SHORT).show();
				progressDialog.dismiss();
				verCode.dismiss();
		        LoginCtgu.this.finish();
		        
		    }
			break;
			
			//��ȡ��Ϣʧ��
            case Codes.getScoreInfoErr:
			{
				//�Ժ����ʧ����Ϣ
		    }
			break;
			
            case Codes.getCourseTable://�ɹ��õ��α�
			{
				FileIO.saveObjectToFile(LoginCtgu.this, URLs.courseTables, msg.obj);
				
				Toast.makeText(getApplicationContext(), "�õ��α����뷵���ҵĿα�鿴", Toast.LENGTH_SHORT).show();
				progressDialog.dismiss();
				verCode.dismiss();
				LoginCtgu.this.finish();
			}
			break;
			
            case Codes.CodeErr://�α���ȡʧ��
            	progressDialog.dismiss();
				verCode.dismiss();
				
				
        	case Codes.getEvaluationInfo://������Ϣ
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
	
	//�ҳ�Xml�ؼ�
		private void findById() {
			// TODO Auto-generated method stub
			stuNumEdiText=(EditText)findViewById(R.id.username_edit);
			pasNumEdiText=(EditText)findViewById(R.id.password_edit);
			
		}

	//��½���񴦵�½��ť
	public void ctgu_onclick_login(View view)
	{
		if(stuNumEdiText.getText().toString().length()<1||pasNumEdiText.getText().toString().length()<1)
		{
			Toast.makeText(LoginCtgu.this, "��Ϣû����д����!", Toast.LENGTH_SHORT).show();			
			return;
		}
		
		else {
			View Dialog = LayoutInflater.from(this).inflate(
					R.layout.pop_login, null);
			
			imgbtn = (ImageButton) Dialog.findViewById(R.id.UI_VerImg);
			et = (EditText) Dialog.findViewById(R.id.UI_VerTxt);
			verbtn = (Button) Dialog.findViewById(R.id.UI_VerBtn);//��֤�밴ť
			getVerificationCode();
			verCode = new AlertDialog.Builder(this).setView(Dialog).show();	
			
			imgbtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					getVerificationCode();
					Toast.makeText(LoginCtgu.this, "ˢ����֤��!",
							Toast.LENGTH_SHORT).show();
				}
			});
			
			verbtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (et.getText().length() > 0) {
						progressDialog = ProgressDialog.show(
								LoginCtgu.this, "�����������",
								"���ĺͺ����ܻ�õ������\n��������˹̹", true, true);
						RelateCtgu.loginJwc_Ctgu(handler, et.getText()
								.toString(), stuNumEdiText.getText().toString(),
								pasNumEdiText.getText().toString(), type);
																	
					}
					else {
						Toast.makeText(LoginCtgu.this, "Ҫ������֤��Ŷ!",
								Toast.LENGTH_SHORT).show();
					}
				}
			});
		}
	}

	
	//��ȡ��֤�뺯��
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
				System.out.println("��ȡ��֤��ʧ�ܣ�" + msg);
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
					Utility.sendMsg(handler,Codes.getVerificationCodeErr, null);//�������������Ҫ���ݣ���������sendmsg
			}
		});
	}
	
	
	
}
