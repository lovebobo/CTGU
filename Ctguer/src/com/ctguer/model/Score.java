package com.ctguer.model;

import java.io.Serializable;

public class Score implements Serializable{

	public int Year;//学年
	public int Term;//学期
	public String CourseName;//课程名
	public Float Credit;//课程学分
	public String Type;//考试类型
	public String _Score;//分数
	public Float GPA;//所获绩点 // 所获学分
	public String StuID;//学号
	public boolean isFail;//是否挂科
	
	public Score()
	{
		
	}
	
	public static void getYearTermScore(int year, int term)
	{
		//处理management.scores
	}
}
