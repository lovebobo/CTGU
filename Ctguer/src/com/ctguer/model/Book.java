package com.ctguer.model;

import java.io.Serializable;

public class Book implements Serializable{

	
	

	
	public String BookName;//����

	public String authorName;//������
	
	public String PublicPlace;//������
	
	public String bookUrl;//ͼ��·��
	
	public String dutyName;//��������
	
	public String PublicTime;//����ʱ��
	
	public String BookId;//��ȡ��
	
	public String AllNum;//����
	
	public String UnUsedNum;//�ɽ�����
	
	public String RelativeResouse;//�ɽ�����
	
	
	//�����鼮�Ĺ��캯��
	public Book(String dutyName,String BookName,String bookUrl,String authorName,String PublicPlace,String PublicTime,String BookId,String AllNum,String UnUsedNum,String RelativeResouse)
	{
		//TODO��ID;
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
