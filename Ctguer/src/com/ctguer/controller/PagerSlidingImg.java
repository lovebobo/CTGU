package com.ctguer.controller;

import com.ctguer.R;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

//����ͼƬ��װ��
public class PagerSlidingImg {
	
		private Context context;
	
	 	private ViewPager viewPager;    //ViewPager 
	 
	    private ImageView[] tips;  //װ����ImageView���� 
	      
	    private ImageView[] mImageViews;  //װImageView���� 
	      
	    private int[] imgIdArray ;  //ͼƬ��Դid 
	    
	    private String[] imageUrls;  //װImageView���� 
	    
		
		private ViewGroup group;
	    
	  //�������뵽ViewGroup��  
	    
		public String[] getImageUrls() {
			return imageUrls;
		}


		public void setImageUrls(String[] imageUrls) {
			this.imageUrls = imageUrls;
		}
		
	    public PagerSlidingImg(Context context) {
			// TODO Auto-generated constructor stub
		}

		
		public ViewPager getViewPager() {
			return viewPager;
		}

		public void setViewPager(ViewPager viewPager) {
			this.viewPager = viewPager;
		}

		public ImageView[] getTips() {
			return tips;
		}

		public void setTips(ImageView[] tips) {
			this.tips = tips;
		}

		public ImageView[] getmImageViews() {
			return mImageViews;
		}

		public void setmImageViews(ImageView[] mImageViews) {
			this.mImageViews = mImageViews;
		}

		public int[] getImgIdArray() {
			return imgIdArray;
		}

		public void setImgIdArray(int[] imgIdArray) {
			this.imgIdArray = imgIdArray;
		}

		public ViewGroup getGroup() {
			return group;
		}

		public void setGroup(ViewGroup group) {
			this.group = group;
		}

		public void addPoint(Context context)
	    {
	    	for(int i=0; i<tips.length; i++){  
	            ImageView imageView = new ImageView(context);  
	            imageView.setLayoutParams(new LayoutParams(10,10));  
	            tips[i] = imageView;  
	            if(i == 0){  
	                tips[i].setBackgroundResource(R.drawable.page_indicator_focused);  
	            }else{  
	                tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);  
	            }  
	              
	            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,    
	                    LayoutParams.WRAP_CONTENT));  
	            layoutParams.leftMargin = 5;  
	            layoutParams.rightMargin = 5;  
	            group.addView(imageView, layoutParams); 
	        }
	    }

		public void addImg(Context context)
	    {
			for(int i=0; i<mImageViews.length; i++){  
	            ImageView imageView = new ImageView(context);  
	            mImageViews[i] = imageView;  
	            imageView.setBackgroundResource(imgIdArray[i]);  
	        }  
	    }
		
		
		
		
		//����Adapter  
		public void setAdapter(Context context)
		{
		
			this.context=context;
									
	        viewPager.setAdapter(new MyAdapter());  	        	        
		    
	        //���ü�������Ҫ�����õ��ı���  
	        viewPager.setOnPageChangeListener(new OnPageChangeListener() {
				       	        	
        	@Override
        	public void onPageSelected(int arg0) {
        		// TODO Auto-generated method stub
        		setImageBackground(arg0 % mImageViews.length); 
        	}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});  
        //����ViewPager��Ĭ����, ����Ϊ���ȵ�100���������ӿ�ʼ�������󻬶�  
        viewPager.setCurrentItem((mImageViews.length) * 100); 
        
	}
		
		
		//PagerAdapter ���������ڲ���
		 public class MyAdapter extends PagerAdapter{  
			  
		        @Override  
		        public int getCount() {  
		            return Integer.MAX_VALUE;  
		        }  	  
		        
		        
		        
		        @Override  
		        public void destroyItem(View container, int position, Object object) {  
		           // ((ViewPager)container).removeView(mImageViews[position % mImageViews.length]);  
		              
		        }  
		  
		        /** 
		         * ����ͼƬ��ȥ���õ�ǰ��position ���� ͼƬ���鳤��ȡ�����ǹؼ� 
		         */  
		        @Override  
		        public Object instantiateItem(View container, int position) {  
		        	 try {    
		                 ((ViewPager)container).addView(mImageViews[position % mImageViews.length], 0);  
		                  mImageViews[position% mImageViews.length].setOnClickListener(new OnClickListener() {							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								Uri uri = Uri.parse(imageUrls[0]); 
								Intent it  = new Intent(Intent.ACTION_VIEW,uri); 
								context.startActivity(it);  
								
								}
						});
		        	 }catch(Exception e){  
		                 //handler something
		        		 int i=0,j=0;
							i=j;
		             }  
		             return mImageViews[position % mImageViews.length];  
		        }

				@Override
				public boolean isViewFromObject(View arg0, Object arg1) {
					// TODO Auto-generated method stub
					return arg0 == arg1; 
				}  
		          
		                    
		    }  	
		 /** 
	     * ����ѡ�е�tip�ı��� 
	     * @param selectItems 
	     */  
	    private void setImageBackground(int selectItems){  
	        for(int i=0; i<tips.length; i++){  
	            if(i == selectItems){  
	                tips[i].setBackgroundResource(R.drawable.page_indicator_focused);  
	            }else{  
	                tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);  
	            }  
	        }  
	    }
}
