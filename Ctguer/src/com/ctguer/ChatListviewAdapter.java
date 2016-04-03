package com.ctguer;

import java.util.List;

import com.ctguer.model.ChatMessage;
import com.ctguer.model.ChatMessage.Type;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ChatListviewAdapter extends BaseAdapter
{
	private LayoutInflater mInflater;
	private List<ChatMessage> mDatas;
	

	public ChatListviewAdapter(Context context, List<ChatMessage> datas)
	{
		mInflater = LayoutInflater.from(context);
		mDatas = datas;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mDatas.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mDatas.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public int getItemViewType(int position)
	{
		ChatMessage msg = mDatas.get(position);
		return msg.getType() == Type.INPUT ? 1 : 0;
	}

	@Override
	public int getViewTypeCount()
	{
		return 2;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ChatMessage chatMessage = mDatas.get(position);

		ViewHolder viewHolder = null;
		if (convertView == null)
		{
			viewHolder = new ViewHolder();
			if (chatMessage.getType() == Type.INPUT)
			{
				convertView = mInflater.inflate(R.layout.chat_receivemsg,
						parent, false);
				viewHolder.content = (TextView) convertView
						.findViewById(R.id.chat_from_content);
				convertView.setTag(viewHolder);
			} else
			{
				convertView = mInflater.inflate(R.layout.chat_sendmsg,
						null);

				viewHolder.content = (TextView) convertView
						.findViewById(R.id.chat_send_content);
				convertView.setTag(viewHolder);
			}

		} else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.content.setText(chatMessage.getMsg());


		return convertView;
	}
	private class ViewHolder
	{
		public TextView content;
	}
	
}
