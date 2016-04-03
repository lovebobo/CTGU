package com.ctguer.model;

import java.io.Serializable;

//评教
public class EvaluateTeaching  implements Serializable
{
	public int Year;//学年
	public int Term;//学期
	public String courseNum;//课程编号
	public String CourseName;//课程名
	public String Teacher;//老师
	public boolean isEvaluated=false;//评教状态
	public String id;//评教所需id
	public int Value=0;//默认0分  服务器不计入 8小项共40

	
	
	
}
