package ui;

import java.util.List;

import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.util.EncodingUtils;

import tools.AppManager;
import tools.Logger;
import tools.UIHelper;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.vikaa.meidi.R;

import config.CommonValue;
import config.QYRestClient;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class CreateView extends AppActivity {
	private WebView webView;
	private ProgressDialog loadingPd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_view);
		initUI();
		initData();
	}
	
	private void initUI() {
		webView = (WebView) findViewById(R.id.webview);
	}
	
	private void initData() {
		String url = getIntent().getStringExtra("url");
		webView.getSettings().setLightTouchEnabled(true);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.clearCache(true);
		webView.clearHistory();
		webView.clearFormData();
		
		webView.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				Logger.i(url);
				view.loadUrl(url);
				return true;
			}
			public void onReceivedSslError(WebView view,
					SslErrorHandler handler, SslError error) {
				handler.proceed();
			}
		});
		webView.setWebChromeClient(new WebChromeClient() {
		    public void onProgressChanged(WebView view, int progress) {
		        setTitle("页面加载中，请稍候..." + progress + "%");
		        setProgress(progress * 100);
		        if (progress == 100) {
		        	UIHelper.dismissProgress(loadingPd);
		        }
		    }
		});
		loadingPd = UIHelper.showProgress(this, null, null, true);
		webView.loadUrl(url);
	}
	
	public void ButtonClick(View v) {
		switch (v.getId()) {
		case R.id.leftBarButton:
			AppManager.getAppManager().finishActivity(this);
			break;
		default:
			break;
		}
	}
	
}
