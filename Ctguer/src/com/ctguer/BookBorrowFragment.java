package com.ctguer;

import java.util.ArrayList;
import java.util.List;

import com.ctguer.controller.BackHandledFragment;
import com.ctguer.controller.CmButton;
import com.ctguer.controller.Codes;
import com.ctguer.controller.RelateCtgu;
import com.ctguer.model.BorrowBook;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class BookBorrowFragment extends BackHandledFragment {

	private EditText userEditText;//�û���
		
	private EditText passwordEditText;//�����
	
	private Button loginButton;//��½��ť
	
	private RelativeLayout book_relativelayout;
	
	private List<BorrowBook> BorrowBookList = new ArrayList<BorrowBook>();
	
	private List<BorrowBook> tmpBorrowBookList = new ArrayList<BorrowBook>();
	
	private ListView bookListView;
	
	private listviewAdapter<BorrowBook> bookAdapter;
	
	AlertDialog verCode;//��֤���
	
	ImageButton imgbtn;//��֤��ͼ��
	
	EditText et;//������֤���
	
	Button verbtn;//��֤��ť
	
	ProgressDialog progressDialog;
	
	
	private View contextView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		contextView= inflater.inflate(R.layout.activity_book_borrow_fragment, container, false);  
		findById();
		initData();
		return contextView;
	}
	
	Handler handler=new Handler()
	{

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case Codes.getVerCodeSuc:
				Bitmap bitmap = (Bitmap) msg.obj;
				imgbtn.setImageBitmap(bitmap);
				break;
			case Codes.getVerCodeFail:
				Toast.makeText(getActivity(), "��ȡ��֤��ʧ��", Toast.LENGTH_LONG).show();
				break;
			case Codes.loginLibrarySuc:
				Toast.makeText(getActivity(), "��½ͼ��ݳɹ������ڶ�ȡͼ����Ϣ��", Toast.LENGTH_LONG).show();
				getMyBook(handler);
				break;	
			case Codes.loginLibraryFail:
				getVerificationCode();
				progressDialog.dismiss();
				Toast.makeText(getActivity(), "��½ͼ���ʧ��", Toast.LENGTH_LONG).show();
				break;
			case Codes.getLibraryBookFail:
				
				Toast.makeText(getActivity(), "��ȡͼ��ʧ��"+(String)msg.obj, Toast.LENGTH_LONG).show();
				break;	
				
			case Codes.getLibraryBookSuc:
				progressDialog.dismiss();
				verCode.dismiss();
				tmpBorrowBookList=(List<BorrowBook>)msg.obj;
				if(tmpBorrowBookList.size()>0)
				{
					bindingMyBookInfor(tmpBorrowBookList);
				}
				book_relativelayout.setVisibility(View.GONE);
				bookListView.setVisibility(View.VISIBLE);
				Toast.makeText(getActivity(), "��ȡͼ��ɹ�", Toast.LENGTH_LONG).show();
				break;	
			default:
				break;
			}
			super.handleMessage(msg);
		}
		
	};
	
	private void initData() {
		// TODO Auto-generated method stub
		bookAdapter=new listviewAdapter<BorrowBook>(getActivity(), R.layout.bookborrow_item, BorrowBookList);
		bookListView.setAdapter(bookAdapter);
	}
	
	private void findById() {
		// TODO Auto-generated method stub
		bookListView=(ListView)contextView.findViewById(R.id.book_listview);
		book_relativelayout=(RelativeLayout)contextView.findViewById(R.id.book_relativelayout);
		
		
		userEditText=(EditText)contextView.findViewById(R.id.bookedittext);
		passwordEditText=(EditText)contextView.findViewById(R.id.passwordedittext);
		loginButton=(Button)contextView.findViewById(R.id.bookbutton);
		
		
		loginButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				View Dialog = LayoutInflater.from(getActivity()).inflate(
						R.layout.pop_login, null);
				
				et = (EditText) Dialog.findViewById(R.id.UI_VerTxt);
				verbtn = (Button) Dialog.findViewById(R.id.UI_VerBtn);
				imgbtn = (CmButton) Dialog.findViewById(R.id.UI_VerImg);//��֤�밴ť
				getVerificationCode();
				verCode = new AlertDialog.Builder(getActivity()).setView(Dialog).show();	

				imgbtn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						getVerificationCode();
						Toast.makeText(getActivity(), "ˢ����֤��!",
								Toast.LENGTH_SHORT).show();
					}
				});
				
				verbtn.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (et.getText().length() > 0) {
							progressDialog = ProgressDialog.show(
									getActivity(), "����ͼ�����",
									"���ĺͺ����ܻ�õ������\n��������˹̹", true, true);
							RelateCtgu.loginLibragy_BorrowBook(getActivity(), handler, userEditText.getText().toString(), passwordEditText.getText().toString(), et.getText().toString());
																		
						}
						else {
							Toast.makeText(getActivity(), "Ҫ������֤��Ŷ!",
									Toast.LENGTH_SHORT).show();
						}
					}
				});
			}
		});
		
	}

	//���½�������
	protected void bindingMyBookInfor(List<BorrowBook> booklist) {
		// TODO Auto-generated method stub
		BorrowBookList.clear();
		for (BorrowBook borrowBook : booklist) {
			BorrowBookList.add(borrowBook);
		}
		bookAdapter.notifyDataSetChanged();
	}

	protected void getMyBook(Handler handler2) {
		// TODO Auto-generated method stub
		RelateCtgu.getInformation(handler2);		
	}

	//��ȡ��֤��
	protected void getVerificationCode() {
		// TODO Auto-generated method stub
		RelateCtgu.get_Library_VerCode(getActivity(), handler);
	}

	
	//ÿ��fragment��ʼ�����߲��ɼ�ʱ����
	@Override  
	public void setUserVisibleHint(boolean isVisibleToUser) {  
		if (isVisibleToUser) {  
			BooksRelative.mBackHandedFragment=this;
	    } else {  
	        //�൱��Fragment��onPause   
	    }  
	    super.setUserVisibleHint(isVisibleToUser);  
	    
	}  

	//��д���˼�
	@Override
	public boolean onBackPressed() {
		// TODO Auto-generated method stub
		if(bookListView.getVisibility()==View.VISIBLE)
		{
			bookListView.setVisibility(View.GONE);
			book_relativelayout.setVisibility(View.VISIBLE);
			return true;
		}
		else {
			return false;
		}
		
	}
}
