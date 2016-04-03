package com.ctguer.model;

public class Management {
	public static User curUser;//创建当前用户对象

	
    static {
		if(curUser==null)
		{
			curUser = new User();
		}
	}
}
