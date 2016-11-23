package com.reddit.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.reddit.R;
import com.reddit.appconstants.AppConstants;

public class FeedDetailActivity extends AppCompatActivity {
    private WebView feedDetailView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_detail);
        feedDetailView = (WebView) findViewById(R.id.webView);
        String url = getIntent().getStringExtra(AppConstants.FEED_DETAIL_URL);
        loadDetailView(url);
    }

    public void loadDetailView(String url) {
        feedDetailView.getSettings().setJavaScriptEnabled(true);
        feedDetailView.getSettings().setLoadWithOverviewMode(true);
        feedDetailView.getSettings().setUseWideViewPort(true);
        feedDetailView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }
        });
        feedDetailView.loadUrl(url);
    }

}
