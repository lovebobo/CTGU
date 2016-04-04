package com.ctguer.controller;

public class Codes {

	public static final int getVerificationCode = 0x110;//验证码获取成功
	

	public static final int loginSwitchJwc = 0x120;

	public static final int CodeSuc = 0x100;//成功
	

	
	public static final int getVerificationCodeErr = 0x130;
	
	public static final int returnmsg = 0x140;
	
	//教务相关
	public static final int loginJwc = 0x210;
	public static final int loginJwcErr = 0x220;
	public static final int loginRepeatJwc = 0x230;
	
	public static final int SpecifyName = 0x240;
	
	//成绩
	public static final int getScoreInfo = 0x310;
	public static final int getScoreInfoErr = 0x320;
	
	//课表

	public static final int getCourseTableErr = 0x410;
	public static final int CodeErr = 0x420;
	public static final int getCourseTable = 0x430;
	
	//图书
	public static final int getLibraryBookSuc = 0x500;
	public static final int getBookSuc = 0x510;
	public static final int getBookFail = 0x520;
	
	public static final int getNoBook = 0x530;
	public static final int getBookDetailSuc = 0x540;
	
	public static final int getVerCodeSuc = 0x550;
	
	public static final int getVerCodeFail = 0x560;
	
	public static final int loginLibrarySuc = 0x570;
	
	public static final int loginLibraryFail = 0x580;
	
	public static final int getLibraryBookFail = 0x590;
	
	public static final int continueBorrowFail = 0x5000;
	
	public static final int continueBorrowSuc = 0x5100;

	public static final int chonecontinueBorrowSuc = 0x5200;
	
	//评教
	public static final int getEvaluationInfo = 0x250;
	public static final int getEvaluationInfoErr = 0x251;
	
	//失物招领
	public static final int getLostSuc = 0x350;
	public static final int getLostFail = 0x351;
	public static final int getLostDetailSuc = 0x352;
	public static final int getLostDetailFail = 0x353;
	public static final int postlostfoundSuc = 0x354;
	public static final int postlostfoundFail = 0x355;
	/* 关于新闻  */
	//校园新闻
	public static final int schoolnews = 0x356;
	public static final int schoolnewsdetail = 0x357;
	public static final int instantnews = 0x358;
	public static final int instantnewsdetail = 0x359;

	//勤工俭学
	public static final int getjobSuc = 0x450;
	public static final int getjobFail = 0x451;
	public static final int getjobdetail=0x452;
	public static final int getjiajiaodetail=0x453;
	public static final int getdongtaidetail=0x454;
	
	
	//用户相关
	public static final int registerSuc = 0x550;
	public static final int registerFail = 0x560;
	public static final int login_app_Suc = 0x570;
	public static final int login_app_Fail = 0x580;
	public static final int logout_app_Suc = 0x590;
	public static final int logout_app_Fail = 0x540;
	
	//活动相关
	public static final int getActivityListSuc = 0x600;
	public static final int getActivityListFail = 0x610;
	
	public static final int launchActivityListSuc = 0x620;
	public static final int launchActivityListFail = 0x630;
	
	
	
	
}
