package com.ctguer;
/*此类为显示新闻详细信息的界面*/
import com.ctguer.model.NewsContent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class NewsActivity extends Activity {
	private TextView title, timeandauthor, content;//分别是标题,时间作者,具体的内容
	private NewsContent getdetailnew;//从Intent里获取的数据

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
		title.setText(getdetailnew.getContent());//标题
		timeandauthor.setText(getdetailnew.getTime() + "  "+getdetailnew.getAuthor());
		StringBuffer detailcontent = new StringBuffer("");
		for(String temp:getdetailnew.getDetail()){
			detailcontent.append("\u3000\u3000" + temp + "\n\n");//格式转化
		}
		content.setText(detailcontent.toString());
	}
}
