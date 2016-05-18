package com.ctguer.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.ctguer.model.Activity;
import com.ctguer.model.Comments;
import com.ctguer.model.NewsContent;
import com.ctguer.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tencent.open.utils.Util.Statistic;

import android.R.string;
import android.os.Handler;
import cn.smssdk.app.NewAppReceiver;

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
	
	
	//获取ajb新闻
	
	
	public static void getNewsList(final Handler handler) {
		taskPool.addHttpGetTask(URLs.getNewsList, null, new AbsHttpTask() {
			
			@Override
			public void onError(Object msg) {
				// TODO Auto-generated method stub
				Utility.sendMsg(handler, Codes.getNewListFail,msg);
			}
			
			@Override
			public void onError() {
				// TODO Auto-generated method stub
				Utility.sendMsg(handler, Codes.getNewListFail);
			}
			
			@Override
			public void onComplete(InputStream paramInputStream) {
				// TODO Auto-generated method stub
				String result = Utility.streamToString(paramInputStream);
				if (result == null || result.length() == 0)
					return;
				Object object = getNameFromJson(result, "state");
				
				if (object != null) {
					if ("success".equals(object.toString())){
						try {
			                result=new JSONObject(result)
			                        .get("data")
			                        .toString();
			            } catch (JSONException e) {
			                e.printStackTrace();
			            }
			            Gson gson = new GsonBuilder().create();
			            
			            ArrayList<NewsContent> obj = gson.fromJson(
			                    result, new TypeToken<ArrayList<NewsContent>>(){}.getType());
						Utility.sendMsg(handler, Codes.getNewsListSuc,obj);
					}
				

					else {
						Utility.sendMsg(handler, Codes.getNewListFail,
								getNameFromJson(result, "info"));
					}
				}
				else Utility.sendMsg(handler, Codes.registerFail);
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
	
	//获取活动
	public static void getActivityList(final Handler handler) {
		taskPool.addHttpGetTask(URLs.getactivity, null, new AbsHttpTask() {
			
			@Override
			public void onError(Object msg) {
				// TODO Auto-generated method stub
				Utility.sendMsg(handler, Codes.getActivityListFail,msg);
			}
			
			@Override
			public void onError() {
				// TODO Auto-generated method stub
				Utility.sendMsg(handler, Codes.getActivityListFail);
			}
			
			@Override
			public void onComplete(InputStream paramInputStream) {
				// TODO Auto-generated method stub
				String result = Utility.streamToString(paramInputStream);
				Object object = getNameFromJson(result, "state");
				if(object!=null)
				{
					if("success".equals(object))
					{
						try {
			                result=new JSONObject(result)
			                        .get("data")
			                        .toString();
			            } catch (JSONException e) {
			                e.printStackTrace();
			            }
			            Gson gson = new GsonBuilder().create();
			            
			            ArrayList<Activity> obj = gson.fromJson(
			                    result, new TypeToken<ArrayList<Activity>>(){}.getType());
			            
			            Utility.sendMsg(handler, Codes.getActivityListSuc,obj);
					}
					
					else {
						Utility.sendMsg(handler, Codes.getActivityListFail);
					}
				}
				 

			}
		});
	}
	
	
	//用户注销
		public static void launchActivity(final Handler handler,int Id
				,int limit_count,String content,String title,String place,
				String datatime,String launchtime
				
				) {
			HashMap<String, String> localHashMap = new HashMap<String, String>();
			localHashMap.put(Utility.toUtf8("Id"), Utility.toUtf8(Id+""));
			localHashMap.put(Utility.toUtf8("limit_count"), Utility.toUtf8(limit_count+""));
			localHashMap.put(Utility.toUtf8("content"), Utility.toUtf8(content));
			localHashMap.put(Utility.toUtf8("title"), Utility.toUtf8(title));
			localHashMap.put(Utility.toUtf8("place"), Utility.toUtf8(place));
			localHashMap.put(Utility.toUtf8("datatime"), Utility.toUtf8(datatime));
			localHashMap.put(Utility.toUtf8("launchtime"), Utility.toUtf8(launchtime));
			
			taskPool.addHttpPostTask(URLs.launchactivity, localHashMap, new AbsHttpTask() {
				
				@Override
				public void onError(Object msg) {
					// TODO Auto-generated method stub
					Utility.sendMsg(handler, Codes.launchActivityListFail,msg);
				}
				
				@Override
				public void onError() {
					// TODO Auto-generated method stub
					Utility.sendMsg(handler, Codes.launchActivityListFail);
				}
				
				@Override
				public void onComplete(InputStream paramInputStream) {
					// TODO Auto-generated method stub
					String result = Utility.streamToString(paramInputStream);
					if (result == null || result.length() == 0)
						return;
					Object object = getNameFromJson(result, "status");
					if (object != null) {
						if ("1".equals(object.toString())){
							
							Utility.sendMsg(handler, Codes.launchActivityListSuc);
						}
					
						else {
							Utility.sendMsg(handler, Codes.launchActivityListFail,
									getNameFromJson(result, "info"));
						}
					}
					else Utility.sendMsg(handler, Codes.launchActivityListFail);
				}
			});
			
		}
		
	//活动点赞
	public static void launchPraise(final Handler handler,int Id,int activity_id) {
		HashMap<String, String> localHashMap=new HashMap<>();
		localHashMap.put(Utility.toUtf8("Id"), Utility.toUtf8(Id+""));
		localHashMap.put(Utility.toUtf8("activity_id"), Utility.toUtf8(activity_id+""));
		
		taskPool.addHttpPostTask(URLs.launchPraise, localHashMap, new AbsHttpTask() {
			
			@Override
			public void onError(Object msg) {
				// TODO Auto-generated method stub
				Utility.sendMsg(handler, Codes.launchPraiseFail);
			}
			
			@Override
			public void onError() {
				// TODO Auto-generated method stub
				Utility.sendMsg(handler, Codes.launchPraiseFail);
			}
			
			@Override
			public void onComplete(InputStream paramInputStream) {
				// TODO Auto-generated method stub
				
				String result = Utility.streamToString(paramInputStream);
				if (result == null || result.length() == 0)
					return;
				Object object = getNameFromJson(result, "status");
				if (object != null) {
					if ("1".equals(object.toString())){
						
						Utility.sendMsg(handler, Codes.launchPraiseSuc);
					}
				
					else {
						Utility.sendMsg(handler, Codes.launchPraiseFail,
								getNameFromJson(result, "info"));
					}
				}
				else Utility.sendMsg(handler, Codes.launchPraiseFail);
			
			}
		});
	}	
		
		
	//活动报名
	public static void signActivity(final Handler handler,int Id,int activity_id) {
		HashMap<String, String> localHashMap=new HashMap<>();
		localHashMap.put(Utility.toUtf8("Id"), Utility.toUtf8(Id+""));
		localHashMap.put(Utility.toUtf8("activity_id"), Utility.toUtf8(activity_id+""));
		
		taskPool.addHttpPostTask(URLs.signActivity, localHashMap, new AbsHttpTask() {
			
			@Override
			public void onError(Object msg) {
				// TODO Auto-generated method stub
				Utility.sendMsg(handler, Codes.signActivityFail);
			}
			
			@Override
			public void onError() {
				// TODO Auto-generated method stub
				Utility.sendMsg(handler, Codes.signActivityFail);
			}
			
			@Override
			public void onComplete(InputStream paramInputStream) {
				// TODO Auto-generated method stub
				
				String result = Utility.streamToString(paramInputStream);
				if (result == null || result.length() == 0)
					return;
				Object object = getNameFromJson(result, "status");
				if (object != null) {
					if ("1".equals(object.toString())){
						
						Utility.sendMsg(handler, Codes.signActivitySuc);
					}
				
					else {
						Utility.sendMsg(handler, Codes.signActivityFail,
								getNameFromJson(result, "info"));
					}
				}
				else Utility.sendMsg(handler, Codes.signActivityFail);
			
			}
		});
	}	
	
	//发表评论
	public static void launchComments(final Handler handler,int Id,int activity_id, String comment_time,String comment_content  ) {
		HashMap<String, String> localHashMap=new HashMap<>();
		localHashMap.put(Utility.toUtf8("Id"), Utility.toUtf8(Id+""));
		localHashMap.put(Utility.toUtf8("activity_id"), Utility.toUtf8(activity_id+""));
		localHashMap.put(Utility.toUtf8("comment_content"), Utility.toUtf8(comment_content));
		localHashMap.put(Utility.toUtf8("comment_time"), Utility.toUtf8(comment_time));
		
		taskPool.addHttpPostTask(URLs.launchComments, localHashMap, new AbsHttpTask() {
			
			@Override
			public void onError(Object msg) {
				// TODO Auto-generated method stub
				 Utility.sendMsg(handler, Codes.launchCommentFail);
			}
			
			@Override
			public void onError() {
				// TODO Auto-generated method stub
				 Utility.sendMsg(handler, Codes.launchCommentFail);
			}
			
			@Override
			public void onComplete(InputStream paramInputStream) {
				// TODO Auto-generated method stub
				String result = Utility.streamToString(paramInputStream);
				if (result == null || result.length() == 0)
					return;
				Object object = getNameFromJson(result, "status");
				if (object != null) {
					if ("1".equals(object.toString())){
						
						Utility.sendMsg(handler, Codes.launchCommentSuc);
					}
				
					else {
						Utility.sendMsg(handler, Codes.launchCommentFail,
								getNameFromJson(result, "info"));
					}
				}
				else Utility.sendMsg(handler, Codes.launchCommentFail);
			
			}
		});
	}
		
		
	//获取评论列表
	public static void getCommentList(final Handler handler	,int activity_id) {
		
		HashMap<String, String> localHashMap = new HashMap<String, String>();
		localHashMap.put(Utility.toUtf8("activity_id"), Utility.toUtf8(activity_id+""));
		
		taskPool.addHttpPostTask(URLs.getCommentsList, localHashMap, new AbsHttpTask() {
			
			@Override
			public void onError(Object msg) {
				// TODO Auto-generated method stub
				Utility.sendMsg(handler, Codes.getCommentListFail,msg);
			}
			
			@Override
			public void onError() {
				// TODO Auto-generated method stub
				Utility.sendMsg(handler, Codes.getCommentListFail);
			}
			
			@Override
			public void onComplete(InputStream paramInputStream) {
				// TODO Auto-generated method stub
				String result = Utility.streamToString(paramInputStream);
				if (result == null || result.length() == 0)
					return;
				Object object = getNameFromJson(result, "status");
				
				if (object != null) {
					if ("1".equals(object.toString())){
						try {
			                result=new JSONObject(result)
			                        .get("data")
			                        .toString();
			            } catch (JSONException e) {
			                e.printStackTrace();
			            }
			            Gson gson = new GsonBuilder().create();
			            
			            ArrayList<Comments> obj = gson.fromJson(
			                    result, new TypeToken<ArrayList<Comments>>(){}.getType());
						Utility.sendMsg(handler, Codes.getCommentListSuc,obj);
					}
				
					else {
						Utility.sendMsg(handler, Codes.getCommentListFail1,
								getNameFromJson(result, "暂无评论"));
					}
				}
				else Utility.sendMsg(handler, Codes.getCommentListFail);
			
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
