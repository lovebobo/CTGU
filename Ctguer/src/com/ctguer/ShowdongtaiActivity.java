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
		progressDialog.setTitle("���ڼ���");
		progressDialog.setMessage("����Ŭ�����������У������ĵȺ�...");
		
		 webView = (WebView) findViewById(R.id.webView);
		WebSettings webSettings = webView.getSettings(); 
		webSettings.setSupportZoom(true); // ֧������   
	//	webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);        
	//	webSettings.setDisplayZoomControls(false);  
		webSettings.setJavaScriptEnabled(true); // ����֧��javascript�ű�  
		webSettings.setBuiltInZoomControls(true); // ������ʾ���Ű�ť 
	//	webSettings.setUseWideViewPort(true); 
		webSettings.setLoadWithOverviewMode(true); 
		webView.loadUrl(url);
	
		
		
		webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO Auto-generated method stub
                if (newProgress == 100) {
                    // ��ҳ�������
                	progressDialog.dismiss();

                } else {
                    // ������
                	progressDialog.show();

                }

            }
        });
	}
	//��д�������������ص��߼�
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            if(webView.canGoBack())
            {
                webView.goBack();//������һҳ��
                return true;
            }
            else
            {
                finish();//�˳�����
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
	 

	


