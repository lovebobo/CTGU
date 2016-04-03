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
	
	//�α��ѯ
	public void course_search_onclick(View view)
	{
		Intent intent=new Intent();
		//����type���� 1�ɼ���2�α�3����
		intent.setClass(this, LoginCtgu.class);
		intent.putExtra("type", 2);
		
		startActivity(intent);		
	}
	
	//�ɼ���ѯ
	public void grade_search_onclick(View view)
	{
		Intent intent=new Intent();
		//����type���� 1�ɼ���2�α�3����
		intent.setClass(this, LoginCtgu.class);
		intent.putExtra("type", 1);
		
		startActivity(intent);		
	}
	
	//����	
	public void evaluate_onclick(View view) {

		Intent intent=new Intent();
		//����type���� 1�ɼ���2�α�3����
		intent.setClass(this, LoginCtgu.class);
		intent.putExtra("type", 3);
		
		startActivity(intent);	
		
	}
	
	//�ҵĳɼ�
	public void myrade_onclick(View view)
	{
		Intent intent=new Intent();
		intent.setClass(this, My_Grade.class);		
		startActivity(intent);		
	}
	
	//�ҵĿα�
	public void mycourse_onclick(View view)
	{
		Intent intent=new Intent();
		intent.setClass(this, My_CourseTable.class);		
		startActivity(intent);		
	}
	
}
