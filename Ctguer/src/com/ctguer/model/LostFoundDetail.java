package com.ctguer.model;

import java.io.Serializable;

public class LostFoundDetail implements Serializable{
	
private String url;//url
private String name;//����
private String detail;//����
private String time;//ʱ��
private String address;//��ַ
private String tele;//�绰
private String qq;//QQ
private String posttime;//����ʱ��
private String postName;//����������
private String type;//type=1����ʧ�� type=2��������
private String other;





public String getOther() {
	return other;
}
public void setOther(String other) {
	this.other = other;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public String getPostName() {
	return postName;
}
public void setPostName(String postName) {
	this.postName = postName;
}
public String getUrl() {
	return url;
}
public void setUrl(String url) {
	this.url = url;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getDetail() {
	return detail;
}
public void setDetail(String detail) {
	this.detail = detail;
}
public String getTime() {
	return time;
}
public void setTime(String time) {
	this.time = time;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getTele() {
	return tele;
}
public void setTele(String tele) {
	this.tele = tele;
}
public String getQq() {
	return qq;
}
public void setQq(String qq) {
	this.qq = qq;
}
public String getPosttime() {
	return posttime;
}
public void setPosttime(String posttime) {
	this.posttime = posttime;
}



}
