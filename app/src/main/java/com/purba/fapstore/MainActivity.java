package com.purba.fapstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private WebView webView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RelativeLayout nointernetlayout;
    private ProgressBar progressBar;

    private long backpressedtime;

    @Override
    public void onBackPressed() {

        if(backpressedtime+2000 > System.currentTimeMillis() )
        {
            super.onBackPressed();
            return;
        }else
        {
            Toast.makeText(getBaseContext(),"Press back again to exit",Toast.LENGTH_SHORT).show();
        }

        backpressedtime=System.currentTimeMillis();
    }




    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    webView.goBack();
                    break;

            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       webView=findViewById(R.id.webview);
       swipeRefreshLayout=findViewById(R.id.swipe);
       nointernetlayout=findViewById(R.id.no_interntlayout);

        progressBar=findViewById(R.id.progrs);



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
           @Override
           public void onRefresh() {
               webView.reload();

           }
       });

       webView.setWebChromeClient(new Mychromeclient());
       webView.setWebViewClient(new Browserclient(swipeRefreshLayout));

        WebSettings settings=webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setAppCacheEnabled(true);

        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setAppCacheEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        settings.setUseWideViewPort(true);
        settings.setSavePassword(true);
        settings.setSaveFormData(true);
        settings.setEnableSmoothTransition(true);

        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if (i==KeyEvent.KEYCODE_BACK && keyEvent.getAction()== MotionEvent.ACTION_UP && webView.canGoBack()){
                    handler.sendEmptyMessage(1);
                    return true;
                }
                return false;
            }
        });


        loadWebPage();
    }

    private void loadWebPage() {

        ConnectivityManager cm=(ConnectivityManager) MainActivity.this
        .getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=cm.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnectedOrConnecting()){

            webView.loadUrl("http://www.pritamdigibio.com/FAP_Store/index.php\n");
            nointernetlayout.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);


        }else {
            nointernetlayout.setVisibility(View.VISIBLE);
            webView.setVisibility(View.GONE);
            Toast.makeText(this,"You Don't have any active network",Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);

        }


    }

    public void ReconnectWebSite(View view) {
        loadWebPage();
    }
}