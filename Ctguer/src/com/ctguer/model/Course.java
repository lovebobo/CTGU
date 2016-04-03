package com.ctguer.model;

import java.io.Serializable;

public class Course implements Serializable
{	
	public String ID;// Tostring
	public String Title;//课程名
	public String ClassNum;//班号
	public String TeacherName;
	public String ClassRoomPos;//上课教室
	public int StartSection=0;//第几节课
	public int CoursePeriod;//持续几节课
	public int StartWeek=0;//起始周数
	public int WeekPeriod; //持续周期
	public boolean isAlarm = true;//是否设置了提醒
	public int AlarmType = 0;//暂时0为一般，1为播放声音
	public int WeekNum;//星期几
	public int WeekSpecialNum=0;//0无区分，1，单，2双
	public boolean isCollision = false;
	public boolean isOverlapping = false;
	
	public Course()
	{
		//TODO　ID;
	}
	
	public Course(String title,String classnum,String teachername,String classroompos,int startsection,int courseperiod,int startweek,int weekperiod, int weeknum, int weekspecial, boolean isCollision, boolean isOverlapping)
	{
		//this.ID = this.hashCode();
		this.Title = title;
		this.ClassNum = classnum;
		this.TeacherName = teachername;
		this.ClassRoomPos = classroompos;
		this.StartSection = startsection;
		this.CoursePeriod = courseperiod;
		this.StartWeek = startweek;
		this.WeekPeriod = weekperiod;
		this.WeekNum = weeknum;
		this.WeekSpecialNum = weekspecial;
		this.isCollision = isCollision;
		this.isOverlapping = isOverlapping;
	}

	public void giveId(int year, int term)
	{
		//TODO 班号不可空！！
		this.ID = ("ctgu_" + Integer.toString(year) + Integer.toString(term) + this.Title + this.ClassNum);
	}
	
}

