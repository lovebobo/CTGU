package com.ctguer.controller;

import java.util.List;

import com.ctguer.R;
import android.content.Context;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 鑷畾涔夐�閰嶅櫒
 * @author wulianghuan
 *
 */
public class MyAdapter<T> extends ArrayAdapter<T> {
	Vibrator vibrator;
	
	LayoutParams params;
	

	private Context mContext;
	private int mViewResourceID = 0;
	private List<T> mViewList;

	public MyAdapter (Context context, int textViewResourceId,
			List<T> objects) {
		super(context, textViewResourceId, objects);
		mContext = context;
		mViewResourceID = textViewResourceId;
		mViewList = objects;
	}
	

	@Override
	public int getCount() {
		return mViewList.size();
	}

	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}
	@Override
	public T getItem(int position) {
		return mViewList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

			@Override
public View getView(int position, View convertView, ViewGroup parent) {
				View item = LayoutInflater.from(mContext).inflate(
						mViewResourceID, null);
				
				
				ImageInfo it=(ImageInfo) getItem(position);
				ImageView iv = (ImageView) item.findViewById(R.id.imageView1);
				RelativeLayout relativeLayout = (RelativeLayout)item.findViewById(R.id.relativeLayout);
				iv.setImageResource(it.imageId);
				relativeLayout.setBackgroundResource(it.bgId);
				relativeLayout.getBackground().setAlpha(225);
				TextView tv = (TextView) item.findViewById(R.id.msg);
				tv.setText(it.imageMsg);
				return item;
			
		}



}
