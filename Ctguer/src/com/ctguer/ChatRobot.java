package com.ctguer;

import java.util.ArrayList;
import java.util.List;

import com.ctguer.controller.HttpUtils;
import com.ctguer.model.ChatMessage;
import com.ctguer.model.ChatMessage.Type;

import android.R.integer;
import android.app.Activity;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ChatRobot extends Activity {

	private ListView chatlistview;
	
	private List<ChatMessage> messageList=new ArrayList<ChatMessage>();
	
	private Type type;
	
	private ChatListviewAdapter chatAdapter;
	
	private EditText msgEdittext;
	
	private Button sengButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_chat_robot);
		initData();
		
		bindData();
	}

	private void bindData() {
		// TODO Auto-generated method stub
		chatAdapter=new ChatListviewAdapter(this, messageList);
		chatlistview.setAdapter(chatAdapter);
	}

	private void initData() {
		// TODO Auto-generated method stub
		chatlistview=(ListView)findViewById(R.id.my_chat_listview);
		msgEdittext=(EditText)findViewById(R.id.id_chat_msg);
		sengButton=(Button)findViewById(R.id.id_chat_send);
			
		/*for(int i=0;i<10;i++)
		{
			if(i%2==0)
			messageList.add(new ChatMessage(type.INPUT,"我喜欢你哦"));
			else {
			messageList.add(new ChatMessage(type.OUTPUT,"我喜欢你哦"));
			}
		}*/
		messageList.add(new ChatMessage(type.INPUT,"欢迎来到三大萌宠，现在就可以开始调戏我了哦，come on,gay!"));
				
	}
	
	private Handler handler=new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{
			ChatMessage from = (ChatMessage) msg.obj;
			messageList.add(from);
			chatAdapter.notifyDataSetChanged();
			chatlistview.setSelection(messageList.size() - 1);
		};
	};
	
	//发送消息
	public void sendMessage(View view)
	{
		final String msg = msgEdittext.getText().toString();
		if (TextUtils.isEmpty(msg))
		{
			Toast.makeText(this, "发送内容不能为空!", Toast.LENGTH_SHORT).show();
			return;
		}
		ChatMessage to = new ChatMessage(Type.OUTPUT, msg);
		
		messageList.add(to);
		
		chatAdapter.notifyDataSetChanged();
		chatlistview.setSelection(messageList.size() - 1);

		msgEdittext.setText("");
		
		//关闭软键盘
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		
			if (imm.isActive())
			{
					
				imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
						InputMethodManager.HIDE_NOT_ALWAYS);
					
			}
		new Thread()
		{

			@Override
			public void run() {
				ChatMessage from = null;
				try
				{
					from = HttpUtils.sendMsg(msg);
				} catch (Exception e)
				{
					from = new ChatMessage(Type.INPUT, "请检查网络!");
				}
				Message message = Message.obtain();
				message.obj = from;
				handler.sendMessage(message);
			}
			
		}.start();
		
	}
}
