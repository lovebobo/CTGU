package com.ctguer;

import java.util.ArrayList;
import java.util.List;

import com.ctguer.controller.PagerSlidingImg;
import com.ctguer.model.homepage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Individualcenter extends Activity{

	private PagerSlidingImg slidingImg;//���������
	
	GridView ind_GridView; //�����б�
	
	GridviewAdapter<homepage> gridviewAdapter;//����������

    private ViewPager viewPager;    //ViewPager 
 
    private ImageView[] tips;  //װ����ImageView���� 
      
    private ImageView[] mImageViews;  //װImageView���� 
    
    private String[] imageUrls;//ͼƬ��Ӧ��URL
      
    private int[] imgIdArray ;  //ͼƬ��Դid 
    
    private ViewGroup group;
    private Runnable runnable;
    
    private RelativeLayout viewpage_Relativelayout;
	
	private List<homepage> IconDetailList =new ArrayList<homepage>();//��ҳͼ���б�
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_individualcenter);
		
		initView();//��ʼ������
		
		intiData();//��ʼ������
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		
		//�������
		MainActivity.resideMenu.clearIgnoredViewList();		
		
		//����ViewPage�Ļ�����ͻ
		MainActivity.resideMenu.addIgnoredView(findViewById(R.id.viewPager));
		
		super.onStart();
	}
	
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub		
		super.onResume();
		handler.postDelayed(runnable,5000);
	}

	@Override
    public void onPause()
    {		
        super.onPause();
        handler.removeCallbacksAndMessages(null);
        
    }

	Handler handler = new Handler(); 

	private void initView() {
		slidingImg=new PagerSlidingImg(this);
		// TODO Auto-generated method stub
		ind_GridView=(GridView)findViewById(R.id.ind_gridview);
	    group = (ViewGroup)findViewById(R.id.viewGroup);  
        viewPager = (ViewPager) findViewById(R.id.viewPager);                                
        slidingImg.setGroup(group);
        slidingImg.setViewPager(viewPager);
        
       
        
	}

 void intiData() {
		// TODO Auto-generated method stub
		IconDetailList.add(new homepage("�����ѯ",R.drawable.jiaowu ));
		IconDetailList.add(new homepage("�ͶƱ",R.drawable.toupiao));
		IconDetailList.add(new homepage("�����ȳ�",R.drawable.shuidian));
		IconDetailList.add(new homepage("ͼ���ѯ",R.drawable.tushu));
		IconDetailList.add(new homepage("ʧ������",R.drawable.shiwu));
		IconDetailList.add(new homepage("�����г�",R.drawable.tiaozao));
		IconDetailList.add(new homepage("�ڹ���ѧ",R.drawable.qinggong));
		IconDetailList.add(new homepage("��������",R.drawable.banshi));
		IconDetailList.add(new homepage("�ڴ�����",R.drawable.gengduo));
		
		//��������
		gridviewAdapter=new GridviewAdapter<homepage>(this, R.layout.homepage, IconDetailList);
		ind_GridView.setAdapter(gridviewAdapter);
		ind_GridView.setOnItemClickListener(new ItemClickListener());
		  
		//����ΪͼƬ��������
		
		//����ͼƬURL
		imageUrls=new String[]{"http://shop104171233.taobao.com/?spm=a230r.7195193.1997079397.2.zduakv","http://shop104171233.taobao.com/?spm=a230r.7195193.1997079397.2.zduakv","http://shop104171233.taobao.com/?spm=a230r.7195193.1997079397.2.zduakv"};
		slidingImg.setImageUrls(imageUrls);
		//����ͼƬ��ԴID  
        imgIdArray = new int[]{R.drawable.sisiter, R.drawable.sister2, R.drawable.sister3};  
        slidingImg.setImgIdArray(imgIdArray);
       //�������뵽ViewGroup��  
        tips = new ImageView[imgIdArray.length];  
        slidingImg.setTips(tips);
        slidingImg.addPoint(this);
       //��ͼƬװ�ص�������  
        mImageViews = new ImageView[imgIdArray.length];          
        slidingImg.setmImageViews(mImageViews);
        slidingImg.addImg(this);       
        slidingImg.setAdapter(this);  
        
        //�Զ��ƶ�
        runnable = new Runnable() {  
	        @Override  
	        public void run() {  
	        	handler.postDelayed(this, 5000);
	      	    viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
	      	    
	        }  
	    };  
		
 
	}  
	
	//��gridviewAdapter������(���������߼���)���򷵻ص�Item�����¼�     
    class  ItemClickListener implements OnItemClickListener 
	  {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			Intent intent;
			switch (position) {
			case 0:
				intent= new Intent(Individualcenter.this,
						NewCtguRelative.class);	
				startActivity(intent);
				break;

			case 1:
				intent = new Intent(Individualcenter.this,
						CtguRelative.class);	
				startActivity(intent);
				break;
			case 2:
				intent = new Intent(Individualcenter.this,
						ChatRobot.class);	
				startActivity(intent);
				break;
				//ͼ�����
			case 3:
				intent = new Intent(Individualcenter.this,
						BooksRelative.class);	
				startActivity(intent);
				break;
			
			case 4://ʧ������
				intent = new Intent(Individualcenter.this,
						LostFound.class);	
				startActivity(intent);
				break;
				
			case 5:
				intent = new Intent(Individualcenter.this,
						Activity_Detail.class);	
				startActivity(intent);
				break;
				
			case 6:
				intent = new Intent(Individualcenter.this,
						PartTimeActivity.class);	
				startActivity(intent);
				break;
				
			case 7:
				intent = new Intent(Individualcenter.this,
						CtguRelative.class);	
				startActivity(intent);
				break;
				
			case 8:
				intent = new Intent(Individualcenter.this,
						CtguRelative.class);	
				startActivity(intent);
				break;
				
			default:
				break;
			}		
		}	  
	  }
}
