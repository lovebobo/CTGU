package com.ctguer;

import java.util.ArrayList;
import java.util.List;

import com.ctguer.controller.FileIO;
import com.ctguer.controller.URLs;
import com.ctguer.model.Score;

import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;

public class My_Grade extends BaseActivity {

	private ListView myGradeListView;//成绩列表
	
	private List<Score> gradeList=new ArrayList<Score>();
	
	listviewAdapter<Score> listviewAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_my__grade);
		findByID();		
		init();//界面数据绑定并初始化
	}
	@Override
	protected void onStart() {
		// TODO 在这里读取成绩信息
		
		
		super.onStart();
	}
	
	private void findByID() {
		// TODO Auto-generated method stub
		myGradeListView=(ListView)findViewById(R.id.my_grade_listview);
	}
	
	private void init() {
		// TODO Auto-generated method stub
		
		//读取成绩列表
		getGradeList();
		//绑定适配器
		listviewAdapter=new listviewAdapter<Score>(this, R.layout.grade_item, gradeList);
		myGradeListView.setAdapter(listviewAdapter);
	}
	private void getGradeList() {
		// TODO Auto-generated method stub
		Object  object=FileIO.getObjectFromFile(this, URLs.getFileScorePath());
		if(object!=null)
		{
			gradeList=(List<Score>)object;
			int le=gradeList.size();
		}
	}
}
