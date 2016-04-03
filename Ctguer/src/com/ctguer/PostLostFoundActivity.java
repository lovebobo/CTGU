package com.ctguer;


import com.ctguer.controller.Codes;
import com.ctguer.controller.RelateCtgu;
import com.ctguer.model.LostFoundDetail;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class PostLostFoundActivity extends BaseActivity {

	private RadioButton find;
	private RadioButton lost;
	private EditText lost_find_edit_goods;
	private EditText lost_find_edit_keyword;
	private EditText lost_find_edit_name;
	private EditText lost_find_edit_other;
	private EditText lost_find_edit_phone;
	private EditText lost_find_edit_place;
	private Button lost_find_edit_publish;
	private EditText  lost_find_edit_time;
	private RadioGroup lost_find_type;
	private int type = 0;
	private LostFoundDetail postlostfound = new LostFoundDetail();
	protected ProgressDialog progressDialog;
	
	
	Handler handler = new Handler()
	{
		@Override 
		public void handleMessage(Message msg)
		{   
			progressDialog.dismiss();
			 switch (msg.what){   
	           case Codes.postlostfoundSuc:
				{
					Toast.makeText(PostLostFoundActivity.this, "提交成功！", Toast.LENGTH_LONG).show();
					finish();
				}
					break;
	            case Codes.postlostfoundFail:
				{
					Toast.makeText(getApplication(), "请检查网络设置！", Toast.LENGTH_LONG).show();
					break;
				}
					
				default:
					break;
	            }   
	           super.handleMessage(msg);  
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_post_lost_found);
		findId();
		
		
		
	}

	private void findId() {
		// TODO Auto-generated method stub
		
			lost_find_edit_goods = (EditText) findViewById(R.id.lost_find_edit_goods);
			lost_find_edit_keyword = (EditText) findViewById(R.id.lost_find_edit_keyword);
			lost_find_edit_name = (EditText) findViewById(R.id.lost_find_edit_name);
			lost_find_edit_other = (EditText) findViewById(R.id.lost_find_edit_other);
			lost_find_edit_phone = (EditText) findViewById(R.id.lost_find_edit_phone);
			lost_find_edit_place = (EditText) findViewById(R.id.lost_find_edit_place);
			lost_find_edit_time = (EditText) findViewById(R.id.lost_find_edit_time);
			lost = (RadioButton) findViewById(R.id.lost);
			find = (RadioButton) findViewById(R.id.find);
			lost_find_type = (RadioGroup) findViewById(R.id.lost_find_type);
			lost_find_type.setOnCheckedChangeListener(new OnCheckedChangeListenerImp());	
			lost_find_edit_publish = (Button) findViewById(R.id.lost_find_edit_publish);
			
			
			lost_find_edit_publish.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					System.out.println(lost_find_edit_goods.getText().toString().trim().length());
					if(lost_find_edit_goods.getText().toString().trim().length() < 1
							||lost_find_edit_keyword.getText().toString().trim().length() < 1)
					{
					Toast.makeText(PostLostFoundActivity.this, "请填写信息", Toast.LENGTH_LONG).show();
					return;
					}
					postlostfound.setName(lost_find_edit_goods.getText().toString().trim());
					postlostfound.setDetail(lost_find_edit_keyword.getText().toString().trim());
					
					if(lost_find_edit_time.getText().toString().trim().length() < 1){
						postlostfound.setTime("无");
					}else{
						postlostfound.setTime(lost_find_edit_time.getText().toString().trim());
					}
					
					
					
					if(lost_find_edit_place.getText().toString().trim().length() < 1){
						postlostfound.setAddress("无");
					}else{
						postlostfound.setAddress(lost_find_edit_place.getText().toString().trim());
					}
					
					if(lost_find_edit_phone.getText().toString().trim().length() < 1){
						postlostfound.setTele("无");
					}
					else
					{
						postlostfound.setTele(lost_find_edit_phone.getText().toString().trim());
					}
					
					if(lost_find_edit_name.getText().toString().trim().length() < 1){
						postlostfound.setPostName("无");
					}else {
						postlostfound.setPostName(lost_find_edit_name.getText().toString().trim());
					}
				if(type==0){
					Toast.makeText(PostLostFoundActivity.this, "请填写信息类型", Toast.LENGTH_LONG).show();
					return;
					
				}
					postlostfound.setType(String.valueOf(type));
					
					if(lost_find_edit_other.getText().toString().trim().length() < 1){
						postlostfound.setOther("无");
					}else {
						postlostfound.setOther(lost_find_edit_other.getText().toString().trim());
					}
					postlostfound.setQq("无");
					progressDialog = new ProgressDialog(PostLostFoundActivity.this);
					progressDialog.setTitle("正在加载");
					progressDialog.setMessage("正在努力加载数据中，请耐心等候...");
					progressDialog.show();
					RelateCtgu.PostLostFound(handler, postlostfound);
				}
			});
		
	}
	class OnCheckedChangeListenerImp implements RadioGroup.OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			if (lost.getId() == checkedId) {
				type = 1;
			} else if (find.getId() == checkedId) {
				type = 2;
			}
		}
	}
}
