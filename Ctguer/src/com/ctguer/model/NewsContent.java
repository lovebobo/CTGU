package com.ctguer.model;

import java.io.Serializable;
import java.util.List;
/*����Ϊ����ģ����*/

public class NewsContent implements Serializable{
	String content;//���ű���
	String author;//����
	String time;//����ʱ��
	String href;//������ϸ����
	List<String> detail;//��ϸ����
	String attachmenturl;//��������
	String pv;//pvֵ���������
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
