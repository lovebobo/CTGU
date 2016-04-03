package com.ctguer;

import com.ctguer.controller.FileIO;
import com.ctguer.controller.RelativeUser;
import com.ctguer.controller.URLs;
import com.ctguer.model.Management;
import java.io.Serializable;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;
import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.FeedbackAgent;
import com.umeng.fb.push.FBMessage;
import com.umeng.fb.push.FeedbackPush;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.entity.UMessage;


import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends TabActivity implements View.OnClickListener{



	//SlidingMenu mMenu;
	public static ResideMenu resideMenu;
	public static LinearLayout FragmentView;
    private MainActivity mContext;
    
    private ResideMenuItem itemHome;
    private ResideMenuItem itemProfile;
    private ResideMenuItem itemCalendar;
    private ResideMenuItem itemSettings;
    private ResideMenuItem itemCalendar1;
    private ResideMenuItem itemSettings1;

    private FeedbackAgent agent;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		
		/*SharedPreferences sharedPreferences1 = getSharedPreferences("ifLoginStatus", Context.MODE_PRIVATE); //私有数据		
		if(sharedPreferences1.getBoolean("status", true))
		{
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, LoginAccount.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  
			startActivity(intent);
			return;
		}*/
		Object object=FileIO.getObjectFromFile(MainActivity.this, URLs.uresfile);
		if(null==object)
		{
			/*Object object1 = RelativeUser.getNameFromJson(object.toString(), "iflogin");*/
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, LoginAccount.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  
			startActivity(intent);
			return;
		}
		
		SharedPreferences sharedPreferences = getSharedPreferences("myLoginStatus", Context.MODE_PRIVATE); //私有数据		
		if(sharedPreferences.getBoolean("status", true))
		{
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, ScollerViewActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  
			startActivity(intent);
			return;
		}
		setContentView(R.layout.activity_main);
		mContext = this;
	    setUpMenu();
	   /* if( savedInstanceState == null )*/
	       //changeFragment(new HomeFragment());
		setTabs() ;
		
		init();
		agent = new FeedbackAgent(this);
		//友盟相关
		aboutUmeng();
		   
	}
	
	
	private void aboutUmeng() {
		// TODO Auto-generated method stub
		//如果想程序启动时自动检查是否需要更新， 把下面两行代码加在Activity 的onCreate()函数里。
		//默认在Wi-Fi接入情况下才进行自动提醒。
		UmengUpdateAgent.setUpdateOnlyWifi(false);
		UmengUpdateAgent.update(this);
		UmengUpdateAgent.setUpdateAutoPopup(true);    //是否自动弹出更新对话框 
		UmengUpdateAgent.setDownloadListener(null);    //下载新版本过程事件监听，可设为 null 
		UmengUpdateAgent.setDialogListener(null);    //用户点击更新对话框按钮的回调事件，直接 null 
		                  //从服务器获取更新信息的回调函数 
		UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() { 				       
				@Override
				public void onUpdateReturned(int updateStatus,
						UpdateResponse updateInfo) {
					// TODO Auto-generated method stub
					// TODO Auto-generated method stub
					 switch (updateStatus) { 
	                 case 0: // 有更新 
	                 UmengUpdateAgent.showUpdateDialog(mContext, updateInfo); 
	                 	break; 
	                 case 1: // 无更新 
	                     Toast.makeText(getApplicationContext(), "暂无跟新", Toast.LENGTH_LONG);           
	                	 break; 
	                 case 2: // 如果设置为wifi下更新且wifi无法打开时调用 
	                	 Toast.makeText(getApplicationContext(), "请在wifi下跟新", Toast.LENGTH_LONG);     
	                	 break; 
	                 case 3: // 连接超时 
	                	 Toast.makeText(getApplicationContext(), "连接超时", Toast.LENGTH_LONG);           
	                	 break; 
	             } 
				}

		     }); 
	 
		
		//发送策略定义了用户由统计分析SDK产生的数据发送回友盟服务器的频率。
		MobclickAgent.updateOnlineConfig(this);
		
		/** 设置是否对日志信息进行加密, 默认false(不加密). */
		/*AnalyticsConfig.enableEncrypt(true);*/
		
		//需要提醒用户
		agent.sync();
		
		//开启反馈回复推送服务
		agent.openFeedbackPush();
		//同时开启友盟消息推送服务。
		PushAgent.getInstance(this).enable();
		//使用友盟消息推送自定义消息
		FeedbackPush.getInstance(this).init(true);
		/* */
	}

	UmengMessageHandler mMessageHandler = new UmengMessageHandler() {
	    @Override
	    public void dealWithCustomMessage(Context context, UMessage msg) {
	        if (FeedbackPush.getInstance(context).dealFBMessage(new FBMessage(msg.custom))) {
	            return;
	        }
	        //此推送消息非开发者回复消息，开发者可以继续处理该消息
	        /*************** 其他操作 ***************/
	    }
	};


	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//
/*		android.os.Process.killProcess(android.os.Process.myPid());
		System.exit(0); */  
	}




	private void setUpMenu() {
		// TODO Auto-generated method stub
		 // attach to current activity;
        resideMenu = new ResideMenu(this);
     //   resideMenu.setBackground(R.drawable.menu_background);
        resideMenu.attachToActivity(this);
        resideMenu.setMenuListener(menuListener);
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip. 
        resideMenu.setScaleValue(0.6f);

        // create menu items;
        itemHome     = new ResideMenuItem(this, R.drawable.news, "我的账号");
        itemProfile  = new ResideMenuItem(this, R.drawable.news, "关于软件");
        itemCalendar = new ResideMenuItem(this, R.drawable.news, "帮助与反馈");
        itemSettings = new ResideMenuItem(this, R.drawable.news, "系统设置");
        itemSettings1 = new ResideMenuItem(this, R.drawable.news, "退出登陆");

        itemHome.setOnClickListener(this);
        itemProfile.setOnClickListener(this);
        itemCalendar.setOnClickListener(this);
        itemSettings.setOnClickListener(this);
        itemSettings1.setOnClickListener(this);

        resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemProfile, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemCalendar, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemSettings, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemSettings1, ResideMenu.DIRECTION_LEFT);

        resideMenu.addIgnoredView(LayoutInflater.from(mContext).inflate(
				R.layout.activity_ctgu_news, null));
        resideMenu.addIgnoredView(LayoutInflater.from(mContext).inflate(
				R.layout.fragment_news, null));
        // You can disable a direction by setting ->
        // resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

        /*findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });
        findViewById(R.id.title_bar_right_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
            }
        });*/
        if(FragmentView==null)
        {
        	FragmentView=(LinearLayout)findViewById(R.id.FrameContent);
        }
   
       	
        MainActivity.resideMenu.clearIgnoredViewList();
        
	}
		
	 @Override
	    public boolean dispatchTouchEvent(MotionEvent ev) {
	        return resideMenu.dispatchTouchEvent(ev);
	    }

	    @Override
	    public void onClick(View view) {
	    	
	    	Intent intent=new Intent();
	    	
	        if (view == itemCalendar){
	        	agent.startFeedbackActivity();
	        }else if (view == itemProfile){
	            intent.setClass(this, About_Us.class);
	            startActivity(intent);
	        }else if (view == itemSettings1){
	        	FileIO.deleteFile(URLs.uresfile);
	        	/*SharedPreferences sharedPreferences1 = getSharedPreferences("ifLoginStatus", getApplicationContext().MODE_PRIVATE); //私有数据
				Editor editor = sharedPreferences1.edit();//获取编辑器
				Management.curUser.setLoginstatusBoolean(true);
				editor.putBoolean("status", Management.curUser.loginstatusBoolean);
				editor.commit();//提交修改*/
				startActivity(new Intent(MainActivity.this,LoginAccount.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
	        }/*else if (view == itemSettings){
	            changeFragment(new SettingsFragment());
	        }*/

	        resideMenu.closeMenu();
	    }

	    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
	        @Override
	        public void openMenu() {
	            
	        }

	        @Override
	        public void closeMenu() {
	            
	        }
	    };

	    private void changeFragment(Fragment targetFragment){
	        //resideMenu.clearIgnoredViewList();
	       /* getFragmentManager()
	                .beginTransaction()
	                .replace(R.id.main_fragment, targetFragment, "fragment")
	                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
	                .commit();*/
	    }

	    // What good method is to access resideMenu锛�
	    public ResideMenu getResideMenu(){
	        return resideMenu;
	    }

	private void init() {

	}

	private void setTabs()
	{
		addTab("", R.drawable.zhuye, Individualcenter.class);
		addTab("", R.drawable.news, CtguNews.class);
		addTab("", R.drawable.msg, Association.class);
	}
	
	private void addTab(String labelId, int drawableId, Class<?> c)
	{
		TabHost tabHost = getTabHost();
		Intent intent = new Intent(this, c);
		TabHost.TabSpec spec = tabHost.newTabSpec("tab" + labelId);	
		
		View tabIndicator = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
		TextView title = (TextView) tabIndicator.findViewById(R.id.title);
		title.setText(labelId);
		ImageView icon = (ImageView) tabIndicator.findViewById(R.id.icon);
		icon.setImageResource(drawableId);
		
		spec.setIndicator(tabIndicator);
		spec.setContent(intent);
		tabHost.addTab(spec);
	}
}
