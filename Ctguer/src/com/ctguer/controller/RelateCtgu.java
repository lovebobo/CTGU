package com.ctguer.controller;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import com.ctguer.model.Book;
import com.ctguer.model.BookDetail;

import java.lang.ref.WeakReference;

import com.ctguer.model.BorrowBook;
import com.ctguer.model.Course;
import com.ctguer.model.EvaluateTeaching;
import com.ctguer.model.Jiajiao;
import com.ctguer.model.LostFoundDetail;
import com.ctguer.model.NewsContent;
import com.ctguer.model.PartTimeJob;
import com.ctguer.model.Score;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


public class RelateCtgu {

	private final static String LOGTAG = "RelateCtgu";
	private static NetworkTask taskPool = new NetworkTask();
	
	// 登陆教务
		public static void loginJwc_Ctgu(final Handler handler, String code,
				String username, String passwd,final int next) 
		{
			final Message msg=handler.obtainMessage();
			//final Message msg[] = new Message[2];
		HashMap<String, String> localHashMap = new HashMap<String, String>();

			localHashMap
					.put(Utility.toUtf8("__EVENTVALIDATION"),
							Utility.toUtf8(URLs.getURLValue(1,2)));
			localHashMap
					.put(Utility.toUtf8("__VIEWSTATE"),
							Utility.toUtf8(URLs.getURLValue(1,1)));
			localHashMap.put(Utility.toUtf8("btnLogin.x"), Utility.toUtf8("31"));
			localHashMap.put(Utility.toUtf8("btnLogin.y"), Utility.toUtf8("19"));
		
			localHashMap.put(Utility.toUtf8("txtUserName"),
					Utility.toUtf8(username));// 2011134114 2011112356
			localHashMap.put(Utility.toUtf8("txtPassword"), Utility.toUtf8(passwd));// 112929
																					// 171759
			localHashMap.put(Utility.toUtf8("CheckCode"),
					Utility.toUtf8(code.trim()));
			
			taskPool.addHttpPostTask(URLs.getURL(URLs.login_CTGU), localHashMap,
					new AbsHttpTask() {
						@Override
						public void onComplete(InputStream paramInputStream) {
							String result = Utility.streamToString(paramInputStream);
							if (result == null || result.length() == 0)
								return;

							String Rlogin = UtilityCtgu.isRepeatLogin(result);// true为返回了某个msg，即有错误返回
							if (Rlogin != "")
							{
								Utility.sendMsg(handler,Codes.returnmsg,Rlogin);
								return;
							}
							
							//通过抓取Html网页内容，查找id为“ctl00_lblSignIn”为教务处个人信息，来判断是否登陆成功
							
							String name=UtilityCtgu.pickName(result);					
							
							if(next>0){
									
								if(name==null) {//以取得名字 作为登陆成功标志
									onError();
									return;
								}
								Utility.sendMsg(handler,Codes.loginJwc, next);	
								}
							
							else {
								logout();
								Utility.sendMsg(handler,Codes.SpecifyName, name);								
								}
						}

						@Override
						public void onError(Object msg)
						{
							logout();
							Utility.sendMsg(handler,Codes.loginJwcErr);
						}

						@Override
						public void onError() 
						{
							logout();
							Utility.sendMsg(handler,Codes.loginJwcErr);
						}
					});


		}
		
		//图书详情
		public static void booklibrary_detail(final Context act,final Handler handler, String url)
		{
			taskPool.addHttpGetTask(URLs.search_Librarybook_detail+url, null,
					new AbsHttpTask() {
						@Override
						public void onComplete(InputStream paramInputStream) {
							String result = Utility.streamToString(paramInputStream);
							ArrayList<BookDetail> bookDetails=new ArrayList<BookDetail>();
							if (result == null || result.length() == 0)
								return;
							else{
								bookDetails=ToGetbookDetailWithStream(act,handler,result);
								Utility.sendMsg(handler, Codes.getBookDetailSuc,bookDetails);
							}
						}

						@Override
						public void onError(Object msg)
						{
							Utility.sendMsg(handler, Codes.getBookFail,msg);	
						}

						@Override
						public void onError() 
						{
							Utility.sendMsg(handler, Codes.getBookFail);
							
						}
					});
		}
		

		//处理图书详情信息
		protected static ArrayList<BookDetail> ToGetbookDetailWithStream(Context act,
				Handler handler, String result) {
			// TODO Auto-generated method stub
			String place = null, status = null, indexNumber = null;
			ArrayList<BookDetail> books = new ArrayList<BookDetail>();
			int j = 0;
			Document docc = null;
			docc = Jsoup.parse(result, "UTF-8");
			Elements linkss = docc.select("tr > td");

			for (int i = 6; i < linkss.size(); i++) {
				Element l = linkss.get(i);
				switch (j) {
				case 1:
					place = l.text();
					break;
				case 2:
					indexNumber = l.text();
					break;
				case 6:
					status = l.text();
					books.add(new BookDetail(place, status, indexNumber));
					break;
				}

				j++;
				if (j > 7) {
					j = 1;
				}

			}
			return books;
		}
		
		//获取图书馆验证码
		public static void get_Library_VerCode(final Context act,final Handler handler)
		{
			taskPool.addHttpGetTask(URLs.ver_Code_Library, null, new AbsHttpTask() {
				
				@Override
				public void onError(Object msg) {
					// TODO Auto-generated method stub
					Utility.sendMsg(handler, Codes.getVerCodeFail);
				}
				
				@Override
				public void onError() {
					// TODO Auto-generated method stub
					Utility.sendMsg(handler, Codes.getVerCodeFail);
				}
				
				@Override
				public void onComplete(InputStream paramInputStream) {
					// TODO Auto-generated method stub			
					Bitmap localBitmap = BitmapFactory.decodeStream(paramInputStream);
					Utility.sendMsg(handler, Codes.getVerCodeSuc,localBitmap);	
				}
			});
		}
		
		//获取借书的信息
		public static void getLibragy_BorrowBook(final Context act,final Handler handler)
		{
			taskPool.addHttpGetTask(URLs.borrow_book, null, new AbsHttpTask() {
				
				@Override
				public void onError(Object msg) {
					// TODO Auto-generated method stub
					Utility.sendMsg(handler,Codes.getLibraryBookFail);
				}
				
				@Override
				public void onError() {
					// TODO Auto-generated method stub
					Utility.sendMsg(handler,Codes.getLibraryBookFail);
				}
				
				@Override
				public void onComplete(InputStream paramInputStream) {
					// TODO Auto-generated method stub
					String result = Utility.streamToString(paramInputStream);
					if (result == null || result.length() == 0)
						return;	
					Document doc = Jsoup.parse(result);
					Elements table = doc
							.getElementsByClass("tb");
					table=null;
					Utility.sendMsg(handler,Codes.getLibraryBookSuc);
					
				}
			});
		}
		
		//登陆图书馆
		public static void loginLibragy_BorrowBook(final Context act,final Handler handler, String username,String password,String code)
		{
			HashMap<String, String> localHashMap = new HashMap<String, String>();
			
			localHashMap.put(Utility.toUtf8("__EVENTARGUMENT"),
					Utility.toUtf8(""));
			localHashMap.put(Utility.toUtf8("__EVENTTARGET"),
					Utility.toUtf8(""));
			
			localHashMap.put(Utility.toUtf8("__EVENTVALIDATION"),
					Utility.toUtf8("/wEWBgK8j52iDQKOmK5RApX9wcYGAsP9wL8JApLM7boDAqW86pcIv2Xp1HPv+chf8iF1ODsxm2XoRF4="));
			localHashMap.put(Utility.toUtf8("__VIEWSTATE"),
					Utility.toUtf8("/wEPDwUKMTI3MjA1NDY3Mg9kFgJmD2QWDGYPZBYCAgEPFgIeBGhyZWYFDWNzcy9zdHlsZS5jc3NkAgEPDxYCHghJbWFnZVVybAUaflxpbWFnZXNcaGVhZGVyb3BhY3FnMS5naWZkZAICDw8WAh4EVGV4dAUh5LiJ5bOh5aSn5a2m5Zu+5Lmm6aaG5Lmm55uu5qOA57SiZGQCAw8PFgIfAgUbMjAxNeW5tDAz5pyIMTHml6Ug5pif5pyf5LiJZGQCBA9kFgRmD2QWBAIBDxYCHgtfIUl0ZW1Db3VudAIIFhICAQ9kFgJmDxUDC3NlYXJjaC5hc3B4AAznm67lvZXmo4DntKJkAgIPZBYCZg8VAxNwZXJpX25hdl9jbGFzcy5hc3B4AAzliIbnsbvlr7zoiKpkAgMPZBYCZg8VAw5ib29rX3JhbmsuYXNweAAM6K+75Lmm5oyH5byVZAIED2QWAmYPFQMJeHN0Yi5hc3B4AAzmlrDkuabpgJrmiqVkAgUPZBYCZg8VAxRyZWFkZXJyZWNvbW1lbmQuYXNweAAM6K+76ICF6I2Q6LStZAIGD2QWAmYPFQMTb3ZlcmR1ZWJvb2tzX2YuYXNweAAM5o+Q6YaS5pyN5YqhZAIHD2QWAmYPFQMSdXNlci91c2VyaW5mby5hc3B4AA/miJHnmoTlm77kuabppoZkAggPZBYCZg8VAwtyZWFkbWUuaHRtbAAM5L2/55So5pWZ56iLZAIJD2QWAgIBDxYCHgdWaXNpYmxlaGQCAw8WAh8DZmQCAQ9kFgQCAQ9kFghmDw9kFgIeDGF1dG9jb21wbGV0ZQUDb2ZmZAIBDw8WAh4MRXJyb3JNZXNzYWdlBQ/or7fovpPlhaXor4Hlj7dkZAIDDw8WAh8GBQ/or7fovpPlhaXlr4bnoIFkZAIFDw8WAh8CZWRkAgIPZBYKZg8QZGQWAWZkAgEPEGRkFgFmZAICDw9kFgIfBQUDb2ZmZAIDDw8WAh8GBQ/or7fovpPlhaXor4Hlj7dkZAIFD2QWAgICDw8WAh8GBQ/or7fovpPlhaXlr4bnoIFkZAIFDw8WAh8CBY8C54m55Yir5biu5Yqp77yaDQrkuIDkvZPljJbmo4DntKLmnLrovpPlhaXms5XliIfmjaLmraXpqqQ8YnI+DQrnrKzkuIDmraXvvJrlsIblhYnmoIfnva7kuo7nlYzpnaLlhoXnmoTmlofmnKzmoYblhoXjgIINCuesrOS6jOatpe+8muaMieKAnGN0cmwgK+epuuagvOKAneaJk+W8gOi+k+WFpeazleOAgg0K56ys5LiJ5q2l77ya5oyJIOKAnCBjdHJsICsgc2hpZnTigJwg5YiH5o2i6L6T5YWl5rOV44CCDQrkuK3oi7HmlofovpPlhaXms5XovazmjaLor7fmjInigJxzaGlmdOKAneOAgmRkZH3zOGB4wY6X34hJ/wRor1KbctuK"));
			
			localHashMap.put(Utility.toUtf8("ctl00$ContentPlaceHolder1$btnLogin_Lib"),
					Utility.toUtf8("登录"));
			localHashMap.put(Utility.toUtf8("ctl00$ContentPlaceHolder1$txtlogintype"),
					Utility.toUtf8("0"));
			localHashMap.put(Utility.toUtf8("ctl00$ContentPlaceHolder1$txtUsername_Lib"),
					Utility.toUtf8(username));
			localHashMap.put(Utility.toUtf8("ctl00$ContentPlaceHolder1$txtPas_Lib"), Utility.toUtf8(password));
																					
			localHashMap.put(Utility.toUtf8("ctl00$ContentPlaceHolder1$txtCode"),
					Utility.toUtf8(code.trim()));
			
			taskPool.addHttpPostTask(URLs.login_Library, localHashMap,
					new AbsHttpTask() {
						@Override
						public void onComplete(InputStream paramInputStream) {
							//此处重定向了
							Utility.sendMsg(handler,Codes.loginLibrarySuc);							
						}

						@Override
						public void onError(Object msg)
						{
							Utility.sendMsg(handler,Codes.loginLibraryFail,msg);
						}

						@Override
						public void onError() 
						{
							Utility.sendMsg(handler,Codes.loginJwcErr);
						}
					});

		}		

		//获取借换图书详情
		public static void getInformation(final Handler handler) {
			// TODO Auto-generated method stub
			final List<BorrowBook> BorrowBookList = new ArrayList<BorrowBook>();
			taskPool.addHttpGetTask(URLs.borrow_book, null, new AbsHttpTask() {
	
				@Override
				public void onComplete(InputStream paramInputStream) {
					// TODO Auto-generated method stub
						String result = Utility.streamToString(paramInputStream);	

						Document doc = Jsoup.parse(result);
						
						Element table = doc.select("tbody").first();
						
						Elements course = table.select("tr").select("td");
						
						URLs.__VIEWSTATE = doc.getElementById("__VIEWSTATE").attr("value");
						
						URLs.__EVENTVALIDATION= doc.getElementById("__EVENTVALIDATION").attr("value");
						
						for(int i=0;i<course.size();i++){
						BorrowBookList.add(new BorrowBook(course.get(i).text(), course.get(++i).text(), course.get(++i).select("a").attr("href"), course.get(i).text(), course.get(++i).text(), course.get(++i).text(), course.get(++i).text(), course.get(++i).text()));
						}
						if(BorrowBookList.size()>0)
						{
							Utility.sendMsg(handler,Codes.getLibraryBookSuc,BorrowBookList);
						}
						else {
							Utility.sendMsg(handler,Codes.getLibraryBookFail);
						}
				}
				
				@Override
				public void onError(Object msg) {
					// TODO Auto-generated method stub
					Utility.sendMsg(handler,Codes.loginLibraryFail,msg);
				}
				
				@Override
				public void onError() {
					// TODO Auto-generated method stub
					Utility.sendMsg(handler,Codes.loginJwcErr);
				}
			});
		}

		//搜索图书
		public static void loginlibrary_Ctgu(final Context act,final Handler handler, String bookname,int start)
		{
			
			String booknameurl="";
			List<Book> BookList;
			try {
				booknameurl = URLEncoder.encode(bookname,"GBK");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			taskPool.addHttpGetTask(URLs.search_Library+booknameurl+"&dt=ALL&cl=ALL&dp=20&sf=M_PUB_YEAR&ob=DESC&sm=table&dept=ALL&page="+start, null,
					new AbsHttpTask() {
						@Override
						public void onComplete(InputStream paramInputStream) {
							String result = Utility.streamToString(paramInputStream);
							if (result == null || result.length() == 0)
								return;
							else{
								ToGetbookWithStream(act,handler,result);
								//Utility.sendMsg(handler, Codes.getBookSuc);
							}
						}

						@Override
						public void onError(Object msg)
						{
							Utility.sendMsg(handler, Codes.getBookFail,msg);	
						}

						@Override
						public void onError() 
						{
							Utility.sendMsg(handler, Codes.getBookFail);
							
						}
					});
		}
		
		
		// 获得成绩信息
		// isGet 为true则忽略后俩参数
		// 参数为0表示不填
 		public static void getScoreInfo(final Context act,final Handler handler,boolean isGet, String year, String term) {
			if (isGet) {
				taskPool.addHttpGetTask(URLs.getURL(URLs.score_CTGU), null, new AbsHttpTask() {
					@Override
					public void onComplete(InputStream paramInputStream) {
						System.out.println("Get to get score sucessful");
						logout();
						GetScores(act,paramInputStream);
						Utility.sendMsg(handler, Codes.getScoreInfo);

					}

					@Override
					public void onError(Object msg) 
					{
						logout();
						Utility.sendMsg(handler, Codes.getScoreInfoErr);
					}

					@Override
					public void onError() {
						logout();
						Utility.sendMsg(handler, Codes.getScoreInfoErr);
					}

				});
			} else // POST
			{

				HashMap<String, String> map = new HashMap<String, String>();
				map.put("__EVENTTARGET", "");
				map.put("__EVENTARGUMENT", "");
				map.put("__VIEWSTATE", "");
				map.put("ctl00$MainContentPlaceHolder$School_Year", year);
				map.put("ctl00$MainContentPlaceHolder$School_Term", term);
				map.put("ctl00$MainContentPlaceHolder$score_q", "RadioButton2");
				map.put("__EVENTVALIDATION",
						"/wEWJAKFuri2BALVtaKfCgKb2oHDAgKGzf/rAQKGzYOHCQKGzdfvCwKGzfuIAwKGzY+kCAKGzZPBAQKGzaf6BgKGzcuXDgKGzd+wBwKGzePtDALt9NH0CwLt9OWRAwLt9Mn4BQLt9N2VDQLt9OHOAgLt9PXrCwLt9JmHAwLt9K2gCALt9LHdAQLt9MX2BgKpl6yAAwKpl7C9CAKpl4SEDQKpl6ihAgLbxvmxDALExvmxDALFxvmxDALGxvmxDALHxvmxDAKTn4DADwKTn/TkBgLS+ZvbCQLM08eVCqUzvfMvMY+2My5MZ+9QZQA9S1+L");
				map.put("ctl00$MainContentPlaceHolder$BtnSearch.x", "14");
				map.put("ctl00$MainContentPlaceHolder$BtnSearch.y", "6");
				taskPool.addHttpPostTask(URLs.getURL(URLs.score_CTGU), map, new AbsHttpTask() {
					@Override
					public void onComplete(InputStream paramInputStream) {
						System.out.println("post to get score sucessful");
						// progressDialog.dismiss();
						// LoginActivity.this.finish();
						logout();
						GetScores(act,paramInputStream);
						Utility.sendMsg(handler, Codes.getScoreInfo);
					}

					@Override
					public void onError(Object msg) {
						logout();
						System.out.println("获取成绩失败！" + msg);
						Utility.sendMsg(handler, Codes.getScoreInfoErr);
					}

					@Override
					public void onError() {
						// LoginActivity.this.finish();
						logout();
						Utility.sendMsg(handler, Codes.getScoreInfoErr);
					}
				});
			}

		}
		
		public static void GetScores(final Context act,InputStream stream) {
			String result = Utility.streamToString(stream);
			if (result == null || result.length() == 0)
				return;
			List<Score> tmp=UtilityCtgu.resolveScoresInfo(result);
		    
			if(tmp!=null && tmp.size()!=0)
			{
				//遍历tmp，筛选及格的科目,然后存在Preference里面
			    int qualityCount=0;
			    for (Score score : tmp) {	 
			    	try {
			    		if(score!=null&&(score._Score.equals("合格")||score._Score.equals("良")||score._Score.equals("优秀")||Integer.parseInt(score._Score)>=60))
				    	{
				    		qualityCount++;
				    	}
					} catch (NumberFormatException e) {
						continue;
					}
			    	
				}	
				FileIO.saveObjectToFile(act,URLs.getFileScorePath(),tmp);
			}
		}
		
		
				// isGet当true为false时，year和term才获取对应课表内容,为true则直接get当前学期课表
				//当isGet为true时，后面俩个参数直接无视吧
				public static void getCourseTable(final Context act,final Handler handler,boolean isGet, String year, String term)
				{
					HashMap<String, String> localHashMap2 = new HashMap<String, String>();
					localHashMap2.put("ctl00$MainContentPlaceHolder$BtnSearch.y","1");
					localHashMap2.put("ctl00$MainContentPlaceHolder$BtnSearch.x","7");
					localHashMap2.put("__VIEWSTATE",URLs.getURLValue(2,1));
					localHashMap2.put("__EVENTVALIDATION",URLs.getURLValue(2,2));
					
					
						localHashMap2.put("ctl00$MainContentPlaceHolder$School_Year", year);
						localHashMap2.put("ctl00$MainContentPlaceHolder$School_Term", term);
						taskPool.addHttpPostTask(URLs.getURL(URLs.getCourseTable_CTGU), localHashMap2, new AbsHttpTask()
						{
							@Override
							public void  onComplete(InputStream paramInputStream)
							{
								logout();
								
								System.out.println("Put get table success");
								ToConstructCourseTableWithStream(act,handler, paramInputStream);
								
								//Utility.sendMsg(handler,Codes.getCourseTable);
								//LoginActivity.this.finish();
							}
							
							@Override
							public void onError(Object msg)
							{
								logout();
								System.out.println("获取课表失败！" + msg);
								Utility.sendMsg(handler,Codes.getCourseTableErr);
							}
							
							@Override
							public void onError()
							{
								logout();
								System.out.println("Put get table error");
								Utility.sendMsg(handler,Codes.getCourseTableErr);
								//msg[0]=sendMsg(Codes.getCourseTableErr, null);
								//LoginActivity.this.finish();
							}
						});
		}
				//将stream处理得到课表，并处理课表信息
				private static void ToGetbookWithStream(final Context act,final Handler handler,final String dataString)
				{
					
					Thread thread=new Thread(new Runnable()  
			        {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							try {
								List<Book> BookList=UtilityCtgu.ResolveDataToBookTable(act,dataString);
								if(BookList.size()>0)
								{
									Utility.sendMsg(handler, Codes.getBookSuc,BookList);
								}
								else {
									//无查询结果
									Utility.sendMsg(handler, Codes.getNoBook);
								}
								
							} 
							catch (Exception e) {
								// TODO: handle exception
							}
						}  
			        });
			        thread.start();  
				}
		
				//将stream处理得到课表，并处理课表信息
				private static void ToConstructCourseTableWithStream(final Context act,final Handler handler,final InputStream stream)
				{
					Thread thread=new Thread(new Runnable()  
			        {  
			            @Override  
			            public void run()  
			            {  
			        		try
			        		{
			                    String result = Utility.streamToString(stream);
			                    if(result == null || result.length() == 0)return;
			                    
			                    List<Course> courseTable =  UtilityCtgu.ResolveDataToConstructTable(act,result);
			                    
			                    if(courseTable != null)
			                    {
			                    	giveIsCollisionMark(courseTable);
				                    giveIsOverLappingMark(courseTable);
			                    	//在这里给id
			                    	for (Course course : courseTable)
									{
			                    		//Todo 暂定2015，春季
			                    		course.giveId(2015, 0);
										//course.giveId(Management.ThisYear, Management.ThisTerm);
									}
			                    	Utility.sendMsg(handler, Codes.getCourseTable,courseTable);
			                    }
			                    else 
			                    {
			                    	//TODO　没有课 但其他 有内容
			                    	Utility.sendMsg(handler, Codes.getCourseTable);
			                    	return;
								}
			        		} catch (Exception e)
			        		{
			        			
			        			Utility.sendMsg(handler, Codes.CodeErr);
			        			//System.out.println(e.getMessage());
			        		}
			            }  
			        });  
			        thread.start();  
				}
				
				//mark 是否冲突
				private static void giveIsCollisionMark(List<Course> table)
				{
					for(int i = 0; i < table.size() - 1; i++)
					{
						if(table.get(i).isCollision == true)
						continue;
						
						int j = i+1;
						if(table.get(i).WeekNum != table.get(j).WeekNum)
						{
							table.get(i).isCollision = false;
							continue;
						}
						
						while(j < table.size() && table.get(i).WeekNum == table.get(j).WeekNum )
						{
							//System.out.println(" i outside : " + i);
							if(table.get(i).StartSection != table.get(j).StartSection )
								table.get(i).isCollision = false;
							else
							{
								int maxWeekI = table.get(i).WeekPeriod + table.get(i).StartWeek - 1;
								int maxWeekJ = table.get(j).WeekPeriod + table.get(j).StartWeek - 1;
								int minWeekI = table.get(i).StartWeek;
								int minWeekJ = table.get(j).StartWeek;
								
								System.out.println(table.get(i).WeekPeriod + " " + table.get(j).WeekPeriod + " " + minWeekI + " " + minWeekJ);
								if(((minWeekI <= maxWeekJ && maxWeekI >= maxWeekJ) || (minWeekJ <= maxWeekI && maxWeekJ >= maxWeekI)) && (table.get(i).WeekSpecialNum == 0 || table.get(j).WeekSpecialNum == 0 || table.get(i).WeekSpecialNum == table.get(j).WeekSpecialNum))
									{
										//System.out.println("i: " + i + " table I " + table.get(i).Title + " table J "+ table.get(j).Title );
										table.get(i).isCollision = true;
										table.get(j).isCollision = true;
										//System.out.println("mark冲突： " + table.get(i).Title + table.get(j).Title);
									}
								else {
									table.get(i).isCollision = false;
								}
							}
							j++;
						}
							
					}
				}
				
				//mark是否重叠
				private static void giveIsOverLappingMark(List<Course> table)
				{
					for(int i = 0; i < table.size() - 1; i++)
					{
						if(table.get(i).isOverlapping == true)
							continue;
						
						int j = i+1;
						if(table.get(i).WeekNum != table.get(j).WeekNum)
						{
							table.get(i).isOverlapping = false;
							continue;
						}
						
						while(j < table.size() && table.get(i).WeekNum == table.get(j).WeekNum )
						{
							if(table.get(i).StartSection != table.get(j).StartSection)
								table.get(i).isOverlapping = false;
							else
							{
								int maxWeekI = table.get(i).WeekPeriod + table.get(i).StartWeek - 1;
								int maxWeekJ = table.get(j).WeekPeriod + table.get(j).StartWeek - 1;
								int minWeekI = table.get(i).StartWeek;
								int minWeekJ = table.get(j).StartWeek;
								if((minWeekI > maxWeekJ || minWeekJ > maxWeekI) || (table.get(i).WeekSpecialNum != 0 && table.get(j).WeekSpecialNum != 0 && table.get(i).WeekSpecialNum != table.get(j).WeekSpecialNum))
									{
										table.get(i).isOverlapping = true;
										table.get(j).isOverlapping = true;
										//System.out.println("mark重叠： " + table.get(i).Title + table.get(j).Title);
									}
								else {
									table.get(i).isOverlapping = false;
								}
							}
							j++;
						}
						
					}
				}

				
		// 登出
		public static Message logout() 
		{
			final Message msg[] = new Message[2];
			taskPool.addHttpGetTask(URLs.getURL(URLs.logout_CTGU), null, new AbsHttpTask() 
			{
				@Override
				public void onComplete(InputStream paramInputStream) 
				{
					URLs.jwc_cookies.clear();
				}

				@Override
				public void onError()
				{
				}

				@Override
				public void onError(Object msg) 
				{
				}
			});
			return msg[0];
		}
		
		// 评教
		public static void getEvaluationInfo(final Handler  handler) {
			taskPool.addHttpGetTask(URLs.getURL(URLs.evaluation_CTGU), null, new AbsHttpTask() {
				@Override
				public void onComplete(InputStream paramInputStream) {
					//logout();//!Warning
					List<EvaluateTeaching> evaluation = new LinkedList<EvaluateTeaching>();
					evaluation=ToAnalysisEvaluationStream(paramInputStream);
					logout();
					Utility.sendMsg(handler, Codes.getEvaluationInfo,evaluation);
				}

				@Override
				public void onError(Object msg) {
					logout();
					Utility.sendMsg(handler, Codes.getEvaluationInfoErr);
				}

				@Override
				public void onError() {
					logout();
					Utility.sendMsg(handler, Codes.getEvaluationInfoErr);
				}
			});
		}
		
		public static void getCourseEvaluationDetail(
				final WeakReference<Handler> w_handler,final EvaluateTeaching e){
			if (e != null && e.id.length() != 0) {
				String urlString = String
						.format(URLs.getURL(URLs.evaluation2_CTGU) + "%s", e.id);
				System.out.println(urlString);
				taskPool.addHttpGetTask(urlString, null, new AbsHttpTask() {
					@Override
					public void onComplete(InputStream paramInputStream) {
						PostToEvaluation(w_handler, e);
					}

					@Override
					public void onError(Object msg) {
						Utility.sendMsg(w_handler, Codes.getEvaluationInfoErr);
						Log.i(LOGTAG, "getEvaluation failed "+e.id);
					}

					@Override
					public void onError() {		
						Utility.sendMsg(w_handler, Codes.getEvaluationInfoErr);
						Log.i(LOGTAG, "getEvaluation failed "+e.id);
					}

				});
			}

		}
		public static void PostToEvaluation(
				final WeakReference<Handler> w_handler,final EvaluateTeaching e){
			int num=8;
			int score=e.Value/num;
			score=(e.Value%num>=num/2)?++score:score;
			Log.i("PostToEvaluation: ",e.id+","+score);
			HashMap<String, String> m = new HashMap<String, String>();
			m.put("__EVENTVALIDATION",
					"/wEWNwLC8cOcAQK+8uL8DAKl8uL8DAKi8uL8DAKj8uL8DAKg8uL8DAKh8uL8DAKTtPL/DAKItPL/DAKPtPL/DAKOtPL/DAKNtPL/DAKMtPL/DALUvvL/DALPvvL/DALIvvL/DALJvvL/DALKvvL/DALLvvL/DAKRpPL/DAKKpPL/DAKNpPL/DAKMpPL/DAKPpPL/DAKOpPL/DALSrvL/DALJrvL/DALOrvL/DALPrvL/DALMrvL/DALNrvL/DAKXlPL/DAKMlPL/DAKLlPL/DAKKlPL/DAKJlPL/DAKIlPL/DAKohu38DAKzhu38DAK0hu38DAK1hu38DAK2hu38DAK3hu38DALlje38DAL+je38DAL5je38DAL4je38DAL7je38DAL6je38DAKl0LPfCwKl0Mf7AgL8hv5YAvGriDYCnLe4kgwCvq7zTtOWVfVgbtUU28BPcEnfib4wmfve");
			m.put("__VIEWSTATE",
					"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");
			m.put("btnSave", "·确定·");
			for(int i=2;i<=num+1;i++)
				m.put(String.format("GridCourse2$ctl%02d$userscore", i),""+score);// 分数
			m.put("SuitTeach", "RadioButton1");
			m.put("TeacherChange", "");
			m.put("TeacherGood", "");
			String urlString = String.format(URLs.getURL(URLs.evaluation2_CTGU) + "%s", e.id);
			System.out.println(urlString);
			taskPool.addHttpPostTask(urlString, m, new AbsHttpTask() {
				public void onComplete(InputStream paramInputStream) {
					//String result = Utility.streamToString(paramInputStream);
					//Log.i(LOGTAG, result);
					Utility.sendMsg(w_handler, Codes.getEvaluationInfo);
					//PostToCloseInEvaluation(e);
				}

				public void onError(Object msg) {
					Utility.sendMsg(w_handler, Codes.getEvaluationInfoErr);
					Log.i(LOGTAG, "PostToEvaluation failed "+e.id);
				}

				@Override
				public void onError() {
					Utility.sendMsg(w_handler, Codes.getEvaluationInfoErr);
					Log.i(LOGTAG, "PostToEvaluation failed "+e.id);
					
				}
			});
		}

		// �ر�
		public static void PostToCloseInEvaluation(EvaluateTeaching e) {
			HashMap<String, String> m = new HashMap<String, String>();
			m.put("__VIEWSTATE",
					"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");
			m.put("btnBack", "���رա�");// ����
			m.put("__EVENTVALIDATION",
					"/wEWAgK8pIP3BgK+rvNO9MCBSO00mLlYfMQgbTR1jnx3T/E=");

			String urlString = String.format(URLs.evaluation2_CTGU + "%s", e.id);
			System.out.println(urlString);
			taskPool.addHttpPostTask(urlString, m, new AbsHttpTask() {
				@Override
				public void onComplete(InputStream paramInputStream) {
					logout();
					System.out.println("�رճɹ���");
				}

				@Override
				public void onError(Object msg) {
					logout();
					System.out.println("�ر�ʧ�ܣ�" + msg);
				}

				@Override
				public void onError() {
					// TODO Auto-generated method stub
					
				}

			});
		}
		
		private static List<EvaluateTeaching> ToAnalysisEvaluationStream(final InputStream stream) {
	//		Thread thread = new Thread(new Runnable() {
	//			@Override
	//			public void run() {
					String result = Utility.streamToString(stream);
					if (result == null || result.length() == 0)
						return null;
					return  UtilityCtgu.resolveEvaluationInfo(result);
					//getCourseEvaluationDetail(Management.evaluation.get(0));
		//		}
		//	});
			//thread.start();
		}

	//失物招领------------------------------------------------
		
		//失物信息
		public static void lost_detail(final Context act,final Handler handler, int url,int type)
		{
			if(type==0){
			taskPool.addHttpGetTask(URLs.getLostMessage+url, null,
					new AbsHttpTask() {
						@Override
						public void onComplete(InputStream paramInputStream) {
							String result = Utility.streamToString(paramInputStream);
							ArrayList<LostFoundDetail> losts = new ArrayList<LostFoundDetail>();
							if (result == null || result.length() == 0)
								return;
							else{
								losts=ToGetLostMessage(act,handler,result);
								Utility.sendMsg(handler, Codes.getLostSuc,losts);
							}
						}

						@Override
						public void onError(Object msg)
						{
							Utility.sendMsg(handler, Codes.getLostFail,msg);	
						}

						@Override
						public void onError() 
						{
							Utility.sendMsg(handler, Codes.getLostFail);
							
						}
					});
			}
			else {
				taskPool.addHttpGetTask(URLs.getFoundMessage+url, null,
						new AbsHttpTask() {
							@Override
							public void onComplete(InputStream paramInputStream) {
								String result = Utility.streamToString(paramInputStream);
								ArrayList<LostFoundDetail> losts = new ArrayList<LostFoundDetail>();
								if (result == null || result.length() == 0)
									return;
								else{
									losts=ToGetLostMessage(act,handler,result);
									Utility.sendMsg(handler, Codes.getLostSuc,losts);
								}
							}

							@Override
							public void onError(Object msg)
							{
								Utility.sendMsg(handler, Codes.getLostFail,msg);	
							}

							@Override
							public void onError() 
							{
								Utility.sendMsg(handler, Codes.getLostFail);
								
							}
						});
				
			}
		}
		//失物详情
		public static void getlost_detail(final Context act,final Handler handler, String url,int type)
		{
			if(type == 0){
			taskPool.addHttpGetTask(url, null,
					new AbsHttpTask() {
						@Override
						public void onComplete(InputStream paramInputStream) {
							String result = Utility.streamToString(paramInputStream);
							LostFoundDetail lost = new LostFoundDetail();
							if (result == null || result.length() == 0)
								return;
							else{
								lost=ToGetLostDetail(act,handler,result);
								Utility.sendMsg(handler, Codes.getLostDetailSuc,lost);
							}
						}

						@Override
						public void onError(Object msg)
						{
							Utility.sendMsg(handler, Codes.getLostDetailFail,msg);	
						}

						@Override
						public void onError() 
						{
							Utility.sendMsg(handler, Codes.getLostDetailFail);
							
						}
					});
			}
			else {
				taskPool.addHttpGetTask(url, null,
						new AbsHttpTask() {
							@Override
							public void onComplete(InputStream paramInputStream) {
								String result = Utility.streamToString(paramInputStream);
								LostFoundDetail lost = new LostFoundDetail();
								if (result == null || result.length() == 0)
									return;
								else{
									lost=ToGetLostDetail(act,handler,result);
									Utility.sendMsg(handler, Codes.getLostDetailSuc,lost);
								}
							}

							@Override
							public void onError(Object msg)
							{
								Utility.sendMsg(handler, Codes.getLostDetailFail,msg);	
							}

							@Override
							public void onError() 
							{
								Utility.sendMsg(handler, Codes.getLostDetailFail);
								
							}
						});
				
			}
			}
		//解析失物招领------------------------------
		//失物招领信息
	public  static ArrayList<LostFoundDetail> ToGetLostMessage(Context act,
						Handler handler, String result) {
					// TODO Auto-generated method stub
					try
					{
						ArrayList<LostFoundDetail> losts = new ArrayList<LostFoundDetail>();
						Document doc = Jsoup.parse(result); 
						Elements score1 = doc.getElementsByClass("name_message");
						Elements score2 = doc.getElementsByClass("tezheng_message");
						Elements score3 = doc.getElementsByClass("time_message");
						
						if(score1 != null )
						{
							Elements Elements1 = score1.select("td").select("a");
							
							for(int i = 0 ; i < score1.size();i++){
								
								LostFoundDetail lost= new LostFoundDetail();
								lost.setUrl(URLs.lostfound+score1.get(i).select("a").attr("href"));
								lost.setName(Elements1.get(i).text());
								lost.setDetail(score2.get(i).text());
								lost.setPosttime(score3.get(i).text());
								losts.add(lost);
							}
							System.out.println(losts.size());
							return losts;
							
						}
						
					} catch (Exception e)
					{
						Log.i(Utility.class.toString(), "resolveEvaluationInfo failed ");
						
					}
					return null;
					
				}
				
	public static LostFoundDetail ToGetLostDetail(Context act,
			Handler handler, String result) {
		// TODO Auto-generated method stub
		try
		{
			LostFoundDetail lost = new LostFoundDetail();
			Document doc = Jsoup.parse(result); 
			Elements score = doc.getElementsByClass("view_tezheng_message");
		
			
			if(score != null )
			{
				
				lost.setName(score.get(0).text());
				lost.setDetail(score.get(1).text());
				lost.setTime(score.get(2).text());
				lost.setAddress(score.get(3).text());
				lost.setTele(score.get(4).text());
				lost.setPostName(score.get(5).text());
				lost.setPosttime(score.get(7).text());
				return lost;
				
			}
			
		} catch (Exception e)
		{
			Log.i(Utility.class.toString(), "resolveEvaluationInfo failed ");
			
		}
		return null;
		
	}
	
	
	public static void PostLostFound (final Handler handler,LostFoundDetail lostfound) {
		HashMap<String, String> localHashMap = new HashMap<String, String>();
     String uri;
		localHashMap
				.put(Utility.toUtf8("diu_place"),
						Utility.toUtf8(lostfound.getAddress()));
		localHashMap
		.put(Utility.toUtf8("diu_time"),
				Utility.toUtf8(lostfound.getTime()));
		localHashMap
		.put(Utility.toUtf8("into_add"),
				Utility.toUtf8("发布信息"));
		localHashMap
		.put(Utility.toUtf8("keyword"),
				Utility.toUtf8(lostfound.getDetail()));
		
		localHashMap
		.put(Utility.toUtf8("name"),
				Utility.toUtf8(lostfound.getPostName()));
		
		localHashMap
		.put(Utility.toUtf8("other"),
				Utility.toUtf8(lostfound.getOther()));
		
		localHashMap
		.put(Utility.toUtf8("phone"),
				Utility.toUtf8(lostfound.getTele()));
		
		localHashMap
		.put(Utility.toUtf8("qq"),
				Utility.toUtf8(lostfound.getQq()));
		localHashMap
		.put(Utility.toUtf8("title"),
				Utility.toUtf8(lostfound.getName()));
		if(lostfound.getType().equals("1")){
			uri = URLs.postLost;
		}
		else {
			uri = URLs.postfound;
		}
		taskPool.addHttpPostTask(uri,localHashMap,new AbsHttpTask() {
			
			@Override
			public void onError(Object msg) {
				// TODO Auto-generated method stub
				Utility.sendMsg(handler, Codes.postlostfoundFail);	
			}
			
			@Override
			public void onError() {
				// TODO Auto-generated method stub
				Utility.sendMsg(handler, Codes.postlostfoundFail);	
			}
			
			@Override
			public void onComplete(InputStream paramInputStream) {
				// TODO Auto-generated method stub
				String result = Utility.streamToString(paramInputStream);
				Utility.sendMsg(handler, Codes.postlostfoundSuc);	
				
				
			}
		});
	}
	
	
		//图书续借	
		public static void continue_Borrow(final Handler handler,String Url,int position)
		{
			HashMap<String, String> localHashMap = new HashMap<String, String>();
				
			localHashMap.put(Utility.toUtf8("__EVENTVALIDATION"),
								Utility.toUtf8(URLs.__EVENTVALIDATION));
			localHashMap.put(Utility.toUtf8("__VIEWSTATE"),Utility.toUtf8(URLs.__VIEWSTATE));
			localHashMap.put(Utility.toUtf8("ctl00$cpRight$btnRenew"),Utility.toUtf8("续借"));
			localHashMap.put(Utility.toUtf8("ctl00$cpRight$borrowedRep$ctl0"+(++position)+"$cbk"),Utility.toUtf8("on"));
			taskPool.addHttpPostTask(URLs.reborrow_book, localHashMap, new AbsHttpTask() {
				
				
				@Override
				public void onComplete(InputStream paramInputStream) {
					Utility.sendMsg(handler,Codes.continueBorrowSuc);	
				}
				
				@Override
				public void onError(Object msg) {
					// TODO Auto-generated method stub
					Utility.sendMsg(handler,Codes.continueBorrowFail);
				}
				
				@Override
				public void onError() {
					// TODO Auto-generated method stub
					Utility.sendMsg(handler,Codes.continueBorrowFail);
				}
				
				
			});
		}

		//重定向后续借
		public static void getReBorrowBook(final Handler handler) {
			// TODO Auto-generated method stub
			taskPool.addHttpGetTask(URLs.againborrow_book, null, new AbsHttpTask() {
				@Override
				public void onComplete(InputStream paramInputStream) {
					// TODO Auto-generated method stub
					String result = Utility.streamToString(paramInputStream);	

					Document doc = Jsoup.parse(result);
					
					Elements table = doc.getElementsByClass("modifyMsg");
					
					if(!"".equals(table))
					{
						Utility.sendMsg(handler,Codes.chonecontinueBorrowSuc);
					}
					else {
						Utility.sendMsg(handler,Codes.continueBorrowFail);
					}
					
				}
				@Override
				public void onError(Object msg) {
					// TODO Auto-generated method stub
					Utility.sendMsg(handler,Codes.continueBorrowFail,msg);
				}
				
				@Override
				public void onError() {
					// TODO Auto-generated method stub
					Utility.sendMsg(handler,Codes.continueBorrowFail);
				}
				

				
			});
		}

		/*  关于新闻   */
		//校园新闻
		public static void getSchoolNews(final Context act, final Handler han, final int type, int pages){
			String url;
			if(type == 0){
				url = URLs.schoolnewurl;
			}
			else{
				url = URLs.nextschoolnewurl + pages + ".htm";
			}
				taskPool.addHttpGetTask(url, null, new AbsHttpTask() {
				@Override
				public void onError(Object msg) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onError() {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onComplete(InputStream paramInputStream) {
					// TODO Auto-generated method stub
					String result = Utility.streamToString(paramInputStream);
					ArrayList<NewsContent> scnews = new ArrayList<>();
					if (result == null || result.length() == 0)
						return;
					else{
						scnews =ToGetSchoolNews(act,han,result);
						Utility.sendMsg(han, Codes.schoolnews,scnews);
					}
					
				}
			});
			
		}
		public static ArrayList<NewsContent> ToGetSchoolNews(Context act, Handler han, String result){
			try
			{
				ArrayList<NewsContent> scnews = new ArrayList<NewsContent>();
				Document doc = Jsoup.parse(result); 
				Elements content = doc.getElementsByClass("c10173");
				Elements time = doc.getElementsByClass("timestyle10173");
				/*Element pagenumber = doc.getElementById("fanye10173");
				String pagestr = pagenumber.select("td").text().trim();
				//获取总的页数
				SchoolNewsFragment.pages = Integer.parseInt(pagestr.substring(pagestr.length() - 4));*/
				if(content != null )
				{
					Elements news = content.select("a");
					
					for(int i = 0 ; i < news.size();i++){
						
						NewsContent mess= new NewsContent();
						mess.setContent(news.get(i).text());
						String temphref = news.get(i).attr("href").trim();
						if(temphref.contains("../")){
							mess.setHref(news.get(i).attr("href").trim().substring(3));
						}
						else{
							mess.setHref(news.get(i).attr("href").trim());
						}
							
						mess.setTime(time.get(i).text());
						
						scnews.add(mess);
					}
					
					return scnews;
					
				}
				
			} catch (Exception e)
			{
				Log.i(Utility.class.toString(), "resolveEvaluationInfo failed ");
				
			}
			return null;
		}
		//校园新闻详细
		public static void getSchoolNews_Detail(final Context act,final Handler handler, String url){
		taskPool.addHttpGetTask(url, null,
				new AbsHttpTask() {
					@Override
					public void onComplete(InputStream paramInputStream) {
						String result = Utility.streamToString(paramInputStream);
						NewsContent newdetail = new NewsContent();
						if (result == null || result.length() == 0)
							return;
						else{
							newdetail = ToGetNewsDetail(act,handler,result);
							Utility.sendMsg(handler, Codes.schoolnewsdetail,newdetail);
						}
					}

					@Override
					public void onError(Object msg)
					{
						Utility.sendMsg(handler, Codes.schoolnewsdetail,msg);	
					}

					@Override
					public void onError() 
					{
						Utility.sendMsg(handler, Codes.schoolnewsdetail);
						
					}
				});
		}
		public static NewsContent ToGetNewsDetail(Context act, Handler han, String result){
			try
			{
				NewsContent newid = new NewsContent();
				Document doc = Jsoup.parse(result); 
				Elements tile = doc.getElementsByClass("titlestyle10170");
				Elements author = doc.getElementsByClass("authorstyle10170");
				Elements time = doc.getElementsByClass("timestyle10170");
				Element detail = doc.getElementById("vsb_content_1002");
				if(tile != null )
				{
					newid.setContent(tile.get(0).text());
					newid.setAuthor(author.get(0).text());
					newid.setTime(time.get(0).text());
					List<String> Temp = new ArrayList<String>();
					for(Element temp:detail.select("p")){
						
						if(!(temp.text()).isEmpty()){
							Temp.add(temp.text());
						}
					}
					newid.setDetail(Temp);
					return newid;	
				}
				
			} catch (Exception e)
			{
				Log.i(Utility.class.toString(), "resolveEvaluationInfo failed ");	
			}
			return null;	
		}
		//教务处即时新闻
		public static void getJiaowuNews(final Context act, final Handler han, final int type, int pages){
			String url;
			if(type == 0){
				url = URLs.instantnewsdetail1 + String.valueOf(pages) + URLs.instantnewsdetail2;
			}
			else{
				url = URLs.examnewsdetail1 + String.valueOf(pages) + URLs.examnewsdetail2;
			}
				taskPool.addHttpGetTask(url, null, new AbsHttpTask() {
				@Override
				public void onError(Object msg) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onError() {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onComplete(InputStream paramInputStream) {
					// TODO Auto-generated method stub
					String result = null;
					try{
					InputStreamReader re = new InputStreamReader(paramInputStream, "gb2312");//转码，转为gb2312
					BufferedReader read = new BufferedReader(re);
					String line = null;
					while((line = read.readLine()) != null){
						result += line;
					}
					}
					catch(Exception e){
						e.printStackTrace();
					}
					ArrayList<NewsContent> icnews = new ArrayList<>();
					if (result == null || result.length() == 0)
						return;
					else{
						icnews =ToGetJiaowuNews(act,han,result);
						Utility.sendMsg(han, Codes.instantnews,icnews);
					}
					
				}
			});
			
		}
		public static ArrayList<NewsContent> ToGetJiaowuNews(Context act, Handler han, String result){
			try
			{
				ArrayList<NewsContent> icnews = new ArrayList<NewsContent>();
				Document doc = Jsoup.parse(result);
				Elements newlines = doc.getElementsByAttributeValue("style", "line-height:15pt");
				Elements timelines = doc.getElementsByAttributeValue("color", "999999");
				if(newlines != null )
				{
					for(int i = 0 ; i < newlines.size();i++){
						if(i % 2 == 1){
							NewsContent mess= new NewsContent();
							Element line = newlines.get(i).select("a").get(1);
							Element time = timelines.get(i);
							mess.setContent(line.select("font").text());
							mess.setHref(line.attr("href"));
							mess.setPv(newlines.get(i).select("font").get(1).text());
							mess.setTime(time.text());
							icnews.add(mess);
						}
					}
					
					return icnews;
					
				}
				
			} catch (Exception e)
			{
				Log.i(Utility.class.toString(), "resolveEvaluationInfo failed ");
				
			}
			return null;
		}
		//获取教务详细信息
		public static void getJiaowuNews_Detail(final Context act,final Handler handler, String url){
			taskPool.addHttpGetTask(url, null,
					new AbsHttpTask() {
						@Override
						public void onComplete(InputStream paramInputStream) {
							String result = "";
							try{
								InputStreamReader re = new InputStreamReader(paramInputStream, "gb2312");//转码，转为gb2312
								BufferedReader read = new BufferedReader(re);
								String line = "";
								while((line = read.readLine()) != null){
									result += line;
								}
							}
							catch(Exception e){
								e.printStackTrace();
							}
							if (result == null || result.length() == 0){
								return;
							}
							else{
								Utility.sendMsg(handler, Codes.schoolnewsdetail,result);
							}
						}

						@Override
						public void onError(Object msg)
						{
							Utility.sendMsg(handler, Codes.schoolnewsdetail,msg);	
						}

						@Override
						public void onError() 
						{
							Utility.sendMsg(handler, Codes.schoolnewsdetail);
							
						}
					});
			}
		
		
		//勤工俭学----------------------------------
		
		//校外招聘
		public static void xiaowai(final Context act,final Handler handler,String type,int page)
		{
			String uri = null;
			if(type.equals("1")){
				uri = URLs.xiaowai ;	
			}
			if(type.equals("2")){
				uri = URLs.jiajiao ;
			}
			if(type.equals("3")){
				uri = URLs.xiaonei ;
			}
			if(type.equals("4")){
				uri = URLs.dongtai ;
			}
			taskPool.addHttpGetTask(uri+page, null, new AbsHttpTask() {
				@Override
				public void onComplete(InputStream paramInputStream) {
					// TODO Auto-generated method stub
					
					ArrayList<PartTimeJob> partjob = new ArrayList<PartTimeJob>();
					String result = Utility.streamToString1(paramInputStream);	

					
					Document doc = Jsoup.parse(result);
					 Elements table = doc.getElementsByTag("tbody");
					  table = table.get(0).getElementsByTag("table");
					  table = table.get(0).getElementsByTag("tbody");
					  table = table.get(0).getElementsByTag("tr");
					
					if(!"".equals(table))
					{
						System.out.println(table.size());
					
							Elements Elements1 = table.select("td");
							
							for(int j = 0 ;j <Elements1.size()-3;j++)
								
									if((j+1)%3==0){
								
							       PartTimeJob pJob= new PartTimeJob();
							       pJob.setUrl(URLs.job + Elements1.get(j).select("a").attr("href"));
							
							       pJob.setJobcontext(Elements1.get(j).text());
							       partjob.add(pJob);
									}
						Utility.sendMsg(handler,Codes.getjobSuc,partjob);
					}
					else {
						Utility.sendMsg(handler,Codes.getjobFail);
					}	
				}
				@Override
				public void onError(Object msg) {
					// TODO Auto-generated method stub
					Utility.sendMsg(handler,Codes.getjobFail,msg);
				}
				
				@Override
				public void onError() {
					// TODO Auto-generated method stub
					Utility.sendMsg(handler,Codes.getjobFail);
				}

				
			});
			
		}
		public static void getjob_detail(final Context act,final Handler handler,String url,String type)
		{
			
			if(type.equals("2")){
				
				
				taskPool.addHttpGetTask(url, null, new AbsHttpTask() {
					@Override
					public void onComplete(InputStream paramInputStream) {
						// TODO Auto-generated method stub
						
					Jiajiao job = new Jiajiao();
						String result = Utility.streamToString1(paramInputStream);	

						
						
						Document doc = Jsoup.parse(result);
						
					
						 Elements table = doc.getElementsByClass("STYLE1");
						
						
						if(!"".equals(table))
						{
							job.setPostdete(table.get(0).text());
							job.setPosttime(table.get(2).text());
							job.setXueyuansex(table.get(4).text());
							job.setXueyuangrade(table.get(5).text());
							job.setKemu(table.get(6).text());
							job.setXueyuanaddress(table.get(7).text());
							job.setXueyuanbeiju(table.get(8).text());
							job.setJiaoyuansex(table.get(9).text());
							job.setJiaoyuangrade(table.get(10).text());
							job.setJiaoyuanmajor(table.get(11).text());
							job.setJiaoyuanneed(table.get(12).text());
							job.setJobdaiyu(table.get(13).text());
							job.setJobtime(table.get(14).text());
							job.setJobtype(table.get(15).text());
						
							
							
							Utility.sendMsg(handler,Codes.getjiajiaodetail,job);
						}
						else {
							Utility.sendMsg(handler,Codes.getjobFail);
						}	
					}
					@Override
					public void onError(Object msg) {
						// TODO Auto-generated method stub
						Utility.sendMsg(handler,Codes.getjobFail,msg);
					}
					
					@Override
					public void onError() {
						// TODO Auto-generated method stub
						Utility.sendMsg(handler,Codes.getjobFail);
					}

					
				});
				
			}
			else{
			taskPool.addHttpGetTask(url, null, new AbsHttpTask() {
				@Override
				public void onComplete(InputStream paramInputStream) {
					// TODO Auto-generated method stub
					
					ArrayList<PartTimeJob> partjob = new ArrayList<PartTimeJob>();
					String result = Utility.streamToString1(paramInputStream);	

					
					PartTimeJob job= new PartTimeJob();
					Document doc = Jsoup.parse(result);
					
				
					 Elements table = doc.getElementsByClass("STYLE1");
					
					
					if(!"".equals(table))
					{
						job.setPostdept(table.get(0).text());
						job.setPosttime(table.get(2).text());
						job.setDanwei(table.get(4).text());
						job.setNumber(table.get(5).text());
						job.setTele(table.get(6).text());
						job.setJobtime(table.get(7).text());
						job.setJobaddress(table.get(8).text());
						job.setJobcontext(table.get(9).text());
						job.setJobxinzhi(table.get(10).text());
						job.setJobdaiyu(table.get(11).text());
						job.setJobneed(table.get(12).text());
						job.setJobtype(table.get(13).text());
						
						
						
						Utility.sendMsg(handler,Codes.getjobdetail,job);
					}
					else {
						Utility.sendMsg(handler,Codes.getjobFail);
					}	
				}
				@Override
				public void onError(Object msg) {
					// TODO Auto-generated method stub
					Utility.sendMsg(handler,Codes.getjobFail,msg);
				}
				
				@Override
				public void onError() {
					// TODO Auto-generated method stub
					Utility.sendMsg(handler,Codes.getjobFail);
				}

				
			});
			
			}
			}
}
