package com.example.administrator.wedingapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WeddingActivity extends AppCompatActivity {
    private WebView mWebView;

    //实现交互接口
    interface MyJavaScriptInterface {
        void showToast();

        String htmlToNativeToJs();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weding);

        mWebView = (WebView) findViewById(R.id.wed_web_view);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        setJavascriptInterface();

        mWebView.loadUrl("http://192.168.5.7/wedding/wedding.html");
    }

    //返回上一页
    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack())
            mWebView.goBack();
        else
            super.onBackPressed();
    }

    public void setJavascriptInterface() {
        //第一步 设置javascript 可用
        mWebView.getSettings().setJavaScriptEnabled(true);

        MyJavaScriptInterface myJavaScriptInterface = new MyJavaScriptInterface() {
            @JavascriptInterface
            @Override
            public void showToast() {
                startActivity(new Intent(WeddingActivity.this, GridViewNetActivity.class));
            }

            @JavascriptInterface
            @Override
            public String htmlToNativeToJs() {
                return "http://www.baidu.com";
            }
        };

        //第三步 添加javaScript交互接口到webview
        mWebView.addJavascriptInterface(myJavaScriptInterface, "MyJavaScriptInterface");
    }
}
