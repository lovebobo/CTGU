package com.ctguer;

import java.util.List;
import com.ctguer.model.homepage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridviewAdapter<T>  extends ArrayAdapter<T> {

	private Context mContext;
	private int mViewResourceID = 0;
	private List<T> mViewList;


	public GridviewAdapter(Context context, int textViewResourceId,
			List<T> objects) {
		super(context, textViewResourceId, objects);
		mContext = context;
		mViewResourceID = textViewResourceId;
		mViewList = objects;
	}
	
	public void reloadList(List<T> objects) {
		if(objects!=null){
			mViewList = objects;
			}
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					mViewResourceID, null);
			}
		homepage item=(homepage) getItem(position);
		((TextView)convertView.findViewById(R.id.text)).setText(item.getName());
		((ImageView)convertView.findViewById(R.id.icon)).setBackgroundResource(item.getIcon());;
		return convertView;
	}

}
