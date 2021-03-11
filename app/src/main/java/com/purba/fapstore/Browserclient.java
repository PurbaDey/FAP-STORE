package com.purba.fapstore;

import android.graphics.Bitmap;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class Browserclient extends WebViewClient {
    SwipeRefreshLayout swipeRefreshLayout;
    public Browserclient(SwipeRefreshLayout swipeRefreshLayout){
        this.swipeRefreshLayout=swipeRefreshLayout;


    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        swipeRefreshLayout.setRefreshing(false);
    }
}
