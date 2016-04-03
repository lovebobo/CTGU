package com.ctguer.model;

import java.io.Serializable;
import java.util.List;
/*本类为新闻模板类*/

public class NewsContent implements Serializable{
	String content;//新闻标题
	String author;//作者
	String time;//新闻时间
	String href;//新闻详细链接
	List<String> detail;//详细新闻
	String attachmenturl;//附件链接
	String pv;//pv值，即浏览量
	public String getPv() {
		return pv;
	}
	public void setPv(String pv) {
		this.pv = pv;
	}
	public String getAttachmenturl() {
		return attachmenturl;
	}
	public void setAttachmenturl(String attachmenturl) {
		this.attachmenturl = attachmenturl;
	}
	public List<String> getDetail() {
		return detail;
	}
	public void setDetail(List<String> detail) {
		this.detail = detail;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	

}
