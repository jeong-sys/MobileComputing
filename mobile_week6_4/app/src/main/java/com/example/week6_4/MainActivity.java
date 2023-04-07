package com.example.week6_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText edtUrl;
    Button btnGo, btnBack;
    WebView web;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        edtUrl = (EditText) findViewById(R.id.edturl);
        btnGo = (Button) findViewById(R.id.btngo);
        btnBack = (Button) findViewById(R.id.btnback);
        web = (WebView) findViewById(R.id.webview);

        // web.setWebViewClient(new MyWebViewClient());
        web.setWebViewClient(new WebViewClient());  // 이것만 해 줘도 됨. 크롬으로 실행되지 않게.

        web.getSettings().setJavaScriptEnabled(true);


        //web.getSettings().setBuiltInZoomControls(true);

        btnGo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                web.loadUrl("https://" + edtUrl.getText().toString());

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                web.goBack();
            }
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
       /*
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            //Toast.makeText(, "Oh no! " + description, Toast.LENGTH_SHORT).show();

        }
        */

    }

}