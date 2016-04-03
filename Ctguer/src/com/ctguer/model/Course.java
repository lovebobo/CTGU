package com.ctguer.model;

import java.io.Serializable;

public class Course implements Serializable
{	
	public String ID;// Tostring
	public String Title;//�γ���
	public String ClassNum;//���
	public String TeacherName;
	public String ClassRoomPos;//�Ͽν���
	public int StartSection=0;//�ڼ��ڿ�
	public int CoursePeriod;//�������ڿ�
	public int StartWeek=0;//��ʼ����
	public int WeekPeriod; //��������
	public boolean isAlarm = true;//�Ƿ�����������
	public int AlarmType = 0;//��ʱ0Ϊһ�㣬1Ϊ��������
	public int WeekNum;//���ڼ�
	public int WeekSpecialNum=0;//0�����֣�1������2˫
	public boolean isCollision = false;
	public boolean isOverlapping = false;
	
	public Course()
	{
		//TODO��ID;
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
		//TODO ��Ų��ɿգ���
		this.ID = ("ctgu_" + Integer.toString(year) + Integer.toString(term) + this.Title + this.ClassNum);
	}
	
}

