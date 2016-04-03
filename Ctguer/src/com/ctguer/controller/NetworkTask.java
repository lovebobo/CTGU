package com.ctguer.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

public class NetworkTask
{	
	public static int ConnectCount=0;//同一连接 请求次数
	public static final int ConnectMax=2;//同一连接 最大请求次数
	public static int TIME_OUT_DELAY=15*1000;// 连接超时  
	public static int TIME_OUT_SOCKET=40*1000;// 超时设置  
	private static ExecutorService executorService;
	private static ExecutorService singleExecutorService;
	public static NetworkTask networkTask;
	
	public NetworkTask()
	{
		executorService = Executors.newCachedThreadPool();
		singleExecutorService = Executors.newSingleThreadExecutor();
	}
	
	public static HttpClient initHttp() {
		int ConnectionTimeout = TIME_OUT_DELAY;
		int SocketTimeout = TIME_OUT_SOCKET;
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters,ConnectionTimeout);
		HttpConnectionParams.setSoTimeout(httpParameters, SocketTimeout);
		return new DefaultHttpClient(httpParameters);
	}
	
	
	
	public void addHttpPostTask(String url, HashMap<String, String> map, AbsHttpTask task)
	{
		try
		{
			executorService.execute(new PostTask(url, map, task));
		}
		catch (Exception e)
		{
			executorService.shutdown();
			System.out.println("Network post erro"+e.getMessage());
		}
	}
	
	public void addHttpGetTask(String url, HashMap<String, String> map, AbsHttpTask task)
	{
		try
		{
			executorService.execute(new GetTask(url, map, task));
		}
		catch (Exception e)
		{
			executorService.shutdown();
			System.out.println("Network get erro"+e.getMessage());
		}
	}
	// GET http
		static class GetTask implements Runnable
		{
			private HttpClient Client;
			private HttpGet httpGet;
			private String content;
			private HttpResponse httpResponse;
			private String uriString;
			private AbsHttpTask absTask;

			
			public GetTask(String uri,HashMap<String, String> map, AbsHttpTask task)
			{
				this.uriString = uri;
				this.absTask = task;
			}
			
			@Override
			public void run()
			{
				Client = initHttp();		
				httpGet = new HttpGet(this.uriString);
				System.out.println(this.uriString);
				if (this.uriString.contains(URLs.CTGU_jw)||this.uriString.contains(URLs.CTGU_jw_2)){
					if(URLs.jwc_cookies != null && URLs.jwc_cookies.size() != 0)
						httpGet.setHeader("Cookie","ASP.NET_SessionId="+ URLs.jwc_cookies.get(URLs.jwc_cookies.size() - 1).getValue());
				}
				
				//图书馆登陆
				if(this.uriString.contains(URLs.CTGU_ts))
				{
					if(URLs.library_cookies != null&&URLs.library_cookies.size()!=0)
					//httpGet.setHeader("Cookie","ASP.NET_SessionId="+ URLs.library_cookies.get(URLs.library_cookies.size()-1).getValue());	
					httpGet.addHeader("Cookie", URLs.library_cookies1);
				}
				
				try
				{
					httpResponse = Client.execute(httpGet);
					if(httpResponse.getStatusLine().getStatusCode() == 200)
					{
						if (this.uriString.contains(URLs.CTGU_jw)||this.uriString.contains(URLs.CTGU_jw_2))
						{
							URLs.jwc_cookies.addAll(((AbstractHttpClient) Client).getCookieStore().getCookies());	
						}
						if (this.uriString.contains(URLs.CTGU_ts))
						{
							URLs.library_cookies.addAll(((AbstractHttpClient) Client).getCookieStore().getCookies());	
						}
						StringBuffer sb = new StringBuffer();  
		                HttpEntity entity = httpResponse.getEntity();  
		                InputStream is = entity.getContent();  
			            this.absTask.onComplete(is);
					}
					else 
					{
						this.absTask.onError(httpResponse.getStatusLine().getStatusCode());
					}
				} catch (Exception e)
				{
					this.absTask.onError(e);
					System.out.println("httpget failed"+e.getMessage());
				}
			}
		}
	
	// POST http
		static class PostTask implements Runnable
		{
			private HttpClient client;
			private HttpResponse httpResponse;
			
			private String uriString;
			private HashMap<String, String> paramMap;
			private AbsHttpTask absTask;

			private String whichyear;
			private String whichterm;
			
			public PostTask(String URI, HashMap<String, String> map, AbsHttpTask task)
			{
				this.uriString = URI;
				this.paramMap = map;
				this.absTask = task;
			}

			@Override
			public void run(){
				
				client = initHttp();		
				System.out.println(this.uriString);
				
				HttpPost httpRequest = new HttpPost(this.uriString);
				
				//如果是图书馆登陆则需要阻止自动重定向
				if(this.uriString.contains(URLs.login_Library)||this.uriString.contains(URLs.reborrow_book))
				{
					httpRequest.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS, false);  
				}
				
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				
				if (this.paramMap != null && this.paramMap.size() != 0)
				{
						Set entries = this.paramMap.entrySet();
						if(entries != null)
						{
							Iterator iterator = entries.iterator();
							while(iterator.hasNext())
							{
								Map.Entry entry = (Entry)iterator.next();
								try
								{
									String key = new String(entry.getKey().toString().getBytes(),"UTF-8");
									String value = new String(entry.getValue().toString().getBytes(),"UTF-8"); ;
									params.add(new BasicNameValuePair(key, value));
								} catch (Exception e)
								{
									System.out.println("utf8 failed");
								}
							}
					}
				}
				
				if (this.uriString.contains("http://sulcmis.lib.ctgu.edu.cn/")) {
				
				}
				
				//如果已经登陆取得cookies，则后期post只需要加在头上
				if(this.uriString.contains(URLs.CTGU_jw)||this.uriString.contains(URLs.CTGU_jw_2))
				{
					if(URLs.jwc_cookies != null && URLs.jwc_cookies.size() != 0)
					httpRequest.setHeader("Cookie", "ASP.NET_SessionId="
							+ URLs.jwc_cookies.get(URLs.jwc_cookies.size() - 1).getValue());
				}
				
				//图书馆相关的cookies
				if(this.uriString.contains(URLs.CTGU_ts))
				{
					if(URLs.library_cookies != null && URLs.library_cookies.size() != 0)
					{
						httpRequest.setHeader("Cookie", "ASP.NET_SessionId="
								+ URLs.library_cookies.get(URLs.library_cookies.size() - 1).getValue());
					}
				}
				
				//续借
				if(this.uriString.contains(URLs.reborrow_book))
				{
					if(URLs.library_cookies1 != null)
					{
						httpRequest.setHeader("Cookie",URLs.library_cookies1);
					}
				}
				
				try {
					if (params != null && params.size() != 0) {
						httpRequest.setEntity(new UrlEncodedFormEntity(params,
								HTTP.UTF_8));// HTTP.UTF_8
					}
					try {
						httpResponse = client.execute(httpRequest);
						//如果是图书馆登陆则需要重定向
						
						if(this.uriString.contains(URLs.CTGU_ts+"login.aspx"))
						{
							URLs.library_cookies1=(httpResponse.getFirstHeader("Set-Cookie").getValue()); 
							this.absTask.onComplete(null);						
							return;
						}
						
						if(this.uriString.contains(URLs.reborrow_book))
						{
							URLs.againborrow_book=URLs.CTGU_ts+(httpResponse.getFirstHeader("Location").getValue()); 
							this.absTask.onComplete(null);						
							return;
						}
						System.out.println("after execute");
					} catch (Exception e) {
						System.out.println("failed in execute " + e.getMessage());
						this.absTask.onError(e);
						return;
					}

					if (httpResponse.getStatusLine().getStatusCode() == 200) {	
						if (this.uriString.contains("http://sulcmis.lib.ctgu.edu.cn/"))
						{
							
						}
						else if(this.uriString.contains(URLs.CTGU_ts)){
							URLs.library_cookies.addAll(((AbstractHttpClient) client).getCookieStore().getCookies());	
						}
						
						else if(this.uriString.contains(URLs.CTGU_jw_2)||this.uriString.contains(URLs.CTGU_jw)){
							URLs.jwc_cookies.addAll(((AbstractHttpClient) client).getCookieStore().getCookies());							
						}
						
						else {
							
						}
						
						HttpEntity entity = httpResponse.getEntity();
						InputStream is = entity.getContent();
						this.absTask.onComplete(is);
					} else {
						this.absTask.onError(httpResponse.getStatusLine());
					}
				} catch (Exception e) {
					System.out.println("httpPost failed " + e.getMessage());
					// throw e;
				}

			}
		}
}
