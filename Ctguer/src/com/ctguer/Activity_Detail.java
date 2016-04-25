package com.ctguer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.ctguer.controller.Codes;
import com.ctguer.controller.FileIO;
import com.ctguer.controller.RelativeUser;
import com.ctguer.controller.URLs;
import com.ctguer.model.Comments;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_Detail extends Activity {

	private listviewAdapter<Comments> listviewadapter;
	
	private ArrayList<Comments> commentList=new ArrayList<>();
	
	private MyListView listView;
	
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
	
	private Button commentbutton;//评论按钮
	
	private InputMethodManager inputMethodManager;
	private EditText comment_edittext;//评论文本
	
	private ImageView imageView;
	
	private Button signbutton;
	
	private TextView signTextView;
	
	
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
		listView=(MyListView)findViewById(R.id.listview_de);
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
		signTextView=(TextView)findViewById(R.id.sign_textview);
		
		imageView=(ImageView)findViewById(R.id.imageView3);
		imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String string=FileIO.getObjectFromFile(Activity_Detail.this, URLs.uresfile).toString();
				Object object = RelativeUser.getNameFromJson(string, "Id");
				int Id=Integer.parseInt(object.toString());
				RelativeUser.launchPraise(handler, Id,  activity_temp.getActivity_id());
			}
		});
		
		commentbutton=(Button)findViewById(R.id.commentButton);
		commentbutton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(comment_edittext.getText().toString().trim().length()<1)
				{
					Toast.makeText(Activity_Detail.this, "评论内容不能为空", Toast.LENGTH_SHORT).show();
				}
				else {
					SimpleDateFormat    formatter    =   new    SimpleDateFormat    ("yyyy年MM月dd日    HH:mm:ss     ");       
					Date    curDate    =   new    Date(System.currentTimeMillis());//获取当前时间       
					String    str    =    formatter.format(curDate);       
					
					String string=FileIO.getObjectFromFile(Activity_Detail.this, URLs.uresfile).toString();
					Object object = RelativeUser.getNameFromJson(string, "Id");
					int Id=Integer.parseInt(object.toString());
					RelativeUser.launchComments(handler, Id, activity_temp.getActivity_id(), str, comment_edittext.getText().toString());
				}
			}
		});
		comment_edittext=(EditText)findViewById(R.id.commentEdit);
		
		signbutton=(Button)findViewById(R.id.sign_button);
		signbutton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String string=FileIO.getObjectFromFile(Activity_Detail.this, URLs.uresfile).toString();
				Object object = RelativeUser.getNameFromJson(string, "Id");
				int Id=Integer.parseInt(object.toString());
				RelativeUser.signActivity(handler,  Id,  activity_temp.getActivity_id());
			}
		});
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
			case Codes.launchCommentSuc:
				Toast.makeText(Activity_Detail.this, "评论成功", Toast.LENGTH_SHORT).show();
				RelativeUser.getCommentList(handler, activity_temp.getActivity_id());
				comment_edittext.setText("");
				inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);   
				inputMethodManager.hideSoftInputFromWindow(comment_edittext.getWindowToken(),0);   
				break;	
			case Codes.launchCommentFail:
				Toast.makeText(Activity_Detail.this, "评论失败", Toast.LENGTH_SHORT).show();
				comment_edittext.setText("");
				inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);   
				inputMethodManager.hideSoftInputFromWindow(comment_edittext.getWindowToken(),0);   
				break;	
				
			case Codes.launchPraiseSuc:
				Toast.makeText(Activity_Detail.this, "点赞成功", Toast.LENGTH_SHORT).show();
				textView_praisenum.setText((Integer.parseInt(textView_praisenum.getText().toString())+1)+"");
				break;
			case Codes.launchPraiseFail:
				Toast.makeText(Activity_Detail.this, "点赞失败", Toast.LENGTH_SHORT).show();
				break;
				
			case Codes.signActivitySuc:
				Toast.makeText(Activity_Detail.this, "报名成功", Toast.LENGTH_SHORT).show();
				signTextView.setText("已报名"+(activity_temp.getSign_count()+1)+"人");
				break;
			case Codes.signActivityFail:
				Toast.makeText(Activity_Detail.this, "报名失败", Toast.LENGTH_SHORT).show();
				break;	
			}
		}
		
	};
	
}
