package com.ctguer.controller;

import com.umeng.analytics.MobclickAgent;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public abstract class BackHandledFragment extends Fragment {

	/**
	 * ���м̳�BackHandledFragment�����඼�������������ʵ������Back�����º���߼�
	 * FragmentActivity��׽�������ؼ�����¼��������ѯ��Fragment�Ƿ����Ѹ��¼�
	 * ���û��Fragment��ϢʱFragmentActivity�Լ��Ż����Ѹ��¼�
	 */
	public abstract boolean onBackPressed();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		//����FragmentActivity����ǰFragment��ջ��

	}
	
	@Override
	public void onResume() {
	    super.onResume();
	    MobclickAgent.onPageStart("MainScreen"); //ͳ��ҳ��
	}
	@Override
	public void onPause() {
	    super.onPause();
	    MobclickAgent.onPageEnd("MainScreen"); //ͳ��ҳ��
	}
}