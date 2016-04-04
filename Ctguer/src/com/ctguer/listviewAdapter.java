package com.ctguer;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import com.ctguer.controller.Codes;
import com.ctguer.controller.RelateCtgu;
import com.ctguer.controller.URLs;
import com.ctguer.model.Activity;
import com.ctguer.model.Book;
import com.ctguer.model.BorrowBook;
import com.ctguer.model.LostFoundDetail;

import com.ctguer.model.NewsContent;

import com.ctguer.model.PartTimeJob;

import com.ctguer.model.Score;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class listviewAdapter<T> extends ArrayAdapter<T> 
{
	private Context mContext;
	private int mViewResourceID = 0;
	private List<T> mViewList;
	private int mposition=-1;
	private ViewHolder viewHolder;//成绩的存储标签
	private ProgressDialog progressDialog;
	private buttonOnClickListener clickListener=new buttonOnClickListener();

	public listviewAdapter(Context context, int textViewResourceId,
			List<T> objects) {
		super(context, textViewResourceId, objects);
		mContext = context;
		mViewResourceID = textViewResourceId;
		mViewList = objects;
		progressDialog = ProgressDialog.show(
				mContext, "接入图书馆中",
				"正在续借，请耐心等候！！", true, true);
		progressDialog.dismiss();
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
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					mViewResourceID, null);
			}
		
		//convertView会被重用，滑动新一页会重用上一页的convertView
		
		if(mViewList.get(0).getClass() == NewsContent.class){
			bindingNewsItem((NewsContent)mViewList.get(position), convertView);
		}
		
		if (mViewList.get(0).getClass() == Score.class)
		{
			if(convertView !=null){
				viewHolder = new ViewHolder();
				viewHolder.textViewcourse=(TextView)convertView.findViewById(R.id.name_course_textview);
				viewHolder.textViewscore=(TextView)convertView.findViewById(R.id.grade_textview);
				viewHolder.textViewxuefen=(TextView)convertView.findViewById(R.id.xuefen_textview);
				convertView.setTag(viewHolder);
			}
			
			else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			bindingScoreItem((Score) mViewList.get(position), convertView);
		}
		
		if (mViewList.get(0).getClass() == Book.class)
		{
			bindingBook((Book)mViewList.get(position),convertView);
		}
		
		if(mViewList.get(0).getClass() == LostFoundDetail.class){
			bindingLost((LostFoundDetail)mViewList.get(position),convertView);
		}
		
		if(mViewList.get(0).getClass() == BorrowBook.class){
			bindingBorrowBook((BorrowBook)mViewList.get(position),convertView,position);
		}
		if(mViewList.get(0).getClass() == PartTimeJob.class){
			bindingJob((PartTimeJob)mViewList.get(position),convertView,position);
		}
		
		if(mViewList.get(0).getClass() == Activity.class){
			bindingActivity((Activity)mViewList.get(position),convertView,position);
		}
		return convertView;
	}
	
	private void bindingActivity(Activity activity, View convertView, int position) {
		// TODO Auto-generated method stub
		((TextView)convertView.findViewById(R.id.id_textview)).setText(activity.getTitle());
		((TextView)convertView.findViewById(R.id.time_textview)).setText(activity.getLaunchtime());
		((TextView)convertView.findViewById(R.id.place)).setText(activity.getPlace());
		((TextView)convertView.findViewById(R.id.time)).setText(activity.getDatatime());
		((TextView)convertView.findViewById(R.id.content)).setText(activity.getContent());
		
		((TextView)convertView.findViewById(R.id.Limit_people_num)).setText(activity.getLimit_count()+"");
		
		((TextView)convertView.findViewById(R.id.sign_textview)).setText("已报名"+activity.getSign_count()+"人");
		((TextView)convertView.findViewById(R.id.text_view_1)).setText(activity.getPraise_count()+"");
		((TextView)convertView.findViewById(R.id.text_view_2)).setText(activity.getComment_count()+"");
	}
	
	
	//绑定勤工俭学
	private void bindingJob(PartTimeJob job, View convertView,int position) {
		String t=null;
		String b=null;
		String a = job.getJobcontext().substring(0,job.getJobcontext().indexOf("[")-1);
if(job.getJobcontext().contains("状态")){
		 t = job.getJobcontext().substring(job.getJobcontext().indexOf("[")+1,job.getJobcontext().indexOf("状")-1);
		 b = job.getJobcontext().substring(job.getJobcontext().indexOf("状"),job.getJobcontext().length()-1);
}
else{
	 t = job.getJobcontext().substring(job.getJobcontext().indexOf("[")+1,job.getJobcontext().indexOf("浏")-1);
	 b = job.getJobcontext().substring(job.getJobcontext().indexOf("浏"),job.getJobcontext().length()-1);
}

((TextView)convertView.findViewById(R.id.showcontext)).setText(a);
		((TextView)convertView.findViewById(R.id.showtime)).setText(t);
		((TextView)convertView.findViewById(R.id.showtype)).setText(b);
	}
	
	//绑定图书借还情况
	private void bindingBorrowBook(BorrowBook borrowBook, View convertView,int position) {
		// TODO Auto-generated method stub
		((TextView)convertView.findViewById(R.id.bookTitle)).setText(borrowBook.getBookName());
		((TextView)convertView.findViewById(R.id.author)).setText(borrowBook.getBookType());
		((TextView)convertView.findViewById(R.id.publisher)).setText(borrowBook.getLoginId());
		((TextView)convertView.findViewById(R.id.publicationYear)).setText(borrowBook.getBorrowTime());
		((TextView)convertView.findViewById(R.id.available)).setText(borrowBook.getLastTime());
		((TextView)convertView.findViewById(R.id.callNumber)).setText("".equals(borrowBook.getStatus())?"可续借":borrowBook.getStatus());
		Button button=(Button)convertView.findViewById(R.id.myButton); 
		button.setOnClickListener(clickListener);
		if("".equals(borrowBook.getStatus())){
			(button).setVisibility(View.VISIBLE);;
		}
		button.setTag(position);
	}
	private void bindingLost(LostFoundDetail lostFound, View convertView) {
		// TODO Auto-generated method stub
		((TextView)convertView.findViewById(R.id.goodname)).setText(lostFound.getName());
		((TextView)convertView.findViewById(R.id.detail)).setText(lostFound.getDetail());
		((TextView)convertView.findViewById(R.id.showtime)).setText(lostFound.getPosttime());
		
	}
	private void bindingBook(Book book,View convertView) {
		// TODO Auto-generated method stub
		((TextView)convertView.findViewById(R.id.bookTitle)).setText(book.BookName);
		((TextView)convertView.findViewById(R.id.zerenlayout)).setText(book.authorName);
		((TextView)convertView.findViewById(R.id.publiclayout)).setText(book.PublicPlace);
		((TextView)convertView.findViewById(R.id.publicationYear)).setText(book.PublicTime);
		((TextView)convertView.findViewById(R.id.borrylayout)).setText(book.UnUsedNum);
		((TextView)convertView.findViewById(R.id.idlayout)).setText(book.BookId);
	}
	
	private void bindingNewsItem(NewsContent homepage, View convertView) {
		// TODO Auto-generated method stub
		TextView textViewcontent=((TextView)convertView.findViewById(R.id.news_summary));
		TextView textViewtime = (TextView)convertView.findViewById(R.id.news_arrow);
		textViewcontent.setText(homepage.getContent());
		if(homepage.getPv() != null){
			textViewtime.setText(homepage.getTime() + "  " + homepage.getPv());
		}
		else{
			textViewtime.setText(homepage.getTime());
		}
	}
	
	private void bindingScoreItem(Score score, View convertView) {
		// TODO Auto-generated method stub
		viewHolder.textViewcourse.setText(score.CourseName);
		viewHolder.textViewscore.setText(score._Score);
		viewHolder.textViewxuefen.setText(""+score.GPA);
	}
	
	private  class ViewHolder {		
		TextView textViewcourse;
		TextView textViewscore;
		TextView textViewxuefen;
	}
	
	class buttonOnClickListener implements OnClickListener{

		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			try {
				String booknameurl = URLDecoder.decode("A2470398%e7%bb%ad%e5%80%9f%e6%88%90%e5%8a%9f%ef%bc%81@","utf-8");
				String booknameurl1=booknameurl; 
				mposition=(int)v.getTag();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			BorrowBook tmpBook=((BorrowBook)getItem(mposition));
			if(mposition!=-1)
			{
				progressDialog.show();
				//去掉url前面的2个点
				RelateCtgu.continue_Borrow(handler,URLs.CTGU_ts+"user/"+tmpBook.bookUrl.substring(2, tmpBook.bookUrl.length()),mposition);
			}
		}  
	
	}
	
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			//续借成功
			case Codes.continueBorrowSuc:
				RelateCtgu.getReBorrowBook(handler);
				break;
			//续借失败
			case Codes.continueBorrowFail:
				progressDialog.dismiss();
				Toast.makeText(mContext, "续借失败！！"+(String)msg.obj, Toast.LENGTH_LONG).show();
				break;
				
			case Codes.chonecontinueBorrowSuc:
				//跟新界面，隐藏续借按钮！！
				((BorrowBook)getItem(mposition)).bookType="续满";
				notifyDataSetChanged();
				progressDialog.dismiss();
				Toast.makeText(mContext, "续借成功！！", Toast.LENGTH_LONG).show();
				break;
			default:
				break;
			}
		}
		
	};
}
