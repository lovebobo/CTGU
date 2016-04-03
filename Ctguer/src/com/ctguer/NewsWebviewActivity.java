package com.ctguer;

import com.ctguer.model.NewsContent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class NewsWebviewActivity extends Activity {
	String data;
	WebView show;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_news_webview);
		super.onCreate(savedInstanceState);
		initData();
	}
	void initData(){
		
		show = (WebView)findViewById(R.id.newsweb_show);
		WebSettings webSettings = show.getSettings(); 
		webSettings.setSupportZoom(true); // 支持缩放    
		//webSettings.setAllowFileAccess(true); // 允许访问文件  
		//webSettings.setAppCacheEnabled(true);
		show.setWebViewClient(new WebViewClient(){
			
			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				// TODO Auto-generated method stub
				if(failingUrl.contains("#")){
					Log.d("erik", "failingurl = " + failingUrl);
					String[] temp;
					temp = failingUrl.split("#");
					view.loadUrl(temp[0]); // load page without internal
					try {
						Thread.sleep(400);
					} catch (InterruptedException e) {
 
						e.printStackTrace();
					}
					view.goBack();
					view.goBack();
				}
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				view.loadUrl(url);
				return true;
			}	
		});
	 
		//webSettings.setJavaScriptCanOpenWindowsAutomatically(true);  
	//	webSettings.setUseWideViewPort(true);//关键点    
		webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);        
		webSettings.setDisplayZoomControls(false);  
		webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本  
		webSettings.setBuiltInZoomControls(true); // 设置显示缩放按钮  
	//	webSettings.setLoadWithOverviewMode(true);   
		/*DisplayMetrics metrics = new DisplayMetrics();  
		getWindowManager().getDefaultDisplay().getMetrics(metrics);  
		int mDensity = metrics.densityDpi;  
		Log.d("maomao", "densityDpi = " + mDensity);  
		if (mDensity == 240){   
		webSettings.setDefaultZoom(ZoomDensity.FAR);  
		}
		else if (mDensity == 160) {  
		webSettings.setDefaultZoom(ZoomDensity.MEDIUM); 
		}
		else if(mDensity == 120) {  
		webSettings.setDefaultZoom(ZoomDensity.CLOSE); 
		}
		else if(mDensity == DisplayMetrics.DENSITY_XHIGH){  
		webSettings.setDefaultZoom(ZoomDensity.FAR);   
		}
		else if (mDensity == DisplayMetrics.DENSITY_TV){  
		webSettings.setDefaultZoom(ZoomDensity.FAR); 
		}
		else{  
	    webSettings.setDefaultZoom(ZoomDensity.MEDIUM); 
		}*/
		Intent getdetail = getIntent();
		data  = (String)getdetail.getSerializableExtra("detail");
		if(data.contains("null")){
			data.replace("null", "");
		}
		show.loadDataWithBaseURL(null, data, "text/html", "utf-8", null);
	}
	

}
