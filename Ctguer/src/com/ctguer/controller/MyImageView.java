package com.ctguer.controller;

import com.ctguer.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ImageView;

/**
 * @author wulianghuan
 * 该类为自定义ImageView，用于显示背景图片，并显示背景图片到移动效果
 *
 */
public class MyImageView extends ImageView{
	private Bitmap back;		//背景图片资源
	private Bitmap mBitmap;		//生成位图	
	private double startX = 0;	//移动起始X坐标
	
	//构造函数中必须有context,attributeSet这两个	参数，否则父类无法调用
	public MyImageView(Context context,AttributeSet attributeSet)
	{
		super(context, attributeSet);
	    //由于不是Activity子类，只能通过DisplayMetrics来获取屏幕信息
	    DisplayMetrics dm = getResources().getDisplayMetrics();
	    //屏幕宽度
	    int screenWidth = dm.widthPixels;  
	    //屏幕高度
	    int screenHeight = dm.heightPixels;      
		
		back = BitmapFactory.decodeResource(context.getResources(), R.drawable.rootblock_default_bg);
		
		mBitmap = Bitmap.createScaledBitmap(back, screenWidth*3, screenHeight, true);    
		
		
	}
	
	@Override
	public void onDraw(Canvas canvas)
	{
		//Log.i("TAG", "-----onDraw");
		Bitmap bitmap2 = Bitmap.createBitmap(mBitmap);
		canvas.drawBitmap(mBitmap, (float)startX , 0 , null);
	}
}
