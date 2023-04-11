package com.example.mobile_week6_hw2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    WebView webView;
    Button BtnNaver, BtnDaum, BtnGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hw_week6_2);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        webView = findViewById(R.id.webview);
        BtnNaver = findViewById(R.id.btn_naver);
        BtnDaum = findViewById(R.id.btn_daum);
        BtnGoogle = findViewById(R.id.btn_google);

        webView.setWebViewClient(new MyWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);

        BtnNaver.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {webView.loadUrl("https://www.naver.com");}
        });

        BtnDaum.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {webView.loadUrl("https://www.daum.net");}
        });

        BtnGoogle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {webView.loadUrl("https://www.google.com");}
        });
    }

    class MyWebViewClient extends WebViewClient {
        @Override

        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }
    }
}