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

	private PagerSlidingImg slidingImg;//滑动界面库
	
	GridView ind_GridView; //网格列表
	
	GridviewAdapter<homepage> gridviewAdapter;//网格适配器

    private ViewPager viewPager;    //ViewPager 
 
    private ImageView[] tips;  //装点点的ImageView数组 
      
    private ImageView[] mImageViews;  //装ImageView数组 
    
    private String[] imageUrls;//图片对应的URL
      
    private int[] imgIdArray ;  //图片资源id 
    
    private ViewGroup group;
    private Runnable runnable;
    
    private RelativeLayout viewpage_Relativelayout;
	
	private List<homepage> IconDetailList =new ArrayList<homepage>();//首页图标列表
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_individualcenter);
		
		initView();//初始化界面
		
		intiData();//初始化数据
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		
		//清除滑动
		MainActivity.resideMenu.clearIgnoredViewList();		
		
		//忽略ViewPage的滑动冲突
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
		IconDetailList.add(new homepage("教务查询",R.drawable.jiaowu ));
		IconDetailList.add(new homepage("活动投票",R.drawable.toupiao));
		IconDetailList.add(new homepage("三大萌宠",R.drawable.shuidian));
		IconDetailList.add(new homepage("图书查询",R.drawable.tushu));
		IconDetailList.add(new homepage("失物招领",R.drawable.shiwu));
		IconDetailList.add(new homepage("跳蚤市场",R.drawable.tiaozao));
		IconDetailList.add(new homepage("勤工俭学",R.drawable.qinggong));
		IconDetailList.add(new homepage("办事流程",R.drawable.banshi));
		IconDetailList.add(new homepage("期待更多",R.drawable.gengduo));
		
		//绑定适配器
		gridviewAdapter=new GridviewAdapter<homepage>(this, R.layout.homepage, IconDetailList);
		ind_GridView.setAdapter(gridviewAdapter);
		ind_GridView.setOnItemClickListener(new ItemClickListener());
		  
		//以下为图片滑动代码
		
		//载入图片URL
		imageUrls=new String[]{"http://shop104171233.taobao.com/?spm=a230r.7195193.1997079397.2.zduakv","http://shop104171233.taobao.com/?spm=a230r.7195193.1997079397.2.zduakv","http://shop104171233.taobao.com/?spm=a230r.7195193.1997079397.2.zduakv"};
		slidingImg.setImageUrls(imageUrls);
		//载入图片资源ID  
        imgIdArray = new int[]{R.drawable.sisiter, R.drawable.sister2, R.drawable.sister3};  
        slidingImg.setImgIdArray(imgIdArray);
       //将点点加入到ViewGroup中  
        tips = new ImageView[imgIdArray.length];  
        slidingImg.setTips(tips);
        slidingImg.addPoint(this);
       //将图片装载到数组中  
        mImageViews = new ImageView[imgIdArray.length];          
        slidingImg.setmImageViews(mImageViews);
        slidingImg.addImg(this);       
        slidingImg.setAdapter(this);  
        
        //自动移动
        runnable = new Runnable() {  
	        @Override  
	        public void run() {  
	        	handler.postDelayed(this, 5000);
	      	    viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
	      	    
	        }  
	    };  
		
 
	}  
	
	//当gridviewAdapter被单击(触摸屏或者键盘)，则返回的Item单击事件     
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
				//图书相关
			case 3:
				intent = new Intent(Individualcenter.this,
						BooksRelative.class);	
				startActivity(intent);
				break;
			
			case 4://失物招领
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
