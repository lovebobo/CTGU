package com.ctguer.model;

import java.io.Serializable;

public class BorrowBook implements Serializable{

	public String status;//状态 是否可以续借
	
	public String LastTime;//最晚归还期
	
	public String BookName;//书名
	
	public String juanqi;//卷期
	
	public String bookType;//图书类型
	
	public String loginId;//登陆号
	
	public String borrowTime;//借书的时间

	public String bookUrl;//图书路径
	


	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public String getLastTime() {
		return LastTime;
	}



	public void setLastTime(String lastTime) {
		LastTime = lastTime;
	}



	public String getBookName() {
		return BookName;
	}



	public void setBookName(String bookName) {
		BookName = bookName;
	}



	public String getJuanqi() {
		return juanqi;
	}



	public void setJuanqi(String juanqi) {
		this.juanqi = juanqi;
	}



	public String getBookType() {
		return bookType;
	}



	public void setBookType(String bookType) {
		this.bookType = bookType;
	}



	public String getLoginId() {
		return loginId;
	}



	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}



	public String getBorrowTime() {
		return borrowTime;
	}



	public void setBorrowTime(String borrowTime) {
		this.borrowTime = borrowTime;
	}



	public String getBookUrl() {
		return bookUrl;
	}



	public void setBookUrl(String bookUrl) {
		this.bookUrl = bookUrl;
	}



	//自己借书信息构造函数
	public BorrowBook(String status,String LastTime,String bookUrl,String BookName  ,String juanqi,String bookType,String loginId,String borrowTime)
	{
		//TODO　ID;
		this.status=status;
		this.LastTime=LastTime;
		this.bookUrl=bookUrl;
		this.BookName=BookName;
		this.juanqi=juanqi;
		this.bookType=bookType;
		this.loginId=loginId;
		this.borrowTime=borrowTime;
	}
}
