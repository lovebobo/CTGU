package com.ctguer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ctguer.controller.Codes;
import com.ctguer.controller.RelativeUser;
import com.ctguer.model.Comments;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_Detail extends Activity {

	private listviewAdapter<Comments> listviewadapter;
	
	private ArrayList<Comments> commentList=new ArrayList<>();
	
	private ListView listView;
	
	private com.ctguer.model.Activity activity_temp;
	
	private TextView textView_nick;//昵称
	
	private TextView textView_launchtime;//发起时间
	
	private TextView textView_place;//地点
	
	private TextView textView_activity_time;//活动时间
	
	private TextView textView_content;//活动内容
	
	private TextView textView_num;//人数
	
	private TextView textView_signnum;//已报名人数
	
	private TextView textView_commentnum;//已评论人数
	
	private TextView textView_praisenum;//已点赞人数
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity__detail);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		initView();
		
		initData();
	}

	private void initView() {
		// TODO Auto-generated method stub
		listView=(ListView)findViewById(R.id.listview_de);
		listviewadapter=new listviewAdapter<>(Activity_Detail.this, R.layout.comment_item, commentList);
		listView.setAdapter(listviewadapter);
		
		textView_nick=(TextView)findViewById(R.id.id_textview);		
		textView_launchtime=(TextView)findViewById(R.id.time_textview);
		textView_activity_time=(TextView)findViewById(R.id.time);
		textView_content=(TextView)findViewById(R.id.content);
		textView_place=(TextView)findViewById(R.id.place);
		textView_num=(TextView)findViewById(R.id.Limit_people_num);
		textView_signnum=(TextView)findViewById(R.id.sign_textview);
		textView_commentnum=(TextView)findViewById(R.id.text_view_2);
		textView_praisenum=(TextView)findViewById(R.id.text_view_1);
	}

	private void initData() {
		// TODO Auto-generated method stub
		activity_temp=(com.ctguer.model.Activity) getIntent().getSerializableExtra("Comment_obj");
		textView_nick.setText(activity_temp.getTitle());
		textView_launchtime.setText(activity_temp.getLaunchtime());
		textView_activity_time.setText(activity_temp.getDatatime());
		textView_content.setText(activity_temp.getContent());
		textView_place.setText(activity_temp.getPlace());
		textView_num.setText(activity_temp.getLimit_count()+"");
		textView_signnum.setText("已报名"+activity_temp.getSign_count()+"人");
		textView_commentnum.setText(activity_temp.getComment_count()+"");
		textView_praisenum.setText(activity_temp.getPraise_count()+"");
		RelativeUser.getCommentList(handler, activity_temp.getActivity_id());
	}
	
	Handler handler=new Handler()
	{

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case Codes.getCommentListSuc:
				if(msg.obj!=null)
				{
					commentList.clear();
					for (Comments tmp : (List<Comments>)msg.obj) {
						commentList.add(tmp);
					}
					Collections.reverse(commentList);
				}
				listviewadapter.notifyDataSetChanged();		
				Toast.makeText(Activity_Detail.this, "获取成功", Toast.LENGTH_SHORT).show();
				break;

			case Codes.getCommentListFail:
				Toast.makeText(Activity_Detail.this, "获取失败", Toast.LENGTH_SHORT).show();
				break;
			case Codes.getCommentListFail1:
				Toast.makeText(Activity_Detail.this, "暂无评论", Toast.LENGTH_SHORT).show();
				break;

			}
		}
		
	};
}
