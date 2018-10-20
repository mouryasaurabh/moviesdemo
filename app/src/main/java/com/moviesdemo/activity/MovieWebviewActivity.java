package com.moviesdemo.activity;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.moviesdemo.R;
import com.moviesdemo.utils.AppConstants;


public class MovieWebviewActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_webview);
        progressBar = findViewById(R.id.progress_loader);
        webView = findViewById(R.id.webview);
        if(getIntent()!=null && getIntent().hasExtra(AppConstants.MOVIE_URL) &&
                !TextUtils.isEmpty(getIntent().getStringExtra(AppConstants.MOVIE_URL))){
            String url=getIntent().getStringExtra(AppConstants.MOVIE_URL);
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webView.loadUrl(url);
            webView.setWebViewClient(webViewClient);
        }else{
            finish();
        }

    }

    WebViewClient webViewClient= new WebViewClient(){
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            showProgress(true);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            showProgress(false);
        }
    };

    private void showProgress(boolean showprog){
        if(showprog){
            webView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }else{
            webView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }
}

