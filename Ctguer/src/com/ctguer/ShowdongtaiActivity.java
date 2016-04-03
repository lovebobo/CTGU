package com.ctguer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.LayoutAlgorithm;

public class ShowdongtaiActivity extends Activity {

	protected ProgressDialog progressDialog;
	WebView webView ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String url=getIntent().getStringExtra("url");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_showdongtai);
		progressDialog = new ProgressDialog(ShowdongtaiActivity.this);
		progressDialog.setTitle("正在加载");
		progressDialog.setMessage("正在努力加载数据中，请耐心等候...");
		
		 webView = (WebView) findViewById(R.id.webView);
		WebSettings webSettings = webView.getSettings(); 
		webSettings.setSupportZoom(true); // 支持缩放   
	//	webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);        
	//	webSettings.setDisplayZoomControls(false);  
		webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本  
		webSettings.setBuiltInZoomControls(true); // 设置显示缩放按钮 
	//	webSettings.setUseWideViewPort(true); 
		webSettings.setLoadWithOverviewMode(true); 
		webView.loadUrl(url);
	
		
		
		webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO Auto-generated method stub
                if (newProgress == 100) {
                    // 网页加载完成
                	progressDialog.dismiss();

                } else {
                    // 加载中
                	progressDialog.show();

                }

            }
        });
	}
	//改写物理按键——返回的逻辑
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            if(webView.canGoBack())
            {
                webView.goBack();//返回上一页面
                return true;
            }
            else
            {
                finish();//退出程序
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
	 

	


