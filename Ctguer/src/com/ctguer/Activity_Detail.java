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
	
	private TextView textView_nick;//�ǳ�
	
	private TextView textView_launchtime;//����ʱ��
	
	private TextView textView_place;//�ص�
	
	private TextView textView_activity_time;//�ʱ��
	
	private TextView textView_content;//�����
	
	private TextView textView_num;//����
	
	private TextView textView_signnum;//�ѱ�������
	
	private TextView textView_commentnum;//����������
	
	private TextView textView_praisenum;//�ѵ�������
	
	
	
	
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
		textView_signnum.setText("�ѱ���"+activity_temp.getSign_count()+"��");
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
				Toast.makeText(Activity_Detail.this, "��ȡ�ɹ�", Toast.LENGTH_SHORT).show();
				break;

			case Codes.getCommentListFail:
				Toast.makeText(Activity_Detail.this, "��ȡʧ��", Toast.LENGTH_SHORT).show();
				break;
			case Codes.getCommentListFail1:
				Toast.makeText(Activity_Detail.this, "��������", Toast.LENGTH_SHORT).show();
				break;

			}
		}
		
	};
}