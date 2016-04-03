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
	
	//����
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
		
		// ��HTML������ص�doc��
		private static Document getDoc(String source)
		{
			if (source == null || source.length() == 0)
				return null;
		 	Document doc = Jsoup.parse(source); 
		 	return doc;
		}
		
		
		// �����������
		public static String[] Element2Array(Element e) {
			String tmp = e.html().replace("<br />", "##").replace("&nbsp;", "##");
			String[] result = tmp.split("##");
			if (result.length == 0)
				return null;
			return result;

		}
		
		// ��ȡ�༶��
		public static String pickClassNo(String t) {
			int end = t.indexOf("��)");
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
		
		// �����α��µ������γ̣�Ҳ����ʵ��γ�
		public static String[] resolveOtherCourses(String source) 
		{
			String[] other=null;
			// �α������һЩ�γ̽���
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
		
		//��ȡ�鼮����
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
		// �����α�
		public static List<Course> ResolveDataToConstructTable(final Context act,String source)
		{
			List<Course> OneWeekCourses = new ArrayList<Course>();
			if (source == null || source.length() == 0)
				return null;
			String html = source;
			Document doc = Jsoup.parse(html);
			
			//ȡ��ʵ��γ̲���������
			String[] otherCourse=resolveOtherCourses(source);
			if(otherCourse!=null)
			{
				FileIO.saveObjectToFile(act, URLs.othercourseTables, otherCourse);
			}
			
			//�α�
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
					classstart = startpos(element.text());// �ڼ��ڿ�
				}

				String[] tmp = Element2Array(element);
				//System.out.println("tmp :" + tmp);
				if (tmp != null) {
					for (int k = 0; k < tmp.length / 5; k++) {
						if (tmp[k * 5].contains("��)")) {
							classnum = pickClassNo(tmp[k * 5]);
							//ƥ��10��
						}

						String[] numsStrings = tmp[k * 5 + 2].replace("��", "")
								.split("-");
						weekstart = Integer.parseInt(numsStrings[0]);
						weekperiod = Integer.parseInt(numsStrings[1]) - weekstart
								+ 1;

						Course tmpcourse = new Course();
						//tmpcourse.ID = tmpcourse.hashCode();// !Warn Hash�����ж��Ƿ�Ϊͬһ�ÿ�
						tmpcourse.Title = tmp[k * 5]
								.replaceAll("\\([\\d]+��\\)", "");// ƥ��10��;
						tmpcourse.ClassNum = classnum; // Ҫ���»�ȡ����Ϊ���������ڿ���һ����Ҫ�ֱ������
						tmpcourse.TeacherName = tmp[k * 5 + 4];
						tmpcourse.ClassRoomPos = tmp[k * 5 + 1];
						tmpcourse.StartSection = classstart;
						tmpcourse.CoursePeriod = 2;
						tmpcourse.StartWeek = weekstart;
						tmpcourse.WeekPeriod = weekperiod;
						tmpcourse.WeekNum = count % 8;// ��ͬ
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
		
		// ��������
		// ����ֵ����
		public static List<EvaluateTeaching> resolveEvaluationInfo(String source) 
		{
			System.out.println("����������Ϣ�� ");
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
								
								
								if(Elements.get(i * 7 + 5).text().contains("������"))
								{
									e.isEvaluated = true;
									evaluation.add(e);
									e.id = "";
								}
								else
								{
									e.isEvaluated = false;//δ���������id��
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
		
		
		
		
		
		
		
		
		
		
		
		// ��������
		private static int getWeekSpecialNum(String s) {
			switch (s) {
			case "����":
				return 1;
			case "˫��":
				return 2;
			default:
				return -1;
			}
		}
		
		private static int startpos(String s) {
			switch (s) {
			case "��һ������":
				return 1;
			case "�������Ľ�":
				return 3;
			case "���填����":
				return 5;
			case "���ߡ��˽�":
				return 7;
			case "�ھš�ʮ��":
				return 9;
			case "��ʮһ��ʮ����":
				return 11;
			}
			return -1;
		}
		// �ɼ���Ϣ
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
									s.Credit = Float.parseFloat(scoreElements.get(i * 7 + 3).text());//�γ�ѧ��
									s.Type = scoreElements.get(i * 7 + 4).text();
									s._Score = scoreElements.get(i * 7 + 5).text();
									s.GPA = Float.parseFloat(scoreElements.get(i * 7 + 6).text());
									//s.StuID = ;
									if (s.GPA == 0 && !s._Score.contains("δ����"))
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
