package com.ctguer.model;

import java.io.Serializable;

public class Score implements Serializable{

	public int Year;//ѧ��
	public int Term;//ѧ��
	public String CourseName;//�γ���
	public Float Credit;//�γ�ѧ��
	public String Type;//��������
	public String _Score;//����
	public Float GPA;//���񼨵� // ����ѧ��
	public String StuID;//ѧ��
	public boolean isFail;//�Ƿ�ҿ�
	
	public Score()
	{
		
	}
	
	public static void getYearTermScore(int year, int term)
	{
		//����management.scores
	}
}
