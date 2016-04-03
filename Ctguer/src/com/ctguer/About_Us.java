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
	
	// wx42df77539166bdbd是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
	private String appID = "wx372ac6b22e5d1be8";
	private String appSecret = "b0360e3f2485cd22c5543e7b07d21ee0";
	
	
	private String ShareStr="Ctguer,因你而共鸣"; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_about__us);
		//友盟初始化分享
		initShare();  
		UmengUpdateAgent.setUpdateOnlyWifi(false);
		UmengUpdateAgent.setUpdateAutoPopup(true);    //是否自动弹出更新对话框 
		UmengUpdateAgent.setDownloadListener(null);    //下载新版本过程事件监听，可设为 null 
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
                 case 0: // 有更新 
                 UmengUpdateAgent.showUpdateDialog(getBaseContext(), updateInfo); 
                 	break; 
                 case 1: // 无更新 
                     Toast.makeText(getBaseContext(), "暂无跟新", Toast.LENGTH_LONG).show();           
                	 break; 
                 case 2: // 如果设置为wifi下更新且wifi无法打开时调用 
                	 Toast.makeText(getBaseContext(), "请在wifi下跟新", Toast.LENGTH_LONG).show();     
                	 break; 
                 case 3: // 连接超时 
                	 Toast.makeText(getBaseContext(), "连接超时", Toast.LENGTH_LONG).show();           
                	 break; 
             } 
			}

	     }); 	
	}
	
		//检查跟新
		public void checkupdate_onclick(View view)
		{
			progressDialog=ProgressDialog.show(About_Us.this,"检查更新中","",true,true);
			UmengUpdateAgent.forceUpdate(this);
		}
		
		//检查跟新
		public void cleancache_onclik(View view)
		{
			Toast.makeText(About_Us.this, "缓存清理完成", Toast.LENGTH_LONG).show();;  
			DataCleanManager.cleanInternalCache(this);
			DataCleanManager.cleanSharedPreference(this);
			DataCleanManager.cleanFiles(this);
			DataCleanManager.cleanExternalCache(this);
			   
		}
		//检查跟新
		public void About_us_onclick(View view)
		{
			Intent intent=new Intent();   
			intent.setClass(this, About_Developers.class);
			startActivity(intent);
		}		
		
		//推荐给好友
		public void share_onclick(View view) 
		{
			// TODO Auto-generated method stub
			mController.getConfig().removePlatform( SHARE_MEDIA.RENREN, SHARE_MEDIA.DOUBAN);
			// 是否只有已登录用户才能打开分享选择页
			mController.openShare(this, false);
		}
		//友盟社交分享
		private void initShare() {
			// TODO Auto-generated method stub
			// 设置分享内容
			mController.setShareContent("友盟社会化组件（SDK）让移动应用快速整合社交分享功能，http://www.umeng.com/social");
			// 设置分享图片, 参数2为图片的url地址
			//此处图片URL要改成ctguer的图标
			//mController.setShareMedia(new UMImage(this, 
			      //                                "http://www.umeng.com/images/pic/banner_module_social.png"));
			// 添加微信平台
			UMWXHandler wxHandler = new UMWXHandler(this,appID,appSecret);
			wxHandler.addToSocialSDK();
			// 添加微信朋友圈
			UMWXHandler wxCircleHandler = new UMWXHandler(this,appID,appSecret);
			wxCircleHandler.setToCircle(true);
			wxCircleHandler.addToSocialSDK();
			
			//QQ
			//参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
			UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this, "1101376088",
			                "85Knx9wY8Xk2zMLr");
			qqSsoHandler.addToSocialSDK();  
			
			//QQ空间
			//参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
			QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(this, "1101376088",
			                "85Knx9wY8Xk2zMLr");
			qZoneSsoHandler.addToSocialSDK();
			
			//新浪微博
			//设置新浪SSO handler
			mController.getConfig().setSsoHandler(new SinaSsoHandler());
		
			//腾讯微博
			//设置腾讯微博SSO handler
			mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
						
		}

		//配置SSO(免登陆)授权回调
		@Override
		protected void onActivityResult(int requestCode, int resultCode,
				Intent data) {
			// TODO Auto-generated method stub
			super.onActivityResult(requestCode, resultCode, data);
			 /**使用SSO授权必须添加如下代码 */
		    UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode) ;
		    if(ssoHandler != null){
		       ssoHandler.authorizeCallBack(requestCode, resultCode, data);
		    }
		 
			
		}
		
	
		
		
}
