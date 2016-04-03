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

	private ListView myGradeListView;//�ɼ��б�
	
	private List<Score> gradeList=new ArrayList<Score>();
	
	listviewAdapter<Score> listviewAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_my__grade);
		findByID();		
		init();//�������ݰ󶨲���ʼ��
	}
	@Override
	protected void onStart() {
		// TODO �������ȡ�ɼ���Ϣ
		
		
		super.onStart();
	}
	
	private void findByID() {
		// TODO Auto-generated method stub
		myGradeListView=(ListView)findViewById(R.id.my_grade_listview);
	}
	
	private void init() {
		// TODO Auto-generated method stub
		
		//��ȡ�ɼ��б�
		getGradeList();
		//��������
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
