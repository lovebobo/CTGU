package com.ctguer;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebView;

public class WebViewActivity extends Activity {

	
	private WebView webview;
	
	private String url;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_web_view);
		
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		webview=(WebView)findViewById(R.id.webView);
		url=getIntent().getStringExtra("url");
		webview.loadUrl(url);
	}
}
