package com.ctguer.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.cookie.Cookie;

public class URLs {
	
	public static  List<Cookie> jwc_cookies=new ArrayList<Cookie>();//教务登陆成功返回的cookies
	public static  List<Cookie> library_cookies=new ArrayList<Cookie>();//登陆图书馆成功返回的cookies
	public static  String library_cookies1=new String();
	
	//教务选课系统一和二
	public static final String CTGU_jw = "http://210.42.38.26:84/jwc_glxt/";//84
	public static final String CTGU_jw_2="http://210.42.38.26:81/jwc_glxt/";//81
	
	//图书馆登陆
	//public static final String CTGU_ts="http://192.168.52.9/";
	public static final String CTGU_ts="http://sulcmis.lib.ctgu.edu.cn/";
	
	public static String __VIEWSTATE;//
	public static String __EVENTVALIDATION;//
	
	public static final String verificationCodeImg = "ValidateCode.aspx";//教务处登陆验证码
	public static final String logout_CTGU = ("Login.aspx?xttc=1");//退出
	//教务登陆
	public static final String login_CTGU = ("login.aspx");
	public static final String login_VIEWSTATE_1 = "/wEPDwUKMTQ4NjM5NDA3OWQYAQUeX19Db250cm9sc1JlcXVpcmVQb3N0QmFja0tleV9fFgEFCGJ0bkxvZ2luU077LK9itKNe3fhI7aoZZ+S5Ryo=";
	public static final String login_EVENTVALIDATION_1 = "/wEWBQKOmrqLAwKl1bKzCQKC3IeGDAK1qbSRCwLO44u1DVzfq830wXTY29pyqB1kTMdgWLfG";
	public static final String login_VIEWSTATE_2 = "/wEPDwUKMTQ4NjM5NDA3OWQYAQUeX19Db250cm9sc1JlcXVpcmVQb3N0QmFja0tleV9fFgEFCGJ0bkxvZ2luxH3FxhKrV5DSLNmTU4lmM+8noJI=";
	public static final String login_EVENTVALIDATION_2 = "/wEWBQK0rZn5BQKl1bKzCQKC3IeGDAK1qbSRCwLO44u1DRoeeCML9VwaJp827ywoonHLTN5B";
	
	//图书馆登陆																						
	public static final String search_Library = "http://sulcmis.lib.ctgu.edu.cn/searchresult.aspx?anywords=";//图书馆检索地址
	public static final String search_Librarybook_detail = "http://sulcmis.lib.ctgu.edu.cn/";//图书详情地址
	public static final String login_Library =CTGU_ts+"login.aspx?ReturnUrl=%2fuser%2fuserinfo.aspx";//图书馆登陆	

	public static final String ver_Code_Library =CTGU_ts+"gencheckcode.aspx";//图书馆验证码
	public static final String borrow_book =CTGU_ts+"user/bookborrowed.aspx";//图书馆借还
	public static final String reborrow_book =CTGU_ts+"user/bookborrowed.aspx";//图书馆借还
	public static String againborrow_book=CTGU_ts;//重定向图书馆借还

	
	//课表
	public static final String getCourseTable_CTGU = ("Course_Choice/Course_Schedule.aspx");
	public static final String getCourseTable_VIEWSTATE_1 = "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";
	public static final String getCourseTable_EVENTVALIDATION_1 = "/wEWIQKgvMLNAgLVtaKfCgKb2oHDAgKGzf/rAQKGzYOHCQKGzdfvCwKGzfuIAwKGzY+kCAKGzZPBAQKGzaf6BgKGzcuXDgKGzd+wBwKGzePtDALt9NH0CwLt9OWRAwLt9Mn4BQLt9N2VDQLt9OHOAgLt9PXrCwLt9JmHAwLt9K2gCALt9LHdAQLt9MX2BgKpl6yAAwKpl7C9CAKpl4SEDQKpl6ihAgLbxvmxDALExvmxDALFxvmxDALGxvmxDALHxvmxDALM08eVCofzT3F4jBL7hiEkYkEtSdP7Bpxv";
	public static final String getCourseTable_VIEWSTATE_2 = "/wEPDwUJMjI1OTcyMTIyD2QWAmYPZBYCAgMPZBYIAgUPDxYCHgRUZXh0BRMyMDExMTEyMzAy5p+v56uL56eLZGQCBw88KwANAgAPFgIeC18hRGF0YUJvdW5kZ2QMFCsAAgUDMDowFCsAAhYQHwAFDOWtpuS4mumihOitph4FVmFsdWUFDOWtpuS4mumihOitph4LTmF2aWdhdGVVcmwFHi9qd2NfZ2x4dC9TaXRlTWFwL1Rlc3QuYXNweCMzMh4HVG9vbFRpcAUM5a2m5Lia6aKE6K2mHgdFbmFibGVkZx4KU2VsZWN0YWJsZWceCERhdGFQYXRoBR4vandjX2dseHQvc2l0ZW1hcC90ZXN0LmFzcHgjMzIeCURhdGFCb3VuZGcUKwAEBQswOjAsMDoxLDA6MhQrAAIWEB8ABRDil4Yg5Z+55YW76K6h5YiSHwIFEOKXhiDln7nlhbvorqHliJIfAwUpL2p3Y19nbHh0L1BsYW5fVHJhaW4vUGxhblRyYWluX1F1ZXJ5LmFzcHgfBAUM5Z+55YW76K6h5YiSHwVnHwZnHwcFKS9qd2NfZ2x4dC9wbGFuX3RyYWluL3BsYW50cmFpbl9xdWVyeS5hc3B4HwhnZBQrAAIWEB8ABRDil4Yg5a2m5Lia5q+U5a+5HwIFEOKXhiDlrabkuJrmr5Tlr7kfAwUuL2p3Y19nbHh0L1BsYW5fVG9fU3R1ZHkvUGxhblRvU3R1ZHlfUXVlcnkuYXNweB8EBQzlrabkuJrmr5Tlr7kfBWcfBmcfBwUuL2p3Y19nbHh0L3BsYW5fdG9fc3R1ZHkvcGxhbnRvc3R1ZHlfcXVlcnkuYXNweB8IZ2QUKwACFhAfAAUQ4peGIOWtpuS4mumihOitph8CBRDil4Yg5a2m5Lia6aKE6K2mHwMFKy9qd2NfZ2x4dC9QbGFuX1RvX1N0dWR5L1N0dWR5X1dhcm5uaW5nLmFzcHgfBAUM5a2m5Lia6aKE6K2mHwVnHwZnHwcFKy9qd2NfZ2x4dC9wbGFuX3RvX3N0dWR5L3N0dWR5X3dhcm5uaW5nLmFzcHgfCGdkZAIJDzwrAA0CAA8WAh8BZ2QMFCsABwUXMDowLDA6MSwwOjIsMDozLDA6NCwwOjUUKwACFhAfAAURIHwgfCDljbPml7bkuovliqEfAgURIHwgfCDljbPml7bkuovliqEfAwUmL2p3Y19nbHh0L1N0dV9Ob3RpY2UvTm90aWNlX1F1ZXJ5LmFzcHgfBAUV5ZCE57G76YCa55+l5Y+K5LqL5YqhHwVnHwZnHwcFJi9qd2NfZ2x4dC9zdHVfbm90aWNlL25vdGljZV9xdWVyeS5hc3B4HwhnZBQrAAIWEB8ABREgfCB8IOWtpueUn+mAieivvh8CBREgfCB8IOWtpueUn+mAieivvh8DBRsvandjX2dseHQvU2l0ZU1hcC9UZXN0LmFzcHgfBAUM5a2m55Sf6YCJ6K++HwVnHwZnHwcFGy9qd2NfZ2x4dC9zaXRlbWFwL3Rlc3QuYXNweB8IZxQrAAYFEzA6MCwwOjEsMDoyLDA6MywwOjQUKwACFhAfAAUT4peGIOivvueoi+afpeivouOAgB8CBRPil4Yg6K++56iL5p+l6K+i44CAHwMFKS9qd2NfZ2x4dC9Db3Vyc2VfQ2hvaWNlL0NvdXJzZV9RdWVyeS5hc3B4HwQFFeW8gOiuvueahOivvueoi+afpeivoh8FZx8GZx8HBSkvandjX2dseHQvY291cnNlX2Nob2ljZS9jb3Vyc2VfcXVlcnkuYXNweB8IZ2QUKwACFhAfAAUT4peGIOWtpueUn+mAieivvuOAgB8CBRPil4Yg5a2m55Sf6YCJ6K++44CAHwMFKi9qd2NfZ2x4dC9Db3Vyc2VfQ2hvaWNlL0NvdXJzZV9DaG9pY2UuYXNweB8EBQzlrabnlJ/pgInor74fBWcfBmcfBwUqL2p3Y19nbHh0L2NvdXJzZV9jaG9pY2UvY291cnNlX2Nob2ljZS5hc3B4HwhnZBQrAAIWEB8ABRPil4Yg5a2m55Sf6YCA6K++44CAHwIFE+KXhiDlrabnlJ/pgIDor77jgIAfAwUrL2p3Y19nbHh0L0NvdXJzZV9DaG9pY2UvQ291cnNlX0FiYW5kb24uYXNweB8EBQzlrabnlJ/pgIDor74fBWcfBmcfBwUrL2p3Y19nbHh0L2NvdXJzZV9jaG9pY2UvY291cnNlX2FiYW5kb24uYXNweB8IZ2QUKwACFhIfAgUT4peGIOmAieS/ruivvuihqOOAgB8IZx4IU2VsZWN0ZWRnHwAFE+KXhiDpgInkv67or77ooajjgIAfAwUsL2p3Y19nbHh0L0NvdXJzZV9DaG9pY2UvQ291cnNlX1NjaGVkdWxlLmFzcHgfBWcfBmcfBAUM6YCJ5L+u6K++6KGoHwcFLC9qd2NfZ2x4dC9jb3Vyc2VfY2hvaWNlL2NvdXJzZV9zY2hlZHVsZS5hc3B4ZBQrAAIWEB8ABRPil4Yg5bey6YCJ6K++56iL44CAHwIFE+KXhiDlt7LpgInor77nqIvjgIAfAwUtL2p3Y19nbHh0L0NvdXJzZV9DaG9pY2UvU3R1X0NvdXJzZV9RdWVyeS5hc3B4HwQFFeW3sumAieS/ruivvueoi+afpeivoh8FZx8GZx8HBS0vandjX2dseHQvY291cnNlX2Nob2ljZS9zdHVfY291cnNlX3F1ZXJ5LmFzcHgfCGdkFCsAAhYQHwAFESB8IHwg5oiQ57up5p+l6K+iHwIFESB8IHwg5oiQ57up5p+l6K+iHwMFHS9qd2NfZ2x4dC9TaXRlTWFwL1Rlc3QuYXNweCMzHwQFEuWtpueUn+aIkOe7qeafpeivoh8FZx8GZx8HBR0vandjX2dseHQvc2l0ZW1hcC90ZXN0LmFzcHgjMx8IZxQrAAMFBzA6MCwwOjEUKwACFhAfAAUR4peGIOaIkOe7qeafpeivoiAfAgUR4peGIOaIkOe7qeafpeivoiAfAwUoL2p3Y19nbHh0L1N0dWRlbnRfU2NvcmUvU2NvcmVfUXVlcnkuYXNweB8EBQzmiJDnu6nmn6Xor6IfBWcfBmcfBwUoL2p3Y19nbHh0L3N0dWRlbnRfc2NvcmUvc2NvcmVfcXVlcnkuYXNweB8IZ2QUKwACFhAfAAUU4peGIOWPjOWtpuS9jeaIkOe7qSAfAgUU4peGIOWPjOWtpuS9jeaIkOe7qSAfAwUxL2p3Y19nbHh0L0RvdWJsZURlZ3JlZV9TY29yZS9EYmxEZWdyZWVfU2NvcmUuYXNweB8EBRXlj4zlrabkvY3miJDnu6nmn6Xor6IfBWcfBmcfBwUxL2p3Y19nbHh0L2RvdWJsZWRlZ3JlZV9zY29yZS9kYmxkZWdyZWVfc2NvcmUuYXNweB8IZ2QUKwACFhAfAAURIHwgfCDlkITnsbvmiqXlkI0fAgURIHwgfCDlkITnsbvmiqXlkI0fAwUdL2p3Y19nbHh0L1NpdGVNYXAvVGVzdC5hc3B4IzUfBAUM5ZCE57G75oql5ZCNHwVnHwZnHwcFHS9qd2NfZ2x4dC9zaXRlbWFwL3Rlc3QuYXNweCM1HwhnFCsACAUbMDowLDA6MSwwOjIsMDozLDA6NCwwOjUsMDo2FCsAAhYQHwAFEeKXhiDnrYnnuqfogIPor5UgHwIFEeKXhiDnrYnnuqfogIPor5UgHwMFJS9qd2NfZ2x4dC9jZXRfc3lzdGVtL3N0dWRlbnRfY2V0LmFzcHgfBAUS562J57qn6ICD6K+V5oql5ZCNHwVnHwZnHwcFJS9qd2NfZ2x4dC9jZXRfc3lzdGVtL3N0dWRlbnRfY2V0LmFzcHgfCGdkFCsAAhYQHwAFEeKXhiDlkI3ljZXmn6Xor6IgHwIFEeKXhiDlkI3ljZXmn6Xor6IgHwMFJi9qd2NfZ2x4dC9jZXRfc3lzdGVtL3N0dWRlbnRfTGlzdC5hc3B4HwQFHuetiee6p+iAg+ivleaKpeWQjeWQjeWNleafpeivoh8FZx8GZx8HBSYvandjX2dseHQvY2V0X3N5c3RlbS9zdHVkZW50X2xpc3QuYXNweB8IZ2QUKwACFhAfAAUQ4peGIOavleS4muiuvuiuoR8CBRDil4Yg5q+V5Lia6K6+6K6hHwMFLS9qd2NfZ2x4dC9HcmFkdURlc2lnbl9zeXN0ZW0vR3JhZHVEZXNpZ24uYXNweB8EBRjmr5XkuJrorr7orqHor77popjnlLPmiqUfBWcfBmcfBwUtL2p3Y19nbHh0L2dyYWR1ZGVzaWduX3N5c3RlbS9ncmFkdWRlc2lnbi5hc3B4HwhnZBQrAAIWEB8ABRDil4Yg6YeN5L+u5oql5ZCNHwIFEOKXhiDph43kv67miqXlkI0fAwUoL2p3Y19nbHh0L1JlcGVhdF9TdHVkeS9SZXBlYXRfU3R1ZHkuYXNweB8EBQzph43kv67miqXlkI0fBWcfBmcfBwUoL2p3Y19nbHh0L3JlcGVhdF9zdHVkeS9yZXBlYXRfc3R1ZHkuYXNweB8IZ2QUKwACFhAfAAUQ4peGIOmHjeiAg+aKpeWQjR8CBRDil4Yg6YeN6ICD5oql5ZCNHwMFJi9qd2NfZ2x4dC9SZXBlYXRfRXhhbS9SZXBlYXRfRXhhbS5hc3B4HwQFDOmHjeiAg+aKpeWQjR8FZx8GZx8HBSYvandjX2dseHQvcmVwZWF0X2V4YW0vcmVwZWF0X2V4YW0uYXNweB8IZ2QUKwACFhAfAAUQ4peGIOa4heeQhuiAg+ivlR8CBRDil4Yg5riF55CG6ICD6K+VHwMFJC9qd2NfZ2x4dC9DbGVhcl9FeGFtL0NsZWFyX0V4YW0uYXNweB8EBRLmuIXnkIbogIPor5XmiqXlkI0fBWcfBmcfBwUkL2p3Y19nbHh0L2NsZWFyX2V4YW0vY2xlYXJfZXhhbS5hc3B4HwhnZBQrAAIWEB8ABRbil4Yg56ys5LqM5a2m5L2N5oql5ZCNHwIFFuKXhiDnrKzkuozlrabkvY3miqXlkI0fAwUqL2p3Y19nbHh0L0RvdWJsZV9EZWdyZWUvRG91YmxlX0RlZ3JlZS5hc3B4HwQFD+WPjOWtpuS9jeaKpeWQjR8FZx8GZx8HBSovandjX2dseHQvZG91YmxlX2RlZ3JlZS9kb3VibGVfZGVncmVlLmFzcHgfCGdkFCsAAhYQHwAFESB8IHwg5a2m55Sf6K+E5pWZHwIFESB8IHwg5a2m55Sf6K+E5pWZHwMFJC9qd2NfZ2x4dC9TdHVfQXNzZXNzL1N0dV9Bc3Nlc3MuYXNweB8EBQzlrabnlJ/or4TmlZkfBWcfBmcfBwUkL2p3Y19nbHh0L3N0dV9hc3Nlc3Mvc3R1X2Fzc2Vzcy5hc3B4HwhnZBQrAAIWEB8ABREgfCB8IOS4quS6uuS/oeaBrx8CBREgfCB8IOS4quS6uuS/oeaBrx8DBR0vandjX2dseHQvU2l0ZU1hcC9UZXN0LmFzcHgjMR8EBQzkuKrkurrkv6Hmga8fBWcfBmcfBwUdL2p3Y19nbHh0L3NpdGVtYXAvdGVzdC5hc3B4IzEfCGcUKwAEBQswOjAsMDoxLDA6MhQrAAIWEB8ABRDil4Yg5Liq5Lq65L+h5oGvHwIFEOKXhiDkuKrkurrkv6Hmga8fAwUgL2p3Y19nbHh0L1N0dV9JbmZvL1N0dV9pbmZvLmFzcHgfBAUY5a2m55Sf5Liq5Lq65L+h5oGv5p+l6K+iHwVnHwZnHwcFIC9qd2NfZ2x4dC9zdHVfaW5mby9zdHVfaW5mby5hc3B4HwhnZBQrAAIWEB8ABRDil4Yg5L+h5oGv5L+u5pS5HwIFEOKXhiDkv6Hmga/kv67mlLkfAwUmL2p3Y19nbHh0L1N0dV9JbmZvL1N0dWluZm9fTW9kaWZ5LmFzcHgfBAUY5a2m55Sf5Liq5Lq65L+h5oGv5L+u5pS5HwVnHwZnHwcFJi9qd2NfZ2x4dC9zdHVfaW5mby9zdHVpbmZvX21vZGlmeS5hc3B4HwhnZBQrAAIWEB8ABRDil4Yg5a+G56CB5L+u5pS5HwIFEOKXhiDlr4bnoIHkv67mlLkfAwUkL2p3Y19nbHh0L1N0dV9JbmZvL1N0dV9QYXNzd29yZC5hc3B4HwQFGOWtpueUn+eUqOaIt+WvhueggeS/ruaUuR8FZx8GZx8HBSQvandjX2dseHQvc3R1X2luZm8vc3R1X3Bhc3N3b3JkLmFzcHgfCGdkZAILD2QWCGYPEA8WBh4NRGF0YVRleHRGaWVsZAUEdGV4dB4ORGF0YVZhbHVlRmllbGQFBXZhbHVlHwFnZBAVGggo5LiN5aGrKQQyMDIwBDIwMTkEMjAxOAQyMDE3BDIwMTYEMjAxNQQyMDE0BDIwMTMEMjAxMgQyMDExBDIwMTAEMjAwOQQyMDA4BDIwMDcEMjAwNgQyMDA1BDIwMDQEMjAwMwQyMDAyBDIwMDEEMjAwMAQxOTk5BDE5OTgEMTk5NwQxOTk2FRoBMAQyMDIwBDIwMTkEMjAxOAQyMDE3BDIwMTYEMjAxNQQyMDE0BDIwMTMEMjAxMgQyMDExBDIwMTAEMjAwOQQyMDA4BDIwMDcEMjAwNgQyMDA1BDIwMDQEMjAwMwQyMDAyBDIwMDEEMjAwMAQxOTk5BDE5OTgEMTk5NwQxOTk2FCsDGmdnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnFgECB2QCAQ8QDxYGHwoFBHRleHQfCwUFdmFsdWUfAWdkEBUFCCjkuI3loaspDOaYpeWto+WtpuacnwzlpI/lraPlrabmnJ8M56eL5a2j5a2m5pyfDOWGrOWto+WtpuacnxUFATABMQEyATMBNBQrAwVnZ2dnZxYBAgNkAgMPPCsADQEADxYEHwFnHgtfIUl0ZW1Db3VudAIGZBYCZg9kFg4CAQ9kFhBmDw8WAh8ABQ/nrKzkuIDvvZ7kuozoioJkZAIBDw8WAh8ABQYmbmJzcDtkZAICDw8WAh8ABQYmbmJzcDtkZAIDDw8WAh8ABQYmbmJzcDtkZAIEDw8WAh8ABQYmbmJzcDtkZAIFDw8WAh8ABQYmbmJzcDtkZAIGDw8WAh8ABQYmbmJzcDtkZAIHDw8WAh8ABQYmbmJzcDtkZAICD2QWEGYPDxYCHwAFD+esrOS4ie+9nuWbm+iKgmRkAgEPDxYCHwAFBiZuYnNwO2RkAgIPDxYCHwAFBiZuYnNwO2RkAgMPDxYCHwAFBiZuYnNwO2RkAgQPDxYCHwAFBiZuYnNwO2RkAgUPDxYCHwAFBiZuYnNwO2RkAgYPDxYCHwAFBiZuYnNwO2RkAgcPDxYCHwAFBiZuYnNwO2RkAgMPZBYQZg8PFgIfAAUP56ys5LqU772e5YWt6IqCZGQCAQ8PFgIfAAUGJm5ic3A7ZGQCAg8PFgIfAAUGJm5ic3A7ZGQCAw8PFgIfAAUGJm5ic3A7ZGQCBA8PFgIfAAUGJm5ic3A7ZGQCBQ8PFgIfAAUGJm5ic3A7ZGQCBg8PFgIfAAUGJm5ic3A7ZGQCBw8PFgIfAAUGJm5ic3A7ZGQCBA9kFhBmDw8WAh8ABQ/nrKzkuIPvvZ7lhavoioJkZAIBDw8WAh8ABQYmbmJzcDtkZAICDw8WAh8ABQYmbmJzcDtkZAIDDw8WAh8ABQYmbmJzcDtkZAIEDw8WAh8ABQYmbmJzcDtkZAIFDw8WAh8ABQYmbmJzcDtkZAIGDw8WAh8ABQYmbmJzcDtkZAIHDw8WAh8ABQYmbmJzcDtkZAIFD2QWEGYPDxYCHwAFD+esrOS5ne+9nuWNgeiKgmRkAgEPDxYCHwAFBiZuYnNwO2RkAgIPDxYCHwAFBiZuYnNwO2RkAgMPDxYCHwAFBiZuYnNwO2RkAgQPDxYCHwAFBiZuYnNwO2RkAgUPDxYCHwAFBiZuYnNwO2RkAgYPDxYCHwAFBiZuYnNwO2RkAgcPDxYCHwAFBiZuYnNwO2RkAgYPZBYQZg8PFgIfAAUV56ys5Y2B5LiA772e5Y2B5LqM6IqCZGQCAQ8PFgIfAAUGJm5ic3A7ZGQCAg8PFgIfAAUGJm5ic3A7ZGQCAw8PFgIfAAUGJm5ic3A7ZGQCBA8PFgIfAAUGJm5ic3A7ZGQCBQ8PFgIfAAUGJm5ic3A7ZGQCBg8PFgIfAAUGJm5ic3A7ZGQCBw8PFgIfAAUGJm5ic3A7ZGQCBw8PFgIeB1Zpc2libGVoZGQCBA8PFgIfAAXDAeavleS4muWunuS5oCg254+tKSZuYnNwOyZuYnNwOyZuYnNwOzEtNOWRqCZuYnNwO+eOi+S/iuiLsSBIMDcxLOmHkeaelyBIMDY4LOmtj+WugSBIMDc2LOadqOS4luWGmyBIMDY5LOmZiOaYjiBIMDY3LOiHp+WFhuelpSBIMDk2PGJyPumhueebruWunuiurSgy54+tKSZuYnNwOyZuYnNwOyZuYnNwOzYtOeWRqCZuYnNwO+mZiOaYjiBIMDY3PGJyPmRkGAMFHl9fQ29udHJvbHNSZXF1aXJlUG9zdEJhY2tLZXlfXxYBBSZjdGwwMCRNYWluQ29udGVudFBsYWNlSG9sZGVyJEJ0blNlYXJjaAUmY3RsMDAkTWFpbkNvbnRlbnRQbGFjZUhvbGRlciRHcmlkU2NvcmUPPCsACgEIAgFkBQtjdGwwMCRNZW51MQ8PZAUnIHx8IHx8IOWtpueUn+mAieivvlzil4Yg6YCJ5L+u6K++6KGo44CAZKzpF6g7Up4GTVwpAeQ9Z/5+iVLZ";
	public static final String getCourseTable_EVENTVALIDATION_2 = "/wEWIQKxn5SoCwLVtaKfCgKb2oHDAgKGzf/rAQKGzYOHCQKGzdfvCwKGzfuIAwKGzY+kCAKGzZPBAQKGzaf6BgKGzcuXDgKGzd+wBwKGzePtDALt9NH0CwLt9OWRAwLt9Mn4BQLt9N2VDQLt9OHOAgLt9PXrCwLt9JmHAwLt9K2gCALt9LHdAQLt9MX2BgKpl6yAAwKpl7C9CAKpl4SEDQKpl6ihAgLbxvmxDALExvmxDALFxvmxDALGxvmxDALHxvmxDALM08eVCgMpnC7vs+lr/RZAqFhsItp94/mf";
	public static final String getCourseTable_EVENTTARGET_2 = ""; 
	public static final String getCourseTable_EVENTARGUMENT_2 = "";
	
	//失物招领
	public static final String lostfound="http://www.bbangnet.com/";
	public static final String getLostMessage = "http://www.bbangnet.com/list.php?cid=29&page=";
	public static final String getFoundMessage ="http://www.bbangnet.com/list.php?cid=30&page=";
	public static final String postLost ="http://www.bbangnet.com/adder.php?id=29";
	public static final String postfound ="http://www.bbangnet.com/adder.php?id=30";
	
	//勤工俭学
	
	public static final String xiaowai="http://qgzx.ctgu.edu.cn/view.jsp?class=1&pages=";
	public static final String job="http://qgzx.ctgu.edu.cn/";
	public static final String jiajiao="http://qgzx.ctgu.edu.cn/view.jsp?class=11&pages=";
	public static final String xiaonei ="http://qgzx.ctgu.edu.cn/view.jsp?class=2&pages=";
	public static final String dongtai="http://qgzx.ctgu.edu.cn/view.jsp?class=3&pages=";
	
	//成绩
	public static final String score_CTGU = ("Student_Score/Score_Query.aspx");//成绩
	public static final String fileScore="scores_%s.dat";//成绩文件名
	
	//课表
	public static final String courseTables="course_%s.dat";//课表文件名 
	public static final String othercourseTables="othercourse_%s.dat";//实验课表文件名 
	public static final String bookTables="books_%s.dat";//图书文件名 
	
	public static final String lostDetailfile = "lost.dat";
	public static final String foundDetailfile = "found.dat";

	/*   新闻       */
	//校园新闻
	public static final String shoolnewsdetail = "http://syw.ctgu.edu.cn/";//查找校园新闻详细的时候url的前缀
	public static final String shoolnewsfile = "schoolnew.dat";
	public static final String schoolnewurl = "http://syw.ctgu.edu.cn/xxyw.htm";
	public static final String nextschoolnewurl = "http://syw.ctgu.edu.cn/xxyw/";
	//教务处即时新闻
	public static final String instantnewsdetail1 = "http://jwc.ctgu.edu.cn/news_more.asp?page=";
	public static final String instantnewsfile = "instantnew.dat";
	public static final String instantnewsdetail2 = "&word=&lm=&lm2=68&lmname=0&open=1&n=30&hot=0&tj=0&soso=&dot=0&lryname=";
	//教务处考试栏目
	public static final String examnewsdetail1 = "http://jwc.ctgu.edu.cn/news_more.asp?page=";
	public static final String examnewsfile = "examnew.dat";
	public static final String ajbnewsfile = "ajbnew.dat";
	public static final String examnewsdetail2 = "&word=&lm=&lm2=69&lmname=0&open=1&n=30&hot=0&tj=0&soso=&dot=0&lryname=";
	//教务处新闻详细链接
	public static final String jiaowudetail = "http://jwc.ctgu.edu.cn/";
	
	//用户相关
	public static final String userredister = "http://114.215.85.210/ThinkCMF/index.php?g=Ctgu&m=User&a=douserregister";
	public static final String userlogin = "http://114.215.85.210/ThinkCMF/index.php?g=Ctgu&m=User&a=dostudentlogin";
	public static final String userlogonout = "http://114.215.85.210/ThinkCMF/index.php?g=Ctgu&m=User&a=logout";
	public static final String getNewsList = "http://114.215.85.210/ThinkCMF/index.php?g=Ctgu&m=User&a=newsGet";
	public static final String uresfile = "uresfile.dat";
	
	//活动相关
	public static final String getactivity = "http://114.215.85.210/ThinkCMF/index.php?g=Ctgu&m=User&a=activityGet";
	public static final String activityfile = "activityfile.dat";
	
	public static final String launchactivity="http://114.215.85.210/ThinkCMF/index.php?g=Ctgu&m=User&a=launchActivity";
	
	public static final String getCommentsList="http://114.215.85.210/ThinkCMF/index.php?g=Ctgu&m=User&a=commentGet";
	
	public static final String launchComments="http://114.215.85.210/ThinkCMF/index.php?g=Ctgu&m=User&a=launchComment";
	
	public static final String launchPraise="http://114.215.85.210/ThinkCMF/index.php?g=Ctgu&m=User&a=launchPraise";
	
	public static final String signActivity="http://114.215.85.210/ThinkCMF/index.php?g=Ctgu&m=User&a=activitySign";
	
	
	
	//勤工俭学
	public static final String xiaowaiDetailfile = "xiaowai.dat";
	public static final String jiajiaoDetailfile = "jiajiao.dat";
	public static final String xiaoneiDetailfile = "xiaonei.dat";
	public static final String dongtaiDetailfile = "dongtai.dat";
	

	//评教服务器不同传参后面再说
		public static final String evaluation_CTGU = ("Stu_Assess/Stu_Assess.aspx");//获得评教信息
		public static final String evaluation2_CTGU = ("Stu_Assess/Stu_Assess_Proc.aspx?id=");//分数填好后点击确定提交
		public static final String evaluation3_CTGU = ("");//弹框提示”已经提交“，关闭弹框
		public static final String evaluation4_CTGU = ("");//关闭按钮
	
	
	// file base path
	public static final String fileBasePath = "/data/data/com.ctguer/files/";//文件存储基础路径
	
	public static String getURLValue(int type,int key)//key 1VIEWSTATE 2EVENTVALIDATION
	{
		boolean Switch=NetworkTask.ConnectCount > 0;
		String root = "";
		switch(type){//1login 2CourseTable 3grade 4evaluation
		case 1:
			if(key==1)
				root=Switch?login_VIEWSTATE_2:login_VIEWSTATE_1;
			else if(key==2)
				root=Switch?login_EVENTVALIDATION_2:login_EVENTVALIDATION_1;
		break;
		
		case 2:
			if(key==1)
				root=Switch?getCourseTable_VIEWSTATE_2:getCourseTable_VIEWSTATE_1;
			else if(key==2)
				root=Switch?getCourseTable_EVENTVALIDATION_2:getCourseTable_EVENTVALIDATION_1;
		break;
		
		case 3:
			//TODO
		break;
		}
		
		return root;
	}
	
	public static String getURL(String u)
	{
		String jwurl=CTGU_jw;
		if(NetworkTask.ConnectCount > 0)
		{
			jwurl=CTGU_jw_2;
			System.out.println("url jw2");
		}
		return jwurl + u;
	}
	
	public static String getFileScorePath()
	{
		//TODO 当前学号
		return String.format(fileScore, "2014");
	}
}
