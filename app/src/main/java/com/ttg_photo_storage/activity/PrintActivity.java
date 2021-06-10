package com.ttg_photo_storage.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.content.Context;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.ttg_photo_storage.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PrintActivity extends AppCompatActivity {

    @BindView(R.id.webView)
    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.print);
        ButterKnife.bind (this);
        String url = getIntent ().getStringExtra ("print");
        Log.e ("TEsting=====", url.toString ());
        webview.getSettings ().setSupportZoom (true);
        webview.getSettings ().setJavaScriptEnabled (true);
        webview.loadUrl (url);
//        webView.webViewClient = WebViewClient();
//        webView.settings.setSupportZoom(true)
//        webView.settings.javaScriptEnabled = true


//        myWebView = (WebView) findViewById (R.id.webView);
//        myWebView.getSettings().setJavaScriptEnabled(true);
//        myWebView.loadUrl(url );

    }
}