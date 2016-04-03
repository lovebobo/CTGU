package com.ctguer.model;

import java.io.Serializable;

public class Book implements Serializable{

	
	

	
	public String BookName;//书名

	public String authorName;//作者名
	
	public String PublicPlace;//出版社
	
	public String bookUrl;//图书路径
	
	public String dutyName;//责任者名
	
	public String PublicTime;//出版时间
	
	public String BookId;//索取号
	
	public String AllNum;//总量
	
	public String UnUsedNum;//可借数量
	
	public String RelativeResouse;//可借数量
	
	
	//搜索书籍的构造函数
	public Book(String dutyName,String BookName,String bookUrl,String authorName,String PublicPlace,String PublicTime,String BookId,String AllNum,String UnUsedNum,String RelativeResouse)
	{
		//TODO　ID;
		this.dutyName=dutyName;
		this.BookName=BookName;
		this.authorName=authorName;
		this.PublicPlace=PublicPlace;
		this.PublicTime=PublicTime;
		this.BookId=BookId;
		this.AllNum=AllNum;
		this.UnUsedNum=UnUsedNum;
		this.RelativeResouse=RelativeResouse;
		this.bookUrl=bookUrl;
	}
}
