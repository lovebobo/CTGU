package com.ctguer;

import java.util.ArrayList;
import java.util.List;

import com.ctguer.controller.BackHandledFragment;
import com.ctguer.controller.Codes;
import com.ctguer.controller.RelateCtgu;
import com.ctguer.model.Book;
import com.ctguer.model.BookDetail;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.AbsListView.OnScrollListener;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class BookSearchFragment extends BackHandledFragment{

	private View contextView;
	
	private EditText bookEditText;//搜索框
	
	private Button bookButton;//搜索按钮
	
	private ListView booklistView;//书籍列表
	
	private RelativeLayout relativeLayout;
	
	protected ProgressDialog progressDialog;
	
	private int lastItem;
	
	AlertDialog.Builder builder;
	
	private int startPage=1;
	
	private listviewAdapter<Book> bookListviewAdapter;
	
	private List<Book> booksList=new ArrayList<Book>();
	
	private ArrayList<BookDetail> bookDetailsList=new ArrayList<BookDetail>();
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		LayoutParams params = new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		contextView= inflater.inflate(R.layout.activity_book_search_fragment, container, false);  
		findById();
		bindFirst();
		return contextView;
	}

	
	private void bindFirst() {
		// TODO Auto-generated method stub
		/*Object object=FileIO.getObjectFromFile(getActivity(), URLs.bookTables);
		if(object!=null)
		{
			booksList=(List<Book>)object;
		}*/
		bookListviewAdapter=new listviewAdapter<Book>(getActivity(), R.layout.bookssearch_item, booksList);
		
		booklistView.setAdapter(bookListviewAdapter);
		
	}

	Handler handler=new Handler()
	{

		@Override
		public void handleMessage(Message msg) {
			
			//不管得到结果怎么样都让progressDialog.dismiss();
			progressDialog.dismiss();
			// TODO Auto-generated method stub
			switch (msg.what) {
			case Codes.getBookSuc:		
				if(msg.obj!=null)
				{
					for (Book tmp : (List<Book>)msg.obj) {
						booksList.add(tmp);
					}
					
				}
				bookListviewAdapter.notifyDataSetChanged();
				break;
				//无结果
			case Codes.getNoBook:
				Toast.makeText(getActivity(), "无查询结果", Toast.LENGTH_LONG).show();
				break;
				//查找失败
			case Codes.getBookFail:
				Toast.makeText(getActivity(), "请检查网络设置！", Toast.LENGTH_LONG).show();
				break;
				//获得图书详情
			case Codes.getBookDetailSuc:
				bookDetailsList=(ArrayList<BookDetail>)msg.obj;
				showBookDetail();
				break;
				
			default:
				break;
			}
			super.handleMessage(msg);
		}
		
	};
	
	
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		//清空存储的书籍列表
		//FileIO.saveObjectToFile(getActivity(), URLs.bookTables, null);
		super.onDestroy();
	}

	protected void showBookDetail() {
		// TODO Auto-generated method stub
		ArrayList<String> s = new ArrayList<String>();

		builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("图书详情");
		for (int i = 0; i < bookDetailsList.size(); i++) {
			s.add("\n馆藏地" + bookDetailsList.get(i).place + "\n索取号" + bookDetailsList.get(i).indexNumber + "\n状态" + bookDetailsList.get(i).status);

		}
		int lenth = s.size();
		String[] a = new String[lenth];
		for (int i = 0; i < lenth; i++) {
			a[i] = s.get(i);
		}
		builder.setItems(a, null);
		builder.create().show();
	}

	private void findById() {
		// TODO Auto-generated method stub
		
		//实例化progressDialog
		progressDialog = new ProgressDialog(getActivity());
		progressDialog.setTitle("正在加载");
		progressDialog.setMessage("正在努力加载数据中，请耐心等候...");
		
		bookEditText=(EditText)contextView.findViewById(R.id.bookedittext);
		booklistView=(ListView)contextView.findViewById(R.id.book_listview);
		booklistView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				if (lastItem > bookListviewAdapter.getCount() && scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
					progressDialog.show();
					RelateCtgu.loginlibrary_Ctgu(getActivity(),handler, bookEditText.getText().toString(),++startPage);
				}
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				lastItem = firstVisibleItem + visibleItemCount + 1;
				
			}
		});
		booklistView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				Book book = bookListviewAdapter.getItem(position);
				progressDialog.show();
				RelateCtgu.booklibrary_detail(getActivity(), handler, book.bookUrl);
				
			}
		});
		bookButton=(Button)contextView.findViewById(R.id.bookbutton);
		relativeLayout=(RelativeLayout)contextView.findViewById(R.id.book_relativelayout);
		bookButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(bookEditText.getText().length()<1)
				{
					Toast.makeText(getActivity(), "请先填写书名", Toast.LENGTH_SHORT);
				}
				progressDialog.show();
				relativeLayout.setVisibility(View.GONE);
				RelateCtgu.loginlibrary_Ctgu(getActivity(),handler, bookEditText.getText().toString(),startPage);
			}
		} );
	}
	
	@Override  
	public void setUserVisibleHint(boolean isVisibleToUser) {  
		if (isVisibleToUser) {  
			BooksRelative.mBackHandedFragment=this;
	    } else {  
	        //相当于Fragment的onPause   
	    }  
	    super.setUserVisibleHint(isVisibleToUser);  
	    
	}  
	
	@Override
	public boolean onBackPressed() {
		// TODO Auto-generated method stub
		if(booklistView.getVisibility()==View.VISIBLE)
		{
			booklistView.setVisibility(View.GONE);
			relativeLayout.setVisibility(View.VISIBLE);
			return true;
		}
		else {
			return false;
		}
		
	}

	

	
}
