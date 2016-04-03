package com.ctguer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class CtguRelative extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_ctgu_relative);
	}
	
	//课表查询
	public void course_search_onclick(View view)
	{
		Intent intent=new Intent();
		//传递type类型 1成绩，2课表，3评教
		intent.setClass(this, LoginCtgu.class);
		intent.putExtra("type", 2);
		
		startActivity(intent);		
	}
	
	//成绩查询
	public void grade_search_onclick(View view)
	{
		Intent intent=new Intent();
		//传递type类型 1成绩，2课表，3评教
		intent.setClass(this, LoginCtgu.class);
		intent.putExtra("type", 1);
		
		startActivity(intent);		
	}
	
	//评教	
	public void evaluate_onclick(View view) {

		Intent intent=new Intent();
		//传递type类型 1成绩，2课表，3评教
		intent.setClass(this, LoginCtgu.class);
		intent.putExtra("type", 3);
		
		startActivity(intent);	
		
	}
	
	//我的成绩
	public void myrade_onclick(View view)
	{
		Intent intent=new Intent();
		intent.setClass(this, My_Grade.class);		
		startActivity(intent);		
	}
	
	//我的课表
	public void mycourse_onclick(View view)
	{
		Intent intent=new Intent();
		intent.setClass(this, My_CourseTable.class);		
		startActivity(intent);		
	}
	
}
