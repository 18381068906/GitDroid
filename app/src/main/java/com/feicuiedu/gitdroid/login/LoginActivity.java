package com.feicuiedu.gitdroid.login;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.feicuiedu.gitdroid.R;
import com.feicuiedu.gitdroid.network.GitHubApi;

import java.net.URI;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifImageView;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.webView)WebView webView;
    @BindView(R.id.gifImageView)GifImageView gifImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        //清除记录
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        webView.loadUrl(GitHubApi.AUTH_RUL);
        webView.setFocusable(true);
        webView.setFocusableInTouchMode(true);
        // 主要为了监听进度,完成后隐藏gif图片
        webView.setWebChromeClient(webChromeClient);
        // 监听webview
        webView.setWebViewClient(webViewClient);
    }
    private WebChromeClient webChromeClient = new WebChromeClient(){
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress>=100){
                gifImageView.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
            }
        }
    };
    private WebViewClient webViewClient = new WebViewClient(){
        //每次刷新webView时，判断返回的是不是我们自己的callback
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Uri uri = Uri.parse(url);
            //判断是否是我们自己的callback
            if (GitHubApi.CALL_BACK.equals(uri.getScheme())){
                //获取code
                String code = uri.getQueryParameter("code");
            }
            return super.shouldOverrideUrlLoading(view, url);
        }
    };
}
