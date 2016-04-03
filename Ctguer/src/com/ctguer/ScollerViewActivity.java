package com.ctguer;

import com.ctguer.controller.MyScrollView;
import com.ctguer.controller.OnScrollChangedListener;
import com.ctguer.model.Management;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ScollerViewActivity extends Activity implements OnScrollChangedListener {
    private LinearLayout mLLAnim;
    private LinearLayout losrfoundAnim;
    
    private MyScrollView mSVmain;
    private int mScrollViewHeight;
    private int mStartAnimateTop;
    private ImageView titleimageView;
    private ImageView imageView;
    private ImageView tushuimageView;
    private ImageView toupiaoimageView;

    
    
    private ImageView xuanzhuan_image;
    

    private boolean hasStart = false;
    private boolean hasStart1 = false;
    private boolean hasStart2 = false;
    private boolean hasStart3 = false;


    private Animation xuanzuananim;

    private Animation openanim;
    private Animation closeanim;
    private Animation openanim1;
    private Animation closeanim1;
    private Animation openanim2;
    private Animation closeanim2;
    private Animation openanim3;
    private Animation closeanim3;

    private Animation transteranim;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_scoller_view);
	initView();
	setView();
    }

    private void initView() {
	mSVmain = (MyScrollView) findViewById(R.id.sv_main);
	mLLAnim = (LinearLayout) findViewById(R.id.ll_anim);
	losrfoundAnim=(LinearLayout) findViewById(R.id.lostfound_anim);

	titleimageView=(ImageView)findViewById(R.id.title_imageview);
	xuanzhuan_image=(ImageView)findViewById(R.id.xuanzhuan_image);

	imageView=(ImageView)findViewById(R.id.login_ImageView);
	tushuimageView=(ImageView)findViewById(R.id.tushu_image);
	toupiaoimageView=(ImageView)findViewById(R.id.toupiao_anim);

	xuanzuananim=AnimationUtils.loadAnimation(this, R.anim.xuanzhuan);
	
	xuanzhuan_image.setAnimation(xuanzuananim);
	
	
	transteranim=AnimationUtils.loadAnimation(this, R.anim.transter);
	
	
	titleimageView.setAnimation(transteranim);
	
	titleimageView.startAnimation(transteranim);
	

	openanim = AnimationUtils.loadAnimation(this, R.anim.show);
	closeanim = AnimationUtils.loadAnimation(this, R.anim.close);
	
	openanim1 = AnimationUtils.loadAnimation(this, R.anim.show1);
	closeanim1 = AnimationUtils.loadAnimation(this, R.anim.close1);
	
	openanim2 = AnimationUtils.loadAnimation(this, R.anim.show2);
	closeanim2 = AnimationUtils.loadAnimation(this, R.anim.close2);
	
	openanim3 = AnimationUtils.loadAnimation(this, R.anim.show3);
	closeanim3 = AnimationUtils.loadAnimation(this, R.anim.close3);  

	
	imageView.setAnimation(transteranim);

	
	imageView.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			SharedPreferences sharedPreferences = getSharedPreferences("myLoginStatus", getApplicationContext().MODE_PRIVATE); //私有数据
			Editor editor = sharedPreferences.edit();//获取编辑器
			Management.curUser.setStatusBoolean(false);
			editor.putBoolean("status", Management.curUser.statusBoolean);
			editor.commit();//提交修改

			Intent intent = new Intent();
			intent.setClass(ScollerViewActivity.this, MainActivity.class);
			//清除本界面，返回键将不再回来
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  
			startActivity(intent);
		}
	});
    }

    private void setView() {
	mSVmain.setOnScrollChangedListener(this);
	mLLAnim.setVisibility(View.INVISIBLE);
	tushuimageView.setVisibility(View.INVISIBLE);
	losrfoundAnim.setVisibility(View.INVISIBLE);
	toupiaoimageView.setVisibility(View.INVISIBLE);

	imageView.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
	super.onWindowFocusChanged(hasFocus);
	mScrollViewHeight = mSVmain.getHeight();
	mStartAnimateTop = mScrollViewHeight / 5 * 4;// 在距离ScrollView顶部mStartAnimateTop距离的地方开始加载动画
    System.out.printf("mScrollViewHeight",mScrollViewHeight);
    }

    @Override
    public void onScrollChanged(int top, int oldTop) {
	int animTop = mLLAnim.getTop() - top;// 获得mLLAnim控件到ScrollView可视界面顶部的距离
	
	if (top > oldTop) {// 向上滑动
		
	    if (animTop+mLLAnim.getHeight()/2 < mScrollViewHeight && !hasStart) {// 当mLLAnim到可视界面的顶部高度小于我们设置的高度，开始加载动画
			mLLAnim.setVisibility(View.VISIBLE);
			mLLAnim.startAnimation(openanim);		
			hasStart = true;
	    }
	    
	    if(tushuimageView.getTop()-top+tushuimageView.getHeight()/2 < mScrollViewHeight&& !hasStart1)
	    {
	    	tushuimageView.setVisibility(View.VISIBLE);
			tushuimageView.startAnimation(openanim1);
			hasStart1 = true;
	    }
	    
	    if(losrfoundAnim.getTop()-top+losrfoundAnim.getHeight()/2 < mScrollViewHeight&& !hasStart2)
	    {
	    	losrfoundAnim.setVisibility(View.VISIBLE);
	    	losrfoundAnim.startAnimation(openanim2);
			hasStart2 = true;
	    }
	    
	    if(toupiaoimageView.getTop()-top+toupiaoimageView.getHeight()/2 < mScrollViewHeight&& !hasStart3)
	    {
	    	toupiaoimageView.setVisibility(View.VISIBLE);
	    	toupiaoimageView.startAnimation(openanim3);
	    	hasStart3 = true;

	    }
	    
	    if(imageView.getTop()-top+imageView.getHeight()/2 > mScrollViewHeight)
	    {
	    	imageView.setVisibility(View.VISIBLE);
	    	imageView.startAnimation(transteranim);
	    }
	} else {// 向下滑动
		
	    if (animTop+mLLAnim.getHeight() > mScrollViewHeight && hasStart) {		
			mLLAnim.setVisibility(View.INVISIBLE);
			mLLAnim.startAnimation(closeanim);
			hasStart = false;
	    }
	    
	    if(tushuimageView.getTop() - top+tushuimageView.getHeight() > mScrollViewHeight&& hasStart1)
	    {
	    	tushuimageView.setVisibility(View.INVISIBLE);
			tushuimageView.startAnimation(closeanim1);
			hasStart1 = false;
	    }
	    
	    if(losrfoundAnim.getTop() - top+losrfoundAnim.getHeight() > mScrollViewHeight&& hasStart2)
	    {
	    	losrfoundAnim.setVisibility(View.INVISIBLE);
	    	losrfoundAnim.startAnimation(closeanim2);
			hasStart2 = false;
	    }
	    
	    if(toupiaoimageView.getTop()-top+toupiaoimageView.getHeight() > mScrollViewHeight&& hasStart3)
	    {
	    	toupiaoimageView.setVisibility(View.INVISIBLE);
	    	toupiaoimageView.startAnimation(closeanim3);
	    	hasStart3 = false;
	    }
	    
	    
	}
    }

}
