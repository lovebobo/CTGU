package com.ctguer;

import java.util.ArrayList;
import java.util.List;
import com.ctguer.controller.FileIO;
import com.ctguer.controller.URLs;
import com.ctguer.model.Course;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class My_CourseTable extends BaseActivity {

	private List<Course> coursesTable=new ArrayList<Course>();
	private LinearLayout ll[]=new LinearLayout[7];
	private TextView ortherTextView;
	
	Course array[][] = new Course[6][7];
	String othercourseTables[]=new String[]{};
	
	
	private int colors[] = {
			Color.rgb(0xee,0xff,0xff),
			Color.rgb(0xf0,0x96,0x09),
			Color.rgb(0x8c,0xbf,0x26),
			Color.rgb(0x00,0xab,0xa9),
			Color.rgb(0x99,0x6c,0x33),
			Color.rgb(0x3b,0x92,0xbc),
			Color.rgb(0xd5,0x4d,0x34),
			Color.rgb(0xcc,0xcc,0xcc)
		};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//ȥ��������
		setContentView(R.layout.activity_my__course_table);
		findById();
		
		init();
	}

	
	private void findById() {
		// TODO Auto-generated method stub
		//�ֱ��ʾ��һ������
         ll[0] = (LinearLayout)findViewById(R.id.ll1);
         ll[1]= (LinearLayout)findViewById(R.id.ll2);
         ll[2] = (LinearLayout)findViewById(R.id.ll3);
         ll[3]= (LinearLayout)findViewById(R.id.ll4);
         ll[4] = (LinearLayout)findViewById(R.id.ll5);
         ll[5] = (LinearLayout)findViewById(R.id.ll6);
         ll[6] = (LinearLayout)findViewById(R.id.ll7);
         ortherTextView=(TextView)findViewById(R.id.orthercourse);
	}
	private void bindingCourse() {
		// TODO Auto-generated method stub
		for (Course tmp : coursesTable) {
			
			//�ж��Ƿ��Ѿ��󶨿α��費��Ҫ�û�
			if(array[(tmp.StartSection+1)/2-1][tmp.WeekNum-1]!=null)
			{
				//Todo ���к͵�ǰ�ܱȽ���Ҫ����������ϵĿ��Ѿ����꣬��ȥ���ϵİ�
				
				//����Ƿ�ͬһʱ���ж��ſΣ�ȡ���ϵ���ʾ
				if(array[(tmp.StartSection+1)/2-1][tmp.WeekNum-1].StartWeek>tmp.StartWeek)
				{
					array[(tmp.StartSection+1)/2-1][tmp.WeekNum-1]=tmp;
				}
			}
			
			else {
				array[(tmp.StartSection+1)/2-1][tmp.WeekNum-1]=tmp;
			}	
		}
		//�󶨿α�
		for(int i=0;i<7;i++)
		{
			for(int j=0;j<6;j++)
			{
				if(array[j][i]==null)
				{
					setClass(ll[i], "", "", "", "", 2, 0);
				}
				else {					
					setClass(ll[i],array[j][i].Title, array[j][i].ClassRoomPos+"("+array[j][i].ClassNum+"��)", array[j][i].StartWeek+"��"+"~"+(array[j][i].StartWeek+array[j][i].WeekPeriod-1)+"��", array[j][i].TeacherName, 2, (int) (Math.random()*7+1));
				}
			}
		}		
	}

		//����γ̵ļ�����
	    class OnClickClassListener implements OnClickListener{
	    	
	    	@Override
			public void onClick(View v) {
	    		// TODO Auto-generated method stub
	    		String title;
	    		title = (String) ((TextView)v.findViewById(R.id.title)).getText();
	    		Toast.makeText(getApplicationContext(), "�ݲ�֧�ֶ��ſβ鿴������Ŭ��������....", 
	    				Toast.LENGTH_SHORT).show();
	    	}
		}
	
	 void setClass(LinearLayout ll, String title, String place,
	    		String teachername, String time, int classes, int color)
	    {
	    	View view = LayoutInflater.from(this).inflate(R.layout.courseitem, null);
	    	view.setMinimumHeight(dip2px(this,classes * 48));
	    	view.setBackgroundColor(colors[color]);
	    	((TextView)view.findViewById(R.id.title)).setText(title);
	        ((TextView)view.findViewById(R.id.place)).setText(place);
	        ((TextView)view.findViewById(R.id.teachername)).setText(teachername);
	        ((TextView)view.findViewById(R.id.time)).setText(time);
	        //Ϊ�γ�View���õ���ļ�����
	        view.setOnClickListener(new OnClickClassListener());
	        TextView blank1 = new TextView(this);
	        TextView blank2 = new TextView(this);
	        blank1.setHeight(dip2px(this,classes));
	        blank2.setHeight(dip2px(this,classes));
	        ll.addView(blank1);
	        ll.addView(view);
	        ll.addView(blank2);
	    }
	
		 public static int dip2px(Context context, float dpValue) {        
		    	final float scale = context.getResources().getDisplayMetrics().density;        
		    	return (int) (dpValue * scale + 0.5f);} /** * �����ֻ��ķֱ��ʴ� px(����) �ĵ�λ ת��Ϊ dp */
		 public static int px2dip(Context context, float pxValue) {        
			 	final float scale = context.getResources().getDisplayMetrics().density;        
			 	return (int) (pxValue / scale + 0.5f);}
		 
		 //��ʵ��γ�
		 private void bindingOtherCourse() {
				// TODO Auto-generated method stub
			 String temp="";
			 int j=0;
			 for(int i=0;i<othercourseTables.length;i++)
			 {
				 if(j%5==0)
				 {
					 j=1;
					 temp=temp+"\n";
					 temp=temp+othercourseTables[i];
					 
				 }
				 else {
					 j++;
					 temp=temp+othercourseTables[i];
				}
				 
			 }
				ortherTextView.setText(temp);
			}

		 
	private void init() {
		// TODO Auto-generated method stub
		
		Object object=FileIO.getObjectFromFile(this, URLs.courseTables);
		if(object!=null)
		{
			coursesTable=(List<Course>)object;
		}
		    othercourseTables=(String[])FileIO.getObjectFromFile(this, URLs.othercourseTables);
		
		if(coursesTable!=null)
		{
			bindingCourse();//�����γ�
		}
		
		if(othercourseTables!=null)
		{
			bindingOtherCourse();
		}
	}


	

	
}
