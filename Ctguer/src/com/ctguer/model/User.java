package com.ctguer.model;

import java.io.Serializable;

public class User implements Serializable{
	public Boolean statusBoolean=true;//falseδ��¼ , true��¼ 
	
	public Boolean loginstatusBoolean=true;//falseδ��¼ , true��¼ 
	
	private String username;//�û���
	
	private String password;//�û���¼����
	
	private String iflogin;
	
	private String Id;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getLoginstatusBoolean() {
		return loginstatusBoolean;
	}

	public void setLoginstatusBoolean(Boolean loginstatusBoolean) {
		this.loginstatusBoolean = loginstatusBoolean;
	}

	public Boolean getStatusBoolean() {
		return statusBoolean;
	}

	public void setStatusBoolean(Boolean statusBoolean) {
		this.statusBoolean = statusBoolean;
	}
	
}
