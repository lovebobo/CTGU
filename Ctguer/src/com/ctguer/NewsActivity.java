package com.ctguer;
/*����Ϊ��ʾ������ϸ��Ϣ�Ľ���*/
import com.ctguer.model.NewsContent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class NewsActivity extends Activity {
	private TextView title, timeandauthor, content;//�ֱ��Ǳ���,ʱ������,���������
	private NewsContent getdetailnew;//��Intent���ȡ������

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_news_detail);
		findId();
	}
	void findId(){
		title = (TextView)findViewById(R.id.activity_new_title);
		timeandauthor = (TextView)findViewById(R.id.activity_new_timeandauthor);
		content = (TextView)findViewById(R.id.activity_news_content);
		Intent getdetail = getIntent();
		getdetailnew =(NewsContent) getdetail.getSerializableExtra("detail");
		title.setText(getdetailnew.getContent());//����
		timeandauthor.setText(getdetailnew.getTime() + "  "+getdetailnew.getAuthor());
		StringBuffer detailcontent = new StringBuffer("");
		for(String temp:getdetailnew.getDetail()){
			detailcontent.append("\u3000\u3000" + temp + "\n\n");//��ʽת��
		}
		content.setText(detailcontent.toString());
	}
}
