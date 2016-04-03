package com.ctguer;

import android.os.Bundle;

public class Association extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_association);
		
		/*View convertView = LayoutInflater.from(this).inflate(
		R.layout.activity_main, null);
		LinearLayout ignored_view =(LinearLayout)convertView.findViewById(R.id.FrameContent);
		MainActivity.resideMenu.addIgnoredView(ignored_view);*/
		MainActivity.resideMenu.addIgnoredView(MainActivity.FragmentView);
	}
}
