package com.ctguer.model;

import java.io.Serializable;

public class User implements Serializable{
	public Boolean statusBoolean=true;//falseÎ´µÇÂ¼ , trueµÇÂ¼ 
	
	public Boolean loginstatusBoolean=true;//falseÎ´µÇÂ¼ , trueµÇÂ¼ 
	
	private String username;//ÓÃ»§Ãû
	
	private String password;//ÓÃ»§µÇÂ¼ÃÜÂë
	
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
