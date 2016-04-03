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
				Toast.makeText(getBaseContext(),"ɾ�����",Toast.LENGTH_LONG).show();;
			}
		});
		linearLayout2=(LinearLayout)findViewById(R.id.LinearLayout2);
		linearLayout2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FileIO.deleteFile(URLs.courseTables);
				FileIO.deleteFile(URLs.othercourseTables);
				Toast.makeText(getBaseContext(),"ɾ�����",Toast.LENGTH_LONG).show();;
			}
		});
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
