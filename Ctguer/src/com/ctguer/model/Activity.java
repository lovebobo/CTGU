package com.ctguer.model;

import java.io.Serializable;

public class Activity implements Serializable{
	private int activity_id;
	
	private int limit_count;
	
	private String content;
	
	private String title	;
	
	private String url	;
	
	private int praise_count;
	
	private int comment_count;
	
	private int sign_count;
	
	private String place;
	
	private String datatime;
	
	private String launchtime;
	
	public String getLaunchtime() {
		return launchtime;
	}

	public void setLaunchtime(String launchtime) {
		this.launchtime = launchtime;
	}

	public Activity() {
		// TODO Auto-generated constructor stub
	}

	public Activity(int activity_id,int limit_count,String content,String title,int praise_count,int comment_count,int sign_count,String place,String datatime)
	{
		this.activity_id=activity_id;
		this.limit_count=limit_count;
		this.content=content;
		this.title=title;
		this.praise_count=praise_count;
		this.comment_count=comment_count;
		this.sign_count=sign_count;
		this.place=place;
		this.datatime=datatime;
		 
	}
	
	public int getActivity_id() {
		return activity_id;
	}

	public void setActivity_id(int activity_id) {
		this.activity_id = activity_id;
	}

	public int getLimit_count() {
		return limit_count;
	}

	public void setLimit_count(int limit_count) {
		this.limit_count = limit_count;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getPraise_count() {
		return praise_count;
	}

	public void setPraise_count(int praise_count) {
		this.praise_count = praise_count;
	}

	public int getComment_count() {
		return comment_count;
	}

	public void setComment_count(int comment_count) {
		this.comment_count = comment_count;
	}

	public int getSign_count() {
		return sign_count;
	}

	public void setSign_count(int sign_count) {
		this.sign_count = sign_count;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getDatatime() {
		return datatime;
	}

	public void setDatatime(String datatime) {
		this.datatime = datatime;
	}
	
	
	
}
