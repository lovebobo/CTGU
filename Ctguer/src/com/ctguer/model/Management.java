package com.ctguer.model;

public class Management {
	public static User curUser;//������ǰ�û�����

	
    static {
		if(curUser==null)
		{
			curUser = new User();
		}
	}
}
