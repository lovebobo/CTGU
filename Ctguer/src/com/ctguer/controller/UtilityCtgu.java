package com.ctguer.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ctguer.model.Book;


import com.ctguer.model.Course;
import com.ctguer.model.EvaluateTeaching;
import com.ctguer.model.Score;

import android.content.Context;
import android.util.Log;

public class UtilityCtgu {

	private static int classstart = -1;
	private static int count = -1; 
	
	public static String isRepeatLogin(String source)
	{
		String html = source;
		Document doc = Jsoup.parse(html);
		Element msg = doc.getElementById("lblMsg");
		if (msg == null)// && msg.text() == Codes.repeatLoginJwc
		{
			return "";
		}
		return msg.text().toString();
	}
	
	//姓名
		public static String pickName(String source) {
			String Name=null;
			if (source == null || source.length() == 0)
				return Name;
			String html = source;
			try {
				Document doc = Jsoup.parse(html);
			
				Element uElement = doc.getElementById("ctl00_lblSignIn");
				if (uElement != null && uElement.text().trim().length() != 0)
				{
					Name=Utility.getRegex(uElement.text().trim(), "\\D+");
					Log.i("Name ", Name);
				}
				}
			catch(Exception e){
				return null;
				
			}
			return Name;
		}
		
		// 把HTML代码加载到doc中
		private static Document getDoc(String source)
		{
			if (source == null || source.length() == 0)
				return null;
		 	Document doc = Jsoup.parse(source); 
		 	return doc;
		}
		
		
		// 分组输出各项
		public static String[] Element2Array(Element e) {
			String tmp = e.html().replace("<br />", "##").replace("&nbsp;", "##");
			String[] result = tmp.split("##");
			if (result.length == 0)
				return null;
			return result;

		}
		
		// 提取班级号
		public static String pickClassNo(String t) {
			int end = t.indexOf("班)");
			int start = end;
			String Str_ClassNo = new String();
			do {
				start--;
			} while (t.charAt(start) != '(');
			do {
				start++;
				Str_ClassNo += t.charAt(start);
			} while (start < end - 1);
			// int ClassNo=Integer.parseInt(Str_ClassNo);
			return Str_ClassNo;
		}
		
		// 解析课表下的其他课程，也就是实验课程
		public static String[] resolveOtherCourses(String source) 
		{
			String[] other=null;
			// 课表下面的一些课程解析
			if (source != null){
				Document doc = Jsoup.parse(source);
				Element table = doc
						.getElementById("ctl00_MainContentPlaceHolder_Label1");
				//System.out.println(table);
				// Elements course = table.children();
				other = Element2Array(table);
			}

			return other;
		}
		
		//提取书籍名字
		public static String pickBookName(String t)
		{
			int end = t.indexOf("</a>");
			int start = end;
			
			String Str_BookName = new String();
			do {
				start--;
			} while (t.charAt(start) != '>');
			do {
				start++;
				Str_BookName += t.charAt(start);
			} while (start < end - 1);
			return Str_BookName;
		}
		
		public static List<Book> ResolveDataToBookTable(final Context act,String source)
		{
			
			List<Book> BookList = new ArrayList<Book>();
			if (source == null || source.length() == 0)
				return null;
			String html = source;
			Document doc = Jsoup.parse(html);
			Elements table = doc
					.getElementsByClass("tb");
			if (table == null)
				return null;
			Elements course = table.select("tr").select("td");
			
			if (course == null) {
				return null;
			}

			for (int i=0;i<course.size(); i++) {
				
					if(i>=9)
					{    
						BookList.add(new Book(course.get(i).text(), course.get(++i).text(),course.get(i).select("a").attr("href"),course.get(++i).text(), course.get(++i).text(), course.get(++i).text(), course.get(++i).text(), course.get(++i).text(), course.get(++i).text(),course.get(++i).text()));
					}
			}
			
			return BookList;
		}
		// 解析课表
		public static List<Course> ResolveDataToConstructTable(final Context act,String source)
		{
			List<Course> OneWeekCourses = new ArrayList<Course>();
			if (source == null || source.length() == 0)
				return null;
			String html = source;
			Document doc = Jsoup.parse(html);
			
			//取得实验课程并保存下来
			String[] otherCourse=resolveOtherCourses(source);
			if(otherCourse!=null)
			{
				FileIO.saveObjectToFile(act, URLs.othercourseTables, otherCourse);
			}
			
			//课表
			Element table = doc.select(".GridViewStyle").first();
			if (table == null)
				return null;
			Elements course = table.select("tr").select("td");
			
			if (course == null) {
				return null;
			}
			
			for (Element element : course) {
				String classnum = "0";// = -1;
				// int classstart = -1;
				int classperiod = -1;
				int weekstart = -1;
				int weekperiod = -1;

				count++;
				if (element.text().length() != 0 && startpos(element.text()) != -1)
				{
					classstart = startpos(element.text());// 第几节课
				}

				String[] tmp = Element2Array(element);
				//System.out.println("tmp :" + tmp);
				if (tmp != null) {
					for (int k = 0; k < tmp.length / 5; k++) {
						if (tmp[k * 5].contains("班)")) {
							classnum = pickClassNo(tmp[k * 5]);
							//匹配10班
						}

						String[] numsStrings = tmp[k * 5 + 2].replace("周", "")
								.split("-");
						weekstart = Integer.parseInt(numsStrings[0]);
						weekperiod = Integer.parseInt(numsStrings[1]) - weekstart
								+ 1;

						Course tmpcourse = new Course();
						//tmpcourse.ID = tmpcourse.hashCode();// !Warn Hash不能判断是否为同一堂课
						tmpcourse.Title = tmp[k * 5]
								.replaceAll("\\([\\d]+班\\)", "");// 匹配10班;
						tmpcourse.ClassNum = classnum; // 要重新获取，因为可能有两节课在一起需要分别解析班
						tmpcourse.TeacherName = tmp[k * 5 + 4];
						tmpcourse.ClassRoomPos = tmp[k * 5 + 1];
						tmpcourse.StartSection = classstart;
						tmpcourse.CoursePeriod = 2;
						tmpcourse.StartWeek = weekstart;
						tmpcourse.WeekPeriod = weekperiod;
						tmpcourse.WeekNum = count % 8;// 相同
						if (tmp[k * 5 + 3].length() != 0) {
							int speciaknum = getWeekSpecialNum(tmp[k * 5 + 3]);
							if (speciaknum != -1)
								tmpcourse.WeekSpecialNum = speciaknum;
						} else {
							tmpcourse.WeekSpecialNum = 0;
						}
						tmpcourse.isAlarm = true;
						OneWeekCourses.add(tmpcourse);
					}
				}
			}
			
			
			if(OneWeekCourses.size() == 0)return null;
			return OneWeekCourses;
		
		}
		
		// 解析评教
		// 返回值待定
		public static List<EvaluateTeaching> resolveEvaluationInfo(String source) 
		{
			System.out.println("解析评教信息： ");
			List<EvaluateTeaching> evaluation = new LinkedList<EvaluateTeaching>();
			try
			{
				
				Document doc = getDoc(source);
				Element score = doc.getElementById("ctl00_MainContentPlaceHolder_GridCourse");
				if(score != null)
				{
					Elements Elements = score.select("tr").select("td");
					if(Elements != null)
					{
						System.out.println("size " + Elements.size());
						
							for(int i = 0; i < Elements.size() / 7; i++)
							{
								EvaluateTeaching e = new EvaluateTeaching();
								e.Year = Integer.parseInt(Elements.get(i * 7).text());
								e.Term = Integer.parseInt(Elements.get(i * 7 + 1).text());
								e.courseNum = Elements.get(i * 7 + 2).text();
								e.CourseName = Elements.get(i * 7 + 3).text();
								e.Teacher = Elements.get(i * 7 + 4).text();
								
								
								if(Elements.get(i * 7 + 5).text().contains("已评教"))
								{
									e.isEvaluated = true;
									evaluation.add(e);
									e.id = "";
								}
								else
								{
									e.isEvaluated = false;//未评教则获其id号
									String s = getId(Elements.get(i * 7 + 6).html());
									if(s != null)
									{
										//System.out.println("id: "+s);
										e.id = s;
									}
									
									evaluation.add(0, e);
								}
								
							}
						}
					}
				
			} catch (Exception e)
			{
				Log.i(Utility.class.toString(), "resolveEvaluationInfo failed ");
			}
			return evaluation;
		}
		private static String getId(String s)
		{
			//System.out.println("id: "+s);
			
			Pattern pattern = Pattern.compile("\\bid=\\d+");    
			Matcher matcher = pattern.matcher(s);  
			if(matcher.find())
			{
				return matcher.toMatchResult().group(0).replace("id=", "");
			}
			return null;
		}
		
		
		
		
		
		
		
		
		
		
		
		// 辅助函数
		private static int getWeekSpecialNum(String s) {
			switch (s) {
			case "单周":
				return 1;
			case "双周":
				return 2;
			default:
				return -1;
			}
		}
		
		private static int startpos(String s) {
			switch (s) {
			case "第一～二节":
				return 1;
			case "第三～四节":
				return 3;
			case "第五～六节":
				return 5;
			case "第七～八节":
				return 7;
			case "第九～十节":
				return 9;
			case "第十一～十二节":
				return 11;
			}
			return -1;
		}
		// 成绩信息
		public static List<Score> resolveScoresInfo(String source)
		{
			List<Score> scores = new LinkedList<Score>();
			try
			{
				Document doc = getDoc(source);
				Element score = doc.getElementById("ctl00_MainContentPlaceHolder_GridScore");
				if(score != null)
				{
					Elements scoreElements = score.select("tr").select("td");
					if(scoreElements != null)
					{
						
							for(int i = 0; i <= scoreElements.size() / 7 - 1; i++)
							{
									Score s = new Score();
									s.Year = Integer.parseInt(scoreElements.get(i * 7).text());
									s.Term = Integer.parseInt(scoreElements.get(i * 7 + 1).text());
									s.CourseName = scoreElements.get(i * 7 + 2).text();
									s.Credit = Float.parseFloat(scoreElements.get(i * 7 + 3).text());//课程学分
									s.Type = scoreElements.get(i * 7 + 4).text();
									s._Score = scoreElements.get(i * 7 + 5).text();
									s.GPA = Float.parseFloat(scoreElements.get(i * 7 + 6).text());
									//s.StuID = ;
									if (s.GPA == 0 && !s._Score.contains("未评教"))
									{
										
										s.isFail = true;
										scores.add(0, s);
									}
									else 
									{
										s.isFail = false;
										scores.add(s);
									}
								System.out.println(s.CourseName);	
									
							}
					}
				}
			} catch (Exception e)
			{
				return scores;
			}
			return scores;
		}
		
	
				
}
