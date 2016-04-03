package com.ctguer.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.ctguer.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import android.R.string;
import android.os.Handler;

public class RelativeUser {
	private static NetworkTask taskPool = new NetworkTask();
	
	
	//用户注册
	public static void handleRegieter(final Handler handler ,String usename,String password) {
		HashMap<String, String> localHashMap = new HashMap<String, String>();
		localHashMap.put(Utility.toUtf8("username"), Utility.toUtf8(usename));
		localHashMap.put(Utility.toUtf8("password"), Utility.toUtf8(password));
		
		
		taskPool.addHttpPostTask(URLs.userredister, localHashMap, new AbsHttpTask() {
			
			@Override
			public void onComplete(InputStream paramInputStream) {
				// TODO Auto-generated method stub
				String result = Utility.streamToString(paramInputStream);
				if (result == null || result.length() == 0)
					return;
				Object object = getNameFromJson(result, "state");
				
				if (object != null) {
					if ("success".equals(object.toString()))
						Utility.sendMsg(handler, Codes.registerSuc);

					else {
						Utility.sendMsg(handler, Codes.registerFail,
								getNameFromJson(result, "info"));
					}
				}
				else Utility.sendMsg(handler, Codes.registerFail);
				
			}
			
			@Override
			public void onError(Object msg) {
				// TODO Auto-generated method stub
				 Utility.sendMsg(handler, Codes.registerFail,msg);
			}
			
			@Override
			public void onError() {
				// TODO Auto-generated method stub
				 Utility.sendMsg(handler, Codes.registerFail);
			}
			
			
		});
	}
	
	
	//用户登录
	public static void handleLogin(final Handler handler,String usename,String password) {
		HashMap<String, String> localHashMap = new HashMap<String, String>();
		localHashMap.put(Utility.toUtf8("username"), Utility.toUtf8(usename));
		localHashMap.put(Utility.toUtf8("password"), Utility.toUtf8(password));
		
		taskPool.addHttpPostTask(URLs.userlogin, localHashMap, new AbsHttpTask() {
			
			@Override
			public void onError(Object msg) {
				// TODO Auto-generated method stub
				Utility.sendMsg(handler, Codes.login_app_Fail,msg);
			}
			
			@Override
			public void onError() {
				// TODO Auto-generated method stub
				Utility.sendMsg(handler, Codes.login_app_Fail);
			}
			
			@Override
			public void onComplete(InputStream paramInputStream) {
				// TODO Auto-generated method stub
				String result = Utility.streamToString(paramInputStream);
				if (result == null || result.length() == 0)
					return;
				Object object = getNameFromJson(result, "message");
				String result1 = null;
				if (object != null) {
					if ("success".equals(object.toString())){
						 try {
				                result1=new JSONObject(result)
				                        .get("data")
				                        .toString();
				            } catch (JSONException e) {
				                e.printStackTrace();
				            }
				            
						Utility.sendMsg(handler, Codes.login_app_Suc,result1);
					}
				

					else {
						Utility.sendMsg(handler, Codes.login_app_Fail,
								getNameFromJson(result, "info"));
					}
				}
				else Utility.sendMsg(handler, Codes.login_app_Fail);
			}
		});
		
	}
	
	
	//用户注销
	public static void handleLogonout(final Handler handler,String usename,String password) {
		HashMap<String, String> localHashMap = new HashMap<String, String>();
		localHashMap.put(Utility.toUtf8("username"), Utility.toUtf8(usename));
		localHashMap.put(Utility.toUtf8("password"), Utility.toUtf8(password));
		
		taskPool.addHttpPostTask(URLs.userlogonout, localHashMap, new AbsHttpTask() {
			
			@Override
			public void onError(Object msg) {
				// TODO Auto-generated method stub
				Utility.sendMsg(handler, Codes.login_app_Fail,msg);
			}
			
			@Override
			public void onError() {
				// TODO Auto-generated method stub
				Utility.sendMsg(handler, Codes.login_app_Fail);
			}
			
			@Override
			public void onComplete(InputStream paramInputStream) {
				// TODO Auto-generated method stub
				String result = Utility.streamToString(paramInputStream);
				if (result == null || result.length() == 0)
					return;
				Object object = getNameFromJson(result, "message");
				String result1 = null;
				if (object != null) {
					if ("success".equals(object.toString())){
						
						Utility.sendMsg(handler, Codes.logout_app_Suc,result1);
					}
				
					else {
						Utility.sendMsg(handler, Codes.logout_app_Fail,
								getNameFromJson(result, "info"));
					}
				}
				else Utility.sendMsg(handler, Codes.logout_app_Fail);
			}
		});
		
	}
	
	//String  to json
	public static Object getNameFromJson(String result, String name)
	{
		try
		{
	   		JSONObject jsonObject = new JSONObject(result);
		   	if(jsonObject != null)
	   			return jsonObject.optString(name);
		} catch (Exception e)
		{
		}
		return null;
	}
}
