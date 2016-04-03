package com.ctguer;

import com.ctguer.model.PartTimeJob;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class ShowJobDetail extends Activity {

	private PartTimeJob jobdetail=new PartTimeJob();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_show_job_detail);
		
	   jobdetail = ( PartTimeJob)getIntent().getSerializableExtra("job");
		((TextView) findViewById(R.id.postdept)).setText(jobdetail.getPostdept()) ;
		((TextView) findViewById(R.id.posttime)).setText(jobdetail.getPosttime()) ;
		((TextView) findViewById(R.id.danwei)).setText(jobdetail.getDanwei()) ;
		((TextView) findViewById(R.id.num)).setText(jobdetail.getNumber()) ;
		((TextView) findViewById(R.id.tele)).setText(jobdetail.getTele()) ;
		((TextView) findViewById(R.id.jobtime)).setText(jobdetail.getJobtime()) ;
		((TextView) findViewById(R.id.jobaddress)).setText(jobdetail.getJobaddress()) ;
		((TextView) findViewById(R.id.jobcontent)).setText(jobdetail.getJobcontext()) ;
		((TextView) findViewById(R.id.jobxinzhi)).setText(jobdetail.getJobxinzhi()) ;
		((TextView) findViewById(R.id.jobdaiyu)).setText(jobdetail.getJobdaiyu()) ;
		((TextView) findViewById(R.id.jobneed)).setText(jobdetail.getJobneed()) ;
		((TextView) findViewById(R.id.jobtype)).setText(jobdetail.getJobtype()) ;
		
		
		
		
	}
}

