package com.ctguer;

import com.ctguer.controller.DataCleanManager;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;

import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

public class About_Us extends Activity {

	
	private ProgressDialog progressDialog;
	
	final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
	
	// wx42df77539166bdbd������΢�ſ���ƽ̨ע��Ӧ�õ�AppID, ������Ҫ�滻����ע���AppID
	private String appID = "wx372ac6b22e5d1be8";
	private String appSecret = "b0360e3f2485cd22c5543e7b07d21ee0";
	
	
	private String ShareStr="Ctguer,���������"; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_about__us);
		//���˳�ʼ������
		initShare();  
		UmengUpdateAgent.setUpdateOnlyWifi(false);
		UmengUpdateAgent.setUpdateAutoPopup(true);    //�Ƿ��Զ��������¶Ի��� 
		UmengUpdateAgent.setDownloadListener(null);    //�����°汾�����¼�����������Ϊ null 
		UmengUpdateAgent.setDialogListener(null);		
		UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() { 				       
			@Override
			public void onUpdateReturned(int updateStatus,
					UpdateResponse updateInfo) {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				if(progressDialog!=null)
				 progressDialog.dismiss();
				 switch (updateStatus) { 
                 case 0: // �и��� 
                 UmengUpdateAgent.showUpdateDialog(getBaseContext(), updateInfo); 
                 	break; 
                 case 1: // �޸��� 
                     Toast.makeText(getBaseContext(), "���޸���", Toast.LENGTH_LONG).show();           
                	 break; 
                 case 2: // �������Ϊwifi�¸�����wifi�޷���ʱ���� 
                	 Toast.makeText(getBaseContext(), "����wifi�¸���", Toast.LENGTH_LONG).show();     
                	 break; 
                 case 3: // ���ӳ�ʱ 
                	 Toast.makeText(getBaseContext(), "���ӳ�ʱ", Toast.LENGTH_LONG).show();           
                	 break; 
             } 
			}

	     }); 	
	}
	
		//������
		public void checkupdate_onclick(View view)
		{
			progressDialog=ProgressDialog.show(About_Us.this,"��������","",true,true);
			UmengUpdateAgent.forceUpdate(this);
		}
		
		//������
		public void cleancache_onclik(View view)
		{
			Toast.makeText(About_Us.this, "�����������", Toast.LENGTH_LONG).show();;  
			DataCleanManager.cleanInternalCache(this);
			DataCleanManager.cleanSharedPreference(this);
			DataCleanManager.cleanFiles(this);
			DataCleanManager.cleanExternalCache(this);
			   
		}
		//������
		public void About_us_onclick(View view)
		{
			Intent intent=new Intent();   
			intent.setClass(this, About_Developers.class);
			startActivity(intent);
		}		
		
		//�Ƽ�������
		public void share_onclick(View view) 
		{
			// TODO Auto-generated method stub
			mController.getConfig().removePlatform( SHARE_MEDIA.RENREN, SHARE_MEDIA.DOUBAN);
			// �Ƿ�ֻ���ѵ�¼�û����ܴ򿪷���ѡ��ҳ
			mController.openShare(this, false);
		}
		//�����罻����
		private void initShare() {
			// TODO Auto-generated method stub
			// ���÷�������
			mController.setShareContent("������ữ�����SDK�����ƶ�Ӧ�ÿ��������罻�����ܣ�http://www.umeng.com/social");
			// ���÷���ͼƬ, ����2ΪͼƬ��url��ַ
			//�˴�ͼƬURLҪ�ĳ�ctguer��ͼ��
			//mController.setShareMedia(new UMImage(this, 
			      //                                "http://www.umeng.com/images/pic/banner_module_social.png"));
			// ���΢��ƽ̨
			UMWXHandler wxHandler = new UMWXHandler(this,appID,appSecret);
			wxHandler.addToSocialSDK();
			// ���΢������Ȧ
			UMWXHandler wxCircleHandler = new UMWXHandler(this,appID,appSecret);
			wxCircleHandler.setToCircle(true);
			wxCircleHandler.addToSocialSDK();
			
			//QQ
			//����1Ϊ��ǰActivity������2Ϊ��������QQ���������APP ID������3Ϊ��������QQ���������APP kEY.
			UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this, "1101376088",
			                "85Knx9wY8Xk2zMLr");
			qqSsoHandler.addToSocialSDK();  
			
			//QQ�ռ�
			//����1Ϊ��ǰActivity������2Ϊ��������QQ���������APP ID������3Ϊ��������QQ���������APP kEY.
			QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(this, "1101376088",
			                "85Knx9wY8Xk2zMLr");
			qZoneSsoHandler.addToSocialSDK();
			
			//����΢��
			//��������SSO handler
			mController.getConfig().setSsoHandler(new SinaSsoHandler());
		
			//��Ѷ΢��
			//������Ѷ΢��SSO handler
			mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
						
		}

		//����SSO(���½)��Ȩ�ص�
		@Override
		protected void onActivityResult(int requestCode, int resultCode,
				Intent data) {
			// TODO Auto-generated method stub
			super.onActivityResult(requestCode, resultCode, data);
			 /**ʹ��SSO��Ȩ����������´��� */
		    UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode) ;
		    if(ssoHandler != null){
		       ssoHandler.authorizeCallBack(requestCode, resultCode, data);
		    }
		 
			
		}
		
	
		
		
}
