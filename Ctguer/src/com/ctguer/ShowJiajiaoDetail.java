package com.ctguer;

import com.ctguer.model.Jiajiao;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class ShowJiajiaoDetail extends Activity {

	private Jiajiao jobdetail=new Jiajiao();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_show_jiajiao_detail);
		
		jobdetail = ( Jiajiao)getIntent().getSerializableExtra("job");
		
		((TextView) findViewById(R.id.postdept)).setText(jobdetail.getPostdete()) ;
		((TextView) findViewById(R.id.posttime)).setText(jobdetail.getPosttime()) ;
		((TextView) findViewById(R.id.danwei)).setText(jobdetail.getXueyuansex()) ;
		((TextView) findViewById(R.id.num)).setText(jobdetail.getXueyuangrade()) ;
		((TextView) findViewById(R.id.tele)).setText(jobdetail.getKemu()) ;
		((TextView) findViewById(R.id.jobtime)).setText(jobdetail.getXueyuanaddress()) ;
		((TextView) findViewById(R.id.jobaddress)).setText(jobdetail.getXueyuanbeiju()) ;
		((TextView) findViewById(R.id.jobcontent)).setText(jobdetail.getJiaoyuansex()) ;
		((TextView) findViewById(R.id.jobxinzhi)).setText(jobdetail.getJiaoyuangrade()) ;
		((TextView) findViewById(R.id.jobdaiyu)).setText(jobdetail.getJiaoyuanmajor()) ;
		((TextView) findViewById(R.id.jobneed)).setText(jobdetail.getJiaoyuanneed()) ;
		((TextView) findViewById(R.id.jobtype)).setText(jobdetail.getJobdaiyu()) ;
		((TextView) findViewById(R.id.jiaoxuetime)).setText(jobdetail.getJobtime()) ;
		((TextView) findViewById(R.id.zhaopiantype)).setText(jobdetail.getJobtype()) ;
	}
}
