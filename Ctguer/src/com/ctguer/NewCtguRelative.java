package com.ctguer;

import com.ctguer.controller.FileIO;
import com.ctguer.controller.URLs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

public class NewCtguRelative extends BaseActivity {

	private LinearLayout linearLayout1;
	private LinearLayout linearLayout2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_new_ctgu_relative);
		linearLayout1=(LinearLayout)findViewById(R.id.LinearLayout1);
		linearLayout1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FileIO.deleteFile(URLs.getFileScorePath());
				Toast.makeText(getBaseContext(),"删除完成",Toast.LENGTH_LONG).show();;
			}
		});
		linearLayout2=(LinearLayout)findViewById(R.id.LinearLayout2);
		linearLayout2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FileIO.deleteFile(URLs.courseTables);
				FileIO.deleteFile(URLs.othercourseTables);
				Toast.makeText(getBaseContext(),"删除完成",Toast.LENGTH_LONG).show();;
			}
		});
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
